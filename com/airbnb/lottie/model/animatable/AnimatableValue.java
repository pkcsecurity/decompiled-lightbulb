package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;

public interface AnimatableValue<K extends Object, A extends Object> {

   BaseKeyframeAnimation<K, A> a();
}
