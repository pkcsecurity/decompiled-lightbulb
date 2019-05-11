package android.support.design.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class FlowLayout extends ViewGroup {

   private int itemSpacing;
   private int lineSpacing;
   private boolean singleLine;


   public FlowLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public FlowLayout(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public FlowLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.singleLine = false;
      this.loadFromAttributes(var1, var2);
   }

   @TargetApi(21)
   public FlowLayout(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.singleLine = false;
      this.loadFromAttributes(var1, var2);
   }

   private static int getMeasuredDimension(int var0, int var1, int var2) {
      return var1 != Integer.MIN_VALUE?(var1 != 1073741824?var2:var0):Math.min(var2, var0);
   }

   private void loadFromAttributes(Context var1, AttributeSet var2) {
      TypedArray var3 = var1.getTheme().obtainStyledAttributes(var2, R.styleable.FlowLayout, 0, 0);
      this.lineSpacing = var3.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
      this.itemSpacing = var3.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
      var3.recycle();
   }

   protected int getItemSpacing() {
      return this.itemSpacing;
   }

   protected int getLineSpacing() {
      return this.lineSpacing;
   }

   protected boolean isSingleLine() {
      return this.singleLine;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      if(this.getChildCount() != 0) {
         var3 = ViewCompat.getLayoutDirection(this);
         boolean var6 = true;
         if(var3 != 1) {
            var6 = false;
         }

         if(var6) {
            var3 = this.getPaddingRight();
         } else {
            var3 = this.getPaddingLeft();
         }

         if(var6) {
            var5 = this.getPaddingLeft();
         } else {
            var5 = this.getPaddingRight();
         }

         int var8 = this.getPaddingTop();
         int var12 = var4 - var2 - var5;
         var4 = var3;
         var2 = var8;

         for(int var7 = 0; var7 < this.getChildCount(); ++var7) {
            View var14 = this.getChildAt(var7);
            if(var14.getVisibility() != 8) {
               LayoutParams var15 = var14.getLayoutParams();
               int var9;
               int var10;
               if(var15 instanceof MarginLayoutParams) {
                  MarginLayoutParams var16 = (MarginLayoutParams)var15;
                  var10 = MarginLayoutParamsCompat.getMarginStart(var16);
                  var9 = MarginLayoutParamsCompat.getMarginEnd(var16);
               } else {
                  var9 = 0;
                  var10 = 0;
               }

               int var13 = var14.getMeasuredWidth();
               int var11 = var4;
               var5 = var2;
               if(!this.singleLine) {
                  var11 = var4;
                  var5 = var2;
                  if(var4 + var10 + var13 > var12) {
                     var5 = var8 + this.lineSpacing;
                     var11 = var3;
                  }
               }

               var2 = var11 + var10;
               var4 = var14.getMeasuredWidth() + var2;
               var8 = var14.getMeasuredHeight() + var5;
               if(var6) {
                  var14.layout(var12 - var4, var5, var12 - var11 - var10, var8);
               } else {
                  var14.layout(var2, var5, var4, var8);
               }

               var4 = var11 + var10 + var9 + var14.getMeasuredWidth() + this.itemSpacing;
               var2 = var5;
            }
         }

      }
   }

   protected void onMeasure(int var1, int var2) {
      int var14 = MeasureSpec.getSize(var1);
      int var15 = MeasureSpec.getMode(var1);
      int var16 = MeasureSpec.getSize(var2);
      int var17 = MeasureSpec.getMode(var2);
      int var7;
      if(var15 != Integer.MIN_VALUE && var15 != 1073741824) {
         var7 = Integer.MAX_VALUE;
      } else {
         var7 = var14;
      }

      int var5 = this.getPaddingLeft();
      int var3 = this.getPaddingTop();
      int var18 = this.getPaddingRight();
      int var4 = var3;
      int var8 = 0;
      byte var6 = 0;
      int var11 = var3;

      int var23;
      for(var3 = var6; var8 < this.getChildCount(); var4 = var23) {
         View var20 = this.getChildAt(var8);
         if(var20.getVisibility() == 8) {
            var23 = var4;
         } else {
            this.measureChild(var20, var1, var2);
            LayoutParams var21 = var20.getLayoutParams();
            int var9;
            int var10;
            if(var21 instanceof MarginLayoutParams) {
               MarginLayoutParams var22 = (MarginLayoutParams)var21;
               var9 = var22.leftMargin + 0;
               var10 = var22.rightMargin + 0;
            } else {
               var9 = 0;
               var10 = 0;
            }

            int var19 = var20.getMeasuredWidth();
            var23 = var4;
            int var12 = var5;
            if(var5 + var9 + var19 > var7 - var18) {
               var23 = var4;
               var12 = var5;
               if(!this.isSingleLine()) {
                  var12 = this.getPaddingLeft();
                  var23 = this.lineSpacing + var11;
               }
            }

            var5 = var12 + var9 + var20.getMeasuredWidth();
            var11 = var20.getMeasuredHeight();
            var4 = var3;
            if(var5 > var3) {
               var4 = var5;
            }

            var3 = var20.getMeasuredWidth();
            var5 = this.itemSpacing;
            var11 += var23;
            var5 = var12 + var9 + var10 + var3 + var5;
            var3 = var4;
         }

         ++var8;
      }

      this.setMeasuredDimension(getMeasuredDimension(var14, var15, var3), getMeasuredDimension(var16, var17, var11));
   }

   protected void setItemSpacing(int var1) {
      this.itemSpacing = var1;
   }

   protected void setLineSpacing(int var1) {
      this.lineSpacing = var1;
   }

   public void setSingleLine(boolean var1) {
      this.singleLine = var1;
   }
}
