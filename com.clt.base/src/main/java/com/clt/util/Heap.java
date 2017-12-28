/*
 * @(#)Heap.java
 * Created on 05.09.2006 by dabo
 *
 * Copyright (c) CLT Sprachtechnologie GmbH.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of CLT Sprachtechnologie GmbH ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with CLT Sprachtechnologie GmbH.
 */

package com.clt.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author dabo
 * 
 */
public interface Heap<V> extends Collection<V> {

  public class Entry<V> {

    private Collection<Entry<V>> children;
    private V value;
    private int key;


    public Entry(V value, int key) {

      this.value = value;
      this.key = key;
      this.children = Collections.emptyList();
    }


    public V get() {

      return this.value;
    }


    public void decreaseKey() {

      this.decreaseKey(1);
    }


    public void decreaseKey(int n) {

      this.key -= n;
    }


    public int size() {

      return this.children.size();
    }


    public void addChild(Entry<V> child) {

      assert (child.key >= this.key) : "Cannot add child with a smaller key";

      if (this.children.isEmpty()) {
        this.children = new LinkedList<Entry<V>>();
      }
      this.children.add(child);
    }


    public Iterator<Entry<V>> getChildren() {

      return this.children.iterator();
    }
  }


  public V removeMin();


  public void add(V value, int key);


  public void decreaseKey(V value);


  public void decreaseKey(V value, int n);
}
