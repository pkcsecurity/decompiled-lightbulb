package com.facebook.imagepipeline.animated.factory;

import android.graphics.Bitmap.Config;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;

public interface AnimatedImageFactory {

   CloseableImage decodeGif(EncodedImage var1, ImageDecodeOptions var2, Config var3);

   CloseableImage decodeWebP(EncodedImage var1, ImageDecodeOptions var2, Config var3);
}
