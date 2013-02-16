/*
 * #%L
 * *********************************************************************************************************************
 * 
 * TheseFoolishThings - Roles (Spring)
 * %%
 * Copyright (C) 2009 - 2013 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.role;

import javax.annotation.Nonnull;
import java.util.List;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.util.Task;

/***********************************************************************************************************************
 *
 * A facility to register and unregister global and local DCI contexts.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface ContextManager
  {
    /*******************************************************************************************************************
     *
     * Returns the list of current contexts, ordered by their priority.
     *
     * @return  the list of current contexts
     *
     ******************************************************************************************************************/
    @Nonnull
    public List<Object> getContexts();

    /*******************************************************************************************************************
     *
     * Finds a context of the given type.
     *
     * @param   contextClass       the context type
     * @return                     the requested context
     * @throws  NotFoundException  if no context of that type is found
     *
     ******************************************************************************************************************/
    @Nonnull
    public <T> T findContext (@Nonnull Class<T> contextClass)
      throws NotFoundException;

    /*******************************************************************************************************************
     *
     * Adds a global context.
     *
     * @param  context             the new context
     *
     ******************************************************************************************************************/
    public void addGlobalContext (@Nonnull Object context);

    /*******************************************************************************************************************
     *
     * Adds a local context.
     *
     * @param  context             the new context
     *
     ******************************************************************************************************************/
    public void addLocalContext (@Nonnull Object context);

    /*******************************************************************************************************************
     *
     * Removes a local context.
     *
     * @param  context             the context
     *
     ******************************************************************************************************************/
    public void removeLocalContext (@Nonnull Object context);

    /*******************************************************************************************************************
     *
     * Runs a {@link Task} associated with a new local context.
     *
     * @param  context             the context
     * @param  task                the task
     *
     ******************************************************************************************************************/
    public <V, T extends Throwable> V runWithContext (@Nonnull Object context,
                                                      @Nonnull Task<V, T> task)
      throws T;
  }
