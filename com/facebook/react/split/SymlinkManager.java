package com.facebook.react.split;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.ReactBridge;

public class SymlinkManager {

   public HybridData mHybridData;


   static {
      ReactBridge.staticInit();
   }

   public SymlinkManager(String var1) {
      this.mHybridData = initHybrid(var1);
   }

   public static native HybridData initHybrid(String var0);

   public native int createSymlink(String var1, String var2);

   public native int symlinkFilesInsideDir(String var1, String var2);

   public native int unlinkFiles(String var1);
}
