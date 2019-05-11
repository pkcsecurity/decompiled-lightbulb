package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClientEventManager;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zaf;
import com.google.android.gms.common.internal.zag;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Iterator;
import java.util.Set;

@KeepForSdk
public abstract class GmsClient<T extends Object & IInterface> extends BaseGmsClient<T> implements Api.Client, GmsClientEventManager.GmsClientEventState {

   private final Set<Scope> mScopes;
   private final ClientSettings zaes;
   private final Account zax;


   @KeepForSdk
   @VisibleForTesting
   protected GmsClient(Context var1, Handler var2, int var3, ClientSettings var4) {
      this(var1, var2, GmsClientSupervisor.getInstance(var1), GoogleApiAvailability.getInstance(), var3, var4, (GoogleApiClient.ConnectionCallbacks)null, (GoogleApiClient.OnConnectionFailedListener)null);
   }

   @VisibleForTesting
   protected GmsClient(Context var1, Handler var2, GmsClientSupervisor var3, GoogleApiAvailability var4, int var5, ClientSettings var6, GoogleApiClient.ConnectionCallbacks var7, GoogleApiClient.OnConnectionFailedListener var8) {
      super(var1, var2, var3, var4, var5, zaa(var7), zaa(var8));
      this.zaes = (ClientSettings)Preconditions.checkNotNull(var6);
      this.zax = var6.getAccount();
      this.mScopes = this.zaa(var6.getAllRequestedScopes());
   }

   @KeepForSdk
   protected GmsClient(Context var1, Looper var2, int var3, ClientSettings var4) {
      this(var1, var2, GmsClientSupervisor.getInstance(var1), GoogleApiAvailability.getInstance(), var3, var4, (GoogleApiClient.ConnectionCallbacks)null, (GoogleApiClient.OnConnectionFailedListener)null);
   }

   @KeepForSdk
   protected GmsClient(Context var1, Looper var2, int var3, ClientSettings var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6) {
      this(var1, var2, GmsClientSupervisor.getInstance(var1), GoogleApiAvailability.getInstance(), var3, var4, (GoogleApiClient.ConnectionCallbacks)Preconditions.checkNotNull(var5), (GoogleApiClient.OnConnectionFailedListener)Preconditions.checkNotNull(var6));
   }

   @VisibleForTesting
   protected GmsClient(Context var1, Looper var2, GmsClientSupervisor var3, GoogleApiAvailability var4, int var5, ClientSettings var6, GoogleApiClient.ConnectionCallbacks var7, GoogleApiClient.OnConnectionFailedListener var8) {
      super(var1, var2, var3, var4, var5, zaa(var7), zaa(var8), var6.getRealClientClassName());
      this.zaes = var6;
      this.zax = var6.getAccount();
      this.mScopes = this.zaa(var6.getAllRequestedScopes());
   }

   @Nullable
   private static BaseGmsClient.BaseConnectionCallbacks zaa(GoogleApiClient.ConnectionCallbacks var0) {
      return var0 == null?null:new zaf(var0);
   }

   @Nullable
   private static BaseGmsClient.BaseOnConnectionFailedListener zaa(GoogleApiClient.OnConnectionFailedListener var0) {
      return var0 == null?null:new zag(var0);
   }

   private final Set<Scope> zaa(@NonNull Set<Scope> var1) {
      Set var2 = this.validateScopes(var1);
      Iterator var3 = var2.iterator();

      do {
         if(!var3.hasNext()) {
            return var2;
         }
      } while(var1.contains((Scope)var3.next()));

      throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
   }

   public final Account getAccount() {
      return this.zax;
   }

   @KeepForSdk
   protected final ClientSettings getClientSettings() {
      return this.zaes;
   }

   public int getMinApkVersion() {
      return super.getMinApkVersion();
   }

   @KeepForSdk
   public Feature[] getRequiredFeatures() {
      return new Feature[0];
   }

   protected final Set<Scope> getScopes() {
      return this.mScopes;
   }

   @NonNull
   @KeepForSdk
   protected Set<Scope> validateScopes(@NonNull Set<Scope> var1) {
      return var1;
   }
}
