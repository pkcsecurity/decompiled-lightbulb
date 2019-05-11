package android.support.customtabs;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.BundleCompat;

public class TrustedWebUtils {

   public static final String EXTRA_LAUNCH_AS_TRUSTED_WEB_ACTIVITY = "android.support.customtabs.extra.LAUNCH_AS_TRUSTED_WEB_ACTIVITY";


   public static void launchAsTrustedWebActivity(@NonNull Context var0, @NonNull CustomTabsIntent var1, @NonNull Uri var2) {
      if(BundleCompat.getBinder(var1.intent.getExtras(), "android.support.customtabs.extra.SESSION") == null) {
         throw new IllegalArgumentException("Given CustomTabsIntent should be associated with a valid CustomTabsSession");
      } else {
         var1.intent.putExtra("android.support.customtabs.extra.LAUNCH_AS_TRUSTED_WEB_ACTIVITY", true);
         var1.launchUrl(var0, var2);
      }
   }
}
