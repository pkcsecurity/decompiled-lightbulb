package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import com.airbnb.lottie.animation.content.Content;

public interface DrawingContent extends Content {

   void a(Canvas var1, Matrix var2, int var3);

   void a(RectF var1, Matrix var2);
}
