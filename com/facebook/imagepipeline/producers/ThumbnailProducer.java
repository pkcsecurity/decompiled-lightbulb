package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.producers.Producer;

public interface ThumbnailProducer<T extends Object> extends Producer<T> {

   boolean canProvideImageForSize(ResizeOptions var1);
}
