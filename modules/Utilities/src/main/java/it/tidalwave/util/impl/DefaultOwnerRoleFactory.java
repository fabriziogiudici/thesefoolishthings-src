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
package it.tidalwave.util.impl;

import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import it.tidalwave.util.As;
import it.tidalwave.util.RoleFactory;
import it.tidalwave.util.Task;
import it.tidalwave.util.spi.ContextSnapshot;
import it.tidalwave.role.spi.OwnerRoleFactory;
import it.tidalwave.role.spi.SystemRoleFactory;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.util.ShortNames.*;

/***************************************************************************************************************************************************************
 *
 * An implementation for {@link As} based on Spring.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Slf4j
class DefaultOwnerRoleFactory implements OwnerRoleFactory
  {
    @Nonnull
    private final Object owner;

    private final ContextSnapshot contextSnapshot;

    /***********************************************************************************************************************************************************
     * Constructor for use with composition.
     *
     * @param  owner  the owner object
     **********************************************************************************************************************************************************/
    public DefaultOwnerRoleFactory (@Nonnull final Object owner)
      {
        this.owner = owner;
        contextSnapshot = new ContextSnapshot(owner);
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull @SuppressWarnings("unchecked")
    public <T> Collection<T> findRoles (@Nonnull final Class<? extends T> roleType)
      {
        log.trace("as({}) for {}", shortName(roleType), shortId(owner));
        log.trace(">>>> contexts: {}", contextSnapshot);
        final var systemRoleFactory = SystemRoleFactory.getInstance();

        final List<T> roles = new ArrayList<>(contextSnapshot.runWithContexts(new Task<List<? extends T>, RuntimeException>()
          {
            @Override @Nonnull
            public List<? extends T> run ()
              {
                final List<T> systemRoles = systemRoleFactory.findRoles(owner, roleType);

                for (final var roleFactory : systemRoleFactory.findRoles(owner, RoleFactory.class))
                  {
                    if (roleType.isAssignableFrom(roleFactory.getRoleType()))
                      {
                        ((RoleFactory<Object, T>)roleFactory).createRoleFor(owner).ifPresent(systemRoles::add);
                      }
                  }

                return systemRoles;
              }
          }));

        if (roleType.isAssignableFrom(owner.getClass()))
          {
            roles.add(roleType.cast(owner));
          }

        log.trace(">>>> as() returning {}", shortIds(roles));

        return roles;
      }
  }
