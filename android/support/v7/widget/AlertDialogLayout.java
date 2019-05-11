package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class AlertDialogLayout extends LinearLayoutCompat {

   public AlertDialogLayout(@Nullable Context var1) {
      super(var1);
   }

   public AlertDialogLayout(@Nullable Context var1, @Nullable AttributeSet var2) {
      super(var1, var2);
   }

   private void forceUniformWidth(int var1, int var2) {
      int var4 = MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824);

      for(int var3 = 0; var3 < var1; ++var3) {
         View var6 = this.getChildAt(var3);
         if(var6.getVisibility() != 8) {
            LinearLayoutCompat.LayoutParams var7 = (LinearLayoutCompat.LayoutParams)var6.getLayoutParams();
            if(var7.width == -1) {
               int var5 = var7.height;
               var7.height = var6.getMeasuredHeight();
               this.measureChildWithMargins(var6, var4, 0, var2, 0);
               var7.height = var5;
            }
         }
      }

   }

   private static int resolveMinimumHeight(View var0) {
      int var1 = ViewCompat.getMinimumHeight(var0);
      if(var1 > 0) {
         return var1;
      } else {
         if(var0 instanceof ViewGroup) {
            ViewGroup var2 = (ViewGroup)var0;
            if(var2.getChildCount() == 1) {
               return resolveMinimumHeight(var2.getChildAt(0));
            }
         }

         return 0;
      }
   }

   private void setChildFrame(View var1, int var2, int var3, int var4, int var5) {
      var1.layout(var2, var3, var4 + var2, var5 + var3);
   }

   private boolean tryOnMeasure(int var1, int var2) {
      int var12 = this.getChildCount();
      View var18 = null;
      View var16 = var18;
      int var3 = 0;

      int var4;
      View var15;
      View var17;
      for(var17 = var18; var3 < var12; ++var3) {
         var15 = this.getChildAt(var3);
         if(var15.getVisibility() != 8) {
            var4 = var15.getId();
            if(var4 == R.id.topPanel) {
               var18 = var15;
            } else if(var4 == R.id.buttonPanel) {
               var17 = var15;
            } else {
               if(var4 != R.id.contentPanel && var4 != R.id.customPanel) {
                  return false;
               }

               if(var16 != null) {
                  return false;
               }

               var16 = var15;
            }
         }
      }

      int var14 = MeasureSpec.getMode(var2);
      int var7 = MeasureSpec.getSize(var2);
      int var13 = MeasureSpec.getMode(var1);
      int var5 = this.getPaddingTop() + this.getPaddingBottom();
      if(var18 != null) {
         var18.measure(var1, 0);
         var5 += var18.getMeasuredHeight();
         var4 = View.combineMeasuredStates(0, var18.getMeasuredState());
      } else {
         var4 = 0;
      }

      int var8;
      if(var17 != null) {
         var17.measure(var1, 0);
         var3 = resolveMinimumHeight(var17);
         var8 = var17.getMeasuredHeight() - var3;
         var5 += var3;
         var4 = View.combineMeasuredStates(var4, var17.getMeasuredState());
      } else {
         var3 = 0;
         var8 = 0;
      }

      int var6;
      int var9;
      if(var16 != null) {
         if(var14 == 0) {
            var6 = 0;
         } else {
            var6 = MeasureSpec.makeMeasureSpec(Math.max(0, var7 - var5), var14);
         }

         var16.measure(var1, var6);
         var9 = var16.getMeasuredHeight();
         var5 += var9;
         var4 = View.combineMeasuredStates(var4, var16.getMeasuredState());
      } else {
         var9 = 0;
      }

      int var10 = var7 - var5;
      var7 = var4;
      int var11 = var10;
      var6 = var5;
      if(var17 != null) {
         var8 = Math.min(var10, var8);
         var7 = var10;
         var6 = var3;
         if(var8 > 0) {
            var7 = var10 - var8;
            var6 = var3 + var8;
         }

         var17.measure(var1, MeasureSpec.makeMeasureSpec(var6, 1073741824));
         var6 = var5 - var3 + var17.getMeasuredHeight();
         var3 = View.combineMeasuredStates(var4, var17.getMeasuredState());
         var11 = var7;
         var7 = var3;
      }

      var4 = var7;
      var3 = var6;
      if(var16 != null) {
         var4 = var7;
         var3 = var6;
         if(var11 > 0) {
            var16.measure(var1, MeasureSpec.makeMeasureSpec(var9 + var11, var14));
            var3 = var6 - var9 + var16.getMeasuredHeight();
            var4 = View.combineMeasuredStates(var7, var16.getMeasuredState());
         }
      }

      var5 = 0;

      for(var6 = 0; var5 < var12; var6 = var7) {
         var15 = this.getChildAt(var5);
         var7 = var6;
         if(var15.getVisibility() != 8) {
            var7 = Math.max(var6, var15.getMeasuredWidth());
         }

         ++var5;
      }

      this.setMeasuredDimension(View.resolveSizeAndState(var6 + this.getPaddingLeft() + this.getPaddingRight(), var1, var4), View.resolveSizeAndState(var3, var2, 0));
      if(var13 != 1073741824) {
         this.forceUniformWidth(var12, var2);
      }

      return true;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var7 = this.getPaddingLeft();
      int var8 = var4 - var2;
      int var9 = this.getPaddingRight();
      int var10 = this.getPaddingRight();
      var2 = this.getMeasuredHeight();
      int var11 = this.getChildCount();
      int var12 = this.getGravity();
      var4 = var12 & 112;
      if(var4 != 16) {
         if(var4 != 80) {
            var2 = this.getPaddingTop();
         } else {
            var2 = this.getPaddingTop() + var5 - var3 - var2;
         }
      } else {
         var4 = this.getPaddingTop();
         var2 = (var5 - var3 - var2) / 2 + var4;
      }

      Drawable var15 = this.getDividerDrawable();
      if(var15 == null) {
         var4 = 0;
      } else {
         var4 = var15.getIntrinsicHeight();
      }

      for(var5 = 0; var5 < var11; var2 = var3) {
         View var17 = this.getChildAt(var5);
         var3 = var2;
         if(var17 != null) {
            var3 = var2;
            if(var17.getVisibility() != 8) {
               int var13 = var17.getMeasuredWidth();
               int var14 = var17.getMeasuredHeight();
               LinearLayoutCompat.LayoutParams var16 = (LinearLayoutCompat.LayoutParams)var17.getLayoutParams();
               int var6 = var16.gravity;
               var3 = var6;
               if(var6 < 0) {
                  var3 = var12 & 8388615;
               }

               var3 = GravityCompat.getAbsoluteGravity(var3, ViewCompat.getLayoutDirection(this)) & 7;
               if(var3 != 1) {
                  if(var3 != 5) {
                     var3 = var16.leftMargin + var7;
                  } else {
                     var3 = var8 - var9 - var13 - var16.rightMargin;
                  }
               } else {
                  var3 = (var8 - var7 - var10 - var13) / 2 + var7 + var16.leftMargin - var16.rightMargin;
               }

               var6 = var2;
               if(this.hasDividerBeforeChildAt(var5)) {
                  var6 = var2 + var4;
               }

               var2 = var6 + var16.topMargin;
               this.setChildFrame(var17, var3, var2, var13, var14);
               var3 = var2 + var14 + var16.bottomMargin;
            }
         }

         ++var5;
      }

   }

   protected void onMeasure(int var1, int var2) {
      if(!this.tryOnMeasure(var1, var2)) {
         super.onMeasure(var1, var2);
      }

   }
}
