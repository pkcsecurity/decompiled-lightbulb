package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.DynamicFromMap;
import com.facebook.react.bridge.NativeMap;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.ReadableType;
import java.util.HashMap;

@DoNotStrip
public class ReadableNativeMap extends NativeMap implements ReadableMap {

   static {
      ReactBridge.staticInit();
   }

   protected ReadableNativeMap(HybridData var1) {
      super(var1);
   }

   public native ReadableNativeArray getArray(String var1);

   public native boolean getBoolean(String var1);

   public native double getDouble(String var1);

   public Dynamic getDynamic(String var1) {
      return DynamicFromMap.create(this, var1);
   }

   public native int getInt(String var1);

   public native ReadableNativeMap getMap(String var1);

   public native String getString(String var1);

   public native ReadableType getType(String var1);

   public native boolean hasKey(String var1);

   public native boolean isNull(String var1);

   public ReadableMapKeySetIterator keySetIterator() {
      return new ReadableNativeMap.ReadableNativeMapKeySetIterator(this);
   }

   public HashMap<String, Object> toHashMap() {
      ReadableMapKeySetIterator var2 = this.keySetIterator();
      HashMap var3 = new HashMap();

      while(var2.hasNextKey()) {
         String var1 = var2.nextKey();
         switch(null.$SwitchMap$com$facebook$react$bridge$ReadableType[this.getType(var1).ordinal()]) {
         case 1:
            var3.put(var1, (Object)null);
            break;
         case 2:
            var3.put(var1, Boolean.valueOf(this.getBoolean(var1)));
            break;
         case 3:
            var3.put(var1, Double.valueOf(this.getDouble(var1)));
            break;
         case 4:
            var3.put(var1, this.getString(var1));
            break;
         case 5:
            var3.put(var1, this.getMap(var1).toHashMap());
            break;
         case 6:
            var3.put(var1, this.getArray(var1).toArrayList());
            break;
         default:
            StringBuilder var4 = new StringBuilder();
            var4.append("Could not convert object with key: ");
            var4.append(var1);
            var4.append(".");
            throw new IllegalArgumentException(var4.toString());
         }
      }

      return var3;
   }

   @DoNotStrip
   static class ReadableNativeMapKeySetIterator implements ReadableMapKeySetIterator {

      @DoNotStrip
      private final HybridData mHybridData;
      @DoNotStrip
      private final ReadableNativeMap mMap;


      public ReadableNativeMapKeySetIterator(ReadableNativeMap var1) {
         this.mMap = var1;
         this.mHybridData = initHybrid(var1);
      }

      private static native HybridData initHybrid(ReadableNativeMap var0);

      public native boolean hasNextKey();

      public native String nextKey();
   }
}
