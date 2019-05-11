package com.alibaba.wireless.security.framework.a;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.util.Log;
import com.alibaba.wireless.security.framework.a.a;
import com.alibaba.wireless.security.framework.a.b;
import com.alibaba.wireless.security.framework.a.c;

public class d {

   protected b a;
   private String b;
   private String c;
   private c d;
   private com.alibaba.wireless.security.framework.b e;
   private AssetManager f;
   private Resources g;
   private Theme h;
   private ActivityInfo i;
   private Activity j;


   public d(Activity var1) {
      this.j = var1;
   }

   private void f() {
      PackageInfo var5 = this.d.f;
      if(var5.activities != null && var5.activities.length > 0) {
         String var6 = this.b;
         int var1 = 0;
         if(var6 == null) {
            this.b = var5.activities[0].name;
         }

         int var3 = var5.applicationInfo.theme;
         ActivityInfo[] var8 = var5.activities;

         for(int var4 = var8.length; var1 < var4; ++var1) {
            ActivityInfo var7 = var8[var1];
            if(var7.name.equals(this.b)) {
               this.i = var7;
               if(this.i.theme == 0) {
                  if(var3 != 0) {
                     this.i.theme = var3;
                  } else {
                     int var2;
                     if(VERSION.SDK_INT >= 14) {
                        var7 = this.i;
                        var2 = 16974120;
                     } else {
                        var7 = this.i;
                        var2 = 16973829;
                     }

                     var7.theme = var2;
                  }
               }
            }
         }
      }

   }

   private void g() {
      StringBuilder var1 = new StringBuilder();
      var1.append("handleActivityInfo, theme=");
      var1.append(this.i.theme);
      Log.d("DLProxyImpl", var1.toString());
      if(this.i.theme > 0) {
         this.j.setTheme(this.i.theme);
      }

      Theme var3 = this.j.getTheme();
      this.h = this.g.newTheme();
      this.h.setTo(var3);

      try {
         this.h.applyStyle(this.i.theme, true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   @TargetApi(14)
   protected void a() {
      try {
         Object var1 = this.b().loadClass(this.b).getConstructor(new Class[0]).newInstance(new Object[0]);
         this.a = (b)var1;
         ((a)this.j).attach(this.a, this.e);
         StringBuilder var2 = new StringBuilder();
         var2.append("instance = ");
         var2.append(var1);
         Log.d("DLProxyImpl", var2.toString());
         this.a.attach(this.j, this.d);
         Bundle var4 = new Bundle();
         var4.putInt("extra.from", 1);
         this.a.onCreate(var4);
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public void a(Intent var1) {
      var1.setExtrasClassLoader(com.alibaba.wireless.security.framework.b.c.a);
      this.c = var1.getStringExtra("extra.package");
      this.b = var1.getStringExtra("extra.class");
      StringBuilder var2 = new StringBuilder();
      var2.append("mClass=");
      var2.append(this.b);
      var2.append(" mPackageName=");
      var2.append(this.c);
      Log.d("DLProxyImpl", var2.toString());
      this.e = com.alibaba.wireless.security.framework.b.a((Context)this.j);
      this.d = this.e.b(this.c);
      this.f = this.d.d;
      this.g = this.d.e;
      this.f();
      this.g();
      this.a();
   }

   public ClassLoader b() {
      return this.d.c;
   }

   public AssetManager c() {
      return this.f;
   }

   public Resources d() {
      return this.g;
   }

   public Theme e() {
      return this.h;
   }
}
