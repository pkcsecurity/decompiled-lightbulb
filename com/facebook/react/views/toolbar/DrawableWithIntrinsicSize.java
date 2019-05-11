package com.facebook.react.views.toolbar;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.imagepipeline.image.ImageInfo;

public class DrawableWithIntrinsicSize extends ForwardingDrawable implements Callback {

   private final ImageInfo mImageInfo;


   public DrawableWithIntrinsicSize(Drawable var1, ImageInfo var2) {
      super(var1);
      this.mImageInfo = var2;
   }

   public int getIntrinsicHeight() {
      return this.mImageInfo.getHeight();
   }

   public int getIntrinsicWidth() {
      return this.mImageInfo.getWidth();
   }
}
