package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.login.LoginClient;

public class LoginFragment extends Fragment {

   static final String EXTRA_REQUEST = "request";
   private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
   static final String REQUEST_KEY = "com.facebook.LoginFragment:Request";
   static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
   private static final String SAVED_LOGIN_CLIENT = "loginClient";
   private static final String TAG = "LoginFragment";
   private String callingPackage;
   private LoginClient loginClient;
   private LoginClient.Request request;


   private void initializeCallingPackage(Activity var1) {
      ComponentName var2 = var1.getCallingActivity();
      if(var2 != null) {
         this.callingPackage = var2.getPackageName();
      }
   }

   private void onLoginClientCompleted(LoginClient.Result var1) {
      this.request = null;
      byte var2;
      if(var1.code == LoginClient.Code.CANCEL) {
         var2 = 0;
      } else {
         var2 = -1;
      }

      Bundle var3 = new Bundle();
      var3.putParcelable("com.facebook.LoginFragment:Result", var1);
      Intent var4 = new Intent();
      var4.putExtras(var3);
      if(this.isAdded()) {
         this.getActivity().setResult(var2, var4);
         this.getActivity().finish();
      }

   }

   protected LoginClient createLoginClient() {
      return new LoginClient(this);
   }

   @LayoutRes
   protected int getLayoutResId() {
      return com.facebook.common.R.com_facebook_login_fragment;
   }

   LoginClient getLoginClient() {
      return this.loginClient;
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      this.loginClient.onActivityResult(var1, var2, var3);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(var1 != null) {
         this.loginClient = (LoginClient)var1.getParcelable("loginClient");
         this.loginClient.setFragment(this);
      } else {
         this.loginClient = this.createLoginClient();
      }

      this.loginClient.setOnCompletedListener(new LoginClient.OnCompletedListener() {
         public void onCompleted(LoginClient.Result var1) {
            LoginFragment.this.onLoginClientCompleted(var1);
         }
      });
      FragmentActivity var2 = this.getActivity();
      if(var2 != null) {
         this.initializeCallingPackage(var2);
         Intent var3 = var2.getIntent();
         if(var3 != null) {
            var1 = var3.getBundleExtra("com.facebook.LoginFragment:Request");
            if(var1 != null) {
               this.request = (LoginClient.Request)var1.getParcelable("request");
            }
         }

      }
   }

   public View onCreateView(LayoutInflater var1, @Nullable ViewGroup var2, @Nullable Bundle var3) {
      View var4 = var1.inflate(this.getLayoutResId(), var2, false);
      final View var5 = var4.findViewById(com.facebook.common.R.com_facebook_login_fragment_progress_bar);
      this.loginClient.setBackgroundProcessingListener(new LoginClient.BackgroundProcessingListener() {
         public void onBackgroundProcessingStarted() {
            var5.setVisibility(0);
         }
         public void onBackgroundProcessingStopped() {
            var5.setVisibility(8);
         }
      });
      return var4;
   }

   public void onDestroy() {
      this.loginClient.cancelCurrentHandler();
      super.onDestroy();
   }

   public void onPause() {
      super.onPause();
      View var1;
      if(this.getView() == null) {
         var1 = null;
      } else {
         var1 = this.getView().findViewById(com.facebook.common.R.com_facebook_login_fragment_progress_bar);
      }

      if(var1 != null) {
         var1.setVisibility(8);
      }

   }

   public void onResume() {
      super.onResume();
      if(this.callingPackage == null) {
         Log.e("LoginFragment", "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.");
         this.getActivity().finish();
      } else {
         this.loginClient.startOrContinueAuth(this.request);
      }
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      var1.putParcelable("loginClient", this.loginClient);
   }
}
