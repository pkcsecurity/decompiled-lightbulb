package com.facebook.datasource;

import com.facebook.common.internal.Supplier;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.SimpleDataSource;

public class DataSources {

   public static <T extends Object> Supplier<DataSource<T>> getFailedDataSourceSupplier(final Throwable var0) {
      return new Supplier() {
         public DataSource<T> get() {
            return DataSources.immediateFailedDataSource(var0);
         }
      };
   }

   public static <T extends Object> DataSource<T> immediateDataSource(T var0) {
      SimpleDataSource var1 = SimpleDataSource.create();
      var1.setResult(var0);
      return var1;
   }

   public static <T extends Object> DataSource<T> immediateFailedDataSource(Throwable var0) {
      SimpleDataSource var1 = SimpleDataSource.create();
      var1.setFailure(var0);
      return var1;
   }
}
