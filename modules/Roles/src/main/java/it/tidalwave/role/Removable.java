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
package it.tidalwave.role;

/***************************************************************************************************************************************************************
 *
 * The role of an object that can be removed (e.g. deleted).
 *
 * @stereotype Role
 *
 * @author  Fabrizio Giudici
 * @it.tidalwave.javadoc.stable
 *
 **************************************************************************************************************************************************************/
@FunctionalInterface
public interface Removable
  {
    public static final Class<Removable> _Removable_ = Removable.class;

    /***********************************************************************************************************************************************************
     * A default {@code Removable} which does nothing (useful for implementing the NullObject pattern).
     **********************************************************************************************************************************************************/
    public static final Removable DEFAULT = () -> {};

    /***********************************************************************************************************************************************************
     * Removes this object from some implicit, or context-provided container (e.g. a persistence facility, etc...).
     * @throws  Exception   if the operation fails
     **********************************************************************************************************************************************************/
    @SuppressWarnings("RedundantThrows")
    public void remove()
      throws Exception;
  }
