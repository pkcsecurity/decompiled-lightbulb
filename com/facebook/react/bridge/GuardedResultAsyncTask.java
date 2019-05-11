package com.facebook.react.bridge;

import android.os.AsyncTask;
import com.facebook.react.bridge.ReactContext;

public abstract class GuardedResultAsyncTask<Result extends Object> extends AsyncTask<Void, Void, Result> {

   private final ReactContext mReactContext;


   protected GuardedResultAsyncTask(ReactContext var1) {
      this.mReactContext = var1;
   }

   protected final Result doInBackground(Void ... var1) {
      try {
         Object var3 = this.doInBackgroundGuarded();
         return var3;
      } catch (RuntimeException var2) {
         this.mReactContext.handleException(var2);
         throw var2;
      }
   }

   protected abstract Result doInBackgroundGuarded();

   protected final void onPostExecute(Result var1) {
      try {
         this.onPostExecuteGuarded(var1);
      } catch (RuntimeException var2) {
         this.mReactContext.handleException(var2);
      }
   }

   protected abstract void onPostExecuteGuarded(Result var1);
}
