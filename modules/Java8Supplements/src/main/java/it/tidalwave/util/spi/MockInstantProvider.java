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
 * $Id$
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.util.spi;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import it.tidalwave.util.InstantProvider;

/***********************************************************************************************************************
 *
 * A mock implementation of {@link InstantProvider} which returns a fixed value that can be set (the zero epoch instant
 * by default).
 * 
 * @see     InstantProvider
 * @author  Fabrizio Giudici
 * @version $Id$
 * @since   1.39
 *
 **********************************************************************************************************************/
public class MockInstantProvider implements InstantProvider
  {
    @Getter @Setter
    private Instant instant = Instant.ofEpochMilli(0);
  }
