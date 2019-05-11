package com.facebook.litho.dataflow;

import android.annotation.TargetApi;

public interface ChoreographerCompat {

   void postFrameCallback(ChoreographerCompat.FrameCallback var1);

   void postFrameCallbackDelayed(ChoreographerCompat.FrameCallback var1, long var2);

   void removeFrameCallback(ChoreographerCompat.FrameCallback var1);

   public abstract static class FrameCallback {

      private android.view.Choreographer.FrameCallback mFrameCallback;
      private Runnable mRunnable;


      public abstract void doFrame(long var1);

      @TargetApi(16)
      android.view.Choreographer.FrameCallback getFrameCallback() {
         if(this.mFrameCallback == null) {
            this.mFrameCallback = new android.view.Choreographer.FrameCallback() {
               public void doFrame(long var1) {
                  FrameCallback.this.doFrame(var1);
               }
            };
         }

         return this.mFrameCallback;
      }

      Runnable getRunnable() {
         if(this.mRunnable == null) {
            this.mRunnable = new Runnable() {
               public void run() {
                  FrameCallback.this.doFrame(System.nanoTime());
               }
            };
         }

         return this.mRunnable;
      }
   }
}
