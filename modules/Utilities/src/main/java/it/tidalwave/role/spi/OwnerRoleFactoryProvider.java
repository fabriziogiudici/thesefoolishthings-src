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
package it.tidalwave.role.spi;

import jakarta.annotation.Nonnull;
import it.tidalwave.util.LazySupplier;
import static it.tidalwave.util.impl.ServiceLoaderLocator.lazySupplierOf;

/***************************************************************************************************************************************************************
 *
 * The provider of the singleton {@link OwnerRoleFactory}.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@FunctionalInterface
public interface OwnerRoleFactoryProvider
  {
    static class Inner // TODO: will go away with Java 17
      {
        private static final LazySupplier<OwnerRoleFactoryProvider> PROVIDER_REF =
                lazySupplierOf(OwnerRoleFactoryProvider.class);
      }

    /***********************************************************************************************************************************************************
     * Returns the singleton instance of {@link OwnerRoleFactoryProvider}
     *
     * @return    the singleton instance
     **********************************************************************************************************************************************************/
    @Nonnull
    public static OwnerRoleFactoryProvider getInstance ()
      {
        return Inner.PROVIDER_REF.get();
      }

    /***********************************************************************************************************************************************************
     * Creates an {@link OwnerRoleFactory} for the given object
     *
     * @param     owner   the object
     * @return            {@code AsDelegate}
     **********************************************************************************************************************************************************/
    @Nonnull
    public OwnerRoleFactory createRoleFactory (@Nonnull Object owner);
  }
