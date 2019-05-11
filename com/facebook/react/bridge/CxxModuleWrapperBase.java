package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactBridge;

@DoNotStrip
public class CxxModuleWrapperBase implements NativeModule {

   @DoNotStrip
   private HybridData mHybridData;


   static {
      ReactBridge.staticInit();
   }

   protected CxxModuleWrapperBase(HybridData var1) {
      this.mHybridData = var1;
   }

   public boolean canOverrideExistingModule() {
      return false;
   }

   public native String getName();

   public void initialize() {}

   public void onCatalystInstanceDestroy() {
      this.mHybridData.resetNative();
   }

   protected void resetModule(HybridData var1) {
      if(var1 != this.mHybridData) {
         this.mHybridData.resetNative();
         this.mHybridData = var1;
      }

   }
}
