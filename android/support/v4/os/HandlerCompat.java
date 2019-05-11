package android.support.v4.os;

import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class HandlerCompat {

   public static boolean postDelayed(@NonNull Handler var0, @NonNull Runnable var1, @Nullable Object var2, long var3) {
      if(VERSION.SDK_INT >= 28) {
         return var0.postDelayed(var1, var2, var3);
      } else {
         Message var5 = Message.obtain(var0, var1);
         var5.obj = var2;
         return var0.sendMessageDelayed(var5, var3);
      }
   }
}
