package org.reactivestreams;

import org.reactivestreams.Subscriber;

public interface Publisher<T extends Object> {

   void subscribe(Subscriber<? super T> var1);
}
