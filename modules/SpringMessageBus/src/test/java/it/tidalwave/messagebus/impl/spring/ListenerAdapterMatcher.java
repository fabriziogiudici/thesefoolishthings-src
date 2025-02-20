/*
 * *************************************************************************************************************************************************************
 *
 * TheseFoolishThings: Miscellaneous utilities
 * http://tidalwave.it/projects/thesefoolishthings
 *
 * Copyright (C) 2009 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
 *
 * *************************************************************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 * *************************************************************************************************************************************************************
 *
 * git clone https://bitbucket.org/tidalwave/thesefoolishthings-src
 * git clone https://github.com/tidalwave-it/thesefoolishthings-src
 *
 * *************************************************************************************************************************************************************
 */
package it.tidalwave.messagebus.impl.spring;

import jakarta.annotation.Nonnull;
import it.tidalwave.messagebus.impl.spring.MessageBusAdapterFactory.MessageBusListenerAdapter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;
import org.mockito.ArgumentMatcher;
import static lombok.AccessLevel.PRIVATE;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@RequiredArgsConstructor(access = PRIVATE) @AllArgsConstructor @ToString
public class ListenerAdapterMatcher<T> implements ArgumentMatcher<MessageBusListenerAdapter<T>>
  {
    @With
    public String methodName;

    @With
    public Object owner;

    @Nonnull
    public Class<?> topic;

    @Override
    public boolean matches (final MessageBusListenerAdapter<T> listener)
      {
        return (methodName.equals(listener.getMethod().getName())
                && (owner == listener.getOwner())
                && topic.equals(listener.getTopic()));
      }

    @Nonnull
    public static <T> ListenerAdapterMatcher<T> listenerAdapter (@Nonnull final Class<T> topic)
      {
        return new ListenerAdapterMatcher<>(topic);
      }

//    @Override FIXME!
//    public void describeTo (final @Nonnull Description description)
//      {
//        description.appendText(toString());
//      }
  }
