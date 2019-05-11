package com.facebook.react.views.imagehelper;

import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.react.views.imagehelper.ImageSource;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class MultiSourceHelper {

   public static MultiSourceHelper.MultiSourceResult getBestSourceForSize(int var0, int var1, List<ImageSource> var2) {
      return getBestSourceForSize(var0, var1, var2, 1.0D);
   }

   public static MultiSourceHelper.MultiSourceResult getBestSourceForSize(int var0, int var1, List<ImageSource> var2, double var3) {
      if(var2.isEmpty()) {
         return new MultiSourceHelper.MultiSourceResult((ImageSource)null, (ImageSource)null, null);
      } else if(var2.size() == 1) {
         return new MultiSourceHelper.MultiSourceResult((ImageSource)var2.get(0), (ImageSource)null, null);
      } else if(var0 > 0 && var1 > 0) {
         ImagePipeline var18 = ImagePipelineFactory.getInstance().getImagePipeline();
         double var13 = (double)(var0 * var1);
         Iterator var19 = var2.iterator();
         double var7 = Double.MAX_VALUE;
         double var5 = Double.MAX_VALUE;
         ImageSource var20 = null;
         ImageSource var15 = var20;

         ImageSource var16;
         while(var19.hasNext()) {
            var16 = (ImageSource)var19.next();
            double var9 = Math.abs(1.0D - var16.getSize() / (var13 * var3));
            ImageSource var17 = var15;
            double var11 = var7;
            if(var9 < var7) {
               var17 = var16;
               var11 = var9;
            }

            var15 = var17;
            var7 = var11;
            if(var9 < var5) {
               if(!var18.isInBitmapMemoryCache(var16.getUri())) {
                  var15 = var17;
                  var7 = var11;
                  if(!var18.isInDiskCacheSync(var16.getUri())) {
                     continue;
                  }
               }

               var20 = var16;
               var5 = var9;
               var15 = var17;
               var7 = var11;
            }
         }

         var16 = var20;
         if(var20 != null) {
            var16 = var20;
            if(var15 != null) {
               var16 = var20;
               if(var20.getSource().equals(var15.getSource())) {
                  var16 = null;
               }
            }
         }

         return new MultiSourceHelper.MultiSourceResult(var15, var16, null);
      } else {
         return new MultiSourceHelper.MultiSourceResult((ImageSource)null, (ImageSource)null, null);
      }
   }

   public static class MultiSourceResult {

      @Nullable
      private final ImageSource bestResult;
      @Nullable
      private final ImageSource bestResultInCache;


      private MultiSourceResult(@Nullable ImageSource var1, @Nullable ImageSource var2) {
         this.bestResult = var1;
         this.bestResultInCache = var2;
      }

      // $FF: synthetic method
      MultiSourceResult(ImageSource var1, ImageSource var2, Object var3) {
         this(var1, var2);
      }

      @Nullable
      public ImageSource getBestResult() {
         return this.bestResult;
      }

      @Nullable
      public ImageSource getBestResultInCache() {
         return this.bestResultInCache;
      }
   }
}
