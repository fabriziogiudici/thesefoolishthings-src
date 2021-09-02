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
package it.tidalwave.thesefoolishthings.examples.extendedfinderexample;

import it.tidalwave.thesefoolishthings.examples.person.Person;
import it.tidalwave.util.spi.FinderSupport;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/***********************************************************************************************************************
 *
 * A variant of {@link PersonRegistryImpl2a} that uses an internal status class.
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class PersonFinderImpl2b extends FinderSupport<Person, PersonFinder> implements PersonFinder
  {
    // This could be generated by Lombok's @RequiredArgsConstructor
    // START SNIPPET: public-constructor-and-fields
    static class Status
      {
        Status (@Nonnull final List<Person> persons,
                @Nonnull final String firstNameRegex,
                @Nonnull final String lastNameRegex)
          {
            this.persons = persons;
            this.firstNameRegex = firstNameRegex;
            this.lastNameRegex = lastNameRegex;
          }

        @Nonnull
        final List<Person> persons;

        @Nonnull
        final String firstNameRegex;

        @Nonnull
        final String lastNameRegex;
      }

    @Nonnull
    private final Status status;

    // This is for public use
    public PersonFinderImpl2b (@Nonnull final List<Person> persons)
      {
        this(new Status(persons, ".*",  ".*"));
      }
    // END SNIPPET: public-constructor-and-fields

    // This is for internal purposes
    // This could be generated by Lombok's @RequiredArgsConstructor
    // START SNIPPET: private-constructor
    private PersonFinderImpl2b (@Nonnull final Status status)
      {
        this.status = status;
      }
    // END SNIPPET: private-constructor

    // START SNIPPET: clone-constructor
    public PersonFinderImpl2b (@Nonnull final PersonFinderImpl2b other, @Nonnull final Object override)
      {
        super(other, override);
        final Status source = getSource(Status.class, other.status, override);
        this.status = new Status(source.persons, source.firstNameRegex, source.lastNameRegex);
      }
    // END SNIPPET: clone-constructor

    // START SNIPPET: new-methods
    @Override @Nonnull
    public PersonFinder withFirstName (@Nonnull final String regex)
      {
        return clonedWith(new Status(status.persons, regex, status.lastNameRegex));
      }

    @Override @Nonnull
    public PersonFinder withLastName (@Nonnull final String regex)
      {
        return clonedWith(new Status(status.persons, status.firstNameRegex, regex));
      }
    // END SNIPPET: new-methods

    // START SNIPPET: computeResults
    @Override @Nonnull
    protected List<? extends Person> computeResults()
      {
        final Pattern firstNamePattern = Pattern.compile(status.firstNameRegex);
        final Pattern lastNamePattern = Pattern.compile(status.lastNameRegex);

        return status.persons.stream()
                             .filter(p -> firstNamePattern.matcher(p.getFirstName()).matches()
                                       && lastNamePattern.matcher(p.getLastName()).matches())
                             .collect(Collectors.toList());
      }
    // END SNIPPET: computeResults
  }
