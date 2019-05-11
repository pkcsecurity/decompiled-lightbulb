package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimationBinding;

public interface AnimationBindingListener {

   void onCanceledBeforeStart(AnimationBinding var1);

   void onFinish(AnimationBinding var1);

   void onScheduledToStartLater(AnimationBinding var1);

   void onWillStart(AnimationBinding var1);

   boolean shouldStart(AnimationBinding var1);
}
