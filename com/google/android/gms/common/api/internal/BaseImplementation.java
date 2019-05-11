package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;

@KeepForSdk
public class BaseImplementation {


   @KeepForSdk
   public abstract static class ApiMethodImpl<R extends Object & Result, A extends Object & Api.AnyClient> extends BasePendingResult<R> implements BaseImplementation.ResultHolder<R> {

      @KeepForSdk
      private final Api<?> mApi;
      @KeepForSdk
      private final Api.AnyClientKey<A> mClientKey;


      @Deprecated
      @KeepForSdk
      protected ApiMethodImpl(@NonNull Api.AnyClientKey<A> var1, @NonNull GoogleApiClient var2) {
         super((GoogleApiClient)Preconditions.checkNotNull(var2, "GoogleApiClient must not be null"));
         this.mClientKey = (Api.AnyClientKey)Preconditions.checkNotNull(var1);
         this.mApi = null;
      }

      @KeepForSdk
      protected ApiMethodImpl(@NonNull Api<?> var1, @NonNull GoogleApiClient var2) {
         super((GoogleApiClient)Preconditions.checkNotNull(var2, "GoogleApiClient must not be null"));
         Preconditions.checkNotNull(var1, "Api must not be null");
         this.mClientKey = var1.getClientKey();
         this.mApi = var1;
      }

      @VisibleForTesting
      @KeepForSdk
      protected ApiMethodImpl(@NonNull BasePendingResult.CallbackHandler<R> var1) {
         super(var1);
         this.mClientKey = null;
         this.mApi = null;
      }

      @KeepForSdk
      private void setFailedResult(@NonNull RemoteException var1) {
         this.setFailedResult(new Status(8, var1.getLocalizedMessage(), (PendingIntent)null));
      }

      @KeepForSdk
      public abstract void doExecute(@NonNull A var1) throws RemoteException;

      @KeepForSdk
      public final Api<?> getApi() {
         return this.mApi;
      }

      @KeepForSdk
      public final Api.AnyClientKey<A> getClientKey() {
         return this.mClientKey;
      }

      @KeepForSdk
      protected void onSetFailedResult(@NonNull R var1) {}

      @KeepForSdk
      public final void run(@NonNull A var1) throws DeadObjectException {
         Object var2 = var1;
         if(var1 instanceof SimpleClientAdapter) {
            var2 = ((SimpleClientAdapter)var1).getClient();
         }

         try {
            this.doExecute((Api.AnyClient)var2);
         } catch (DeadObjectException var3) {
            this.setFailedResult((RemoteException)var3);
            throw var3;
         } catch (RemoteException var4) {
            this.setFailedResult(var4);
         }
      }

      @KeepForSdk
      public final void setFailedResult(@NonNull Status var1) {
         Preconditions.checkArgument(var1.isSuccess() ^ true, "Failed result must not be success");
         Result var2 = this.createFailedResult(var1);
         this.setResult(var2);
         this.onSetFailedResult(var2);
      }
   }

   @KeepForSdk
   public interface ResultHolder<R extends Object> {

      @KeepForSdk
      void setFailedResult(Status var1);

      @KeepForSdk
      void setResult(R var1);
   }
}
