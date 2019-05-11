package com.facebook.login;


public enum DefaultAudience {

   // $FF: synthetic field
   private static final DefaultAudience[] $VALUES = new DefaultAudience[]{NONE, ONLY_ME, FRIENDS, EVERYONE};
   EVERYONE("EVERYONE", 3, "everyone"),
   FRIENDS("FRIENDS", 2, "friends"),
   NONE("NONE", 0, (String)null),
   ONLY_ME("ONLY_ME", 1, "only_me");
   private final String nativeProtocolAudience;


   private DefaultAudience(String var1, int var2, String var3) {
      this.nativeProtocolAudience = var3;
   }

   public String getNativeProtocolAudience() {
      return this.nativeProtocolAudience;
   }
}
