package com.facebook.litho;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import com.facebook.litho.MatrixDrawable;
import com.facebook.litho.displaylist.DisplayList;
import com.facebook.litho.displaylist.DisplayListException;

class DisplayListDrawable extends Drawable implements Callback {

   private static final boolean DEBUG = false;
   private static final String LOG_TAG = "DisplayListDrawable";
   private static Paint sDebugBorderPaint;
   @Nullable
   private DisplayList mDisplayList;
   private boolean mDoNotAttemptDLDrawing = false;
   private Drawable mDrawable;
   private boolean mIgnoreInvalidations;
   private boolean mInvalidated;
   @Nullable
   private String mName;


   DisplayListDrawable(Drawable var1) {
      this.setWrappedDrawable(var1);
   }

   @UiThread
   private void drawContentIntoDisplayList() throws DisplayListException {
      logDebug("Drawing content into DL", false);
      Rect var1 = this.mDrawable.getBounds();
      Canvas var2 = this.mDisplayList.start(var1.width(), var1.height());
      var2.translate((float)(-var1.left), (float)(-var1.top));
      this.mDrawable.draw(var2);
      var2.translate((float)var1.left, (float)var1.top);
      drawDebugBorder(var1, var2);
      this.mDisplayList.end(var2);
      this.mDisplayList.setBounds(var1.left, var1.top, var1.right, var1.bottom);
      this.mDisplayList.setTranslationX(0.0F);
      this.mDisplayList.setTranslationY(0.0F);
   }

   private static void drawDebugBorder(Rect var0, Canvas var1) {}

   private void invalidateDL() {
      logDebug("invalidateDL", true);
      this.mInvalidated = true;
   }

   private static void logDebug(String var0, boolean var1) {}

   public void draw(Canvas var1) {
      if(this.mDrawable == null) {
         throw new IllegalStateException("The wrapped drawable hasn\'t been set yet");
      } else {
         logDebug("Drawing", false);
         if(this.mDoNotAttemptDLDrawing) {
            logDebug("\t> origin (not attempting DL drawing)", false);
            this.mDrawable.draw(var1);
         } else {
            if(this.mDisplayList == null) {
               DisplayList var2 = DisplayList.createDisplayList(this.mName);
               if(var2 == null) {
                  logDebug("\t> origin (DL wasn\'t created)", false);
                  this.mDrawable.draw(var1);
                  return;
               }

               this.setDisplayList(var2);
            }

            try {
               if(this.mInvalidated || !this.mDisplayList.isValid()) {
                  this.drawContentIntoDisplayList();
                  this.mInvalidated = false;
               }

               if(!this.mDisplayList.isValid()) {
                  logDebug("\t> origin (DL isn\'t valid)", false);
                  this.mDrawable.draw(var1);
               } else {
                  logDebug("\t> DL", false);
                  this.mDisplayList.draw(var1);
               }
            } catch (DisplayListException var4) {
               StringBuilder var3 = new StringBuilder();
               var3.append("\t> origin (exception)\n\t");
               var3.append(var4);
               logDebug(var3.toString(), false);
               this.mDoNotAttemptDLDrawing = true;
               this.mDisplayList = null;
               this.mDrawable.draw(var1);
            }
         }
      }
   }

   public int getChangingConfigurations() {
      return this.mDrawable.getChangingConfigurations();
   }

   public Drawable getCurrent() {
      return this.mDrawable.getCurrent();
   }

   public int getIntrinsicHeight() {
      return this.mDrawable.getIntrinsicHeight();
   }

   public int getIntrinsicWidth() {
      return this.mDrawable.getIntrinsicWidth();
   }

   public int getMinimumHeight() {
      return this.mDrawable.getMinimumHeight();
   }

   public int getMinimumWidth() {
      return this.mDrawable.getMinimumWidth();
   }

   public int getOpacity() {
      return this.mDrawable.getOpacity();
   }

   public boolean getPadding(Rect var1) {
      return this.mDrawable.getPadding(var1);
   }

   public int[] getState() {
      return this.mDrawable.getState();
   }

   public Region getTransparentRegion() {
      return this.mDrawable.getTransparentRegion();
   }

   public void invalidateDrawable(Drawable var1) {
      logDebug("Invalidating drawable", true);
      this.invalidateSelf();
      if(!this.mIgnoreInvalidations) {
         this.setBounds(this.mDrawable.getBounds());
         this.invalidateDL();
      }
   }

   public boolean isStateful() {
      return this.mDrawable.isStateful();
   }

   public Drawable mutate() {
      Drawable var1 = this.mDrawable;
      Drawable var2 = var1.mutate();
      if(var2 != var1) {
         this.setWrappedDrawable(var2);
      }

      return this;
   }

   @UiThread
   protected void onBoundsChange(Rect var1) {
      StringBuilder var4 = new StringBuilder();
      var4.append("On bounds change, bounds=");
      var4.append(var1);
      logDebug(var4.toString(), false);
      this.mDrawable.setCallback((Callback)null);
      this.mDrawable.setBounds(var1);
      if(this.mDrawable instanceof MatrixDrawable) {
         ((MatrixDrawable)this.mDrawable).bind(var1.width(), var1.height());
      }

      this.mDrawable.setCallback(this);
      if(this.mDisplayList != null && this.mDisplayList.isValid() && !this.mInvalidated) {
         Rect var7 = this.mDisplayList.getBounds();
         if(var1.height() == var7.height() && var1.width() == var7.width()) {
            try {
               int var2 = var1.left - var7.left;
               int var3 = var1.top - var7.top;
               StringBuilder var6 = new StringBuilder();
               var6.append("\t> size didn\'t change, translating, dx=");
               var6.append(var2);
               var6.append(", dy=");
               var6.append(var3);
               logDebug(var6.toString(), false);
               this.mDisplayList.setTranslationX((float)var2);
               this.mDisplayList.setTranslationY((float)var3);
            } catch (DisplayListException var5) {
               var4 = new StringBuilder();
               var4.append("\t> couldn\'t translate\n\t");
               var4.append(var5);
               logDebug(var4.toString(), false);
               this.mDisplayList = null;
            }
         } else {
            logDebug("\t> size changed, invalidating", false);
            this.invalidateDL();
         }
      } else {
         logDebug("\t> no valid DL", false);
      }
   }

   protected boolean onLevelChange(int var1) {
      return this.mDrawable.setLevel(var1);
   }

   public void release() {
      this.setCallback((Callback)null);
      this.mIgnoreInvalidations = false;
      this.mInvalidated = false;
      this.mDoNotAttemptDLDrawing = false;
      this.mDrawable.setCallback((Callback)null);
      this.mDrawable = null;
      this.mName = null;
      this.mDisplayList = null;
   }

   public void scheduleDrawable(Drawable var1, Runnable var2, long var3) {
      this.scheduleSelf(var2, var3);
   }

   public void setAlpha(int var1) {
      this.mDrawable.setAlpha(var1);
   }

   public void setChangingConfigurations(int var1) {
      this.mDrawable.setChangingConfigurations(var1);
   }

   public void setColorFilter(ColorFilter var1) {
      this.mDrawable.setColorFilter(var1);
   }

   @VisibleForTesting
   void setDisplayList(DisplayList var1) {
      this.mDisplayList = var1;
      this.invalidateDL();
   }

   public void setDither(boolean var1) {
      this.mDrawable.setDither(var1);
   }

   public void setFilterBitmap(boolean var1) {
      this.mDrawable.setFilterBitmap(var1);
   }

   public boolean setState(int[] var1) {
      return this.mDrawable.setState(var1);
   }

   public void setTint(int var1) {
      this.setTintList(ColorStateList.valueOf(var1));
   }

   @TargetApi(21)
   public void setTintList(ColorStateList var1) {
      this.mDrawable.setTintList(var1);
   }

   @TargetApi(21)
   public void setTintMode(Mode var1) {
      this.mDrawable.setTintMode(var1);
   }

   public boolean setVisible(boolean var1, boolean var2) {
      return super.setVisible(var1, var2) || this.mDrawable.setVisible(var1, var2);
   }

   void setWrappedDrawable(Drawable var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The wrapped drawable must not be null");
      } else {
         if(this.mDrawable != null) {
            this.mDrawable.setCallback((Callback)null);
         }

         this.mDrawable = var1;
         this.mDrawable.setCallback(this);
         this.invalidateDL();
         this.invalidateSelf();
      }
   }

   public void suppressInvalidations(boolean var1) {
      this.mIgnoreInvalidations = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("DisplayListDrawable(");
      var1.append(this.hashCode());
      var1.append("){\n\tbounds=");
      var1.append(this.getBounds());
      var1.append("\n\torigin=");
      var1.append(this.mDrawable);
      var1.append(" bounds=");
      var1.append(this.mDrawable.getBounds());
      var1.append("\n\tDL=");
      var1.append(this.mDisplayList);
      var1.append("\n\tinvalidated=");
      var1.append(this.mInvalidated);
      var1.append("\n\tskipping DL=");
      var1.append(this.mDoNotAttemptDLDrawing);
      var1.append('}');
      return var1.toString();
   }

   public void unscheduleDrawable(Drawable var1, Runnable var2) {
      this.unscheduleSelf(var2);
   }
}
