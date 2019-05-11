package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginMethodHandler;
import java.util.Collection;
import java.util.Locale;

abstract class WebLoginMethodHandler extends LoginMethodHandler {

   private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
   private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
   private String e2e;


   WebLoginMethodHandler(Parcel var1) {
      super(var1);
   }

   WebLoginMethodHandler(LoginClient var1) {
      super(var1);
   }

   private static final String getRedirectUri() {
      StringBuilder var0 = new StringBuilder();
      var0.append("fb");
      var0.append(FacebookSdk.getApplicationId());
      var0.append("://authorize");
      return var0.toString();
   }

   private String loadCookieToken() {
      return this.loginClient.getActivity().getSharedPreferences("com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).getString("TOKEN", "");
   }

   private void saveCookieToken(String var1) {
      this.loginClient.getActivity().getSharedPreferences("com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).edit().putString("TOKEN", var1).apply();
   }

   protected Bundle addExtraParameters(Bundle var1, LoginClient.Request var2) {
      var1.putString("redirect_uri", getRedirectUri());
      var1.putString("client_id", var2.getApplicationId());
      LoginClient var3 = this.loginClient;
      var1.putString("e2e", LoginClient.getE2E());
      var1.putString("response_type", "token,signed_request");
      var1.putString("return_scopes", "true");
      var1.putString("auth_type", var2.getAuthType());
      if(this.getSSODevice() != null) {
         var1.putString("sso", this.getSSODevice());
      }

      return var1;
   }

   protected Bundle getParameters(LoginClient.Request var1) {
      Bundle var2 = new Bundle();
      if(!Utility.isNullOrEmpty((Collection)var1.getPermissions())) {
         String var3 = TextUtils.join(",", var1.getPermissions());
         var2.putString("scope", var3);
         this.addLoggingExtra("scope", var3);
      }

      var2.putString("default_audience", var1.getDefaultAudience().getNativeProtocolAudience());
      var2.putString("state", this.getClientState(var1.getAuthId()));
      AccessToken var4 = AccessToken.getCurrentAccessToken();
      String var5;
      if(var4 != null) {
         var5 = var4.getToken();
      } else {
         var5 = null;
      }

      if(var5 != null && var5.equals(this.loadCookieToken())) {
         var2.putString("access_token", var5);
         this.addLoggingExtra("access_token", "1");
         return var2;
      } else {
         Utility.clearFacebookCookies(this.loginClient.getActivity());
         this.addLoggingExtra("access_token", "0");
         return var2;
      }
   }

   protected String getSSODevice() {
      return null;
   }

   abstract AccessTokenSource getTokenSource();

   protected void onComplete(LoginClient.Request var1, Bundle var2, FacebookException var3) {
      this.e2e = null;
      LoginClient.Result var5;
      if(var2 != null) {
         if(var2.containsKey("e2e")) {
            this.e2e = var2.getString("e2e");
         }

         try {
            AccessToken var7 = createAccessTokenFromWebBundle(var1.getPermissions(), var2, this.getTokenSource(), var1.getApplicationId());
            var5 = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), var7);
            CookieSyncManager.createInstance(this.loginClient.getActivity()).sync();
            this.saveCookieToken(var7.getToken());
         } catch (FacebookException var4) {
            var5 = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), (String)null, var4.getMessage());
         }
      } else if(var3 instanceof FacebookOperationCanceledException) {
         var5 = LoginClient.Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
      } else {
         this.e2e = null;
         String var6 = var3.getMessage();
         String var8;
         if(var3 instanceof FacebookServiceException) {
            FacebookRequestError var9 = ((FacebookServiceException)var3).getRequestError();
            var8 = String.format(Locale.ROOT, "%d", new Object[]{Integer.valueOf(var9.getErrorCode())});
            var6 = var9.toString();
         } else {
            var8 = null;
         }

         var5 = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), (String)null, var6, var8);
      }

      if(!Utility.isNullOrEmpty(this.e2e)) {
         this.logWebLoginCompleted(this.e2e);
      }

      this.loginClient.completeAndValidate(var5);
   }
}
