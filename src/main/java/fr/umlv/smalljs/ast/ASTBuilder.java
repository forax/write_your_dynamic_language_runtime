package fr.umlv.smalljs.ast;

import static java.util.stream.Collectors.toMap;

import fr.umlv.smalljs.grammar.antlr.ECMAScriptParser;
import fr.umlv.smalljs.grammar.antlr.ECMAScriptVisitor;
import fr.umlv.smalljs.rt.JSObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public final class ASTBuilder implements ECMAScriptVisitor<Expr> {

  private ASTBuilder() {}

  public static Script createScript(String code) {
    var input = CharStreams.fromString(code);
    var lexer = new fr.umlv.smalljs.grammar.antlr.ECMAScriptLexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new ECMAScriptParser(tokens);
    var tree = parser.program();
    var visitor = new ASTBuilder();
    var body = (Expr.Block) tree.accept(visitor);
    return new Script(body);
  }

  private int lineNumber(Token token) {
    return token.getLine();
  }
  private int lineNumber(ParserRuleContext ctx) {
    return lineNumber(ctx.getStart());
  }

  private UnsupportedOperationException unsupported(String feature, ParserRuleContext ctx) {
    return new UnsupportedOperationException("unsupported syntax '" + feature + "' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitProgram(ECMAScriptParser.ProgramContext ctx) {
    return ctx.sourceElements().accept(this);
  }

  @Override
  public Expr visitSourceElements(ECMAScriptParser.SourceElementsContext ctx) {
    var instrs = ctx.sourceElement().stream().map(e -> e.accept(this)).toList();
    return new Expr.Block(instrs, lineNumber(ctx));
  }

  @Override
  public Expr visitSourceElement(ECMAScriptParser.SourceElementContext ctx) {
    return ctx.children.getFirst().accept(this);
  }

  @Override
  public Expr visitStatement(ECMAScriptParser.StatementContext ctx) {
    return ctx.children.getFirst().accept(this);
  }

  @Override
  public Expr visitBlock(ECMAScriptParser.BlockContext ctx) {
    var statementList = ctx.statementList();
    if (statementList == null) {
      return new Expr.Block(List.of(), lineNumber(ctx));
    }
    var statements = statementList.statement().stream()
        .map(s -> s.accept(this))
        .filter(Objects::nonNull)  // remove empty statement
        .toList();
    return new Expr.Block(statements, lineNumber(ctx));
  }
  @Override
  public Expr visitStatementList(ECMAScriptParser.StatementListContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitVariableStatement(ECMAScriptParser.VariableStatementContext ctx) {
    var vars = ctx.variableDeclarationList().variableDeclaration().stream()
        .<Expr>map(v -> new Expr.VarAssignment(v.Identifier().getText(), v.initialiser().accept(this), true, lineNumber(v)))
        .toList();
    if (vars.size() == 1) {
      return vars.getFirst();
    }
    return new Expr.Block(vars, lineNumber(ctx));
  }
  @Override
  public Expr visitVariableDeclarationList(ECMAScriptParser.VariableDeclarationListContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitVariableDeclaration(ECMAScriptParser.VariableDeclarationContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitInitialiser(ECMAScriptParser.InitialiserContext ctx) {
    return ctx.singleExpression().accept(this);
  }

  @Override
  public Expr visitEmptyStatement_(ECMAScriptParser.EmptyStatement_Context ctx) {
    return null;
  }

  @Override
  public Expr visitExpressionStatement(ECMAScriptParser.ExpressionStatementContext ctx) {
    return ctx.expressionSequence().accept(this);
  }

  private static Expr.Block asBlock(Expr expr) {
    return expr instanceof Expr.Block block ? block : new Expr.Block(List.of(expr), expr.lineNumber());
  }

  @Override
  public Expr visitIfStatement(ECMAScriptParser.IfStatementContext ctx) {
    var condition = ctx.expressionSequence().accept(this);
    var statement = ctx.statement();
    var trueBlock = asBlock(statement.getFirst().accept(this));
    if (statement.size() == 1) {
      return new Expr.If(condition, trueBlock, new Expr.Block(List.of(), -1), lineNumber(ctx));
    }
    var falseBlock = asBlock(statement.get(1).accept(this));
    return new Expr.If(condition, trueBlock, falseBlock, lineNumber(ctx));
  }

  @Override
  public Expr visitDoStatement(ECMAScriptParser.DoStatementContext ctx) {
    throw unsupported("do ... while", ctx);
  }

  @Override
  public Expr visitWhileStatement(ECMAScriptParser.WhileStatementContext ctx) {
    throw unsupported("while", ctx);
  }

  @Override
  public Expr visitForStatement(ECMAScriptParser.ForStatementContext ctx) {
    throw unsupported("for", ctx);
  }
  @Override
  public Expr visitForVarStatement(ECMAScriptParser.ForVarStatementContext ctx) {
    throw unsupported("for", ctx);
  }
  @Override
  public Expr visitForInStatement(ECMAScriptParser.ForInStatementContext ctx) {
    throw unsupported("for", ctx);
  }
  @Override
  public Expr visitForVarInStatement(ECMAScriptParser.ForVarInStatementContext ctx) {
    throw unsupported("for", ctx);
  }

  @Override
  public Expr visitContinueStatement(ECMAScriptParser.ContinueStatementContext ctx) {
    throw unsupported("continue", ctx);
  }

  @Override
  public Expr visitBreakStatement(ECMAScriptParser.BreakStatementContext ctx) {
    throw unsupported("break", ctx);
  }

  @Override
  public Expr visitReturnStatement(ECMAScriptParser.ReturnStatementContext ctx) {
    var expressionSequence = ctx.expressionSequence();
    if (expressionSequence == null) {
      return new Expr.Return(new Expr.Literal(JSObject.UNDEFINED, lineNumber(ctx)), lineNumber(ctx));
    }
    var expr = expressionSequence.accept(this);
    return new Expr.Return(expr, lineNumber(ctx));
  }

  @Override
  public Expr visitWithStatement(ECMAScriptParser.WithStatementContext ctx) {
    throw unsupported("with", ctx);
  }

  @Override
  public Expr visitSwitchStatement(ECMAScriptParser.SwitchStatementContext ctx) {
    throw unsupported("switc", ctx);
  }
  @Override
  public Expr visitCaseBlock(ECMAScriptParser.CaseBlockContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitCaseClauses(ECMAScriptParser.CaseClausesContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitCaseClause(ECMAScriptParser.CaseClauseContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitDefaultClause(ECMAScriptParser.DefaultClauseContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitLabelledStatement(ECMAScriptParser.LabelledStatementContext ctx) {
    throw unsupported("label", ctx);
  }

  @Override
  public Expr visitThrowStatement(ECMAScriptParser.ThrowStatementContext ctx) {
    throw unsupported("throw", ctx);
  }

  @Override
  public Expr visitTryStatement(ECMAScriptParser.TryStatementContext ctx) {
    throw unsupported("try", ctx);
  }
  @Override
  public Expr visitCatchProduction(ECMAScriptParser.CatchProductionContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitFinallyProduction(ECMAScriptParser.FinallyProductionContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitDebuggerStatement(ECMAScriptParser.DebuggerStatementContext ctx) {
    throw unsupported("debugger", ctx);
  }

  private List<String> formalParameterList(ECMAScriptParser.FormalParameterListContext ctx) {
    if (ctx == null) {
      return List.of();
    }
    return ctx.Identifier().stream().map(ParseTree::getText).toList();
  }

  @Override
  public Expr visitFunctionDeclaration(ECMAScriptParser.FunctionDeclarationContext ctx) {
    var name = ctx.Identifier() instanceof TerminalNode id ? id.getText() : null;
    if (name == null) {
      throw unsupported("unnamed function statement", ctx);
    }
    var parameters = formalParameterList(ctx.formalParameterList());
    var body = (Expr.Block) ctx.functionBody().accept(this);
    return new Expr.Fun(name, parameters, true, body, lineNumber(ctx));
  }
  @Override
  public Expr visitFormalParameterList(ECMAScriptParser.FormalParameterListContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitFunctionBody(ECMAScriptParser.FunctionBodyContext ctx) {
    var sourceElements = ctx.sourceElements();
    if (sourceElements == null) {
      return new Expr.Block(List.of(), lineNumber(ctx));
    }
    return sourceElements.accept(this);
  }

  @Override
  public Expr visitArrayLiteral(ECMAScriptParser.ArrayLiteralContext ctx) {
    throw unsupported("array literal", ctx);
  }
  @Override
  public Expr visitElementList(ECMAScriptParser.ElementListContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitElision(ECMAScriptParser.ElisionContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitObjectLiteral(ECMAScriptParser.ObjectLiteralContext ctx) {
    var propertyNameAndValueList = ctx.propertyNameAndValueList();
    if (propertyNameAndValueList == null) {
      return new Expr.ObjectLiteral(new LinkedHashMap<>(), lineNumber(ctx));
    }
    var initMap = propertyNameAndValueList.propertyAssignment().stream()
        .map(prop -> {
          if (prop instanceof ECMAScriptParser.PropertyExpressionAssignmentContext propAssignment) {
            return propAssignment;
          }
          throw unsupported("getter or setter", ctx);
        })
        .collect(toMap(p -> p.propertyName().getText(),
            p -> p.singleExpression().accept(this),
            (_, _) -> { throw new AssertionError(); },
            LinkedHashMap<String, Expr>::new));
    return new Expr.ObjectLiteral(initMap, lineNumber(ctx));
  }
  @Override
  public Expr visitPropertyNameAndValueList(ECMAScriptParser.PropertyNameAndValueListContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitPropertyExpressionAssignment(ECMAScriptParser.PropertyExpressionAssignmentContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitPropertyGetter(ECMAScriptParser.PropertyGetterContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitPropertySetter(ECMAScriptParser.PropertySetterContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitPropertyName(ECMAScriptParser.PropertyNameContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitPropertySetParameterList(ECMAScriptParser.PropertySetParameterListContext ctx) {
    throw new AssertionError();
  }

  private List<Expr> arguments(ECMAScriptParser.ArgumentsContext ctx) {
    var argumentList = ctx.argumentList();
    if (argumentList == null) {
      return List.of();
    }
    return argumentList.singleExpression().stream()
        .map(arg -> arg.accept(this))
        .toList();
  }
  @Override
  public Expr visitArguments(ECMAScriptParser.ArgumentsContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitArgumentList(ECMAScriptParser.ArgumentListContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitExpressionSequence(ECMAScriptParser.ExpressionSequenceContext ctx) {
    if (ctx.singleExpression().size() != 1) {
      throw unsupported("comma separated expression", ctx);
    }
    return ctx.singleExpression().getFirst().accept(this);
  }

  @Override
  public Expr visitTernaryExpression(ECMAScriptParser.TernaryExpressionContext ctx) {
    throw unsupported("ternary expression", ctx);
  }

  @Override
  public Expr visitLogicalAndExpression(ECMAScriptParser.LogicalAndExpressionContext ctx) {
    throw unsupported("logical and", ctx);
  }

  @Override
  public Expr visitPreIncrementExpression(ECMAScriptParser.PreIncrementExpressionContext ctx) {
    throw unsupported("pre-increment", ctx);
  }

  @Override
  public Expr visitObjectLiteralExpression(ECMAScriptParser.ObjectLiteralExpressionContext ctx) {
    return ctx.objectLiteral().accept(this);
  }

  @Override
  public Expr visitInExpression(ECMAScriptParser.InExpressionContext ctx) {
    throw unsupported("in expression", ctx);
  }

  @Override
  public Expr visitLogicalOrExpression(ECMAScriptParser.LogicalOrExpressionContext ctx) {
    throw unsupported("logical or", ctx);
  }

  @Override
  public Expr visitNotExpression(ECMAScriptParser.NotExpressionContext ctx) {
    throw unsupported("logical not", ctx);
  }

  @Override
  public Expr visitPreDecreaseExpression(ECMAScriptParser.PreDecreaseExpressionContext ctx) {
    throw unsupported("pre-decrement", ctx);
  }

  @Override
  public Expr visitArgumentsExpression(ECMAScriptParser.ArgumentsExpressionContext ctx) {
    var left = ctx.singleExpression();
    if (left instanceof ECMAScriptParser.MemberDotExpressionContext field) {
      var receiver = field.singleExpression().accept(this);
      var name = identifierName(field.identifierName());
      return new Expr.MethodCall(receiver, name, arguments(ctx.arguments()), lineNumber(ctx.arguments()));
    }
    var qualifier = left.accept(this);
    return new Expr.Call(qualifier, arguments(ctx.arguments()), lineNumber(ctx.arguments()));
  }

  @Override
  public Expr visitThisExpression(ECMAScriptParser.ThisExpressionContext ctx) {
    return new Expr.Identifier("this", lineNumber(ctx));
  }

  @Override
  public Expr visitFunctionExpression(ECMAScriptParser.FunctionExpressionContext ctx) {
    var name = ctx.Identifier() instanceof TerminalNode id ? id.getText() : "anonymous";
    var parameters = formalParameterList(ctx.formalParameterList());
    var body = (Expr.Block) ctx.functionBody().sourceElements().accept(this);
    return new Expr.Fun(name, parameters, false, body, lineNumber(ctx));
  }

  @Override
  public Expr visitUnaryMinusExpression(ECMAScriptParser.UnaryMinusExpressionContext ctx) {
    throw unsupported("unary minus", ctx);
  }

  @Override
  public Expr visitAssignmentExpression(ECMAScriptParser.AssignmentExpressionContext ctx) {
    var expr = ctx.singleExpression(1).accept(this);
    var left = ctx.singleExpression(0);
    if (left instanceof ECMAScriptParser.IdentifierExpressionContext identifierExpression) {
      var name = identifierExpression.Identifier().getText();
      return new Expr.VarAssignment(name, expr, false, lineNumber(ctx.Assign().getSymbol()));
    }
    if (left instanceof ECMAScriptParser.MemberDotExpressionContext field) {
      var receiver = field.singleExpression().accept(this);
      var name = identifierName(field.identifierName());
      return new Expr.FieldAssignment(receiver, name, expr, lineNumber(field.Dot().getSymbol()));
    }
    System.err.println("left " + left.getClass());
    throw unsupported("assignment", ctx);

  }

  @Override
  public Expr visitPostDecreaseExpression(ECMAScriptParser.PostDecreaseExpressionContext ctx) {
    throw unsupported("post-decrement", ctx);
  }

  @Override
  public Expr visitTypeofExpression(ECMAScriptParser.TypeofExpressionContext ctx) {
    throw unsupported("typeof", ctx);
  }

  @Override
  public Expr visitInstanceofExpression(ECMAScriptParser.InstanceofExpressionContext ctx) {
    throw unsupported("instanceof", ctx);
  }

  @Override
  public Expr visitUnaryPlusExpression(ECMAScriptParser.UnaryPlusExpressionContext ctx) {
    throw unsupported("unary plus", ctx);
  }

  @Override
  public Expr visitDeleteExpression(ECMAScriptParser.DeleteExpressionContext ctx) {
    throw unsupported("delete", ctx);
  }

  private Expr binOp(String op, Expr left, Expr right, int lineNumber) {
    return new Expr.Call(new Expr.Identifier(op, lineNumber), List.of(left, right), lineNumber);
  }

  @Override
  public Expr visitEqualityExpression(ECMAScriptParser.EqualityExpressionContext ctx) {
    var op = ctx.Equals() != null ? "==" : ctx.NotEquals() != null ? "!=" : null;
    if (op == null) {
      throw unsupported("=== or !==", ctx);
    }
    var left = ctx.singleExpression(0).accept(this);
    var right = ctx.singleExpression(1).accept(this);
    return binOp(op, left, right, lineNumber(ctx));
  }

  @Override
  public Expr visitBitXOrExpression(ECMAScriptParser.BitXOrExpressionContext ctx) {
    throw unsupported("bit xor", ctx);
  }

  @Override
  public Expr visitMultiplicativeExpression(ECMAScriptParser.MultiplicativeExpressionContext ctx) {
    var op = ctx.Multiply() != null ? "*" : ctx.Divide() != null ? "/" : "%";
    var left = ctx.singleExpression(0).accept(this);
    var right = ctx.singleExpression(1).accept(this);
    return binOp(op, left, right, lineNumber(ctx));
  }

  @Override
  public Expr visitBitShiftExpression(ECMAScriptParser.BitShiftExpressionContext ctx) {
    throw unsupported("bit shift", ctx);
  }

  @Override
  public Expr visitParenthesizedExpression(ECMAScriptParser.ParenthesizedExpressionContext ctx) {
    return ctx.expressionSequence().accept(this);
  }

  @Override
  public Expr visitAdditiveExpression(ECMAScriptParser.AdditiveExpressionContext ctx) {
    var op = ctx.Plus() != null ? "+" : "-" ;
    var left = ctx.singleExpression(0).accept(this);
    var right = ctx.singleExpression(1).accept(this);
    return binOp(op, left, right, lineNumber(ctx));
  }

  @Override
  public Expr visitRelationalExpression(ECMAScriptParser.RelationalExpressionContext ctx) {
    var op = ctx.LessThan() != null ? "<" : ctx.LessThanEquals() != null ? "<=" : ctx.MoreThan() != null ? "<" : "<=";
    var left = ctx.singleExpression(0).accept(this);
    var right = ctx.singleExpression(1).accept(this);
    return binOp(op, left, right, lineNumber(ctx));
  }

  @Override
  public Expr visitPostIncrementExpression(ECMAScriptParser.PostIncrementExpressionContext ctx) {
    throw unsupported("post increment", ctx);
  }

  @Override
  public Expr visitBitNotExpression(ECMAScriptParser.BitNotExpressionContext ctx) {
    throw unsupported("bit not", ctx);
  }

  @Override
  public Expr visitNewExpression(ECMAScriptParser.NewExpressionContext ctx) {
    throw unsupported("new", ctx);
  }

  @Override
  public Expr visitLiteralExpression(ECMAScriptParser.LiteralExpressionContext ctx) {
    return ctx.literal().accept(this);
  }

  @Override
  public Expr visitArrayLiteralExpression(ECMAScriptParser.ArrayLiteralExpressionContext ctx) {
    throw unsupported("array literal", ctx);
  }

  private String identifierName(ECMAScriptParser.IdentifierNameContext ctx) {
    if (ctx.Identifier() != null) {
      return ctx.Identifier().getText();
    }
    throw unsupported("keyword identifier", ctx);
  }

  @Override
  public Expr visitMemberDotExpression(ECMAScriptParser.MemberDotExpressionContext ctx) {
    var receiver = ctx.singleExpression().accept(this);
    var name = identifierName(ctx.identifierName());
    return new Expr.FieldAccess(receiver, name, lineNumber(ctx));
  }

  @Override
  public Expr visitMemberIndexExpression(ECMAScriptParser.MemberIndexExpressionContext ctx) {
    throw unsupported("indexed expression", ctx);
  }

  @Override
  public Expr visitIdentifierExpression(ECMAScriptParser.IdentifierExpressionContext ctx) {
    var name = ctx.Identifier().getText();
    if (name.equals("undefined")) {
      return new Expr.Literal(JSObject.UNDEFINED, lineNumber(ctx));
    }
    return new Expr.Identifier(name, lineNumber(ctx));
  }

  @Override
  public Expr visitBitAndExpression(ECMAScriptParser.BitAndExpressionContext ctx) {
    throw unsupported("bit and", ctx);
  }

  @Override
  public Expr visitBitOrExpression(ECMAScriptParser.BitOrExpressionContext ctx) {
    throw unsupported("bit or", ctx);
  }

  @Override
  public Expr visitAssignmentOperatorExpression(ECMAScriptParser.AssignmentOperatorExpressionContext ctx) {
    throw unsupported("assignment operator'", ctx);
  }

  @Override
  public Expr visitVoidExpression(ECMAScriptParser.VoidExpressionContext ctx) {
    throw unsupported("void operator", ctx);
  }

  @Override
  public Expr visitAssignmentOperator(ECMAScriptParser.AssignmentOperatorContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitLiteral(ECMAScriptParser.LiteralContext ctx) {
    if (ctx.numericLiteral() != null) {
      return ctx.numericLiteral().accept(this);
    }
    if (ctx.StringLiteral() != null) {
      var text = ctx.StringLiteral().getText();
      return new Expr.Literal(text.substring(1, text.length() - 1), lineNumber(ctx));
    }
    throw unsupported("literal", ctx);
  }

  @Override
  public Expr visitNumericLiteral(ECMAScriptParser.NumericLiteralContext ctx) {
    if (ctx.DecimalLiteral() == null) {
      throw unsupported("numeric literal", ctx);
    }
    var text = ctx.DecimalLiteral().getText();
    return new Expr.Literal(Integer.parseInt(text), lineNumber(ctx));
  }

  @Override
  public Expr visitIdentifierName(ECMAScriptParser.IdentifierNameContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitReservedWord(ECMAScriptParser.ReservedWordContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitKeyword(ECMAScriptParser.KeywordContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitFutureReservedWord(ECMAScriptParser.FutureReservedWordContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitGetter(ECMAScriptParser.GetterContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitSetter(ECMAScriptParser.SetterContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visitEos(ECMAScriptParser.EosContext ctx) {
    throw new AssertionError();
  }
  @Override
  public Expr visitEof(ECMAScriptParser.EofContext ctx) {
    throw new AssertionError();
  }

  @Override
  public Expr visit(ParseTree parseTree) {
    throw new AssertionError(parseTree.getText());
  }

  @Override
  public Expr visitChildren(RuleNode ruleNode) {
    throw new AssertionError();
  }

  @Override
  public Expr visitTerminal(TerminalNode terminalNode) {
    throw new AssertionError();
  }

  @Override
  public Expr visitErrorNode(ErrorNode errorNode) {
    var token = errorNode.getSymbol();
    throw new UnsupportedOperationException("unsupported syntax '" + "error" + "' at " + token.getLine());
  }
}
