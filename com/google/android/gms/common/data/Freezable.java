package com.google.android.gms.common.data;


public interface Freezable<T extends Object> {

   T freeze();

   boolean isDataValid();
}
