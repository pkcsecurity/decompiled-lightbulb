package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.util.concurrent.ListenableFuture;

@Beta
@GwtCompatible
public interface CheckedFuture<V extends Object, X extends Exception> extends ListenableFuture<V> {
}
