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
package it.tidalwave.role.ui;

import javax.annotation.Nonnull;
import it.tidalwave.role.ui.impl.DefaultPresentable;

/***********************************************************************************************************************
 *
 * The role of an object that can be presented on a UI, thus is capable of creating a {@link PresentationModel}.
 *
 * @stereotype Role
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@FunctionalInterface
public interface Presentable
  {
    public final static Class<Presentable> _Presentable_ = Presentable.class;

    /*******************************************************************************************************************
     *
     * Creates a {@link PresentationModel} with some optional roles.
     *
     * @param  instanceRoles    the optional roles
     * @return                  the {@code PresentationModel}
     *
     ******************************************************************************************************************/
    @Nonnull
    public PresentationModel createPresentationModel (@Nonnull Object... instanceRoles);

    /*******************************************************************************************************************
     *
     * @since         3.2-ALPHA-2
     *
     ******************************************************************************************************************/
    @Nonnull
    public static Presentable of (final @Nonnull Object owner)
      {
        return new DefaultPresentable(owner);
      }
  }
