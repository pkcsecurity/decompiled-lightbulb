package com.facebook.litho;


public interface PerfEvent {

   int getInstanceKey();

   int getMarkerId();

   void markerAnnotate(String var1, double var2);

   void markerAnnotate(String var1, int var2);

   void markerAnnotate(String var1, String var2);

   void markerAnnotate(String var1, boolean var2);

   void markerAnnotate(String var1, int[] var2);

   void markerAnnotate(String var1, Double[] var2);

   void markerAnnotate(String var1, String[] var2);

   void markerPoint(String var1);
}
