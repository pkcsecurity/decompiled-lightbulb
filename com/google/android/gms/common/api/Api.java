package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends Object & Api.ApiOptions> {

   private final String mName;
   private final Api.AbstractClientBuilder<?, O> zaau;
   private final Api.zaa<?, O> zaav;
   private final Api.ClientKey<?> zaaw;
   private final Api.zab<?> zaax;


   public <C extends Object & Api.Client> Api(String var1, Api.AbstractClientBuilder<C, O> var2, Api.ClientKey<C> var3) {
      Preconditions.checkNotNull(var2, "Cannot construct an Api with a null ClientBuilder");
      Preconditions.checkNotNull(var3, "Cannot construct an Api with a null ClientKey");
      this.mName = var1;
      this.zaau = var2;
      this.zaav = null;
      this.zaaw = var3;
      this.zaax = null;
   }

   public final Api.AnyClientKey<?> getClientKey() {
      if(this.zaaw != null) {
         return this.zaaw;
      } else {
         throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
      }
   }

   public final String getName() {
      return this.mName;
   }

   public final Api.BaseClientBuilder<?, O> zah() {
      return this.zaau;
   }

   public final Api.AbstractClientBuilder<?, O> zai() {
      boolean var1;
      if(this.zaau != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1, "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
      return this.zaau;
   }

   @KeepForSdk
   @VisibleForTesting
   public abstract static class AbstractClientBuilder<T extends Object & Api.Client, O extends Object> extends Api.BaseClientBuilder<T, O> {

      @KeepForSdk
      public abstract T buildClient(Context var1, Looper var2, ClientSettings var3, O var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6);
   }

   public interface NotRequiredOptions extends Api.ApiOptions {
   }

   @KeepForSdk
   public interface Client extends Api.AnyClient {

      @KeepForSdk
      void connect(BaseGmsClient.ConnectionProgressReportCallbacks var1);

      @KeepForSdk
      void disconnect();

      @KeepForSdk
      void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

      @KeepForSdk
      Feature[] getAvailableFeatures();

      @KeepForSdk
      String getEndpointPackageName();

      @KeepForSdk
      int getMinApkVersion();

      @KeepForSdk
      void getRemoteService(IAccountAccessor var1, Set<Scope> var2);

      @KeepForSdk
      Feature[] getRequiredFeatures();

      @Nullable
      @KeepForSdk
      IBinder getServiceBrokerBinder();

      @KeepForSdk
      Intent getSignInIntent();

      @KeepForSdk
      boolean isConnected();

      @KeepForSdk
      boolean isConnecting();

      @KeepForSdk
      void onUserSignOut(BaseGmsClient.SignOutCallbacks var1);

      @KeepForSdk
      boolean providesSignIn();

      @KeepForSdk
      boolean requiresAccount();

      @KeepForSdk
      boolean requiresGooglePlayServices();

      @KeepForSdk
      boolean requiresSignIn();
   }

   public interface ApiOptions {
   }

   public interface HasAccountOptions extends Api.HasOptions, Api.NotRequiredOptions {

      Account getAccount();
   }

   @KeepForSdk
   @VisibleForTesting
   public static final class ClientKey<C extends Object & Api.Client> extends Api.AnyClientKey<C> {

   }

   public interface SimpleClient<T extends Object & IInterface> extends Api.AnyClient {

      T createServiceInterface(IBinder var1);

      Context getContext();

      String getServiceDescriptor();

      String getStartServiceAction();

      void setState(int var1, T var2);
   }

   public interface Optional extends Api.HasOptions, Api.NotRequiredOptions {
   }

   public static final class NoOptions implements Api.NotRequiredOptions {

   }

   @KeepForSdk
   @VisibleForTesting
   public static class BaseClientBuilder<T extends Object & Api.AnyClient, O extends Object> {

      @KeepForSdk
      public static final int API_PRIORITY_GAMES = 1;
      @KeepForSdk
      public static final int API_PRIORITY_OTHER = Integer.MAX_VALUE;
      @KeepForSdk
      public static final int API_PRIORITY_PLUS = 2;


      @KeepForSdk
      public List<Scope> getImpliedScopes(O var1) {
         return Collections.emptyList();
      }

      @KeepForSdk
      public int getPriority() {
         return Integer.MAX_VALUE;
      }
   }

   @VisibleForTesting
   public static final class zab<C extends Object & Api.SimpleClient> extends Api.AnyClientKey<C> {
   }

   @KeepForSdk
   public interface AnyClient {
   }

   public interface HasGoogleSignInAccountOptions extends Api.HasOptions {

      GoogleSignInAccount getGoogleSignInAccount();
   }

   @VisibleForTesting
   public static class zaa<T extends Object & Api.SimpleClient, O extends Object> extends Api.BaseClientBuilder<T, O> {
   }

   public interface HasOptions extends Api.ApiOptions {
   }

   @KeepForSdk
   public static class AnyClientKey<C extends Object & Api.AnyClient> {

   }
}
