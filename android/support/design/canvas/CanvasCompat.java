package android.support.design.canvas;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class CanvasCompat {

   public static int saveLayerAlpha(Canvas var0, float var1, float var2, float var3, float var4, int var5) {
      return VERSION.SDK_INT > 21?var0.saveLayerAlpha(var1, var2, var3, var4, var5):var0.saveLayerAlpha(var1, var2, var3, var4, var5, 31);
   }

   public static int saveLayerAlpha(Canvas var0, RectF var1, int var2) {
      return VERSION.SDK_INT > 21?var0.saveLayerAlpha(var1, var2):var0.saveLayerAlpha(var1, var2, 31);
   }
}
