package com.facebook.litho;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.facebook.litho.ComponentsPools;
import java.util.WeakHashMap;

public class ComponentsLifecycles {

   private static WeakHashMap<Context, ComponentsLifecycles.LeakDetector> mTrackedContexts;


   public static void onActivityCreated(Activity var0, Bundle var1) {
      onContextCreated(var0);
   }

   public static void onActivityDestroyed(Activity var0) {
      onContextDestroyed(var0);
   }

   public static void onContextCreated(Activity var0) {
      ComponentsPools.sIsManualCallbacks = true;
      onContextCreated((Context)var0);
   }

   public static void onContextCreated(Context var0) {
      if(mTrackedContexts == null) {
         mTrackedContexts = new WeakHashMap();
      }

      if((ComponentsLifecycles.LeakDetector)mTrackedContexts.put(var0, new ComponentsLifecycles.LeakDetector(var0)) != null) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Duplicate onContextCreated call for: ");
         var1.append(var0);
         throw new RuntimeException(var1.toString());
      } else {
         ComponentsPools.onContextCreated(var0);
      }
   }

   public static void onContextDestroyed(Context var0) {
      StringBuilder var2;
      if(mTrackedContexts == null) {
         var2 = new StringBuilder();
         var2.append("onContextDestroyed called without onContextCreated for: ");
         var2.append(var0);
         throw new RuntimeException(var2.toString());
      } else {
         ComponentsLifecycles.LeakDetector var1 = (ComponentsLifecycles.LeakDetector)mTrackedContexts.remove(var0);
         if(var1 == null) {
            var2 = new StringBuilder();
            var2.append("onContextDestroyed called without onContextCreated for: ");
            var2.append(var0);
            throw new RuntimeException(var2.toString());
         } else {
            var1.clear();
            ComponentsPools.onContextDestroyed(var0);
         }
      }
   }

   static class LeakDetector {

      private Context mContext;


      LeakDetector(Context var1) {
         this.mContext = var1;
      }

      void clear() {
         this.mContext = null;
      }

      public void finalize() {
         if(this.mContext != null) {
            final Context var1 = this.mContext;
            (new Handler(Looper.getMainLooper())).post(new Runnable() {
               public void run() {
                  StringBuilder var1x = new StringBuilder();
                  var1x.append("onContextDestroyed method not called for: ");
                  var1x.append(var1);
                  throw new RuntimeException(var1x.toString());
               }
            });
         }

      }
   }
}
