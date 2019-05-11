package com.facebook.appevents.codeless.internal;

import android.util.Log;

public class UnityReflection {

   private static final String CAPTURE_VIEW_HIERARCHY_METHOD = "CaptureViewHierarchy";
   private static final String EVENT_MAPPING_METHOD = "OnReceiveMapping";
   private static final String FB_UNITY_GAME_OBJECT = "UnityFacebookSDKPlugin";
   private static final String TAG = UnityReflection.class.getCanonicalName();
   private static final String UNITY_PLAYER_CLASS = "com.unity3d.player.UnityPlayer";
   private static final String UNITY_SEND_MESSAGE_METHOD = "UnitySendMessage";
   private static Class<?> unityPlayer;


   public static void captureViewHierarchy() {
      sendMessage("UnityFacebookSDKPlugin", "CaptureViewHierarchy", "");
   }

   public static void sendEventMapping(String var0) {
      sendMessage("UnityFacebookSDKPlugin", "OnReceiveMapping", var0);
   }

   public static void sendMessage(String var0, String var1, String var2) {
      try {
         if(unityPlayer == null) {
            unityPlayer = Class.forName("com.unity3d.player.UnityPlayer");
         }

         unityPlayer.getMethod("UnitySendMessage", new Class[]{String.class, String.class, String.class}).invoke(unityPlayer, new Object[]{var0, var1, var2});
      } catch (Exception var3) {
         Log.e(TAG, "Failed to send message to Unity", var3);
      }
   }
}
