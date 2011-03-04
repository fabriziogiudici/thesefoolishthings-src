/***********************************************************************************************************************
 *
 * These Foolish Things - Miscellaneous utilities
 * Copyright (C) 2009-2011 by Tidalwave s.a.s. (http://www.tidalwave.it)
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
package it.tidalwave.beans;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.InputStream;
import org.junit.BeforeClass;
import it.tidalwave.util.logging.SingleLineLogFormatter;

/*******************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 ******************************************************************************/
public class TestSupport 
  {
    @BeforeClass
    public static void setupLogging()
      throws Exception
      {
        try 
          {
            final InputStream is = TestSupport.class.getResourceAsStream("log.properties");
            LogManager.getLogManager().readConfiguration(is);
            is.close();
            
            //
            // The formatter must be set programmatically as the property in the log.properties won't
            // be honored. I suspect it is related with NetBeans module classloaders as the formatter
            // is loaded inside LogManager by using the SystemClassLoader, which only sees the classpath.
            //
            final SingleLineLogFormatter formatter = new SingleLineLogFormatter();
            Logger rootLogger = Logger.getLogger(TestSupport.class.getName());
            
            while (rootLogger.getParent() != null)
              {
                rootLogger = rootLogger.getParent();  
              }

            for (final Handler handler : rootLogger.getHandlers())
              {
                handler.setFormatter(formatter);
              }
          } 
        catch (Exception e) 
          {
            e.printStackTrace();
          }
      }
  }
