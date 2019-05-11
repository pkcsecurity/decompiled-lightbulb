package com.facebook.datasource;

import com.facebook.datasource.DataSource;

public interface DataSubscriber<T extends Object> {

   void onCancellation(DataSource<T> var1);

   void onFailure(DataSource<T> var1);

   void onNewResult(DataSource<T> var1);

   void onProgressUpdate(DataSource<T> var1);
}
