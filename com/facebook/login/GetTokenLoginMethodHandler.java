package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.login.GetTokenClient;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginMethodHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class GetTokenLoginMethodHandler extends LoginMethodHandler {

   public static final Creator<GetTokenLoginMethodHandler> CREATOR = new Creator() {
      public GetTokenLoginMethodHandler createFromParcel(Parcel var1) {
         return new GetTokenLoginMethodHandler(var1);
      }
      public GetTokenLoginMethodHandler[] newArray(int var1) {
         return new GetTokenLoginMethodHandler[var1];
      }
   };
   private GetTokenClient getTokenClient;


   GetTokenLoginMethodHandler(Parcel var1) {
      super(var1);
   }

   GetTokenLoginMethodHandler(LoginClient var1) {
      super(var1);
   }

   void cancel() {
      if(this.getTokenClient != null) {
         this.getTokenClient.cancel();
         this.getTokenClient.setCompletedListener((PlatformServiceClient.CompletedListener)null);
         this.getTokenClient = null;
      }

   }

   void complete(final LoginClient.Request var1, final Bundle var2) {
      String var3 = var2.getString("com.facebook.platform.extra.USER_ID");
      if(var3 != null && !var3.isEmpty()) {
         this.onComplete(var1, var2);
      } else {
         this.loginClient.notifyBackgroundProcessingStart();
         Utility.getGraphMeRequestWithCacheAsync(var2.getString("com.facebook.platform.extra.ACCESS_TOKEN"), new Utility.GraphMeRequestWithCacheCallback() {
            public void onFailure(FacebookException var1x) {
               GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", var1x.getMessage()));
            }
            public void onSuccess(JSONObject var1x) {
               try {
                  String var3 = var1x.getString("id");
                  var2.putString("com.facebook.platform.extra.USER_ID", var3);
                  GetTokenLoginMethodHandler.this.onComplete(var1, var2);
               } catch (JSONException var2x) {
                  GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", var2x.getMessage()));
               }
            }
         });
      }
   }

   public int describeContents() {
      return 0;
   }

   String getNameForLogging() {
      return "get_token";
   }

   void getTokenCompleted(LoginClient.Request var1, Bundle var2) {
      if(this.getTokenClient != null) {
         this.getTokenClient.setCompletedListener((PlatformServiceClient.CompletedListener)null);
      }

      this.getTokenClient = null;
      this.loginClient.notifyBackgroundProcessingStop();
      if(var2 != null) {
         ArrayList var3 = var2.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
         Set var4 = var1.getPermissions();
         if(var3 != null && (var4 == null || var3.containsAll(var4))) {
            this.complete(var1, var2);
            return;
         }

         HashSet var6 = new HashSet();
         Iterator var7 = var4.iterator();

         while(var7.hasNext()) {
            String var5 = (String)var7.next();
            if(!var3.contains(var5)) {
               var6.add(var5);
            }
         }

         if(!var6.isEmpty()) {
            this.addLoggingExtra("new_permissions", TextUtils.join(",", var6));
         }

         var1.setPermissions(var6);
      }

      this.loginClient.tryNextHandler();
   }

   void onComplete(LoginClient.Request var1, Bundle var2) {
      AccessToken var3 = createAccessTokenFromNativeLogin(var2, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE, var1.getApplicationId());
      LoginClient.Result var4 = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), var3);
      this.loginClient.completeAndValidate(var4);
   }

   boolean tryAuthorize(final LoginClient.Request var1) {
      this.getTokenClient = new GetTokenClient(this.loginClient.getActivity(), var1.getApplicationId());
      if(!this.getTokenClient.start()) {
         return false;
      } else {
         this.loginClient.notifyBackgroundProcessingStart();
         PlatformServiceClient.CompletedListener var2 = new PlatformServiceClient.CompletedListener() {
            public void completed(Bundle var1x) {
               GetTokenLoginMethodHandler.this.getTokenCompleted(var1, var1x);
            }
         };
         this.getTokenClient.setCompletedListener(var2);
         return true;
      }
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
   }
}
