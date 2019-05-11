package android.support.v4.content.pm;

import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class PackageInfoCompat {

   public static long getLongVersionCode(@NonNull PackageInfo var0) {
      return VERSION.SDK_INT >= 28?var0.getLongVersionCode():(long)var0.versionCode;
   }
}
