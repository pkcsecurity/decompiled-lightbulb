package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.infer.annotation.ThreadSafe;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ThreadConfined("ANY")
public class TreeProps {

   private final Map<Class, Object> mMap = Collections.synchronizedMap(new HashMap());


   @ThreadSafe(
      enableChecks = false
   )
   public static TreeProps acquire(TreeProps param0) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   @ThreadSafe(
      enableChecks = false
   )
   public static TreeProps copy(TreeProps var0) {
      return var0 == null?null:acquire(var0);
   }

   @Nullable
   public <T extends Object> T get(Class<T> var1) {
      return this.mMap.get(var1);
   }

   public void put(Class var1, Object var2) {
      this.mMap.put(var1, var2);
   }

   void reset() {
      this.mMap.clear();
   }
}
