/*
 * @(#)GraphListener.java
 * Created on Fri Oct 31 2003
 *
 * Copyright (c) 2003 CLT Sprachtechnologie GmbH.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of CLT Sprachtechnologie GmbH ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with CLT Sprachtechnologie GmbH.
 */

package com.clt.diamant.graph;

/**
 * 
 * 
 * @author Daniel Bobbert
 * @version 1.0
 */

public interface GraphListener {

  public void elementAdded(Graph g, VisualGraphElement element);


  public void elementRemoved(Graph g, VisualGraphElement element);


  public void sizeChanged(Graph g, int width, int height);


  public void graphRenamed(Graph g, String name);
}
