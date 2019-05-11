package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerFactory;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.Set;
import javax.annotation.Nullable;

public class PipelineDraweeControllerBuilder extends AbstractDraweeControllerBuilder<PipelineDraweeControllerBuilder, ImageRequest, CloseableReference<CloseableImage>, ImageInfo> {

   private final ImagePipeline mImagePipeline;
   private final PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;


   public PipelineDraweeControllerBuilder(Context var1, PipelineDraweeControllerFactory var2, ImagePipeline var3, Set<ControllerListener> var4) {
      super(var1, var4);
      this.mImagePipeline = var3;
      this.mPipelineDraweeControllerFactory = var2;
   }

   public static ImageRequest.RequestLevel convertCacheLevelToRequestLevel(AbstractDraweeControllerBuilder.CacheLevel var0) {
      switch(null.$SwitchMap$com$facebook$drawee$controller$AbstractDraweeControllerBuilder$CacheLevel[var0.ordinal()]) {
      case 1:
         return ImageRequest.RequestLevel.FULL_FETCH;
      case 2:
         return ImageRequest.RequestLevel.DISK_CACHE;
      case 3:
         return ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE;
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Cache level");
         var1.append(var0);
         var1.append("is not supported. ");
         throw new RuntimeException(var1.toString());
      }
   }

   private CacheKey getCacheKey() {
      ImageRequest var1 = (ImageRequest)this.getImageRequest();
      CacheKeyFactory var2 = this.mImagePipeline.getCacheKeyFactory();
      return var2 != null && var1 != null?(var1.getPostprocessor() != null?var2.getPostprocessedBitmapCacheKey(var1, this.getCallerContext()):var2.getBitmapCacheKey(var1, this.getCallerContext())):null;
   }

   protected DataSource<CloseableReference<CloseableImage>> getDataSourceForRequest(ImageRequest var1, Object var2, AbstractDraweeControllerBuilder.CacheLevel var3) {
      return this.mImagePipeline.fetchDecodedImage(var1, var2, convertCacheLevelToRequestLevel(var3));
   }

   protected PipelineDraweeControllerBuilder getThis() {
      return this;
   }

   protected PipelineDraweeController obtainController() {
      DraweeController var1 = this.getOldController();
      if(var1 instanceof PipelineDraweeController) {
         PipelineDraweeController var2 = (PipelineDraweeController)var1;
         var2.initialize(this.obtainDataSourceSupplier(), generateUniqueControllerId(), this.getCacheKey(), this.getCallerContext());
         return var2;
      } else {
         return this.mPipelineDraweeControllerFactory.newController(this.obtainDataSourceSupplier(), generateUniqueControllerId(), this.getCacheKey(), this.getCallerContext());
      }
   }

   public PipelineDraweeControllerBuilder setUri(@Nullable Uri var1) {
      return var1 == null?(PipelineDraweeControllerBuilder)super.setImageRequest((Object)null):(PipelineDraweeControllerBuilder)super.setImageRequest(ImageRequestBuilder.newBuilderWithSource(var1).setRotationOptions(RotationOptions.autoRotateAtRenderTime()).build());
   }

   public PipelineDraweeControllerBuilder setUri(@Nullable String var1) {
      return var1 != null && !var1.isEmpty()?this.setUri(Uri.parse(var1)):(PipelineDraweeControllerBuilder)super.setImageRequest(ImageRequest.fromUri(var1));
   }
}
