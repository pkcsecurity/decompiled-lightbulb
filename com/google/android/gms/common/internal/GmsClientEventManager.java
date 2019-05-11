package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Handler.Callback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class GmsClientEventManager implements Callback {

   private final Handler mHandler;
   private final Object mLock = new Object();
   private final GmsClientEventManager.GmsClientEventState zaok;
   private final ArrayList<GoogleApiClient.ConnectionCallbacks> zaol = new ArrayList();
   @VisibleForTesting
   private final ArrayList<GoogleApiClient.ConnectionCallbacks> zaom = new ArrayList();
   private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zaon = new ArrayList();
   private volatile boolean zaoo = false;
   private final AtomicInteger zaop = new AtomicInteger(0);
   private boolean zaoq = false;


   public GmsClientEventManager(Looper var1, GmsClientEventManager.GmsClientEventState var2) {
      this.zaok = var2;
      this.mHandler = new gh(var1, this);
   }

   public final boolean areCallbacksEnabled() {
      return this.zaoo;
   }

   public final void disableCallbacks() {
      this.zaoo = false;
      this.zaop.incrementAndGet();
   }

   public final void enableCallbacks() {
      this.zaoo = true;
   }

   public final boolean handleMessage(Message param1) {
      // $FF: Couldn't be decompiled
   }

   public final boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks param1) {
      // $FF: Couldn't be decompiled
   }

   public final boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener param1) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   public final void onConnectionFailure(ConnectionResult param1) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   protected final void onConnectionSuccess() {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   public final void onConnectionSuccess(Bundle param1) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   public final void onUnintentionalDisconnection(int param1) {
      // $FF: Couldn't be decompiled
   }

   public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks param1) {
      // $FF: Couldn't be decompiled
   }

   public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks param1) {
      // $FF: Couldn't be decompiled
   }

   public final void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener param1) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   public interface GmsClientEventState {

      Bundle getConnectionHint();

      boolean isConnected();
   }
}
