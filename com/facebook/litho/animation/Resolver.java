package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimatedPropertyNode;
import com.facebook.litho.animation.PropertyHandle;

public interface Resolver {

   AnimatedPropertyNode getAnimatedPropertyNode(PropertyHandle var1);

   float getCurrentState(PropertyHandle var1);
}
