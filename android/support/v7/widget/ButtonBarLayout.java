package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ButtonBarLayout extends LinearLayout {

   private static final int PEEK_BUTTON_DP = 16;
   private boolean mAllowStacking;
   private int mLastWidthSize = -1;
   private int mMinimumHeight = 0;


   public ButtonBarLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
      TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.ButtonBarLayout);
      this.mAllowStacking = var3.getBoolean(R.styleable.ButtonBarLayout_allowStacking, true);
      var3.recycle();
   }

   private int getNextVisibleChildIndex(int var1) {
      for(int var2 = this.getChildCount(); var1 < var2; ++var1) {
         if(this.getChildAt(var1).getVisibility() == 0) {
            return var1;
         }
      }

      return -1;
   }

   private boolean isStacked() {
      return this.getOrientation() == 1;
   }

   private void setStacked(boolean var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   public int getMinimumHeight() {
      return Math.max(this.mMinimumHeight, super.getMinimumHeight());
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      boolean var7 = this.mAllowStacking;
      byte var6 = 0;
      if(var7) {
         if(var3 > this.mLastWidthSize && this.isStacked()) {
            this.setStacked(false);
         }

         this.mLastWidthSize = var3;
      }

      int var4;
      boolean var10;
      if(!this.isStacked() && MeasureSpec.getMode(var1) == 1073741824) {
         var4 = MeasureSpec.makeMeasureSpec(var3, Integer.MIN_VALUE);
         var10 = true;
      } else {
         var4 = var1;
         var10 = false;
      }

      super.onMeasure(var4, var2);
      boolean var5 = var10;
      if(this.mAllowStacking) {
         var5 = var10;
         if(!this.isStacked()) {
            boolean var11;
            if((this.getMeasuredWidthAndState() & -16777216) == 16777216) {
               var11 = true;
            } else {
               var11 = false;
            }

            var5 = var10;
            if(var11) {
               this.setStacked(true);
               var5 = true;
            }
         }
      }

      if(var5) {
         super.onMeasure(var1, var2);
      }

      var3 = this.getNextVisibleChildIndex(0);
      var1 = var6;
      if(var3 >= 0) {
         View var8 = this.getChildAt(var3);
         LayoutParams var9 = (LayoutParams)var8.getLayoutParams();
         var2 = this.getPaddingTop() + var8.getMeasuredHeight() + var9.topMargin + var9.bottomMargin + 0;
         if(this.isStacked()) {
            var3 = this.getNextVisibleChildIndex(var3 + 1);
            var1 = var2;
            if(var3 >= 0) {
               var1 = var2 + this.getChildAt(var3).getPaddingTop() + (int)(this.getResources().getDisplayMetrics().density * 16.0F);
            }
         } else {
            var1 = var2 + this.getPaddingBottom();
         }
      }

      if(ViewCompat.getMinimumHeight(this) != var1) {
         this.setMinimumHeight(var1);
      }

   }

   public void setAllowStacking(boolean var1) {
      if(this.mAllowStacking != var1) {
         this.mAllowStacking = var1;
         if(!this.mAllowStacking && this.getOrientation() == 1) {
            this.setStacked(false);
         }

         this.requestLayout();
      }

   }
}
