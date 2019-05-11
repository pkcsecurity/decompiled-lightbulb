package com.facebook.react.uimanager;

import android.app.Activity;
import android.content.Context;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import javax.annotation.Nullable;

public class ThemedReactContext extends ReactContext {

   private final ReactApplicationContext mReactApplicationContext;


   public ThemedReactContext(ReactApplicationContext var1, Context var2) {
      super(var2);
      this.initializeWithInstance(var1.getCatalystInstance());
      this.mReactApplicationContext = var1;
   }

   public void addLifecycleEventListener(LifecycleEventListener var1) {
      this.mReactApplicationContext.addLifecycleEventListener(var1);
   }

   @Nullable
   public Activity getCurrentActivity() {
      return this.mReactApplicationContext.getCurrentActivity();
   }

   public boolean hasCurrentActivity() {
      return this.mReactApplicationContext.hasCurrentActivity();
   }

   public void removeLifecycleEventListener(LifecycleEventListener var1) {
      this.mReactApplicationContext.removeLifecycleEventListener(var1);
   }
}
