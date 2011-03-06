/***********************************************************************************************************************
 *
 * These Foolish Things - Miscellaneous utilities
 * Copyright (C) 2009-2011 by Tidalwave s.a.s. (http://www.tidalwave.it)
 *
 ***********************************************************************************************************************
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
 ***********************************************************************************************************************
 *
 * WWW: http://thesefoolishthings.kenai.com
 * SCM: https://kenai.com/hg/thesefoolishthings~src
 *
 **********************************************************************************************************************/
package it.tidalwave.thesefoolishthings.examples.finderexample1;

import it.tidalwave.util.Finder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class PersonFinderTest 
  {
    private Finder<Person> finder;
    
    @Before
    public void setupFixture() 
      {
        final PersonRegistry1 registry = new DefaultPersonRegistry1();

        registry.add(new Person("Richard", "Nixon"));
        registry.add(new Person("Jimmy", "Carter"));
        registry.add(new Person("Ronald", "Reagan"));
        registry.add(new Person("George", "Bush"));
        registry.add(new Person("Bill", "Clinton"));
        registry.add(new Person("George Walker", "Bush"));
        registry.add(new Person("Barack", "Obama"));
        
        finder = registry.findPersons();
      }

    @Test
    public void testAllPersons()
      {
        assertThat(finder.results().toString(),
                   is("[Richard Nixon, Jimmy Carter, Ronald Reagan, George Bush, "
                    + "Bill Clinton, George Walker Bush, Barack Obama]"));
      }
    
    @Test
    public void testPersonRange()
      {
        assertThat(finder.from(3).max(2).results().toString(),
                   is("[George Bush, Bill Clinton]"));
      }
  }
