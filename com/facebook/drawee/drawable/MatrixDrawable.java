package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ForwardingDrawable;

public class MatrixDrawable extends ForwardingDrawable {

   private Matrix mDrawMatrix;
   private Matrix mMatrix;
   private int mUnderlyingHeight = 0;
   private int mUnderlyingWidth = 0;


   public MatrixDrawable(Drawable var1, Matrix var2) {
      super((Drawable)Preconditions.checkNotNull(var1));
      this.mMatrix = var2;
   }

   private void configureBounds() {
      Drawable var3 = this.getCurrent();
      Rect var4 = this.getBounds();
      int var1 = var3.getIntrinsicWidth();
      this.mUnderlyingWidth = var1;
      int var2 = var3.getIntrinsicHeight();
      this.mUnderlyingHeight = var2;
      if(var1 > 0 && var2 > 0) {
         var3.setBounds(0, 0, var1, var2);
         this.mDrawMatrix = this.mMatrix;
      } else {
         var3.setBounds(var4);
         this.mDrawMatrix = null;
      }
   }

   private void configureBoundsIfUnderlyingChanged() {
      if(this.mUnderlyingWidth != this.getCurrent().getIntrinsicWidth() || this.mUnderlyingHeight != this.getCurrent().getIntrinsicHeight()) {
         this.configureBounds();
      }

   }

   public void draw(Canvas var1) {
      this.configureBoundsIfUnderlyingChanged();
      if(this.mDrawMatrix != null) {
         int var2 = var1.save();
         var1.clipRect(this.getBounds());
         var1.concat(this.mDrawMatrix);
         super.draw(var1);
         var1.restoreToCount(var2);
      } else {
         super.draw(var1);
      }
   }

   public Matrix getMatrix() {
      return this.mMatrix;
   }

   public void getTransform(Matrix var1) {
      super.getTransform(var1);
      if(this.mDrawMatrix != null) {
         var1.preConcat(this.mDrawMatrix);
      }

   }

   protected void onBoundsChange(Rect var1) {
      super.onBoundsChange(var1);
      this.configureBounds();
   }

   public Drawable setCurrent(Drawable var1) {
      var1 = super.setCurrent(var1);
      this.configureBounds();
      return var1;
   }

   public void setMatrix(Matrix var1) {
      this.mMatrix = var1;
      this.configureBounds();
      this.invalidateSelf();
   }
}
