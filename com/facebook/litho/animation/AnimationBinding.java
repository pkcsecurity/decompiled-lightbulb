package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimationBindingListener;
import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.Resolver;
import java.util.ArrayList;

public interface AnimationBinding {

   void addListener(AnimationBindingListener var1);

   void collectTransitioningProperties(ArrayList<PropertyAnimation> var1);

   boolean isActive();

   void prepareToStartLater();

   void removeListener(AnimationBindingListener var1);

   void start(Resolver var1);

   void stop();
}
