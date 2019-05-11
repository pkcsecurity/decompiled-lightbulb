package com.airbnb.lottie;

import android.support.v4.util.ArraySet;
import android.support.v4.util.Pair;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PerformanceTracker {

   private boolean a = false;
   private final Set<PerformanceTracker.FrameListener> b = new ArraySet();
   private final Map<String, ew> c = new HashMap();
   private final Comparator<Pair<String, Float>> d = new Comparator() {
      public int a(Pair<String, Float> var1, Pair<String, Float> var2) {
         float var3 = ((Float)var1.second).floatValue();
         float var4 = ((Float)var2.second).floatValue();
         return var4 > var3?1:(var3 > var4?-1:0);
      }
      // $FF: synthetic method
      public int compare(Object var1, Object var2) {
         return this.a((Pair)var1, (Pair)var2);
      }
   };


   public void a(String var1, float var2) {
      if(this.a) {
         ew var4 = (ew)this.c.get(var1);
         ew var3 = var4;
         if(var4 == null) {
            var3 = new ew();
            this.c.put(var1, var3);
         }

         var3.a(var2);
         if(var1.equals("__container")) {
            Iterator var5 = this.b.iterator();

            while(var5.hasNext()) {
               ((PerformanceTracker.FrameListener)var5.next()).a(var2);
            }
         }

      }
   }

   public void a(boolean var1) {
      this.a = var1;
   }

   public interface FrameListener {

      void a(float var1);
   }
}
