/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE
 * or https://OpenDS.dev.java.net/OpenDS.LICENSE.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE.  If applicable,
 * add the following below this CDDL HEADER, with the fields enclosed
 * by brackets "[]" replaced with your own identifying information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Copyright 2008 Sun Microsystems, Inc.
 */
package org.opends.server.core.dataproviders;



import java.util.List;

import org.opends.messages.Message;
import org.opends.server.admin.std.server.DataProviderCfg;
import org.opends.server.config.ConfigException;
import org.opends.server.types.InitializationException;



/**
 * A factory for creating data provider instances.
 *
 * @param <T>
 *          The type of configuration handled by this data provider.
 * @see DataProvider
 */
public interface DataProviderFactory<T extends DataProviderCfg>
{

  /**
   * Creates and initializes a new data provider based on the
   * information in the provided configuration.
   * <p>
   * Implementations must not start any services nor attempt to connect
   * to other data providers. However, they may register with the
   * backup/restore and import/export managers.
   *
   * @param id
   *          The ID which should be used to uniquely identify this data
   *          provider.
   * @param configuration
   *          The configuration that contains the information to use to
   *          create and initialize the new data provider.
   * @return The new data provider instance.
   * @throws ConfigException
   *           If an unrecoverable problem arises during initialization
   *           of the data provider as a result of the server
   *           configuration.
   * @throws InitializationException
   *           If a problem occurs during initialization of the data
   *           provider that is not related to the server configuration.
   * @see DataProvider#startDataProvider()
   */
  DataProvider createDataProvider(DataProviderID id, T configuration)
      throws ConfigException, InitializationException;



  /**
   * Indicates whether the provided configuration is acceptable for
   * creating and initializing a new data provider using this data
   * provider factory.
   * <p>
   * This method will be called before
   * {@link #createDataProvider(DataProviderID, DataProviderCfg)} in
   * order validate the configuration.
   * <p>
   * Implementations should perform basic validation of the
   * configuration but should not attempt to start any resource or
   * connect to other data providers.
   *
   * @param configuration
   *          The configuration for which to make the determination.
   * @param unacceptableReasons
   *          A list that may be used to hold the reasons that the
   *          provided configuration is not acceptable.
   * @return {@code true} if the provided configuration is acceptable
   *         for this data provider factory, or {@code false} if not.
   */
  boolean isConfigurationAcceptable(T configuration,
      List<Message> unacceptableReasons);
}
