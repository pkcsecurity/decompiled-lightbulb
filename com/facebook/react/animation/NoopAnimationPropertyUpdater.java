package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AnimationPropertyUpdater;

public class NoopAnimationPropertyUpdater implements AnimationPropertyUpdater {

   public void onFinish(View var1) {}

   public void onUpdate(View var1, float var2) {}

   public void prepare(View var1) {}
}
