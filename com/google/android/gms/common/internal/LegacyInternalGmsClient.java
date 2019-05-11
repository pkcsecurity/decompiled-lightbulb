package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IInterface;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.GmsClientEventManager;

@Deprecated
public abstract class LegacyInternalGmsClient<T extends Object & IInterface> extends GmsClient<T> {

   private final GmsClientEventManager zagr;


   public LegacyInternalGmsClient(Context var1, int var2, ClientSettings var3, GoogleApiClient.ConnectionCallbacks var4, GoogleApiClient.OnConnectionFailedListener var5) {
      super(var1, var1.getMainLooper(), var2, var3);
      this.zagr = new GmsClientEventManager(var1.getMainLooper(), this);
      this.zagr.registerConnectionCallbacks(var4);
      this.zagr.registerConnectionFailedListener(var5);
   }

   public void checkAvailabilityAndConnect() {
      this.zagr.enableCallbacks();
      super.checkAvailabilityAndConnect();
   }

   public void disconnect() {
      this.zagr.disableCallbacks();
      super.disconnect();
   }

   public int getMinApkVersion() {
      return super.getMinApkVersion();
   }

   public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks var1) {
      return this.zagr.isConnectionCallbacksRegistered(var1);
   }

   public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener var1) {
      return this.zagr.isConnectionFailedListenerRegistered(var1);
   }

   public void onConnectedLocked(@NonNull T var1) {
      super.onConnectedLocked(var1);
      this.zagr.onConnectionSuccess(this.getConnectionHint());
   }

   public void onConnectionFailed(ConnectionResult var1) {
      super.onConnectionFailed(var1);
      this.zagr.onConnectionFailure(var1);
   }

   public void onConnectionSuspended(int var1) {
      super.onConnectionSuspended(var1);
      this.zagr.onUnintentionalDisconnection(var1);
   }

   public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1) {
      this.zagr.registerConnectionCallbacks(var1);
   }

   public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1) {
      this.zagr.registerConnectionFailedListener(var1);
   }

   public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1) {
      this.zagr.unregisterConnectionCallbacks(var1);
   }

   public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1) {
      this.zagr.unregisterConnectionFailedListener(var1);
   }
}
