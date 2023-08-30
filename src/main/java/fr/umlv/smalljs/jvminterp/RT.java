package fr.umlv.smalljs.jvminterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.foldArguments;
import static java.lang.invoke.MethodHandles.guardWithTest;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.invoker;
import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

import fr.umlv.smalljs.rt.ArrayMap;
import fr.umlv.smalljs.rt.ArrayMap.Layout;
import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;

public class RT {
  private static final MethodHandle INVOKER, LOOKUP, REGISTER, TRUTH, GET_MH, METH_LOOKUP_MH;
  static {
    var lookup = MethodHandles.lookup();
    try {
      INVOKER = lookup.findVirtual(JSObject.class, "invoke", methodType(Object.class, Object.class, Object[].class));
      LOOKUP = lookup.findVirtual(JSObject.class, "lookup", methodType(Object.class, String.class));
      REGISTER = lookup.findVirtual(JSObject.class, "register", methodType(void.class, String.class, Object.class));
      TRUTH = lookup.findStatic(RT.class, "truth", methodType(boolean.class, Object.class));

      GET_MH = lookup.findVirtual(JSObject.class, "getMethodHandle", methodType(MethodHandle.class));
      METH_LOOKUP_MH = lookup.findStatic(RT.class, "lookupMethodHandle", methodType(MethodHandle.class, JSObject.class, String.class));
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }

  public static Object bsm_undefined(Lookup lookup, String name, Class<?> type) {
    return UNDEFINED;
  }

  public static Object bsm_const(Lookup lookup, String name, Class<?> type, int constant) {
    return constant;
  }

  public static CallSite bsm_funcall(Lookup lookup, String name, MethodType type) {
    throw new UnsupportedOperationException("TODO bsm_funcall");
  }

  public static CallSite bsm_lookup(Lookup lookup, String name, MethodType type, String functionName) {
    throw new UnsupportedOperationException("TODO bsm_lookup");
    //var classLoader = (FunClassLoader) lookup.lookupClass().getClassLoader();
    //var globalEnv = classLoader.getGlobal();
    // TODO
  }

  public static Object bsm_fun(Lookup lookup, String name, Class<?> type, int funId) {
    throw new UnsupportedOperationException("TODO bsm_fun");
    //var classLoader = (FunClassLoader) lookup.lookupClass().getClassLoader();
    //var globalEnv = classLoader.getGlobal();
    //var fun = classLoader.getDictionary().lookupAndClear(funId);
    //return ByteCodeRewriter.createFunction(fun.name().orElse("lambda"), fun.parameters(), fun.body(), globalEnv);
  }

  public static CallSite bsm_register(Lookup lookup, String name, MethodType type, String functionName) {
    throw new UnsupportedOperationException("TODO bsm_register");
    //var classLoader = (FunClassLoader) lookup.lookupClass().getClassLoader();
    //var globalEnv = classLoader.getGlobal();
    //TODO
  }

  @SuppressWarnings("unused")  // used by a method handle
  private static boolean truth(Object o) {
    return o != null && o != UNDEFINED && o != Boolean.FALSE;
  }
  public static CallSite bsm_truth(Lookup lookup, String name, MethodType type) {
    throw new UnsupportedOperationException("TODO bsm_truth");
    //TODO
  }

  public static CallSite bsm_get(Lookup lookup, String name, MethodType type, String fieldName) {
    throw new UnsupportedOperationException("TODO bsm_get");
    //TODO
  }

  public static CallSite bsm_set(Lookup lookup, String name, MethodType type, String fieldName) {
    throw new UnsupportedOperationException("TODO bsm_set");
    //TODO
  }

  @SuppressWarnings("unused")  // used by a method handle
  private static MethodHandle lookupMethodHandle(JSObject receiver, String fieldName) {
    var function = (JSObject)receiver.lookup(fieldName);
    return function.getMethodHandle();
  }

  public static CallSite bsm_methodcall(Lookup lookup, String name, MethodType type) {
    throw new UnsupportedOperationException("TODO bsm_methodcall");
    //var combiner = insertArguments(METH_LOOKUP_MH, 1, name).asType(methodType(MethodHandle.class, Object.class));
    //var target = foldArguments(invoker(type), combiner);
    //return new ConstantCallSite(target);
  }
}
