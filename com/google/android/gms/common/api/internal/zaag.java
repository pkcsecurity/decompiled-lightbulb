package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class zaag extends GoogleApiClient {

   private final String zafr;


   public zaag(String var1) {
      this.zafr = var1;
   }

   public ConnectionResult blockingConnect() {
      throw new UnsupportedOperationException(this.zafr);
   }

   public ConnectionResult blockingConnect(long var1, @NonNull TimeUnit var3) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public PendingResult<Status> clearDefaultAccountAndReconnect() {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void connect() {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void disconnect() {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      throw new UnsupportedOperationException(this.zafr);
   }

   @NonNull
   public ConnectionResult getConnectionResult(@NonNull Api<?> var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public boolean hasConnectedApi(@NonNull Api<?> var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public boolean isConnected() {
      throw new UnsupportedOperationException(this.zafr);
   }

   public boolean isConnecting() {
      throw new UnsupportedOperationException(this.zafr);
   }

   public boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void reconnect() {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void stopAutoManage(@NonNull FragmentActivity var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks var1) {
      throw new UnsupportedOperationException(this.zafr);
   }

   public void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener var1) {
      throw new UnsupportedOperationException(this.zafr);
   }
}
