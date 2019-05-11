package com.facebook.imagepipeline.datasource;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import javax.annotation.Nullable;

public abstract class BaseBitmapDataSubscriber extends BaseDataSubscriber<CloseableReference<CloseableImage>> {

   public abstract void onNewResultImpl(@Nullable Bitmap var1);

   public void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> var1) {
      if(var1.isFinished()) {
         CloseableReference var3 = (CloseableReference)var1.getResult();
         Object var2 = null;
         Bitmap var6 = (Bitmap)var2;
         if(var3 != null) {
            var6 = (Bitmap)var2;
            if(var3.get() instanceof CloseableBitmap) {
               var6 = ((CloseableBitmap)var3.get()).getUnderlyingBitmap();
            }
         }

         try {
            this.onNewResultImpl(var6);
         } finally {
            CloseableReference.closeSafely(var3);
         }

      }
   }
}
