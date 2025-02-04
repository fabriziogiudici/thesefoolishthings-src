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
package it.tidalwave.util;

import jakarta.annotation.Nonnull;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***************************************************************************************************************************************************************
 *
 * Facility class to manage resource bundles.
 *
 * @since   3.1-ALPHA-2
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@NoArgsConstructor(access = AccessLevel.PRIVATE) @Slf4j
public final class BundleUtilities
  {
    /***********************************************************************************************************************************************************
     * {@return a localised message}.
     * @param   clazz                 the owner of the bundle
     * @param   key                   the key of the resource inside the bundle
     * @param   params                the parameters (used if the string in the bundle is a {@code String} format)
     **********************************************************************************************************************************************************/
    @Nonnull
    public static String getMessage (@Nonnull final Class<?> clazz, @Nonnull final String key, @Nonnull final Object ... params)
      {
        return getMessage(clazz, Locale.getDefault(), key, params);
      }

    /***********************************************************************************************************************************************************
     * {@return a localised message}.
     * @since   3.1-ALPHA-4
     * @param   clazz                 the owner of the bundle
     * @param   locale                the {@link Locale}
     * @param   key                   the key of the resource inside the bundle
     * @param   params                the parameters (used if the string in the bundle is a {@code String} format)
     **********************************************************************************************************************************************************/
    @Nonnull
    public static String getMessage (@Nonnull final Class<?> clazz, @Nonnull final Locale locale, @Nonnull final String key, @Nonnull final Object ... params)
      {
        try
          {
            final var moduleName = clazz.getModule().getName();
            final var packageName = clazz.getPackage().getName();
            final var bundleName = packageName + ".Bundle";
            final var bundle = moduleName == null ? ResourceBundle.getBundle(bundleName, locale)
                                                  : ResourceBundle.getBundle(bundleName, locale, clazz.getModule());
            final var string = bundle.getString(key);
            return params.length == 0 ? string : String.format(string, params);
          }
        catch (MissingResourceException e)
          {
            log.error("", e);
            return "Missing resource: " + key;
          }
      }
  }
