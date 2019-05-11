package com.facebook.react.bridge;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.DynamicFromMap;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JavaOnlyMap implements ReadableMap, WritableMap {

   private final Map mBackingMap;


   public JavaOnlyMap() {
      this.mBackingMap = new HashMap();
   }

   private JavaOnlyMap(Object ... var1) {
      if(var1.length % 2 != 0) {
         throw new IllegalArgumentException("You must provide the same number of keys and values");
      } else {
         this.mBackingMap = new HashMap();

         for(int var2 = 0; var2 < var1.length; var2 += 2) {
            this.mBackingMap.put(var1[var2], var1[var2 + 1]);
         }

      }
   }

   public static JavaOnlyMap of(Object ... var0) {
      return new JavaOnlyMap(var0);
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            JavaOnlyMap var2 = (JavaOnlyMap)var1;
            if(this.mBackingMap != null) {
               if(!this.mBackingMap.equals(var2.mBackingMap)) {
                  return false;
               }
            } else if(var2.mBackingMap != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public JavaOnlyArray getArray(String var1) {
      return (JavaOnlyArray)this.mBackingMap.get(var1);
   }

   public boolean getBoolean(String var1) {
      return ((Boolean)this.mBackingMap.get(var1)).booleanValue();
   }

   public double getDouble(String var1) {
      return ((Double)this.mBackingMap.get(var1)).doubleValue();
   }

   public Dynamic getDynamic(String var1) {
      return DynamicFromMap.create(this, var1);
   }

   public int getInt(String var1) {
      return ((Integer)this.mBackingMap.get(var1)).intValue();
   }

   public JavaOnlyMap getMap(String var1) {
      return (JavaOnlyMap)this.mBackingMap.get(var1);
   }

   public String getString(String var1) {
      return (String)this.mBackingMap.get(var1);
   }

   public ReadableType getType(String var1) {
      Object var2 = this.mBackingMap.get(var1);
      if(var2 == null) {
         return ReadableType.Null;
      } else if(var2 instanceof Number) {
         return ReadableType.Number;
      } else if(var2 instanceof String) {
         return ReadableType.String;
      } else if(var2 instanceof Boolean) {
         return ReadableType.Boolean;
      } else if(var2 instanceof ReadableMap) {
         return ReadableType.Map;
      } else if(var2 instanceof ReadableArray) {
         return ReadableType.Array;
      } else if(var2 instanceof Dynamic) {
         return ((Dynamic)var2).getType();
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Invalid value ");
         var3.append(var2.toString());
         var3.append(" for key ");
         var3.append(var1);
         var3.append("contained in JavaOnlyMap");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public boolean hasKey(String var1) {
      return this.mBackingMap.containsKey(var1);
   }

   public int hashCode() {
      return this.mBackingMap != null?this.mBackingMap.hashCode():0;
   }

   public boolean isNull(String var1) {
      return this.mBackingMap.get(var1) == null;
   }

   public ReadableMapKeySetIterator keySetIterator() {
      return new ReadableMapKeySetIterator() {

         Iterator<String> mIterator;

         {
            this.mIterator = JavaOnlyMap.this.mBackingMap.keySet().iterator();
         }
         public boolean hasNextKey() {
            return this.mIterator.hasNext();
         }
         public String nextKey() {
            return (String)this.mIterator.next();
         }
      };
   }

   public void merge(ReadableMap var1) {
      this.mBackingMap.putAll(((JavaOnlyMap)var1).mBackingMap);
   }

   public void putArray(String var1, WritableArray var2) {
      this.mBackingMap.put(var1, var2);
   }

   public void putBoolean(String var1, boolean var2) {
      this.mBackingMap.put(var1, Boolean.valueOf(var2));
   }

   public void putDouble(String var1, double var2) {
      this.mBackingMap.put(var1, Double.valueOf(var2));
   }

   public void putInt(String var1, int var2) {
      this.mBackingMap.put(var1, Integer.valueOf(var2));
   }

   public void putMap(String var1, WritableMap var2) {
      this.mBackingMap.put(var1, var2);
   }

   public void putNull(String var1) {
      this.mBackingMap.put(var1, (Object)null);
   }

   public void putString(String var1, String var2) {
      this.mBackingMap.put(var1, var2);
   }

   public HashMap<String, Object> toHashMap() {
      return new HashMap(this.mBackingMap);
   }

   public String toString() {
      return this.mBackingMap.toString();
   }
}
