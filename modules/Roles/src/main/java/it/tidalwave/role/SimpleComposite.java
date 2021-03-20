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
 * 
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.role;

import javax.annotation.Nonnull;
import java.util.Collection;
import it.tidalwave.util.Finder;
import it.tidalwave.role.impl.DefaultSimpleComposite;

/***********************************************************************************************************************
 *
 * A simple {@link Composite} that uses the default {@link Finder}.
 *
 * @stereotype Role
 *
 * @author  Fabrizio Giudici
 * @it.tidalwave.javadoc.stable
 *
 **********************************************************************************************************************/
@FunctionalInterface
public interface SimpleComposite<TYPE> extends Composite<TYPE, Finder<? extends TYPE>>
  {
    public static final Class<SimpleComposite> _SimpleComposite_ = SimpleComposite.class;

    /*******************************************************************************************************************
     *
     * Returns a wrapped {@code SimpleComposite} on a given {@link Finder}
     *
     * @param   <TYPE>  the type of the {@code Finder}
     * @param   finder  the {@code Finder}
     * @return          the wrapped {@code SimpleComposite}
     * @since 3.2-ALPHA-1
     *
     ******************************************************************************************************************/
    @Nonnull
    public static <TYPE> SimpleComposite<TYPE> of (final Finder<TYPE> finder)
      {
        return new DefaultSimpleComposite<>(finder);
      }

    /*******************************************************************************************************************
     *
     * Returns a wrapped {@code SimpleComposite} on a given collection of elements. The collection is cloned and will be
     * immutable
     *
     * @param   <TYPE>  the type of the {@code Finder}
     * @param   items   the objects to wrap
     * @return          the wrapped {@code SimpleComposite}
     * @since 3.2-ALPHA-1
     *
     ******************************************************************************************************************/
    @Nonnull
    public static <TYPE> SimpleComposite<TYPE> ofCloned (@Nonnull final Collection<TYPE> items)
      {
        return of(Finder.ofCloned(items));
      }
  }
