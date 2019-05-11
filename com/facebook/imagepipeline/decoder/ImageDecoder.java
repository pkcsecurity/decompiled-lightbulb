package com.facebook.imagepipeline.decoder;

import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.QualityInfo;

public interface ImageDecoder {

   CloseableImage decode(EncodedImage var1, int var2, QualityInfo var3, ImageDecodeOptions var4);
}
