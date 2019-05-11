package com.facebook.login;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.login.DeviceAuthDialog;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginMethodHandler;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

class DeviceAuthMethodHandler extends LoginMethodHandler {

   public static final Creator<DeviceAuthMethodHandler> CREATOR = new Creator() {
      public DeviceAuthMethodHandler createFromParcel(Parcel var1) {
         return new DeviceAuthMethodHandler(var1);
      }
      public DeviceAuthMethodHandler[] newArray(int var1) {
         return new DeviceAuthMethodHandler[var1];
      }
   };
   private static ScheduledThreadPoolExecutor backgroundExecutor;


   protected DeviceAuthMethodHandler(Parcel var1) {
      super(var1);
   }

   DeviceAuthMethodHandler(LoginClient var1) {
      super(var1);
   }

   public static ScheduledThreadPoolExecutor getBackgroundExecutor() {
      synchronized(DeviceAuthMethodHandler.class){}

      ScheduledThreadPoolExecutor var0;
      try {
         if(backgroundExecutor == null) {
            backgroundExecutor = new ScheduledThreadPoolExecutor(1);
         }

         var0 = backgroundExecutor;
      } finally {
         ;
      }

      return var0;
   }

   private void showDialog(LoginClient.Request var1) {
      DeviceAuthDialog var2 = this.createDeviceAuthDialog();
      var2.show(this.loginClient.getActivity().getSupportFragmentManager(), "login_with_facebook");
      var2.startLogin(var1);
   }

   protected DeviceAuthDialog createDeviceAuthDialog() {
      return new DeviceAuthDialog();
   }

   public int describeContents() {
      return 0;
   }

   String getNameForLogging() {
      return "device_auth";
   }

   public void onCancel() {
      LoginClient.Result var1 = LoginClient.Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
      this.loginClient.completeAndValidate(var1);
   }

   public void onError(Exception var1) {
      LoginClient.Result var2 = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), (String)null, var1.getMessage());
      this.loginClient.completeAndValidate(var2);
   }

   public void onSuccess(String var1, String var2, String var3, Collection<String> var4, Collection<String> var5, AccessTokenSource var6, Date var7, Date var8, Date var9) {
      AccessToken var10 = new AccessToken(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      LoginClient.Result var11 = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), var10);
      this.loginClient.completeAndValidate(var11);
   }

   boolean tryAuthorize(LoginClient.Request var1) {
      this.showDialog(var1);
      return true;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
   }
}
