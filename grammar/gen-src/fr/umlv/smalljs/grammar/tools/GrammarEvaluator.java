package fr.umlv.smalljs.grammar.tools;

import fr.umlv.smalljs.ast.Block;
import fr.umlv.smalljs.ast.Expr;
import fr.umlv.smalljs.ast.Literal;
import java.util.List;

/** 
 *  This class is generated - please do not edit it 
 */
public interface GrammarEvaluator {
  /** This methods is called after the reduction of the non terminal script
   *  by the grammar production script.
   *  <code>script ::= instr_star_0</code>
   */
  public void script(List<Expr> instr_star);
  /** This methods is called after the reduction of the non terminal block
   *  by the grammar production block.
   *  <code>block ::= lcurl instr_star_1 rcurl</code>
   */
  public Block block(List<Expr> instr_star);
  /** This methods is called after the reduction of the non terminal instr
   *  by the grammar production instr_expr.
   *  <code>instr ::= expr eoi</code>
   */
  public Expr instr_expr(Expr expr);
  /** This methods is called after the reduction of the non terminal instr
   *  by the grammar production instr_var_decl.
   *  <code>instr ::= var id assign expr eoi</code>
   */
  public Expr instr_var_decl(Literal<String> id,Expr expr);
  /** This methods is called after the reduction of the non terminal instr
   *  by the grammar production instr_var_assign.
   *  <code>instr ::= id assign expr eoi</code>
   */
  public Expr instr_var_assign(Literal<String> id,Expr expr);
  /** This methods is called after the reduction of the non terminal instr
   *  by the grammar production instr_field_assign.
   *  <code>instr ::= expr dot id assign expr eoi</code>
   */
  public Expr instr_field_assign(Expr expr,Literal<String> id,Expr expr2);
  /** This methods is called after the reduction of the non terminal instr
   *  by the grammar production instr_if.
   *  <code>instr ::= _if expr block _else block</code>
   */
  public Expr instr_if(Expr expr,Block block,Block block2);
  /** This methods is called after the reduction of the non terminal instr
   *  by the grammar production instr_return.
   *  <code>instr ::= _return expr eoi</code>
   */
  public Expr instr_return(Expr expr);
  /** This methods is called after the reduction of the non terminal args
   *  by the grammar production args.
   *  <code>args ::= expr_star_2</code>
   */
  public List<Expr> args(List<Expr> expr_star);
  /** This methods is called after the reduction of the non terminal init
   *  by the grammar production init.
   *  <code>init ::= id colon expr eol_optional_3</code>
   */
  public Expr[] init(Literal<String> id,Expr expr);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_numeric.
   *  <code>expr ::= integer</code>
   */
  public Expr expr_numeric(Literal<Integer> integer);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_text.
   *  <code>expr ::= text</code>
   */
  public Expr expr_text(Literal<String> text);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_parens.
   *  <code>expr ::= lpar expr rpar</code>
   */
  public Expr expr_parens(Expr expr);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_id.
   *  <code>expr ::= id</code>
   */
  public Expr expr_id(Literal<String> id);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_function_creation.
   *  <code>expr ::= function id_optional_4 lpar id_star_5 rpar block</code>
   */
  public Expr expr_function_creation(Literal<String> id_optional,List<Literal<String>> id_star,Block block);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_apply.
   *  <code>expr ::= expr lpar args rpar</code>
   */
  public Expr expr_apply(Expr expr,List<Expr> args);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_new.
   *  <code>expr ::= lcurl init_star_6 rcurl</code>
   */
  public Expr expr_new(List<Expr[]> init_star);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_field_access.
   *  <code>expr ::= expr dot id</code>
   */
  public Expr expr_field_access(Expr expr,Literal<String> id);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_method_call.
   *  <code>expr ::= expr dot id lpar args rpar</code>
   */
  public Expr expr_method_call(Expr expr,Literal<String> id,List<Expr> args);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_mul.
   *  <code>expr ::= expr mul expr</code>
   */
  public Expr expr_mul(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_div.
   *  <code>expr ::= expr div expr</code>
   */
  public Expr expr_div(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_rem.
   *  <code>expr ::= expr rem expr</code>
   */
  public Expr expr_rem(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_add.
   *  <code>expr ::= expr add expr</code>
   */
  public Expr expr_add(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_sub.
   *  <code>expr ::= expr sub expr</code>
   */
  public Expr expr_sub(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_eq.
   *  <code>expr ::= expr eq expr</code>
   */
  public Expr expr_eq(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_ne.
   *  <code>expr ::= expr ne expr</code>
   */
  public Expr expr_ne(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_lt.
   *  <code>expr ::= expr lt expr</code>
   */
  public Expr expr_lt(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_le.
   *  <code>expr ::= expr le expr</code>
   */
  public Expr expr_le(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_gt.
   *  <code>expr ::= expr gt expr</code>
   */
  public Expr expr_gt(Expr expr,Expr expr2);
  /** This methods is called after the reduction of the non terminal expr
   *  by the grammar production expr_ge.
   *  <code>expr ::= expr ge expr</code>
   */
  public Expr expr_ge(Expr expr,Expr expr2);

  public void acceptScript();
}
