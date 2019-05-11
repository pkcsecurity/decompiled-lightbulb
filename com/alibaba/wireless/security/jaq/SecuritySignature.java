package com.alibaba.wireless.security.jaq;

import android.content.Context;
import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import java.util.HashMap;

public class SecuritySignature {

   private Context a;


   public SecuritySignature(Context var1) {
      if(var1 != null) {
         this.a = var1.getApplicationContext();
      }

   }

   public String atlasSign(String var1, String var2) throws JAQException {
      HashMap var3 = new HashMap();
      var3.put("INPUT", var1);
      var3.put("ATLAS", "a");
      SecurityGuardParamContext var5 = new SecurityGuardParamContext();
      var5.appKey = var2;
      var5.paramMap = var3;
      var5.requestType = 5;

      try {
         if(SecurityGuardManager.getInstance(this.a).getAtlasEncryptComp() != null) {
            return SecurityGuardManager.getInstance(this.a).getSecureSignatureComp().signRequest(var5, "0335");
         } else {
            throw new SecException(1098);
         }
      } catch (SecException var4) {
         var4.printStackTrace();
         throw new JAQException(var4.getErrorCode());
      }
   }

   public String sign(String var1, String var2) throws JAQException {
      HashMap var3 = new HashMap();
      var3.put("INPUT", var1);
      SecurityGuardParamContext var5 = new SecurityGuardParamContext();
      var5.appKey = var2;
      var5.paramMap = var3;
      var5.requestType = 3;

      try {
         var1 = SecurityGuardManager.getInstance(this.a).getSecureSignatureComp().signRequest(var5, "0335");
         return var1;
      } catch (SecException var4) {
         var4.printStackTrace();
         throw new JAQException(var4.getErrorCode());
      }
   }

   public String signWithSimulator(String var1, String var2) throws JAQException {
      HashMap var3 = new HashMap();
      var3.put("INPUT", var1);
      SecurityGuardParamContext var5 = new SecurityGuardParamContext();
      var5.appKey = var2;
      var5.paramMap = var3;
      var5.requestType = 6;

      try {
         var1 = SecurityGuardManager.getInstance(this.a).getSecureSignatureComp().signRequest(var5, "0335");
         return var1;
      } catch (SecException var4) {
         var4.printStackTrace();
         throw new JAQException(var4.getErrorCode());
      }
   }
}
