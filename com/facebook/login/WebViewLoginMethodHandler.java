package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.app.FragmentActivity;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginClient;
import com.facebook.login.WebLoginMethodHandler;

class WebViewLoginMethodHandler extends WebLoginMethodHandler {

   public static final Creator<WebViewLoginMethodHandler> CREATOR = new Creator() {
      public WebViewLoginMethodHandler createFromParcel(Parcel var1) {
         return new WebViewLoginMethodHandler(var1);
      }
      public WebViewLoginMethodHandler[] newArray(int var1) {
         return new WebViewLoginMethodHandler[var1];
      }
   };
   private String e2e;
   private WebDialog loginDialog;


   WebViewLoginMethodHandler(Parcel var1) {
      super(var1);
      this.e2e = var1.readString();
   }

   WebViewLoginMethodHandler(LoginClient var1) {
      super(var1);
   }

   void cancel() {
      if(this.loginDialog != null) {
         this.loginDialog.cancel();
         this.loginDialog = null;
      }

   }

   public int describeContents() {
      return 0;
   }

   String getNameForLogging() {
      return "web_view";
   }

   AccessTokenSource getTokenSource() {
      return AccessTokenSource.WEB_VIEW;
   }

   boolean needsInternetPermission() {
      return true;
   }

   void onWebDialogComplete(LoginClient.Request var1, Bundle var2, FacebookException var3) {
      super.onComplete(var1, var2, var3);
   }

   boolean tryAuthorize(final LoginClient.Request var1) {
      Bundle var4 = this.getParameters(var1);
      WebDialog.OnCompleteListener var5 = new WebDialog.OnCompleteListener() {
         public void onComplete(Bundle var1x, FacebookException var2) {
            WebViewLoginMethodHandler.this.onWebDialogComplete(var1, var1x, var2);
         }
      };
      this.e2e = LoginClient.getE2E();
      this.addLoggingExtra("e2e", this.e2e);
      FragmentActivity var3 = this.loginClient.getActivity();
      boolean var2 = Utility.isChromeOS(var3);
      this.loginDialog = (new WebViewLoginMethodHandler.AuthDialogBuilder(var3, var1.getApplicationId(), var4)).setE2E(this.e2e).setIsChromeOS(var2).setAuthType(var1.getAuthType()).setOnCompleteListener(var5).build();
      FacebookDialogFragment var6 = new FacebookDialogFragment();
      var6.setRetainInstance(true);
      var6.setDialog(this.loginDialog);
      var6.show(var3.getSupportFragmentManager(), "FacebookDialogFragment");
      return true;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeString(this.e2e);
   }

   static class AuthDialogBuilder extends WebDialog.Builder {

      private static final String OAUTH_DIALOG = "oauth";
      private String authType;
      private String e2e;
      private String redirect_uri = "fbconnect://success";


      public AuthDialogBuilder(Context var1, String var2, Bundle var3) {
         super(var1, var2, "oauth", var3);
      }

      public WebDialog build() {
         Bundle var1 = this.getParameters();
         var1.putString("redirect_uri", this.redirect_uri);
         var1.putString("client_id", this.getApplicationId());
         var1.putString("e2e", this.e2e);
         var1.putString("response_type", "token,signed_request");
         var1.putString("return_scopes", "true");
         var1.putString("auth_type", this.authType);
         return WebDialog.newInstance(this.getContext(), "oauth", var1, this.getTheme(), this.getListener());
      }

      public WebViewLoginMethodHandler.AuthDialogBuilder setAuthType(String var1) {
         this.authType = var1;
         return this;
      }

      public WebViewLoginMethodHandler.AuthDialogBuilder setE2E(String var1) {
         this.e2e = var1;
         return this;
      }

      public WebViewLoginMethodHandler.AuthDialogBuilder setIsChromeOS(boolean var1) {
         String var2;
         if(var1) {
            var2 = "fbconnect://chrome_os_success";
         } else {
            var2 = "fbconnect://success";
         }

         this.redirect_uri = var2;
         return this;
      }

      public WebViewLoginMethodHandler.AuthDialogBuilder setIsRerequest(boolean var1) {
         return this;
      }
   }
}
