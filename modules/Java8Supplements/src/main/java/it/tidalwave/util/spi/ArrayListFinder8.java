/*
 * #%L
 * *********************************************************************************************************************
 *
 * SteelBlue
 * http://steelblue.tidalwave.it - git clone git@bitbucket.org:tidalwave/steelblue-src.git
 * %%
 * Copyright (C) 2015 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
 * %%
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
 * $Id$
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.util.spi;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import it.tidalwave.util.Finder8;
import static it.tidalwave.util.spi.FinderSupport.getSource;

/***********************************************************************************************************************
 *
 * An implementation of {@link Finder8} which holds an immutable list of items.
 *
 * @param  <T>   the type of contained items
 *
 * @since   3.1-ALPHA-2
 * @author  Fabrizio Giudici (Fabrizio.Giudici@tidalwave.it)
 * @version $Id: Class.java,v 631568052e17 2013/02/19 15:45:02 fabrizio $
 *
 **********************************************************************************************************************/
@Immutable
public class ArrayListFinder8<T> extends SimpleFinder8Support<T>
  {
    private static final long serialVersionUID = -3529114277448372453L;

    @Nonnull
    private final Collection<T> items;

    public ArrayListFinder8 (final @Nonnull Collection<T> items)
      {
        this.items = Collections.unmodifiableCollection(new ArrayList<>(items));
      }

    public ArrayListFinder8 (final @Nonnull ArrayListFinder8<T> other, @Nonnull Object override)
      {
        super(other, override);
        final ArrayListFinder8<T> source = getSource(ArrayListFinder8.class, other, override);
        this.items = source.items;
      }

    @Override @Nonnull
    protected List<? extends T> computeResults()
      {
        return new CopyOnWriteArrayList<>(items);
      }
  }
