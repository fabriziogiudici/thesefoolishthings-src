/*
 * #%L
 * *********************************************************************************************************************
 * 
 * These Foolish Things - Miscellaneous utilities
 * http://thesefoolishthings.java.net - hg clone https://bitbucket.org/tidalwave/thesefoolishthings-src
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
package it.tidalwave.messagebus.spi;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.MessageBus.Listener;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * A partial implementation of {@link MessageBus}.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@ThreadSafe @Slf4j
public abstract class MessageBusSupport implements MessageBus
  {
    private final Map<Class<?>, List<WeakReference<Listener<?>>>> listenersMapByTopic = new HashMap<>();

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    public <Topic> void publish (final @Nonnull Topic message)
      {
        publish((Class<Topic>)message.getClass(), message);
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    public <Topic> void publish (final @Nonnull Class<Topic> topic, final @Nonnull Topic message)
      {
        log.trace("publish({}, {})", topic, message);
        getExecutor().execute(new Runnable()
          {
            @Override
            public void run()
              {
                deliverMessage(topic, message);
              }
        });
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    public <Topic> void subscribe (final @Nonnull Class<Topic> topic, final @Nonnull Listener<Topic> listener)
      {
        log.debug("subscribe({}, {})", topic, listener);
        findListenersByTopic(topic).add(new WeakReference<>(listener));
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    public void unsubscribe (final @Nonnull Listener<?> listener)
      {
        log.debug("unsubscribe({})", listener);

        for (final List<WeakReference<Listener<?>>> list : listenersMapByTopic.values())
          {
            for (final Iterator<WeakReference<Listener<?>>> i = list.iterator(); i.hasNext(); )
              {
                final WeakReference<?> ref = i.next();

                if ((ref.get() == null) || (ref.get() == listener))
                  {
                    i.remove();
                  }
              }
          }
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    protected <Topic> void deliverMessage (final @Nonnull Class<Topic> topic, final @Nonnull Topic message)
      {
        final HashSet<Entry<Class<?>, List<WeakReference<Listener<?>>>>> clone =
                new HashSet<>(listenersMapByTopic.entrySet());

        for (final Entry<Class<?>, List<WeakReference<Listener<?>>>> e : clone)
          {
            if (e.getKey().isAssignableFrom(topic))
              {
                final List<WeakReference<Listener<Topic>>> listeners = (List)e.getValue();

                for (final WeakReference<Listener<Topic>> listenerReference : listeners)
                  {
                    final Listener<Topic> listener = listenerReference.get();

                    if (listener != null)
                      {
                        try
                          {
                            listener.notify(message);
                          }
                        catch (Throwable t)
                          {
                            log.warn("deliverMessage()", t);
                          }
                      }
                  }
              }
          }
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    protected abstract Executor getExecutor();

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    private <Topic> List<WeakReference<Listener<Topic>>> findListenersByTopic (final @Nonnull Class<Topic> topic)
      {
        final List tmp = listenersMapByTopic.get(topic);
        List<WeakReference<Listener<Topic>>> listeners = tmp;

        if (listeners == null)
          {
            listeners = new ArrayList<>();
            listenersMapByTopic.put(topic, (List)listeners);
          }

        return listeners;
      }
  }
