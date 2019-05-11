package android.support.v4.view;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import android.view.Menu;
import android.view.MenuItem;

public final class MenuCompat {

   @SuppressLint({"NewApi"})
   public static void setGroupDividerEnabled(Menu var0, boolean var1) {
      if(var0 instanceof SupportMenu) {
         ((SupportMenu)var0).setGroupDividerEnabled(var1);
      } else {
         if(VERSION.SDK_INT >= 28) {
            var0.setGroupDividerEnabled(var1);
         }

      }
   }

   @Deprecated
   public static void setShowAsAction(MenuItem var0, int var1) {
      var0.setShowAsAction(var1);
   }
}
