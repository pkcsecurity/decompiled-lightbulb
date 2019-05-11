package com.facebook.react.flat;

import android.graphics.Bitmap;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.flat.BitmapUpdateListener;
import com.facebook.react.flat.RCTImageView;
import javax.annotation.Nullable;

final class PipelineRequestHelper implements DataSubscriber<CloseableReference<CloseableImage>> {

   private int mAttachCounter;
   @Nullable
   private BitmapUpdateListener mBitmapUpdateListener;
   @Nullable
   private DataSource<CloseableReference<CloseableImage>> mDataSource;
   @Nullable
   private CloseableReference<CloseableImage> mImageRef;
   private final ImageRequest mImageRequest;


   PipelineRequestHelper(ImageRequest var1) {
      this.mImageRequest = var1;
   }

   void attach(BitmapUpdateListener var1) {
      this.mBitmapUpdateListener = var1;
      ++this.mAttachCounter;
      if(this.mAttachCounter != 1) {
         Bitmap var4 = this.getBitmap();
         if(var4 != null) {
            var1.onSecondaryAttach(var4);
         }

      } else {
         var1.onImageLoadEvent(4);
         DataSource var5 = this.mDataSource;
         boolean var3 = false;
         boolean var2;
         if(var5 == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         Assertions.assertCondition(var2);
         var2 = var3;
         if(this.mImageRef == null) {
            var2 = true;
         }

         Assertions.assertCondition(var2);
         this.mDataSource = ImagePipelineFactory.getInstance().getImagePipeline().fetchDecodedImage(this.mImageRequest, RCTImageView.getCallerContext());
         this.mDataSource.subscribe(this, UiThreadImmediateExecutorService.getInstance());
      }
   }

   void detach() {
      --this.mAttachCounter;
      if(this.mAttachCounter == 0) {
         if(this.mDataSource != null) {
            this.mDataSource.close();
            this.mDataSource = null;
         }

         if(this.mImageRef != null) {
            this.mImageRef.close();
            this.mImageRef = null;
         }

         this.mBitmapUpdateListener = null;
      }
   }

   @Nullable
   Bitmap getBitmap() {
      if(this.mImageRef == null) {
         return null;
      } else {
         CloseableImage var1 = (CloseableImage)this.mImageRef.get();
         if(!(var1 instanceof CloseableBitmap)) {
            this.mImageRef.close();
            this.mImageRef = null;
            return null;
         } else {
            return ((CloseableBitmap)var1).getUnderlyingBitmap();
         }
      }
   }

   boolean isDetached() {
      return this.mAttachCounter == 0;
   }

   public void onCancellation(DataSource<CloseableReference<CloseableImage>> var1) {
      if(this.mDataSource == var1) {
         this.mDataSource = null;
      }

      var1.close();
   }

   public void onFailure(DataSource<CloseableReference<CloseableImage>> var1) {
      if(this.mDataSource == var1) {
         ((BitmapUpdateListener)Assertions.assumeNotNull(this.mBitmapUpdateListener)).onImageLoadEvent(1);
         ((BitmapUpdateListener)Assertions.assumeNotNull(this.mBitmapUpdateListener)).onImageLoadEvent(3);
         this.mDataSource = null;
      }

      var1.close();
   }

   public void onNewResult(DataSource<CloseableReference<CloseableImage>> param1) {
      // $FF: Couldn't be decompiled
   }

   public void onProgressUpdate(DataSource<CloseableReference<CloseableImage>> var1) {}
}
