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
 * 
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.role.ui.impl;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import it.tidalwave.role.ui.Styleable;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * A default implementation of {@link Styleable}. This class is not part of the public API.
 * 
 * @stereotype Role
 * 
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Nonnull @ToString
public class DefaultStyleable implements Styleable
  {
    private final List<String> styles = new ArrayList<>();
    
    public DefaultStyleable (@Nonnull final Collection<String> styles)
      {
        this.styles.addAll(styles);
      }
    
    public DefaultStyleable (@Nonnull final String ... styles)
      {
        this(Arrays.asList(styles));
      }
    
    @Override @Nonnull
    public Collection<String> getStyles() 
      {
        return new CopyOnWriteArrayList<>(styles);
      }
  }
