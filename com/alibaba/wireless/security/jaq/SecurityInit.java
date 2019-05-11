package com.alibaba.wireless.security.jaq;

import android.content.Context;
import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;

public class SecurityInit {

   public static int Initialize(Context var0) throws JAQException {
      try {
         int var1 = SecurityGuardManager.getInitializer().initialize(var0);
         return var1;
      } catch (SecException var2) {
         var2.printStackTrace();
         throw new JAQException(var2.getErrorCode());
      }
   }
}
