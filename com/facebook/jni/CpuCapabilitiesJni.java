package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;

@DoNotStrip
public class CpuCapabilitiesJni {

   static {
      SoLoader.loadLibrary("fb");
   }

   @DoNotStrip
   public static native boolean nativeDeviceSupportsNeon();

   @DoNotStrip
   public static native boolean nativeDeviceSupportsVFPFP16();

   @DoNotStrip
   public static native boolean nativeDeviceSupportsX86();
}
