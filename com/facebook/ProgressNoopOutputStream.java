package com.facebook;

import android.os.Handler;
import com.facebook.GraphRequest;
import com.facebook.RequestOutputStream;
import com.facebook.RequestProgress;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

class ProgressNoopOutputStream extends OutputStream implements RequestOutputStream {

   private int batchMax;
   private final Handler callbackHandler;
   private GraphRequest currentRequest;
   private RequestProgress currentRequestProgress;
   private final Map<GraphRequest, RequestProgress> progressMap = new HashMap();


   ProgressNoopOutputStream(Handler var1) {
      this.callbackHandler = var1;
   }

   void addProgress(long var1) {
      if(this.currentRequestProgress == null) {
         this.currentRequestProgress = new RequestProgress(this.callbackHandler, this.currentRequest);
         this.progressMap.put(this.currentRequest, this.currentRequestProgress);
      }

      this.currentRequestProgress.addToMax(var1);
      this.batchMax = (int)((long)this.batchMax + var1);
   }

   int getMaxProgress() {
      return this.batchMax;
   }

   Map<GraphRequest, RequestProgress> getProgressMap() {
      return this.progressMap;
   }

   public void setCurrentRequest(GraphRequest var1) {
      this.currentRequest = var1;
      RequestProgress var2;
      if(var1 != null) {
         var2 = (RequestProgress)this.progressMap.get(var1);
      } else {
         var2 = null;
      }

      this.currentRequestProgress = var2;
   }

   public void write(int var1) {
      this.addProgress(1L);
   }

   public void write(byte[] var1) {
      this.addProgress((long)var1.length);
   }

   public void write(byte[] var1, int var2, int var3) {
      this.addProgress((long)var3);
   }
}
