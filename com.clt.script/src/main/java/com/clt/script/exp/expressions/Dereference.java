/*
 * @(#)Dereference.java
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

package com.clt.script.exp.expressions;

import java.io.PrintWriter;
import java.util.Map;

import com.clt.script.debug.Debugger;
import com.clt.script.exp.EvaluationException;
import com.clt.script.exp.Expression;
import com.clt.script.exp.Type;
import com.clt.script.exp.TypeException;
import com.clt.script.exp.Value;
import com.clt.script.exp.types.PointerType;
import com.clt.script.exp.values.PointerValue;

/**
 * 
 * 
 * @author Daniel Bobbert
 * @version 1.0
 */

public class Dereference extends Expression {

  Expression e;


  public Dereference(Expression e) {

    this.e = e;
  }


  @Override
  public Expression copy(Map<?, ?> mapping) {

    return new Dereference(this.e.copy(mapping));
  }


  @Override
  protected Value eval(Debugger dbg) {

    Value v = this.e.evaluate(dbg);

    if (v instanceof PointerValue) {
      return ((PointerValue)v).getValue();
    }
    else {
      throw new EvaluationException("Cannot dereference " + v);
    }
  }


  @Override
  public Type getType() {

    Type t = this.e.getType();
    if (!(t instanceof PointerType)) {
      throw new TypeException("Cannot dereference " + this.e);
    }
    else {
      return ((PointerType)t).getBaseType();
    }
  }


  @Override
  public int getPriority() {

    return 17;
  }


  @Override
  public void write(PrintWriter w) {

    w.print('*');
    this.e.write(w, this.e.getPriority() <= this.getPriority());
  }
}
