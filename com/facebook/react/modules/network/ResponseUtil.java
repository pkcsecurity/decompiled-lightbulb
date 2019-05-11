package com.facebook.react.modules.network;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class ResponseUtil {

   public static void onDataReceived(DeviceEventManagerModule.RCTDeviceEventEmitter var0, int var1, String var2) {
      WritableArray var3 = Arguments.createArray();
      var3.pushInt(var1);
      var3.pushString(var2);
      var0.emit("didReceiveNetworkData", var3);
   }

   public static void onDataReceivedProgress(DeviceEventManagerModule.RCTDeviceEventEmitter var0, int var1, long var2, long var4) {
      WritableArray var6 = Arguments.createArray();
      var6.pushInt(var1);
      var6.pushInt((int)var2);
      var6.pushInt((int)var4);
      var0.emit("didReceiveNetworkDataProgress", var6);
   }

   public static void onDataSend(DeviceEventManagerModule.RCTDeviceEventEmitter var0, int var1, long var2, long var4) {
      WritableArray var6 = Arguments.createArray();
      var6.pushInt(var1);
      var6.pushInt((int)var2);
      var6.pushInt((int)var4);
      var0.emit("didSendNetworkData", var6);
   }

   public static void onIncrementalDataReceived(DeviceEventManagerModule.RCTDeviceEventEmitter var0, int var1, String var2, long var3, long var5) {
      WritableArray var7 = Arguments.createArray();
      var7.pushInt(var1);
      var7.pushString(var2);
      var7.pushInt((int)var3);
      var7.pushInt((int)var5);
      var0.emit("didReceiveNetworkIncrementalData", var7);
   }

   public static void onRequestError(DeviceEventManagerModule.RCTDeviceEventEmitter var0, int var1, String var2, IOException var3) {
      WritableArray var4 = Arguments.createArray();
      var4.pushInt(var1);
      var4.pushString(var2);
      if(var3 != null && var3.getClass() == SocketTimeoutException.class) {
         var4.pushBoolean(true);
      }

      var0.emit("didCompleteNetworkResponse", var4);
   }

   public static void onRequestSuccess(DeviceEventManagerModule.RCTDeviceEventEmitter var0, int var1) {
      WritableArray var2 = Arguments.createArray();
      var2.pushInt(var1);
      var2.pushNull();
      var0.emit("didCompleteNetworkResponse", var2);
   }

   public static void onResponseReceived(DeviceEventManagerModule.RCTDeviceEventEmitter var0, int var1, int var2, WritableMap var3, String var4) {
      WritableArray var5 = Arguments.createArray();
      var5.pushInt(var1);
      var5.pushInt(var2);
      var5.pushMap(var3);
      var5.pushString(var4);
      var0.emit("didReceiveNetworkResponse", var5);
   }
}
