package com.facebook.litho;


public interface LayoutHandler {

   boolean post(Runnable var1);

   void removeCallbacks(Runnable var1);

   void removeCallbacksAndMessages(Object var1);
}
