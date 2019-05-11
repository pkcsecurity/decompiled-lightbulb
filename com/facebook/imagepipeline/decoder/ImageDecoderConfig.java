package com.facebook.imagepipeline.decoder;

import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageDecoderConfig {

   private final Map<ImageFormat, ImageDecoder> mCustomImageDecoders;
   private final List<ImageFormat.FormatChecker> mCustomImageFormats;


   private ImageDecoderConfig(ImageDecoderConfig.Builder var1) {
      this.mCustomImageDecoders = var1.mCustomImageDecoders;
      this.mCustomImageFormats = var1.mCustomImageFormats;
   }

   // $FF: synthetic method
   ImageDecoderConfig(ImageDecoderConfig.Builder var1, Object var2) {
      this(var1);
   }

   public static ImageDecoderConfig.Builder newBuilder() {
      return new ImageDecoderConfig.Builder();
   }

   public Map<ImageFormat, ImageDecoder> getCustomImageDecoders() {
      return this.mCustomImageDecoders;
   }

   public List<ImageFormat.FormatChecker> getCustomImageFormats() {
      return this.mCustomImageFormats;
   }

   public static class Builder {

      private Map<ImageFormat, ImageDecoder> mCustomImageDecoders;
      private List<ImageFormat.FormatChecker> mCustomImageFormats;


      public ImageDecoderConfig.Builder addDecodingCapability(ImageFormat var1, ImageFormat.FormatChecker var2, ImageDecoder var3) {
         if(this.mCustomImageFormats == null) {
            this.mCustomImageFormats = new ArrayList();
         }

         this.mCustomImageFormats.add(var2);
         this.overrideDecoder(var1, var3);
         return this;
      }

      public ImageDecoderConfig build() {
         return new ImageDecoderConfig(this, null);
      }

      public ImageDecoderConfig.Builder overrideDecoder(ImageFormat var1, ImageDecoder var2) {
         if(this.mCustomImageDecoders == null) {
            this.mCustomImageDecoders = new HashMap();
         }

         this.mCustomImageDecoders.put(var1, var2);
         return this;
      }
   }
}
