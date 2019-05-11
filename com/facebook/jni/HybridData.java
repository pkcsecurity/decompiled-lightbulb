package com.facebook.jni;

import com.facebook.jni.DestructorThread;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;

@DoNotStrip
public class HybridData {

   @DoNotStrip
   private HybridData.Destructor mDestructor = new HybridData.Destructor(this);


   static {
      SoLoader.loadLibrary("fb");
   }

   public boolean isValid() {
      return this.mDestructor.mNativePointer != 0L;
   }

   public void resetNative() {
      synchronized(this){}

      try {
         this.mDestructor.destruct();
      } finally {
         ;
      }

   }

   public static class Destructor extends DestructorThread.Destructor {

      @DoNotStrip
      private long mNativePointer;


      Destructor(Object var1) {
         super(var1);
      }

      static native void deleteNative(long var0);

      void destruct() {
         deleteNative(this.mNativePointer);
         this.mNativePointer = 0L;
      }
   }
}
