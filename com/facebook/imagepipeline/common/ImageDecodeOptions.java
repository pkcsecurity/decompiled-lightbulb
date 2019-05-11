package com.facebook.imagepipeline.common;

import android.graphics.Bitmap.Config;
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import java.util.Locale;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ImageDecodeOptions {

   private static final ImageDecodeOptions DEFAULTS = newBuilder().build();
   public final Config bitmapConfig;
   @Nullable
   public final ImageDecoder customImageDecoder;
   public final boolean decodeAllFrames;
   public final boolean decodePreviewFrame;
   public final boolean forceStaticImage;
   public final int minDecodeIntervalMs;
   public final boolean useLastFrameForPreview;


   public ImageDecodeOptions(ImageDecodeOptionsBuilder var1) {
      this.minDecodeIntervalMs = var1.getMinDecodeIntervalMs();
      this.decodePreviewFrame = var1.getDecodePreviewFrame();
      this.useLastFrameForPreview = var1.getUseLastFrameForPreview();
      this.decodeAllFrames = var1.getDecodeAllFrames();
      this.forceStaticImage = var1.getForceStaticImage();
      this.bitmapConfig = var1.getBitmapConfig();
      this.customImageDecoder = var1.getCustomImageDecoder();
   }

   public static ImageDecodeOptions defaults() {
      return DEFAULTS;
   }

   public static ImageDecodeOptionsBuilder newBuilder() {
      return new ImageDecodeOptionsBuilder();
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            ImageDecodeOptions var2 = (ImageDecodeOptions)var1;
            return this.decodePreviewFrame != var2.decodePreviewFrame?false:(this.useLastFrameForPreview != var2.useLastFrameForPreview?false:(this.decodeAllFrames != var2.decodeAllFrames?false:(this.forceStaticImage != var2.forceStaticImage?false:(this.bitmapConfig != var2.bitmapConfig?false:this.customImageDecoder == var2.customImageDecoder))));
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   public String toString() {
      return String.format((Locale)null, "%d-%b-%b-%b-%b-%s-%s", new Object[]{Integer.valueOf(this.minDecodeIntervalMs), Boolean.valueOf(this.decodePreviewFrame), Boolean.valueOf(this.useLastFrameForPreview), Boolean.valueOf(this.decodeAllFrames), Boolean.valueOf(this.forceStaticImage), this.bitmapConfig.name(), this.customImageDecoder});
   }
}
