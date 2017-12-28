package com.clt.diamant;import java.util.ArrayList;import java.util.Collection;import java.util.List;import com.clt.util.Misc;import com.clt.util.StringTools;public class DialogOutput    implements Cloneable {  private List<String> expressions;  public DialogOutput() {    this(null);  }  private DialogOutput(Collection<String> expressions) {    if (expressions == null) {      this.expressions = new ArrayList<String>();    }    else {      this.expressions = new ArrayList<String>(expressions);    }  }  @Override  public Object clone() {    return new DialogOutput(this.expressions);  }  public void add(String value) {    this.expressions.add(value);  }  public void remove(int[] rows) {    Misc.removeElements(this.expressions, rows);  }  public void move(int source, int target) {    String o = this.expressions.get(source);    this.expressions.set(source, this.expressions.get(target));    this.expressions.set(target, o);  }  public String getValue(int i) {    return this.expressions.get(i);  }  public void setValue(int i, String s) {    this.expressions.set(i, s);  }  public int size() {    return this.expressions.size();  }  @Override  public String toString() {    StringBuilder b = new StringBuilder();    for (int i = 0; i < this.size(); i++) {      b.append(this.getValue(i));      b.append('\n');    }    return b.toString();  }  public String getDescription() {    StringBuilder buffer = new StringBuilder();    buffer.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");    for (int i = 0; i < this.size(); i++) {      buffer.append("<tr>");      buffer.append("<td width=\"20\"></td>");      buffer.append("<td><tt>"        + StringTools.toHTML(this.getValue(i).toString()) + "</tt></td>");      buffer.append("</tr>");    }    buffer.append("</table>");    return buffer.toString();  }}