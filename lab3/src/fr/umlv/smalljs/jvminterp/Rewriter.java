package fr.umlv.smalljs.jvminterp;

import static java.lang.invoke.MethodType.genericMethodType;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.V11;

import java.io.PrintWriter;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.CheckClassAdapter;

import fr.umlv.smalljs.ast.Block;
import fr.umlv.smalljs.ast.Expr;
import fr.umlv.smalljs.ast.FieldAccess;
import fr.umlv.smalljs.ast.FieldAssignment;
import fr.umlv.smalljs.ast.Fun;
import fr.umlv.smalljs.ast.FunCall;
import fr.umlv.smalljs.ast.If;
import fr.umlv.smalljs.ast.Instr;
import fr.umlv.smalljs.ast.Literal;
import fr.umlv.smalljs.ast.LocalVarAccess;
import fr.umlv.smalljs.ast.LocalVarAssignment;
import fr.umlv.smalljs.ast.MethodCall;
import fr.umlv.smalljs.ast.New;
import fr.umlv.smalljs.ast.Return;
import fr.umlv.smalljs.ast.VoidVisitor;
import fr.umlv.smalljs.rt.JSObject;

public class Rewriter {
    private final VoidVisitor<JSObject> visitor;

    private Rewriter(MethodVisitor mv, Dictionary dictionary) {
        this.visitor = createVisitor(mv, dictionary);
    }

    public static JSObject createFunction(String name, List<String> parameters, Block body, JSObject global) {
        var env = JSObject.newEnv(null);

        env.register("this", 0);
        for (String parameter : parameters) {
            env.register(parameter, env.length());
        }
        var parameterCount = env.length();
        visitVariable(body, env);
        var localVariableCount = env.length();

        var cv = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cv.visit(V11, ACC_PUBLIC | ACC_SUPER, "script", null, "java/lang/Object", null);
        cv.visitSource("script", null);

        var methodType = genericMethodType(1 + parameters.size());
        var desc = methodType.toMethodDescriptorString();
        var mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, name, desc, null, null);
        mv.visitCode();

        //initialize local variables to undefined by default
        for(var i = parameterCount; i < localVariableCount; i++) {
          mv.visitLdcInsn(new ConstantDynamic("undefined", "Ljava/lang/Object;", BSM_UNDEFINED));
          mv.visitVarInsn(ASTORE, i);
        }

        var dictionary = new Dictionary();
        var rewriter = new Rewriter(mv, dictionary);
        rewriter.visitor.visit(body, env);

        mv.visitLdcInsn(new ConstantDynamic("undefined", "Ljava/lang/Object;", BSM_UNDEFINED));
        mv.visitInsn(ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        var instrs = cv.toByteArray();
        dumpBytecode(instrs);

        var functionClassLoader = new FunctionClassLoader(dictionary, global);
        var type = functionClassLoader.createClass("script", instrs);

        MethodHandle mh;
        try {
            mh = MethodHandles.lookup().findStatic(type, name, methodType);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new AssertionError(e);
        }

        return JSObject.newFunction(name, mh);
    }

    private static void dumpBytecode(byte[] array) {
        ClassReader reader = new ClassReader(array);
        CheckClassAdapter.verify(reader, true, new PrintWriter(System.err));
    }

    private static void visitVariable(Expr expr, JSObject env) {
        VARIABLE_VISITOR.visit(expr, env);
    }

    private static final VoidVisitor<JSObject> VARIABLE_VISITOR =
            new VoidVisitor<JSObject>()
                    .when(Block.class, (block, env) -> {
                        for (Expr instr : block.getInstrs()) {
                            visitVariable(instr, env);
                        }
                    })
                    .when(Literal.class, (literal, env) -> {
                        // do nothing
                    })
                    .when(FunCall.class, (funCall, env) -> {
                        // do nothing
                    })
                    .when(LocalVarAssignment.class, (localVarAssignment, env) -> {
                        if (localVarAssignment.isDeclaration()) {
                            env.register(localVarAssignment.getName(), env.length());
                        }
                    })
                    .when(LocalVarAccess.class, (localVarAccess, env) -> {
                        // do nothing
                    })
                    .when(Fun.class, (fun, env) -> {
                        // do nothing
                    })
                    .when(Return.class, (_return, env) -> {
                        // do nothing
                    })
                    .when(If.class, (_if, env) -> {
                        visitVariable(_if.getTrueBlock(), env);
                        visitVariable(_if.getFalseBlock(), env);
                    })
                    .when(New.class, (_new, env) -> {
                        // do nothing
                    })
                    .when(FieldAccess.class, (fieldAccess, env) -> {
                        // do nothing
                    })
                    .when(FieldAssignment.class, (fieldAssignment, env) -> {
                        // do nothing
                    })
                    .when(MethodCall.class, (methodCall, env) -> {
                        // do nothing
                    });


    private static Handle bsm(String name, Class<?> returnType, Class<?>... parameterTypes) {
        return new Handle(H_INVOKESTATIC,
                RT_NAME, name,
                MethodType.methodType(returnType, parameterTypes).toMethodDescriptorString(), false);
    }

    private static final String JSOBJECT = JSObject.class.getName().replace('.', '/');
    private static final String RT_NAME = RT.class.getName().replace('.', '/');
    private static final Handle BSM_UNDEFINED = bsm("bsm_undefined", Object.class, Lookup.class, String.class, Class.class);
    private static final Handle BSM_CONST = bsm("bsm_const", Object.class, Lookup.class, String.class, Class.class, int.class);
    private static final Handle BSM_FUNCALL = bsm("bsm_funcall", CallSite.class, Lookup.class, String.class, MethodType.class);
    private static final Handle BSM_LOOKUP = bsm("bsm_lookup", CallSite.class, Lookup.class, String.class, MethodType.class, String.class);
    private static final Handle BSM_FUN = bsm("bsm_fun", Object.class, Lookup.class, String.class, Class.class, int.class);
    private static final Handle BSM_REGISTER = bsm("bsm_register", CallSite.class, Lookup.class, String.class, MethodType.class, String.class);
    private static final Handle BSM_TRUTH = bsm("bsm_truth", CallSite.class, Lookup.class, String.class, MethodType.class);
    private static final Handle BSM_GET = bsm("bsm_get", CallSite.class, Lookup.class, String.class, MethodType.class, String.class);
    private static final Handle BSM_SET = bsm("bsm_set", CallSite.class, Lookup.class, String.class, MethodType.class, String.class);
    private static final Handle BSM_METHODCALL = bsm("bsm_methodcall", CallSite.class, Lookup.class, String.class, MethodType.class);

    private static VoidVisitor<JSObject> createVisitor(MethodVisitor mv, Dictionary dictionary) {
    	  var visitor= new VoidVisitor<JSObject>();
        visitor
                .when(Block.class, (block, env) -> {
                    for (Expr e : block.getInstrs()) {
                        visitor.visit(e, env);
                        if (!(e instanceof Instr)) {
                        	mv.visitInsn(POP);
                        }
                    }
                })
                .when(Literal.class, (literal, env) -> {
                	  // get the value
                    Object value = literal.getValue();
                    // if it's an integer 
                    if (value instanceof Integer) { // use constant dynamic
                        //TODO
                    } else {  // otherwise, emit an ldc
                        //TODO
                    }
                })
                .when(FunCall.class, (funCall, env) -> {
                	  // visit qualifier
                    //TODO
                    // load this
                    //TODO
                    // visit all arguments
                    //TODO
                    // invokedynamic funcall with a generic method type
                    //TODO
                })
                .when(LocalVarAssignment.class, (localVarAssignment, env) -> {
                	  // visit expression
                    //TODO
                    // store at slot returned by env lookup
                    //TODO
                })
                .when(LocalVarAccess.class, (localVarAccess, env) -> {
                	  // get name
                    //TOO
                    // lookup
                    //TODO
                    /*if (object == JSObject.UNDEFINED) {
                    	  // invokedynamic lookup
                        // TODO
                    } else {
                       // load local variable at the slot
                    }*/
                })
                .when(Fun.class, (fun, env) -> {
                	  // register fun in dictionnary
                    //TODO
                    // emit a LDC with a constant dynamic to load fun from the id
                    //mv.visitLdcInsn(new ConstantDynamic("fun", "Ljava/lang/Object;", BSM_FUN, funId));
                    fun.getName().ifPresent(funName -> {
                    	// dup
                    	//TODO
                      // emit an indy to register
                      //TODO
                    });
                })
                .when(Return.class, (_return, env) -> {
                	  // visit expression
                    //TODO
                	  // emit a return Object
                    //TODO
                })
                .when(If.class, (_if, env) -> {
                    // declare labels
                	  // TODO
                	  // visit condition
                    // TODO
                    mv.visitInvokeDynamicInsn("truth", "(Ljava/lang/Object;)Z", BSM_TRUTH);
                    // jump if equals to 0
                    // TODO
                    // visit true block
                    // TODO
                    // goto
                    // TODO
                    // visit label
                    // TODO
                    // visit false block
                    // visit label
                })
                .when(New.class, (_new, env) -> {
                	  // put null on top of the stack (the prototype)
                    // TODO
                    // invokestatic newObject
                    // TODO
                    _new.getInitMap().forEach((key, init) -> {
                    	// dup
                      //TODO
                      // ldc key
                      //TODO
                      // visit init
                      //TODO
                      // invoke virtual register
                      //TODO
                    });
                })
                .when(FieldAccess.class, (fieldAccess, env) -> {
                	  // visit receiver
                	  //TODO
                	  // emit indy get with the field name
                	  //TODO
                })
                .when(FieldAssignment.class, (fieldAssignment, env) -> {
                	  // visit receiver
                	  //TODO
                	  // visit expression
                	  //TODO
                    // emit indy set with the field name
              	    //TODO
                })
                .when(MethodCall.class, (methodCall, env) -> {
                	  // visit receiver
                	  //TODO
                	  // visit arguments
                	  //TODO
                	  // emit indy method_call with method name
                })
        ;
        return visitor;
    }
}
