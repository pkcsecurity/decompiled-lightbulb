package android.support.transition;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

class RectEvaluator implements TypeEvaluator<Rect> {

   private Rect mRect;


   RectEvaluator() {}

   RectEvaluator(Rect var1) {
      this.mRect = var1;
   }

   public Rect evaluate(float var1, Rect var2, Rect var3) {
      int var4 = var2.left + (int)((float)(var3.left - var2.left) * var1);
      int var5 = var2.top + (int)((float)(var3.top - var2.top) * var1);
      int var6 = var2.right + (int)((float)(var3.right - var2.right) * var1);
      int var7 = var2.bottom + (int)((float)(var3.bottom - var2.bottom) * var1);
      if(this.mRect == null) {
         return new Rect(var4, var5, var6, var7);
      } else {
         this.mRect.set(var4, var5, var6, var7);
         return this.mRect;
      }
   }
}
