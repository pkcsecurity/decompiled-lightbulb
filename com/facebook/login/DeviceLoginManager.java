package com.facebook.login;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginManager;
import java.util.Collection;

public class DeviceLoginManager extends LoginManager {

   private static volatile DeviceLoginManager instance;
   @Nullable
   private String deviceAuthTargetUserId;
   private Uri deviceRedirectUri;


   public static DeviceLoginManager getInstance() {
      // $FF: Couldn't be decompiled
   }

   protected LoginClient.Request createLoginRequest(Collection<String> var1) {
      LoginClient.Request var3 = super.createLoginRequest(var1);
      Uri var2 = this.getDeviceRedirectUri();
      if(var2 != null) {
         var3.setDeviceRedirectUriString(var2.toString());
      }

      String var4 = this.getDeviceAuthTargetUserId();
      if(var4 != null) {
         var3.setDeviceAuthTargetUserId(var4);
      }

      return var3;
   }

   @Nullable
   public String getDeviceAuthTargetUserId() {
      return this.deviceAuthTargetUserId;
   }

   public Uri getDeviceRedirectUri() {
      return this.deviceRedirectUri;
   }

   public void setDeviceAuthTargetUserId(@Nullable String var1) {
      this.deviceAuthTargetUserId = var1;
   }

   public void setDeviceRedirectUri(Uri var1) {
      this.deviceRedirectUri = var1;
   }
}
