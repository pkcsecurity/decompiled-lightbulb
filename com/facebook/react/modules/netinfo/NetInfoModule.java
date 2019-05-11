package com.facebook.react.modules.netinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(
   name = "NetInfo"
)
public class NetInfoModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

   private static final String CONNECTION_TYPE_BLUETOOTH = "bluetooth";
   private static final String CONNECTION_TYPE_CELLULAR = "cellular";
   private static final String CONNECTION_TYPE_ETHERNET = "ethernet";
   private static final String CONNECTION_TYPE_NONE = "none";
   private static final String CONNECTION_TYPE_NONE_DEPRECATED = "NONE";
   private static final String CONNECTION_TYPE_UNKNOWN = "unknown";
   private static final String CONNECTION_TYPE_UNKNOWN_DEPRECATED = "UNKNOWN";
   private static final String CONNECTION_TYPE_WIFI = "wifi";
   private static final String CONNECTION_TYPE_WIMAX = "wimax";
   private static final String EFFECTIVE_CONNECTION_TYPE_2G = "2g";
   private static final String EFFECTIVE_CONNECTION_TYPE_3G = "3g";
   private static final String EFFECTIVE_CONNECTION_TYPE_4G = "4g";
   private static final String EFFECTIVE_CONNECTION_TYPE_UNKNOWN = "unknown";
   private static final String ERROR_MISSING_PERMISSION = "E_MISSING_PERMISSION";
   private static final String MISSING_PERMISSION_MESSAGE = "To use NetInfo on Android, add the following to your AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />";
   private String mConnectionType = "unknown";
   private final NetInfoModule.ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;
   private String mConnectivityDeprecated = "UNKNOWN";
   private final ConnectivityManager mConnectivityManager;
   private String mEffectiveConnectionType = "unknown";
   private boolean mNoNetworkPermission = false;


   public NetInfoModule(ReactApplicationContext var1) {
      super(var1);
      this.mConnectivityManager = (ConnectivityManager)var1.getSystemService("connectivity");
      this.mConnectivityBroadcastReceiver = new NetInfoModule.ConnectivityBroadcastReceiver(null);
   }

   private WritableMap createConnectivityEventMap() {
      WritableNativeMap var1 = new WritableNativeMap();
      var1.putString("network_info", this.mConnectivityDeprecated);
      var1.putString("connectionType", this.mConnectionType);
      var1.putString("effectiveConnectionType", this.mEffectiveConnectionType);
      return var1;
   }

   private String getCurrentConnectionType() {
      // $FF: Couldn't be decompiled
   }

   private String getEffectiveConnectionType(NetworkInfo var1) {
      switch(var1.getSubtype()) {
      case 1:
      case 2:
      case 4:
      case 7:
      case 11:
         return "2g";
      case 3:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 12:
      case 14:
         return "3g";
      case 13:
      case 15:
         return "4g";
      default:
         return "unknown";
      }
   }

   private void registerReceiver() {
      IntentFilter var1 = new IntentFilter();
      var1.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      this.getReactApplicationContext().registerReceiver(this.mConnectivityBroadcastReceiver, var1);
      this.mConnectivityBroadcastReceiver.setRegistered(true);
   }

   private void sendConnectivityChangedEvent() {
      ((DeviceEventManagerModule.RCTDeviceEventEmitter)this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("networkStatusDidChange", this.createConnectivityEventMap());
   }

   private void unregisterReceiver() {
      if(this.mConnectivityBroadcastReceiver.isRegistered()) {
         this.getReactApplicationContext().unregisterReceiver(this.mConnectivityBroadcastReceiver);
         this.mConnectivityBroadcastReceiver.setRegistered(false);
      }

   }

   private void updateAndSendConnectionType() {
      // $FF: Couldn't be decompiled
   }

   @ReactMethod
   public void getCurrentConnectivity(Promise var1) {
      if(this.mNoNetworkPermission) {
         var1.reject("E_MISSING_PERMISSION", "To use NetInfo on Android, add the following to your AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />", (Throwable)null);
      } else {
         var1.resolve(this.createConnectivityEventMap());
      }
   }

   public String getName() {
      return "NetInfo";
   }

   public void initialize() {
      this.getReactApplicationContext().addLifecycleEventListener(this);
   }

   @ReactMethod
   public void isConnectionMetered(Promise var1) {
      if(this.mNoNetworkPermission) {
         var1.reject("E_MISSING_PERMISSION", "To use NetInfo on Android, add the following to your AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />", (Throwable)null);
      } else {
         var1.resolve(Boolean.valueOf(ConnectivityManagerCompat.isActiveNetworkMetered(this.mConnectivityManager)));
      }
   }

   public void onHostDestroy() {}

   public void onHostPause() {
      this.unregisterReceiver();
   }

   public void onHostResume() {
      this.registerReceiver();
   }

   class ConnectivityBroadcastReceiver extends BroadcastReceiver {

      private boolean isRegistered;


      private ConnectivityBroadcastReceiver() {
         this.isRegistered = false;
      }

      // $FF: synthetic method
      ConnectivityBroadcastReceiver(Object var2) {
         this();
      }

      public boolean isRegistered() {
         return this.isRegistered;
      }

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            NetInfoModule.this.updateAndSendConnectionType();
         }

      }

      public void setRegistered(boolean var1) {
         this.isRegistered = var1;
      }
   }
}
