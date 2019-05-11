package com.facebook.imagepipeline.animated.factory;

import android.content.Context;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public interface AnimatedFactory {

   AnimatedDrawableFactory getAnimatedDrawableFactory(Context var1);

   AnimatedImageFactory getAnimatedImageFactory();
}
