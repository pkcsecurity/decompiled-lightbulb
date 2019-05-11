package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;
import com.facebook.yoga.YogaExperimentalFeature;
import com.facebook.yoga.YogaLogger;

@DoNotStrip
public class YogaConfig {

   private YogaLogger mLogger;
   long mNativePointer = this.jni_YGConfigNew();


   static {
      SoLoader.loadLibrary("yoga");
   }

   public YogaConfig() {
      if(this.mNativePointer == 0L) {
         throw new IllegalStateException("Failed to allocate native memory");
      }
   }

   private native void jni_YGConfigFree(long var1);

   private native long jni_YGConfigNew();

   private native void jni_YGConfigSetExperimentalFeatureEnabled(long var1, int var3, boolean var4);

   private native void jni_YGConfigSetLogger(long var1, Object var3);

   private native void jni_YGConfigSetPointScaleFactor(long var1, float var3);

   private native void jni_YGConfigSetUseLegacyStretchBehaviour(long var1, boolean var3);

   private native void jni_YGConfigSetUseWebDefaults(long var1, boolean var3);

   protected void finalize() throws Throwable {
      try {
         this.jni_YGConfigFree(this.mNativePointer);
      } finally {
         super.finalize();
      }

   }

   public YogaLogger getLogger() {
      return this.mLogger;
   }

   public void setExperimentalFeatureEnabled(YogaExperimentalFeature var1, boolean var2) {
      this.jni_YGConfigSetExperimentalFeatureEnabled(this.mNativePointer, var1.intValue(), var2);
   }

   public void setLogger(YogaLogger var1) {
      this.mLogger = var1;
      this.jni_YGConfigSetLogger(this.mNativePointer, var1);
   }

   public void setPointScaleFactor(float var1) {
      this.jni_YGConfigSetPointScaleFactor(this.mNativePointer, var1);
   }

   public void setUseLegacyStretchBehaviour(boolean var1) {
      this.jni_YGConfigSetUseLegacyStretchBehaviour(this.mNativePointer, var1);
   }

   public void setUseWebDefaults(boolean var1) {
      this.jni_YGConfigSetUseWebDefaults(this.mNativePointer, var1);
   }
}
