package com.alibaba.wireless.security.jaq;

import android.content.Context;
import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;

public class SecurityStorage {

   private Context a;


   public SecurityStorage(Context var1) {
      if(var1 != null) {
         this.a = var1.getApplicationContext();
      }

   }

   public String getString(String var1) throws JAQException {
      try {
         var1 = SecurityGuardManager.getInstance(this.a).getDynamicDataStoreComp().getString(var1);
         return var1;
      } catch (SecException var2) {
         var2.printStackTrace();
         throw new JAQException(var2.getErrorCode());
      }
   }

   public int putString(String var1, String var2) throws JAQException {
      try {
         int var3 = SecurityGuardManager.getInstance(this.a).getDynamicDataStoreComp().putString(var1, var2);
         return var3;
      } catch (SecException var4) {
         var4.printStackTrace();
         throw new JAQException(var4.getErrorCode());
      }
   }

   public void removeString(String var1) throws JAQException {
      try {
         SecurityGuardManager.getInstance(this.a).getDynamicDataStoreComp().removeString(var1);
      } catch (SecException var2) {
         var2.printStackTrace();
         throw new JAQException(var2.getErrorCode());
      }
   }
}
