package fr.umlv.smalljs.grammar.tools;

import fr.umlv.smalljs.ast.Literal;

/** 
 *  @param <D> data type passed by the lexer listener.
 *
 *  This class is generated - please do not edit it 
 */
public interface TerminalEvaluator<D> {
  /** This method is called when the rule <code>id</code> is recognized by the lexer.
   *  @param data the data sent by the lexer, in general, the
   *         {@link fr.umlv.tatoo.runtime.buffer.TokenBuffer#view a view of the token buffer} or the buffer itself.
  
   *  @return the value associated with the terminal spawn for the rule.
   */
  public Literal<String> id(D data);
  /** This method is called when the rule <code>integer</code> is recognized by the lexer.
   *  @param data the data sent by the lexer, in general, the
   *         {@link fr.umlv.tatoo.runtime.buffer.TokenBuffer#view a view of the token buffer} or the buffer itself.
  
   *  @return the value associated with the terminal spawn for the rule.
   */
  public Literal<Integer> integer(D data);
  /** This method is called when the rule <code>text</code> is recognized by the lexer.
   *  @param data the data sent by the lexer, in general, the
   *         {@link fr.umlv.tatoo.runtime.buffer.TokenBuffer#view a view of the token buffer} or the buffer itself.
  
   *  @return the value associated with the terminal spawn for the rule.
   */
  public Literal<String> text(D data);
  /** This method is called when the rule <code>comment</code> is recognized by the lexer.
   *  @param data the data sent by the lexer, in general, the
   *         {@link fr.umlv.tatoo.runtime.buffer.TokenBuffer#view a view of the token buffer} or the buffer itself.
   */
  public void comment(D data);
}
