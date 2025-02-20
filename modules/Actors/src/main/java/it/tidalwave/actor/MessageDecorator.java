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
package it.tidalwave.actor;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/***************************************************************************************************************************************************************
 *
 * This role should be injected into a message to decorated it at the moment of sending. This technique is experimental
 * and represents the function of a Decorator Pattern in a message-oriented design.
 *
 * @stereotype Role
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public interface MessageDecorator
  {
    public static final Class<MessageDecorator> _MessageDecorator_ = MessageDecorator.class;

    /***********************************************************************************************************************************************************
     * A default implementation of {@code MessageDecorator} which returns the same message.
     **********************************************************************************************************************************************************/
    @Getter @RequiredArgsConstructor @ToString
    public static class Same<T extends MessageSupport> implements MessageDecorator
      {
        @Nonnull
        private final T decoratedMessage;
      }

    /***********************************************************************************************************************************************************
     * Returns the decorated message.
     *
     * @return  the decorated message
     **********************************************************************************************************************************************************/
    @Nonnull
    public MessageSupport getDecoratedMessage();
  }
