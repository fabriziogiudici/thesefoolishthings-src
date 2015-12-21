/*
 * #%L
 * *********************************************************************************************************************
 * 
 * These Foolish Things - Miscellaneous utilities
 * http://thesefoolishthings.tidalwave.it - git clone git@bitbucket.org:tidalwave/thesefoolishthings-src.git
 * %%
 * Copyright (C) 2009 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.actor.netbeans.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.openide.util.lookup.ServiceProvider;
import it.tidalwave.messagebus.impl.netbeans.NetBeansPlatformMessageBus;
import it.tidalwave.actor.spi.CollaborationAwareMessageBus;
import it.tidalwave.actor.impl.DefaultCollaboration;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * An implementation of {@link EventBus}.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@ServiceProvider(service = CollaborationAwareMessageBus.class)
@ThreadSafe @Slf4j
public class NetBeansPlatformCollaborationAwareMessageBus extends NetBeansPlatformMessageBus
                                                          implements CollaborationAwareMessageBus
  {
    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Override
    protected <Topic> void dispatchMessage (final @Nonnull Class<Topic> topic, final @Nonnull Topic message)
      {
        super.dispatchMessage(topic, message);
        DefaultCollaboration.getCollaboration(message).unregisterDeliveringMessage(message);
      }
  }
