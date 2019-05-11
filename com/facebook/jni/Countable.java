package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;

@DoNotStrip
public class Countable {

   @DoNotStrip
   private long mInstance = 0L;


   static {
      SoLoader.loadLibrary("fb");
   }

   public native void dispose();

   protected void finalize() throws Throwable {
      this.dispose();
      super.finalize();
   }
}
