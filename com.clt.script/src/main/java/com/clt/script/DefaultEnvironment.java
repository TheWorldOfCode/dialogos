/*
 * @(#)DefaultEnvironment.java
 * Created on Fri Nov 07 2003
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

package com.clt.script;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import com.clt.script.exp.Expression;
import com.clt.script.exp.MethodDescriptor;
import com.clt.script.exp.Type;
import com.clt.script.exp.Variable;

/**
 * 
 * 
 * @author Daniel Bobbert
 * @version 1.0
 */

public class DefaultEnvironment implements Environment {

  private static ClassEnvironment builtin = null;


  public DefaultEnvironment() {

    if (DefaultEnvironment.builtin == null) {
      DefaultEnvironment.builtin = new ClassEnvironment(BuiltinFunctions.class);
    }
  }


  public Type getType(String typeName) {

    return DefaultEnvironment.builtin.getType(typeName);
  }


  public Variable createVariableReference(String name) {

    return DefaultEnvironment.builtin.createVariableReference(name);
  }


  public Expression createFunctionCall(String name, Expression[] arguments) {

    return DefaultEnvironment.builtin.createFunctionCall(name, arguments);
  }


  public Reader include(String name)
      throws IOException {

    return DefaultEnvironment.builtin.include(name);
  }


  public Iterator<MethodDescriptor> getMethods() {

    return DefaultEnvironment.builtin.getMethods();
  }


  static String functionSignature(String name, Expression[] arguments) {

    StringBuilder f = new StringBuilder();
    f.append(name);
    f.append('(');
    for (int i = 0; i < arguments.length; i++) {
      if (i > 0) {
        f.append(", ");
      }
      f.append(arguments[i].getType().resolve());
    }
    f.append(')');
    return f.toString();
  }
}
