package com.facebook.react;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import com.facebook.react.bridge.MemoryPressureListener;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class MemoryPressureRouter implements ComponentCallbacks2 {

   private final Set<MemoryPressureListener> mListeners = Collections.synchronizedSet(new LinkedHashSet());


   MemoryPressureRouter(Context var1) {
      var1.getApplicationContext().registerComponentCallbacks(this);
   }

   private void dispatchMemoryPressure(int var1) {
      MemoryPressureListener[] var4 = (MemoryPressureListener[])this.mListeners.toArray(new MemoryPressureListener[this.mListeners.size()]);
      int var3 = var4.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         var4[var2].handleMemoryPressure(var1);
      }

   }

   public void addMemoryPressureListener(MemoryPressureListener var1) {
      this.mListeners.add(var1);
   }

   public void destroy(Context var1) {
      var1.getApplicationContext().unregisterComponentCallbacks(this);
   }

   public void onConfigurationChanged(Configuration var1) {}

   public void onLowMemory() {}

   public void onTrimMemory(int var1) {
      this.dispatchMemoryPressure(var1);
   }

   public void removeMemoryPressureListener(MemoryPressureListener var1) {
      this.mListeners.remove(var1);
   }
}
