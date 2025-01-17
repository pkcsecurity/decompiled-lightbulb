package android.support.v4.content.res;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class ConfigurationHelper {

   public static int getDensityDpi(@NonNull Resources var0) {
      return VERSION.SDK_INT >= 17?var0.getConfiguration().densityDpi:var0.getDisplayMetrics().densityDpi;
   }
}
