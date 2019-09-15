package fr.umlv.smalljs.astinterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import fr.umlv.smalljs.ast.Block;
import fr.umlv.smalljs.ast.Expr;
import fr.umlv.smalljs.ast.FieldAccess;
import fr.umlv.smalljs.ast.FieldAssignment;
import fr.umlv.smalljs.ast.Fun;
import fr.umlv.smalljs.ast.FunCall;
import fr.umlv.smalljs.ast.If;
import fr.umlv.smalljs.ast.Literal;
import fr.umlv.smalljs.ast.LocalVarAccess;
import fr.umlv.smalljs.ast.LocalVarAssignment;
import fr.umlv.smalljs.ast.MethodCall;
import fr.umlv.smalljs.ast.New;
import fr.umlv.smalljs.ast.Return;
import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.ast.Visitor;
import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;

public class ASTInterpreter {
    private static <T> T as(Object value, Class<T> type, Expr failedExpr) {
        try {
            return type.cast(value);
        } catch(@SuppressWarnings("unused") ClassCastException e) {
            throw new Failure("at line " + failedExpr.getLineNumber() + ", type error " + value + " is not a " + type.getSimpleName());
        }
    }

    static Object visit(Expr expr, JSObject env) {
        return VISITOR.visit(expr, env);
    }

    private static final Visitor<JSObject, Object> VISITOR =
            new Visitor<JSObject, Object>()
                    .when(Block.class, (block, env) -> {
                        throw new UnsupportedOperationException("TODO Block");
                    })
                    .when(Literal.class, (literal, env) -> {
                        throw new UnsupportedOperationException("TODO Literal");
                    })
                    
                    .when(FunCall.class, (funCall, env) -> {
                        throw new UnsupportedOperationException("TODO FunCall");
                    })
                    .when(LocalVarAccess.class, (localVarAccess, env) -> {
                        throw new UnsupportedOperationException("TODO LocalVarAccess");
                    })
                    .when(LocalVarAssignment.class, (localVarAssignment, env) -> {
                        throw new UnsupportedOperationException("TODO LocalVarAssignment");
                    }) 
                    .when(Fun.class, (fun, env) -> {
                        throw new UnsupportedOperationException("TODO Fun");
                    })
                    .when(Return.class, (_return, env) -> {
                        throw new UnsupportedOperationException("TODO Return");
                    })
                    .when(If.class, (_if, env) -> {
                        throw new UnsupportedOperationException("TODO If");
                    })
                    .when(New.class, (_new, env) -> {
                        throw new UnsupportedOperationException("TODO New");
                    })
                    .when(FieldAccess.class, (fieldAccess, env) -> {
                        throw new UnsupportedOperationException("TODO FieldAccess");
                    })
                    .when(FieldAssignment.class, (fieldAssignment, env) -> {
                        throw new UnsupportedOperationException("TODO FieldAssignment");
                    })
                    .when(MethodCall.class, (methodCall, env) -> {
                        throw new UnsupportedOperationException("TODO MethodCall");
                    })
            ;

    @SuppressWarnings("unchecked")
    public static void interpret(Script script, PrintStream outStream) {
        JSObject globalEnv = JSObject.newEnv(null);
        Block body = script.getBody();
        globalEnv.register("global", globalEnv);
        globalEnv.register("print", JSObject.newFunction("print", (self, receiver, args) -> {
            System.err.println("print called with " + Arrays.toString(args));
            outStream.println(Arrays.stream(args).map(Object::toString).collect(Collectors.joining(" ")));
            return UNDEFINED;
        }));
        globalEnv.register("+", JSObject.newFunction("+", (self, receiver, args) -> (Integer) args[0] + (Integer) args[1]));
        globalEnv.register("-", JSObject.newFunction("-", (self, receiver, args) -> (Integer) args[0] - (Integer) args[1]));
        globalEnv.register("/", JSObject.newFunction("/", (self, receiver, args) -> (Integer) args[0] / (Integer) args[1]));
        globalEnv.register("*", JSObject.newFunction("*", (self, receiver, args) -> (Integer) args[0] * (Integer) args[1]));
        globalEnv.register("%", JSObject.newFunction("%", (self, receiver, args) -> (Integer) args[0] * (Integer) args[1]));

        globalEnv.register("==", JSObject.newFunction("==", (self, receiver, args) -> args[0].equals(args[1]) ? 1 : 0));
        globalEnv.register("!=", JSObject.newFunction("!=", (self, receiver, args) -> !args[0].equals(args[1]) ? 1 : 0));
        globalEnv.register("<", JSObject.newFunction("<",   (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) < 0) ? 1 : 0));
        globalEnv.register("<=", JSObject.newFunction("<=", (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) <= 0) ? 1 : 0));
        globalEnv.register(">", JSObject.newFunction(">",   (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) > 0) ? 1 : 0));
        globalEnv.register(">=", JSObject.newFunction(">=", (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) >= 0) ? 1 : 0));
        visit(body, globalEnv);
    }
}

