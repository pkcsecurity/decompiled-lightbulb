package com.facebook.internal;

import android.content.Intent;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.Validate;
import java.util.HashMap;
import java.util.Map;

public final class CallbackManagerImpl implements CallbackManager {

   private static final String INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
   private static final String TAG = "CallbackManagerImpl";
   private static Map<Integer, CallbackManagerImpl.Callback> staticCallbacks = new HashMap();
   private Map<Integer, CallbackManagerImpl.Callback> callbacks = new HashMap();


   private static CallbackManagerImpl.Callback getStaticCallback(Integer var0) {
      synchronized(CallbackManagerImpl.class){}

      CallbackManagerImpl.Callback var3;
      try {
         var3 = (CallbackManagerImpl.Callback)staticCallbacks.get(var0);
      } finally {
         ;
      }

      return var3;
   }

   public static void registerStaticCallback(int param0, CallbackManagerImpl.Callback param1) {
      // $FF: Couldn't be decompiled
   }

   private static boolean runStaticCallback(int var0, int var1, Intent var2) {
      CallbackManagerImpl.Callback var3 = getStaticCallback(Integer.valueOf(var0));
      return var3 != null?var3.onActivityResult(var1, var2):false;
   }

   public boolean onActivityResult(int var1, int var2, Intent var3) {
      CallbackManagerImpl.Callback var4 = (CallbackManagerImpl.Callback)this.callbacks.get(Integer.valueOf(var1));
      return var4 != null?var4.onActivityResult(var2, var3):runStaticCallback(var1, var2, var3);
   }

   public void registerCallback(int var1, CallbackManagerImpl.Callback var2) {
      Validate.notNull(var2, "callback");
      this.callbacks.put(Integer.valueOf(var1), var2);
   }

   public void unregisterCallback(int var1) {
      this.callbacks.remove(Integer.valueOf(var1));
   }

   public interface Callback {

      boolean onActivityResult(int var1, Intent var2);
   }

   public static enum RequestCodeOffset {

      // $FF: synthetic field
      private static final CallbackManagerImpl.RequestCodeOffset[] $VALUES = new CallbackManagerImpl.RequestCodeOffset[]{Login, Share, Message, Like, GameRequest, AppGroupCreate, AppGroupJoin, AppInvite, DeviceShare};
      AppGroupCreate("AppGroupCreate", 5, 5),
      AppGroupJoin("AppGroupJoin", 6, 6),
      AppInvite("AppInvite", 7, 7),
      DeviceShare("DeviceShare", 8, 8),
      GameRequest("GameRequest", 4, 4),
      Like("Like", 3, 3),
      Login("Login", 0, 0),
      Message("Message", 2, 2),
      Share("Share", 1, 1);
      private final int offset;


      private RequestCodeOffset(String var1, int var2, int var3) {
         this.offset = var3;
      }

      public int toRequestCode() {
         return FacebookSdk.getCallbackRequestCodeOffset() + this.offset;
      }
   }
}
