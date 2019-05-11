package com.google.android.gms.common.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zai;
import com.google.android.gms.common.internal.zaj;
import com.google.android.gms.common.internal.zak;
import com.google.android.gms.common.internal.zal;

@KeepForSdk
public class PendingResultUtil {

   private static final PendingResultUtil.zaa zaot = new zai();


   @KeepForSdk
   public static <R extends Object & Result, T extends Response<R>> lh<T> toResponseTask(PendingResult<R> var0, T var1) {
      return toTask(var0, new zak(var1));
   }

   @KeepForSdk
   public static <R extends Object & Result, T extends Object> lh<T> toTask(PendingResult<R> var0, PendingResultUtil.ResultConverter<R, T> var1) {
      PendingResultUtil.zaa var2 = zaot;
      li var3 = new li();
      var0.addStatusListener(new zaj(var0, var3, var1, var2));
      return var3.a();
   }

   @KeepForSdk
   public static <R extends Object & Result> lh<Void> toVoidTask(PendingResult<R> var0) {
      return toTask(var0, new zal());
   }

   @KeepForSdk
   public interface ResultConverter<R extends Object & Result, T extends Object> {

      @KeepForSdk
      T convert(R var1);
   }

   public interface zaa {

      ApiException zaf(Status var1);
   }
}
