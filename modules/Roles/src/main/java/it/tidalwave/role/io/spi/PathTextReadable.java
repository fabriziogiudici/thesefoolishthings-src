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
package it.tidalwave.role.io.spi;

import jakarta.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import it.tidalwave.role.io.TextReadable;

/***************************************************************************************************************************************************************
 *
 * An implementation of {@link TextReadable} which delegates to a {@link Path}.
 *
 * @author  Fabrizio Giudici
 * @since   3.2-ALPHA-12
 * @it.tidalwave.javadoc.stable
 *
 **************************************************************************************************************************************************************/
public class PathTextReadable implements TextReadable
  {
    @Nonnull
    private final Path path;

    @Nonnull
    private final Charset charset;

    /***********************************************************************************************************************************************************
     * Creates an instance with the given path and options.
     * @param   path          the path to open
     **********************************************************************************************************************************************************/
    public PathTextReadable (@Nonnull final Path path)
      {
        this(path, StandardCharsets.UTF_8);
      }

    /***********************************************************************************************************************************************************
     * Creates an instance with the given path and options.
     * @param   path          the path to open
     * @param   charset       the character set
     **********************************************************************************************************************************************************/
    public PathTextReadable (@Nonnull final Path path, @Nonnull final Charset charset)
      {
        this.path = path;
        this.charset = charset;
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Reader openReader()
      throws IOException
      {
        return Files.newBufferedReader(path, charset);
      }
  }
