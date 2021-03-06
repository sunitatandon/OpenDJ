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
 *      Copyright 2008-2009 Sun Microsystems, Inc.
 *      Portions Copyright 2014-2015 ForgeRock AS
 */

package org.opends.guitools.controlpanel.task;

import static org.opends.messages.AdminToolMessages.*;

import java.util.ArrayList;

import org.opends.guitools.controlpanel.datamodel.ControlPanelInfo;
import org.opends.guitools.controlpanel.ui.ProgressDialog;
import org.forgerock.i18n.LocalizableMessage;

/**
 * The task called when we want to start the server.
 *
 */
public class StartServerTask extends StartStopTask
{

  /**
   * Constructor of the task.
   * @param info the control panel information.
   * @param dlg the progress dialog where the task progress will be displayed.
   */
  public StartServerTask(ControlPanelInfo info, ProgressDialog dlg)
  {
    super(info, dlg);
  }

  /** {@inheritDoc} */
  public Type getType()
  {
    return Type.START_SERVER;
  }

  /** {@inheritDoc} */
  public LocalizableMessage getTaskDescription()
  {
    return INFO_CTRL_PANEL_START_SERVER_TASK_DESCRIPTION.get();
  }

  /** {@inheritDoc} */
  protected String getCommandLinePath()
  {
    return getCommandLinePath("start-ds");
  }

  /** {@inheritDoc} */
  protected ArrayList<String> getCommandLineArguments()
  {
    ArrayList<String> args = new ArrayList<>();
    args.add("--timeout");
    args.add("0");
    return args;
  }
}
