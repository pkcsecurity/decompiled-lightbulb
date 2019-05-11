package com.facebook;

import android.os.Handler;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.RequestOutputStream;
import com.facebook.RequestProgress;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

class ProgressOutputStream extends FilterOutputStream implements RequestOutputStream {

   private long batchProgress;
   private RequestProgress currentRequestProgress;
   private long lastReportedProgress;
   private long maxProgress;
   private final Map<GraphRequest, RequestProgress> progressMap;
   private final GraphRequestBatch requests;
   private final long threshold;


   ProgressOutputStream(OutputStream var1, GraphRequestBatch var2, Map<GraphRequest, RequestProgress> var3, long var4) {
      super(var1);
      this.requests = var2;
      this.progressMap = var3;
      this.maxProgress = var4;
      this.threshold = FacebookSdk.getOnProgressThreshold();
   }

   private void addProgress(long var1) {
      if(this.currentRequestProgress != null) {
         this.currentRequestProgress.addProgress(var1);
      }

      this.batchProgress += var1;
      if(this.batchProgress >= this.lastReportedProgress + this.threshold || this.batchProgress >= this.maxProgress) {
         this.reportBatchProgress();
      }

   }

   private void reportBatchProgress() {
      if(this.batchProgress > this.lastReportedProgress) {
         Iterator var1 = this.requests.getCallbacks().iterator();

         while(var1.hasNext()) {
            GraphRequestBatch.Callback var3 = (GraphRequestBatch.Callback)var1.next();
            if(var3 instanceof GraphRequestBatch.OnProgressCallback) {
               Handler var2 = this.requests.getCallbackHandler();
               final GraphRequestBatch.OnProgressCallback var4 = (GraphRequestBatch.OnProgressCallback)var3;
               if(var2 == null) {
                  var4.onBatchProgress(this.requests, this.batchProgress, this.maxProgress);
               } else {
                  var2.post(new Runnable() {
                     public void run() {
                        var4.onBatchProgress(ProgressOutputStream.this.requests, ProgressOutputStream.this.batchProgress, ProgressOutputStream.this.maxProgress);
                     }
                  });
               }
            }
         }

         this.lastReportedProgress = this.batchProgress;
      }

   }

   public void close() throws IOException {
      super.close();
      Iterator var1 = this.progressMap.values().iterator();

      while(var1.hasNext()) {
         ((RequestProgress)var1.next()).reportProgress();
      }

      this.reportBatchProgress();
   }

   long getBatchProgress() {
      return this.batchProgress;
   }

   long getMaxProgress() {
      return this.maxProgress;
   }

   public void setCurrentRequest(GraphRequest var1) {
      RequestProgress var2;
      if(var1 != null) {
         var2 = (RequestProgress)this.progressMap.get(var1);
      } else {
         var2 = null;
      }

      this.currentRequestProgress = var2;
   }

   public void write(int var1) throws IOException {
      this.out.write(var1);
      this.addProgress(1L);
   }

   public void write(byte[] var1) throws IOException {
      this.out.write(var1);
      this.addProgress((long)var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
      this.addProgress((long)var3);
   }
}
