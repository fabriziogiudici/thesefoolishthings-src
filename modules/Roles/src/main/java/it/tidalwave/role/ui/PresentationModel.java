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
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import it.tidalwave.util.As;
import it.tidalwave.util.NamedCallback;
import it.tidalwave.role.ui.impl.DefaultPresentationModel;

/***********************************************************************************************************************
 *
 * TODO: As the NetBeans Node, it should allow children, have event listeners for children added/removed/changed.
 * This class so becomes the true M in MVC.
 *
 * @stereotype Role
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public interface PresentationModel extends As
  {
    public static Class<PresentationModel> PresentationModel = PresentationModel.class;

    public static final String PROPERTY_CHILDREN = "children";

    /** This is an undocumented feature. If you add a {@link NamedCallback} with this name as a role in this object, it
     * will be called back when {@link #dispose()} is called. */
    public static final String CALLBACK_DISPOSE = "dispose";

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public void dispose();

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public void addPropertyChangeListener (@Nonnull PropertyChangeListener listener);

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public void addPropertyChangeListener (@Nonnull String propertyName, @Nonnull PropertyChangeListener listener);

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public void removePropertyChangeListener (@Nonnull PropertyChangeListener listener);

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public void removePropertyChangeListener (@Nonnull String propertyName, @Nonnull PropertyChangeListener listener);

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public boolean hasListeners (@Nonnull String propertyName);

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public PropertyChangeListener[] getPropertyChangeListeners();

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public PropertyChangeListener[] getPropertyChangeListeners (@Nonnull String propertyName);

    /*******************************************************************************************************************
     *
     *
     * @since 3.2-ALPHA-1
     *
     ******************************************************************************************************************/
    public static PresentationModel of (final @Nonnull Object owner, final @Nonnull Object ... rolesOrFactories)
      {
        return new DefaultPresentationModel(owner, rolesOrFactories);
      }
  }
