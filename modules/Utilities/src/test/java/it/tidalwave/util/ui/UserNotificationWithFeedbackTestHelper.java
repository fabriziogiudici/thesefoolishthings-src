/*
 * #%L
 * *********************************************************************************************************************
 *
 * These Foolish Things - Miscellaneous utilities
 * http://thesefoolishthings.tidalwave.it - git clone git@bitbucket.org:tidalwave/thesefoolishthings-src.git
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
package it.tidalwave.util.ui;

import javax.annotation.Nonnull;
import java.awt.EventQueue;
import lombok.NoArgsConstructor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import lombok.extern.slf4j.Slf4j;
import static lombok.AccessLevel.*;

/***********************************************************************************************************************
 *
 * A factory class to create testing matchers.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@NoArgsConstructor(access = PRIVATE) @Slf4j
public final class UserNotificationWithFeedbackTestHelper
  {
    /*******************************************************************************************************************
     *
     * Creates a {@link UserNotification} matcher for the given caption and text.
     *
     * @param   captionRegex    the regular expression that should match the caption
     * @param   textRegex       the regular expression that should match the text
     * @return                  the matcher
     *
     ******************************************************************************************************************/
    @Nonnull
    public static UserNotificationMatcher notification (final @Nonnull String captionRegex,
                                                        final @Nonnull String textRegex)
      {
        return new UserNotificationMatcher(captionRegex, textRegex);
      }

    /*******************************************************************************************************************
     *
     * Creates a {@link UserNotificationWithFeedback} matcher for the given caption and text.
     *
     * @param   captionRegex    the regular expression that should match the caption
     * @param   textRegex       the regular expression that should match the text
     * @return                  the matcher
     *
     ******************************************************************************************************************/
    @Nonnull
    public static UserNotificationWithFeedbackMatcher notificationWithFeedback (final @Nonnull String captionRegex,
                                                                                final @Nonnull String textRegex)
      {
        return new UserNotificationWithFeedbackMatcher(captionRegex, textRegex);
      }

    /*******************************************************************************************************************
     *
     * An {@link Answer} that triggers a confirmation to a {@link UserNotificationWithFeedback}.
     *
     * @return  the {@code Answer}
     *
     ******************************************************************************************************************/
    public static final Answer<Void> confirm()
      {
        return CONFIRM;
      }

    /*******************************************************************************************************************
     *
     * An {@link Answer} that triggers a cancellation to a {@link UserNotificationWithFeedback}.
     *
     * @return  the {@code Answer}
     *
     ******************************************************************************************************************/
    public static final Answer<Void> cancel()
      {
        return CANCEL;
      }

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    private static final Answer<Void> CONFIRM = new Answer<Void>()
      {
        @Override
        public Void answer (final @Nonnull InvocationOnMock invocation)
          throws Exception
          {
            EventQueue.invokeAndWait(new Runnable() // FIXME: use Platform.runLater() and wait
              {
                @Override
                public void run()
                  {
                    try
                      {
                        final UserNotificationWithFeedback notification =
                                (UserNotificationWithFeedback)invocation.getArguments()[0];
                        log.info(">>>> mock UI confirming {}...", notification);
                        notification.confirm();
                      }
                    catch (Exception e)
                      {
                        log.debug("", e);
                      }
                  }
              });
            return null;
          }
      };

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    private static final Answer<Void> CANCEL = new Answer<Void>()
      {
        @Override
        public Void answer (final @Nonnull InvocationOnMock invocation)
          throws Exception
          {
            EventQueue.invokeAndWait(new Runnable() // FIXME: use Platform.runLater() and wait
              {
                @Override
                public void run()
                  {
                    try
                      {
                        final UserNotificationWithFeedback notification =
                                (UserNotificationWithFeedback)invocation.getArguments()[0];
                        log.info(">>>> mock UI cancelling {}...", notification);
                        notification.cancel();
                      }
                    catch (Exception e)
                      {
                        log.debug("", e);
                      }
                  }
              });
            return null;
          }
      };
  }
