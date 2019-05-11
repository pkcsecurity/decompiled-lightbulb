package com.facebook.react.modules.fresco;

import android.util.Pair;
import com.facebook.imagepipeline.listener.BaseRequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.systrace.Systrace;
import java.util.HashMap;
import java.util.Map;

public class SystraceRequestListener extends BaseRequestListener {

   int mCurrentID = 0;
   Map<String, Pair<Integer, String>> mProducerID = new HashMap();
   Map<String, Pair<Integer, String>> mRequestsID = new HashMap();


   public void onProducerEvent(String var1, String var2, String var3) {
      if(Systrace.isTracing(0L)) {
         StringBuilder var4 = new StringBuilder();
         var4.append("FRESCO_PRODUCER_EVENT_");
         var4.append(var1.replace(':', '_'));
         var4.append("_");
         var4.append(var2.replace(':', '_'));
         var4.append("_");
         var4.append(var3.replace(':', '_'));
         Systrace.traceInstant(0L, var4.toString(), Systrace.EventScope.THREAD);
      }
   }

   public void onProducerFinishWithCancellation(String var1, String var2, Map<String, String> var3) {
      if(Systrace.isTracing(0L)) {
         if(this.mProducerID.containsKey(var1)) {
            Pair var4 = (Pair)this.mProducerID.get(var1);
            Systrace.endAsyncSection(0L, (String)var4.second, ((Integer)var4.first).intValue());
            this.mProducerID.remove(var1);
         }

      }
   }

   public void onProducerFinishWithFailure(String var1, String var2, Throwable var3, Map<String, String> var4) {
      if(Systrace.isTracing(0L)) {
         if(this.mProducerID.containsKey(var1)) {
            Pair var5 = (Pair)this.mProducerID.get(var1);
            Systrace.endAsyncSection(0L, (String)var5.second, ((Integer)var5.first).intValue());
            this.mProducerID.remove(var1);
         }

      }
   }

   public void onProducerFinishWithSuccess(String var1, String var2, Map<String, String> var3) {
      if(Systrace.isTracing(0L)) {
         if(this.mProducerID.containsKey(var1)) {
            Pair var4 = (Pair)this.mProducerID.get(var1);
            Systrace.endAsyncSection(0L, (String)var4.second, ((Integer)var4.first).intValue());
            this.mProducerID.remove(var1);
         }

      }
   }

   public void onProducerStart(String var1, String var2) {
      if(Systrace.isTracing(0L)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("FRESCO_PRODUCER_");
         var3.append(var2.replace(':', '_'));
         Pair var4 = Pair.create(Integer.valueOf(this.mCurrentID), var3.toString());
         Systrace.beginAsyncSection(0L, (String)var4.second, this.mCurrentID);
         this.mProducerID.put(var1, var4);
         ++this.mCurrentID;
      }
   }

   public void onRequestCancellation(String var1) {
      if(Systrace.isTracing(0L)) {
         if(this.mRequestsID.containsKey(var1)) {
            Pair var2 = (Pair)this.mRequestsID.get(var1);
            Systrace.endAsyncSection(0L, (String)var2.second, ((Integer)var2.first).intValue());
            this.mRequestsID.remove(var1);
         }

      }
   }

   public void onRequestFailure(ImageRequest var1, String var2, Throwable var3, boolean var4) {
      if(Systrace.isTracing(0L)) {
         if(this.mRequestsID.containsKey(var2)) {
            Pair var5 = (Pair)this.mRequestsID.get(var2);
            Systrace.endAsyncSection(0L, (String)var5.second, ((Integer)var5.first).intValue());
            this.mRequestsID.remove(var2);
         }

      }
   }

   public void onRequestStart(ImageRequest var1, Object var2, String var3, boolean var4) {
      if(Systrace.isTracing(0L)) {
         StringBuilder var6 = new StringBuilder();
         var6.append("FRESCO_REQUEST_");
         var6.append(var1.getSourceUri().toString().replace(':', '_'));
         Pair var5 = Pair.create(Integer.valueOf(this.mCurrentID), var6.toString());
         Systrace.beginAsyncSection(0L, (String)var5.second, this.mCurrentID);
         this.mRequestsID.put(var3, var5);
         ++this.mCurrentID;
      }
   }

   public void onRequestSuccess(ImageRequest var1, String var2, boolean var3) {
      if(Systrace.isTracing(0L)) {
         if(this.mRequestsID.containsKey(var2)) {
            Pair var4 = (Pair)this.mRequestsID.get(var2);
            Systrace.endAsyncSection(0L, (String)var4.second, ((Integer)var4.first).intValue());
            this.mRequestsID.remove(var2);
         }

      }
   }

   public boolean requiresExtraMap(String var1) {
      return false;
   }
}
