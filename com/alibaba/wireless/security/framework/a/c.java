package com.alibaba.wireless.security.framework.a;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.alibaba.wireless.security.open.initialize.ISecurityGuardPlugin;
import dalvik.system.DexClassLoader;

public class c {

   public String a;
   public String b;
   public DexClassLoader c;
   public AssetManager d;
   public Resources e;
   public PackageInfo f;
   public ISecurityGuardPlugin g;


   public c(DexClassLoader var1, Resources var2, PackageInfo var3, ISecurityGuardPlugin var4) {
      this.a = var3.packageName;
      this.c = var1;
      this.d = var2.getAssets();
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.b = this.a();
   }

   private final String a() {
      return this.f.activities != null && this.f.activities.length > 0?this.f.activities[0].name:"";
   }
}
