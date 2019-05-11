package com.facebook.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginMethodHandler;

abstract class NativeAppLoginMethodHandler extends LoginMethodHandler {

   NativeAppLoginMethodHandler(Parcel var1) {
      super(var1);
   }

   NativeAppLoginMethodHandler(LoginClient var1) {
      super(var1);
   }

   private String getError(Bundle var1) {
      String var3 = var1.getString("error");
      String var2 = var3;
      if(var3 == null) {
         var2 = var1.getString("error_type");
      }

      return var2;
   }

   private String getErrorMessage(Bundle var1) {
      String var3 = var1.getString("error_message");
      String var2 = var3;
      if(var3 == null) {
         var2 = var1.getString("error_description");
      }

      return var2;
   }

   private LoginClient.Result handleResultCancel(LoginClient.Request var1, Intent var2) {
      Bundle var3 = var2.getExtras();
      String var4 = this.getError(var3);
      String var5;
      if(var3.get("error_code") != null) {
         var5 = var3.get("error_code").toString();
      } else {
         var5 = null;
      }

      return "CONNECTION_FAILURE".equals(var5)?LoginClient.Result.createErrorResult(var1, var4, this.getErrorMessage(var3), var5):LoginClient.Result.createCancelResult(var1, var4);
   }

   private LoginClient.Result handleResultOk(LoginClient.Request var1, Intent var2) {
      Bundle var3 = var2.getExtras();
      String var4 = this.getError(var3);
      String var8;
      if(var3.get("error_code") != null) {
         var8 = var3.get("error_code").toString();
      } else {
         var8 = null;
      }

      String var5 = this.getErrorMessage(var3);
      String var6 = var3.getString("e2e");
      if(!Utility.isNullOrEmpty(var6)) {
         this.logWebLoginCompleted(var6);
      }

      if(var4 == null && var8 == null && var5 == null) {
         try {
            LoginClient.Result var9 = LoginClient.Result.createTokenResult(var1, createAccessTokenFromWebBundle(var1.getPermissions(), var3, AccessTokenSource.FACEBOOK_APPLICATION_WEB, var1.getApplicationId()));
            return var9;
         } catch (FacebookException var7) {
            return LoginClient.Result.createErrorResult(var1, (String)null, var7.getMessage());
         }
      } else {
         return ServerProtocol.errorsProxyAuthDisabled.contains(var4)?null:(ServerProtocol.errorsUserCanceled.contains(var4)?LoginClient.Result.createCancelResult(var1, (String)null):LoginClient.Result.createErrorResult(var1, var4, var5, var8));
      }
   }

   boolean onActivityResult(int var1, int var2, Intent var3) {
      LoginClient.Request var4 = this.loginClient.getPendingRequest();
      LoginClient.Result var5;
      if(var3 == null) {
         var5 = LoginClient.Result.createCancelResult(var4, "Operation canceled");
      } else if(var2 == 0) {
         var5 = this.handleResultCancel(var4, var3);
      } else if(var2 != -1) {
         var5 = LoginClient.Result.createErrorResult(var4, "Unexpected resultCode from authorization.", (String)null);
      } else {
         var5 = this.handleResultOk(var4, var3);
      }

      if(var5 != null) {
         this.loginClient.completeAndValidate(var5);
      } else {
         this.loginClient.tryNextHandler();
      }

      return true;
   }

   abstract boolean tryAuthorize(LoginClient.Request var1);

   protected boolean tryIntent(Intent var1, int var2) {
      if(var1 == null) {
         return false;
      } else {
         try {
            this.loginClient.getFragment().startActivityForResult(var1, var2);
            return true;
         } catch (ActivityNotFoundException var3) {
            return false;
         }
      }
   }
}
