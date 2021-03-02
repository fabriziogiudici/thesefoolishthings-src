/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - git clone https://bitbucket.org/tidalwave/northernwind-src.git
 * %%
 * Copyright (C) 2009 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
 * %%
 * *********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * *********************************************************************************************************************
 *
 * $Id$
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.role.spring.spi;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import it.tidalwave.util.Task;
import it.tidalwave.role.ContextManager;
import it.tidalwave.role.spi.ContextManagerProvider;
import it.tidalwave.dci.annotation.DciContext;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class DciContextWithAutoThreadBindingAspectTest
  {
    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    static class Support
      {
        public Map<String, AtomicInteger> invocationCount = new HashMap<>();

        public void publicMethod()
          {
            registerMethodInvocation("publicMethod");
          }

        protected void protectedMethod()
          {
            registerMethodInvocation("protectedMethod");
          }

        /* package */ void packageMethod()
          {
            registerMethodInvocation("packageMethod");
          }

        private void registerMethodInvocation (final @Nonnull String methodName)
          {
            log.info("{}()", methodName);
            AtomicInteger count = invocationCount.get(methodName);

            if (count == null)
              {
                invocationCount.put(methodName, count = new AtomicInteger(0));
              }

            count.incrementAndGet();
//            invocationCount.computeIfAbsent(methodName, s -> new AtomicInteger(0)).incrementAndGet();
          }
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    static class WithoutAnnotation extends Support
      {
        @Override
        public void publicMethod()
          {
            super.publicMethod();
          }

        @Override
        protected void protectedMethod()
          {
            super.protectedMethod();
          }

        @Override
        /* package */ void packageMethod()
          {
            super.packageMethod();
          }
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    @DciContext
    static class WithAnnotationButNoAutoThreadBinding extends Support
      {
        @Override
        public void publicMethod()
          {
            super.publicMethod();
          }

        @Override
        protected void protectedMethod()
          {
            super.protectedMethod();
          }

        @Override
        /* package */ void packageMethod()
          {
            super.packageMethod();
          }
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    @DciContext(autoThreadBinding = true)
    static class WithAnnotationAndAutoThreadBinding extends Support
      {
        @Override
        public void publicMethod()
          {
            super.publicMethod();
          }

        @Override
        protected void protectedMethod()
          {
            super.protectedMethod();
          }

        @Override
        /* package */ void packageMethod()
          {
            super.packageMethod();
          }
      }

    private ContextManager contextManager;

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    @BeforeClass
    public void setup()
      throws Throwable
      {
        contextManager = mock(ContextManager.class);
        doAnswer(new Answer()
          {
            @Override
            public Object answer (final InvocationOnMock invocation)
              throws Throwable
              {
                final Task task = (Task)invocation.getArguments()[1];
                return task.run();
              }
          }).when(contextManager).runWithContext(anyObject(), any(Task.class));

        ContextManager.Locator.set(new ContextManagerProvider()
          {
            @Override
            public ContextManager getContextManager()
              {
                return contextManager;
              }
          });
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    @Test(dataProvider = "methodNames")
    public void must_not_bind_context_when_no_annotation (final @Nonnull String methodName)
      throws Exception
      {
        // given
        final Support object = new WithoutAnnotation();
        // when
        invoke(object, methodName);
        // then
        verifyZeroInteractions(contextManager);
        verifyMethodInvocations(object, methodName);
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    @Test(dataProvider = "methodNames")
    public void must_not_bind_context_when_annotation_present_but_no_autoThreadBinding(final @Nonnull String methodName)
      throws Exception
      {
        // given
        final Support object = new WithAnnotationButNoAutoThreadBinding();
        // when
        invoke(object, methodName);
        // then
        verifyZeroInteractions(contextManager);
        verifyMethodInvocations(object, methodName);
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    @Test(dataProvider = "methodNames")
    public void must_bind_context_when_annotation_present_and_autoThreadBinding (final @Nonnull String methodName)
      throws Throwable
      {
        // given
        final Support object = new WithAnnotationAndAutoThreadBinding();
        // when
        invoke(object, methodName);
        // then
        verify(contextManager, times(1)).runWithContext(same(object), any(Task.class));
        verifyNoMoreInteractions(contextManager);
        verifyMethodInvocations(object, methodName);
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    @DataProvider
    private static Object[][] methodNames()
      {
        return new Object[][]
          {
            {"publicMethod"}, {"protectedMethod"}, {"packageMethod"}
          };
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    private void invoke (final @Nonnull Object object, final @Nonnull String methodName)
      throws Exception
      {
        final Method method = object.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(object);
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    private void verifyMethodInvocations (final @Nonnull Support object, final @Nonnull String methodName)
      {
        assertThat("target method not invoked: " + methodName,
                   object.invocationCount.size(), is(1));
        assertThat("extra method invoked: " + methodName,
                   object.invocationCount.getOrDefault(methodName, new AtomicInteger(0)).get(), is(1));
      }
  }
