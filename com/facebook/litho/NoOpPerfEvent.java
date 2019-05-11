package com.facebook.litho;

import com.facebook.litho.PerfEvent;

public class NoOpPerfEvent implements PerfEvent {

   public int getInstanceKey() {
      return 0;
   }

   public int getMarkerId() {
      return 0;
   }

   public void markerAnnotate(String var1, double var2) {}

   public void markerAnnotate(String var1, int var2) {}

   public void markerAnnotate(String var1, String var2) {}

   public void markerAnnotate(String var1, boolean var2) {}

   public void markerAnnotate(String var1, int[] var2) {}

   public void markerAnnotate(String var1, Double[] var2) {}

   public void markerAnnotate(String var1, String[] var2) {}

   public void markerPoint(String var1) {}
}
