package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.ProducerContext;

public interface Producer<T extends Object> {

   void produceResults(Consumer<T> var1, ProducerContext var2);
}
