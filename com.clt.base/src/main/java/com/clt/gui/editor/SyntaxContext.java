/* * @(#)JavaContext.java	1.2 98/05/04 * * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved. * * This software is the confidential and proprietary information of Sun * Microsystems, Inc. ("Confidential Information").  You shall not * disclose such Confidential Information and shall use it only in * accordance with the terms of the license agreement you entered into * with Sun. * * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING * THIS SOFTWARE OR ITS DERIVATIVES. * */package com.clt.gui.editor;import java.awt.Color;import java.awt.Container;import java.awt.Font;import java.awt.Graphics;import javax.swing.event.ChangeEvent;import javax.swing.event.ChangeListener;import javax.swing.text.BadLocationException;import javax.swing.text.Element;import javax.swing.text.Segment;import javax.swing.text.Style;import javax.swing.text.StyleConstants;import javax.swing.text.StyleContext;import javax.swing.text.Utilities;import javax.swing.text.View;import javax.swing.text.ViewFactory;import javax.swing.text.WrappedPlainView;/** * A collection of styles used to render java text. This class also acts as a * factory for the views used to represent the java documents. Since the * rendering styles are based upon view preferences, the views need a way to * gain access to the style settings which is facilitated by implementing the * factory in the style storage. Both functionalities can be widely shared * across java document views. *  * @author Timothy Prinzing * @version 1.2 05/04/98 */public class SyntaxContext    extends StyleContext    implements ViewFactory {  /**   * The styles representing the actual token types.   */  private Style[] tokenStyles;  /**   * Cache of foreground colors to represent the various tokens.   */  private transient Color[] tokenColors;  /**   * Cache of fonts to represent the various tokens.   */  private transient Font[] tokenFonts;  /**   * Constructs a set of styles to represent lexical tokens. By default there   * are no colors or fonts specified.   */  public SyntaxContext(Scanner scanner) {    super();    Style root = this.getStyle(StyleContext.DEFAULT_STYLE);    int size = scanner.numStyles();    this.tokenStyles = new Style[size];    Object scannerAttribute = new Object() {      @Override      public String toString() {        return "symbol";      }    };    for (int i = 0; i < size; i++) {      Style parent = this.getStyle(this.getStyleName(i));      if (parent == null) {        parent = this.addStyle(this.getStyleName(i), root);      }      Style s = this.addStyle(null, parent);      s.addAttribute(scannerAttribute, scanner);      StyleConstants.setForeground(s, scanner.getStyleColor(i));      this.tokenStyles[i] = s;    }  }  private String getStyleName(int index) {    return "Style " + index;  }  /**   * Fetch the foreground color to use for a lexical token with the given value.   */  public Color getForeground(int code) {    if (this.tokenColors == null) {      this.tokenColors = new Color[this.tokenStyles.length];    }    if ((code >= 0) && (code < this.tokenColors.length)) {      Color c = this.tokenColors[code];      if (c == null) {        Style s = this.tokenStyles[code];        c = StyleConstants.getForeground(s);      }      return c;    }    return Color.black;  }  /**   * Fetch the font to use for a lexical token with the given scan value.   */  public Font getFont(int code) {    if (this.tokenFonts == null) {      this.tokenFonts = new Font[this.tokenStyles.length];    }    if (code < this.tokenFonts.length) {      Font f = this.tokenFonts[code];      if (f == null) {        Style s = this.tokenStyles[code];        f = this.getFont(s);      }      return f;    }    return null;  }  // --- ViewFactory methods -------------------------------------  public View create(Element elem) {    return new SyntaxView(elem);  }  /**   * View that uses the lexical information to determine the style   * characteristics of the text that it renders. This simply colorizes the   * various tokens and assumes a constant font family and size.   */  class SyntaxView        extends WrappedPlainView {    Scanner lexer;    /**     * Construct a simple colorized view of the text.     */    SyntaxView(Element elem) {      super(elem);      SyntaxDocument doc = (SyntaxDocument)this.getDocument();      doc.addChangeListener(new ChangeListener() {        public void stateChanged(ChangeEvent evt) {          Container c = SyntaxView.this.getContainer();          if (c != null) {            c.repaint();          }        }      });      this.lexer = doc.getScanner();    }    /**     * Renders the given range in the model as normal unselected text. This is     * implemented to paint colors based upon the token-to-color translations.     * To reduce the number of calls to the Graphics object, text is batched up     * until a color change is detected or the entire requested range has been     * reached.     *      * @param g     *          the graphics context     * @param x     *          the starting X coordinate     * @param y     *          the starting Y coordinate     * @param p0     *          the beginning position in the model     * @param p1     *          the ending position in the model     * @returns the location of the end of the range     * @exception BadLocationException     *              if the range is invalid     */    @Override    protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1)            throws BadLocationException {      SyntaxDocument doc = (SyntaxDocument)this.getDocument();      Color last = null;      // System.out.println("Drawing " + p0 + " - " + p1);      if (p0 < p1) {        Symbol[] symbols = doc.getSymbols(p0, p1);        for (int i = 0; i < symbols.length; i++) {          // int start = Math.max(symbols[i].getStart(), p0);          int start = p0;          int end =            Math.min(i < symbols.length - 1 ? symbols[i + 1].getStart() : p1,              p1);          // System.out.println("Token " + (i+1) + ": " + start + " -          // " + end);          Color fg = SyntaxContext.this.getForeground(symbols[i].getStyle());          if (fg != last) {            g.setColor(fg);            last = fg;          }          Segment text = this.getLineBuffer();          doc.getText(start, end - start, text);          x = Utilities.drawTabbedText(text, x, y, g, this, start);          p0 = end;        }      }      return x;    }    @Override    protected int getTabSize() {      return 4;    }  }}