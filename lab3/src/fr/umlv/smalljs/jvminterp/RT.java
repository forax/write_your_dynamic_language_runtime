package fr.umlv.smalljs.jvminterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.foldArguments;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.invoker;
import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

import fr.umlv.smalljs.rt.JSObject;

public class RT {
  //private static final MethodHandle INVOKER, LOOKUP, REGISTER, TRUTH, GET_MH, LOOKUP_MH;
  static {
    var lookup = MethodHandles.lookup();
    /*try {
      
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new AssertionError(e);
    }*/
  }
  
  public static Object bsm_undefined(Lookup lookup, String name, Class<?> type) {
    return UNDEFINED;
  }

  public static Object bsm_const(Lookup lookup, String name, Class<?> type, int constant) {
    return constant;
  }

  public static CallSite bsm_funcall(Lookup lookup, String name, MethodType type) {
  	// get method handle on JSObject.invoke
  	// use asCollector on all the arguments by the 2 firsts
  	// use asType
  	// return a constant callsite
  	throw new UnsupportedOperationException("TODO");
  }

  public static CallSite bsm_lookup(Lookup lookup, String name, MethodType type, String functionName) {
  	// get class loader
    var classLoader = (FunctionClassLoader) lookup.lookupClass().getClassLoader();
    // get global env
    var globalEnv = classLoader.getGlobal();
    // get method handle on JSObject.lookup
    // insert argument the global env and the name
    // return a constant callsite
    throw new UnsupportedOperationException("TODO");
  }

  public static Object bsm_fun(Lookup lookup, String name, Class<?> type, int funId) {
  	// get class loader
    var classLoader = (FunctionClassLoader) lookup.lookupClass().getClassLoader();
    // get global env
    var globalEnv = classLoader.getGlobal();
    // retrieve the Fun AST node from the dictionary
    var fun = classLoader.getDictionary().lookupAndClear(funId);
    // call the rewriter to create the function and return it
    throw new UnsupportedOperationException("TODO");
  }

  public static CallSite bsm_register(Lookup lookup, String name, MethodType type, String functionName) {
  	// get classloader
    var classLoader = (FunctionClassLoader) lookup.lookupClass().getClassLoader();
    // get global env
    var globalEnv = classLoader.getGlobal();
    // get method handle on JSObject.register
    // insert argument the global env and the name
    // return a constant callsite
    throw new UnsupportedOperationException("TODO");
  }

  @SuppressWarnings("unused")  // used by a method handle
  private static boolean truth(Object o) {
    return o != null && o != UNDEFINED && o != Boolean.FALSE;
  }
  public static CallSite bsm_truth(Lookup lookup, String name, MethodType type) {
    // get a method handle on truth
  	// return a constant callsite on the method handle
    throw new UnsupportedOperationException("TODO");
  }

  public static CallSite bsm_get(Lookup lookup, String name, MethodType type, String fieldName) {
  	// get a method handle on lookup
  	// inject the field name
  	// correct the signature with asType
  	// return a constant callsite
    throw new UnsupportedOperationException("TODO");
  }

  public static CallSite bsm_set(Lookup lookup, String name, MethodType type, String fieldName) {
    // get a method handle on register
   	// inject the field name
   	// fix the signature with asType
   	// return a constant callsite
    throw new UnsupportedOperationException("TODO");
  }

  @SuppressWarnings("unused")  // used by a method handle
	private static MethodHandle lookupMethodHandle(JSObject receiver, String fieldName) {
  	// find field fieldName in receiver
    var function = (JSObject)receiver.lookup(fieldName);
    // return the method handle stored in the JSObject
    return function.getMethodHandle();
  }
  
  public static CallSite bsm_methodcall(Lookup lookup, String name, MethodType type) {
  	// get a method handle on lookup
  	// fix the signature with asType
  	// fold with a MethodHandles.exactInvoker
  	//var target = foldArguments(MethodHandles.exactInvoker(type), combiner);
  	// return a constant call site
    throw new UnsupportedOperationException("TODO");
  }
}
