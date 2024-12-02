/*
 * *********************************************************************************************************************
 *
 * TheseFoolishThings: Miscellaneous utilities
 * http://tidalwave.it/projects/thesefoolishthings
 *
 * Copyright (C) 2009 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
 *
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
 * git clone https://bitbucket.org/tidalwave/thesefoolishthings-src
 * git clone https://github.com/tidalwave-it/thesefoolishthings-src
 *
 * *********************************************************************************************************************
 */
package it.tidalwave.actor.impl;

import javax.annotation.Nonnull;
import javax.inject.Provider;

/***********************************************************************************************************************
 *
 * A trimmed down replacement for OpenBlueSky Locator, in order to avoid depending on OpenBlueSky.
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class Locator
  {
    public static class NotFoundException extends RuntimeException
      {
        NotFoundException (@Nonnull final Class<?> serviceClass)
          {
            super("Not found: " + serviceClass);
          }

        NotFoundException (@Nonnull final String name)
          {
            super("Not found: " + name);
          }
      }

    @Nonnull
    public static <T> Provider<T> createProviderFor (@Nonnull final Class<? extends T> serviceClass)
      {
        return new Provider<>()
          {
            @Override @Nonnull
            public T get ()
              {
                return find(serviceClass);
              }
          };
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    private static <T> T find (@Nonnull final Class<T> serviceClass)
      {
//        final T service = Lookup.getDefault().lookup(serviceClass); FIXME
//
//        if (service == null)
//          {
            throw new NotFoundException(serviceClass);
//          }
//
//        return service;
      }
  }
