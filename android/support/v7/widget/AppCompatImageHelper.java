package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintInfo;
import android.util.AttributeSet;
import android.widget.ImageView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class AppCompatImageHelper {

   private TintInfo mImageTint;
   private TintInfo mInternalImageTint;
   private TintInfo mTmpInfo;
   private final ImageView mView;


   public AppCompatImageHelper(ImageView var1) {
      this.mView = var1;
   }

   private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable var1) {
      if(this.mTmpInfo == null) {
         this.mTmpInfo = new TintInfo();
      }

      TintInfo var2 = this.mTmpInfo;
      var2.clear();
      ColorStateList var3 = ImageViewCompat.getImageTintList(this.mView);
      if(var3 != null) {
         var2.mHasTintList = true;
         var2.mTintList = var3;
      }

      Mode var4 = ImageViewCompat.getImageTintMode(this.mView);
      if(var4 != null) {
         var2.mHasTintMode = true;
         var2.mTintMode = var4;
      }

      if(!var2.mHasTintList && !var2.mHasTintMode) {
         return false;
      } else {
         AppCompatDrawableManager.tintDrawable(var1, var2, this.mView.getDrawableState());
         return true;
      }
   }

   private boolean shouldApplyFrameworkTintUsingColorFilter() {
      int var1 = VERSION.SDK_INT;
      boolean var2 = false;
      if(var1 > 21) {
         if(this.mInternalImageTint != null) {
            var2 = true;
         }

         return var2;
      } else {
         return var1 == 21;
      }
   }

   void applySupportImageTint() {
      Drawable var1 = this.mView.getDrawable();
      if(var1 != null) {
         DrawableUtils.fixDrawable(var1);
      }

      if(var1 != null) {
         if(this.shouldApplyFrameworkTintUsingColorFilter() && this.applyFrameworkTintUsingColorFilter(var1)) {
            return;
         }

         if(this.mImageTint != null) {
            AppCompatDrawableManager.tintDrawable(var1, this.mImageTint, this.mView.getDrawableState());
            return;
         }

         if(this.mInternalImageTint != null) {
            AppCompatDrawableManager.tintDrawable(var1, this.mInternalImageTint, this.mView.getDrawableState());
         }
      }

   }

   ColorStateList getSupportImageTintList() {
      return this.mImageTint != null?this.mImageTint.mTintList:null;
   }

   Mode getSupportImageTintMode() {
      return this.mImageTint != null?this.mImageTint.mTintMode:null;
   }

   boolean hasOverlappingRendering() {
      Drawable var1 = this.mView.getBackground();
      return VERSION.SDK_INT < 21 || !(var1 instanceof RippleDrawable);
   }

   public void loadFromAttributes(AttributeSet param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void setImageResource(int var1) {
      if(var1 != 0) {
         Drawable var2 = AppCompatResources.getDrawable(this.mView.getContext(), var1);
         if(var2 != null) {
            DrawableUtils.fixDrawable(var2);
         }

         this.mView.setImageDrawable(var2);
      } else {
         this.mView.setImageDrawable((Drawable)null);
      }

      this.applySupportImageTint();
   }

   void setInternalImageTint(ColorStateList var1) {
      if(var1 != null) {
         if(this.mInternalImageTint == null) {
            this.mInternalImageTint = new TintInfo();
         }

         this.mInternalImageTint.mTintList = var1;
         this.mInternalImageTint.mHasTintList = true;
      } else {
         this.mInternalImageTint = null;
      }

      this.applySupportImageTint();
   }

   void setSupportImageTintList(ColorStateList var1) {
      if(this.mImageTint == null) {
         this.mImageTint = new TintInfo();
      }

      this.mImageTint.mTintList = var1;
      this.mImageTint.mHasTintList = true;
      this.applySupportImageTint();
   }

   void setSupportImageTintMode(Mode var1) {
      if(this.mImageTint == null) {
         this.mImageTint = new TintInfo();
      }

      this.mImageTint.mTintMode = var1;
      this.mImageTint.mHasTintMode = true;
      this.applySupportImageTint();
   }
}
