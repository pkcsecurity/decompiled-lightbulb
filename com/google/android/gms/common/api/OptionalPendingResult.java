package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

public abstract class OptionalPendingResult<R extends Object & Result> extends PendingResult<R> {

   public abstract R get();

   public abstract boolean isDone();
}
