/***********************************************************************************************************************
 *
 * TheseFoolishThings - Miscellaneous utilities
 * ============================================
 *
 * Copyright (C) 2009-2011 by Tidalwave s.a.s.
 * Project home page: http://thesefoolishthings.kenai.com
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
 * $Id$
 *
 **********************************************************************************************************************/
package it.tidalwave.role;

import javax.annotation.Nonnull;
import java.util.Locale;

/***********************************************************************************************************************
 *
 * This interface defines the behavior of objects that can display their names possibly according to the current
 * {@link Locale}.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 * @stable
 *
 **********************************************************************************************************************/
public interface Displayable
  {
    public final static Class<Displayable> Displayable = Displayable.class;
    
    public final static Displayable DEFAULT = new DefaultDisplayable("", "DEFAULT"); 
    
    /*******************************************************************************************************************
     *
     * Returns the display name in the current {@link Locale}.
     *
     * @returns  the display name
     *
     ******************************************************************************************************************/
    @Nonnull
    public String getDisplayName();
  }
