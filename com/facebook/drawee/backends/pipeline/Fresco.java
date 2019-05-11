package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import javax.annotation.Nullable;

public class Fresco {

   private static final Class<?> TAG = Fresco.class;
   private static PipelineDraweeControllerBuilderSupplier sDraweeControllerBuilderSupplier;
   private static volatile boolean sIsInitialized;


   public static PipelineDraweeControllerBuilderSupplier getDraweeControllerBuilderSupplier() {
      return sDraweeControllerBuilderSupplier;
   }

   public static ImagePipeline getImagePipeline() {
      return getImagePipelineFactory().getImagePipeline();
   }

   public static ImagePipelineFactory getImagePipelineFactory() {
      return ImagePipelineFactory.getInstance();
   }

   public static boolean hasBeenInitialized() {
      return sIsInitialized;
   }

   public static void initialize(Context var0) {
      initialize(var0, (ImagePipelineConfig)null, (DraweeConfig)null);
   }

   public static void initialize(Context var0, @Nullable ImagePipelineConfig var1) {
      initialize(var0, var1, (DraweeConfig)null);
   }

   public static void initialize(Context var0, @Nullable ImagePipelineConfig var1, @Nullable DraweeConfig var2) {
      if(sIsInitialized) {
         FLog.w(TAG, "Fresco has already been initialized! `Fresco.initialize(...)` should only be called 1 single time to avoid memory leaks!");
      } else {
         sIsInitialized = true;
      }

      var0 = var0.getApplicationContext();
      if(var1 == null) {
         ImagePipelineFactory.initialize(var0);
      } else {
         ImagePipelineFactory.initialize(var1);
      }

      initializeDrawee(var0, var2);
   }

   private static void initializeDrawee(Context var0, @Nullable DraweeConfig var1) {
      sDraweeControllerBuilderSupplier = new PipelineDraweeControllerBuilderSupplier(var0, var1);
      SimpleDraweeView.initialize(sDraweeControllerBuilderSupplier);
   }

   public static PipelineDraweeControllerBuilder newDraweeControllerBuilder() {
      return sDraweeControllerBuilderSupplier.get();
   }

   public static void shutDown() {
      sDraweeControllerBuilderSupplier = null;
      SimpleDraweeView.shutDown();
      ImagePipelineFactory.shutDown();
   }
}
