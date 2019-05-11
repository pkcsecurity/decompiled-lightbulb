package io.reactivex;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;

public interface CompletableSource {

   void subscribe(@NonNull CompletableObserver var1);
}
