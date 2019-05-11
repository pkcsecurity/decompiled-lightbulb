package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.facebook.react.flat.AbstractDrawCommand;

final class DrawBackgroundColor extends AbstractDrawCommand {

   private static final Paint PAINT = new Paint();
   private final int mBackgroundColor;


   DrawBackgroundColor(int var1) {
      this.mBackgroundColor = var1;
   }

   public void onDraw(Canvas var1) {
      PAINT.setColor(this.mBackgroundColor);
      var1.drawRect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom(), PAINT);
   }
}
