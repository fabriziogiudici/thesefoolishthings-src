/*
 * #%L
 * *********************************************************************************************************************
 * 
 * These Foolish Things - Miscellaneous utilities
 * http://thesefoolishthings.tidalwave.it - git clone git@bitbucket.org:tidalwave/thesefoolishthings-src.git
 * %%
 * Copyright (C) 2009 - 2018 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.role.spi;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import it.tidalwave.role.TextWritable;

/***********************************************************************************************************************
 *
 * An implementation of {@link TextWritable} which delegates to a {@link File}.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 * @it.tidalwave.javadoc.stable
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class FileTextWritable implements TextWritable
  {
    @Nonnull
    private final File file;

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Writer openWriter()
      throws IOException
      {
        return new FileWriter(file);
      }
  }
