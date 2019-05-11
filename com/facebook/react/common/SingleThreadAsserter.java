package com.facebook.react.common;

import com.facebook.infer.annotation.Assertions;
import javax.annotation.Nullable;

public class SingleThreadAsserter {

   @Nullable
   private Thread mThread = null;


   public void assertNow() {
      Thread var2 = Thread.currentThread();
      if(this.mThread == null) {
         this.mThread = var2;
      }

      boolean var1;
      if(this.mThread == var2) {
         var1 = true;
      } else {
         var1 = false;
      }

      Assertions.assertCondition(var1);
   }
}
