package com.facebook.react.bridge;

import android.app.Activity;
import android.content.Intent;

public interface ActivityEventListener {

   void onActivityResult(Activity var1, int var2, int var3, Intent var4);

   void onNewIntent(Intent var1);
}
