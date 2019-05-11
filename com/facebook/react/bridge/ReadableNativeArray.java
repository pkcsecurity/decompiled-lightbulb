package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.DynamicFromArray;
import com.facebook.react.bridge.NativeArray;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.ReadableType;
import java.util.ArrayList;

@DoNotStrip
public class ReadableNativeArray extends NativeArray implements ReadableArray {

   static {
      ReactBridge.staticInit();
   }

   protected ReadableNativeArray(HybridData var1) {
      super(var1);
   }

   public native ReadableNativeArray getArray(int var1);

   public native boolean getBoolean(int var1);

   public native double getDouble(int var1);

   public Dynamic getDynamic(int var1) {
      return DynamicFromArray.create(this, var1);
   }

   public native int getInt(int var1);

   public native ReadableNativeMap getMap(int var1);

   public native String getString(int var1);

   public native ReadableType getType(int var1);

   public native boolean isNull(int var1);

   public native int size();

   public ArrayList<Object> toArrayList() {
      ArrayList var2 = new ArrayList();

      for(int var1 = 0; var1 < this.size(); ++var1) {
         switch(null.$SwitchMap$com$facebook$react$bridge$ReadableType[this.getType(var1).ordinal()]) {
         case 1:
            var2.add((Object)null);
            break;
         case 2:
            var2.add(Boolean.valueOf(this.getBoolean(var1)));
            break;
         case 3:
            var2.add(Double.valueOf(this.getDouble(var1)));
            break;
         case 4:
            var2.add(this.getString(var1));
            break;
         case 5:
            var2.add(this.getMap(var1).toHashMap());
            break;
         case 6:
            var2.add(this.getArray(var1).toArrayList());
            break;
         default:
            StringBuilder var3 = new StringBuilder();
            var3.append("Could not convert object at index: ");
            var3.append(var1);
            var3.append(".");
            throw new IllegalArgumentException(var3.toString());
         }
      }

      return var2;
   }
}
