package com.facebook.react.bridge;

import android.support.v4.util.Pools;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import javax.annotation.Nullable;

public class DynamicFromArray implements Dynamic {

   private static final Pools.SimplePool<DynamicFromArray> sPool = new Pools.SimplePool(10);
   @Nullable
   private ReadableArray mArray;
   private int mIndex = -1;


   public static DynamicFromArray create(ReadableArray var0, int var1) {
      DynamicFromArray var3 = (DynamicFromArray)sPool.acquire();
      DynamicFromArray var2 = var3;
      if(var3 == null) {
         var2 = new DynamicFromArray();
      }

      var2.mArray = var0;
      var2.mIndex = var1;
      return var2;
   }

   public ReadableArray asArray() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.getArray(this.mIndex);
      }
   }

   public boolean asBoolean() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.getBoolean(this.mIndex);
      }
   }

   public double asDouble() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.getDouble(this.mIndex);
      }
   }

   public int asInt() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.getInt(this.mIndex);
      }
   }

   public ReadableMap asMap() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.getMap(this.mIndex);
      }
   }

   public String asString() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.getString(this.mIndex);
      }
   }

   public ReadableType getType() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.getType(this.mIndex);
      }
   }

   public boolean isNull() {
      if(this.mArray == null) {
         throw new IllegalStateException("This dynamic value has been recycled");
      } else {
         return this.mArray.isNull(this.mIndex);
      }
   }

   public void recycle() {
      this.mArray = null;
      this.mIndex = -1;
      sPool.release(this);
   }
}
