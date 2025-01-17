package android.support.v4.content;

import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class IntentCompat {

   public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
   public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
   public static final String EXTRA_START_PLAYBACK = "android.intent.extra.START_PLAYBACK";


   @NonNull
   public static Intent makeMainSelectorActivity(@NonNull String var0, @NonNull String var1) {
      if(VERSION.SDK_INT >= 15) {
         return Intent.makeMainSelectorActivity(var0, var1);
      } else {
         Intent var2 = new Intent(var0);
         var2.addCategory(var1);
         return var2;
      }
   }
}
