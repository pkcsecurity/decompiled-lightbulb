package com.facebook.login;

import com.facebook.AccessToken;
import java.util.Set;

public class LoginResult {

   private final AccessToken accessToken;
   private final Set<String> recentlyDeniedPermissions;
   private final Set<String> recentlyGrantedPermissions;


   public LoginResult(AccessToken var1, Set<String> var2, Set<String> var3) {
      this.accessToken = var1;
      this.recentlyGrantedPermissions = var2;
      this.recentlyDeniedPermissions = var3;
   }

   public AccessToken getAccessToken() {
      return this.accessToken;
   }

   public Set<String> getRecentlyDeniedPermissions() {
      return this.recentlyDeniedPermissions;
   }

   public Set<String> getRecentlyGrantedPermissions() {
      return this.recentlyGrantedPermissions;
   }
}
