package com.facebook.litho;

import android.support.annotation.VisibleForTesting;

public final class Diff<T extends Object> {

   T mNext;
   T mPrevious;


   public T getNext() {
      return this.mNext;
   }

   public T getPrevious() {
      return this.mPrevious;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public void init(T var1, T var2) {
      this.mPrevious = var1;
      this.mNext = var2;
   }

   void release() {
      this.mPrevious = null;
      this.mNext = null;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Diff{mPrevious=");
      var1.append(this.mPrevious);
      var1.append(", mNext=");
      var1.append(this.mNext);
      var1.append('}');
      return var1.toString();
   }
}
