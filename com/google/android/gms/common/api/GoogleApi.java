package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zab;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zaae;
import com.google.android.gms.common.api.internal.zabp;
import com.google.android.gms.common.api.internal.zace;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.Set;

@KeepForSdk
public class GoogleApi<O extends Object & Api.ApiOptions> {

   private final Api<O> mApi;
   private final Context mContext;
   private final int mId;
   private final O zabh;
   private final zai<O> zabi;
   private final Looper zabj;
   private final GoogleApiClient zabk;
   private final StatusExceptionMapper zabl;
   protected final GoogleApiManager zabm;


   @MainThread
   @KeepForSdk
   public GoogleApi(@NonNull Activity var1, Api<O> var2, O var3, GoogleApi.Settings var4) {
      Preconditions.checkNotNull(var1, "Null activity is not permitted.");
      Preconditions.checkNotNull(var2, "Api must not be null.");
      Preconditions.checkNotNull(var4, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
      this.mContext = var1.getApplicationContext();
      this.mApi = var2;
      this.zabh = var3;
      this.zabj = var4.zabo;
      this.zabi = zai.zaa(this.mApi, this.zabh);
      this.zabk = new zabp(this);
      this.zabm = GoogleApiManager.zab(this.mContext);
      this.mId = this.zabm.zabd();
      this.zabl = var4.zabn;
      if(!(var1 instanceof GoogleApiActivity)) {
         zaae.zaa(var1, this.zabm, this.zabi);
      }

      this.zabm.zaa(this);
   }

   @Deprecated
   @KeepForSdk
   public GoogleApi(@NonNull Activity var1, Api<O> var2, O var3, StatusExceptionMapper var4) {
      this(var1, var2, var3, (new GoogleApi.Builder()).setMapper(var4).setLooper(var1.getMainLooper()).build());
   }

   @KeepForSdk
   protected GoogleApi(@NonNull Context var1, Api<O> var2, Looper var3) {
      Preconditions.checkNotNull(var1, "Null context is not permitted.");
      Preconditions.checkNotNull(var2, "Api must not be null.");
      Preconditions.checkNotNull(var3, "Looper must not be null.");
      this.mContext = var1.getApplicationContext();
      this.mApi = var2;
      this.zabh = null;
      this.zabj = var3;
      this.zabi = zai.zaa(var2);
      this.zabk = new zabp(this);
      this.zabm = GoogleApiManager.zab(this.mContext);
      this.mId = this.zabm.zabd();
      this.zabl = new ApiExceptionMapper();
   }

   @Deprecated
   @KeepForSdk
   public GoogleApi(@NonNull Context var1, Api<O> var2, O var3, Looper var4, StatusExceptionMapper var5) {
      this(var1, var2, var3, (new GoogleApi.Builder()).setLooper(var4).setMapper(var5).build());
   }

   @KeepForSdk
   public GoogleApi(@NonNull Context var1, Api<O> var2, O var3, GoogleApi.Settings var4) {
      Preconditions.checkNotNull(var1, "Null context is not permitted.");
      Preconditions.checkNotNull(var2, "Api must not be null.");
      Preconditions.checkNotNull(var4, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
      this.mContext = var1.getApplicationContext();
      this.mApi = var2;
      this.zabh = var3;
      this.zabj = var4.zabo;
      this.zabi = zai.zaa(this.mApi, this.zabh);
      this.zabk = new zabp(this);
      this.zabm = GoogleApiManager.zab(this.mContext);
      this.mId = this.zabm.zabd();
      this.zabl = var4.zabn;
      this.zabm.zaa(this);
   }

   @Deprecated
   @KeepForSdk
   public GoogleApi(@NonNull Context var1, Api<O> var2, O var3, StatusExceptionMapper var4) {
      this(var1, var2, var3, (new GoogleApi.Builder()).setMapper(var4).build());
   }

   private final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zaa(int var1, @NonNull T var2) {
      var2.zau();
      this.zabm.zaa(this, var1, var2);
      return var2;
   }

   private final <TResult extends Object, A extends Object & Api.AnyClient> lh<TResult> zaa(int var1, @NonNull TaskApiCall<A, TResult> var2) {
      li var3 = new li();
      this.zabm.zaa(this, var1, var2, var3, this.zabl);
      return var3.a();
   }

   @KeepForSdk
   public GoogleApiClient asGoogleApiClient() {
      return this.zabk;
   }

   @KeepForSdk
   protected ClientSettings.Builder createClientSettingsBuilder() {
      GoogleSignInAccount var1;
      ClientSettings.Builder var2;
      Account var3;
      label24: {
         var2 = new ClientSettings.Builder();
         if(this.zabh instanceof Api.HasGoogleSignInAccountOptions) {
            var1 = ((Api.HasGoogleSignInAccountOptions)this.zabh).getGoogleSignInAccount();
            if(var1 != null) {
               var3 = var1.d();
               break label24;
            }
         }

         if(this.zabh instanceof Api.HasAccountOptions) {
            var3 = ((Api.HasAccountOptions)this.zabh).getAccount();
         } else {
            var3 = null;
         }
      }

      var2 = var2.setAccount(var3);
      Set var4;
      if(this.zabh instanceof Api.HasGoogleSignInAccountOptions) {
         var1 = ((Api.HasGoogleSignInAccountOptions)this.zabh).getGoogleSignInAccount();
         if(var1 != null) {
            var4 = var1.j();
            return var2.addAllRequiredScopes(var4).setRealClientClassName(this.mContext.getClass().getName()).setRealClientPackageName(this.mContext.getPackageName());
         }
      }

      var4 = Collections.emptySet();
      return var2.addAllRequiredScopes(var4).setRealClientClassName(this.mContext.getClass().getName()).setRealClientPackageName(this.mContext.getPackageName());
   }

   @KeepForSdk
   protected lh<Boolean> disconnectService() {
      return this.zabm.zac(this);
   }

   @KeepForSdk
   public <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doBestEffortWrite(@NonNull T var1) {
      return this.zaa(2, var1);
   }

   @KeepForSdk
   public <TResult extends Object, A extends Object & Api.AnyClient> lh<TResult> doBestEffortWrite(TaskApiCall<A, TResult> var1) {
      return this.zaa(2, var1);
   }

   @KeepForSdk
   public <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doRead(@NonNull T var1) {
      return this.zaa(0, var1);
   }

   @KeepForSdk
   public <TResult extends Object, A extends Object & Api.AnyClient> lh<TResult> doRead(TaskApiCall<A, TResult> var1) {
      return this.zaa(0, var1);
   }

   @Deprecated
   @KeepForSdk
   public <A extends Object & Api.AnyClient, T extends RegisterListenerMethod<A, ?>, U extends UnregisterListenerMethod<A, ?>> lh<Void> doRegisterEventListener(@NonNull T var1, U var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var1.getListenerKey(), "Listener has already been released.");
      Preconditions.checkNotNull(var2.getListenerKey(), "Listener has already been released.");
      Preconditions.checkArgument(var1.getListenerKey().equals(var2.getListenerKey()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
      return this.zabm.zaa(this, var1, var2);
   }

   @KeepForSdk
   public <A extends Object & Api.AnyClient> lh<Void> doRegisterEventListener(@NonNull RegistrationMethods<A, ?> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var1.zajy.getListenerKey(), "Listener has already been released.");
      Preconditions.checkNotNull(var1.zajz.getListenerKey(), "Listener has already been released.");
      return this.zabm.zaa(this, var1.zajy, var1.zajz);
   }

   @KeepForSdk
   public lh<Boolean> doUnregisterEventListener(@NonNull ListenerHolder.ListenerKey<?> var1) {
      Preconditions.checkNotNull(var1, "Listener key cannot be null.");
      return this.zabm.zaa(this, var1);
   }

   @KeepForSdk
   public <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doWrite(@NonNull T var1) {
      return this.zaa(1, var1);
   }

   @KeepForSdk
   public <TResult extends Object, A extends Object & Api.AnyClient> lh<TResult> doWrite(TaskApiCall<A, TResult> var1) {
      return this.zaa(1, var1);
   }

   public final Api<O> getApi() {
      return this.mApi;
   }

   @KeepForSdk
   public O getApiOptions() {
      return this.zabh;
   }

   @KeepForSdk
   public Context getApplicationContext() {
      return this.mContext;
   }

   public final int getInstanceId() {
      return this.mId;
   }

   @KeepForSdk
   public Looper getLooper() {
      return this.zabj;
   }

   @KeepForSdk
   public <L extends Object> ListenerHolder<L> registerListener(@NonNull L var1, String var2) {
      return ListenerHolders.createListenerHolder(var1, this.zabj, var2);
   }

   @WorkerThread
   public Api.Client zaa(Looper var1, GoogleApiManager.zaa<O> var2) {
      ClientSettings var3 = this.createClientSettingsBuilder().build();
      return this.mApi.zai().buildClient(this.mContext, var1, var3, this.zabh, var2, var2);
   }

   public zace zaa(Context var1, Handler var2) {
      return new zace(var1, var2, this.createClientSettingsBuilder().build());
   }

   public final zai<O> zak() {
      return this.zabi;
   }

   @KeepForSdk
   public static class Builder {

      private Looper zabj;
      private StatusExceptionMapper zabl;


      @KeepForSdk
      public GoogleApi.Settings build() {
         if(this.zabl == null) {
            this.zabl = new ApiExceptionMapper();
         }

         if(this.zabj == null) {
            this.zabj = Looper.getMainLooper();
         }

         return new GoogleApi.Settings(this.zabl, (Account)null, this.zabj, (zab)null);
      }

      @KeepForSdk
      public GoogleApi.Builder setLooper(Looper var1) {
         Preconditions.checkNotNull(var1, "Looper must not be null.");
         this.zabj = var1;
         return this;
      }

      @KeepForSdk
      public GoogleApi.Builder setMapper(StatusExceptionMapper var1) {
         Preconditions.checkNotNull(var1, "StatusExceptionMapper must not be null.");
         this.zabl = var1;
         return this;
      }
   }

   @KeepForSdk
   public static class Settings {

      @KeepForSdk
      public static final GoogleApi.Settings DEFAULT_SETTINGS = (new GoogleApi.Builder()).build();
      public final StatusExceptionMapper zabn;
      public final Looper zabo;


      @KeepForSdk
      private Settings(StatusExceptionMapper var1, Account var2, Looper var3) {
         this.zabn = var1;
         this.zabo = var3;
      }

      // $FF: synthetic method
      Settings(StatusExceptionMapper var1, Account var2, Looper var3, zab var4) {
         this(var1, (Account)null, var3);
      }
   }
}
