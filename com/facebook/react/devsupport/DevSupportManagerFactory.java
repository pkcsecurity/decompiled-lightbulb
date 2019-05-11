package com.facebook.react.devsupport;

import android.content.Context;
import com.facebook.react.devsupport.DisabledDevSupportManager;
import com.facebook.react.devsupport.ReactInstanceDevCommandsHandler;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import javax.annotation.Nullable;

public class DevSupportManagerFactory {

   private static final String DEVSUPPORT_IMPL_CLASS = "DevSupportManagerImpl";
   private static final String DEVSUPPORT_IMPL_PACKAGE = "com.facebook.react.devsupport";


   public static DevSupportManager create(Context var0, ReactInstanceDevCommandsHandler var1, @Nullable String var2, boolean var3, int var4) {
      return create(var0, var1, var2, var3, (RedBoxHandler)null, (DevBundleDownloadListener)null, var4);
   }

   public static DevSupportManager create(Context var0, ReactInstanceDevCommandsHandler var1, @Nullable String var2, boolean var3, @Nullable RedBoxHandler var4, @Nullable DevBundleDownloadListener var5, int var6) {
      if(!var3) {
         return new DisabledDevSupportManager();
      } else {
         try {
            StringBuilder var7 = new StringBuilder("com.facebook.react.devsupport");
            var7.append(".");
            var7.append("DevSupportManagerImpl");
            DevSupportManager var9 = (DevSupportManager)Class.forName(var7.toString()).getConstructor(new Class[]{Context.class, ReactInstanceDevCommandsHandler.class, String.class, Boolean.TYPE, RedBoxHandler.class, DevBundleDownloadListener.class, Integer.TYPE}).newInstance(new Object[]{var0, var1, var2, Boolean.valueOf(true), var4, var5, Integer.valueOf(var6)});
            return var9;
         } catch (Exception var8) {
            throw new RuntimeException("Requested enabled DevSupportManager, but DevSupportManagerImpl class was not found or could not be created", var8);
         }
      }
   }
}
