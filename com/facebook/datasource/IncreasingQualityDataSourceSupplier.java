package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class IncreasingQualityDataSourceSupplier<T extends Object> implements Supplier<DataSource<T>> {

   private final List<Supplier<DataSource<T>>> mDataSourceSuppliers;


   private IncreasingQualityDataSourceSupplier(List<Supplier<DataSource<T>>> var1) {
      Preconditions.checkArgument(var1.isEmpty() ^ true, "List of suppliers is empty!");
      this.mDataSourceSuppliers = var1;
   }

   public static <T extends Object> IncreasingQualityDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> var0) {
      return new IncreasingQualityDataSourceSupplier(var0);
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof IncreasingQualityDataSourceSupplier)) {
         return false;
      } else {
         IncreasingQualityDataSourceSupplier var2 = (IncreasingQualityDataSourceSupplier)var1;
         return Objects.equal(this.mDataSourceSuppliers, var2.mDataSourceSuppliers);
      }
   }

   public DataSource<T> get() {
      return new IncreasingQualityDataSourceSupplier.IncreasingQualityDataSource();
   }

   public int hashCode() {
      return this.mDataSourceSuppliers.hashCode();
   }

   public String toString() {
      return Objects.toStringHelper((Object)this).add("list", this.mDataSourceSuppliers).toString();
   }

   class InternalDataSubscriber implements DataSubscriber<T> {

      private int mIndex;


      public InternalDataSubscriber(int var2) {
         this.mIndex = var2;
      }

      public void onCancellation(DataSource<T> var1) {}

      public void onFailure(DataSource<T> var1) {
         IncreasingQualityDataSourceSupplier.super.onDataSourceFailed(this.mIndex, var1);
      }

      public void onNewResult(DataSource<T> var1) {
         if(var1.hasResult()) {
            IncreasingQualityDataSourceSupplier.super.onDataSourceNewResult(this.mIndex, var1);
         } else {
            if(var1.isFinished()) {
               IncreasingQualityDataSourceSupplier.super.onDataSourceFailed(this.mIndex, var1);
            }

         }
      }

      public void onProgressUpdate(DataSource<T> var1) {
         if(this.mIndex == 0) {
            IncreasingQualityDataSourceSupplier.this.setProgress(var1.getProgress());
         }

      }
   }

   @ThreadSafe
   class IncreasingQualityDataSource extends AbstractDataSource<T> {

      @Nullable
      @GuardedBy
      private ArrayList<DataSource<T>> mDataSources;
      @GuardedBy
      private int mIndexOfDataSourceWithResult;


      public IncreasingQualityDataSource() {
         int var3 = IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.size();
         this.mIndexOfDataSourceWithResult = var3;
         this.mDataSources = new ArrayList(var3);

         for(int var2 = 0; var2 < var3; ++var2) {
            DataSource var4 = (DataSource)((Supplier)IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.get(var2)).get();
            this.mDataSources.add(var4);
            var4.subscribe(new IncreasingQualityDataSourceSupplier.InternalDataSubscriber(var2), CallerThreadExecutor.getInstance());
            if(var4.hasResult()) {
               return;
            }
         }

      }

      private void closeSafely(DataSource<T> var1) {
         if(var1 != null) {
            var1.close();
         }

      }

      @Nullable
      private DataSource<T> getAndClearDataSource(int param1) {
         // $FF: Couldn't be decompiled
      }

      @Nullable
      private DataSource<T> getDataSource(int var1) {
         synchronized(this){}
         boolean var4 = false;

         DataSource var2;
         try {
            var4 = true;
            if(this.mDataSources != null) {
               if(var1 < this.mDataSources.size()) {
                  var2 = (DataSource)this.mDataSources.get(var1);
                  var4 = false;
                  return var2;
               }

               var4 = false;
            } else {
               var4 = false;
            }
         } finally {
            if(var4) {
               ;
            }
         }

         var2 = null;
         return var2;
      }

      @Nullable
      private DataSource<T> getDataSourceWithResult() {
         synchronized(this){}

         DataSource var1;
         try {
            var1 = this.getDataSource(this.mIndexOfDataSourceWithResult);
         } finally {
            ;
         }

         return var1;
      }

      private void maybeSetIndexOfDataSourceWithResult(int param1, DataSource<T> param2, boolean param3) {
         // $FF: Couldn't be decompiled
      }

      private void onDataSourceFailed(int var1, DataSource<T> var2) {
         this.closeSafely(this.tryGetAndClearDataSource(var1, var2));
         if(var1 == 0) {
            this.setFailure(var2.getFailureCause());
         }

      }

      private void onDataSourceNewResult(int var1, DataSource<T> var2) {
         this.maybeSetIndexOfDataSourceWithResult(var1, var2, var2.isFinished());
         if(var2 == this.getDataSourceWithResult()) {
            boolean var3;
            if(var1 == 0 && var2.isFinished()) {
               var3 = true;
            } else {
               var3 = false;
            }

            this.setResult((Object)null, var3);
         }

      }

      @Nullable
      private DataSource<T> tryGetAndClearDataSource(int param1, DataSource<T> param2) {
         // $FF: Couldn't be decompiled
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
