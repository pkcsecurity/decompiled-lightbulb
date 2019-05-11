package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.IconCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class IconCompatParcelizer {

   public static IconCompat read(q var0) {
      IconCompat var1 = new IconCompat();
      var1.mType = var0.b(var1.mType, 1);
      var1.mData = var0.b(var1.mData, 2);
      var1.mParcelable = var0.b(var1.mParcelable, 3);
      var1.mInt1 = var0.b(var1.mInt1, 4);
      var1.mInt2 = var0.b(var1.mInt2, 5);
      var1.mTintList = (ColorStateList)var0.b(var1.mTintList, 6);
      var1.mTintModeStr = var0.b(var1.mTintModeStr, 7);
      var1.onPostParceling();
      return var1;
   }

   public static void write(IconCompat var0, q var1) {
      var1.a(true, true);
      var0.onPreParceling(var1.a());
      var1.a(var0.mType, 1);
      var1.a(var0.mData, 2);
      var1.a(var0.mParcelable, 3);
      var1.a(var0.mInt1, 4);
      var1.a(var0.mInt2, 5);
      var1.a(var0.mTintList, 6);
      var1.a(var0.mTintModeStr, 7);
   }
}
