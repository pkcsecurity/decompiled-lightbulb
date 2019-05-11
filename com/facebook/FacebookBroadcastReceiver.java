package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;

public class FacebookBroadcastReceiver extends BroadcastReceiver {

   protected void onFailedAppCall(String var1, String var2, Bundle var3) {}

   public void onReceive(Context var1, Intent var2) {
      String var5 = var2.getStringExtra("com.facebook.platform.protocol.CALL_ID");
      String var3 = var2.getStringExtra("com.facebook.platform.protocol.PROTOCOL_ACTION");
      if(var5 != null && var3 != null) {
         Bundle var4 = var2.getExtras();
         if(NativeProtocol.isErrorResult(var2)) {
            this.onFailedAppCall(var5, var3, var4);
            return;
         }

         this.onSuccessfulAppCall(var5, var3, var4);
      }

   }

   protected void onSuccessfulAppCall(String var1, String var2, Bundle var3) {}
}
