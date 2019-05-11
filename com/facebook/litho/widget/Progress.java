package com.facebook.litho.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.widget.ProgressBar;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.ProgressSpec;

public final class Progress extends Component {

   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int color = 0;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.DRAWABLE
   )
   Drawable indeterminateDrawable;
   Drawable resolvedIndeterminateDrawable;


   private Progress() {
      super("Progress");
   }

   public static Progress.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Progress.Builder create(ComponentContext var0, int var1, int var2) {
      Progress.Builder var3 = new Progress.Builder();
      var3.init(var0, var1, var2, new Progress());
      return var3;
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   protected void copyInterStageImpl(Component var1) {
      this.resolvedIndeterminateDrawable = ((Progress)var1).resolvedIndeterminateDrawable;
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.VIEW;
   }

   public boolean isEquivalentTo(Component var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            Progress var2 = (Progress)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else if(this.color != var2.color) {
               return false;
            } else {
               if(this.indeterminateDrawable != null) {
                  if(!this.indeterminateDrawable.equals(var2.indeterminateDrawable)) {
                     return false;
                  }
               } else if(var2.indeterminateDrawable != null) {
                  return false;
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean isPureRender() {
      return true;
   }

   public Progress makeShallowCopy() {
      Progress var1 = (Progress)super.makeShallowCopy();
      var1.resolvedIndeterminateDrawable = null;
      return var1;
   }

   protected Object onCreateMountContent(Context var1) {
      return ProgressSpec.onCreateMountContent(var1);
   }

   protected void onLoadStyle(ComponentContext var1) {
      Output var2 = this.acquireOutput();
      ProgressSpec.onLoadStyle(var1, var2);
      if(var2.get() != null) {
         this.indeterminateDrawable = (Drawable)var2.get();
      }

      this.releaseOutput(var2);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      ProgressSpec.onMeasure(var1, var2, var3, var4, var5);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      ProgressSpec.onMount(var1, (ProgressBar)var2, this.color, this.resolvedIndeterminateDrawable);
   }

   protected void onPrepare(ComponentContext var1) {
      Output var2 = this.acquireOutput();
      ProgressSpec.onPrepare(var1, this.indeterminateDrawable, var2);
      this.resolvedIndeterminateDrawable = (Drawable)var2.get();
      this.releaseOutput(var2);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      ProgressSpec.onUnmount(var1, (ProgressBar)var2, this.color, this.resolvedIndeterminateDrawable);
   }

   protected int poolSize() {
      return 3;
   }

   public static class Builder extends Component.Builder<Progress.Builder> {

      ComponentContext mContext;
      Progress mProgress;


      private void init(ComponentContext var1, int var2, int var3, Progress var4) {
         super.init(var1, var2, var3, var4);
         this.mProgress = var4;
         this.mContext = var1;
      }

      public Progress build() {
         Progress var1 = this.mProgress;
         this.release();
         return var1;
      }

      public Progress.Builder color(@ColorInt int var1) {
         this.mProgress.color = var1;
         return this;
      }

      public Progress.Builder colorAttr(@AttrRes int var1) {
         this.mProgress.color = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Progress.Builder colorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mProgress.color = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Progress.Builder colorRes(@ColorRes int var1) {
         this.mProgress.color = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Progress.Builder getThis() {
         return this;
      }

      public Progress.Builder indeterminateDrawable(Drawable var1) {
         this.mProgress.indeterminateDrawable = var1;
         return this;
      }

      public Progress.Builder indeterminateDrawableAttr(@AttrRes int var1) {
         this.mProgress.indeterminateDrawable = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         return this;
      }

      public Progress.Builder indeterminateDrawableAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mProgress.indeterminateDrawable = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         return this;
      }

      public Progress.Builder indeterminateDrawableRes(@DrawableRes int var1) {
         this.mProgress.indeterminateDrawable = this.mResourceResolver.resolveDrawableRes(var1);
         return this;
      }

      protected void release() {
         super.release();
         this.mProgress = null;
         this.mContext = null;
      }
   }
}
