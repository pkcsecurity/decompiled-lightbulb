package com.facebook.imagepipeline.common;

import android.graphics.Bitmap.Config;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import javax.annotation.Nullable;

public class ImageDecodeOptionsBuilder {

   private Config mBitmapConfig;
   @Nullable
   private ImageDecoder mCustomImageDecoder;
   private boolean mDecodeAllFrames;
   private boolean mDecodePreviewFrame;
   private boolean mForceStaticImage;
   private int mMinDecodeIntervalMs = 100;
   private boolean mUseLastFrameForPreview;


   public ImageDecodeOptionsBuilder() {
      this.mBitmapConfig = Config.ARGB_8888;
   }

   public ImageDecodeOptions build() {
      return new ImageDecodeOptions(this);
   }

   public Config getBitmapConfig() {
      return this.mBitmapConfig;
   }

   @Nullable
   public ImageDecoder getCustomImageDecoder() {
      return this.mCustomImageDecoder;
   }

   public boolean getDecodeAllFrames() {
      return this.mDecodeAllFrames;
   }

   public boolean getDecodePreviewFrame() {
      return this.mDecodePreviewFrame;
   }

   public boolean getForceStaticImage() {
      return this.mForceStaticImage;
   }

   public int getMinDecodeIntervalMs() {
      return this.mMinDecodeIntervalMs;
   }

   public boolean getUseLastFrameForPreview() {
      return this.mUseLastFrameForPreview;
   }

   public ImageDecodeOptionsBuilder setBitmapConfig(Config var1) {
      this.mBitmapConfig = var1;
      return this;
   }

   public ImageDecodeOptionsBuilder setCustomImageDecoder(@Nullable ImageDecoder var1) {
      this.mCustomImageDecoder = var1;
      return this;
   }

   public ImageDecodeOptionsBuilder setDecodeAllFrames(boolean var1) {
      this.mDecodeAllFrames = var1;
      return this;
   }

   public ImageDecodeOptionsBuilder setDecodePreviewFrame(boolean var1) {
      this.mDecodePreviewFrame = var1;
      return this;
   }

   public ImageDecodeOptionsBuilder setForceStaticImage(boolean var1) {
      this.mForceStaticImage = var1;
      return this;
   }

   public ImageDecodeOptionsBuilder setFrom(ImageDecodeOptions var1) {
      this.mDecodePreviewFrame = var1.decodePreviewFrame;
      this.mUseLastFrameForPreview = var1.useLastFrameForPreview;
      this.mDecodeAllFrames = var1.decodeAllFrames;
      this.mForceStaticImage = var1.forceStaticImage;
      this.mBitmapConfig = var1.bitmapConfig;
      return this;
   }

   public ImageDecodeOptionsBuilder setMinDecodeIntervalMs(int var1) {
      this.mMinDecodeIntervalMs = var1;
      return this;
   }

   public ImageDecodeOptionsBuilder setUseLastFrameForPreview(boolean var1) {
      this.mUseLastFrameForPreview = var1;
      return this;
   }
}
