package com.google.android.gms.common.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.regex.Pattern;

@KeepForSdk
@VisibleForTesting
public class Strings {

   private static final Pattern zzhf = Pattern.compile("\\$\\{(.*?)\\}");


   @Nullable
   @KeepForSdk
   public static String emptyToNull(@Nullable String var0) {
      String var1 = var0;
      if(TextUtils.isEmpty(var0)) {
         var1 = null;
      }

      return var1;
   }

   @KeepForSdk
   public static boolean isEmptyOrWhitespace(@Nullable String var0) {
      return var0 == null || var0.trim().isEmpty();
   }
}
