package fr.umlv.smalljs.ast;

import java.io.Reader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.umlv.smalljs.ast.Expr.Block;
import fr.umlv.smalljs.ast.Expr.FieldAccess;
import fr.umlv.smalljs.ast.Expr.FieldAssignment;
import fr.umlv.smalljs.ast.Expr.Fun;
import fr.umlv.smalljs.ast.Expr.FunCall;
import fr.umlv.smalljs.ast.Expr.If;
import fr.umlv.smalljs.ast.Expr.Literal;
import fr.umlv.smalljs.ast.Expr.LocalVarAccess;
import fr.umlv.smalljs.ast.Expr.LocalVarAssignment;
import fr.umlv.smalljs.ast.Expr.MethodCall;
import fr.umlv.smalljs.ast.Expr.New;
import fr.umlv.smalljs.ast.Expr.Return;
import fr.umlv.smalljs.grammar.tools.Analyzers;
import fr.umlv.smalljs.grammar.tools.GrammarEvaluator;
import fr.umlv.smalljs.grammar.tools.TerminalEvaluator;
import fr.umlv.tatoo.runtime.buffer.impl.LocationTracker;
import fr.umlv.tatoo.runtime.buffer.impl.ReaderWrapper;

public class ASTBuilder implements GrammarEvaluator {
  private final LocationTracker tracker;

  ASTBuilder(LocationTracker tracker) {
    this.tracker = tracker;
  }

  public static Script createScript(Reader reader) {
    LocationTracker tracker = new LocationTracker();
    ReaderWrapper buffer = new ReaderWrapper(reader, tracker);
    ASTBuilder astBuilder = new ASTBuilder(tracker);
    Analyzers.run(buffer, new TerminalBuilder(tracker), astBuilder, null, null);
    return astBuilder.script;
  }

  static class TerminalBuilder implements TerminalEvaluator<CharSequence> {
    private final LocationTracker tracker;

    TerminalBuilder(LocationTracker tracker) {
      this.tracker = tracker;
    }

    @Override
    public void comment(CharSequence data) {
      // do nothing
    }

    @Override
    public Literal<String> id(CharSequence data) {
      return newToken(data.toString());
    }

    @Override
    public Literal<Integer> integer(CharSequence data) {
      return newToken(Integer.parseInt(data.toString()));
    }

    @Override
    public Literal<String> text(CharSequence data) {
      return newToken(data.subSequence(1, data.length() - 1).toString());
    }

    private <T> Literal<T> newToken(T value) {
      return new Literal<>(value, 1 + tracker.getLineNumber());
    }
  }


  private Script script;

  @Override
  public void acceptScript() {
    // cool
  }
  @Override
  public void script(List<Expr> expr_star) {
    script = new Script(new Block(List.copyOf(expr_star), 1));
  }
  @Override
  public Block block(List<Expr> instr_star) {
    return new Block(List.copyOf(instr_star), 1 + tracker.getLineNumber());
  }

  @Override
  public Expr instr_expr(Expr expr) {
    return expr;
  }
  @Override
  public Expr instr_var_decl(Literal<String> id, Expr expr) {
    return new LocalVarAssignment(id.value(), expr, true, id.lineNumber());
  }
  @Override
  public Expr instr_var_assign(Literal<String> id, Expr expr) {
    return new LocalVarAssignment(id.value(), expr, false, id.lineNumber());
  }
  @Override
  public Expr instr_field_assign(Expr expr, Literal<String> id, Expr expr2) {
    return new FieldAssignment(expr, id.value(), expr2, expr.lineNumber());
  }
  @Override
  public Expr instr_return(Expr expr) {
    return new Return(expr, expr.lineNumber());
  }
  @Override
  public Expr instr_if(Expr expr, Block block, Block block2) {
    return new If(expr, block, block2, expr.lineNumber());
  }
  
  @Override
  public List<Expr> args(List<Expr> expr_star) {
    return List.copyOf(expr_star);
  }
  
  @Override
  public Expr expr_numeric(Literal<Integer> integer) {
    return integer;
  }
  @Override
  public Expr expr_text(Literal<String> text) {
    return text;
  }
  @Override
  public Expr expr_parens(Expr expr) {
    return expr;
  }
  @Override
  public Expr expr_id(Literal<String> id) {
    return new LocalVarAccess(id.value(), id.lineNumber());
  }
  @Override
  public Expr expr_function_creation(Literal<String> id_optional, List<Literal<String>> parameters, Block block) {
    Optional<Literal<String>> optionalName = Optional.ofNullable(id_optional);
    return new Fun(
        optionalName.map(Literal::value),
        parameters.stream().map(Literal::value).toList(),
        block,
        optionalName.map(Literal::lineNumber).orElseGet(() -> parameters.stream().mapToInt(Literal::lineNumber).findFirst().orElse(block.lineNumber()))
        );
  }
  @Override
  public Expr expr_apply(Expr expr, List<Expr> args) {
    return new FunCall(expr, args, expr.lineNumber());
  }
  @Override
  public Expr[] init(Literal<String> id, Expr expr) {
    return new Expr[] { id, expr };
  }
  @Override
  public Expr expr_new(List<Expr[]> init_star) {
    return new New(
        Collections.unmodifiableMap(init_star.stream().collect(Collectors.toMap((Expr[] v) -> v[0].toString(), (Expr[] v) -> v[1], (_1, _2) -> null, LinkedHashMap::new))),
        init_star.stream().mapToInt(x -> x[0].lineNumber()).findFirst().orElse(1 + tracker.getLineNumber()));
  }
  @Override
  public Expr expr_field_access(Expr expr, Literal<String> id) {
    return new FieldAccess(expr, id.value(), expr.lineNumber());
  }
  @Override
  public Expr expr_method_call(Expr expr, Literal<String> id, List<Expr> args) {
    return new MethodCall(expr, id.value(), args, expr.lineNumber());
  }
  
  @Override
  public Expr expr_add(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("+", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_sub(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("-", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_mul(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("*", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_div(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("/", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_rem(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("%", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_eq(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("==", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_ne(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("!=", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_lt(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("<", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_le(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("<=", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_gt(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess(">", lineNumber), List.of(expr, expr2), lineNumber);
  }
  @Override
  public Expr expr_ge(Expr expr, Expr expr2) {
    int lineNumber = expr.lineNumber();
    return new FunCall(new LocalVarAccess("!>=", lineNumber), List.of(expr, expr2), lineNumber);
  }
}
