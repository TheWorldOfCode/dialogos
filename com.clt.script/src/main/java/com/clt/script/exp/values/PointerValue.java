/*
 * @(#)PointerValue.java
 * Created on Sun Oct 05 2003
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

package com.clt.script.exp.values;

import com.clt.script.exp.Type;
import com.clt.script.exp.Value;
import com.clt.script.exp.Variable;
import com.clt.script.exp.types.PointerType;

/**
 * 
 * 
 * @author Daniel Bobbert
 * @version 1.0
 */

public final class PointerValue extends Value
{

	Variable variable;

	public PointerValue(Variable variable)
	{

		this.variable = variable;
	}

	@Override
	protected Value copyValue()
	{

		return new PointerValue(this.variable);
	}

	@Override
	public Type getType()
	{

		return new PointerType(this.variable.getType());
	}

	@Override
	public boolean equals(Object v)
	{

		if (v instanceof PointerValue)
		{
			return ((PointerValue) v).variable == this.variable;
		} else
		{
			return false;
		}
	}

	@Override
	public int hashCode()
	{

		return this.variable.hashCode();
	}

	/**
	 * Return the value pointed to by this pointer.
	 */
	public Value getValue()
	{

		return this.variable.getValue();
	}

	/**
	 * Change the value pointed to by this pointer.
	 */
	public void setValue(Value value)
	{

		this.variable.setValue(value);
	}

	@Override
	public String toString()
	{

		String s = Integer.toHexString(this.hashCode());
		StringBuilder b = new StringBuilder(10);
		b.append("0x");
		for (int i = 8 - s.length(); i > 0; i--)
		{
			b.append('0');
		}
		b.append(s);
		return b.toString();
	}

	@Override
	public Object getReadableValue()
	{
		return getValue().getReadableValue();
	}
}
