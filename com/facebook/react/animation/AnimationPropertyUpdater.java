package com.facebook.react.animation;

import android.view.View;

public interface AnimationPropertyUpdater {

   void onFinish(View var1);

   void onUpdate(View var1, float var2);

   void prepare(View var1);
}
