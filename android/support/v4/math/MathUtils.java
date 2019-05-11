package android.support.v4.math;


public class MathUtils {

   public static double clamp(double var0, double var2, double var4) {
      return var0 < var2?var2:(var0 > var4?var4:var0);
   }

   public static float clamp(float var0, float var1, float var2) {
      return var0 < var1?var1:(var0 > var2?var2:var0);
   }

   public static int clamp(int var0, int var1, int var2) {
      return var0 < var1?var1:(var0 > var2?var2:var0);
   }
}
