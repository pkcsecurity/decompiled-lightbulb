package com.google.android.gms.common.images;


public final class Size {

   private final int zand;
   private final int zane;


   public Size(int var1, int var2) {
      this.zand = var1;
      this.zane = var2;
   }

   public static Size parseSize(String var0) throws NumberFormatException {
      if(var0 == null) {
         throw new IllegalArgumentException("string must not be null");
      } else {
         int var2 = var0.indexOf(42);
         int var1 = var2;
         if(var2 < 0) {
            var1 = var0.indexOf(120);
         }

         if(var1 < 0) {
            throw zah(var0);
         } else {
            try {
               Size var3 = new Size(Integer.parseInt(var0.substring(0, var1)), Integer.parseInt(var0.substring(var1 + 1)));
               return var3;
            } catch (NumberFormatException var4) {
               throw zah(var0);
            }
         }
      }
   }

   private static NumberFormatException zah(String var0) {
      StringBuilder var1 = new StringBuilder(String.valueOf(var0).length() + 16);
      var1.append("Invalid Size: \"");
      var1.append(var0);
      var1.append("\"");
      throw new NumberFormatException(var1.toString());
   }

   public final boolean equals(Object var1) {
      if(var1 == null) {
         return false;
      } else if(this == var1) {
         return true;
      } else if(var1 instanceof Size) {
         Size var2 = (Size)var1;
         return this.zand == var2.zand && this.zane == var2.zane;
      } else {
         return false;
      }
   }

   public final int getHeight() {
      return this.zane;
   }

   public final int getWidth() {
      return this.zand;
   }

   public final int hashCode() {
      return this.zane ^ (this.zand << 16 | this.zand >>> 16);
   }

   public final String toString() {
      int var1 = this.zand;
      int var2 = this.zane;
      StringBuilder var3 = new StringBuilder(23);
      var3.append(var1);
      var3.append("x");
      var3.append(var2);
      return var3.toString();
   }
}
