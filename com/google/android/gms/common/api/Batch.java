package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResult;
import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zaa;
import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends BasePendingResult<BatchResult> {

   private final Object mLock;
   private int zaaz;
   private boolean zaba;
   private boolean zabb;
   private final PendingResult<?>[] zabc;


   private Batch(List<PendingResult<?>> var1, GoogleApiClient var2) {
      super(var2);
      this.mLock = new Object();
      this.zaaz = var1.size();
      this.zabc = new PendingResult[this.zaaz];
      if(var1.isEmpty()) {
         this.setResult(new BatchResult(Status.RESULT_SUCCESS, this.zabc));
      } else {
         for(int var3 = 0; var3 < var1.size(); ++var3) {
            PendingResult var4 = (PendingResult)var1.get(var3);
            this.zabc[var3] = var4;
            var4.addStatusListener(new zaa(this));
         }

      }
   }

   // $FF: synthetic method
   Batch(List var1, GoogleApiClient var2, zaa var3) {
      this(var1, var2);
   }

   // $FF: synthetic method
   static Object zaa(Batch var0) {
      return var0.mLock;
   }

   // $FF: synthetic method
   static boolean zaa(Batch var0, boolean var1) {
      var0.zabb = true;
      return true;
   }

   // $FF: synthetic method
   static int zab(Batch var0) {
      int var1 = var0.zaaz;
      var0.zaaz = var1 - 1;
      return var1;
   }

   // $FF: synthetic method
   static boolean zab(Batch var0, boolean var1) {
      var0.zaba = true;
      return true;
   }

   // $FF: synthetic method
   static int zac(Batch var0) {
      return var0.zaaz;
   }

   // $FF: synthetic method
   static boolean zad(Batch var0) {
      return var0.zabb;
   }

   // $FF: synthetic method
   static void zae(Batch var0) {
      var0.cancel();
   }

   // $FF: synthetic method
   static boolean zaf(Batch var0) {
      return var0.zaba;
   }

   // $FF: synthetic method
   static PendingResult[] zag(Batch var0) {
      return var0.zabc;
   }

   public final void cancel() {
      super.cancel();
      PendingResult[] var3 = this.zabc;
      int var2 = var3.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         var3[var1].cancel();
      }

   }

   public final BatchResult createFailedResult(Status var1) {
      return new BatchResult(var1, this.zabc);
   }

   public static final class Builder {

      private List<PendingResult<?>> zabe = new ArrayList();
      private GoogleApiClient zabf;


      public Builder(GoogleApiClient var1) {
         this.zabf = var1;
      }

      public final <R extends Object & Result> BatchResultToken<R> add(PendingResult<R> var1) {
         BatchResultToken var2 = new BatchResultToken(this.zabe.size());
         this.zabe.add(var1);
         return var2;
      }

      public final Batch build() {
         return new Batch(this.zabe, this.zabf, (zaa)null);
      }
   }
}
