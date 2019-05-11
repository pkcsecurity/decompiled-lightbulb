package com.facebook.react.packagerconnection;

import android.os.Looper;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.packagerconnection.RequestOnlyHandler;
import com.facebook.react.packagerconnection.Responder;
import com.facebook.soloader.SoLoader;
import javax.annotation.Nullable;

public class SamplingProfilerPackagerMethod extends RequestOnlyHandler {

   private SamplingProfilerPackagerMethod.SamplingProfilerJniMethod mJniMethod;


   static {
      SoLoader.loadLibrary("packagerconnectionjnifb");
   }

   public SamplingProfilerPackagerMethod(long var1) {
      this.mJniMethod = new SamplingProfilerPackagerMethod.SamplingProfilerJniMethod(var1);
   }

   public void onRequest(@Nullable Object var1, Responder var2) {
      this.mJniMethod.poke(var2);
   }

   static final class SamplingProfilerJniMethod {

      @DoNotStrip
      private final HybridData mHybridData;


      public SamplingProfilerJniMethod(long var1) {
         if(Looper.myLooper() == null) {
            Looper.prepare();
         }

         this.mHybridData = initHybrid(var1);
      }

      @DoNotStrip
      private static native HybridData initHybrid(long var0);

      @DoNotStrip
      private native void poke(Responder var1);
   }
}
