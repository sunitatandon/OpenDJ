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



import static org.testng.Assert.*;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.opends.messages.Message;
import org.opends.server.core.AddOperation;
import org.opends.server.core.BindOperation;
import org.opends.server.core.CompareOperation;
import org.opends.server.core.DeleteOperation;
import org.opends.server.core.ModifyDNOperation;
import org.opends.server.core.ModifyOperation;
import org.opends.server.core.SearchOperation;
import org.opends.server.types.CanceledOperationException;
import org.opends.server.types.DN;
import org.opends.server.types.DirectoryException;
import org.opends.server.types.Entry;
import org.opends.server.types.SearchFilter;
import org.opends.server.types.SearchScope;
import org.testng.annotations.Test;



/**
 * This class tests {@link DataProviderEvent}.
 */
public final class AbstractDataProviderTestCase extends
    DataProviderTestCase
{

  /**
   * Mock data provider.
   */
  private final static class MyDataProvider extends
      AbstractDataProvider
  {

    @Override
    public DataProviderConnection connect()
    {
      return new AbstractDataProviderConnection()
        {

          @Override
          protected AbstractDataProvider getAbstractDataProvider()
          {
            return MyDataProvider.this;
          }

        };
    }



    public void startDataProvider()
    {
      // Nothing to do.
    }



    public void stopDataProvider()
    {
      // Nothing to do.
    }



    public void finalizeDataProvider()
    {
      // Nothing to do.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(AddOperation addOperation)
        throws CanceledOperationException, DirectoryException
    {
      // No implementation required.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(BindOperation bindOperation)
        throws CanceledOperationException, DirectoryException
    {
      // No implementation required.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(CompareOperation compareOperation)
        throws CanceledOperationException, DirectoryException
    {
      // No implementation required.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(DeleteOperation deleteOperation)
        throws CanceledOperationException, DirectoryException
    {
      // No implementation required.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(ModifyDNOperation modifyDNOperation)
        throws CanceledOperationException, DirectoryException
    {
      // No implementation required.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(ModifyOperation modifyOperation)
        throws CanceledOperationException, DirectoryException
    {
      // No implementation required.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(SearchOperation searchOperation)
        throws CanceledOperationException, DirectoryException
    {
      // No implementation required.
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected DataProviderStatus getStatus(DN baseDN)
        throws DirectoryException
    {
      // No implementation required.
      return DataProviderStatus.ENABLED;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<DN> getBaseDNs()
    {
      // No implementation required.
      return Collections.emptySet();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected Entry getEntry(DN dn) throws DirectoryException
    {
      // No implementation required.
      throw new RuntimeException();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<String> getSupportedControls(DN baseDN)
        throws DirectoryException
    {
      // No implementation required.
      return Collections.emptySet();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<String> getSupportedFeatures(DN baseDN)
        throws DirectoryException
    {
      // No implementation required.
      return Collections.emptySet();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void search(DN baseDN, SearchScope scope,
        SearchFilter filter, DataProviderSearchHandler handler)
        throws DirectoryException
    {
      // No implementation required.
    }

  };

  /**
   * Mock listener.
   */
  private static final class TestListener implements
      DataProviderConnectionEventListener
  {

    public DataProviderEvent event = null;

    public boolean isClosed = false;



    /**
     * {@inheritDoc}
     */
    public void dataProviderStateChanged(DataProviderEvent event)
    {
      this.event = event;
    }



    /**
     * {@inheritDoc}
     */
    public void dataProviderConnectionClosed()
    {
      isClosed = true;
    }

  };



  /**
   * Tests notifyDataProviderStateChanged with no listeners.
   */
  @Test
  public void testNotifyDataProviderStateChangedWithNoListeners1()
  {
    AbstractDataProvider provider = new MyDataProvider();
    provider.notifyDataProviderStateChanged(Message.EMPTY, EnumSet
        .of(DataProviderEvent.Type.ACCESS_MODE));
  }



  /**
   * Tests notifyDataProviderStateChanged with no listeners.
   */
  @Test
  public void testNotifyDataProviderStateChangedWithNoListeners2()
  {
    AbstractDataProvider provider = new MyDataProvider();
    DataProviderEvent event =
        new DataProviderEvent(Message.EMPTY, EnumSet
            .of(DataProviderEvent.Type.ACCESS_MODE));
    provider.notifyDataProviderStateChanged(event);
  }



  /**
   * Tests listener registration / deregistration.
   */
  @Test
  public void testNotifyDataProviderStateChangedWithSingleListener1()
  {
    AbstractDataProvider provider = new MyDataProvider();
    TestListener listener = new TestListener();

    // Test notification occurs after registration.
    provider.registerEventListener(listener);
    provider.notifyDataProviderStateChanged(Message.EMPTY, EnumSet
        .of(DataProviderEvent.Type.ACCESS_MODE));

    assertNotNull(listener.event);

    // Test notification event is correct.
    assertSame(listener.event.getReason(), Message.EMPTY);
    assertEquals(listener.event.getEventTypes(), EnumSet
        .of(DataProviderEvent.Type.ACCESS_MODE));

    // Test notification stops after deregistration.
    provider.deregisterEventListener(listener);
    listener.event = null;

    provider.notifyDataProviderStateChanged(Message.EMPTY, EnumSet
        .of(DataProviderEvent.Type.ACCESS_MODE));

    assertNull(listener.event);
  }



  /**
   * Tests listener registration / deregistration.
   */
  @Test
  public void testNotifyDataProviderStateChangedWithSingleListener2()
  {
    AbstractDataProvider provider = new MyDataProvider();
    TestListener listener = new TestListener();
    DataProviderEvent event =
        new DataProviderEvent(Message.EMPTY, EnumSet
            .of(DataProviderEvent.Type.ACCESS_MODE));

    // Test notification occurs after registration.
    provider.registerEventListener(listener);
    provider.notifyDataProviderStateChanged(event);

    assertNotNull(listener.event);

    // Test notification event is correct.
    assertSame(listener.event.getReason(), event.getReason());
    assertEquals(listener.event.getEventTypes(), event.getEventTypes());

    // Test notification stops after deregistration.
    provider.deregisterEventListener(listener);
    listener.event = null;

    provider.notifyDataProviderStateChanged(event);

    assertNull(listener.event);
  }

}
