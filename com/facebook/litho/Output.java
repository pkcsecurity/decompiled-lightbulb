package com.facebook.litho;

import com.facebook.infer.annotation.ReturnsOwnership;
import com.facebook.infer.annotation.ThreadSafe;
import javax.annotation.Nullable;

public class Output<T extends Object> {

   @Nullable
   private T mT;


   @Nullable
   @ReturnsOwnership
   public T get() {
      return this.mT;
   }

   void release() {
      this.mT = null;
   }

   @ThreadSafe(
      enableChecks = false
   )
   public void set(@Nullable T var1) {
      this.mT = var1;
   }
}
