package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.util.VisibleForTesting;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@KeepForSdk
@VisibleForTesting
public final class ArrayUtils {

   @KeepForSdk
   public static <T extends Object> T[] appendToArray(T[] var0, T var1) {
      if(var0 == null && var1 == null) {
         throw new IllegalArgumentException("Cannot generate array of generic type w/o class info");
      } else {
         if(var0 == null) {
            var0 = (Object[])Array.newInstance(var1.getClass(), 1);
         } else {
            var0 = Arrays.copyOf(var0, var0.length + 1);
         }

         var0[var0.length - 1] = var1;
         return var0;
      }
   }

   @KeepForSdk
   public static <T extends Object> T[] concat(T[] ... var0) {
      if(var0.length == 0) {
         return (Object[])Array.newInstance(var0.getClass(), 0);
      } else {
         int var1 = 0;

         int var2;
         for(var2 = 0; var1 < var0.length; ++var1) {
            var2 += var0[var1].length;
         }

         Object[] var3 = Arrays.copyOf(var0[0], var2);
         var2 = var0[0].length;

         for(var1 = 1; var1 < var0.length; ++var1) {
            Object[] var4 = var0[var1];
            System.arraycopy(var4, 0, var3, var2, var4.length);
            var2 += var4.length;
         }

         return var3;
      }
   }

   @KeepForSdk
   public static byte[] concatByteArrays(byte[] ... var0) {
      if(var0.length == 0) {
         return new byte[0];
      } else {
         int var1 = 0;

         int var2;
         for(var2 = 0; var1 < var0.length; ++var1) {
            var2 += var0[var1].length;
         }

         byte[] var3 = Arrays.copyOf(var0[0], var2);
         var2 = var0[0].length;

         for(var1 = 1; var1 < var0.length; ++var1) {
            byte[] var4 = var0[var1];
            System.arraycopy(var4, 0, var3, var2, var4.length);
            var2 += var4.length;
         }

         return var3;
      }
   }

   @KeepForSdk
   public static boolean contains(int[] var0, int var1) {
      if(var0 == null) {
         return false;
      } else {
         int var3 = var0.length;

         for(int var2 = 0; var2 < var3; ++var2) {
            if(var0[var2] == var1) {
               return true;
            }
         }

         return false;
      }
   }

   @KeepForSdk
   public static <T extends Object> boolean contains(T[] var0, T var1) {
      int var3;
      if(var0 != null) {
         var3 = var0.length;
      } else {
         var3 = 0;
      }

      int var2 = 0;

      while(true) {
         if(var2 >= var3) {
            var2 = -1;
            break;
         }

         if(Objects.equal(var0[var2], var1)) {
            break;
         }

         ++var2;
      }

      return var2 >= 0;
   }

   @KeepForSdk
   public static <T extends Object> ArrayList<T> newArrayList() {
      return new ArrayList();
   }

   @KeepForSdk
   public static <T extends Object> T[] removeAll(T[] var0, T ... var1) {
      if(var0 == null) {
         return null;
      } else if(var1 != null && var1.length != 0) {
         Object[] var6 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var0.length);
         int var2 = var1.length;
         int var4 = 0;
         int var3;
         int var5;
         Object var7;
         if(var2 == 1) {
            var5 = var0.length;
            var4 = 0;
            var2 = 0;

            while(true) {
               var3 = var2;
               if(var4 >= var5) {
                  break;
               }

               var7 = var0[var4];
               var3 = var2;
               if(!Objects.equal(var1[0], var7)) {
                  var6[var2] = var7;
                  var3 = var2 + 1;
               }

               ++var4;
               var2 = var3;
            }
         } else {
            var5 = var0.length;
            var2 = 0;

            while(true) {
               var3 = var2;
               if(var4 >= var5) {
                  break;
               }

               var7 = var0[var4];
               var3 = var2;
               if(!contains(var1, var7)) {
                  var6[var2] = var7;
                  var3 = var2 + 1;
               }

               ++var4;
               var2 = var3;
            }
         }

         if(var6 == null) {
            return null;
         } else {
            var0 = var6;
            if(var3 != var6.length) {
               var0 = Arrays.copyOf(var6, var3);
            }

            return var0;
         }
      } else {
         return Arrays.copyOf(var0, var0.length);
      }
   }

   @KeepForSdk
   public static <T extends Object> ArrayList<T> toArrayList(T[] var0) {
      int var2 = var0.length;
      ArrayList var3 = new ArrayList(var2);

      for(int var1 = 0; var1 < var2; ++var1) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @KeepForSdk
   public static int[] toPrimitiveArray(Collection<Integer> var0) {
      int var1 = 0;
      if(var0 != null && var0.size() != 0) {
         int[] var2 = new int[var0.size()];

         for(Iterator var3 = var0.iterator(); var3.hasNext(); ++var1) {
            var2[var1] = ((Integer)var3.next()).intValue();
         }

         return var2;
      } else {
         return new int[0];
      }
   }

   @KeepForSdk
   public static Integer[] toWrapperArray(int[] var0) {
      if(var0 == null) {
         return null;
      } else {
         int var2 = var0.length;
         Integer[] var3 = new Integer[var2];

         for(int var1 = 0; var1 < var2; ++var1) {
            var3[var1] = Integer.valueOf(var0[var1]);
         }

         return var3;
      }
   }

   @KeepForSdk
   public static void writeArray(StringBuilder var0, double[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 != 0) {
            var0.append(",");
         }

         var0.append(Double.toString(var1[var2]));
      }

   }

   @KeepForSdk
   public static void writeArray(StringBuilder var0, float[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 != 0) {
            var0.append(",");
         }

         var0.append(Float.toString(var1[var2]));
      }

   }

   @KeepForSdk
   public static void writeArray(StringBuilder var0, int[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 != 0) {
            var0.append(",");
         }

         var0.append(Integer.toString(var1[var2]));
      }

   }

   @KeepForSdk
   public static void writeArray(StringBuilder var0, long[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 != 0) {
            var0.append(",");
         }

         var0.append(Long.toString(var1[var2]));
      }

   }

   @KeepForSdk
   public static <T extends Object> void writeArray(StringBuilder var0, T[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 != 0) {
            var0.append(",");
         }

         var0.append(var1[var2].toString());
      }

   }

   @KeepForSdk
   public static void writeArray(StringBuilder var0, boolean[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 != 0) {
            var0.append(",");
         }

         var0.append(Boolean.toString(var1[var2]));
      }

   }

   @KeepForSdk
   public static void writeStringArray(StringBuilder var0, String[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 != 0) {
            var0.append(",");
         }

         var0.append("\"");
         var0.append(var1[var2]);
         var0.append("\"");
      }

   }
}
