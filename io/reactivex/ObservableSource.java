package io.reactivex;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

public interface ObservableSource<T extends Object> {

   void subscribe(@NonNull Observer<? super T> var1);
}
