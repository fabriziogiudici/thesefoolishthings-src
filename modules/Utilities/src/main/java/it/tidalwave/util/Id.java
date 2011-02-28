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
 * $Id$
 *
 **********************************************************************************************************************/
package it.tidalwave.util;

import javax.annotation.concurrent.Immutable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.Serializable;

/***********************************************************************************************************************
 *
 * An opaque container for identifiers.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 * @stable
 *
 **********************************************************************************************************************/
@Immutable
public class Id implements Serializable, Comparable<Id>, StringValue
  {
    private static final long serialVersionUID = 3309234234279593043L;
    
    @Nonnull
    private final String value;

    /***************************************************************************
     *
     * Creates a new <code>Id</code>.
     *
     * @param   value  the value of the id
     *
     **************************************************************************/
    public Id (@Nonnull final String value)
      {
        if (value == null)
          {
            throw new IllegalArgumentException("value is mandatory");
          }

        this.value = value;
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     *
     **************************************************************************/
//    @Override
    @Nonnull
    public String stringValue()
      {
        return value;
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     *
     **************************************************************************/
    @Override
    public boolean equals (@CheckForNull final Object object)
      {
        if (object == null)
          {
            return false;
          }

        if (getClass() != object.getClass())
          {
            return false;
          }

        final Id other = (Id)object;

        return this.value.equals(other.value);
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     *
     **************************************************************************/
    @Override
    public int hashCode() 
      {
        return value.hashCode();
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     *
     **************************************************************************/
//    @Override
    public int compareTo (final Id other)
      {
        return this.value.compareTo(other.value);
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     *
     **************************************************************************/
    @Override @Nonnull
    public String toString()
      {
        return value;
      }
  }
