package android.support.v4.os;

import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.support.v4.os.LocaleListCompat;
import java.util.Locale;

public final class ConfigurationCompat {

   public static LocaleListCompat getLocales(Configuration var0) {
      return VERSION.SDK_INT >= 24?LocaleListCompat.wrap(var0.getLocales()):LocaleListCompat.create(new Locale[]{var0.locale});
   }
}
