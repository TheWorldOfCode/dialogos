package com.clt.gui;import java.awt.Container;import java.awt.Rectangle;import java.awt.event.MouseEvent;import javax.swing.event.MouseInputListener;public class ParentMouseInputListener implements MouseInputListener {    Container parent;    MouseInputListener ml;    public ParentMouseInputListener(Container parent, MouseInputListener ml) {        this.parent = parent;        this.ml = ml;    }    public static void propagateEvent(MouseEvent evt) {        ParentMouseInputListener.propagateEvent(evt, evt.getComponent().getParent());    }    public static void propagateEvent(MouseEvent evt, Container parent) {        parent.dispatchEvent(ParentMouseInputListener.transform(evt, parent));    }    public static MouseEvent transform(MouseEvent e, Container parent) {        Rectangle r = GUI.getRelativeBounds(e.getComponent(), parent);        return new MouseEvent(parent, e.getID(), e.getWhen(), e.getModifiers(),                e.getX() + r.x, e.getY() + r.y, e.getClickCount(), e.isPopupTrigger());    }    public void mouseClicked(MouseEvent e) {        this.ml.mouseClicked(ParentMouseInputListener.transform(e, this.parent));    }    public void mousePressed(MouseEvent e) {        this.ml.mousePressed(ParentMouseInputListener.transform(e, this.parent));    }    public void mouseReleased(MouseEvent e) {        this.ml.mouseReleased(ParentMouseInputListener.transform(e, this.parent));    }    public void mouseEntered(MouseEvent e) {        this.ml.mouseEntered(ParentMouseInputListener.transform(e, this.parent));    }    public void mouseExited(MouseEvent e) {        this.ml.mouseExited(ParentMouseInputListener.transform(e, this.parent));    }    public void mouseDragged(MouseEvent e) {        this.ml.mouseDragged(ParentMouseInputListener.transform(e, this.parent));    }    public void mouseMoved(MouseEvent e) {        this.ml.mouseMoved(ParentMouseInputListener.transform(e, this.parent));    }}