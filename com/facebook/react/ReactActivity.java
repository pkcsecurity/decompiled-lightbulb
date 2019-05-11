package com.facebook.react;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import javax.annotation.Nullable;

public abstract class ReactActivity extends Activity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

   private final ReactActivityDelegate mDelegate = this.createReactActivityDelegate();


   protected ReactActivityDelegate createReactActivityDelegate() {
      return new ReactActivityDelegate(this, this.getMainComponentName());
   }

   @Nullable
   protected String getMainComponentName() {
      return null;
   }

   protected final ReactInstanceManager getReactInstanceManager() {
      return this.mDelegate.getReactInstanceManager();
   }

   protected final ReactNativeHost getReactNativeHost() {
      return this.mDelegate.getReactNativeHost();
   }

   public void invokeDefaultOnBackPressed() {
      super.onBackPressed();
   }

   protected final void loadApp(String var1) {
      this.mDelegate.loadApp(var1);
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      this.mDelegate.onActivityResult(var1, var2, var3);
   }

   public void onBackPressed() {
      if(!this.mDelegate.onBackPressed()) {
         super.onBackPressed();
      }

   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.mDelegate.onCreate(var1);
   }

   protected void onDestroy() {
      super.onDestroy();
      this.mDelegate.onDestroy();
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      return this.mDelegate.onKeyUp(var1, var2) || super.onKeyUp(var1, var2);
   }

   public void onNewIntent(Intent var1) {
      if(!this.mDelegate.onNewIntent(var1)) {
         super.onNewIntent(var1);
      }

   }

   protected void onPause() {
      super.onPause();
      this.mDelegate.onPause();
   }

   public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      this.mDelegate.onRequestPermissionsResult(var1, var2, var3);
   }

   protected void onResume() {
      super.onResume();
      this.mDelegate.onResume();
   }

   public void requestPermissions(String[] var1, int var2, PermissionListener var3) {
      this.mDelegate.requestPermissions(var1, var2, var3);
   }
}
