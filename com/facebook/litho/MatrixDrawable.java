package com.facebook.litho;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import com.facebook.litho.DrawableMatrix;
import com.facebook.litho.Touchable;
import javax.annotation.Nullable;

public class MatrixDrawable<T extends Drawable> extends Drawable implements Callback, Touchable {

   public static final int UNSET = -1;
   private T mDrawable;
   private int mHeight;
   private DrawableMatrix mMatrix;
   private boolean mShouldClipRect;
   private int mWidth;


   private void setDrawableVisibilitySafe(boolean var1, boolean var2) {
      if(this.mDrawable != null && this.mDrawable.isVisible() != var1) {
         try {
            this.mDrawable.setVisible(var1, var2);
         } catch (NullPointerException var4) {
            return;
         }
      }

   }

   private void setInnerDrawableBounds(int var1, int var2) {
      if(this.mDrawable != null) {
         this.mDrawable.setBounds(0, 0, var1, var2);
      }
   }

   public void bind(int var1, int var2) {
      this.mWidth = var1;
      this.mHeight = var2;
      this.setInnerDrawableBounds(this.mWidth, this.mHeight);
   }

   public void draw(Canvas var1) {
      if(this.mDrawable != null) {
         Rect var3 = this.getBounds();
         int var2 = var1.save();
         var1.translate((float)var3.left, (float)var3.top);
         if(this.mShouldClipRect) {
            var1.clipRect(0, 0, var3.width(), var3.height());
         }

         if(this.mMatrix != null) {
            var1.concat(this.mMatrix);
         }

         this.mDrawable.draw(var1);
         var1.restoreToCount(var2);
      }
   }

   public int getChangingConfigurations() {
      return this.mDrawable == null?-1:this.mDrawable.getChangingConfigurations();
   }

   @Nullable
   public Drawable getCurrent() {
      return this.mDrawable == null?null:this.mDrawable.getCurrent();
   }

   public int getIntrinsicHeight() {
      return this.mDrawable == null?-1:this.mDrawable.getIntrinsicHeight();
   }

   public int getIntrinsicWidth() {
      return this.mDrawable == null?-1:this.mDrawable.getIntrinsicWidth();
   }

   public int getMinimumHeight() {
      return this.mDrawable == null?-1:this.mDrawable.getMinimumHeight();
   }

   public int getMinimumWidth() {
      return this.mDrawable == null?-1:this.mDrawable.getMinimumWidth();
   }

   public T getMountedDrawable() {
      return this.mDrawable;
   }

   public int getOpacity() {
      return this.mDrawable == null?-1:this.mDrawable.getOpacity();
   }

   public boolean getPadding(Rect var1) {
      return this.mDrawable != null && this.mDrawable.getPadding(var1);
   }

   @Nullable
   public int[] getState() {
      return this.mDrawable == null?null:this.mDrawable.getState();
   }

   @Nullable
   public Region getTransparentRegion() {
      return this.mDrawable == null?null:this.mDrawable.getTransparentRegion();
   }

   public void invalidateDrawable(Drawable var1) {
      this.invalidateSelf();
   }

   public boolean isStateful() {
      return this.mDrawable != null && this.mDrawable.isStateful();
   }

   public void mount(T var1) {
      this.mount(var1, (DrawableMatrix)null);
   }

   public void mount(T var1, DrawableMatrix var2) {
      if(this.mDrawable != var1) {
         Drawable var4 = this.mDrawable;
         boolean var3 = false;
         if(var4 != null) {
            this.setDrawableVisibilitySafe(false, false);
            this.mDrawable.setCallback((Callback)null);
         }

         this.mDrawable = var1;
         if(this.mDrawable != null) {
            this.setDrawableVisibilitySafe(this.isVisible(), false);
            this.mDrawable.setCallback(this);
         }

         this.mMatrix = var2;
         if(this.mMatrix != null && this.mMatrix.shouldClipRect() || VERSION.SDK_INT < 11 && this.mDrawable instanceof ColorDrawable || this.mDrawable instanceof InsetDrawable) {
            var3 = true;
         }

         this.mShouldClipRect = var3;
         this.invalidateSelf();
      }
   }

   protected boolean onLevelChange(int var1) {
      return this.mDrawable != null && this.mDrawable.setLevel(var1);
   }

   @TargetApi(21)
   public boolean onTouchEvent(MotionEvent var1, View var2) {
      Rect var7 = this.getBounds();
      int var3 = (int)var1.getX();
      int var4 = var7.left;
      int var5 = (int)var1.getY();
      int var6 = var7.top;
      this.mDrawable.setHotspot((float)(var3 - var4), (float)(var5 - var6));
      return false;
   }

   public void scheduleDrawable(Drawable var1, Runnable var2, long var3) {
      this.scheduleSelf(var2, var3);
   }

   public void setAlpha(int var1) {
      if(this.mDrawable != null) {
         this.mDrawable.setAlpha(var1);
      }
   }

   public void setBounds(int var1, int var2, int var3, int var4) {
      super.setBounds(var1, var2, var3, var4);
   }

   public void setBounds(Rect var1) {
      super.setBounds(var1);
   }

   public void setChangingConfigurations(int var1) {
      if(this.mDrawable != null) {
         this.mDrawable.setChangingConfigurations(var1);
      }
   }

   public void setColorFilter(ColorFilter var1) {
      if(this.mDrawable != null) {
         this.mDrawable.setColorFilter(var1);
      }
   }

   public void setDither(boolean var1) {
      if(this.mDrawable != null) {
         this.mDrawable.setDither(var1);
      }
   }

   public void setFilterBitmap(boolean var1) {
      if(this.mDrawable != null) {
         this.mDrawable.setFilterBitmap(var1);
      }
   }

   public boolean setState(int[] var1) {
      return this.mDrawable != null && this.mDrawable.setState(var1);
   }

   public boolean setVisible(boolean var1, boolean var2) {
      boolean var3 = super.setVisible(var1, var2);
      this.setDrawableVisibilitySafe(var1, var2);
      return var3;
   }

   public boolean shouldHandleTouchEvent(MotionEvent var1) {
      return VERSION.SDK_INT >= 21 && this.mDrawable != null && this.mDrawable instanceof RippleDrawable && var1.getActionMasked() == 0 && this.getBounds().contains((int)var1.getX(), (int)var1.getY());
   }

   public void unmount() {
      if(this.mDrawable != null) {
         this.setDrawableVisibilitySafe(false, false);
         this.mDrawable.setCallback((Callback)null);
      }

      this.mDrawable = null;
      this.mMatrix = null;
      this.mShouldClipRect = false;
      this.mHeight = 0;
      this.mWidth = 0;
   }

   public void unscheduleDrawable(Drawable var1, Runnable var2) {
      this.unscheduleSelf(var2);
   }
}
