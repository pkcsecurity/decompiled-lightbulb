package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public interface Continuation<TResult extends Object, TContinuationResult extends Object> {

   TContinuationResult then(@NonNull lh<TResult> var1) throws Exception;
}
