package io.reactivex;

import io.reactivex.MaybeObserver;
import io.reactivex.annotations.NonNull;

public interface MaybeSource<T extends Object> {

   void subscribe(@NonNull MaybeObserver<? super T> var1);
}
