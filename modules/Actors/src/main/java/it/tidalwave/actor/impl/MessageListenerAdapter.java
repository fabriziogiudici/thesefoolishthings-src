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
package it.tidalwave.actor.impl;

import java.lang.reflect.Method;
import jakarta.annotation.Nonnull;
import it.tidalwave.actor.annotation.Message;
import it.tidalwave.actor.spi.ActorActivatorStats;
import it.tidalwave.messagebus.MessageBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@RequiredArgsConstructor @Slf4j
class MessageListenerAdapter<T> implements MessageBus.Listener<T>
  {
    @Nonnull
    private final Object owner;

    @Nonnull
    private final Method method;

    @Nonnull
    private final ExecutorWithPriority executor;

    @Nonnull
    private final ActorActivatorStats stats;

    @Override
    public void notify (@Nonnull final T message)
      {
        log.trace("notify({})", message);
        final var collaboration = DefaultCollaboration.getCollaboration(message);
        collaboration.registerPendingMessage(message);
        stats.changePendingMessageCount(+1);

        final Runnable messageWorker = () ->
          {
            collaboration.bindToCurrentThread();
            collaboration.unregisterPendingMessage(message);
            stats.changePendingMessageCount(-1);

            try
              {
                stats.incrementInvocationCount();
                method.invoke(owner, message);
              }
            catch (Throwable t)
              {
                stats.incrementInvocationErrorCount();
                log.error("Error calling {} with {}", method, message.getClass());
                log.error("", t);
              }
            finally
              {
                stats.incrementSuccessfulInvocationCount();
                collaboration.unbindFromCurrentThread();
              }
          };

        if (message.getClass().getAnnotation(Message.class).outOfBand())
          {
            executor.executeWithPriority(messageWorker);
          }
        else
          {
            executor.execute(messageWorker);
          }
      }
  }


