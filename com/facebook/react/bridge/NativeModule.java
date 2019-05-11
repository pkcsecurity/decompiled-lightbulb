package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JSInstance;
import com.facebook.react.bridge.ReadableNativeArray;

@DoNotStrip
public interface NativeModule {

   boolean canOverrideExistingModule();

   String getName();

   void initialize();

   void onCatalystInstanceDestroy();

   public interface NativeMethod {

      String getType();

      void invoke(JSInstance var1, ReadableNativeArray var2);
   }
}
