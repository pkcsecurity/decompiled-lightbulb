package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.ThumbnailSizeChecker;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.JfifUtil;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalContentUriThumbnailFetchProducer extends LocalFetchProducer implements ThumbnailProducer<EncodedImage> {

   private static final Rect MICRO_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 96, 96);
   private static final Rect MINI_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 512, 384);
   private static final int NO_THUMBNAIL = 0;
   public static final String PRODUCER_NAME = "LocalContentUriThumbnailFetchProducer";
   private static final String[] PROJECTION = new String[]{"_id", "_data"};
   private static final Class<?> TAG = LocalContentUriThumbnailFetchProducer.class;
   private static final String[] THUMBNAIL_PROJECTION = new String[]{"_data"};
   private final ContentResolver mContentResolver;


   public LocalContentUriThumbnailFetchProducer(Executor var1, PooledByteBufferFactory var2, ContentResolver var3) {
      super(var1, var2);
      this.mContentResolver = var3;
   }

   @Nullable
   private EncodedImage getCameraImage(Uri param1, ResizeOptions param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static int getLength(String var0) {
      return var0 == null?-1:(int)(new File(var0)).length();
   }

   private static int getRotationAngle(String var0) {
      if(var0 != null) {
         try {
            int var1 = JfifUtil.getAutoRotateAngleFromOrientation((new ExifInterface(var0)).getAttributeInt("Orientation", 1));
            return var1;
         } catch (IOException var3) {
            FLog.e(TAG, var3, "Unable to retrieve thumbnail rotation for %s", new Object[]{var0});
         }
      }

      return 0;
   }

   private EncodedImage getThumbnail(ResizeOptions param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static int getThumbnailKind(ResizeOptions var0) {
      return ThumbnailSizeChecker.isImageBigEnough(MICRO_THUMBNAIL_DIMENSIONS.width(), MICRO_THUMBNAIL_DIMENSIONS.height(), var0)?3:(ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), var0)?1:0);
   }

   public boolean canProvideImageForSize(ResizeOptions var1) {
      return ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), var1);
   }

   protected EncodedImage getEncodedImage(ImageRequest var1) throws IOException {
      Uri var2 = var1.getSourceUri();
      if(UriUtil.isLocalCameraUri(var2)) {
         EncodedImage var3 = this.getCameraImage(var2, var1.getResizeOptions());
         if(var3 != null) {
            return var3;
         }
      }

      return null;
   }

   protected String getProducerName() {
      return "LocalContentUriThumbnailFetchProducer";
   }
}
