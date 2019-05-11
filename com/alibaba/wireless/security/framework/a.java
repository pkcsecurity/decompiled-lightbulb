package com.alibaba.wireless.security.framework;

import android.content.Intent;
import android.os.Parcelable;
import java.io.Serializable;

public class a extends Intent {

   private String a;
   private String b;


   private void a(Object var1) {
      ClassLoader var2 = var1.getClass().getClassLoader();
      com.alibaba.wireless.security.framework.b.c.a = var2;
      this.setExtrasClassLoader(var2);
   }

   public String a() {
      return this.a;
   }

   public void a(String var1) {
      this.a = var1;
   }

   public String b() {
      return this.b;
   }

   public Intent putExtra(String var1, Parcelable var2) {
      this.a((Object)var2);
      return super.putExtra(var1, var2);
   }

   public Intent putExtra(String var1, Serializable var2) {
      this.a((Object)var2);
      return super.putExtra(var1, var2);
   }
}
