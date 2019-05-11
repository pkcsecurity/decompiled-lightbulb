package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class AttributionIdentifiers {

   private static final String ANDROID_ID_COLUMN_NAME = "androidid";
   private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
   private static final String ATTRIBUTION_ID_CONTENT_PROVIDER = "com.facebook.katana.provider.AttributionIdProvider";
   private static final String ATTRIBUTION_ID_CONTENT_PROVIDER_WAKIZASHI = "com.facebook.wakizashi.provider.AttributionIdProvider";
   private static final int CONNECTION_RESULT_SUCCESS = 0;
   private static final long IDENTIFIER_REFRESH_INTERVAL_MILLIS = 3600000L;
   private static final String LIMIT_TRACKING_COLUMN_NAME = "limit_tracking";
   private static final String TAG = AttributionIdentifiers.class.getCanonicalName();
   private static AttributionIdentifiers recentlyFetchedIdentifiers;
   private String androidAdvertiserId;
   private String androidInstallerPackage;
   private String attributionId;
   private long fetchTime;
   private boolean limitTracking;


   private static AttributionIdentifiers cacheAndReturnIdentifiers(AttributionIdentifiers var0) {
      var0.fetchTime = System.currentTimeMillis();
      recentlyFetchedIdentifiers = var0;
      return var0;
   }

   private static AttributionIdentifiers getAndroidId(Context var0) {
      AttributionIdentifiers var2 = getAndroidIdViaReflection(var0);
      AttributionIdentifiers var1 = var2;
      if(var2 == null) {
         AttributionIdentifiers var3 = getAndroidIdViaService(var0);
         var1 = var3;
         if(var3 == null) {
            var1 = new AttributionIdentifiers();
         }
      }

      return var1;
   }

   private static AttributionIdentifiers getAndroidIdViaReflection(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private static AttributionIdentifiers getAndroidIdViaService(Context var0) {
      AttributionIdentifiers.GoogleAdServiceConnection var1 = new AttributionIdentifiers.GoogleAdServiceConnection(null);
      Intent var2 = new Intent("com.google.android.gms.ads.identifier.service.START");
      var2.setPackage("com.google.android.gms");
      if(var0.bindService(var2, var1, 1)) {
         try {
            AttributionIdentifiers.GoogleAdInfo var8 = new AttributionIdentifiers.GoogleAdInfo(var1.getBinder());
            AttributionIdentifiers var3 = new AttributionIdentifiers();
            var3.androidAdvertiserId = var8.getAdvertiserId();
            var3.limitTracking = var8.isTrackingLimited();
            return var3;
         } catch (Exception var6) {
            Utility.logd("android_id", var6);
         } finally {
            var0.unbindService(var1);
         }

         return null;
      } else {
         return null;
      }
   }

   public static AttributionIdentifiers getAttributionIdentifiers(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static AttributionIdentifiers getCachedIdentifiers() {
      return recentlyFetchedIdentifiers;
   }

   @Nullable
   private static String getInstallerPackageName(Context var0) {
      PackageManager var1 = var0.getPackageManager();
      return var1 != null?var1.getInstallerPackageName(var0.getPackageName()):null;
   }

   public String getAndroidAdvertiserId() {
      return FacebookSdk.isInitialized() && FacebookSdk.getAdvertiserIDCollectionEnabled()?this.androidAdvertiserId:null;
   }

   public String getAndroidInstallerPackage() {
      return this.androidInstallerPackage;
   }

   public String getAttributionId() {
      return this.attributionId;
   }

   public boolean isTrackingLimited() {
      return this.limitTracking;
   }

   static final class GoogleAdServiceConnection implements ServiceConnection {

      private AtomicBoolean consumed;
      private final BlockingQueue<IBinder> queue;


      private GoogleAdServiceConnection() {
         this.consumed = new AtomicBoolean(false);
         this.queue = new LinkedBlockingDeque();
      }

      // $FF: synthetic method
      GoogleAdServiceConnection(Object var1) {
         this();
      }

      public IBinder getBinder() throws InterruptedException {
         if(this.consumed.compareAndSet(true, true)) {
            throw new IllegalStateException("Binder already consumed");
         } else {
            return (IBinder)this.queue.take();
         }
      }

      public void onServiceConnected(ComponentName var1, IBinder var2) {
         if(var2 != null) {
            try {
               this.queue.put(var2);
            } catch (InterruptedException var3) {
               return;
            }
         }

      }

      public void onServiceDisconnected(ComponentName var1) {}
   }

   static final class GoogleAdInfo implements IInterface {

      private static final int FIRST_TRANSACTION_CODE = 1;
      private static final int SECOND_TRANSACTION_CODE = 2;
      private IBinder binder;


      GoogleAdInfo(IBinder var1) {
         this.binder = var1;
      }

      public IBinder asBinder() {
         return this.binder;
      }

      public String getAdvertiserId() throws RemoteException {
         Parcel var1 = Parcel.obtain();
         Parcel var2 = Parcel.obtain();

         String var3;
         try {
            var1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            this.binder.transact(1, var1, var2, 0);
            var2.readException();
            var3 = var2.readString();
         } finally {
            var2.recycle();
            var1.recycle();
         }

         return var3;
      }

      public boolean isTrackingLimited() throws RemoteException {
         // $FF: Couldn't be decompiled
      }
   }
}
