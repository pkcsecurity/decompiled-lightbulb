package com.facebook.login;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.LoginClient;
import com.facebook.login.NativeAppLoginMethodHandler;

class FacebookLiteLoginMethodHandler extends NativeAppLoginMethodHandler {

   public static final Creator<FacebookLiteLoginMethodHandler> CREATOR = new Creator() {
      public FacebookLiteLoginMethodHandler createFromParcel(Parcel var1) {
         return new FacebookLiteLoginMethodHandler(var1);
      }
      public FacebookLiteLoginMethodHandler[] newArray(int var1) {
         return new FacebookLiteLoginMethodHandler[var1];
      }
   };


   FacebookLiteLoginMethodHandler(Parcel var1) {
      super(var1);
   }

   FacebookLiteLoginMethodHandler(LoginClient var1) {
      super(var1);
   }

   public int describeContents() {
      return 0;
   }

   String getNameForLogging() {
      return "fb_lite_login";
   }

   boolean tryAuthorize(LoginClient.Request var1) {
      String var2 = LoginClient.getE2E();
      Intent var3 = NativeProtocol.createFacebookLiteIntent(this.loginClient.getActivity(), var1.getApplicationId(), var1.getPermissions(), var2, var1.isRerequest(), var1.hasPublishPermission(), var1.getDefaultAudience(), this.getClientState(var1.getAuthId()), var1.getAuthType());
      this.addLoggingExtra("e2e", var2);
      return this.tryIntent(var3, LoginClient.getLoginRequestCode());
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
   }
}
