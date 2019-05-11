package com.facebook.react.module.model;


public class ReactModuleInfo {

   private final boolean mCanOverrideExistingModule;
   private final boolean mHasConstants;
   private final String mName;
   private final boolean mNeedsEagerInit;


   public ReactModuleInfo(String var1, boolean var2, boolean var3, boolean var4) {
      this.mName = var1;
      this.mCanOverrideExistingModule = var2;
      this.mNeedsEagerInit = var3;
      this.mHasConstants = var4;
   }

   public boolean canOverrideExistingModule() {
      return this.mCanOverrideExistingModule;
   }

   public boolean hasConstants() {
      return this.mHasConstants;
   }

   public String name() {
      return this.mName;
   }

   public boolean needsEagerInit() {
      return this.mNeedsEagerInit;
   }
}
