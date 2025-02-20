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
package it.tidalwave.thesefoolishthings.examples.person;

import jakarta.annotation.Nonnull;
import java.util.function.Consumer;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.thesefoolishthings.examples.dci.swing.role.HtmlRenderable;
import it.tidalwave.thesefoolishthings.examples.dci.swing.role.StringRenderable;
import lombok.RequiredArgsConstructor;

/***************************************************************************************************************************************************************
 *
 * The implementation of the {@link HtmlRenderable} role for {@link Person}.
 *
 * @stereotype Role
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@DciRole(datumType = Person.class)
@RequiredArgsConstructor
public class PersonHtmlRenderable implements HtmlRenderable, StringRenderable
  {
    @Nonnull
    private final Person datum;

    @Override
    public void renderTo (@Nonnull final String pattern, @Nonnull final Consumer<String> renderingContext)
      {
        renderingContext.accept(String.format("<html>%s <b>%s</b></html>", datum.firstName, datum.lastName));
      }
  }
