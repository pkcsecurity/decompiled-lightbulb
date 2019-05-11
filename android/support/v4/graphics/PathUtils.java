package android.support.v4.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.PathSegment;
import java.util.ArrayList;
import java.util.Collection;

public final class PathUtils {

   @NonNull
   @RequiresApi(26)
   public static Collection<PathSegment> flatten(@NonNull Path var0) {
      return flatten(var0, 0.5F);
   }

   @NonNull
   @RequiresApi(26)
   public static Collection<PathSegment> flatten(@NonNull Path var0, 
      @FloatRange(
         from = 0.0D
      ) float var1) {
      float[] var12 = var0.approximate(var1);
      int var8 = var12.length / 3;
      ArrayList var11 = new ArrayList(var8);

      for(int var7 = 1; var7 < var8; ++var7) {
         int var9 = var7 * 3;
         int var10 = (var7 - 1) * 3;
         var1 = var12[var9];
         float var2 = var12[var9 + 1];
         float var3 = var12[var9 + 2];
         float var4 = var12[var10];
         float var5 = var12[var10 + 1];
         float var6 = var12[var10 + 2];
         if(var1 != var4 && (var2 != var5 || var3 != var6)) {
            var11.add(new PathSegment(new PointF(var5, var6), var4, new PointF(var2, var3), var1));
         }
      }

      return var11;
   }
}
