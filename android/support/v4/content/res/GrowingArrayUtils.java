package android.support.v4.content.res;

import java.lang.reflect.Array;

final class GrowingArrayUtils {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;


   public static int[] append(int[] var0, int var1, int var2) {
      int[] var3 = var0;
      if(var1 + 1 > var0.length) {
         var3 = new int[growSize(var1)];
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      var3[var1] = var2;
      return var3;
   }

   public static long[] append(long[] var0, int var1, long var2) {
      long[] var4 = var0;
      if(var1 + 1 > var0.length) {
         var4 = new long[growSize(var1)];
         System.arraycopy(var0, 0, var4, 0, var1);
      }

      var4[var1] = var2;
      return var4;
   }

   public static <T extends Object> T[] append(T[] var0, int var1, T var2) {
      Object[] var3 = var0;
      if(var1 + 1 > var0.length) {
         var3 = (Object[])Array.newInstance(var0.getClass().getComponentType(), growSize(var1));
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      var3[var1] = var2;
      return var3;
   }

   public static boolean[] append(boolean[] var0, int var1, boolean var2) {
      boolean[] var3 = var0;
      if(var1 + 1 > var0.length) {
         var3 = new boolean[growSize(var1)];
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      var3[var1] = var2;
      return var3;
   }

   public static int growSize(int var0) {
      return var0 <= 4?8:var0 * 2;
   }

   public static int[] insert(int[] var0, int var1, int var2, int var3) {
      if(var1 + 1 <= var0.length) {
         System.arraycopy(var0, var2, var0, var2 + 1, var1 - var2);
         var0[var2] = var3;
         return var0;
      } else {
         int[] var4 = new int[growSize(var1)];
         System.arraycopy(var0, 0, var4, 0, var2);
         var4[var2] = var3;
         System.arraycopy(var0, var2, var4, var2 + 1, var0.length - var2);
         return var4;
      }
   }

   public static long[] insert(long[] var0, int var1, int var2, long var3) {
      if(var1 + 1 <= var0.length) {
         System.arraycopy(var0, var2, var0, var2 + 1, var1 - var2);
         var0[var2] = var3;
         return var0;
      } else {
         long[] var5 = new long[growSize(var1)];
         System.arraycopy(var0, 0, var5, 0, var2);
         var5[var2] = var3;
         System.arraycopy(var0, var2, var5, var2 + 1, var0.length - var2);
         return var5;
      }
   }

   public static <T extends Object> T[] insert(T[] var0, int var1, int var2, T var3) {
      if(var1 + 1 <= var0.length) {
         System.arraycopy(var0, var2, var0, var2 + 1, var1 - var2);
         var0[var2] = var3;
         return var0;
      } else {
         Object[] var4 = (Object[])Array.newInstance(var0.getClass().getComponentType(), growSize(var1));
         System.arraycopy(var0, 0, var4, 0, var2);
         var4[var2] = var3;
         System.arraycopy(var0, var2, var4, var2 + 1, var0.length - var2);
         return var4;
      }
   }

   public static boolean[] insert(boolean[] var0, int var1, int var2, boolean var3) {
      if(var1 + 1 <= var0.length) {
         System.arraycopy(var0, var2, var0, var2 + 1, var1 - var2);
         var0[var2] = var3;
         return var0;
      } else {
         boolean[] var4 = new boolean[growSize(var1)];
         System.arraycopy(var0, 0, var4, 0, var2);
         var4[var2] = var3;
         System.arraycopy(var0, var2, var4, var2 + 1, var0.length - var2);
         return var4;
      }
   }
}
