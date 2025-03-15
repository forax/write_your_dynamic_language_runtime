package fr.umlv.smalljs.rt;

import static java.util.Objects.requireNonNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.function.UnaryOperator;

public final class JSObject {
  private static final class Layout {
    private final LinkedHashMap<String, Integer> slotMap;
    private final HashMap<String, Layout> forwardMap = new HashMap<>();

    private Layout(LinkedHashMap<String, Integer> slotMap) {
      this.slotMap = slotMap;
    }

    private int slot(String key) {
      return slotMap.getOrDefault(key, -1);
    }

    private Layout forward(String key) {
      return forwardMap.computeIfAbsent(key, k -> {
        var newSlotMap = new LinkedHashMap<>(slotMap);
        newSlotMap.put(k, newSlotMap.size());
        return new Layout(newSlotMap);
      });
    }
  }

  private static final Layout ROOT = new Layout(new LinkedHashMap<>());
  private static final Object[] EMPTY_ARRAY = new Object[0];

  private final JSObject proto;
  private final String name;
  private Layout layout = ROOT;
  private Object[] array = EMPTY_ARRAY;
  private final MethodHandle mh;
  private SwitchPoint switchPoint = new SwitchPoint();
  
  private static final class Undefined {
  	@Override public String toString() { return "undefined"; }
  }
  public static final Object UNDEFINED = new Undefined();
  
  private static final MethodHandle INVOKER;
  static {
    try {
      INVOKER = MethodHandles.lookup().findVirtual(Invoker.class, "invoke", MethodType.methodType(Object.class, Object.class, Object[].class));
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }

  public interface Invoker {
    Object invoke(Object receiver, Object... args);
  }

  private static MethodHandle asMethodHandle(Invoker invoker) {
    return INVOKER.bindTo(invoker).withVarargs(true);
  }

  public static final MethodHandle NO_INVOKER_MH =
      asMethodHandle((_, _) -> { throw new Failure("can not be invoked"); });
  
  private JSObject(JSObject proto, String name, MethodHandle mh) {
    this.proto = proto;
    this.name = name;
    this.mh = mh;
  }
  
  public static JSObject newObject(JSObject proto) {
    return new JSObject(proto, "object", NO_INVOKER_MH);
  }
  public static JSObject newEnv(JSObject parent) {
    return new JSObject(parent, "env", NO_INVOKER_MH);
  }
  public static JSObject newFunction(String name, Invoker invoker) {
    requireNonNull(name);
    requireNonNull(invoker);
    return newFunction(name, asMethodHandle(invoker));
  }
  public static JSObject newFunction(String name, MethodHandle mh) {
    requireNonNull(name);
    requireNonNull(mh);
    var function = new JSObject(null, "function " + name, mh);
    function.register("apply", function);
    return function;
  }
  
  public String name() {
		return name;
	}
  public MethodHandle methodHandle() {
    return mh;
  }
  public SwitchPoint switchPoint() {
    return switchPoint;
  }
  public Object layout() {
    return layout;
  }
  public int layoutSlot(String key) {
    return layout.slot(key);
  }
  public Object fastAccess(int slot) {
    return array[slot];
  }

  public Object invoke(Object receiver, Object... args) {
    //System.err.println("invoke " + this + " " + receiver + " " + java.util.Arrays.toString(args));
    //System.err.println("invoke mh " + mh);

    if (!mh.isVarargsCollector() && args.length != mh.type().parameterCount() - 1) {
      throw new Failure("arguments doesn't match parameters count " + args.length + " " + (mh.type().parameterCount() - 1));
    }
    var array = new Object[args.length + 1];
    array[0] = receiver;
    System.arraycopy(args, 0, array, 1, args.length);
    try {
      return mh.invokeWithArguments(array);
    } catch(RuntimeException | Error e) {
      throw e;
    } catch (Throwable e) {
      throw new Failure(e.getMessage(), e);
    }
  }
  
  public Object lookup(String key) {
    requireNonNull(key);
    var slot = layout.slot(key);
    if (slot != -1) {
      return array[slot];
    }
    if (proto != null) {
      return proto.lookup(key);
    }
    return UNDEFINED;
  }

  public void register(String key, Object value) {
    requireNonNull(key);
    requireNonNull(value);
    var slot = layout.slot(key);
    if (slot != -1) {
      array[slot] = value;
    } else {
      layout = layout.forward(key);
      array = Arrays.copyOf(array, array.length + 1);
      array[array.length - 1] = value;
    }

    // broadcast change, not thread safe
    SwitchPoint.invalidateAll(new SwitchPoint[] { switchPoint });
    switchPoint = new SwitchPoint();
  }
  
  public int length() {
    return array.length;
  }
  
  public JSObject mirror(UnaryOperator<Object> valueMapper) {
    requireNonNull(valueMapper);
    var mirror = newObject(null);
    var array = this.array;
    layout.slotMap.forEach((key, slot) -> {
      mirror.register(key, valueMapper.apply(array[slot]));
    });
    return mirror;
  }
  
  @Override
  public String toString() {
    var builder = new StringBuilder();
    toString(this, builder, Collections.newSetFromMap(new IdentityHashMap<>()));
    return builder.toString();
  }

  private static void toString(Object object, StringBuilder builder, Set<Object> seen) {
    if(object == null) {
      builder.append("null");
      return;
    }
    if (!seen.add(object)) {
      builder.append("...");
      if (object instanceof JSObject jsObject) {
        builder.append(" // ").append(jsObject.name);
      }
      return;
    }
    if (!(object instanceof JSObject jsObject)) {
      builder.append(object);
      return;
    }
    builder.append("{ // ").append(jsObject.name).append('\n');
    jsObject.layout.slotMap.forEach((key, slot) -> {
      builder.append("  ").append(key).append(": ");
      toString(jsObject.array[slot], builder, seen);
      builder.append("\n");
    });
    builder.append("  proto: ");
    toString(jsObject.proto, builder, seen);
    builder.append("\n");
    builder.append("}");
  }
}