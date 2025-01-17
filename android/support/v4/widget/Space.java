package android.support.v4.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

@Deprecated
public class Space extends View {

   @Deprecated
   public Space(@NonNull Context var1) {
      this(var1, (AttributeSet)null);
   }

   @Deprecated
   public Space(@NonNull Context var1, @Nullable AttributeSet var2) {
      this(var1, var2, 0);
   }

   @Deprecated
   public Space(@NonNull Context var1, @Nullable AttributeSet var2, int var3) {
      super(var1, var2, var3);
      if(this.getVisibility() == 0) {
         this.setVisibility(4);
      }

   }

   private static int getDefaultSize2(int var0, int var1) {
      int var2 = MeasureSpec.getMode(var1);
      int var3 = MeasureSpec.getSize(var1);
      if(var2 != Integer.MIN_VALUE) {
         var1 = var0;
         if(var2 != 0) {
            if(var2 != 1073741824) {
               return var0;
            }

            return var3;
         }
      } else {
         var1 = Math.min(var0, var3);
      }

      return var1;
   }

   @Deprecated
   @SuppressLint({"MissingSuperCall"})
   public void draw(Canvas var1) {}

   @Deprecated
   protected void onMeasure(int var1, int var2) {
      this.setMeasuredDimension(getDefaultSize2(this.getSuggestedMinimumWidth(), var1), getDefaultSize2(this.getSuggestedMinimumHeight(), var2));
   }
}
