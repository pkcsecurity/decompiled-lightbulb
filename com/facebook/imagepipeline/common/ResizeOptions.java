package com.facebook.imagepipeline.common;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.HashCodeUtil;
import java.util.Locale;
import javax.annotation.Nullable;

public class ResizeOptions {

   public static final float DEFAULT_ROUNDUP_FRACTION = 0.6666667F;
   public final int height;
   public final float maxBitmapSize;
   public final float roundUpFraction;
   public final int width;


   public ResizeOptions(int var1, int var2) {
      this(var1, var2, 2048.0F);
   }

   public ResizeOptions(int var1, int var2, float var3) {
      this(var1, var2, var3, 0.6666667F);
   }

   public ResizeOptions(int var1, int var2, float var3, float var4) {
      boolean var6 = false;
      boolean var5;
      if(var1 > 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      var5 = var6;
      if(var2 > 0) {
         var5 = true;
      }

      Preconditions.checkArgument(var5);
      this.width = var1;
      this.height = var2;
      this.maxBitmapSize = var3;
      this.roundUpFraction = var4;
   }

   @Nullable
   public static ResizeOptions forDimensions(int var0, int var1) {
      return var0 > 0 && var1 > 0?new ResizeOptions(var0, var1):null;
   }

   @Nullable
   public static ResizeOptions forSquareSize(int var0) {
      return var0 <= 0?null:new ResizeOptions(var0, var0);
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof ResizeOptions)) {
         return false;
      } else {
         ResizeOptions var2 = (ResizeOptions)var1;
         return this.width == var2.width && this.height == var2.height;
      }
   }

   public int hashCode() {
      return HashCodeUtil.hashCode(this.width, this.height);
   }

   public String toString() {
      return String.format((Locale)null, "%dx%d", new Object[]{Integer.valueOf(this.width), Integer.valueOf(this.height)});
   }
}
