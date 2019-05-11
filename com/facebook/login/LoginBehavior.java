package com.facebook.login;


public enum LoginBehavior {

   // $FF: synthetic field
   private static final LoginBehavior[] $VALUES = new LoginBehavior[]{NATIVE_WITH_FALLBACK, NATIVE_ONLY, KATANA_ONLY, WEB_ONLY, WEB_VIEW_ONLY, DIALOG_ONLY, DEVICE_AUTH};
   DEVICE_AUTH("DEVICE_AUTH", 6, false, false, false, true, false, false),
   DIALOG_ONLY("DIALOG_ONLY", 5, false, true, true, false, true, true),
   KATANA_ONLY("KATANA_ONLY", 2, false, true, false, false, false, false),
   NATIVE_ONLY("NATIVE_ONLY", 1, true, true, false, false, false, true),
   NATIVE_WITH_FALLBACK("NATIVE_WITH_FALLBACK", 0, true, true, true, false, true, true),
   WEB_ONLY("WEB_ONLY", 3, false, false, true, false, true, false),
   WEB_VIEW_ONLY("WEB_VIEW_ONLY", 4, false, false, true, false, false, false);
   private final boolean allowsCustomTabAuth;
   private final boolean allowsDeviceAuth;
   private final boolean allowsFacebookLiteAuth;
   private final boolean allowsGetTokenAuth;
   private final boolean allowsKatanaAuth;
   private final boolean allowsWebViewAuth;


   private LoginBehavior(String var1, int var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8) {
      this.allowsGetTokenAuth = var3;
      this.allowsKatanaAuth = var4;
      this.allowsWebViewAuth = var5;
      this.allowsDeviceAuth = var6;
      this.allowsCustomTabAuth = var7;
      this.allowsFacebookLiteAuth = var8;
   }

   boolean allowsCustomTabAuth() {
      return this.allowsCustomTabAuth;
   }

   boolean allowsDeviceAuth() {
      return this.allowsDeviceAuth;
   }

   boolean allowsFacebookLiteAuth() {
      return this.allowsFacebookLiteAuth;
   }

   boolean allowsGetTokenAuth() {
      return this.allowsGetTokenAuth;
   }

   boolean allowsKatanaAuth() {
      return this.allowsKatanaAuth;
   }

   boolean allowsWebViewAuth() {
      return this.allowsWebViewAuth;
   }
}
