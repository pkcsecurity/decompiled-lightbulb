package com.alibaba.wireless.security.jaq;

import android.content.Context;
import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;

public class SecurityCipher {

   private Context a;


   public SecurityCipher(Context var1) {
      if(var1 != null) {
         this.a = var1.getApplicationContext();
      }

   }

   public String atlasEncryptString(String param1) throws JAQException {
      // $FF: Couldn't be decompiled
   }

   public byte[] decryptBinary(byte[] var1, String var2) throws JAQException {
      try {
         var1 = SecurityGuardManager.getInstance(this.a).getStaticDataEncryptComp().staticBinarySafeDecrypt(16, var2, var1, "0335");
         return var1;
      } catch (SecException var3) {
         var3.printStackTrace();
         throw new JAQException(var3.getErrorCode());
      }
   }

   public String decryptString(String var1, String var2) throws JAQException {
      try {
         var1 = SecurityGuardManager.getInstance(this.a).getStaticDataEncryptComp().staticSafeDecrypt(16, var2, var1, "0335");
         return var1;
      } catch (SecException var3) {
         var3.printStackTrace();
         throw new JAQException(var3.getErrorCode());
      }
   }

   public byte[] encryptBinary(byte[] var1, String var2) throws JAQException {
      try {
         var1 = SecurityGuardManager.getInstance(this.a).getStaticDataEncryptComp().staticBinarySafeEncrypt(16, var2, var1, "0335");
         return var1;
      } catch (SecException var3) {
         var3.printStackTrace();
         throw new JAQException(var3.getErrorCode());
      }
   }

   public String encryptString(String var1, String var2) throws JAQException {
      try {
         var1 = SecurityGuardManager.getInstance(this.a).getStaticDataEncryptComp().staticSafeEncrypt(16, var2, var1, "0335");
         return var1;
      } catch (SecException var3) {
         var3.printStackTrace();
         throw new JAQException(var3.getErrorCode());
      }
   }
}
