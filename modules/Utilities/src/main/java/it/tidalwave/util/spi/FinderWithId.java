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
package it.tidalwave.util.spi;

import jakarta.annotation.Nonnull;
import it.tidalwave.util.Finder;
import it.tidalwave.util.Id;

/***************************************************************************************************************************************************************
 *
 * A {@link Finder} that provides filtering by id.
 *
 * @param <T>     the product abstract type
 * @param <F>     the {@code Finder} type
 * @since         3.2-ALPHA-15
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public interface FinderWithId<T, F extends ExtendedFinderSupport<T, F>> extends Finder<T>, ExtendedFinderSupport<T, F>
  {
    @Nonnull
    public F withId (@Nonnull Id id);
  }
