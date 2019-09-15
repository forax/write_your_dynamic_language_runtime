package fr.umlv.smalljs.grammar.tools;

  import fr.umlv.smalljs.ast.Block;
    import fr.umlv.smalljs.ast.Expr;
    import fr.umlv.smalljs.ast.Literal;
    import java.util.List;
  
import fr.umlv.smalljs.grammar.lexer.RuleEnum;
import fr.umlv.smalljs.grammar.parser.TerminalEnum;
import fr.umlv.smalljs.grammar.parser.NonTerminalEnum;
import fr.umlv.smalljs.grammar.parser.ProductionEnum;
import fr.umlv.smalljs.grammar.tools.TerminalEvaluator;
import fr.umlv.smalljs.grammar.tools.GrammarEvaluator;

import fr.umlv.tatoo.runtime.buffer.LexerBuffer;
import fr.umlv.tatoo.runtime.tools.AnalyzerListener;
import fr.umlv.tatoo.runtime.tools.DataViewer;
import fr.umlv.tatoo.runtime.tools.SemanticStack;

/**  This class is called by the parser when
 *  <ol>
 *    <li>a terminal is shifted</li>
 *    <li>a non terminal is reduced</li>
 *    <li>a non terminal is accepted</li>
 *   </ol>
 *   In that case, depending on the information of the .xtls, terminal and non-terminal
 *   values are pushed/pop from a semantic stack.
 *   
 *   Furthermore, in case of error recovery, values of the stack can be pop out
 *   depending if the last recognized element is a terminal or a non-terminal.
 * 
 *  This class is generated - please do not edit it 
 */
public class AnalyzerProcessor<B extends LexerBuffer,D>
  implements AnalyzerListener<RuleEnum,B,TerminalEnum,NonTerminalEnum,ProductionEnum> {
          
  private final GrammarEvaluator grammarEvaluator;
  private final TerminalEvaluator<? super D> terminalEvaluator;
  private final DataViewer<? super B,? extends D> dataViewer;
  private final SemanticStack stack;
  
  protected AnalyzerProcessor(TerminalEvaluator<? super D> terminalEvaluator, GrammarEvaluator grammarEvaluator, DataViewer<? super B,? extends D> dataViewer, SemanticStack stack) {
    this.terminalEvaluator=terminalEvaluator;
    this.grammarEvaluator=grammarEvaluator;
    this.dataViewer=dataViewer;
    this.stack=stack;
  }
  
  /** Creates a tools listener that redirect accept/shift/reduce and comment to the terminal Evaluator
      and the grammar evaluator..
      This constructor allows to share the same stack between more
      than one parser processor.
      @param terminalEvaluator the terminal evaluator.
      @param grammarEvaluator the grammar evaluator.
      @param stack the stack used by the processor
   */
  public static <B extends LexerBuffer,D> AnalyzerProcessor<B,D>
    createAnalyzerProcessor(TerminalEvaluator<? super D> terminalEvaluator, GrammarEvaluator grammarEvaluator, DataViewer<? super B,? extends D> dataViewer, SemanticStack stack) {
    
    return new AnalyzerProcessor<B,D>(terminalEvaluator,grammarEvaluator,dataViewer,stack);
  }
  
  public void comment(RuleEnum rule, B buffer) {
    D data;
    switch(rule) {           case comment:
            data=dataViewer.view(buffer);
            terminalEvaluator.comment(data);
            return;
              default:
    }
    throw new AssertionError("unknown rule "+rule);
  }
 
   public void shift(TerminalEnum terminal, RuleEnum rule, B buffer) {
     D data;
     switch(terminal) {      case assign: {
                       return;
           }
                 case dot: {
                       return;
           }
                 case colon: {
                       return;
           }
                 case comma: {
                       return;
           }
                 case semicolon: {
                       return;
           }
                 case eol: {
                       return;
           }
                 case lpar: {
                       return;
           }
                 case rpar: {
                       return;
           }
                 case lcurl: {
                       return;
           }
                 case rcurl: {
                       return;
           }
                 case add: {
                       return;
           }
                 case sub: {
                       return;
           }
                 case mul: {
                       return;
           }
                 case div: {
                       return;
           }
                 case rem: {
                       return;
           }
                 case eq: {
                       return;
           }
                 case ne: {
                       return;
           }
                 case lt: {
                       return;
           }
                 case le: {
                       return;
           }
                 case gt: {
                       return;
           }
                 case ge: {
                       return;
           }
                 case var: {
                       return;
           }
                 case function: {
                       return;
           }
                 case _if: {
                       return;
           }
                 case _else: {
                       return;
           }
                 case _return: {
                       return;
           }
                 case text: {
         data=dataViewer.view(buffer);
                                  fr.umlv.smalljs.ast.Literal<String> text=terminalEvaluator.text(data);
                                      stack.push_Object(text);
                                 return;
           }
                 case integer: {
         data=dataViewer.view(buffer);
                                  fr.umlv.smalljs.ast.Literal<Integer> integer=terminalEvaluator.integer(data);
                                      stack.push_Object(integer);
                                 return;
           }
                 case id: {
         data=dataViewer.view(buffer);
                                  fr.umlv.smalljs.ast.Literal<String> id=terminalEvaluator.id(data);
                                      stack.push_Object(id);
                                 return;
           }
                 case __eof__: {
                       return;
           }
                 }
     throw new AssertionError("unknown terminal "+terminal);
   }
    
    
    @SuppressWarnings("unchecked")
    public void reduce(ProductionEnum production) {
      switch(production) {           case instr_star_0_empty: { // STAR_EMPTY
                            stack.push_Object(new java.util.ArrayList<Object>());
                  
          }
          return;
                    case instr_star_0_rec: { // STAR_RECURSIVE_LEFT
                            
                    Expr instr=(Expr)stack.pop_Object();
                    List<Expr> instr_star_0=(List<Expr>)stack.pop_Object();
                     instr_star_0.add(instr);
                     stack.push_Object(instr_star_0);
                       
          }
          return;
                    case script: { // not synthetic
                                 List<Expr> instr_star_0=(List<Expr>)stack.pop_Object();
                                           grammarEvaluator.script(instr_star_0);
                      
          }
          return;
                    case instr_star_1_empty: { // STAR_EMPTY
                            stack.push_Object(new java.util.ArrayList<Object>());
                  
          }
          return;
                    case instr_star_1_rec: { // STAR_RECURSIVE_LEFT
                            
                    Expr instr=(Expr)stack.pop_Object();
                    List<Expr> instr_star_1=(List<Expr>)stack.pop_Object();
                     instr_star_1.add(instr);
                     stack.push_Object(instr_star_1);
                       
          }
          return;
                    case block: { // not synthetic
                                 List<Expr> instr_star_1=(List<Expr>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.block(instr_star_1));
                      
          }
          return;
                    case eoi__eol: { // not synthetic
            
          }
          return;
                    case eoi__semicolon: { // not synthetic
            
          }
          return;
                    case instr_expr: { // not synthetic
                                 Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.instr_expr(expr));
                      
          }
          return;
                    case instr_var_decl: { // not synthetic
                                 Expr expr=(Expr)stack.pop_Object();
                                          Literal<String> id=(Literal<String>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.instr_var_decl(id,expr));
                      
          }
          return;
                    case instr_var_assign: { // not synthetic
                                 Expr expr=(Expr)stack.pop_Object();
                                          Literal<String> id=(Literal<String>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.instr_var_assign(id,expr));
                      
          }
          return;
                    case instr_field_assign: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Literal<String> id=(Literal<String>)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.instr_field_assign(expr,id,expr2));
                      
          }
          return;
                    case instr_if: { // not synthetic
                                 Block block2=(Block)stack.pop_Object();
                                          Block block=(Block)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.instr_if(expr,block,block2));
                      
          }
          return;
                    case instr_return: { // not synthetic
                                 Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.instr_return(expr));
                      
          }
          return;
                    case expr_star_2_element: { // STAR_SINGLETON
                             java.util.ArrayList<Expr> list=
                     new java.util.ArrayList<Expr>();
                   list.add((Expr)stack.pop_Object());
                   stack.push_Object(list);
                    
          }
          return;
                    case expr_star_2_rec: { // STAR_RECURSIVE_LEFT
                            
                    Expr expr=(Expr)stack.pop_Object();
                    List<Expr> expr_star_2_sub=(List<Expr>)stack.pop_Object();
                     expr_star_2_sub.add(expr);
                     stack.push_Object(expr_star_2_sub);
                       
          }
          return;
                    case expr_star_2_empty: { // STAR_EMPTY
                            stack.push_Object(new java.util.ArrayList<Object>());
                  
          }
          return;
                    case expr_star_2_through: { // STAR_PASS_THROUGH
            
          }
          return;
                    case args: { // not synthetic
                                 List<Expr> expr_star_2=(List<Expr>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.args(expr_star_2));
                      
          }
          return;
                    case eol_optional_3_empty: { // OPTIONAL_EMPTY
            
          }
          return;
                    case eol_optional_3_eol: { // OPTIONAL_SINGLETON
            
          }
          return;
                    case init: { // not synthetic
                                 Expr expr=(Expr)stack.pop_Object();
                                          Literal<String> id=(Literal<String>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.init(id,expr));
                      
          }
          return;
                    case expr_numeric: { // not synthetic
                                 Literal<Integer> integer=(Literal<Integer>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_numeric(integer));
                      
          }
          return;
                    case expr_text: { // not synthetic
                                 Literal<String> text=(Literal<String>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_text(text));
                      
          }
          return;
                    case expr_parens: { // not synthetic
                                 Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_parens(expr));
                      
          }
          return;
                    case expr_id: { // not synthetic
                                 Literal<String> id=(Literal<String>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_id(id));
                      
          }
          return;
                    case id_optional_4_empty: { // OPTIONAL_EMPTY
                              stack.push_Object(null);
                    
          }
          return;
                    case id_optional_4_id: { // OPTIONAL_SINGLETON
            
          }
          return;
                    case id_star_5_element: { // STAR_SINGLETON
                             java.util.ArrayList<Literal<String>> list=
                     new java.util.ArrayList<Literal<String>>();
                   list.add((Literal<String>)stack.pop_Object());
                   stack.push_Object(list);
                    
          }
          return;
                    case id_star_5_rec: { // STAR_RECURSIVE_LEFT
                            
                    Literal<String> id=(Literal<String>)stack.pop_Object();
                    List<Literal<String>> id_star_5_sub=(List<Literal<String>>)stack.pop_Object();
                     id_star_5_sub.add(id);
                     stack.push_Object(id_star_5_sub);
                       
          }
          return;
                    case id_star_5_empty: { // STAR_EMPTY
                            stack.push_Object(new java.util.ArrayList<Object>());
                  
          }
          return;
                    case id_star_5_through: { // STAR_PASS_THROUGH
            
          }
          return;
                    case expr_function_creation: { // not synthetic
                                 Block block=(Block)stack.pop_Object();
                                          List<Literal<String>> id_star_5=(List<Literal<String>>)stack.pop_Object();
                                          Literal<String> id_optional_4=(Literal<String>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_function_creation(id_optional_4,id_star_5,block));
                      
          }
          return;
                    case expr_apply: { // not synthetic
                                 List<Expr> args=(List<Expr>)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_apply(expr,args));
                      
          }
          return;
                    case init_star_6_element: { // STAR_SINGLETON
                             java.util.ArrayList<Expr[]> list=
                     new java.util.ArrayList<Expr[]>();
                   list.add((Expr[])stack.pop_Object());
                   stack.push_Object(list);
                    
          }
          return;
                    case init_star_6_rec: { // STAR_RECURSIVE_LEFT
                            
                    Expr[] init=(Expr[])stack.pop_Object();
                    List<Expr[]> init_star_6_sub=(List<Expr[]>)stack.pop_Object();
                     init_star_6_sub.add(init);
                     stack.push_Object(init_star_6_sub);
                       
          }
          return;
                    case init_star_6_empty: { // STAR_EMPTY
                            stack.push_Object(new java.util.ArrayList<Object>());
                  
          }
          return;
                    case init_star_6_through: { // STAR_PASS_THROUGH
            
          }
          return;
                    case expr_new: { // not synthetic
                                 List<Expr[]> init_star_6=(List<Expr[]>)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_new(init_star_6));
                      
          }
          return;
                    case expr_field_access: { // not synthetic
                                 Literal<String> id=(Literal<String>)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_field_access(expr,id));
                      
          }
          return;
                    case expr_method_call: { // not synthetic
                                 List<Expr> args=(List<Expr>)stack.pop_Object();
                                          Literal<String> id=(Literal<String>)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_method_call(expr,id,args));
                      
          }
          return;
                    case expr_mul: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_mul(expr,expr2));
                      
          }
          return;
                    case expr_div: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_div(expr,expr2));
                      
          }
          return;
                    case expr_rem: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_rem(expr,expr2));
                      
          }
          return;
                    case expr_add: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_add(expr,expr2));
                      
          }
          return;
                    case expr_sub: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_sub(expr,expr2));
                      
          }
          return;
                    case expr_eq: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_eq(expr,expr2));
                      
          }
          return;
                    case expr_ne: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_ne(expr,expr2));
                      
          }
          return;
                    case expr_lt: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_lt(expr,expr2));
                      
          }
          return;
                    case expr_le: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_le(expr,expr2));
                      
          }
          return;
                    case expr_gt: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_gt(expr,expr2));
                      
          }
          return;
                    case expr_ge: { // not synthetic
                                 Expr expr2=(Expr)stack.pop_Object();
                                          Expr expr=(Expr)stack.pop_Object();
                                                stack.push_Object(grammarEvaluator.expr_ge(expr,expr2));
                      
          }
          return;
                    default:
             throw new AssertionError("unknown production "+production);
       }
    }

     public void accept(NonTerminalEnum nonterminal) {
       switch(nonterminal) {            case script:
             grammarEvaluator.acceptScript();
             return;
                     default:
       }
        throw new AssertionError("unknown start nonterminal "+nonterminal);
     }

      public void popTerminalOnError(TerminalEnum terminal) {
        switch(terminal) {             case assign:
              
              return;
                         case dot:
              
              return;
                         case colon:
              
              return;
                         case comma:
              
              return;
                         case semicolon:
              
              return;
                         case eol:
              
              return;
                         case lpar:
              
              return;
                         case rpar:
              
              return;
                         case lcurl:
              
              return;
                         case rcurl:
              
              return;
                         case add:
              
              return;
                         case sub:
              
              return;
                         case mul:
              
              return;
                         case div:
              
              return;
                         case rem:
              
              return;
                         case eq:
              
              return;
                         case ne:
              
              return;
                         case lt:
              
              return;
                         case le:
              
              return;
                         case gt:
              
              return;
                         case ge:
              
              return;
                         case var:
              
              return;
                         case function:
              
              return;
                         case _if:
              
              return;
                         case _else:
              
              return;
                         case _return:
              
              return;
                         case text:
              stack.pop_Object();
              return;
                         case integer:
              stack.pop_Object();
              return;
                         case id:
              stack.pop_Object();
              return;
                         case __eof__:
              
              return;
                     }
        throw new AssertionError("unknown terminal "+terminal);
      }
 
      public void popNonTerminalOnError(NonTerminalEnum nonTerminal) {
        switch(nonTerminal) {             case script:
              
              return;
                         case block:
              stack.pop_Object();
              return;
                         case eoi:
              
              return;
                         case instr:
              stack.pop_Object();
              return;
                         case args:
              stack.pop_Object();
              return;
                         case init:
              stack.pop_Object();
              return;
                         case expr:
              stack.pop_Object();
              return;
                         case instr_star_0:
              stack.pop_Object();
              return;
                         case instr_star_1:
              stack.pop_Object();
              return;
                         case expr_star_2:
              stack.pop_Object();
              return;
                         case expr_star_2_sub:
              stack.pop_Object();
              return;
                         case eol_optional_3:
              
              return;
                         case id_optional_4:
              stack.pop_Object();
              return;
                         case id_star_5:
              stack.pop_Object();
              return;
                         case id_star_5_sub:
              stack.pop_Object();
              return;
                         case init_star_6:
              stack.pop_Object();
              return;
                         case init_star_6_sub:
              stack.pop_Object();
              return;
                     }
        throw new AssertionError("unknown nonterminal "+nonTerminal);
      }
}