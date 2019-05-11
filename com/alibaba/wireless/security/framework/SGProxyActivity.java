package com.alibaba.wireless.security.framework;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;
import com.alibaba.wireless.security.framework.b;

public class SGProxyActivity extends Activity implements com.alibaba.wireless.security.framework.a.a {

   private com.alibaba.wireless.security.framework.a.d a = new com.alibaba.wireless.security.framework.a.d(this);
   protected com.alibaba.wireless.security.framework.a.b mRemoteActivity;


   public void attach(com.alibaba.wireless.security.framework.a.b var1, b var2) {
      this.mRemoteActivity = var1;
   }

   public AssetManager getAssets() {
      return this.a.c() == null?super.getAssets():this.a.c();
   }

   public ClassLoader getClassLoader() {
      return this.a.b();
   }

   public Resources getResources() {
      return this.a.d() == null?super.getResources():this.a.d();
   }

   public Theme getTheme() {
      return this.a.e() == null?super.getTheme():this.a.e();
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      this.mRemoteActivity.onActivityResult(var1, var2, var3);
      super.onActivityResult(var1, var2, var3);
   }

   public void onBackPressed() {
      this.mRemoteActivity.onBackPressed();
      super.onBackPressed();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.a.a(this.getIntent());
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.mRemoteActivity.onCreateOptionsMenu(var1);
      return super.onCreateOptionsMenu(var1);
   }

   protected void onDestroy() {
      this.mRemoteActivity.onDestroy();
      super.onDestroy();
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      super.onKeyUp(var1, var2);
      return this.mRemoteActivity.onKeyUp(var1, var2);
   }

   protected void onNewIntent(Intent var1) {
      this.mRemoteActivity.onNewIntent(var1);
      super.onNewIntent(var1);
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      this.mRemoteActivity.onOptionsItemSelected(var1);
      return super.onOptionsItemSelected(var1);
   }

   protected void onPause() {
      this.mRemoteActivity.onPause();
      super.onPause();
   }

   protected void onRestart() {
      this.mRemoteActivity.onRestart();
      super.onRestart();
   }

   protected void onRestoreInstanceState(Bundle var1) {
      this.mRemoteActivity.onRestoreInstanceState(var1);
      super.onRestoreInstanceState(var1);
   }

   protected void onResume() {
      this.mRemoteActivity.onResume();
      super.onResume();
   }

   protected void onSaveInstanceState(Bundle var1) {
      this.mRemoteActivity.onSaveInstanceState(var1);
      super.onSaveInstanceState(var1);
   }

   protected void onStart() {
      this.mRemoteActivity.onStart();
      super.onStart();
   }

   protected void onStop() {
      this.mRemoteActivity.onStop();
      super.onStop();
   }

   public boolean onTouchEvent(MotionEvent var1) {
      super.onTouchEvent(var1);
      return this.mRemoteActivity.onTouchEvent(var1);
   }

   public void onWindowAttributesChanged(LayoutParams var1) {
      this.mRemoteActivity.onWindowAttributesChanged(var1);
      super.onWindowAttributesChanged(var1);
   }

   public void onWindowFocusChanged(boolean var1) {
      this.mRemoteActivity.onWindowFocusChanged(var1);
      super.onWindowFocusChanged(var1);
   }

   public ComponentName startService(Intent var1) {
      return super.startService(var1);
   }
}
