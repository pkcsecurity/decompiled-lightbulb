package android.support.v4.database;

import android.database.CursorWindow;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class CursorWindowCompat {

   @NonNull
   public static CursorWindow create(@Nullable String var0, long var1) {
      return VERSION.SDK_INT >= 28?new CursorWindow(var0, var1):(VERSION.SDK_INT >= 15?new CursorWindow(var0):new CursorWindow(false));
   }
}
