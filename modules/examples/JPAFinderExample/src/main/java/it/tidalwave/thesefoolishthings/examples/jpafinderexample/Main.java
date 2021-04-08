/*
 * *********************************************************************************************************************
 *
 * TheseFoolishThings: Miscellaneous utilities
 * http://tidalwave.it/projects/thesefoolishthings
 *
 * Copyright (C) 2009 - 2021 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.thesefoolishthings.examples.jpafinderexample;

import javax.annotation.Nonnull;
import it.tidalwave.thesefoolishthings.examples.person.PersonRegistryHelper;
import it.tidalwave.thesefoolishthings.examples.jpafinderexample.impl.JpaPersonRegistry;
import it.tidalwave.thesefoolishthings.examples.jpafinderexample.impl.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.util.Finder.SortDirection.DESCENDING;
import static it.tidalwave.thesefoolishthings.examples.jpafinderexample.PersonRegistry3.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Slf4j
public class Main
  {
    public static void main (@Nonnull final String[] args)
            throws Exception
      {
        try (final TxManager txManager = new TxManagerImpl())
          {
            final PersonRegistry3 registry = new JpaPersonRegistry(txManager);
            PersonRegistryHelper.populate(registry);

            log.info("All: {}",
                     registry.findPerson()
                             .results());

            log.info("Count: {}",
                     registry.findPerson()
                             .count());

            log.info("Two persons from the 3rd position: {}",
                     registry.findPerson()
                             .from(3)
                             .max(2)
                             .results());

            log.info("All, sorted by first name: {}",
                     registry.findPerson()
                             .sort(BY_FIRST_NAME)
                             .results());

            log.info("All, sorted by last name, descending: {}",
                     registry.findPerson()
                             .sort(BY_LAST_NAME, DESCENDING)
                             .results());
          }
      }
  }
