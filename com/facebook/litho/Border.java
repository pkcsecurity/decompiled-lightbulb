package com.facebook.litho;

import android.graphics.ComposePathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathDashPathEffect.Style;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ResourceResolver;
import com.facebook.yoga.YogaEdge;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Border {

   static final int EDGE_BOTTOM = 3;
   static final int EDGE_COUNT = 4;
   static final int EDGE_LEFT = 0;
   static final int EDGE_RIGHT = 2;
   static final int EDGE_TOP = 1;
   static final int RADIUS_COUNT = 4;
   final int[] mEdgeColors;
   final int[] mEdgeWidths;
   PathEffect mPathEffect;
   final float[] mRadius;


   private Border() {
      this.mRadius = new float[4];
      this.mEdgeWidths = new int[4];
      this.mEdgeColors = new int[4];
   }

   // $FF: synthetic method
   Border(Object var1) {
      this();
   }

   public static Border.Builder create(ComponentContext var0) {
      return new Border.Builder(var0);
   }

   static YogaEdge edgeFromIndex(int var0) {
      StringBuilder var1;
      if(var0 >= 0 && var0 < 4) {
         switch(var0) {
         case 0:
            return YogaEdge.LEFT;
         case 1:
            return YogaEdge.TOP;
         case 2:
            return YogaEdge.RIGHT;
         case 3:
            return YogaEdge.BOTTOM;
         default:
            var1 = new StringBuilder();
            var1.append("Given unknown edge index: ");
            var1.append(var0);
            throw new IllegalArgumentException(var1.toString());
         }
      } else {
         var1 = new StringBuilder();
         var1.append("Given index out of range of acceptable edges: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   private static int edgeIndex(YogaEdge var0) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaEdge[var0.ordinal()]) {
      case 4:
      case 8:
         return 0;
      case 5:
         return 1;
      case 6:
      case 9:
         return 2;
      case 7:
         return 3;
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Given unsupported edge ");
         var1.append(var0.name());
         throw new IllegalArgumentException(var1.toString());
      }
   }

   static boolean equalValues(int[] var0) {
      if(var0.length != 4) {
         throw new IllegalArgumentException("Given wrongly sized array");
      } else {
         int var2 = var0[0];
         int var3 = var0.length;

         for(int var1 = 1; var1 < var3; ++var1) {
            if(var2 != var0[var1]) {
               return false;
            }
         }

         return true;
      }
   }

   static int getEdgeColor(int[] var0, YogaEdge var1) {
      if(var0.length != 4) {
         throw new IllegalArgumentException("Given wrongly sized array");
      } else {
         return var0[edgeIndex(var1)];
      }
   }

   private static void setEdgeValue(int[] var0, YogaEdge var1, int var2) {
      int var4 = null.$SwitchMap$com$facebook$yoga$YogaEdge[var1.ordinal()];
      int var3 = 0;
      switch(var4) {
      case 1:
         while(var3 < 4) {
            var0[var3] = var2;
            ++var3;
         }

         return;
      case 2:
         var0[1] = var2;
         var0[3] = var2;
         return;
      case 3:
         var0[0] = var2;
         var0[2] = var2;
         return;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
         var0[edgeIndex(var1)] = var2;
         return;
      default:
      }
   }

   void setEdgeColor(YogaEdge var1, @ColorInt int var2) {
      setEdgeValue(this.mEdgeColors, var1, var2);
   }

   void setEdgeWidth(YogaEdge var1, int var2) {
      if(var2 < 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Given negative border width value: ");
         var3.append(var2);
         var3.append(" for edge ");
         var3.append(var1.name());
         throw new IllegalArgumentException(var3.toString());
      } else {
         setEdgeValue(this.mEdgeWidths, var1, var2);
      }
   }

   public static class Builder {

      private static final int MAX_PATH_EFFECTS = 2;
      private final Border mBorder;
      private int mNumPathEffects;
      private PathEffect[] mPathEffects = new PathEffect[2];
      @Nullable
      private ResourceResolver mResourceResolver;


      Builder(ComponentContext var1) {
         this.mResourceResolver = new ResourceResolver(var1);
         this.mBorder = new Border(null);
      }

      private void checkEffectCount() {
         if(this.mNumPathEffects >= 2) {
            throw new IllegalArgumentException("You cannot specify more than 2 effects to compose");
         }
      }

      private void checkNotBuilt() {
         if(this.mResourceResolver == null) {
            throw new IllegalStateException("This builder has already been disposed / built!");
         }
      }

      public Border build() {
         this.checkNotBuilt();
         this.mResourceResolver.release();
         this.mResourceResolver = null;
         if(this.mNumPathEffects == 2) {
            this.mBorder.mPathEffect = new ComposePathEffect(this.mPathEffects[0], this.mPathEffects[1]);
         } else if(this.mNumPathEffects > 0) {
            this.mBorder.mPathEffect = this.mPathEffects[0];
         }

         if(this.mBorder.mPathEffect != null && !Border.equalValues(this.mBorder.mEdgeWidths)) {
            throw new IllegalArgumentException("Borders do not currently support different widths with a path effect");
         } else {
            return this.mBorder;
         }
      }

      public Border.Builder color(YogaEdge var1, @ColorInt int var2) {
         this.checkNotBuilt();
         this.mBorder.setEdgeColor(var1, var2);
         return this;
      }

      public Border.Builder colorRes(YogaEdge var1, @ColorRes int var2) {
         this.checkNotBuilt();
         return this.color(var1, this.mResourceResolver.resolveColorRes(var2));
      }

      @Deprecated
      public Border.Builder cornerEffect(float var1) {
         this.checkNotBuilt();
         if(var1 < 0.0F) {
            throw new IllegalArgumentException("Can\'t have a negative radius value");
         } else {
            this.radiusPx(Math.round(var1));
            return this;
         }
      }

      public Border.Builder dashEffect(float[] var1, float var2) {
         this.checkNotBuilt();
         this.checkEffectCount();
         PathEffect[] var4 = this.mPathEffects;
         int var3 = this.mNumPathEffects;
         this.mNumPathEffects = var3 + 1;
         var4[var3] = new DashPathEffect(var1, var2);
         return this;
      }

      public Border.Builder discreteEffect(float var1, float var2) {
         this.checkNotBuilt();
         this.checkEffectCount();
         PathEffect[] var4 = this.mPathEffects;
         int var3 = this.mNumPathEffects;
         this.mNumPathEffects = var3 + 1;
         var4[var3] = new DiscretePathEffect(var1, var2);
         return this;
      }

      public Border.Builder pathDashEffect(Path var1, float var2, float var3, Style var4) {
         this.checkNotBuilt();
         this.checkEffectCount();
         PathEffect[] var6 = this.mPathEffects;
         int var5 = this.mNumPathEffects;
         this.mNumPathEffects = var5 + 1;
         var6[var5] = new PathDashPathEffect(var1, var2, var3, var4);
         return this;
      }

      public Border.Builder radiusAttr(@AttrRes int var1) {
         return this.radiusAttr(var1, 0);
      }

      public Border.Builder radiusAttr(@AttrRes int var1, @DimenRes int var2) {
         this.checkNotBuilt();
         return this.radiusPx(this.mResourceResolver.resolveDimenSizeAttr(var1, var2));
      }

      public Border.Builder radiusAttr(int var1, @AttrRes int var2, @DimenRes int var3) {
         this.checkNotBuilt();
         return this.radiusPx(var1, this.mResourceResolver.resolveDimenSizeAttr(var2, var3));
      }

      public Border.Builder radiusDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.checkNotBuilt();
         return this.radiusPx(this.mResourceResolver.dipsToPixels(var1));
      }

      public Border.Builder radiusDip(int var1, 
         @Dimension(
            unit = 0
         ) float var2) {
         this.checkNotBuilt();
         return this.radiusPx(var1, this.mResourceResolver.dipsToPixels(var2));
      }

      public Border.Builder radiusPx(@Px int var1) {
         this.checkNotBuilt();

         for(int var2 = 0; var2 < 4; ++var2) {
            this.mBorder.mRadius[var2] = (float)var1;
         }

         return this;
      }

      public Border.Builder radiusPx(int var1, @Px int var2) {
         this.checkNotBuilt();
         if(var1 >= 0 && var1 < 4) {
            this.mBorder.mRadius[var1] = (float)var2;
            return this;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Given invalid corner: ");
            var3.append(var1);
            throw new IllegalArgumentException(var3.toString());
         }
      }

      public Border.Builder radiusRes(@DimenRes int var1) {
         this.checkNotBuilt();
         return this.radiusPx(this.mResourceResolver.resolveDimenSizeRes(var1));
      }

      public Border.Builder radiusRes(int var1, @DimenRes int var2) {
         this.checkNotBuilt();
         return this.radiusPx(var1, this.mResourceResolver.resolveDimenSizeRes(var2));
      }

      public Border.Builder widthAttr(YogaEdge var1, @AttrRes int var2) {
         this.checkNotBuilt();
         return this.widthAttr(var1, var2, 0);
      }

      public Border.Builder widthAttr(YogaEdge var1, @AttrRes int var2, @DimenRes int var3) {
         this.checkNotBuilt();
         return this.widthPx(var1, this.mResourceResolver.resolveDimenSizeAttr(var2, var3));
      }

      public Border.Builder widthDip(YogaEdge var1, 
         @Dimension(
            unit = 0
         ) float var2) {
         this.checkNotBuilt();
         return this.widthPx(var1, this.mResourceResolver.dipsToPixels(var2));
      }

      public Border.Builder widthPx(YogaEdge var1, @Px int var2) {
         this.checkNotBuilt();
         this.mBorder.setEdgeWidth(var1, var2);
         return this;
      }

      public Border.Builder widthRes(YogaEdge var1, @DimenRes int var2) {
         this.checkNotBuilt();
         return this.widthPx(var1, this.mResourceResolver.resolveDimenSizeRes(var2));
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface Corner {

      int BOTTOM_LEFT = 3;
      int BOTTOM_RIGHT = 2;
      int TOP_LEFT = 0;
      int TOP_RIGHT = 1;

   }
}
