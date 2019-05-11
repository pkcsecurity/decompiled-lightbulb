package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.CxxModuleWrapperBase;
import com.facebook.soloader.SoLoader;

@DoNotStrip
public class CxxModuleWrapper extends CxxModuleWrapperBase {

   protected CxxModuleWrapper(HybridData var1) {
      super(var1);
   }

   public static CxxModuleWrapper makeDso(String var0, String var1) {
      SoLoader.loadLibrary(var0);
      return makeDsoNative(SoLoader.unpackLibraryAndDependencies(var0).getAbsolutePath(), var1);
   }

   private static native CxxModuleWrapper makeDsoNative(String var0, String var1);
}
