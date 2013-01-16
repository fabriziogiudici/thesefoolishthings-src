/***********************************************************************************************************************
 *
 * These Foolish Things - Miscellaneous utilities
 * Copyright (C) 2009-2013 by Tidalwave s.a.s. (http://tidalwave.it)
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
 * WWW: http://thesefoolishthings.java.net
 * SCM: https://bitbucket.org/tidalwave/thesefoolishthings-src
 *
 **********************************************************************************************************************/
package it.tidalwave.thesefoolishthings.examples.dci.marshal.xstream;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import it.tidalwave.util.Id;
import it.tidalwave.role.AsExtensions;
import it.tidalwave.role.ContextManager;
import it.tidalwave.thesefoolishthings.examples.person.Person;
import it.tidalwave.thesefoolishthings.examples.person.DefaultPersonRegistry;
import it.tidalwave.thesefoolishthings.examples.person.ListOfPersons;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.ExtensionMethod;
import static it.tidalwave.role.Marshallable.Marshallable;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@ExtensionMethod(AsExtensions.class) @Slf4j
public class DciMarshalXStreamExample
  {
    @Inject @Nonnull
    private ContextManager contextManager;
    
    public void run()
      throws Exception
      {
        final Person joe = new Person(new Id("1"), "Joe", "Smith");
        final Person luke = new Person(new Id("2"), "Luke", "Skywalker");

        final XStreamContext xStreamContext = new XStreamContext();
        contextManager.addLocalContext(xStreamContext);

        final ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        joe.as(Marshallable).marshal(os1);
        log.info("******** (jos as Mashallable) marshalled: {}", new String(os1.toByteArray()));

        final ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        final ListOfPersons listOfPersons = new ListOfPersons(Arrays.asList(joe, luke));
        listOfPersons.as(Marshallable).marshal(os2);
        log.info("******** (listOfPersons as Mashallable) marshalled: {}", new String(os2.toByteArray()));

        final ByteArrayOutputStream os3 = new ByteArrayOutputStream();
        final DefaultPersonRegistry personRegistry = new DefaultPersonRegistry();
        personRegistry.addPerson(joe);
        personRegistry.addPerson(luke);
        personRegistry.as(Marshallable).marshal(os3);
        log.info("******** (personRegistry as Mashallable) marshalled: {}", new String(os2.toByteArray()));
        
        contextManager.removeLocalContext(xStreamContext);
      }
  }
