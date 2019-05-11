package com.facebook.drawee.backends.pipeline;

import android.graphics.drawable.Drawable;
import com.facebook.imagepipeline.image.CloseableImage;
import javax.annotation.Nullable;

public interface DrawableFactory {

   @Nullable
   Drawable createDrawable(CloseableImage var1);

   boolean supportsImageType(CloseableImage var1);
}
