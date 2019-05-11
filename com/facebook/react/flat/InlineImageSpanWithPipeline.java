package com.facebook.react.flat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.FontMetricsInt;
import android.text.style.ReplacementSpan;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.flat.AttachDetachListener;
import com.facebook.react.flat.BitmapUpdateListener;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.PipelineRequestHelper;
import javax.annotation.Nullable;

final class InlineImageSpanWithPipeline extends ReplacementSpan implements AttachDetachListener, BitmapUpdateListener {

   private static final RectF TMP_RECT = new RectF();
   @Nullable
   private FlatViewGroup.InvalidateCallback mCallback;
   private boolean mFrozen;
   private float mHeight;
   @Nullable
   private PipelineRequestHelper mRequestHelper;
   private float mWidth;


   InlineImageSpanWithPipeline() {
      this((PipelineRequestHelper)null, Float.NaN, Float.NaN);
   }

   private InlineImageSpanWithPipeline(@Nullable PipelineRequestHelper var1, float var2, float var3) {
      this.mRequestHelper = var1;
      this.mWidth = var2;
      this.mHeight = var3;
   }

   public void draw(Canvas var1, CharSequence var2, int var3, int var4, float var5, int var6, int var7, int var8, Paint var9) {
      if(this.mRequestHelper != null) {
         Bitmap var11 = this.mRequestHelper.getBitmap();
         if(var11 != null) {
            float var10 = (float)var8 - (float)var9.getFontMetricsInt().descent;
            TMP_RECT.set(var5, var10 - this.mHeight, this.mWidth + var5, var10);
            var1.drawBitmap(var11, (Rect)null, TMP_RECT, var9);
         }
      }
   }

   void freeze() {
      this.mFrozen = true;
   }

   float getHeight() {
      return this.mHeight;
   }

   public int getSize(Paint var1, CharSequence var2, int var3, int var4, FontMetricsInt var5) {
      if(var5 != null) {
         var5.ascent = -Math.round(this.mHeight);
         var5.descent = 0;
         var5.top = var5.ascent;
         var5.bottom = 0;
      }

      return Math.round(this.mWidth);
   }

   float getWidth() {
      return this.mWidth;
   }

   boolean hasImageRequest() {
      return this.mRequestHelper != null;
   }

   boolean isFrozen() {
      return this.mFrozen;
   }

   InlineImageSpanWithPipeline mutableCopy() {
      return new InlineImageSpanWithPipeline(this.mRequestHelper, this.mWidth, this.mHeight);
   }

   public void onAttached(FlatViewGroup.InvalidateCallback var1) {
      this.mCallback = var1;
      if(this.mRequestHelper != null) {
         this.mRequestHelper.attach(this);
      }

   }

   public void onBitmapReady(Bitmap var1) {
      ((FlatViewGroup.InvalidateCallback)Assertions.assumeNotNull(this.mCallback)).invalidate();
   }

   public void onDetached() {
      if(this.mRequestHelper != null) {
         this.mRequestHelper.detach();
         if(this.mRequestHelper.isDetached()) {
            this.mCallback = null;
         }
      }

   }

   public void onImageLoadEvent(int var1) {}

   public void onSecondaryAttach(Bitmap var1) {
      ((FlatViewGroup.InvalidateCallback)Assertions.assumeNotNull(this.mCallback)).invalidate();
   }

   void setHeight(float var1) {
      this.mHeight = var1;
   }

   void setImageRequest(@Nullable ImageRequest var1) {
      if(var1 == null) {
         this.mRequestHelper = null;
      } else {
         this.mRequestHelper = new PipelineRequestHelper(var1);
      }
   }

   void setWidth(float var1) {
      this.mWidth = var1;
   }
}
