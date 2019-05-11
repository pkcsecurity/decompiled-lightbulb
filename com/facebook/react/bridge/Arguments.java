package com.facebook.react.bridge;

import android.os.Bundle;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class Arguments {

   private static void addEntry(WritableNativeMap var0, String var1, Object var2) {
      var2 = makeNativeObject(var2);
      if(var2 == null) {
         var0.putNull(var1);
      } else if(var2 instanceof Boolean) {
         var0.putBoolean(var1, ((Boolean)var2).booleanValue());
      } else if(var2 instanceof Integer) {
         var0.putInt(var1, ((Integer)var2).intValue());
      } else if(var2 instanceof Number) {
         var0.putDouble(var1, ((Number)var2).doubleValue());
      } else if(var2 instanceof String) {
         var0.putString(var1, (String)var2);
      } else if(var2 instanceof WritableNativeArray) {
         var0.putArray(var1, (WritableNativeArray)var2);
      } else if(var2 instanceof WritableNativeMap) {
         var0.putMap(var1, (WritableNativeMap)var2);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Could not convert ");
         var3.append(var2.getClass());
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public static WritableArray createArray() {
      return new WritableNativeArray();
   }

   public static WritableMap createMap() {
      return new WritableNativeMap();
   }

   public static WritableArray fromArray(Object var0) {
      WritableArray var8 = createArray();
      boolean var7 = var0 instanceof String[];
      byte var2 = 0;
      byte var3 = 0;
      byte var4 = 0;
      byte var5 = 0;
      byte var6 = 0;
      int var1 = 0;
      int var15;
      if(var7) {
         String[] var9 = (String[])var0;

         for(var15 = var9.length; var1 < var15; ++var1) {
            var8.pushString(var9[var1]);
         }
      } else if(var0 instanceof Bundle[]) {
         Bundle[] var10 = (Bundle[])var0;
         int var16 = var10.length;

         for(var1 = var2; var1 < var16; ++var1) {
            var8.pushMap(fromBundle(var10[var1]));
         }
      } else if(var0 instanceof int[]) {
         int[] var11 = (int[])var0;
         var15 = var11.length;

         for(var1 = var3; var1 < var15; ++var1) {
            var8.pushInt(var11[var1]);
         }
      } else if(var0 instanceof float[]) {
         float[] var12 = (float[])var0;
         var15 = var12.length;

         for(var1 = var4; var1 < var15; ++var1) {
            var8.pushDouble((double)var12[var1]);
         }
      } else if(var0 instanceof double[]) {
         double[] var13 = (double[])var0;
         var15 = var13.length;

         for(var1 = var5; var1 < var15; ++var1) {
            var8.pushDouble(var13[var1]);
         }
      } else {
         if(!(var0 instanceof boolean[])) {
            StringBuilder var17 = new StringBuilder();
            var17.append("Unknown array type ");
            var17.append(var0.getClass());
            throw new IllegalArgumentException(var17.toString());
         }

         boolean[] var14 = (boolean[])var0;
         var15 = var14.length;

         for(var1 = var6; var1 < var15; ++var1) {
            var8.pushBoolean(var14[var1]);
         }
      }

      return var8;
   }

   public static WritableMap fromBundle(Bundle var0) {
      WritableMap var2 = createMap();
      Iterator var3 = var0.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         Object var1 = var0.get(var4);
         if(var1 == null) {
            var2.putNull(var4);
         } else if(var1.getClass().isArray()) {
            var2.putArray(var4, fromArray(var1));
         } else if(var1 instanceof String) {
            var2.putString(var4, (String)var1);
         } else if(var1 instanceof Number) {
            if(var1 instanceof Integer) {
               var2.putInt(var4, ((Integer)var1).intValue());
            } else {
               var2.putDouble(var4, ((Number)var1).doubleValue());
            }
         } else if(var1 instanceof Boolean) {
            var2.putBoolean(var4, ((Boolean)var1).booleanValue());
         } else if(var1 instanceof Bundle) {
            var2.putMap(var4, fromBundle((Bundle)var1));
         } else {
            if(!(var1 instanceof List)) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Could not convert ");
               var5.append(var1.getClass());
               throw new IllegalArgumentException(var5.toString());
            }

            var2.putArray(var4, fromList((List)var1));
         }
      }

      return var2;
   }

   public static WritableNativeArray fromJavaArgs(Object[] var0) {
      WritableNativeArray var3 = new WritableNativeArray();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         Object var4 = var0[var1];
         if(var4 == null) {
            var3.pushNull();
         } else {
            Class var2 = var4.getClass();
            if(var2 == Boolean.class) {
               var3.pushBoolean(((Boolean)var4).booleanValue());
            } else if(var2 == Integer.class) {
               var3.pushDouble(((Integer)var4).doubleValue());
            } else if(var2 == Double.class) {
               var3.pushDouble(((Double)var4).doubleValue());
            } else if(var2 == Float.class) {
               var3.pushDouble(((Float)var4).doubleValue());
            } else if(var2 == String.class) {
               var3.pushString(var4.toString());
            } else if(var2 == WritableNativeMap.class) {
               var3.pushMap((WritableNativeMap)var4);
            } else {
               if(var2 != WritableNativeArray.class) {
                  StringBuilder var5 = new StringBuilder();
                  var5.append("Cannot convert argument of type ");
                  var5.append(var2);
                  throw new RuntimeException(var5.toString());
               }

               var3.pushArray((WritableNativeArray)var4);
            }
         }
      }

      return var3;
   }

   public static WritableArray fromList(List var0) {
      WritableArray var1 = createArray();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if(var3 == null) {
            var1.pushNull();
         } else if(var3.getClass().isArray()) {
            var1.pushArray(fromArray(var3));
         } else if(var3 instanceof Bundle) {
            var1.pushMap(fromBundle((Bundle)var3));
         } else if(var3 instanceof List) {
            var1.pushArray(fromList((List)var3));
         } else if(var3 instanceof String) {
            var1.pushString((String)var3);
         } else if(var3 instanceof Integer) {
            var1.pushInt(((Integer)var3).intValue());
         } else if(var3 instanceof Number) {
            var1.pushDouble(((Number)var3).doubleValue());
         } else {
            if(!(var3 instanceof Boolean)) {
               StringBuilder var4 = new StringBuilder();
               var4.append("Unknown value type ");
               var4.append(var3.getClass());
               throw new IllegalArgumentException(var4.toString());
            }

            var1.pushBoolean(((Boolean)var3).booleanValue());
         }
      }

      return var1;
   }

   public static <T extends Object> WritableNativeArray makeNativeArray(final Object var0) {
      return var0 == null?new WritableNativeArray():makeNativeArray((List)(new AbstractList() {
         public Object get(int var1) {
            return Array.get(var0, var1);
         }
         public int size() {
            return Array.getLength(var0);
         }
      }));
   }

   public static WritableNativeArray makeNativeArray(List var0) {
      WritableNativeArray var1 = new WritableNativeArray();
      if(var0 == null) {
         return var1;
      } else {
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            Object var3 = makeNativeObject(var2.next());
            if(var3 == null) {
               var1.pushNull();
            } else if(var3 instanceof Boolean) {
               var1.pushBoolean(((Boolean)var3).booleanValue());
            } else if(var3 instanceof Integer) {
               var1.pushInt(((Integer)var3).intValue());
            } else if(var3 instanceof Double) {
               var1.pushDouble(((Double)var3).doubleValue());
            } else if(var3 instanceof String) {
               var1.pushString((String)var3);
            } else if(var3 instanceof WritableNativeArray) {
               var1.pushArray((WritableNativeArray)var3);
            } else {
               if(!(var3 instanceof WritableNativeMap)) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append("Could not convert ");
                  var4.append(var3.getClass());
                  throw new IllegalArgumentException(var4.toString());
               }

               var1.pushMap((WritableNativeMap)var3);
            }
         }

         return var1;
      }
   }

   public static WritableNativeMap makeNativeMap(Bundle var0) {
      WritableNativeMap var1 = new WritableNativeMap();
      if(var0 == null) {
         return var1;
      } else {
         Iterator var2 = var0.keySet().iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            addEntry(var1, var3, var0.get(var3));
         }

         return var1;
      }
   }

   public static WritableNativeMap makeNativeMap(Map<String, Object> var0) {
      WritableNativeMap var1 = new WritableNativeMap();
      if(var0 == null) {
         return var1;
      } else {
         Iterator var3 = var0.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var2 = (Entry)var3.next();
            addEntry(var1, (String)var2.getKey(), var2.getValue());
         }

         return var1;
      }
   }

   private static Object makeNativeObject(Object var0) {
      return var0 == null?null:(!(var0 instanceof Float) && !(var0 instanceof Long) && !(var0 instanceof Byte) && !(var0 instanceof Short)?(var0.getClass().isArray()?makeNativeArray(var0):(var0 instanceof List?makeNativeArray((List)var0):(var0 instanceof Map?makeNativeMap((Map)var0):(var0 instanceof Bundle?makeNativeMap((Bundle)var0):var0)))):new Double(((Number)var0).doubleValue()));
   }

   @Nullable
   public static Bundle toBundle(@Nullable ReadableMap var0) {
      if(var0 == null) {
         return null;
      } else {
         ReadableMapKeySetIterator var2 = var0.keySetIterator();
         Bundle var3 = new Bundle();

         while(var2.hasNextKey()) {
            String var1 = var2.nextKey();
            ReadableType var4 = var0.getType(var1);
            switch(null.$SwitchMap$com$facebook$react$bridge$ReadableType[var4.ordinal()]) {
            case 1:
               var3.putString(var1, (String)null);
               break;
            case 2:
               var3.putBoolean(var1, var0.getBoolean(var1));
               break;
            case 3:
               var3.putDouble(var1, var0.getDouble(var1));
               break;
            case 4:
               var3.putString(var1, var0.getString(var1));
               break;
            case 5:
               var3.putBundle(var1, toBundle(var0.getMap(var1)));
               break;
            case 6:
               var3.putSerializable(var1, toList(var0.getArray(var1)));
               break;
            default:
               StringBuilder var5 = new StringBuilder();
               var5.append("Could not convert object with key: ");
               var5.append(var1);
               var5.append(".");
               throw new IllegalArgumentException(var5.toString());
            }
         }

         return var3;
      }
   }

   @Nullable
   public static ArrayList toList(@Nullable ReadableArray var0) {
      if(var0 == null) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();

         for(int var3 = 0; var3 < var0.size(); ++var3) {
            switch(null.$SwitchMap$com$facebook$react$bridge$ReadableType[var0.getType(var3).ordinal()]) {
            case 1:
               var4.add((Object)null);
               break;
            case 2:
               var4.add(Boolean.valueOf(var0.getBoolean(var3)));
               break;
            case 3:
               double var1 = var0.getDouble(var3);
               if(var1 == Math.rint(var1)) {
                  var4.add(Integer.valueOf((int)var1));
               } else {
                  var4.add(Double.valueOf(var1));
               }
               break;
            case 4:
               var4.add(var0.getString(var3));
               break;
            case 5:
               var4.add(toBundle(var0.getMap(var3)));
               break;
            case 6:
               var4.add(toList(var0.getArray(var3)));
               break;
            default:
               throw new IllegalArgumentException("Could not convert object in array.");
            }
         }

         return var4;
      }
   }
}
