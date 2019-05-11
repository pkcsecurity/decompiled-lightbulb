package com.facebook.imagepipeline.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import java.util.List;
import java.util.concurrent.CancellationException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class ListDataSource<T extends Object> extends AbstractDataSource<List<CloseableReference<T>>> {

   private final DataSource<CloseableReference<T>>[] mDataSources;
   @GuardedBy
   private int mFinishedDataSources;


   protected ListDataSource(DataSource<CloseableReference<T>>[] var1) {
      this.mDataSources = var1;
      this.mFinishedDataSources = 0;
   }

   public static <T extends Object> ListDataSource<T> create(DataSource<CloseableReference<T>> ... var0) {
      Preconditions.checkNotNull(var0);
      int var2 = var0.length;
      int var1 = 0;
      boolean var3;
      if(var2 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3);
      ListDataSource var4 = new ListDataSource(var0);

      for(var2 = var0.length; var1 < var2; ++var1) {
         DataSource var5 = var0[var1];
         if(var5 != null) {
            var5.subscribe(var4.new InternalDataSubscriber(null), CallerThreadExecutor.getInstance());
         }
      }

      return var4;
   }

   private boolean increaseAndCheckIfLast() {
      // $FF: Couldn't be decompiled
   }

   private void onDataSourceCancelled() {
      this.setFailure(new CancellationException());
   }

   private void onDataSourceFailed(DataSource<CloseableReference<T>> var1) {
      this.setFailure(var1.getFailureCause());
   }

   private void onDataSourceFinished() {
      if(this.increaseAndCheckIfLast()) {
         this.setResult((Object)null, true);
      }

   }

   private void onDataSourceProgress() {
      DataSource[] var4 = this.mDataSources;
      int var3 = var4.length;
      float var1 = 0.0F;

      for(int var2 = 0; var2 < var3; ++var2) {
         var1 += var4[var2].getProgress();
      }

      this.setProgress(var1 / (float)this.mDataSources.length);
   }

   public boolean close() {
      boolean var3 = super.close();
      int var1 = 0;
      if(!var3) {
         return false;
      } else {
         DataSource[] var4 = this.mDataSources;

         for(int var2 = var4.length; var1 < var2; ++var1) {
            var4[var1].close();
         }

         return true;
      }
   }

   @Nullable
   public List<CloseableReference<T>> getResult() {
      // $FF: Couldn't be decompiled
   }

   public boolean hasResult() {
      synchronized(this){}
      boolean var6 = false;

      boolean var3;
      label45: {
         int var1;
         int var2;
         try {
            var6 = true;
            if(this.isClosed()) {
               var6 = false;
               break label45;
            }

            var1 = this.mFinishedDataSources;
            var2 = this.mDataSources.length;
            var6 = false;
         } finally {
            if(var6) {
               ;
            }
         }

         if(var1 == var2) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   class InternalDataSubscriber implements DataSubscriber<CloseableReference<T>> {

      @GuardedBy
      boolean mFinished;


      private InternalDataSubscriber() {
         this.mFinished = false;
      }

      // $FF: synthetic method
      InternalDataSubscriber(Object var2) {
         this();
      }

      private boolean tryFinish() {
         // $FF: Couldn't be decompiled
      }

      public void onCancellation(DataSource<CloseableReference<T>> var1) {
         ListDataSource.this.onDataSourceCancelled();
      }

      public void onFailure(DataSource<CloseableReference<T>> var1) {
         ListDataSource.this.onDataSourceFailed(var1);
      }

      public void onNewResult(DataSource<CloseableReference<T>> var1) {
         if(var1.isFinished() && this.tryFinish()) {
            ListDataSource.this.onDataSourceFinished();
         }

      }

      public void onProgressUpdate(DataSource<CloseableReference<T>> var1) {
         ListDataSource.this.onDataSourceProgress();
      }
   }
}
