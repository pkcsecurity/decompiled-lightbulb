package android.support.v4.view.animation;

import android.graphics.Path;
import android.os.Build.VERSION;
import android.support.v4.view.animation.PathInterpolatorApi14;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public final class PathInterpolatorCompat {

   public static Interpolator create(float var0, float var1) {
      return (Interpolator)(VERSION.SDK_INT >= 21?new PathInterpolator(var0, var1):new PathInterpolatorApi14(var0, var1));
   }

   public static Interpolator create(float var0, float var1, float var2, float var3) {
      return (Interpolator)(VERSION.SDK_INT >= 21?new PathInterpolator(var0, var1, var2, var3):new PathInterpolatorApi14(var0, var1, var2, var3));
   }

   public static Interpolator create(Path var0) {
      return (Interpolator)(VERSION.SDK_INT >= 21?new PathInterpolator(var0):new PathInterpolatorApi14(var0));
   }
}
