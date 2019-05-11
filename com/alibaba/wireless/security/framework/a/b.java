package com.alibaba.wireless.security.framework.a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;
import com.alibaba.wireless.security.framework.a.c;

public interface b {

   void attach(Activity var1, c var2);

   void onActivityResult(int var1, int var2, Intent var3);

   void onBackPressed();

   void onCreate(Bundle var1);

   boolean onCreateOptionsMenu(Menu var1);

   void onDestroy();

   boolean onKeyUp(int var1, KeyEvent var2);

   void onNewIntent(Intent var1);

   boolean onOptionsItemSelected(MenuItem var1);

   void onPause();

   void onRestart();

   void onRestoreInstanceState(Bundle var1);

   void onResume();

   void onSaveInstanceState(Bundle var1);

   void onStart();

   void onStop();

   boolean onTouchEvent(MotionEvent var1);

   void onWindowAttributesChanged(LayoutParams var1);

   void onWindowFocusChanged(boolean var1);
}
