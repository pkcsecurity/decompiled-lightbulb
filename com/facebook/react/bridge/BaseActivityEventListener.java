package com.facebook.react.bridge;

import android.app.Activity;
import android.content.Intent;
import com.facebook.react.bridge.ActivityEventListener;

public class BaseActivityEventListener implements ActivityEventListener {

   @Deprecated
   public void onActivityResult(int var1, int var2, Intent var3) {}

   public void onActivityResult(Activity var1, int var2, int var3, Intent var4) {}

   public void onNewIntent(Intent var1) {}
}
