package android.support.v4.graphics.drawable;

import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.IconCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class IconCompatParcelizer extends androidx.core.graphics.drawable.IconCompatParcelizer {

   public static IconCompat read(q var0) {
      return androidx.core.graphics.drawable.IconCompatParcelizer.read(var0);
   }

   public static void write(IconCompat var0, q var1) {
      androidx.core.graphics.drawable.IconCompatParcelizer.write(var0, var1);
   }
}
