package com.facebook.login.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import com.facebook.login.DeviceLoginManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

public class DeviceLoginButton extends LoginButton {

   private Uri deviceRedirectUri;


   public DeviceLoginButton(Context var1) {
      super(var1);
   }

   public DeviceLoginButton(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public DeviceLoginButton(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public Uri getDeviceRedirectUri() {
      return this.deviceRedirectUri;
   }

   protected LoginButton.LoginClickListener getNewLoginClickListener() {
      return new DeviceLoginButton.DeviceLoginClickListener(null);
   }

   public void setDeviceRedirectUri(Uri var1) {
      this.deviceRedirectUri = var1;
   }

   class DeviceLoginClickListener extends LoginButton.LoginClickListener {

      private DeviceLoginClickListener() {
         super();
      }

      // $FF: synthetic method
      DeviceLoginClickListener(Object var2) {
         this();
      }

      protected LoginManager getLoginManager() {
         DeviceLoginManager var1 = DeviceLoginManager.getInstance();
         var1.setDefaultAudience(DeviceLoginButton.this.getDefaultAudience());
         var1.setLoginBehavior(LoginBehavior.DEVICE_AUTH);
         var1.setDeviceRedirectUri(DeviceLoginButton.this.getDeviceRedirectUri());
         return var1;
      }
   }
}
