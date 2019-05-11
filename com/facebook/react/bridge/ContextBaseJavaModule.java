package com.facebook.react.bridge;

import android.content.Context;
import com.facebook.react.bridge.BaseJavaModule;

public abstract class ContextBaseJavaModule extends BaseJavaModule {

   private final Context mContext;


   public ContextBaseJavaModule(Context var1) {
      this.mContext = var1;
   }

   protected final Context getContext() {
      return this.mContext;
   }
}
