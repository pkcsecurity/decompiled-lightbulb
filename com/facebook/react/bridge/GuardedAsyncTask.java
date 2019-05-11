package com.facebook.react.bridge;

import android.os.AsyncTask;
import com.facebook.react.bridge.ReactContext;

public abstract class GuardedAsyncTask<Params extends Object, Progress extends Object> extends AsyncTask<Params, Progress, Void> {

   private final ReactContext mReactContext;


   protected GuardedAsyncTask(ReactContext var1) {
      this.mReactContext = var1;
   }

   protected final Void doInBackground(Params ... var1) {
      try {
         this.doInBackgroundGuarded(var1);
      } catch (RuntimeException var2) {
         this.mReactContext.handleException(var2);
      }

      return null;
   }

   protected abstract void doInBackgroundGuarded(Params ... var1);
}
