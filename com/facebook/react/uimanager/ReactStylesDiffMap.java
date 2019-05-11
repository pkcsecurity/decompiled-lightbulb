package com.facebook.react.uimanager;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import javax.annotation.Nullable;

public class ReactStylesDiffMap {

   final ReadableMap mBackingMap;


   public ReactStylesDiffMap(ReadableMap var1) {
      this.mBackingMap = var1;
   }

   @Nullable
   public ReadableArray getArray(String var1) {
      return this.mBackingMap.getArray(var1);
   }

   public boolean getBoolean(String var1, boolean var2) {
      return this.mBackingMap.isNull(var1)?var2:this.mBackingMap.getBoolean(var1);
   }

   public double getDouble(String var1, double var2) {
      return this.mBackingMap.isNull(var1)?var2:this.mBackingMap.getDouble(var1);
   }

   @Nullable
   public Dynamic getDynamic(String var1) {
      return this.mBackingMap.getDynamic(var1);
   }

   public float getFloat(String var1, float var2) {
      return this.mBackingMap.isNull(var1)?var2:(float)this.mBackingMap.getDouble(var1);
   }

   public int getInt(String var1, int var2) {
      return this.mBackingMap.isNull(var1)?var2:this.mBackingMap.getInt(var1);
   }

   @Nullable
   public ReadableMap getMap(String var1) {
      return this.mBackingMap.getMap(var1);
   }

   @Nullable
   public String getString(String var1) {
      return this.mBackingMap.getString(var1);
   }

   public boolean hasKey(String var1) {
      return this.mBackingMap.hasKey(var1);
   }

   public boolean isNull(String var1) {
      return this.mBackingMap.isNull(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("{ ");
      var1.append(this.getClass().getSimpleName());
      var1.append(": ");
      var1.append(this.mBackingMap.toString());
      var1.append(" }");
      return var1.toString();
   }
}
