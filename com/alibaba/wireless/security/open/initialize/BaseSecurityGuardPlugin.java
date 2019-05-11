package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.alibaba.wireless.security.open.IRouterComponent;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.initialize.ISecurityGuardPlugin;

public abstract class BaseSecurityGuardPlugin implements ISecurityGuardPlugin {

   private Context a = null;
   private IRouterComponent b = null;
   private PackageInfo c = null;


   public Context getContext() {
      return this.a;
   }

   public <T extends Object> T getInterface(Class<T> param1) {
      // $FF: Couldn't be decompiled
   }

   public String getMetaData(String var1) {
      PackageInfo var2 = this.getPackageInfo();
      return var2 != null?var2.applicationInfo.metaData.getString(var1):"";
   }

   public PackageInfo getPackageInfo() {
      return this.c;
   }

   public IRouterComponent getRouter() {
      return this.b;
   }

   public String getVersion() {
      return this.getPackageInfo().versionName;
   }

   public IRouterComponent onPluginLoaded(Context var1, IRouterComponent var2, PackageInfo var3, String var4) throws SecException {
      this.setContext(var1);
      this.setRouter(var2);
      this.setPackageInfo(var3);
      return var2;
   }

   public void setContext(Context var1) {
      this.a = var1;
   }

   public void setPackageInfo(PackageInfo var1) {
      this.c = var1;
   }

   public void setRouter(IRouterComponent var1) {
      this.b = var1;
   }
}
