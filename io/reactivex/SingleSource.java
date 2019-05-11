package io.reactivex;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

public interface SingleSource<T extends Object> {

   void subscribe(@NonNull SingleObserver<? super T> var1);
}
