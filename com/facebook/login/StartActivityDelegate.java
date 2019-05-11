package com.facebook.login;

import android.app.Activity;
import android.content.Intent;

interface StartActivityDelegate {

   Activity getActivityContext();

   void startActivityForResult(Intent var1, int var2);
}
