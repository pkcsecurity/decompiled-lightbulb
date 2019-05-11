package com.facebook.litho;

import android.graphics.drawable.Drawable;
import android.support.annotation.Px;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.litho.reference.Reference;
import com.facebook.yoga.YogaDirection;

@ThreadConfined("ANY")
public interface ComponentLayout {

   Reference<? extends Drawable> getBackground();

   @Px
   int getHeight();

   @Px
   int getPaddingBottom();

   @Px
   int getPaddingLeft();

   @Px
   int getPaddingRight();

   @Px
   int getPaddingTop();

   YogaDirection getResolvedLayoutDirection();

   @Px
   int getWidth();

   @Px
   int getX();

   @Px
   int getY();

   boolean isPaddingSet();
}
