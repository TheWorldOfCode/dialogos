/*
 * @(#)Variable.java
 * Created on Mon Oct 06 2003
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

package com.clt.script.exp;

/**
 * A reference to a variable value.
 * 
 * The runtime uses variables to access external information within expressions
 * and scripts. To create such a reference that can be used by the runtime, you
 * have to implement methods that get and set the current value.
 * 
 * @author Daniel Bobbert
 * @version 2.0
 */

public interface Variable {

  /**
   * Return the fully qualified name of the variable.
   */
  public String getName();


  /**
   * Return the current value of the variable.
   * 
   * @return The current variable value
   * @see #setValue
   */
  public Value getValue();


  /**
   * Set the current value of the variable.
   * 
   * @param value
   *          The new value
   * @see #getValue
   */
  public void setValue(Value value);


  /**
   * Return the type of the variable.
   * 
   * You must ensure that {@link Variable#getValue} will always return values
   * matching the type given by <code>getType</code>.
   * 
   * @return The type of the variable.
   * @see #getValue
   * @see #setValue
   */
  public Type getType();
}
