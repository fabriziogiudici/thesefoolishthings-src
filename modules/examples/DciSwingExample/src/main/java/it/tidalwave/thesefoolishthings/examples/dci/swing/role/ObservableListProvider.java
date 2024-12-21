/*
 * *************************************************************************************************************************************************************
 *
 * TheseFoolishThings: Miscellaneous utilities
 * http://tidalwave.it/projects/thesefoolishthings
 *
 * Copyright (C) 2009 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.thesefoolishthings.examples.dci.swing.role;

import javax.annotation.Nonnull;
import org.jdesktop.observablecollections.ObservableList;

/***************************************************************************************************************************************************************
 *
 * A role which creates an {@link ObservableList} bound to the datum.
 *
 * @stereotype Role
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public interface ObservableListProvider
  {
    public static final Class<ObservableListProvider> _ObservableListProvider_ = ObservableListProvider.class;

    /***********************************************************************************************************************************************************
     * Creates a new {@link ObservableList} for the associated datum.
     *
     * @return  the {@code ObservableList}
     **********************************************************************************************************************************************************/
    @Nonnull
    public <T> ObservableList<T> createObservableList();
  }
