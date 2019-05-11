package com.facebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.common.R;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginFragment;
import com.facebook.share.internal.DeviceShareDialogFragment;
import com.facebook.share.model.ShareContent;

public class FacebookActivity extends FragmentActivity {

   private static String FRAGMENT_TAG;
   public static String PASS_THROUGH_CANCEL_ACTION;
   private static final String TAG = "com.facebook.FacebookActivity";
   private Fragment singleFragment;


   private void handlePassThroughError() {
      FacebookException var1 = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(this.getIntent()));
      this.setResult(0, NativeProtocol.createProtocolResultIntent(this.getIntent(), (Bundle)null, var1));
      this.finish();
   }

   public Fragment getCurrentFragment() {
      return this.singleFragment;
   }

   protected Fragment getFragment() {
      Intent var4 = this.getIntent();
      FragmentManager var3 = this.getSupportFragmentManager();
      Fragment var2 = var3.findFragmentByTag(FRAGMENT_TAG);
      Object var1 = var2;
      if(var2 == null) {
         if("FacebookDialogFragment".equals(var4.getAction())) {
            FacebookDialogFragment var6 = new FacebookDialogFragment();
            var6.setRetainInstance(true);
            var6.show(var3, FRAGMENT_TAG);
            return var6;
         }

         if("DeviceShareDialogFragment".equals(var4.getAction())) {
            DeviceShareDialogFragment var5 = new DeviceShareDialogFragment();
            var5.setRetainInstance(true);
            var5.setShareContent((ShareContent)var4.getParcelableExtra("content"));
            var5.show(var3, FRAGMENT_TAG);
            return var5;
         }

         var1 = new LoginFragment();
         ((Fragment)var1).setRetainInstance(true);
         var3.beginTransaction().add(R.id.com_facebook_fragment_container, (Fragment)var1, FRAGMENT_TAG).commit();
      }

      return (Fragment)var1;
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      if(this.singleFragment != null) {
         this.singleFragment.onConfigurationChanged(var1);
      }

   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      if(!FacebookSdk.isInitialized()) {
         Utility.logd(TAG, "Facebook SDK not initialized. Make sure you call sdkInitialize inside your Application\'s onCreate method.");
         FacebookSdk.sdkInitialize(this.getApplicationContext());
      }

      this.setContentView(R.layout.com_facebook_activity_layout);
      if(PASS_THROUGH_CANCEL_ACTION.equals(var2.getAction())) {
         this.handlePassThroughError();
      } else {
         this.singleFragment = this.getFragment();
      }
   }
}
