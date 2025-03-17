package fr.umlv.smalljs.rt;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JSObjectTest {

  @Nested
  public class ObjectCreationTests {
    @Test
    public void testNewObject() {
      var obj = JSObject.newObject(null);
      obj.register("x", 745);

      assertAll(
          () -> assertNotNull(obj),
          () -> assertEquals("object", obj.name()),
          () -> assertEquals(1, obj.length()),
          () -> assertEquals(745, obj.lookupOrDefault("x", null))
      );
    }

    @Test
    public void testNewEnv() {
      var parent = JSObject.newEnv(null);
      parent.register("a", 12);
      var env = JSObject.newEnv(parent);
      env.register("b", 34);

      assertAll(
          () -> assertNotNull(env),
          () -> assertEquals("env", env.name()),
          () -> assertSame(34, env.lookupOrDefault("b", null)),
          () -> assertSame(12, env.lookupOrDefault("a", null))
      );
    }

    @Test
    public void testNewFunctionWithInvoker() {
      JSObject.Invoker invoker = (receiver, args) -> "" + args[0] + args[1];
      var func = JSObject.newFunction("test", invoker);
      var call = func.lookupOrDefault("call", null);

      assertAll(
          () -> assertNotNull(func),
          () -> assertEquals("function test", func.name()),
          () -> assertNotNull(func.methodHandle()),
          () -> assertNotNull(call),
          () -> assertEquals("23", ((JSObject) call).invoke(func, null, 2, 3))
      );
    }

    @Test
    public void testNewFunctionWithMethodHandle() throws Exception {
      var mh = MethodHandles.lookup()
          .findStatic(String.class, "valueOf", MethodType.methodType(String.class, Object.class))
          .asType(MethodType.methodType(Object.class, Object.class));

      var func = JSObject.newFunction("valueOf", mh);

      assertAll(
          () -> assertNotNull(func),
          () -> assertEquals("function valueOf", func.name()),
          () -> assertNotNull(func.methodHandle())
      );
    }
  }

  @Nested
  public class PropertyTests {
    @Test
    public void testRegisterAndLookup() {
      var obj = JSObject.newObject(null);
      obj.register("a", 1);
      obj.register("b", "text");

      assertAll(
          () -> assertEquals(1, obj.lookupOrDefault("a", null)),
          () -> assertEquals("text", obj.lookupOrDefault("b", null)),
          () -> assertNull(obj.lookupOrDefault("c", null)),
          () -> assertEquals(2, obj.length())
      );
    }

    @Test
    public void testPropertyUpdate() {
      var obj = JSObject.newObject(null);
      obj.register("a", 1);
      obj.register("a", 2);

      assertAll(
          () -> assertEquals(2, obj.lookupOrDefault("a", null)),
          () -> assertEquals(1, obj.length()) // Length shouldn't change for updates
      );
    }

    @Test
    public void testPrototypeInheritance() {
      var proto = JSObject.newObject(null);
      proto.register("a", 1);
      proto.register("b", 2);

      var obj = JSObject.newObject(proto);
      obj.register("c", 3);

      assertAll(
          () -> assertEquals(1, obj.lookupOrDefault("a", null)),
          () -> assertEquals(2, obj.lookupOrDefault("b", null)),
          () -> assertEquals(3, obj.lookupOrDefault("c", null)),
          () -> assertEquals(1, obj.length()) // Only direct properties count for length
      );
    }

    @Test
    public void testFastAccess() {
      var obj = JSObject.newObject(null);
      obj.register("a", 42);
      obj.register("b", "text");

      var slotA = obj.layoutSlot("a");
      var slotB = obj.layoutSlot("b");

      assertAll(
          () -> assertEquals(0, slotA),
          () -> assertEquals(1, slotB),
          () -> assertEquals(42, obj.fastAccess(slotA)),
          () -> assertEquals("text", obj.fastAccess(slotB))
      );
    }
  }

  @Nested
  public class LayoutTests {
    @Test
    public void testLayoutSlots() {
      var obj = JSObject.newObject(null);
      obj.register("a", 1);
      obj.register("b", 2);

      var layout = obj.layout();

      assertAll(
          () -> assertEquals(0, obj.layoutSlot("a")),
          () -> assertEquals(1, obj.layoutSlot("b")),
          () -> assertEquals(-1, obj.layoutSlot("c"))
      );
    }

    @Test
    public void testSwitchPointInvalidation() {
      var obj = JSObject.newObject(null);
      var initialSwitchPoint = obj.switchPoint();

      obj.register("a", 1);  // New property
      var secondSwitchPoint = obj.switchPoint();

      obj.register("a", 2); // Update existing property

      assertAll(
          () -> assertNotSame(initialSwitchPoint, secondSwitchPoint),
          () -> assertNotSame(secondSwitchPoint, obj.switchPoint())
      );
    }
  }

  @Nested
  public class FunctionInvocationTests {
    @Test
    public void testFunctionInvocation() {
      var func = JSObject.newFunction("add", (receiver, args) -> {
        var a = (int) args[0];
        var b = (int) args[1];
        return a + b;
      });

      var result = func.invoke(null, 3, 4);
      assertEquals(7, result);
    }

    @Test
    public void testArgumentMismatch() throws Exception {
      // Create a non-varargs MethodHandle for testing argument count
      var mh = MethodHandles.lookup()
          .findStatic(Integer.class, "sum", MethodType.methodType(int.class, int.class, int.class));
      // drop the receiver
      mh = MethodHandles.dropArguments(mh, 0, Object.class);
      mh = mh .asType(MethodType.methodType(Object.class, Object.class, Object.class, Object.class));

      var func = JSObject.newFunction("sum", mh);

      assertAll(
          () -> assertThrows(Failure.class, () -> func.invoke(null, 1)),
          () -> assertThrows(Failure.class, () -> func.invoke(null, 1, 2, 3))
      );
    }

    @Test
    public void testNewObjectNoInvokerMH() {
      var obj = JSObject.newObject(null);
      assertThrows(Failure.class, () -> obj.invoke(null));
    }

    @Test
    public void testNewEnvNoInvokerMH() {
      var obj = JSObject.newEnv(null);
      assertThrows(Failure.class, () -> obj.invoke(null));
    }
  }

  @Nested
  public class MirrorTests {
    @Test
    public void testMirror() {
      var obj = JSObject.newObject(null);
      obj.register("a", 1);
      obj.register("b", 2);
      obj.register("c", "text");

      var mirror = obj.mirror(value -> {
        return switch (value) {
          case Integer i -> i * 2;
          default -> value;
        };
      });

      assertAll(
          () -> assertEquals(2, mirror.lookupOrDefault("a", null)),
          () -> assertEquals(4, mirror.lookupOrDefault("b", null)),
          () -> assertEquals("text", mirror.lookupOrDefault("c", null))
      );
    }

    @Test
    public void testTransformPrimitives() {
      var obj = JSObject.newObject(null);
      obj.register("int", 42);
      obj.register("double", 3.14);
      obj.register("string", "hello");
      obj.register("boolean", true);

      var mirror = obj.mirror(Object::toString);

      assertAll(
          () -> assertEquals("42", mirror.lookupOrDefault("int", null)),
          () -> assertEquals("3.14", mirror.lookupOrDefault("double", null)),
          () -> assertEquals("hello", mirror.lookupOrDefault("string", null)),
          () -> assertEquals("true", mirror.lookupOrDefault("boolean", null))
      );
    }

    @Test
    public void testNestedJSObjects() {
      var innerObj = JSObject.newObject(null);
      innerObj.register("inner", "value");

      var outerObj = JSObject.newObject(null);
      outerObj.register("nested", innerObj);

      var mirror = outerObj.mirror(value -> value);

      assertSame(innerObj, mirror.lookupOrDefault("nested", null));
    }


    @Test
    public void testEmptyObject() {
      var obj = JSObject.newObject(null);
      var mirror = obj.mirror(value -> value);

      assertEquals(0, mirror.length());
    }
  }

  @Nested
  public class ToStringTests {
    @Test
    public void testToString() {
      var obj = JSObject.newObject(null);
      obj.register("a", 1);
      obj.register("b", "text");

      var str = obj.toString();

      assertAll(
          () -> assertTrue(str.contains("a: 1")),
          () -> assertTrue(str.contains("b: text")),
          () -> assertTrue(str.contains("object"))
      );
    }

    @Test
    public void testToStringWithCircularReferences() {
      var obj = JSObject.newObject(null);
      obj.register("self", obj);

      var str = obj.toString();
      assertTrue(str.contains("self: ..."));
    }

    @Test
    public void testUndefinedToString() {
      assertEquals("undefined", JSObject.UNDEFINED.toString());
    }
  }
}