package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.facebook.AccessTokenManager;

public final class CurrentAccessTokenExpirationBroadcastReceiver extends BroadcastReceiver {

   public void onReceive(Context var1, Intent var2) {
      if("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED".equals(var2.getAction())) {
         AccessTokenManager.getInstance().currentAccessTokenChanged();
      }

   }
}
