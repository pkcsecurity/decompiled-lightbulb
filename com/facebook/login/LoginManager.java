package com.facebook.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginLogger;
import com.facebook.login.LoginMethodHandler;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginStatusClient;
import com.facebook.login.StartActivityDelegate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager {

   private static final String EXPRESS_LOGIN_ALLOWED = "express_login_allowed";
   private static final String MANAGE_PERMISSION_PREFIX = "manage";
   private static final Set<String> OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
   private static final String PREFERENCE_LOGIN_MANAGER = "com.facebook.loginManager";
   private static final String PUBLISH_PERMISSION_PREFIX = "publish";
   private static volatile LoginManager instance;
   private String authType;
   private DefaultAudience defaultAudience;
   private LoginBehavior loginBehavior;
   private final SharedPreferences sharedPreferences;


   LoginManager() {
      this.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
      this.defaultAudience = DefaultAudience.FRIENDS;
      this.authType = "rerequest";
      Validate.sdkInitialized();
      this.sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.loginManager", 0);
   }

   static LoginResult computeLoginResult(LoginClient.Request var0, AccessToken var1) {
      Set var3 = var0.getPermissions();
      HashSet var2 = new HashSet(var1.getPermissions());
      if(var0.isRerequest()) {
         var2.retainAll(var3);
      }

      HashSet var4 = new HashSet(var3);
      var4.removeAll(var2);
      return new LoginResult(var1, var2, var4);
   }

   private LoginClient.Request createLoginRequestFromResponse(GraphResponse var1) {
      Validate.notNull(var1, "response");
      AccessToken var2 = var1.getRequest().getAccessToken();
      Set var3;
      if(var2 != null) {
         var3 = var2.getPermissions();
      } else {
         var3 = null;
      }

      return this.createLoginRequest(var3);
   }

   private void finishLogin(AccessToken var1, LoginClient.Request var2, FacebookException var3, boolean var4, FacebookCallback<LoginResult> var5) {
      if(var1 != null) {
         AccessToken.setCurrentAccessToken(var1);
         Profile.fetchProfileForCurrentAccessToken();
      }

      if(var5 != null) {
         LoginResult var6;
         if(var1 != null) {
            var6 = computeLoginResult(var2, var1);
         } else {
            var6 = null;
         }

         if(!var4 && (var6 == null || var6.getRecentlyGrantedPermissions().size() != 0)) {
            if(var3 != null) {
               var5.onError(var3);
               return;
            }

            if(var1 != null) {
               this.setExpressLoginStatus(true);
               var5.onSuccess(var6);
               return;
            }
         } else {
            var5.onCancel();
         }
      }

   }

   @Nullable
   static Map<String, String> getExtraDataFromIntent(Intent var0) {
      if(var0 == null) {
         return null;
      } else {
         LoginClient.Result var1 = (LoginClient.Result)var0.getParcelableExtra("com.facebook.LoginFragment:Result");
         return var1 == null?null:var1.extraData;
      }
   }

   public static LoginManager getInstance() {
      // $FF: Couldn't be decompiled
   }

   private static Set<String> getOtherPublishPermissions() {
      return Collections.unmodifiableSet(new HashSet() {
         {
            this.add("ads_management");
            this.add("create_event");
            this.add("rsvp_event");
         }
      });
   }

   @Nullable
   private static Profile getProfileFromBundle(Bundle var0) {
      String var1 = var0.getString("com.facebook.platform.extra.PROFILE_NAME");
      String var2 = var0.getString("com.facebook.platform.extra.PROFILE_FIRST_NAME");
      String var3 = var0.getString("com.facebook.platform.extra.PROFILE_MIDDLE_NAME");
      String var4 = var0.getString("com.facebook.platform.extra.PROFILE_LAST_NAME");
      String var5 = var0.getString("com.facebook.platform.extra.PROFILE_LINK");
      String var6 = var0.getString("com.facebook.platform.extra.PROFILE_USER_ID");
      return var1 != null && var2 != null && var3 != null && var4 != null && var5 != null && var6 != null?new Profile(var6, var2, var3, var4, var1, Uri.parse(var5)):null;
   }

   private static void handleLoginStatusError(String var0, String var1, String var2, LoginLogger var3, LoginStatusCallback var4) {
      StringBuilder var5 = new StringBuilder();
      var5.append(var0);
      var5.append(": ");
      var5.append(var1);
      FacebookException var6 = new FacebookException(var5.toString());
      var3.logLoginStatusError(var2, var6);
      var4.onError(var6);
   }

   private boolean isExpressLoginAllowed() {
      return this.sharedPreferences.getBoolean("express_login_allowed", true);
   }

   static boolean isPublishPermission(String var0) {
      return var0 != null && (var0.startsWith("publish") || var0.startsWith("manage") || OTHER_PUBLISH_PERMISSIONS.contains(var0));
   }

   private void logCompleteLogin(Context var1, LoginClient.Code var2, Map<String, String> var3, Exception var4, boolean var5, LoginClient.Request var6) {
      LoginLogger var7 = LoginManager.LoginLoggerHolder.getLogger(var1);
      if(var7 != null) {
         if(var6 == null) {
            var7.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
         } else {
            HashMap var8 = new HashMap();
            String var9;
            if(var5) {
               var9 = "1";
            } else {
               var9 = "0";
            }

            var8.put("try_login_activity", var9);
            var7.logCompleteLogin(var6.getAuthId(), var8, var2, var3, var4);
         }
      }
   }

   private void logInWithPublishPermissions(FragmentWrapper var1, Collection<String> var2) {
      this.validatePublishPermissions(var2);
      LoginClient.Request var3 = this.createLoginRequest(var2);
      this.startLogin(new LoginManager.FragmentStartActivityDelegate(var1), var3);
   }

   private void logInWithReadPermissions(FragmentWrapper var1, Collection<String> var2) {
      this.validateReadPermissions(var2);
      LoginClient.Request var3 = this.createLoginRequest(var2);
      this.startLogin(new LoginManager.FragmentStartActivityDelegate(var1), var3);
   }

   private void logStartLogin(Context var1, LoginClient.Request var2) {
      LoginLogger var3 = LoginManager.LoginLoggerHolder.getLogger(var1);
      if(var3 != null && var2 != null) {
         var3.logStartLogin(var2);
      }

   }

   private void reauthorizeDataAccess(FragmentWrapper var1) {
      LoginClient.Request var2 = this.createReauthorizeRequest();
      this.startLogin(new LoginManager.FragmentStartActivityDelegate(var1), var2);
   }

   private void resolveError(FragmentWrapper var1, GraphResponse var2) {
      this.startLogin(new LoginManager.FragmentStartActivityDelegate(var1), this.createLoginRequestFromResponse(var2));
   }

   private boolean resolveIntent(Intent var1) {
      PackageManager var3 = FacebookSdk.getApplicationContext().getPackageManager();
      boolean var2 = false;
      if(var3.resolveActivity(var1, 0) != null) {
         var2 = true;
      }

      return var2;
   }

   private void retrieveLoginStatusImpl(Context var1, final LoginStatusCallback var2, long var3) {
      final String var5 = FacebookSdk.getApplicationId();
      final String var6 = UUID.randomUUID().toString();
      final LoginLogger var7 = new LoginLogger(var1, var5);
      if(!this.isExpressLoginAllowed()) {
         var7.logLoginStatusFailure(var6);
         var2.onFailure();
      } else {
         LoginStatusClient var8 = new LoginStatusClient(var1, var5, var6, FacebookSdk.getGraphApiVersion(), var3);
         var8.setCompletedListener(new PlatformServiceClient.CompletedListener() {
            public void completed(Bundle var1) {
               if(var1 != null) {
                  String var2x = var1.getString("com.facebook.platform.status.ERROR_TYPE");
                  String var3 = var1.getString("com.facebook.platform.status.ERROR_DESCRIPTION");
                  if(var2x != null) {
                     LoginManager.handleLoginStatusError(var2x, var3, var6, var7, var2);
                  } else {
                     var3 = var1.getString("com.facebook.platform.extra.ACCESS_TOKEN");
                     Date var4 = Utility.getBundleLongAsDate(var1, "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH", new Date(0L));
                     ArrayList var5x = var1.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
                     var2x = var1.getString("signed request");
                     Date var6x = Utility.getBundleLongAsDate(var1, "com.facebook.platform.extra.EXTRA_DATA_ACCESS_EXPIRATION_TIME", new Date(0L));
                     if(!Utility.isNullOrEmpty(var2x)) {
                        var2x = LoginMethodHandler.getUserIDFromSignedRequest(var2x);
                     } else {
                        var2x = null;
                     }

                     if(!Utility.isNullOrEmpty(var3) && var5x != null && !var5x.isEmpty() && !Utility.isNullOrEmpty(var2x)) {
                        AccessToken var8 = new AccessToken(var3, var5, var2x, var5x, (Collection)null, (AccessTokenSource)null, var4, (Date)null, var6x);
                        AccessToken.setCurrentAccessToken(var8);
                        Profile var7x = LoginManager.getProfileFromBundle(var1);
                        if(var7x != null) {
                           Profile.setCurrentProfile(var7x);
                        } else {
                           Profile.fetchProfileForCurrentAccessToken();
                        }

                        var7.logLoginStatusSuccess(var6);
                        var2.onCompleted(var8);
                     } else {
                        var7.logLoginStatusFailure(var6);
                        var2.onFailure();
                     }
                  }
               } else {
                  var7.logLoginStatusFailure(var6);
                  var2.onFailure();
               }
            }
         });
         var7.logLoginStatusStart(var6);
         if(!var8.start()) {
            var7.logLoginStatusFailure(var6);
            var2.onFailure();
         }

      }
   }

   private void setExpressLoginStatus(boolean var1) {
      Editor var2 = this.sharedPreferences.edit();
      var2.putBoolean("express_login_allowed", var1);
      var2.apply();
   }

   private void startLogin(StartActivityDelegate var1, LoginClient.Request var2) throws FacebookException {
      this.logStartLogin(var1.getActivityContext(), var2);
      CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() {
         public boolean onActivityResult(int var1, Intent var2) {
            return LoginManager.this.onActivityResult(var1, var2);
         }
      });
      if(!this.tryFacebookActivity(var1, var2)) {
         FacebookException var3 = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
         this.logCompleteLogin(var1.getActivityContext(), LoginClient.Code.ERROR, (Map)null, var3, false, var2);
         throw var3;
      }
   }

   private boolean tryFacebookActivity(StartActivityDelegate var1, LoginClient.Request var2) {
      Intent var4 = this.getFacebookActivityIntent(var2);
      if(!this.resolveIntent(var4)) {
         return false;
      } else {
         try {
            var1.startActivityForResult(var4, LoginClient.getLoginRequestCode());
            return true;
         } catch (ActivityNotFoundException var3) {
            return false;
         }
      }
   }

   private void validatePublishPermissions(Collection<String> var1) {
      if(var1 != null) {
         Iterator var3 = var1.iterator();

         String var2;
         do {
            if(!var3.hasNext()) {
               return;
            }

            var2 = (String)var3.next();
         } while(isPublishPermission(var2));

         throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", new Object[]{var2}));
      }
   }

   private void validateReadPermissions(Collection<String> var1) {
      if(var1 != null) {
         Iterator var3 = var1.iterator();

         String var2;
         do {
            if(!var3.hasNext()) {
               return;
            }

            var2 = (String)var3.next();
         } while(!isPublishPermission(var2));

         throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[]{var2}));
      }
   }

   protected LoginClient.Request createLoginRequest(Collection<String> var1) {
      LoginBehavior var2 = this.loginBehavior;
      HashSet var3;
      if(var1 != null) {
         var3 = new HashSet(var1);
      } else {
         var3 = new HashSet();
      }

      LoginClient.Request var4 = new LoginClient.Request(var2, Collections.unmodifiableSet(var3), this.defaultAudience, this.authType, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
      var4.setRerequest(AccessToken.isCurrentAccessTokenActive());
      return var4;
   }

   protected LoginClient.Request createReauthorizeRequest() {
      return new LoginClient.Request(LoginBehavior.DIALOG_ONLY, new HashSet(), this.defaultAudience, "reauthorize", FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
   }

   public String getAuthType() {
      return this.authType;
   }

   public DefaultAudience getDefaultAudience() {
      return this.defaultAudience;
   }

   protected Intent getFacebookActivityIntent(LoginClient.Request var1) {
      Intent var2 = new Intent();
      var2.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
      var2.setAction(var1.getLoginBehavior().toString());
      Bundle var3 = new Bundle();
      var3.putParcelable("request", var1);
      var2.putExtra("com.facebook.LoginFragment:Request", var3);
      return var2;
   }

   public LoginBehavior getLoginBehavior() {
      return this.loginBehavior;
   }

   public void logInWithPublishPermissions(Activity var1, Collection<String> var2) {
      this.validatePublishPermissions(var2);
      LoginClient.Request var3 = this.createLoginRequest(var2);
      this.startLogin(new LoginManager.ActivityStartActivityDelegate(var1), var3);
   }

   public void logInWithPublishPermissions(Fragment var1, Collection<String> var2) {
      this.logInWithPublishPermissions(new FragmentWrapper(var1), var2);
   }

   public void logInWithPublishPermissions(android.support.v4.app.Fragment var1, Collection<String> var2) {
      this.logInWithPublishPermissions(new FragmentWrapper(var1), var2);
   }

   public void logInWithReadPermissions(Activity var1, Collection<String> var2) {
      this.validateReadPermissions(var2);
      LoginClient.Request var3 = this.createLoginRequest(var2);
      this.startLogin(new LoginManager.ActivityStartActivityDelegate(var1), var3);
   }

   public void logInWithReadPermissions(Fragment var1, Collection<String> var2) {
      this.logInWithReadPermissions(new FragmentWrapper(var1), var2);
   }

   public void logInWithReadPermissions(android.support.v4.app.Fragment var1, Collection<String> var2) {
      this.logInWithReadPermissions(new FragmentWrapper(var1), var2);
   }

   public void logOut() {
      AccessToken.setCurrentAccessToken((AccessToken)null);
      Profile.setCurrentProfile((Profile)null);
      this.setExpressLoginStatus(false);
   }

   boolean onActivityResult(int var1, Intent var2) {
      return this.onActivityResult(var1, var2, (FacebookCallback)null);
   }

   boolean onActivityResult(int var1, Intent var2, FacebookCallback<LoginResult> var3) {
      LoginClient.Code var8 = LoginClient.Code.ERROR;
      LoginClient.Request var10 = null;
      FacebookAuthorizationException var6 = null;
      Object var7 = null;
      boolean var5 = false;
      boolean var4 = false;
      Object var12;
      Object var13;
      if(var2 != null) {
         LoginClient.Result var9 = (LoginClient.Result)var2.getParcelableExtra("com.facebook.LoginFragment:Result");
         if(var9 != null) {
            var10 = var9.request;
            var8 = var9.code;
            AccessToken var11;
            if(var1 == -1) {
               if(var9.code == LoginClient.Code.SUCCESS) {
                  var11 = var9.token;
                  var6 = (FacebookAuthorizationException)var7;
               } else {
                  var6 = new FacebookAuthorizationException(var9.errorMessage);
                  var11 = null;
               }
            } else if(var1 == 0) {
               var11 = null;
               var4 = true;
               var6 = (FacebookAuthorizationException)var7;
            } else {
               var11 = null;
               var6 = (FacebookAuthorizationException)var7;
            }

            var13 = var9.loggingExtras;
            var7 = var11;
            var12 = var10;
         } else {
            var7 = null;
            var13 = var7;
            var12 = var7;
            var4 = var5;
            var6 = var10;
         }
      } else if(var1 == 0) {
         var8 = LoginClient.Code.CANCEL;
         var12 = null;
         var7 = var12;
         var13 = var12;
         var4 = true;
      } else {
         var12 = null;
         var7 = var12;
         var13 = var12;
         var4 = false;
      }

      Object var14 = var6;
      if(var6 == null) {
         var14 = var6;
         if(var7 == null) {
            var14 = var6;
            if(!var4) {
               var14 = new FacebookException("Unexpected call to LoginManager.onActivityResult");
            }
         }
      }

      this.logCompleteLogin((Context)null, var8, (Map)var13, (Exception)var14, true, (LoginClient.Request)var12);
      this.finishLogin((AccessToken)var7, (LoginClient.Request)var12, (FacebookException)var14, var4, var3);
      return true;
   }

   public void reauthorizeDataAccess(Activity var1) {
      LoginClient.Request var2 = this.createReauthorizeRequest();
      this.startLogin(new LoginManager.ActivityStartActivityDelegate(var1), var2);
   }

   public void reauthorizeDataAccess(android.support.v4.app.Fragment var1) {
      this.reauthorizeDataAccess(new FragmentWrapper(var1));
   }

   public void registerCallback(CallbackManager var1, final FacebookCallback<LoginResult> var2) {
      if(!(var1 instanceof CallbackManagerImpl)) {
         throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
      } else {
         ((CallbackManagerImpl)var1).registerCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int var1, Intent var2x) {
               return LoginManager.this.onActivityResult(var1, var2x, var2);
            }
         });
      }
   }

   public void resolveError(Activity var1, GraphResponse var2) {
      this.startLogin(new LoginManager.ActivityStartActivityDelegate(var1), this.createLoginRequestFromResponse(var2));
   }

   public void resolveError(Fragment var1, GraphResponse var2) {
      this.resolveError(new FragmentWrapper(var1), var2);
   }

   public void resolveError(android.support.v4.app.Fragment var1, GraphResponse var2) {
      this.resolveError(new FragmentWrapper(var1), var2);
   }

   public void retrieveLoginStatus(Context var1, long var2, LoginStatusCallback var4) {
      this.retrieveLoginStatusImpl(var1, var4, var2);
   }

   public void retrieveLoginStatus(Context var1, LoginStatusCallback var2) {
      this.retrieveLoginStatus(var1, 5000L, var2);
   }

   public LoginManager setAuthType(String var1) {
      this.authType = var1;
      return this;
   }

   public LoginManager setDefaultAudience(DefaultAudience var1) {
      this.defaultAudience = var1;
      return this;
   }

   public LoginManager setLoginBehavior(LoginBehavior var1) {
      this.loginBehavior = var1;
      return this;
   }

   public void unregisterCallback(CallbackManager var1) {
      if(!(var1 instanceof CallbackManagerImpl)) {
         throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
      } else {
         ((CallbackManagerImpl)var1).unregisterCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode());
      }
   }

   static class FragmentStartActivityDelegate implements StartActivityDelegate {

      private final FragmentWrapper fragment;


      FragmentStartActivityDelegate(FragmentWrapper var1) {
         Validate.notNull(var1, "fragment");
         this.fragment = var1;
      }

      public Activity getActivityContext() {
         return this.fragment.getActivity();
      }

      public void startActivityForResult(Intent var1, int var2) {
         this.fragment.startActivityForResult(var1, var2);
      }
   }

   static class ActivityStartActivityDelegate implements StartActivityDelegate {

      private final Activity activity;


      ActivityStartActivityDelegate(Activity var1) {
         Validate.notNull(var1, "activity");
         this.activity = var1;
      }

      public Activity getActivityContext() {
         return this.activity;
      }

      public void startActivityForResult(Intent var1, int var2) {
         this.activity.startActivityForResult(var1, var2);
      }
   }

   static class LoginLoggerHolder {

      private static LoginLogger logger;


      private static LoginLogger getLogger(Context param0) {
         // $FF: Couldn't be decompiled
      }
   }
}
