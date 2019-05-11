package com.facebook.react.views.art;

import com.facebook.react.bridge.ReadableArray;
import javax.annotation.Nullable;

class PropHelper {

   static int toFloatArray(ReadableArray var0, float[] var1) {
      int var2;
      if(var0.size() > var1.length) {
         var2 = var1.length;
      } else {
         var2 = var0.size();
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3] = (float)var0.getDouble(var3);
      }

      return var0.size();
   }

   @Nullable
   static float[] toFloatArray(@Nullable ReadableArray var0) {
      if(var0 != null) {
         float[] var1 = new float[var0.size()];
         toFloatArray(var0, var1);
         return var1;
      } else {
         return null;
      }
   }
}
