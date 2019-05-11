package com.facebook;

import android.os.Handler;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;

class RequestProgress {

   private final Handler callbackHandler;
   private long lastReportedProgress;
   private long maxProgress;
   private long progress;
   private final GraphRequest request;
   private final long threshold;


   RequestProgress(Handler var1, GraphRequest var2) {
      this.request = var2;
      this.callbackHandler = var1;
      this.threshold = FacebookSdk.getOnProgressThreshold();
   }

   void addProgress(long var1) {
      this.progress += var1;
      if(this.progress >= this.lastReportedProgress + this.threshold || this.progress >= this.maxProgress) {
         this.reportProgress();
      }

   }

   void addToMax(long var1) {
      this.maxProgress += var1;
   }

   long getMaxProgress() {
      return this.maxProgress;
   }

   long getProgress() {
      return this.progress;
   }

   void reportProgress() {
      if(this.progress > this.lastReportedProgress) {
         GraphRequest.Callback var5 = this.request.getCallback();
         if(this.maxProgress > 0L && var5 instanceof GraphRequest.OnProgressCallback) {
            final long var1 = this.progress;
            final long var3 = this.maxProgress;
            final GraphRequest.OnProgressCallback var6 = (GraphRequest.OnProgressCallback)var5;
            if(this.callbackHandler == null) {
               var6.onProgress(var1, var3);
            } else {
               this.callbackHandler.post(new Runnable() {
                  public void run() {
                     var6.onProgress(var1, var3);
                  }
               });
            }

            this.lastReportedProgress = this.progress;
         }
      }

   }
}
