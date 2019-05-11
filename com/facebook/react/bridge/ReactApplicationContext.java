package com.facebook.react.bridge;

import android.content.Context;
import com.facebook.react.bridge.ReactContext;

public class ReactApplicationContext extends ReactContext {

   public ReactApplicationContext(Context var1) {
      super(var1.getApplicationContext());
   }
}
