/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at legal-notices/CDDLv1_0.txt
 * or http://forgerock.org/license/CDDLv1.0.html.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at legal-notices/CDDLv1_0.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Copyright 2006-2010 Sun Microsystems, Inc.
 *      Portions Copyright 2013-2015 ForgeRock AS.
 */
package org.opends.server.replication.plugin;

import org.forgerock.opendj.ldap.ByteString;
import org.opends.server.replication.common.CSN;

/** AttrValueHistorical is the historical information of the modification of one attribute value. */
public class AttrValueHistorical
{
  private ByteString value;
  private CSN valueDeleteTime;
  private CSN valueUpdateTime;

  /**
   * Build an AttrValueHistorical for a provided attribute value, providing
   * the last time the provided value is either updated or deleted.
   * @param value    the provided attributeValue
   * @param csnUpdate last time when this value was updated
   * @param csnDelete last time when this value for deleted
   */
  public AttrValueHistorical(ByteString value, CSN csnUpdate, CSN csnDelete)
  {
    this.value = value;
    this.valueUpdateTime = csnUpdate;
    this.valueDeleteTime = csnDelete;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof AttrValueHistorical)
    {
      AttrValueHistorical objVal = (AttrValueHistorical) obj;
      return value.equals(objVal.getAttributeValue());
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return value.hashCode();
  }

  /**
   * Get the last time when the value was deleted.
   * @return the last time when the value was deleted
   */
  public CSN getValueDeleteTime()
  {
    return valueDeleteTime;
  }

  /**
   * Get the last time when the value was updated.
   * @return the last time when the value was updated
   */
  public CSN getValueUpdateTime()
  {
    return valueUpdateTime;
  }

  /**
   * Get the attributeValue for which this object was generated.
   * @return the value for which this object was generated
   */
  public ByteString getAttributeValue()
  {
    return value;
  }

  /**
   * Check if the value associated with this object was updated.
   * @return true if the value associated with this object was updated
   */
  public boolean isUpdate()
  {
    return valueUpdateTime != null;
  }

  @Override
  public String toString()
  {
    if (valueUpdateTime != null)
    {
      return valueDeleteTime != null
          // valueUpdateTime and valueDeleteTime should have the same value
          ? valueUpdateTime + ":replace:" + value
          : valueUpdateTime + ":add:" + value;
    }
    else
    {
      return valueDeleteTime != null
          ? valueDeleteTime + ":delete:" + value
          : "????:" + value;
    }
  }
}
