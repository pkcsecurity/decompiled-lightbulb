package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

@ReactModule(
   name = "JSCSamplingProfiler",
   needsEagerInit = true
)
public class JSCSamplingProfiler extends ReactContextBaseJavaModule {

   private static final HashSet<JSCSamplingProfiler> sRegisteredDumpers = new HashSet();
   @Nullable
   private String mOperationError = null;
   private boolean mOperationInProgress = false;
   private int mOperationToken = 0;
   @Nullable
   private JSCSamplingProfiler.SamplingProfiler mSamplingProfiler = null;
   @Nullable
   private String mSamplingProfilerResult = null;


   public JSCSamplingProfiler(ReactApplicationContext var1) {
      super(var1);
   }

   private int getOperationToken() throws JSCSamplingProfiler.ProfilerException {
      if(this.mOperationInProgress) {
         throw new JSCSamplingProfiler.ProfilerException("Another operation already in progress.");
      } else {
         this.mOperationInProgress = true;
         int var1 = this.mOperationToken + 1;
         this.mOperationToken = var1;
         return var1;
      }
   }

   public static List<String> poke(long var0) throws JSCSamplingProfiler.ProfilerException {
      synchronized(JSCSamplingProfiler.class){}

      LinkedList var2;
      try {
         var2 = new LinkedList();
         if(sRegisteredDumpers.isEmpty()) {
            throw new JSCSamplingProfiler.ProfilerException("No JSC registered");
         }

         Iterator var3 = sRegisteredDumpers.iterator();

         while(var3.hasNext()) {
            JSCSamplingProfiler var4 = (JSCSamplingProfiler)var3.next();
            var4.pokeHelper(var0);
            var2.add(var4.mSamplingProfilerResult);
         }
      } finally {
         ;
      }

      return var2;
   }

   private void pokeHelper(long var1) throws JSCSamplingProfiler.ProfilerException {
      synchronized(this){}

      try {
         if(this.mSamplingProfiler == null) {
            throw new JSCSamplingProfiler.ProfilerException("SamplingProfiler.js module not connected");
         }

         this.mSamplingProfiler.poke(this.getOperationToken());
         this.waitForOperation(var1);
      } finally {
         ;
      }

   }

   private static void registerSamplingProfiler(JSCSamplingProfiler var0) {
      synchronized(JSCSamplingProfiler.class){}

      try {
         if(sRegisteredDumpers.contains(var0)) {
            throw new RuntimeException("a JSCSamplingProfiler registered more than once");
         }

         sRegisteredDumpers.add(var0);
      } finally {
         ;
      }

   }

   private static void unregisterSamplingProfiler(JSCSamplingProfiler var0) {
      synchronized(JSCSamplingProfiler.class){}

      try {
         sRegisteredDumpers.remove(var0);
      } finally {
         ;
      }

   }

   private void waitForOperation(long var1) throws JSCSamplingProfiler.ProfilerException {
      try {
         this.wait(var1);
      } catch (InterruptedException var5) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Waiting for heap capture failed: ");
         var4.append(var5.getMessage());
         throw new JSCSamplingProfiler.ProfilerException(var4.toString());
      }

      if(this.mOperationInProgress) {
         this.mOperationInProgress = false;
         throw new JSCSamplingProfiler.ProfilerException("heap capture timed out.");
      } else if(this.mOperationError != null) {
         throw new JSCSamplingProfiler.ProfilerException(this.mOperationError);
      }
   }

   public String getName() {
      return "JSCSamplingProfiler";
   }

   public void initialize() {
      super.initialize();
      this.mSamplingProfiler = (JSCSamplingProfiler.SamplingProfiler)this.getReactApplicationContext().getJSModule(JSCSamplingProfiler.SamplingProfiler.class);
      registerSamplingProfiler(this);
   }

   public void onCatalystInstanceDestroy() {
      super.onCatalystInstanceDestroy();
      unregisterSamplingProfiler(this);
      this.mSamplingProfiler = null;
   }

   @ReactMethod
   public void operationComplete(int var1, String var2, String var3) {
      synchronized(this){}

      try {
         if(var1 != this.mOperationToken) {
            throw new RuntimeException("Completed operation is not in progress.");
         }

         this.mOperationInProgress = false;
         this.mSamplingProfilerResult = var2;
         this.mOperationError = var3;
         this.notify();
      } finally {
         ;
      }

   }

   public static class ProfilerException extends Exception {

      ProfilerException(String var1) {
         super(var1);
      }
   }

   public interface SamplingProfiler extends JavaScriptModule {

      void poke(int var1);
   }
}
