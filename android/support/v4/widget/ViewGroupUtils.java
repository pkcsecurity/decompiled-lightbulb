package android.support.v4.widget;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.RestrictTo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class ViewGroupUtils {

   private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal();
   private static final ThreadLocal<RectF> sRectF = new ThreadLocal();


   public static void getDescendantRect(ViewGroup var0, View var1, Rect var2) {
      var2.set(0, 0, var1.getWidth(), var1.getHeight());
      offsetDescendantRect(var0, var1, var2);
   }

   private static void offsetDescendantMatrix(ViewParent var0, View var1, Matrix var2) {
      ViewParent var3 = var1.getParent();
      if(var3 instanceof View && var3 != var0) {
         View var4 = (View)var3;
         offsetDescendantMatrix(var0, var4, var2);
         var2.preTranslate((float)(-var4.getScrollX()), (float)(-var4.getScrollY()));
      }

      var2.preTranslate((float)var1.getLeft(), (float)var1.getTop());
      if(!var1.getMatrix().isIdentity()) {
         var2.preConcat(var1.getMatrix());
      }

   }

   static void offsetDescendantRect(ViewGroup var0, View var1, Rect var2) {
      Matrix var3 = (Matrix)sMatrix.get();
      if(var3 == null) {
         var3 = new Matrix();
         sMatrix.set(var3);
      } else {
         var3.reset();
      }

      offsetDescendantMatrix(var0, var1, var3);
      RectF var5 = (RectF)sRectF.get();
      RectF var4 = var5;
      if(var5 == null) {
         var4 = new RectF();
         sRectF.set(var4);
      }

      var4.set(var2);
      var3.mapRect(var4);
      var2.set((int)(var4.left + 0.5F), (int)(var4.top + 0.5F), (int)(var4.right + 0.5F), (int)(var4.bottom + 0.5F));
   }
}
