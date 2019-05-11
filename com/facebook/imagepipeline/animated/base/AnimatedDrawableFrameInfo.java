package com.facebook.imagepipeline.animated.base;


public class AnimatedDrawableFrameInfo {

   public final AnimatedDrawableFrameInfo.BlendOperation blendOperation;
   public final AnimatedDrawableFrameInfo.DisposalMethod disposalMethod;
   public final int frameNumber;
   public final int height;
   public final int width;
   public final int xOffset;
   public final int yOffset;


   public AnimatedDrawableFrameInfo(int var1, int var2, int var3, int var4, int var5, AnimatedDrawableFrameInfo.BlendOperation var6, AnimatedDrawableFrameInfo.DisposalMethod var7) {
      this.frameNumber = var1;
      this.xOffset = var2;
      this.yOffset = var3;
      this.width = var4;
      this.height = var5;
      this.blendOperation = var6;
      this.disposalMethod = var7;
   }

   public static enum BlendOperation {

      // $FF: synthetic field
      private static final AnimatedDrawableFrameInfo.BlendOperation[] $VALUES = new AnimatedDrawableFrameInfo.BlendOperation[]{BLEND_WITH_PREVIOUS, NO_BLEND};
      BLEND_WITH_PREVIOUS("BLEND_WITH_PREVIOUS", 0),
      NO_BLEND("NO_BLEND", 1);


      private BlendOperation(String var1, int var2) {}
   }

   public static enum DisposalMethod {

      // $FF: synthetic field
      private static final AnimatedDrawableFrameInfo.DisposalMethod[] $VALUES = new AnimatedDrawableFrameInfo.DisposalMethod[]{DISPOSE_DO_NOT, DISPOSE_TO_BACKGROUND, DISPOSE_TO_PREVIOUS};
      DISPOSE_DO_NOT("DISPOSE_DO_NOT", 0),
      DISPOSE_TO_BACKGROUND("DISPOSE_TO_BACKGROUND", 1),
      DISPOSE_TO_PREVIOUS("DISPOSE_TO_PREVIOUS", 2);


      private DisposalMethod(String var1, int var2) {}
   }
}
