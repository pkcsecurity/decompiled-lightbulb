package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent;
import com.alibaba.wireless.security.open.initialize.c;
import java.util.HashSet;
import java.util.Set;

public class b {

   boolean a = false;
   private Set<IInitializeComponent.IInitFinishListener> b = new HashSet();
   private Object c = new Object();


   private void a() {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static void a(b var0) {
      var0.a();
   }

   private void b() {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static void b(b var0) {
      var0.b();
   }

   public int a(Context param1, String param2, boolean param3) throws SecException {
      // $FF: Couldn't be decompiled
   }

   public void a(IInitializeComponent.IInitFinishListener param1) throws SecException {
      // $FF: Couldn't be decompiled
   }

   public boolean a(Context var1) throws SecException {
      return true;
   }

   public void b(Context var1, String var2, boolean var3) throws SecException {
      if(var1 == null) {
         throw new SecException(101);
      } else {
         (new Thread(new c(this, var1, var2, var3))).start();
      }
   }

   public void b(IInitializeComponent.IInitFinishListener param1) throws SecException {
      // $FF: Couldn't be decompiled
   }
}
