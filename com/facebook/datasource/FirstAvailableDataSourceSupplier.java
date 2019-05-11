package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class FirstAvailableDataSourceSupplier<T extends Object> implements Supplier<DataSource<T>> {

   private final List<Supplier<DataSource<T>>> mDataSourceSuppliers;


   private FirstAvailableDataSourceSupplier(List<Supplier<DataSource<T>>> var1) {
      Preconditions.checkArgument(var1.isEmpty() ^ true, "List of suppliers is empty!");
      this.mDataSourceSuppliers = var1;
   }

   public static <T extends Object> FirstAvailableDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> var0) {
      return new FirstAvailableDataSourceSupplier(var0);
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof FirstAvailableDataSourceSupplier)) {
         return false;
      } else {
         FirstAvailableDataSourceSupplier var2 = (FirstAvailableDataSourceSupplier)var1;
         return Objects.equal(this.mDataSourceSuppliers, var2.mDataSourceSuppliers);
      }
   }

   public DataSource<T> get() {
      return new FirstAvailableDataSourceSupplier.FirstAvailableDataSource();
   }

   public int hashCode() {
      return this.mDataSourceSuppliers.hashCode();
   }

   public String toString() {
      return Objects.toStringHelper((Object)this).add("list", this.mDataSourceSuppliers).toString();
   }

   class InternalDataSubscriber implements DataSubscriber<T> {

      private InternalDataSubscriber() {}

      // $FF: synthetic method
      InternalDataSubscriber(Object var2) {
         this();
      }

      public void onCancellation(DataSource<T> var1) {}

      public void onFailure(DataSource<T> var1) {
         FirstAvailableDataSourceSupplier.super.onDataSourceFailed(var1);
      }

      public void onNewResult(DataSource<T> var1) {
         if(var1.hasResult()) {
            FirstAvailableDataSourceSupplier.super.onDataSourceNewResult(var1);
         } else {
            if(var1.isFinished()) {
               FirstAvailableDataSourceSupplier.super.onDataSourceFailed(var1);
            }

         }
      }

      public void onProgressUpdate(DataSource<T> var1) {
         float var2 = FirstAvailableDataSourceSupplier.this.getProgress();
         FirstAvailableDataSourceSupplier.this.setProgress(Math.max(var2, var1.getProgress()));
      }
   }

   @ThreadSafe
   class FirstAvailableDataSource extends AbstractDataSource<T> {

      private DataSource<T> mCurrentDataSource = null;
      private DataSource<T> mDataSourceWithResult = null;
      private int mIndex = 0;


      public FirstAvailableDataSource() {
         if(!this.startNextDataSource()) {
            this.setFailure(new RuntimeException("No data source supplier or supplier returned null."));
         }

      }

      private boolean clearCurrentDataSource(DataSource<T> var1) {
         synchronized(this){}

         try {
            if(!this.isClosed() && var1 == this.mCurrentDataSource) {
               this.mCurrentDataSource = null;
               return true;
            }
         } finally {
            ;
         }

         return false;
      }

      private void closeSafely(DataSource<T> var1) {
         if(var1 != null) {
            var1.close();
         }

      }

      @Nullable
      private DataSource<T> getDataSourceWithResult() {
         synchronized(this){}

         DataSource var1;
         try {
            var1 = this.mDataSourceWithResult;
         } finally {
            ;
         }

         return var1;
      }

      @Nullable
      private Supplier<DataSource<T>> getNextSupplier() {
         synchronized(this){}

         Supplier var5;
         try {
            if(this.isClosed() || this.mIndex >= FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers.size()) {
               return null;
            }

            List var2 = FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers;
            int var1 = this.mIndex;
            this.mIndex = var1 + 1;
            var5 = (Supplier)var2.get(var1);
         } finally {
            ;
         }

         return var5;
      }

      private void maybeSetDataSourceWithResult(DataSource<T> param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }

      private void onDataSourceFailed(DataSource<T> var1) {
         if(this.clearCurrentDataSource(var1)) {
            if(var1 != this.getDataSourceWithResult()) {
               this.closeSafely(var1);
            }

            if(!this.startNextDataSource()) {
               this.setFailure(var1.getFailureCause());
            }

         }
      }

      private void onDataSourceNewResult(DataSource<T> var1) {
         this.maybeSetDataSourceWithResult(var1, var1.isFinished());
         if(var1 == this.getDataSourceWithResult()) {
            this.setResult((Object)null, var1.isFinished());
         }

      }

      private boolean setCurrentDataSource(DataSource<T> param1) {
         // $FF: Couldn't be decompiled
      }

      private boolean startNextDataSource() {
         Supplier var1 = this.getNextSupplier();
         DataSource var2;
         if(var1 != null) {
            var2 = (DataSource)var1.get();
         } else {
            var2 = null;
         }

         if(this.setCurrentDataSource(var2) && var2 != null) {
            var2.subscribe(new FirstAvailableDataSourceSupplier.InternalDataSubscriber(null), CallerThreadExecutor.getInstance());
            return true;
         } else {
            this.closeSafely(var2);
            return false;
         }
      }

      public boolean close() {
         // $FF: Couldn't be decompiled
      }

      @Nullable
      public T getResult() {
         // $FF: Couldn't be decompiled
      }

      public boolean hasResult() {
         // $FF: Couldn't be decompiled
      }
   }
}
