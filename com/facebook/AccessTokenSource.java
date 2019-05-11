package com.facebook;


public enum AccessTokenSource {

   // $FF: synthetic field
   private static final AccessTokenSource[] $VALUES = new AccessTokenSource[]{NONE, FACEBOOK_APPLICATION_WEB, FACEBOOK_APPLICATION_NATIVE, FACEBOOK_APPLICATION_SERVICE, WEB_VIEW, CHROME_CUSTOM_TAB, TEST_USER, CLIENT_TOKEN, DEVICE_AUTH};
   CHROME_CUSTOM_TAB("CHROME_CUSTOM_TAB", 5, true),
   CLIENT_TOKEN("CLIENT_TOKEN", 7, true),
   DEVICE_AUTH("DEVICE_AUTH", 8, true),
   FACEBOOK_APPLICATION_NATIVE("FACEBOOK_APPLICATION_NATIVE", 2, true),
   FACEBOOK_APPLICATION_SERVICE("FACEBOOK_APPLICATION_SERVICE", 3, true),
   FACEBOOK_APPLICATION_WEB("FACEBOOK_APPLICATION_WEB", 1, true),
   NONE("NONE", 0, false),
   TEST_USER("TEST_USER", 6, true),
   WEB_VIEW("WEB_VIEW", 4, true);
   private final boolean canExtendToken;


   private AccessTokenSource(String var1, int var2, boolean var3) {
      this.canExtendToken = var3;
   }

   boolean canExtendToken() {
      return this.canExtendToken;
   }
}
