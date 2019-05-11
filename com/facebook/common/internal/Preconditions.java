package com.facebook.common.internal;

import javax.annotation.Nullable;

public final class Preconditions {

   private static String badElementIndex(int var0, int var1, @Nullable String var2) {
      if(var0 < 0) {
         return format("%s (%s) must not be negative", new Object[]{var2, Integer.valueOf(var0)});
      } else if(var1 < 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append("negative size: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         return format("%s (%s) must be less than size (%s)", new Object[]{var2, Integer.valueOf(var0), Integer.valueOf(var1)});
      }
   }

   private static String badPositionIndex(int var0, int var1, @Nullable String var2) {
      if(var0 < 0) {
         return format("%s (%s) must not be negative", new Object[]{var2, Integer.valueOf(var0)});
      } else if(var1 < 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append("negative size: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         return format("%s (%s) must not be greater than size (%s)", new Object[]{var2, Integer.valueOf(var0), Integer.valueOf(var1)});
      }
   }

   private static String badPositionIndexes(int var0, int var1, int var2) {
      return var0 >= 0 && var0 <= var2?(var1 >= 0 && var1 <= var2?format("end index (%s) must not be less than start index (%s)", new Object[]{Integer.valueOf(var1), Integer.valueOf(var0)}):badPositionIndex(var1, var2, "end index")):badPositionIndex(var0, var2, "start index");
   }

   public static void checkArgument(boolean var0) {
      if(!var0) {
         throw new IllegalArgumentException();
      }
   }

   public static void checkArgument(boolean var0, @Nullable Object var1) {
      if(!var0) {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   public static void checkArgument(boolean var0, @Nullable String var1, @Nullable Object ... var2) {
      if(!var0) {
         throw new IllegalArgumentException(format(var1, var2));
      }
   }

   public static int checkElementIndex(int var0, int var1) {
      return checkElementIndex(var0, var1, "index");
   }

   public static int checkElementIndex(int var0, int var1, @Nullable String var2) {
      if(var0 >= 0 && var0 < var1) {
         return var0;
      } else {
         throw new IndexOutOfBoundsException(badElementIndex(var0, var1, var2));
      }
   }

   public static <T extends Object> T checkNotNull(T var0) {
      if(var0 == null) {
         throw new NullPointerException();
      } else {
         return var0;
      }
   }

   public static <T extends Object> T checkNotNull(T var0, @Nullable Object var1) {
      if(var0 == null) {
         throw new NullPointerException(String.valueOf(var1));
      } else {
         return var0;
      }
   }

   public static <T extends Object> T checkNotNull(T var0, @Nullable String var1, @Nullable Object ... var2) {
      if(var0 == null) {
         throw new NullPointerException(format(var1, var2));
      } else {
         return var0;
      }
   }

   public static int checkPositionIndex(int var0, int var1) {
      return checkPositionIndex(var0, var1, "index");
   }

   public static int checkPositionIndex(int var0, int var1, @Nullable String var2) {
      if(var0 >= 0 && var0 <= var1) {
         return var0;
      } else {
         throw new IndexOutOfBoundsException(badPositionIndex(var0, var1, var2));
      }
   }

   public static void checkPositionIndexes(int var0, int var1, int var2) {
      if(var0 < 0 || var1 < var0 || var1 > var2) {
         throw new IndexOutOfBoundsException(badPositionIndexes(var0, var1, var2));
      }
   }

   public static void checkState(boolean var0) {
      if(!var0) {
         throw new IllegalStateException();
      }
   }

   public static void checkState(boolean var0, @Nullable Object var1) {
      if(!var0) {
         throw new IllegalStateException(String.valueOf(var1));
      }
   }

   public static void checkState(boolean var0, @Nullable String var1, @Nullable Object ... var2) {
      if(!var0) {
         throw new IllegalStateException(format(var1, var2));
      }
   }

   static String format(@Nullable String var0, @Nullable Object ... var1) {
      var0 = String.valueOf(var0);
      StringBuilder var5 = new StringBuilder(var0.length() + var1.length * 16);
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1.length; ++var2) {
         int var4 = var0.indexOf("%s", var3);
         if(var4 == -1) {
            break;
         }

         var5.append(var0.substring(var3, var4));
         var5.append(var1[var2]);
         var3 = var4 + 2;
      }

      var5.append(var0.substring(var3));
      if(var2 < var1.length) {
         var5.append(" [");
         var3 = var2 + 1;
         var5.append(var1[var2]);

         for(var2 = var3; var2 < var1.length; ++var2) {
            var5.append(", ");
            var5.append(var1[var2]);
         }

         var5.append(']');
      }

      return var5.toString();
   }
}
