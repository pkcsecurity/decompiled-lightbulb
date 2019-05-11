package android.support.v4.util;

import android.support.annotation.RestrictTo;
import java.io.PrintWriter;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class TimeUtils {

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static final int HUNDRED_DAY_FIELD_LEN = 19;
   private static final int SECONDS_PER_DAY = 86400;
   private static final int SECONDS_PER_HOUR = 3600;
   private static final int SECONDS_PER_MINUTE = 60;
   private static char[] sFormatStr = new char[24];
   private static final Object sFormatSync = new Object();


   private static int accumField(int var0, int var1, boolean var2, int var3) {
      return var0 <= 99 && (!var2 || var3 < 3)?(var0 <= 9 && (!var2 || var3 < 2)?(!var2 && var0 <= 0?0:var1 + 1):var1 + 2):var1 + 3;
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static void formatDuration(long var0, long var2, PrintWriter var4) {
      if(var0 == 0L) {
         var4.print("--");
      } else {
         formatDuration(var0 - var2, var4, 0);
      }
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static void formatDuration(long var0, PrintWriter var2) {
      formatDuration(var0, var2, 0);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static void formatDuration(long param0, PrintWriter param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static void formatDuration(long param0, StringBuilder param2) {
      // $FF: Couldn't be decompiled
   }

   private static int formatDurationLocked(long var0, int var2) {
      if(sFormatStr.length < var2) {
         sFormatStr = new char[var2];
      }

      char[] var13 = sFormatStr;
      if(var0 == 0L) {
         while(var2 - 1 > 0) {
            var13[0] = 32;
         }

         var13[0] = 48;
         return 1;
      } else {
         byte var3;
         if(var0 > 0L) {
            var3 = 43;
         } else {
            var3 = 45;
            var0 = -var0;
         }

         int var11 = (int)(var0 % 1000L);
         int var4 = (int)Math.floor((double)(var0 / 1000L));
         int var6;
         if(var4 > 86400) {
            var6 = var4 / 86400;
            var4 -= 86400 * var6;
         } else {
            var6 = 0;
         }

         int var7;
         if(var4 > 3600) {
            var7 = var4 / 3600;
            var4 -= var7 * 3600;
         } else {
            var7 = 0;
         }

         int var5;
         int var8;
         if(var4 > 60) {
            var8 = var4 / 60;
            var5 = var4 - var8 * 60;
         } else {
            var8 = 0;
            var5 = var4;
         }

         int var9;
         int var10;
         boolean var12;
         byte var16;
         if(var2 != 0) {
            var4 = accumField(var6, 1, false, 0);
            if(var4 > 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            var4 += accumField(var7, 1, var12, 2);
            if(var4 > 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            var4 += accumField(var8, 1, var12, 2);
            if(var4 > 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            var9 = var4 + accumField(var5, 1, var12, 2);
            if(var9 > 0) {
               var16 = 3;
            } else {
               var16 = 0;
            }

            var9 += accumField(var11, 2, true, var16) + 1;
            var4 = 0;

            while(true) {
               var10 = var4;
               if(var9 >= var2) {
                  break;
               }

               var13[var4] = 32;
               ++var4;
               ++var9;
            }
         } else {
            var10 = 0;
         }

         var13[var10] = (char)var3;
         var9 = var10 + 1;
         boolean var14;
         if(var2 != 0) {
            var14 = true;
         } else {
            var14 = false;
         }

         var6 = printField(var13, var6, 'd', var9, false, 0);
         if(var6 != var9) {
            var12 = true;
         } else {
            var12 = false;
         }

         if(var14) {
            var16 = 2;
         } else {
            var16 = 0;
         }

         var6 = printField(var13, var7, 'h', var6, var12, var16);
         if(var6 != var9) {
            var12 = true;
         } else {
            var12 = false;
         }

         if(var14) {
            var16 = 2;
         } else {
            var16 = 0;
         }

         var6 = printField(var13, var8, 'm', var6, var12, var16);
         if(var6 != var9) {
            var12 = true;
         } else {
            var12 = false;
         }

         if(var14) {
            var16 = 2;
         } else {
            var16 = 0;
         }

         var4 = printField(var13, var5, 's', var6, var12, var16);
         byte var15;
         if(var14 && var4 != var9) {
            var15 = 3;
         } else {
            var15 = 0;
         }

         var2 = printField(var13, var11, 'm', var4, true, var15);
         var13[var2] = 115;
         return var2 + 1;
      }
   }

   private static int printField(char[] var0, int var1, char var2, int var3, boolean var4, int var5) {
      int var6;
      if(!var4) {
         var6 = var3;
         if(var1 <= 0) {
            return var6;
         }
      }

      int var7;
      if((!var4 || var5 < 3) && var1 <= 99) {
         var6 = var3;
      } else {
         var7 = var1 / 100;
         var0[var3] = (char)(var7 + 48);
         var6 = var3 + 1;
         var1 -= var7 * 100;
      }

      label42: {
         if((!var4 || var5 < 2) && var1 <= 9) {
            var7 = var6;
            var5 = var1;
            if(var3 == var6) {
               break label42;
            }
         }

         var3 = var1 / 10;
         var0[var6] = (char)(var3 + 48);
         var7 = var6 + 1;
         var5 = var1 - var3 * 10;
      }

      var0[var7] = (char)(var5 + 48);
      var1 = var7 + 1;
      var0[var1] = var2;
      var6 = var1 + 1;
      return var6;
   }
}
