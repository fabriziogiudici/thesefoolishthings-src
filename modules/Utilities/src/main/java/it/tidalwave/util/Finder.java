/***********************************************************************************************************************
 *
 * These Foolish Things - Miscellaneous utilities
 * Copyright (C) 2009-2011 by Tidalwave s.a.s.
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
package it.tidalwave.util;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;
import java.io.Serializable;

/***********************************************************************************************************************
 *
 * A factory for providing results of a search.
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 * @draft
 *
 **********************************************************************************************************************/
public interface Finder<T> extends Cloneable, Serializable
  {
    /*******************************************************************************************************************
     *
     * A tag interface to mark objects which are meaningful sort criteria that can be passed to 
     * {@link Finder#sort(it.tidalwave.util.Finder.SortCriterion)}. In general, a {@code SortCriterion} is just a 
     * behaviourless and methodless object, that should be specifically handled by concrete implementations of 
     * {@link Finder}. The only exceptions are {@link FilterSortCriterion} objects.
     *
     ******************************************************************************************************************/
    public static interface SortCriterion
      {
        //@bluebook-ignore-begin
        public static final Class<SortCriterion> SortCriterion = SortCriterion.class;
    
        /** A special {@link SortCriterion} which indicates that no sort has been performed. */
        public static final SortCriterion UNSORTED = new FilterSortCriterion() 
          {
            @Nonnull
            public Finder sort (final @Nonnull Finder finder, final @Nonnull SortDirection sortDirection) 
              {
                return finder;
              }
          };
        
        public static final SortCriterion DEFAULT = UNSORTED;
        //@bluebook-ignore-end
      }

    /*******************************************************************************************************************
     *
     * An interface that should be implemented by specific {@link SortCriterion} objects which are capable to implement
     * by themselves the sorting of objects, by post-processing an existing collection of objects. While this is often
     * convenient, it is possible for it to be inefficient in cases in which the original source of objects is capable
     * to perform the sort in an optimized way (e.g. a SQL database by means of {@code ORDER BY}}. The facility class
     * {@link FinderSupport} supports {@code FilterSortCriterion} objects out of the box. A convenient partial 
     * implementation of {@code FilterSortCriterion} is {@link DefaultFilterSortCriterion}.
     *
     ******************************************************************************************************************/
    public static interface FilterSortCriterion<T> extends SortCriterion
      {
        /***************************************************************************************************************
         *
         * Performs the sort of results.
         * 
         * @param  finder         the {@code Finder} that provides unsorted results
         * @param  sortDirection  the sort direction
         * @return                a {@code Finder} that will return sorted results
         *
         **************************************************************************************************************/
        @Nonnull
        public Finder<T> sort (@Nonnull Finder<T> finder, @Nonnull SortDirection sortDirection);
      }

    /*******************************************************************************************************************
     *
     * An enumeration to define the direction of a sort (ascending or descending).
     * 
     * @stable
     *
     ******************************************************************************************************************/
    public static enum SortDirection
      {
        ASCENDING(+1), DESCENDING(-1);
        //@bluebook-ignore-begin
        
        private final int intValue;
        
        private SortDirection (final int intValue)
          {
            this.intValue = intValue;
          }
        
        /** Returns +1 for ascending direction, -1 for descending */
        public int intValue()
          {
            return intValue;  
          }
        //@bluebook-ignore-end
      }

    /*******************************************************************************************************************
     *
     * Tells the {@code Finder} that only a subset of found items will be returned, starting from the given position.
     * 
     * @return            the {@code Finder}
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder<T> from (@Nonnegative int firstResult);

    /*******************************************************************************************************************
     *
     * Tells the {@code Finder} that only a maximum number of found items will be returned.
     * 
     * @return            the {@code Finder}
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder<T> max (@Nonnegative int maxResults);

    /*******************************************************************************************************************
     *
     * @return            the {@code Finder}
     *
     ******************************************************************************************************************/
    @Nonnull
    public <X> Finder<X> ofType (@Nonnull Class<X> type);

    /*******************************************************************************************************************
     *
     * Tells the {@code Finder} that results will be sorted according to the given criterion, in ascending direction.
     * 
     * @param  criterion  the sort criterion
     * @return            the {@code Finder}
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder<T> sort (@Nonnull SortCriterion criterion);

    /*******************************************************************************************************************
     *
     * Tells the {@code Finder} that results will be sorted according to the given criterion and direction.
     * 
     * @param  criterion  the sort criterion
     * @param  direction  the sort direction
     * @return            the {@code Finder}
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder<T> sort (@Nonnull SortCriterion criterion, @Nonnull SortDirection direction);

    /*******************************************************************************************************************
     *
     * Performs the search assuming that it will return a single item and returns it. This method fails if the search
     * returns more than one single item.
     * 
     * @return                    the found item
     * @throws NotFoundException  if the search didn't find anything
     * @throws RuntimeException   if the search returned more than one single item
     *
     ******************************************************************************************************************/
    @Nonnull
    public T result()
      throws NotFoundException;

    /*******************************************************************************************************************
     *
     * Performs the search and returns only the first found item.
     * 
     * @return                    the first found item
     * @throws NotFoundException  if the search didn't find anything
     *
     ******************************************************************************************************************/
    @Nonnull
    public T firstResult()
      throws NotFoundException;

    /*******************************************************************************************************************
     *
     * Performs the search and returns the found items.
     * 
     * @return            the searched items
     *
     ******************************************************************************************************************/
    @Nonnull
    public List<? extends T> results();

    /*******************************************************************************************************************
     *
     * Performs the search and returns the count of found items.
     * 
     * @return            the count of found items
     *
     ******************************************************************************************************************/
    @Nonnegative
    public int count();
  }
