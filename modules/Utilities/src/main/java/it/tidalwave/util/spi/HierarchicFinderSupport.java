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
package it.tidalwave.util.spi;

import java.lang.reflect.Constructor;
// import javax.annotation.Nonnegative;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import it.tidalwave.util.Finder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.util.CollectionUtils.concat;

/***************************************************************************************************************************************************************
 *
 * A support class for implementing a {@link Finder}. Subclasses only need to implement the {@link #computeResults()}
 * method where <i>raw</i> results are retrieved (with raw we mean that they shouldn't be filtered or sorted, as
 * post-processing will be performed by this class) and a clone constructor.
 *
 * If you don't need to extend the {@link Finder} with extra methods, please use the simplified
 * {@link SimpleFinderSupport}.
 *
 * @author Fabrizio Giudici
 * @it.tidalwave.javadoc.draft
 *
 **************************************************************************************************************************************************************/
@Slf4j @AllArgsConstructor(access = AccessLevel.PRIVATE) @ToString
public class HierarchicFinderSupport<T, F extends Finder<T>> implements Finder<T>
  {
    private static final long serialVersionUID = 2467809593956684L;

    @RequiredArgsConstructor
    static class Sorter<U>
      {
        @Nonnull
        private final InMemorySortCriterion<U> sortCriterion;

        @Nonnull
        private final SortDirection sortDirection;

        public void sort (@Nonnull final List<? extends U> results)
          {
            sortCriterion.sort(results, sortDirection);
          }
      }

    private static final String MESSAGE =
          "Since version 2.0, Implementations of Finder must have a clone constructor such as "
        + "MyFinder(MyFinder other, Object override). This means that they can't be implemented by anonymous or inner, "
        + "non static classes. See the javadoc for further information. Could not find constructor: ";

    @Nonnull
    private final String name;

    /* @Nonnegative */
    protected final int firstResult;

    /* @Nonnegative */
    protected final int maxResults;

    @Nonnull @Getter(AccessLevel.PROTECTED)
    private final List<Object> contexts;

    @Nonnull
    private final List<Sorter<T>> sorters;

    private static final int DEFAULT_MAX_RESULTS = Integer.MAX_VALUE;

    /***********************************************************************************************************************************************************
     * Creates an instance with the given name (that will be used for diagnostics).
     *
     * @param  name   the name
     **********************************************************************************************************************************************************/
    protected HierarchicFinderSupport (@Nonnull final String name)
      {
        this.name = name;
        this.firstResult = 0;
        this.maxResults = DEFAULT_MAX_RESULTS;
        this.sorters = new ArrayList<>();
        this.contexts = Collections.emptyList();
        checkSubClass();
      }

    /***********************************************************************************************************************************************************
     * Default constructor.
     **********************************************************************************************************************************************************/
    protected HierarchicFinderSupport()
      {
        this.name = getClass().getName();
        this.firstResult = 0;
        this.maxResults = DEFAULT_MAX_RESULTS;
        this.sorters = new ArrayList<>();
        this.contexts = Collections.emptyList();
        checkSubClass();
      }

    /***********************************************************************************************************************************************************
     * Clone constructor for subclasses.
     *
     * @param   other     the other instance to clone
     * @param   holder    the holder object
     **********************************************************************************************************************************************************/
    protected HierarchicFinderSupport (@Nonnull final HierarchicFinderSupport<T, F> other, @Nonnull final Object holder)
      {
        log.trace("HierarchicFinderSupport({}, {})", other, holder);
        final var source = getSource(HierarchicFinderSupport.class, other, holder);
        this.name = source.name;
        this.firstResult = source.firstResult;
        this.maxResults = source.maxResults;
        this.sorters = source.sorters;
        this.contexts = source.contexts; // it's always unmodifiable
      }

    /***********************************************************************************************************************************************************
     * This method throws an exception since a {@code Finder} extending this class must be cloned with
     * {@link #clonedWith(Object)}.
     * 
     * @see #clonedWith(Object)
     * @deprecated
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public final HierarchicFinderSupport<T, F> clone()
      {
        throw new UnsupportedOperationException("\"HierarchicFinderSupport.clone() no more supported");
      }

    /***********************************************************************************************************************************************************
     * Create a clone of this object calling the special copy constructor by reflection.
     *
     * @param   override  the override object
     * @return            the clone
     **********************************************************************************************************************************************************/
    @Nonnull
    protected F clonedWith (@Nonnull final Object override)
      {
        try
          {
            final var constructor = getCloneConstructor();
            constructor.setAccessible(true);
            return (F)constructor.newInstance(this, override);
          }
        catch (Exception e)
          {
            throw new RuntimeException(e);
          }
      }

    /***********************************************************************************************************************************************************
     * Create a clone of this object calling the special clone constructor by reflection.
     *
     * @param   override  the override object
     * @return            the clone
     * @deprecated        Use {@link #clonedWith(Object)} instead.
     **********************************************************************************************************************************************************/
    @Nonnull @Deprecated
    protected F clone (@Nonnull final Object override)
      {
        return clonedWith(override);
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public F from (/* @Nonnegative */ final int firstResult)
      {
        return clonedWith(new HierarchicFinderSupport<T, F>(name, firstResult, maxResults, contexts, sorters));
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public F max (/* @Nonnegative */ final int maxResults)
      {
        return clonedWith(new HierarchicFinderSupport<T, F>(name, firstResult, maxResults, contexts, sorters));
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public F withContext (@Nonnull final Object context)
      {
        final var contexts = concat(this.contexts, context);
        return clonedWith(new HierarchicFinderSupport<T, F>(name, firstResult, maxResults, contexts, sorters));
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public <U> Finder<U> ofType (@Nonnull final Class<U> type)
      {
        throw new UnsupportedOperationException("Must be eventually implemented by subclasses.");
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public F sort (@Nonnull final SortCriterion criterion, @Nonnull final SortDirection direction)
      {
        if (criterion instanceof Finder.InMemorySortCriterion)
          {
            @SuppressWarnings("unchecked")
            final var sorters = concat(this.sorters, new Sorter<>((InMemorySortCriterion<T>)criterion, direction));
            return clonedWith(new HierarchicFinderSupport<T, F>(name, firstResult, maxResults, contexts, sorters));
          }

        final var template = "%s does not implement %s - you need to subclass Finder and override sort()";
        final var message = String.format(template, criterion, InMemorySortCriterion.class);
        throw new UnsupportedOperationException(message);
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public final F sort (@Nonnull final SortCriterion criterion)
      {
        return sort(criterion, SortDirection.ASCENDING);
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public List<T> results()
      {
        return computeNeededResults();
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override /* @Nonnegative */
    public int count()
      {
        return computeNeededResults().size();
      }

    /***********************************************************************************************************************************************************
     * Subclasses can implement this method where *all* the raw results must be actually retrieved.
     *
     * @return  the unprocessed results
     **********************************************************************************************************************************************************/
    // START SNIPPET: computeResults
    @Nonnull
    protected List<T> computeResults()
    // END SNIPPET: computeResults
      {
        throw new UnsupportedOperationException("You must implement me!");
      }

    /***********************************************************************************************************************************************************
     * Subclasses can implement this method where *only the requested* raw results must be retrieved.
     *
     * @return  the unprocessed results
     **********************************************************************************************************************************************************/
    // START SNIPPET: computeNeededResults
    @Nonnull
    protected List<T> computeNeededResults()
    // END SNIPPET: computeNeededResults
      {
        log.trace("computeNeededResults() - {}", this);
        var results = computeResults();

        // First sort and then extract the sublist
        for (final var sorter : sorters)
          {
            log.trace(">>>> sorting with {}...", sorter);
            sorter.sort(results);
          }

        final var toIndex = (int)Math.min(results.size(), (long)firstResult + (long)maxResults);

        if (firstResult > toIndex)
          {
            return new CopyOnWriteArrayList<>();
          }

        results = results.subList(firstResult, toIndex);

        return results;
      }

    /***********************************************************************************************************************************************************
     * A utility method used by the copy constructor (see general documentation). If the override object is strictly
     * of the specified type, it is returned; otherwise the other object is returned.
     *
     * @param <U>       the static type of the source
     * @param type      the dynamic type of the source
     * @param other     the other finder
     * @param override  the holder object
     * @return          the override or other
     **********************************************************************************************************************************************************/
    @Nonnull
    protected static <U> U getSource (@Nonnull final Class<? extends U> type,
                                      @Nonnull final U other,
                                      @Nonnull final Object override)
      {
        return override.getClass().equals(type) ? type.cast(override) : other;
      }

    /***********************************************************************************************************************************************************
     **********************************************************************************************************************************************************/
    @Nonnull @SuppressWarnings("unchecked")
    private Constructor<HierarchicFinderSupport<T, F>> getCloneConstructor()
      throws SecurityException, NoSuchMethodException
      {
        return (Constructor<HierarchicFinderSupport<T, F>>)getClass().getConstructor(getClass(), Object.class);
      }

    /***********************************************************************************************************************************************************
     **********************************************************************************************************************************************************/
    private void checkSubClass ()
      {
        try
          {
            getCloneConstructor();
          }
        catch (SecurityException | NoSuchMethodException e)
          {
            throw new ExceptionInInitializerError(MESSAGE + e.getMessage());
          }
      }
  }
