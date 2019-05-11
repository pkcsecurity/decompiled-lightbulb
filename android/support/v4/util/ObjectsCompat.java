package android.support.v4.util;

import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class ObjectsCompat {

   public static boolean equals(@Nullable Object var0, @Nullable Object var1) {
      return VERSION.SDK_INT >= 19?Objects.equals(var0, var1):var0 == var1 || var0 != null && var0.equals(var1);
   }

   public static int hash(@Nullable Object ... var0) {
      return VERSION.SDK_INT >= 19?Objects.hash(var0):Arrays.hashCode(var0);
   }

   public static int hashCode(@Nullable Object var0) {
      return var0 != null?var0.hashCode():0;
   }
}
