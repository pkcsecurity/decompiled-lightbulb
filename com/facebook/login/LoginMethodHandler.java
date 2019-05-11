package com.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

abstract class LoginMethodHandler implements Parcelable {

   protected LoginClient loginClient;
   Map<String, String> methodLoggingExtras;


   LoginMethodHandler(Parcel var1) {
      this.methodLoggingExtras = Utility.readStringMapFromParcel(var1);
   }

   LoginMethodHandler(LoginClient var1) {
      this.loginClient = var1;
   }

   static AccessToken createAccessTokenFromNativeLogin(Bundle var0, AccessTokenSource var1, String var2) {
      Date var3 = Utility.getBundleLongAsDate(var0, "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH", new Date(0L));
      ArrayList var4 = var0.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
      String var5 = var0.getString("com.facebook.platform.extra.ACCESS_TOKEN");
      Date var6 = Utility.getBundleLongAsDate(var0, "com.facebook.platform.extra.EXTRA_DATA_ACCESS_EXPIRATION_TIME", new Date(0L));
      return Utility.isNullOrEmpty(var5)?null:new AccessToken(var5, var2, var0.getString("com.facebook.platform.extra.USER_ID"), var4, (Collection)null, var1, var3, new Date(), var6);
   }

   public static AccessToken createAccessTokenFromWebBundle(Collection<String> var0, Bundle var1, AccessTokenSource var2, String var3) throws FacebookException {
      Date var5 = Utility.getBundleLongAsDate(var1, "expires_in", new Date());
      String var6 = var1.getString("access_token");
      Date var7 = Utility.getBundleLongAsDate(var1, "data_access_expiration_time", new Date(0L));
      String var4 = var1.getString("granted_scopes");
      if(!Utility.isNullOrEmpty(var4)) {
         var0 = new ArrayList(Arrays.asList(var4.split(",")));
      }

      var4 = var1.getString("denied_scopes");
      ArrayList var8;
      if(!Utility.isNullOrEmpty(var4)) {
         var8 = new ArrayList(Arrays.asList(var4.split(",")));
      } else {
         var8 = null;
      }

      return Utility.isNullOrEmpty(var6)?null:new AccessToken(var6, var3, getUserIDFromSignedRequest(var1.getString("signed_request")), (Collection)var0, var8, var2, var5, new Date(), var7);
   }

   static String getUserIDFromSignedRequest(String var0) throws FacebookException {
      if(var0 != null && !var0.isEmpty()) {
         try {
            String[] var2 = var0.split("\\.");
            if(var2.length == 2) {
               var0 = (new JSONObject(new String(Base64.decode(var2[1], 0), "UTF-8"))).getString("user_id");
               return var0;
            }
         } catch (JSONException var1) {
            ;
         }

         throw new FacebookException("Failed to retrieve user_id from signed_request");
      } else {
         throw new FacebookException("Authorization response does not contain the signed_request");
      }
   }

   protected void addLoggingExtra(String var1, Object var2) {
      if(this.methodLoggingExtras == null) {
         this.methodLoggingExtras = new HashMap();
      }

      Map var3 = this.methodLoggingExtras;
      String var4;
      if(var2 == null) {
         var4 = null;
      } else {
         var4 = var2.toString();
      }

      var3.put(var1, var4);
   }

   void cancel() {}

   protected String getClientState(String var1) {
      JSONObject var2 = new JSONObject();

      try {
         var2.put("0_auth_logger_id", var1);
         var2.put("3_method", this.getNameForLogging());
         this.putChallengeParam(var2);
      } catch (JSONException var4) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Error creating client state json: ");
         var3.append(var4.getMessage());
         Log.w("LoginMethodHandler", var3.toString());
      }

      return var2.toString();
   }

   abstract String getNameForLogging();

   protected void logWebLoginCompleted(String var1) {
      String var2 = this.loginClient.getPendingRequest().getApplicationId();
      AppEventsLogger var3 = AppEventsLogger.newLogger(this.loginClient.getActivity(), var2);
      Bundle var4 = new Bundle();
      var4.putString("fb_web_login_e2e", var1);
      var4.putLong("fb_web_login_switchback_time", System.currentTimeMillis());
      var4.putString("app_id", var2);
      var3.logSdkEvent("fb_dialogs_web_login_dialog_complete", (Double)null, var4);
   }

   boolean needsInternetPermission() {
      return false;
   }

   boolean onActivityResult(int var1, int var2, Intent var3) {
      return false;
   }

   void putChallengeParam(JSONObject var1) throws JSONException {}

   void setLoginClient(LoginClient var1) {
      if(this.loginClient != null) {
         throw new FacebookException("Can\'t set LoginClient if it is already set.");
      } else {
         this.loginClient = var1;
      }
   }

   abstract boolean tryAuthorize(LoginClient.Request var1);

   public void writeToParcel(Parcel var1, int var2) {
      Utility.writeStringMapToParcel(var1, this.methodLoggingExtras);
   }
}
