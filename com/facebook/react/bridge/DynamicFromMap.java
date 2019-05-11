package com.facebook.react.bridge;

import android.support.v4.util.Pools;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import javax.annotation.Nullable;

public class DynamicFromMap implements Dynamic {

   private static final Pools.SimplePool<DynamicFromMap> sPool = new Pools.SimplePool(10);
   @Nullable
   private ReadableMap mMap;
   @Nullable
   private String mName;


   public static DynamicFromMap create(ReadableMap var0, String var1) {
      DynamicFromMap var3 = (DynamicFromMap)sPool.acquire();
      DynamicFromMap var2 = var3;
      if(var3 == null) {
         var2 = new DynamicFromMap();
      }

      var2.mMap = var0;
      var2.mName = var1;
      return var2;
   }

   public ReadableArray asArray() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.getArray(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public boolean asBoolean() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.getBoolean(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public double asDouble() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.getDouble(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public int asInt() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.getInt(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public ReadableMap asMap() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.getMap(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public String asString() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.getString(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public ReadableType getType() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.getType(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public boolean isNull() {
      if(this.mMap != null && this.mName != null) {
         return this.mMap.isNull(this.mName);
      } else {
         throw new IllegalStateException("This dynamic value has been recycled");
      }
   }

   public void recycle() {
      this.mMap = null;
      this.mName = null;
      sPool.release(this);
   }
}
