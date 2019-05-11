package com.facebook.login;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.CustomTabLoginMethodHandler;
import com.facebook.login.DefaultAudience;
import com.facebook.login.DeviceAuthMethodHandler;
import com.facebook.login.FacebookLiteLoginMethodHandler;
import com.facebook.login.GetTokenLoginMethodHandler;
import com.facebook.login.KatanaProxyLoginMethodHandler;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginMethodHandler;
import com.facebook.login.WebViewLoginMethodHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class LoginClient implements Parcelable {

   public static final Creator<LoginClient> CREATOR = new Creator() {
      public LoginClient createFromParcel(Parcel var1) {
         return new LoginClient(var1);
      }
      public LoginClient[] newArray(int var1) {
         return new LoginClient[var1];
      }
   };
   LoginClient.BackgroundProcessingListener backgroundProcessingListener;
   boolean checkedInternetPermission;
   int currentHandler = -1;
   Map<String, String> extraData;
   Fragment fragment;
   LoginMethodHandler[] handlersToTry;
   Map<String, String> loggingExtras;
   private LoginLogger loginLogger;
   LoginClient.OnCompletedListener onCompletedListener;
   LoginClient.Request pendingRequest;


   public LoginClient(Parcel var1) {
      Parcelable[] var3 = var1.readParcelableArray(LoginMethodHandler.class.getClassLoader());
      this.handlersToTry = new LoginMethodHandler[var3.length];

      for(int var2 = 0; var2 < var3.length; ++var2) {
         this.handlersToTry[var2] = (LoginMethodHandler)var3[var2];
         this.handlersToTry[var2].setLoginClient(this);
      }

      this.currentHandler = var1.readInt();
      this.pendingRequest = (LoginClient.Request)var1.readParcelable(LoginClient.Request.class.getClassLoader());
      this.loggingExtras = Utility.readStringMapFromParcel(var1);
      this.extraData = Utility.readStringMapFromParcel(var1);
   }

   public LoginClient(Fragment var1) {
      this.fragment = var1;
   }

   private void addLoggingExtra(String var1, String var2, boolean var3) {
      if(this.loggingExtras == null) {
         this.loggingExtras = new HashMap();
      }

      String var4 = var2;
      if(this.loggingExtras.containsKey(var1)) {
         var4 = var2;
         if(var3) {
            StringBuilder var5 = new StringBuilder();
            var5.append((String)this.loggingExtras.get(var1));
            var5.append(",");
            var5.append(var2);
            var4 = var5.toString();
         }
      }

      this.loggingExtras.put(var1, var4);
   }

   private void completeWithFailure() {
      this.complete(LoginClient.Result.createErrorResult(this.pendingRequest, "Login attempt failed.", (String)null));
   }

   static String getE2E() {
      JSONObject var0 = new JSONObject();

      try {
         var0.put("init", System.currentTimeMillis());
      } catch (JSONException var2) {
         ;
      }

      return var0.toString();
   }

   private LoginLogger getLogger() {
      if(this.loginLogger == null || !this.loginLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
         this.loginLogger = new LoginLogger(this.getActivity(), this.pendingRequest.getApplicationId());
      }

      return this.loginLogger;
   }

   public static int getLoginRequestCode() {
      return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
   }

   private void logAuthorizationMethodComplete(String var1, LoginClient.Result var2, Map<String, String> var3) {
      this.logAuthorizationMethodComplete(var1, var2.code.getLoggingValue(), var2.errorMessage, var2.errorCode, var3);
   }

   private void logAuthorizationMethodComplete(String var1, String var2, String var3, String var4, Map<String, String> var5) {
      if(this.pendingRequest == null) {
         this.getLogger().logUnexpectedError("fb_mobile_login_method_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.", var1);
      } else {
         this.getLogger().logAuthorizationMethodComplete(this.pendingRequest.getAuthId(), var1, var2, var3, var4, var5);
      }
   }

   private void notifyOnCompleteListener(LoginClient.Result var1) {
      if(this.onCompletedListener != null) {
         this.onCompletedListener.onCompleted(var1);
      }

   }

   void addExtraData(String var1, String var2, boolean var3) {
      if(this.extraData == null) {
         this.extraData = new HashMap();
      }

      String var4 = var2;
      if(this.extraData.containsKey(var1)) {
         var4 = var2;
         if(var3) {
            StringBuilder var5 = new StringBuilder();
            var5.append((String)this.extraData.get(var1));
            var5.append(",");
            var5.append(var2);
            var4 = var5.toString();
         }
      }

      this.extraData.put(var1, var4);
   }

   void authorize(LoginClient.Request var1) {
      if(var1 != null) {
         if(this.pendingRequest != null) {
            throw new FacebookException("Attempted to authorize while a request is pending.");
         } else if(!AccessToken.isCurrentAccessTokenActive() || this.checkInternetPermission()) {
            this.pendingRequest = var1;
            this.handlersToTry = this.getHandlersToTry(var1);
            this.tryNextHandler();
         }
      }
   }

   void cancelCurrentHandler() {
      if(this.currentHandler >= 0) {
         this.getCurrentHandler().cancel();
      }

   }

   boolean checkInternetPermission() {
      if(this.checkedInternetPermission) {
         return true;
      } else if(this.checkPermission("android.permission.INTERNET") != 0) {
         FragmentActivity var2 = this.getActivity();
         String var1 = var2.getString(com.facebook.common.R.com_facebook_internet_permission_error_title);
         String var3 = var2.getString(com.facebook.common.R.com_facebook_internet_permission_error_message);
         this.complete(LoginClient.Result.createErrorResult(this.pendingRequest, var1, var3));
         return false;
      } else {
         this.checkedInternetPermission = true;
         return true;
      }
   }

   int checkPermission(String var1) {
      return this.getActivity().checkCallingOrSelfPermission(var1);
   }

   void complete(LoginClient.Result var1) {
      LoginMethodHandler var2 = this.getCurrentHandler();
      if(var2 != null) {
         this.logAuthorizationMethodComplete(var2.getNameForLogging(), var1, var2.methodLoggingExtras);
      }

      if(this.loggingExtras != null) {
         var1.loggingExtras = this.loggingExtras;
      }

      if(this.extraData != null) {
         var1.extraData = this.extraData;
      }

      this.handlersToTry = null;
      this.currentHandler = -1;
      this.pendingRequest = null;
      this.loggingExtras = null;
      this.notifyOnCompleteListener(var1);
   }

   void completeAndValidate(LoginClient.Result var1) {
      if(var1.token != null && AccessToken.isCurrentAccessTokenActive()) {
         this.validateSameFbidAndFinish(var1);
      } else {
         this.complete(var1);
      }
   }

   public int describeContents() {
      return 0;
   }

   FragmentActivity getActivity() {
      return this.fragment.getActivity();
   }

   LoginClient.BackgroundProcessingListener getBackgroundProcessingListener() {
      return this.backgroundProcessingListener;
   }

   LoginMethodHandler getCurrentHandler() {
      return this.currentHandler >= 0?this.handlersToTry[this.currentHandler]:null;
   }

   public Fragment getFragment() {
      return this.fragment;
   }

   protected LoginMethodHandler[] getHandlersToTry(LoginClient.Request var1) {
      ArrayList var2 = new ArrayList();
      LoginBehavior var3 = var1.getLoginBehavior();
      if(var3.allowsGetTokenAuth()) {
         var2.add(new GetTokenLoginMethodHandler(this));
      }

      if(var3.allowsKatanaAuth()) {
         var2.add(new KatanaProxyLoginMethodHandler(this));
      }

      if(var3.allowsFacebookLiteAuth()) {
         var2.add(new FacebookLiteLoginMethodHandler(this));
      }

      if(var3.allowsCustomTabAuth()) {
         var2.add(new CustomTabLoginMethodHandler(this));
      }

      if(var3.allowsWebViewAuth()) {
         var2.add(new WebViewLoginMethodHandler(this));
      }

      if(var3.allowsDeviceAuth()) {
         var2.add(new DeviceAuthMethodHandler(this));
      }

      LoginMethodHandler[] var4 = new LoginMethodHandler[var2.size()];
      var2.toArray(var4);
      return var4;
   }

   boolean getInProgress() {
      return this.pendingRequest != null && this.currentHandler >= 0;
   }

   LoginClient.OnCompletedListener getOnCompletedListener() {
      return this.onCompletedListener;
   }

   public LoginClient.Request getPendingRequest() {
      return this.pendingRequest;
   }

   void notifyBackgroundProcessingStart() {
      if(this.backgroundProcessingListener != null) {
         this.backgroundProcessingListener.onBackgroundProcessingStarted();
      }

   }

   void notifyBackgroundProcessingStop() {
      if(this.backgroundProcessingListener != null) {
         this.backgroundProcessingListener.onBackgroundProcessingStopped();
      }

   }

   public boolean onActivityResult(int var1, int var2, Intent var3) {
      return this.pendingRequest != null?this.getCurrentHandler().onActivityResult(var1, var2, var3):false;
   }

   void setBackgroundProcessingListener(LoginClient.BackgroundProcessingListener var1) {
      this.backgroundProcessingListener = var1;
   }

   void setFragment(Fragment var1) {
      if(this.fragment != null) {
         throw new FacebookException("Can\'t set fragment once it is already set.");
      } else {
         this.fragment = var1;
      }
   }

   void setOnCompletedListener(LoginClient.OnCompletedListener var1) {
      this.onCompletedListener = var1;
   }

   void startOrContinueAuth(LoginClient.Request var1) {
      if(!this.getInProgress()) {
         this.authorize(var1);
      }

   }

   boolean tryCurrentHandler() {
      LoginMethodHandler var2 = this.getCurrentHandler();
      if(var2.needsInternetPermission() && !this.checkInternetPermission()) {
         this.addLoggingExtra("no_internet_permission", "1", false);
         return false;
      } else {
         boolean var1 = var2.tryAuthorize(this.pendingRequest);
         if(var1) {
            this.getLogger().logAuthorizationMethodStart(this.pendingRequest.getAuthId(), var2.getNameForLogging());
            return var1;
         } else {
            this.getLogger().logAuthorizationMethodNotTried(this.pendingRequest.getAuthId(), var2.getNameForLogging());
            this.addLoggingExtra("not_tried", var2.getNameForLogging(), true);
            return var1;
         }
      }
   }

   void tryNextHandler() {
      if(this.currentHandler >= 0) {
         this.logAuthorizationMethodComplete(this.getCurrentHandler().getNameForLogging(), "skipped", (String)null, (String)null, this.getCurrentHandler().methodLoggingExtras);
      }

      while(this.handlersToTry != null && this.currentHandler < this.handlersToTry.length - 1) {
         ++this.currentHandler;
         if(this.tryCurrentHandler()) {
            return;
         }
      }

      if(this.pendingRequest != null) {
         this.completeWithFailure();
      }

   }

   void validateSameFbidAndFinish(LoginClient.Result param1) {
      // $FF: Couldn't be decompiled
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeParcelableArray(this.handlersToTry, var2);
      var1.writeInt(this.currentHandler);
      var1.writeParcelable(this.pendingRequest, var2);
      Utility.writeStringMapToParcel(var1, this.loggingExtras);
      Utility.writeStringMapToParcel(var1, this.extraData);
   }

   public static class Request implements Parcelable {

      public static final Creator<LoginClient.Request> CREATOR = new Creator() {
         public LoginClient.Request createFromParcel(Parcel var1) {
            return new LoginClient.Request(var1, null);
         }
         public LoginClient.Request[] newArray(int var1) {
            return new LoginClient.Request[var1];
         }
      };
      private final String applicationId;
      private final String authId;
      private String authType;
      private final DefaultAudience defaultAudience;
      private String deviceAuthTargetUserId;
      private String deviceRedirectUriString;
      private boolean isRerequest;
      private final LoginBehavior loginBehavior;
      private Set<String> permissions;


      private Request(Parcel var1) {
         boolean var2 = false;
         this.isRerequest = false;
         String var3 = var1.readString();
         Object var4 = null;
         LoginBehavior var6;
         if(var3 != null) {
            var6 = LoginBehavior.valueOf(var3);
         } else {
            var6 = null;
         }

         this.loginBehavior = var6;
         ArrayList var7 = new ArrayList();
         var1.readStringList(var7);
         this.permissions = new HashSet(var7);
         String var5 = var1.readString();
         DefaultAudience var8 = (DefaultAudience)var4;
         if(var5 != null) {
            var8 = DefaultAudience.valueOf(var5);
         }

         this.defaultAudience = var8;
         this.applicationId = var1.readString();
         this.authId = var1.readString();
         if(var1.readByte() != 0) {
            var2 = true;
         }

         this.isRerequest = var2;
         this.deviceRedirectUriString = var1.readString();
         this.authType = var1.readString();
         this.deviceAuthTargetUserId = var1.readString();
      }

      // $FF: synthetic method
      Request(Parcel var1, Object var2) {
         this(var1);
      }

      Request(LoginBehavior var1, Set<String> var2, DefaultAudience var3, String var4, String var5, String var6) {
         this.isRerequest = false;
         this.loginBehavior = var1;
         if(var2 == null) {
            var2 = new HashSet();
         }

         this.permissions = (Set)var2;
         this.defaultAudience = var3;
         this.authType = var4;
         this.applicationId = var5;
         this.authId = var6;
      }

      public int describeContents() {
         return 0;
      }

      String getApplicationId() {
         return this.applicationId;
      }

      String getAuthId() {
         return this.authId;
      }

      String getAuthType() {
         return this.authType;
      }

      DefaultAudience getDefaultAudience() {
         return this.defaultAudience;
      }

      String getDeviceAuthTargetUserId() {
         return this.deviceAuthTargetUserId;
      }

      String getDeviceRedirectUriString() {
         return this.deviceRedirectUriString;
      }

      LoginBehavior getLoginBehavior() {
         return this.loginBehavior;
      }

      Set<String> getPermissions() {
         return this.permissions;
      }

      boolean hasPublishPermission() {
         Iterator var1 = this.permissions.iterator();

         do {
            if(!var1.hasNext()) {
               return false;
            }
         } while(!LoginManager.isPublishPermission((String)var1.next()));

         return true;
      }

      boolean isRerequest() {
         return this.isRerequest;
      }

      void setAuthType(String var1) {
         this.authType = var1;
      }

      void setDeviceAuthTargetUserId(String var1) {
         this.deviceAuthTargetUserId = var1;
      }

      void setDeviceRedirectUriString(String var1) {
         this.deviceRedirectUriString = var1;
      }

      void setPermissions(Set<String> var1) {
         Validate.notNull(var1, "permissions");
         this.permissions = var1;
      }

      void setRerequest(boolean var1) {
         this.isRerequest = var1;
      }

      public void writeToParcel(Parcel var1, int var2) {
         LoginBehavior var3 = this.loginBehavior;
         Object var4 = null;
         String var5;
         if(var3 != null) {
            var5 = this.loginBehavior.name();
         } else {
            var5 = null;
         }

         var1.writeString(var5);
         var1.writeStringList(new ArrayList(this.permissions));
         var5 = (String)var4;
         if(this.defaultAudience != null) {
            var5 = this.defaultAudience.name();
         }

         var1.writeString(var5);
         var1.writeString(this.applicationId);
         var1.writeString(this.authId);
         var1.writeByte((byte)this.isRerequest);
         var1.writeString(this.deviceRedirectUriString);
         var1.writeString(this.authType);
         var1.writeString(this.deviceAuthTargetUserId);
      }
   }

   interface BackgroundProcessingListener {

      void onBackgroundProcessingStarted();

      void onBackgroundProcessingStopped();
   }

   static enum Code {

      // $FF: synthetic field
      private static final LoginClient.Code[] $VALUES = new LoginClient.Code[]{SUCCESS, CANCEL, ERROR};
      CANCEL("CANCEL", 1, "cancel"),
      ERROR("ERROR", 2, "error"),
      SUCCESS("SUCCESS", 0, "success");
      private final String loggingValue;


      private Code(String var1, int var2, String var3) {
         this.loggingValue = var3;
      }

      String getLoggingValue() {
         return this.loggingValue;
      }
   }

   public interface OnCompletedListener {

      void onCompleted(LoginClient.Result var1);
   }

   public static class Result implements Parcelable {

      public static final Creator<LoginClient.Result> CREATOR = new Creator() {
         public LoginClient.Result createFromParcel(Parcel var1) {
            return new LoginClient.Result(var1, null);
         }
         public LoginClient.Result[] newArray(int var1) {
            return new LoginClient.Result[var1];
         }
      };
      final LoginClient.Code code;
      final String errorCode;
      final String errorMessage;
      public Map<String, String> extraData;
      public Map<String, String> loggingExtras;
      final LoginClient.Request request;
      final AccessToken token;


      private Result(Parcel var1) {
         this.code = LoginClient.Code.valueOf(var1.readString());
         this.token = (AccessToken)var1.readParcelable(AccessToken.class.getClassLoader());
         this.errorMessage = var1.readString();
         this.errorCode = var1.readString();
         this.request = (LoginClient.Request)var1.readParcelable(LoginClient.Request.class.getClassLoader());
         this.loggingExtras = Utility.readStringMapFromParcel(var1);
         this.extraData = Utility.readStringMapFromParcel(var1);
      }

      // $FF: synthetic method
      Result(Parcel var1, Object var2) {
         this(var1);
      }

      Result(LoginClient.Request var1, LoginClient.Code var2, AccessToken var3, String var4, String var5) {
         Validate.notNull(var2, "code");
         this.request = var1;
         this.token = var3;
         this.errorMessage = var4;
         this.code = var2;
         this.errorCode = var5;
      }

      static LoginClient.Result createCancelResult(LoginClient.Request var0, String var1) {
         return new LoginClient.Result(var0, LoginClient.Code.CANCEL, (AccessToken)null, var1, (String)null);
      }

      static LoginClient.Result createErrorResult(LoginClient.Request var0, String var1, String var2) {
         return createErrorResult(var0, var1, var2, (String)null);
      }

      static LoginClient.Result createErrorResult(LoginClient.Request var0, String var1, String var2, String var3) {
         var1 = TextUtils.join(": ", Utility.asListNoNulls(new String[]{var1, var2}));
         return new LoginClient.Result(var0, LoginClient.Code.ERROR, (AccessToken)null, var1, var3);
      }

      static LoginClient.Result createTokenResult(LoginClient.Request var0, AccessToken var1) {
         return new LoginClient.Result(var0, LoginClient.Code.SUCCESS, var1, (String)null, (String)null);
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         var1.writeString(this.code.name());
         var1.writeParcelable(this.token, var2);
         var1.writeString(this.errorMessage);
         var1.writeString(this.errorCode);
         var1.writeParcelable(this.request, var2);
         Utility.writeStringMapToParcel(var1, this.loggingExtras);
         Utility.writeStringMapToParcel(var1, this.extraData);
      }
   }
}
