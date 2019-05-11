package com.facebook.drawee.debug;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.drawee.drawable.ScalingUtils;
import javax.annotation.Nullable;

public class DebugControllerOverlayDrawable extends Drawable {

   private static final float IMAGE_SIZE_THRESHOLD_NOT_OK = 0.5F;
   private static final float IMAGE_SIZE_THRESHOLD_OK = 0.1F;
   private static final int MAX_LINE_WIDTH_EM = 7;
   private static final int MAX_NUMBER_OF_LINES = 7;
   private static final int MAX_TEXT_SIZE_PX = 40;
   private static final int MIN_TEXT_SIZE_PX = 12;
   private static final String NO_CONTROLLER_ID = "none";
   private static final int OUTLINE_COLOR = -26624;
   private static final int OUTLINE_STROKE_WIDTH_PX = 2;
   @VisibleForTesting
   static final int OVERLAY_COLOR_IMAGE_ALMOST_OK = 1728026624;
   @VisibleForTesting
   static final int OVERLAY_COLOR_IMAGE_NOT_OK = 1727284022;
   @VisibleForTesting
   static final int OVERLAY_COLOR_IMAGE_OK = 1716301648;
   private static final int TEXT_COLOR = -1;
   private static final int TEXT_LINE_SPACING_PX = 8;
   private static final int TEXT_PADDING_PX = 10;
   private String mControllerId;
   private int mCurrentTextXPx;
   private int mCurrentTextYPx;
   private int mFrameCount;
   private int mHeightPx;
   private String mImageFormat;
   private int mImageSizeBytes;
   private int mLineIncrementPx;
   private int mLoopCount;
   private final Matrix mMatrix = new Matrix();
   private final Paint mPaint = new Paint(1);
   private final Rect mRect = new Rect();
   private final RectF mRectF = new RectF();
   private ScalingUtils.ScaleType mScaleType;
   private int mStartTextXPx;
   private int mStartTextYPx;
   private int mTextGravity = 80;
   private int mWidthPx;


   public DebugControllerOverlayDrawable() {
      this.reset();
   }

   private void addDebugText(Canvas var1, String var2, @Nullable Object ... var3) {
      if(var3 == null) {
         var1.drawText(var2, (float)this.mCurrentTextXPx, (float)this.mCurrentTextYPx, this.mPaint);
      } else {
         var1.drawText(String.format(var2, var3), (float)this.mCurrentTextXPx, (float)this.mCurrentTextYPx, this.mPaint);
      }

      this.mCurrentTextYPx += this.mLineIncrementPx;
   }

   private void prepareDebugTextParameters(Rect var1, int var2, int var3) {
      var2 = Math.min(40, Math.max(12, Math.min(var1.width() / var3, var1.height() / var2)));
      this.mPaint.setTextSize((float)var2);
      this.mLineIncrementPx = var2 + 8;
      if(this.mTextGravity == 80) {
         this.mLineIncrementPx *= -1;
      }

      this.mStartTextXPx = var1.left + 10;
      if(this.mTextGravity == 80) {
         var2 = var1.bottom - 10;
      } else {
         var2 = var1.top + 10 + 12;
      }

      this.mStartTextYPx = var2;
   }

   @VisibleForTesting
   int determineOverlayColor(int var1, int var2, @Nullable ScalingUtils.ScaleType var3) {
      int var10 = this.getBounds().width();
      int var9 = this.getBounds().height();
      if(var10 > 0 && var9 > 0 && var1 > 0) {
         if(var2 <= 0) {
            return 1727284022;
         } else {
            int var8 = var10;
            int var7 = var9;
            if(var3 != null) {
               Rect var11 = this.mRect;
               this.mRect.top = 0;
               var11.left = 0;
               this.mRect.right = var10;
               this.mRect.bottom = var9;
               this.mMatrix.reset();
               var3.getTransform(this.mMatrix, this.mRect, var1, var2, 0.0F, 0.0F);
               RectF var12 = this.mRectF;
               this.mRectF.top = 0.0F;
               var12.left = 0.0F;
               this.mRectF.right = (float)var1;
               this.mRectF.bottom = (float)var2;
               this.mMatrix.mapRect(this.mRectF);
               var8 = (int)this.mRectF.width();
               var7 = (int)this.mRectF.height();
               var8 = Math.min(var10, var8);
               var7 = Math.min(var9, var7);
            }

            float var4 = (float)var8;
            float var5 = (float)var7;
            var1 = Math.abs(var1 - var8);
            var2 = Math.abs(var2 - var7);
            float var6 = (float)var1;
            return var6 < var4 * 0.1F && (float)var2 < 0.1F * var5?1716301648:(var6 < var4 * 0.5F && (float)var2 < var5 * 0.5F?1728026624:1727284022);
         }
      } else {
         return 1727284022;
      }
   }

   public void draw(Canvas var1) {
      Rect var2 = this.getBounds();
      this.mPaint.setStyle(Style.STROKE);
      this.mPaint.setStrokeWidth(2.0F);
      this.mPaint.setColor(-26624);
      var1.drawRect((float)var2.left, (float)var2.top, (float)var2.right, (float)var2.bottom, this.mPaint);
      this.mPaint.setStyle(Style.FILL);
      this.mPaint.setColor(this.determineOverlayColor(this.mWidthPx, this.mHeightPx, this.mScaleType));
      var1.drawRect((float)var2.left, (float)var2.top, (float)var2.right, (float)var2.bottom, this.mPaint);
      this.mPaint.setStyle(Style.FILL);
      this.mPaint.setStrokeWidth(0.0F);
      this.mPaint.setColor(-1);
      this.mCurrentTextXPx = this.mStartTextXPx;
      this.mCurrentTextYPx = this.mStartTextYPx;
      this.addDebugText(var1, "ID: %s", new Object[]{this.mControllerId});
      this.addDebugText(var1, "D: %dx%d", new Object[]{Integer.valueOf(var2.width()), Integer.valueOf(var2.height())});
      this.addDebugText(var1, "I: %dx%d", new Object[]{Integer.valueOf(this.mWidthPx), Integer.valueOf(this.mHeightPx)});
      this.addDebugText(var1, "I: %d KiB", new Object[]{Integer.valueOf(this.mImageSizeBytes / 1024)});
      if(this.mImageFormat != null) {
         this.addDebugText(var1, "i format: %s", new Object[]{this.mImageFormat});
      }

      if(this.mFrameCount > 0) {
         this.addDebugText(var1, "anim: f %d, l %d", new Object[]{Integer.valueOf(this.mFrameCount), Integer.valueOf(this.mLoopCount)});
      }

      if(this.mScaleType != null) {
         this.addDebugText(var1, "scale: %s", new Object[]{this.mScaleType});
      }

   }

   public int getOpacity() {
      return -3;
   }

   protected void onBoundsChange(Rect var1) {
      super.onBoundsChange(var1);
      this.prepareDebugTextParameters(var1, 7, 7);
   }

   public void reset() {
      this.mWidthPx = -1;
      this.mHeightPx = -1;
      this.mImageSizeBytes = -1;
      this.mFrameCount = -1;
      this.mLoopCount = -1;
      this.mImageFormat = null;
      this.setControllerId((String)null);
      this.invalidateSelf();
   }

   public void setAlpha(int var1) {}

   public void setAnimationInfo(int var1, int var2) {
      this.mFrameCount = var1;
      this.mLoopCount = var2;
      this.invalidateSelf();
   }

   public void setColorFilter(ColorFilter var1) {}

   public void setControllerId(@Nullable String var1) {
      if(var1 == null) {
         var1 = "none";
      }

      this.mControllerId = var1;
      this.invalidateSelf();
   }

   public void setDimensions(int var1, int var2) {
      this.mWidthPx = var1;
      this.mHeightPx = var2;
      this.invalidateSelf();
   }

   public void setImageFormat(@Nullable String var1) {
      this.mImageFormat = var1;
   }

   public void setImageSize(int var1) {
      this.mImageSizeBytes = var1;
   }

   public void setScaleType(ScalingUtils.ScaleType var1) {
      this.mScaleType = var1;
   }

   public void setTextGravity(int var1) {
      this.mTextGravity = var1;
      this.invalidateSelf();
   }
}
