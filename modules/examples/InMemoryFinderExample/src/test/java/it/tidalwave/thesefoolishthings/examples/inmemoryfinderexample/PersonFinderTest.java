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
package it.tidalwave.thesefoolishthings.examples.inmemoryfinderexample;

import it.tidalwave.util.Finder;
import it.tidalwave.thesefoolishthings.examples.person.Person;
import it.tidalwave.thesefoolishthings.examples.person.PersonRegistry;
import it.tidalwave.thesefoolishthings.examples.person.PersonRegistryHelper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static it.tidalwave.thesefoolishthings.examples.inmemoryfinderexample.PersonSortCriteria.*;
import static it.tidalwave.util.Finder.SortDirection.DESCENDING;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public class PersonFinderTest
  {
    private Finder<Person> underTest;

    @BeforeMethod
    public void setup()
      {
        final PersonRegistry registry = new InMemoryPersonRegistry();
        PersonRegistryHelper.populate(registry);
        underTest = registry.findPerson();
      }

    @Test
    public void testAllPersons()
      {
        assertThat(underTest.results().toString(),
                   is("[Michelangelo Buonarroti, Lorenzo Bernini, Leonardo da Vinci, Pietro Perugino, " +
                      "Paolo Uccello, Andrea Mantegna, Ambrogio Lorenzetti, Piero della Francesca, Giotto da Bondone]"));
      }

    @Test
    public void testAllPersonsSortedByFirstName()
      {
        assertThat(underTest.sort(BY_FIRST_NAME).results().toString(),
                   is("[Ambrogio Lorenzetti, Andrea Mantegna, Giotto da Bondone, Leonardo da Vinci, " +
                      "Lorenzo Bernini, Michelangelo Buonarroti, Paolo Uccello, Piero della Francesca, Pietro Perugino]"));
      }

    @Test
    public void testAllPersonsSortedByLastNameDescending()
      {
        assertThat(underTest.sort(BY_LAST_NAME, DESCENDING).results().toString(),
                   is("[Piero della Francesca, Leonardo da Vinci, Giotto da Bondone, Paolo Uccello, Pietro Perugino," +
                      " Andrea Mantegna, Ambrogio Lorenzetti, Michelangelo Buonarroti, Lorenzo Bernini]"));
      }

    @Test
    public void testPersonRange()
      {
        assertThat(underTest.from(3).max(2).results().toString(),
                   is("[Pietro Perugino, Paolo Uccello]"));
      }
  }
