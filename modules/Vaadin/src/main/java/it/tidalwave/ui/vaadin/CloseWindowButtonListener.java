/*
 * #%L
 * *********************************************************************************************************************
 * 
 * These Foolish Things - Miscellaneous utilities
 * http://thesefoolishthings.java.net - hg clone https://bitbucket.org/tidalwave/thesefoolishthings-src
 * %%
 * Copyright (C) 2009 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.ui.vaadin;

import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author Gabriele Cuccu <gabriele.cuccu@gmail.com>
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class CloseWindowButtonListener implements Button.ClickListener
  {
    private final Window window;

    @Override
    public void buttonClick (final Button.ClickEvent event)
      {
        final Button button = event.getButton();
        button.removeListener(this);
        (window.getParent()).removeWindow(window);
      }
  };
