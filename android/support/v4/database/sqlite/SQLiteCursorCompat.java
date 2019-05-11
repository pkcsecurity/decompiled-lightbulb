package android.support.v4.database.sqlite;

import android.database.sqlite.SQLiteCursor;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class SQLiteCursorCompat {

   public static void setFillWindowForwardOnly(@NonNull SQLiteCursor var0, boolean var1) {
      if(VERSION.SDK_INT >= 28) {
         var0.setFillWindowForwardOnly(var1);
      }

   }
}
