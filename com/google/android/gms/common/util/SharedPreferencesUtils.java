package com.google.android.gms.common.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.File;

@KeepForSdk
public class SharedPreferencesUtils {

   @Deprecated
   @KeepForSdk
   public static void publishWorldReadableSharedPreferences(Context var0, Editor var1, String var2) {
      File var4 = new File(var0.getApplicationInfo().dataDir, "shared_prefs");
      File var3 = var4.getParentFile();
      if(var3 != null) {
         var3.setExecutable(true, false);
      }

      var4.setExecutable(true, false);
      var1.commit();
      (new File(var4, String.valueOf(var2).concat(".xml"))).setReadable(true, false);
   }
}
