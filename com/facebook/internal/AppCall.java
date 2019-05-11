package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

public class AppCall {

   private static AppCall currentPendingCall;
   private UUID callId;
   private int requestCode;
   private Intent requestIntent;


   public AppCall(int var1) {
      this(var1, UUID.randomUUID());
   }

   public AppCall(int var1, UUID var2) {
      this.callId = var2;
      this.requestCode = var1;
   }

   public static AppCall finishPendingCall(UUID param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static AppCall getCurrentPendingCall() {
      return currentPendingCall;
   }

   private static boolean setCurrentPendingCall(AppCall var0) {
      synchronized(AppCall.class){}
      boolean var4 = false;

      AppCall var2;
      try {
         var4 = true;
         var2 = getCurrentPendingCall();
         currentPendingCall = var0;
         var4 = false;
      } finally {
         if(var4) {
            ;
         }
      }

      boolean var1;
      if(var2 != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public UUID getCallId() {
      return this.callId;
   }

   public int getRequestCode() {
      return this.requestCode;
   }

   public Intent getRequestIntent() {
      return this.requestIntent;
   }

   public boolean setPending() {
      return setCurrentPendingCall(this);
   }

   public void setRequestCode(int var1) {
      this.requestCode = var1;
   }

   public void setRequestIntent(Intent var1) {
      this.requestIntent = var1;
   }
}
