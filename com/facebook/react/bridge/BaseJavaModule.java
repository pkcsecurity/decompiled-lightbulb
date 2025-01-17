package com.facebook.react.bridge;

import com.facebook.react.bridge.NativeModule;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class BaseJavaModule implements NativeModule {

   public static final String METHOD_TYPE_ASYNC = "async";
   public static final String METHOD_TYPE_PROMISE = "promise";
   public static final String METHOD_TYPE_SYNC = "sync";


   public boolean canOverrideExistingModule() {
      return false;
   }

   @Nullable
   public Map<String, Object> getConstants() {
      return null;
   }

   public boolean hasConstants() {
      return false;
   }

   public void initialize() {}

   public void onCatalystInstanceDestroy() {}
}
