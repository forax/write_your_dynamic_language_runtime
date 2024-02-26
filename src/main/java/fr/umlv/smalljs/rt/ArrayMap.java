package fr.umlv.smalljs.rt;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public final class ArrayMap extends AbstractMap<String, Object> {
  public static class Layout {
    private final LinkedHashMap<String, Integer> slotMap;
    private final HashMap<String, Layout> forwardMap = new HashMap<>();
    
    private Layout(LinkedHashMap<String, Integer> slotMap) {
      this.slotMap = slotMap;
    }
    
    public int slot(Object key) {
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
  
  private Layout layout;
  private Object[] array = EMPTY_ARRAY;
  
  ArrayMap() {
    layout = ROOT;
  }
  
  Layout layout() {
    return layout;
  }
  Object fastAccess(int slot) {
    return array[slot];
  }
  
  @Override
  public Object get(Object key) {
    var slot = layout.slot(key);
    if (slot == -1) {
      return null;
    }
    return array[slot];
  }
  
  @Override
  public Object put(String key, Object value) {
    var slot = layout.slot(key);
    if (slot != -1) {
      var oldValue = array[slot];
      array[slot] = value;
      return oldValue;
    }
    layout = layout.forward(key);
    array = Arrays.copyOf(array, array.length + 1);
    array[array.length - 1] = value;
    return null;
  }
  
  @Override
  public int size() {
    return array.length;
  }
  
  @Override
  public boolean containsKey(Object key) {
    return layout.slot(key) != -1;
  }
  
  @Override
  public Set<Entry<String, Object>> entrySet() {
    var array = this.array;
    return new AbstractSet<>() {
      @Override
      public int size() {
        return array.length;
      }
      
      @Override
      public Iterator<Entry<String, Object>> iterator() {
        var iterator = layout.slotMap.entrySet().iterator();
        return new Iterator<>() {
          @Override
          public boolean hasNext() {
            return iterator.hasNext();
          }
          
          @Override
          public Entry<String, Object> next() {
            var entry = iterator.next();
            return Map.entry(entry.getKey(), array[entry.getValue()]);
          }
        };
      }
    };
  }

  /*
  @Override
  public Collection<Object> values() {
    var array = this.array;
    return new AbstractCollection<>() {
      @Override
      public int size() {
        return array.length;
      }

      @Override
      public Iterator<Object> iterator() {
        return new Iterator<>() {
          private int index;

          @Override
          public boolean hasNext() {
            return index < array.length;
          }

          @Override
          public Object next() {
            if (index < array.length) {
              throw new NoSuchElementException();
            }
            return array[index++];
          }
        };
      }
    };
  }*/

  @Override
  public void forEach(BiConsumer<? super String, ? super Object> action) {
    var array = this.array;
    layout.slotMap.forEach((key, slot) -> action.accept(key, array[slot]));
  }
}
