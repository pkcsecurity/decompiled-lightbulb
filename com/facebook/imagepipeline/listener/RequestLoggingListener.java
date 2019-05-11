package com.facebook.imagepipeline.listener;

import android.os.SystemClock;
import android.util.Pair;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class RequestLoggingListener implements RequestListener {

   private static final String TAG = "RequestLoggingListener";
   @GuardedBy
   private final Map<Pair<String, String>, Long> mProducerStartTimeMap = new HashMap();
   @GuardedBy
   private final Map<String, Long> mRequestStartTimeMap = new HashMap();


   private static long getElapsedTime(@Nullable Long var0, long var1) {
      return var0 != null?var1 - var0.longValue():-1L;
   }

   private static long getTime() {
      return SystemClock.uptimeMillis();
   }

   public void onProducerEvent(String var1, String var2, String var3) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            Pair var6 = Pair.create(var1, var2);
            Long var9 = (Long)this.mProducerStartTimeMap.get(var6);
            long var4 = getTime();
            FLog.v("RequestLoggingListener", "time %d: onProducerEvent: {requestId: %s, stage: %s, eventName: %s; elapsedTime: %d ms}", new Object[]{Long.valueOf(getTime()), var1, var2, var3, Long.valueOf(getElapsedTime(var9, var4))});
         }
      } finally {
         ;
      }

   }

   public void onProducerFinishWithCancellation(String var1, String var2, @Nullable Map<String, String> var3) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            Pair var6 = Pair.create(var1, var2);
            Long var9 = (Long)this.mProducerStartTimeMap.remove(var6);
            long var4 = getTime();
            FLog.v("RequestLoggingListener", "time %d: onProducerFinishWithCancellation: {requestId: %s, stage: %s, elapsedTime: %d ms, extraMap: %s}", new Object[]{Long.valueOf(var4), var1, var2, Long.valueOf(getElapsedTime(var9, var4)), var3});
         }
      } finally {
         ;
      }

   }

   public void onProducerFinishWithFailure(String var1, String var2, Throwable var3, @Nullable Map<String, String> var4) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(5)) {
            Pair var7 = Pair.create(var1, var2);
            Long var10 = (Long)this.mProducerStartTimeMap.remove(var7);
            long var5 = getTime();
            FLog.w("RequestLoggingListener", "time %d: onProducerFinishWithFailure: {requestId: %s, stage: %s, elapsedTime: %d ms, extraMap: %s, throwable: %s}", new Object[]{Long.valueOf(var5), var1, var2, Long.valueOf(getElapsedTime(var10, var5)), var4, var3.toString()});
         }
      } finally {
         ;
      }

   }

   public void onProducerFinishWithSuccess(String var1, String var2, @Nullable Map<String, String> var3) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            Pair var6 = Pair.create(var1, var2);
            Long var9 = (Long)this.mProducerStartTimeMap.remove(var6);
            long var4 = getTime();
            FLog.v("RequestLoggingListener", "time %d: onProducerFinishWithSuccess: {requestId: %s, producer: %s, elapsedTime: %d ms, extraMap: %s}", new Object[]{Long.valueOf(var4), var1, var2, Long.valueOf(getElapsedTime(var9, var4)), var3});
         }
      } finally {
         ;
      }

   }

   public void onProducerStart(String var1, String var2) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            Pair var5 = Pair.create(var1, var2);
            long var3 = getTime();
            this.mProducerStartTimeMap.put(var5, Long.valueOf(var3));
            FLog.v("RequestLoggingListener", "time %d: onProducerStart: {requestId: %s, producer: %s}", Long.valueOf(var3), var1, var2);
         }
      } finally {
         ;
      }

   }

   public void onRequestCancellation(String var1) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            Long var4 = (Long)this.mRequestStartTimeMap.remove(var1);
            long var2 = getTime();
            FLog.v("RequestLoggingListener", "time %d: onRequestCancellation: {requestId: %s, elapsedTime: %d ms}", Long.valueOf(var2), var1, Long.valueOf(getElapsedTime(var4, var2)));
         }
      } finally {
         ;
      }

   }

   public void onRequestFailure(ImageRequest var1, String var2, Throwable var3, boolean var4) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(5)) {
            Long var9 = (Long)this.mRequestStartTimeMap.remove(var2);
            long var5 = getTime();
            FLog.w("RequestLoggingListener", "time %d: onRequestFailure: {requestId: %s, elapsedTime: %d ms, throwable: %s}", new Object[]{Long.valueOf(var5), var2, Long.valueOf(getElapsedTime(var9, var5)), var3.toString()});
         }
      } finally {
         ;
      }

   }

   public void onRequestStart(ImageRequest var1, Object var2, String var3, boolean var4) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            FLog.v("RequestLoggingListener", "time %d: onRequestSubmit: {requestId: %s, callerContext: %s, isPrefetch: %b}", Long.valueOf(getTime()), var3, var2, Boolean.valueOf(var4));
            this.mRequestStartTimeMap.put(var3, Long.valueOf(getTime()));
         }
      } finally {
         ;
      }

   }

   public void onRequestSuccess(ImageRequest var1, String var2, boolean var3) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            Long var8 = (Long)this.mRequestStartTimeMap.remove(var2);
            long var4 = getTime();
            FLog.v("RequestLoggingListener", "time %d: onRequestSuccess: {requestId: %s, elapsedTime: %d ms}", Long.valueOf(var4), var2, Long.valueOf(getElapsedTime(var8, var4)));
         }
      } finally {
         ;
      }

   }

   public void onUltimateProducerReached(String var1, String var2, boolean var3) {
      synchronized(this){}

      try {
         if(FLog.isLoggable(2)) {
            Pair var6 = Pair.create(var1, var2);
            Long var9 = (Long)this.mProducerStartTimeMap.remove(var6);
            long var4 = getTime();
            FLog.v("RequestLoggingListener", "time %d: onUltimateProducerReached: {requestId: %s, producer: %s, elapsedTime: %d ms, success: %b}", new Object[]{Long.valueOf(var4), var1, var2, Long.valueOf(getElapsedTime(var9, var4)), Boolean.valueOf(var3)});
         }
      } finally {
         ;
      }

   }

   public boolean requiresExtraMap(String var1) {
      return FLog.isLoggable(2);
   }
}
