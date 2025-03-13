package fr.umlv.smalljs.ast;

import static java.util.stream.Collectors.toMap;

import fr.umlv.smalljs.grammar.antlr.ECMAScriptParser;
import fr.umlv.smalljs.grammar.antlr.ECMAScriptVisitor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public final class ASTBuilder implements ECMAScriptVisitor<Expr> {

  ASTBuilder() {}

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
    //if (ctx.statement() != null) {
    //  return ctx.statement().accept(this);
    //}
    //return ctx.functionDeclaration().accept(this);
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
        .<Expr>map(v -> new Expr.LocalVarAssignment(v.Identifier().getText(), v.initialiser().accept(this), true, lineNumber(v)))
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
    throw new UnsupportedOperationException("unsupported syntax 'do ... while' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitWhileStatement(ECMAScriptParser.WhileStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'while' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitForStatement(ECMAScriptParser.ForStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'for' at " + lineNumber(ctx));
  }
  @Override
  public Expr visitForVarStatement(ECMAScriptParser.ForVarStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'for' at " + lineNumber(ctx));
  }
  @Override
  public Expr visitForInStatement(ECMAScriptParser.ForInStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'for' at " + lineNumber(ctx));
  }
  @Override
  public Expr visitForVarInStatement(ECMAScriptParser.ForVarInStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'for' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitContinueStatement(ECMAScriptParser.ContinueStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'continue' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitBreakStatement(ECMAScriptParser.BreakStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'break' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitReturnStatement(ECMAScriptParser.ReturnStatementContext ctx) {
    var expr = ctx.expressionSequence().accept(this);
    return new Expr.Return(expr, lineNumber(ctx));
  }

  @Override
  public Expr visitWithStatement(ECMAScriptParser.WithStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'with' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitSwitchStatement(ECMAScriptParser.SwitchStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'switch' at " + lineNumber(ctx));
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
    throw new UnsupportedOperationException("unsupported syntax 'label' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitThrowStatement(ECMAScriptParser.ThrowStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'throw' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitTryStatement(ECMAScriptParser.TryStatementContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'try' at " + lineNumber(ctx));
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
    throw new UnsupportedOperationException("unsupported syntax 'debugger' at " + lineNumber(ctx));
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
    var parameters = formalParameterList(ctx.formalParameterList());
    var body = (Expr.Block) ctx.functionBody().accept(this);
    return new Expr.Fun(Optional.ofNullable(name), parameters, body, lineNumber(ctx));
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
    throw new UnsupportedOperationException("unsupported syntax 'array literal' at " + lineNumber(ctx));
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
    var initMap = ctx.propertyNameAndValueList().propertyAssignment().stream()
        .map(prop -> {
          if (prop instanceof ECMAScriptParser.PropertyExpressionAssignmentContext propAssignment) {
            return propAssignment;
          }
          throw new UnsupportedOperationException("unsupported syntax 'getter or setter' at " + lineNumber(ctx));
        })
        .collect(toMap(p -> p.propertyName().getText(),
            p -> p.singleExpression().accept(this),
            (_, _) -> { throw new AssertionError(); },
            LinkedHashMap<String, Expr>::new));

    return new Expr.New(initMap, lineNumber(ctx));
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
      throw new UnsupportedOperationException("unsupported syntax 'comma separated expression' at " + lineNumber(ctx));
    }
    return ctx.singleExpression().getFirst().accept(this);
  }

  @Override
  public Expr visitTernaryExpression(ECMAScriptParser.TernaryExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'ternary expression' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitLogicalAndExpression(ECMAScriptParser.LogicalAndExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'logical and' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitPreIncrementExpression(ECMAScriptParser.PreIncrementExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'pre-increment' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitObjectLiteralExpression(ECMAScriptParser.ObjectLiteralExpressionContext ctx) {
    return ctx.objectLiteral().accept(this);
  }

  @Override
  public Expr visitInExpression(ECMAScriptParser.InExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax `in expression' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitLogicalOrExpression(ECMAScriptParser.LogicalOrExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'logical or' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitNotExpression(ECMAScriptParser.NotExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'logical not' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitPreDecreaseExpression(ECMAScriptParser.PreDecreaseExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'pre-decrement' at " + lineNumber(ctx));
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
    return new Expr.FunCall(qualifier, arguments(ctx.arguments()), lineNumber(ctx.arguments()));
  }

  @Override
  public Expr visitThisExpression(ECMAScriptParser.ThisExpressionContext ctx) {
    return new Expr.LocalVarAccess("this", lineNumber(ctx));
  }

  @Override
  public Expr visitFunctionExpression(ECMAScriptParser.FunctionExpressionContext ctx) {
    var name = ctx.Identifier() instanceof TerminalNode id ? id.getText() : null;
    var parameters = formalParameterList(ctx.formalParameterList());
    var body = (Expr.Block) ctx.functionBody().sourceElements().accept(this);
    return new Expr.Fun(Optional.ofNullable(name), parameters, body, lineNumber(ctx));
  }

  @Override
  public Expr visitUnaryMinusExpression(ECMAScriptParser.UnaryMinusExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'unary minus' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitAssignmentExpression(ECMAScriptParser.AssignmentExpressionContext ctx) {
    var expr = ctx.singleExpression(1).accept(this);
    var left = ctx.singleExpression(0);
    if (left instanceof ECMAScriptParser.LiteralExpressionContext literal) {
      var name = literal.literal().getText();
      return new Expr.LocalVarAssignment(name, expr, false, lineNumber(ctx.Assign().getSymbol()));
    }
    if (left instanceof ECMAScriptParser.MemberDotExpressionContext field) {
      var receiver = field.singleExpression().accept(this);
      var name = identifierName(field.identifierName());
      return new Expr.FieldAssignment(receiver, name, expr, lineNumber(field.Dot().getSymbol()));
    }
    throw new UnsupportedOperationException("unsupported syntax 'assignment' at " + lineNumber(ctx));

  }

  @Override
  public Expr visitPostDecreaseExpression(ECMAScriptParser.PostDecreaseExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'post-decrement' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitTypeofExpression(ECMAScriptParser.TypeofExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'typeof' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitInstanceofExpression(ECMAScriptParser.InstanceofExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'instanceof' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitUnaryPlusExpression(ECMAScriptParser.UnaryPlusExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'unary plus' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitDeleteExpression(ECMAScriptParser.DeleteExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'delete' at " + lineNumber(ctx));
  }

  private Expr binOp(String op, Expr left, Expr right, int lineNumber) {
    return new Expr.FunCall(new Expr.LocalVarAccess(op, lineNumber), List.of(left, right), lineNumber);
  }

  @Override
  public Expr visitEqualityExpression(ECMAScriptParser.EqualityExpressionContext ctx) {
    var op = ctx.Equals() != null ? "==" : ctx.NotEquals() != null ? "!=" : null;
    if (op == null) {
      throw new UnsupportedOperationException("unsupported syntax '=== or !==' at " + lineNumber(ctx));
    }
    var left = ctx.singleExpression(0).accept(this);
    var right = ctx.singleExpression(1).accept(this);
    return binOp(op, left, right, lineNumber(ctx));
  }

  @Override
  public Expr visitBitXOrExpression(ECMAScriptParser.BitXOrExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'bit xor' at " + lineNumber(ctx));
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
    throw new UnsupportedOperationException("unsupported syntax 'bit shift' at " + lineNumber(ctx));
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
    throw new UnsupportedOperationException("unsupported syntax 'post increment' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitBitNotExpression(ECMAScriptParser.BitNotExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'bit not' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitNewExpression(ECMAScriptParser.NewExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'new' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitLiteralExpression(ECMAScriptParser.LiteralExpressionContext ctx) {
    return ctx.literal().accept(this);
  }

  @Override
  public Expr visitArrayLiteralExpression(ECMAScriptParser.ArrayLiteralExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'array literal' at " + lineNumber(ctx));
  }

  private String identifierName(ECMAScriptParser.IdentifierNameContext ctx) {
    if (ctx.Identifier() != null) {
      return ctx.Identifier().getText();
    }
    throw new UnsupportedOperationException("unsupported syntax 'keyword identifier' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitMemberDotExpression(ECMAScriptParser.MemberDotExpressionContext ctx) {
    var receiver = ctx.singleExpression().accept(this);
    var name = identifierName(ctx.identifierName());
    return new Expr.FieldAccess(receiver, name, lineNumber(ctx));
  }

  @Override
  public Expr visitMemberIndexExpression(ECMAScriptParser.MemberIndexExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'indexed expression' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitIdentifierExpression(ECMAScriptParser.IdentifierExpressionContext ctx) {
    var name = ctx.Identifier().getText();
    return new Expr.LocalVarAccess(name, lineNumber(ctx));
  }

  @Override
  public Expr visitBitAndExpression(ECMAScriptParser.BitAndExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'bit and' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitBitOrExpression(ECMAScriptParser.BitOrExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'bit or' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitAssignmentOperatorExpression(ECMAScriptParser.AssignmentOperatorExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'assignment operator' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitVoidExpression(ECMAScriptParser.VoidExpressionContext ctx) {
    throw new UnsupportedOperationException("unsupported syntax 'void operator' at " + lineNumber(ctx));
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
      return new Expr.Literal<String>(text.substring(1, text.length() - 1), lineNumber(ctx));
    }
    throw new UnsupportedOperationException("unsupported syntax 'literal' at " + lineNumber(ctx));
  }

  @Override
  public Expr visitNumericLiteral(ECMAScriptParser.NumericLiteralContext ctx) {
    if (ctx.DecimalLiteral() == null) {
      throw new UnsupportedOperationException("unsupported syntax 'numeric literal' at " + lineNumber(ctx));
    }
    return new Expr.Literal<Integer>(Integer.parseInt(ctx.DecimalLiteral().getText()), lineNumber(ctx));
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
    throw new UnsupportedOperationException("unsupported syntax 'error' at " + lineNumber(errorNode.getSymbol()));
  }
}
