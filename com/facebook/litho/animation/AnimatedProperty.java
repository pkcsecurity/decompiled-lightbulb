package com.facebook.litho.animation;

import com.facebook.litho.AnimatableItem;

public interface AnimatedProperty {

   float get(AnimatableItem var1);

   float get(Object var1);

   String getName();

   void reset(Object var1);

   void set(Object var1, float var2);
}
