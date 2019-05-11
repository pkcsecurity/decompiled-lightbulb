package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.CustomTabMainActivity;

public class CustomTabActivity extends Activity {

   public static final String CUSTOM_TAB_REDIRECT_ACTION;
   private static final int CUSTOM_TAB_REDIRECT_REQUEST_CODE = 2;
   public static final String DESTROY_ACTION;
   private BroadcastReceiver closeReceiver;


   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(CustomTabActivity.class.getSimpleName());
      var0.append(".action_customTabRedirect");
      CUSTOM_TAB_REDIRECT_ACTION = var0.toString();
      var0 = new StringBuilder();
      var0.append(CustomTabActivity.class.getSimpleName());
      var0.append(".action_destroy");
      DESTROY_ACTION = var0.toString();
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var2 == 0) {
         var3 = new Intent(CUSTOM_TAB_REDIRECT_ACTION);
         var3.putExtra(CustomTabMainActivity.EXTRA_URL, this.getIntent().getDataString());
         LocalBroadcastManager.getInstance(this).sendBroadcast(var3);
         this.closeReceiver = new BroadcastReceiver() {
            public void onReceive(Context var1, Intent var2) {
               CustomTabActivity.this.finish();
            }
         };
         LocalBroadcastManager.getInstance(this).registerReceiver(this.closeReceiver, new IntentFilter(DESTROY_ACTION));
      }

   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = new Intent(this, CustomTabMainActivity.class);
      var2.setAction(CUSTOM_TAB_REDIRECT_ACTION);
      var2.putExtra(CustomTabMainActivity.EXTRA_URL, this.getIntent().getDataString());
      var2.addFlags(603979776);
      this.startActivityForResult(var2, 2);
   }

   protected void onDestroy() {
      LocalBroadcastManager.getInstance(this).unregisterReceiver(this.closeReceiver);
      super.onDestroy();
   }
}
