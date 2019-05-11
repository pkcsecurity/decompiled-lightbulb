package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.CustomTabActivity;
import com.facebook.FacebookSdk;
import com.facebook.internal.CustomTab;

public class CustomTabMainActivity extends Activity {

   public static final String EXTRA_CHROME_PACKAGE;
   public static final String EXTRA_PARAMS;
   public static final String EXTRA_URL;
   private static final String OAUTH_DIALOG = "oauth";
   public static final String REFRESH_ACTION;
   private BroadcastReceiver redirectReceiver;
   private boolean shouldCloseCustomTab = true;


   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(CustomTabMainActivity.class.getSimpleName());
      var0.append(".extra_params");
      EXTRA_PARAMS = var0.toString();
      var0 = new StringBuilder();
      var0.append(CustomTabMainActivity.class.getSimpleName());
      var0.append(".extra_chromePackage");
      EXTRA_CHROME_PACKAGE = var0.toString();
      var0 = new StringBuilder();
      var0.append(CustomTabMainActivity.class.getSimpleName());
      var0.append(".extra_url");
      EXTRA_URL = var0.toString();
      var0 = new StringBuilder();
      var0.append(CustomTabMainActivity.class.getSimpleName());
      var0.append(".action_refresh");
      REFRESH_ACTION = var0.toString();
   }

   public static final String getRedirectUrl() {
      StringBuilder var0 = new StringBuilder();
      var0.append("fb");
      var0.append(FacebookSdk.getApplicationId());
      var0.append("://authorize");
      return var0.toString();
   }

   private void sendResult(int var1, Intent var2) {
      LocalBroadcastManager.getInstance(this).unregisterReceiver(this.redirectReceiver);
      if(var2 != null) {
         this.setResult(var1, var2);
      } else {
         this.setResult(var1);
      }

      this.finish();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION.equals(this.getIntent().getAction())) {
         this.setResult(0);
         this.finish();
      } else {
         if(var1 == null) {
            var1 = this.getIntent().getBundleExtra(EXTRA_PARAMS);
            String var2 = this.getIntent().getStringExtra(EXTRA_CHROME_PACKAGE);
            (new CustomTab("oauth", var1)).openCustomTab(this, var2);
            this.shouldCloseCustomTab = false;
            this.redirectReceiver = new BroadcastReceiver() {
               public void onReceive(Context var1, Intent var2) {
                  Intent var3 = new Intent(CustomTabMainActivity.this, CustomTabMainActivity.class);
                  var3.setAction(CustomTabMainActivity.REFRESH_ACTION);
                  var3.putExtra(CustomTabMainActivity.EXTRA_URL, var2.getStringExtra(CustomTabMainActivity.EXTRA_URL));
                  var3.addFlags(603979776);
                  CustomTabMainActivity.this.startActivity(var3);
               }
            };
            LocalBroadcastManager.getInstance(this).registerReceiver(this.redirectReceiver, new IntentFilter(CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION));
         }

      }
   }

   protected void onNewIntent(Intent var1) {
      super.onNewIntent(var1);
      if(REFRESH_ACTION.equals(var1.getAction())) {
         Intent var2 = new Intent(CustomTabActivity.DESTROY_ACTION);
         LocalBroadcastManager.getInstance(this).sendBroadcast(var2);
         this.sendResult(-1, var1);
      } else {
         if(CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION.equals(var1.getAction())) {
            this.sendResult(-1, var1);
         }

      }
   }

   protected void onResume() {
      super.onResume();
      if(this.shouldCloseCustomTab) {
         this.sendResult(0, (Intent)null);
      }

      this.shouldCloseCustomTab = true;
   }
}
