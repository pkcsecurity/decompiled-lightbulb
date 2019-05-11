package com.facebook.litho.dataflow.springs;

import com.facebook.litho.dataflow.springs.Spring;

public interface SpringListener {

   void onSpringActivate(Spring var1);

   void onSpringAtRest(Spring var1);

   void onSpringEndStateChange(Spring var1);

   void onSpringUpdate(Spring var1);
}
