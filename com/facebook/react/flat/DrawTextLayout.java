package com.facebook.react.flat;

import android.graphics.Canvas;
import android.text.Layout;
import com.facebook.fbui.textlayoutbuilder.util.LayoutMeasureUtil;
import com.facebook.react.flat.AbstractDrawCommand;

final class DrawTextLayout extends AbstractDrawCommand {

   private Layout mLayout;
   private float mLayoutHeight;
   private float mLayoutWidth;


   DrawTextLayout(Layout var1) {
      this.setLayout(var1);
   }

   public Layout getLayout() {
      return this.mLayout;
   }

   public float getLayoutHeight() {
      return this.mLayoutHeight;
   }

   public float getLayoutWidth() {
      return this.mLayoutWidth;
   }

   protected void onDraw(Canvas var1) {
      float var2 = this.getLeft();
      float var3 = this.getTop();
      var1.translate(var2, var3);
      this.mLayout.draw(var1);
      var1.translate(-var2, -var3);
   }

   public void setLayout(Layout var1) {
      this.mLayout = var1;
      this.mLayoutWidth = (float)var1.getWidth();
      this.mLayoutHeight = (float)LayoutMeasureUtil.getHeight(var1);
   }
}
