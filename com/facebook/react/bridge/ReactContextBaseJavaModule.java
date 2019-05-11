package com.facebook.react.bridge;

import android.app.Activity;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import javax.annotation.Nullable;

public abstract class ReactContextBaseJavaModule extends BaseJavaModule {

   private final ReactApplicationContext mReactApplicationContext;


   public ReactContextBaseJavaModule(ReactApplicationContext var1) {
      this.mReactApplicationContext = var1;
   }

   @Nullable
   protected final Activity getCurrentActivity() {
      return this.mReactApplicationContext.getCurrentActivity();
   }

   protected final ReactApplicationContext getReactApplicationContext() {
      return this.mReactApplicationContext;
   }
}
