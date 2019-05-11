package com.facebook.litho.fresco;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Size;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.fresco.DraweeDrawable;
import com.facebook.litho.fresco.FrescoImageSpec;
import java.util.BitSet;

public final class FrescoImage extends Component {

   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ScalingUtils.ScaleType actualImageScaleType;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ColorFilter colorFilter;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   DraweeController controller;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int fadeDuration;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.DRAWABLE
   )
   Drawable failureImage;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ScalingUtils.ScaleType failureImageScaleType;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.FLOAT
   )
   float imageAspectRatio;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.DRAWABLE
   )
   Drawable placeholderImage;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   PointF placeholderImageFocusPoint;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ScalingUtils.ScaleType placeholderImageScaleType;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.DRAWABLE
   )
   Drawable progressBarImage;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ScalingUtils.ScaleType progressBarImageScaleType;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.DRAWABLE
   )
   Drawable retryImage;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ScalingUtils.ScaleType retryImageScaleType;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RoundingParams roundingParams;


   private FrescoImage() {
      super("FrescoImage");
      this.actualImageScaleType = FrescoImageSpec.actualImageScaleType;
      this.fadeDuration = 300;
      this.failureImageScaleType = FrescoImageSpec.failureImageScaleType;
      this.imageAspectRatio = 1.0F;
      this.placeholderImageFocusPoint = FrescoImageSpec.placeholderImageFocusPoint;
      this.placeholderImageScaleType = FrescoImageSpec.placeholderImageScaleType;
      this.progressBarImageScaleType = FrescoImageSpec.progressBarImageScaleType;
      this.retryImageScaleType = FrescoImageSpec.retryImageScaleType;
   }

   public static FrescoImage.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static FrescoImage.Builder create(ComponentContext var0, int var1, int var2) {
      FrescoImage.Builder var3 = new FrescoImage.Builder();
      var3.init(var0, var1, var2, new FrescoImage());
      return var3;
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.DRAWABLE;
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
            FrescoImage var2 = (FrescoImage)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.actualImageScaleType != null) {
                  if(!this.actualImageScaleType.equals(var2.actualImageScaleType)) {
                     return false;
                  }
               } else if(var2.actualImageScaleType != null) {
                  return false;
               }

               if(this.colorFilter != null) {
                  if(!this.colorFilter.equals(var2.colorFilter)) {
                     return false;
                  }
               } else if(var2.colorFilter != null) {
                  return false;
               }

               if(this.controller != null) {
                  if(!this.controller.equals(var2.controller)) {
                     return false;
                  }
               } else if(var2.controller != null) {
                  return false;
               }

               if(this.fadeDuration != var2.fadeDuration) {
                  return false;
               } else {
                  if(this.failureImage != null) {
                     if(!this.failureImage.equals(var2.failureImage)) {
                        return false;
                     }
                  } else if(var2.failureImage != null) {
                     return false;
                  }

                  if(this.failureImageScaleType != null) {
                     if(!this.failureImageScaleType.equals(var2.failureImageScaleType)) {
                        return false;
                     }
                  } else if(var2.failureImageScaleType != null) {
                     return false;
                  }

                  if(Float.compare(this.imageAspectRatio, var2.imageAspectRatio) != 0) {
                     return false;
                  } else {
                     if(this.placeholderImage != null) {
                        if(!this.placeholderImage.equals(var2.placeholderImage)) {
                           return false;
                        }
                     } else if(var2.placeholderImage != null) {
                        return false;
                     }

                     if(this.placeholderImageFocusPoint != null) {
                        if(!this.placeholderImageFocusPoint.equals(var2.placeholderImageFocusPoint)) {
                           return false;
                        }
                     } else if(var2.placeholderImageFocusPoint != null) {
                        return false;
                     }

                     if(this.placeholderImageScaleType != null) {
                        if(!this.placeholderImageScaleType.equals(var2.placeholderImageScaleType)) {
                           return false;
                        }
                     } else if(var2.placeholderImageScaleType != null) {
                        return false;
                     }

                     if(this.progressBarImage != null) {
                        if(!this.progressBarImage.equals(var2.progressBarImage)) {
                           return false;
                        }
                     } else if(var2.progressBarImage != null) {
                        return false;
                     }

                     if(this.progressBarImageScaleType != null) {
                        if(!this.progressBarImageScaleType.equals(var2.progressBarImageScaleType)) {
                           return false;
                        }
                     } else if(var2.progressBarImageScaleType != null) {
                        return false;
                     }

                     if(this.retryImage != null) {
                        if(!this.retryImage.equals(var2.retryImage)) {
                           return false;
                        }
                     } else if(var2.retryImage != null) {
                        return false;
                     }

                     if(this.retryImageScaleType != null) {
                        if(!this.retryImageScaleType.equals(var2.retryImageScaleType)) {
                           return false;
                        }
                     } else if(var2.retryImageScaleType != null) {
                        return false;
                     }

                     if(this.roundingParams != null) {
                        if(!this.roundingParams.equals(var2.roundingParams)) {
                           return false;
                        }
                     } else if(var2.roundingParams != null) {
                        return false;
                     }

                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   protected void onBind(ComponentContext var1, Object var2) {
      FrescoImageSpec.onBind(var1, (DraweeDrawable)var2, this.controller);
   }

   protected Object onCreateMountContent(Context var1) {
      return FrescoImageSpec.onCreateMountContent(var1);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      FrescoImageSpec.onMeasure(var1, var2, var3, var4, var5, this.imageAspectRatio);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      FrescoImageSpec.onMount(var1, (DraweeDrawable)var2, this.actualImageScaleType, this.fadeDuration, this.failureImage, this.failureImageScaleType, this.placeholderImage, this.placeholderImageFocusPoint, this.placeholderImageScaleType, this.progressBarImage, this.progressBarImageScaleType, this.retryImage, this.retryImageScaleType, this.roundingParams, this.colorFilter);
   }

   protected void onUnbind(ComponentContext var1, Object var2) {
      FrescoImageSpec.onUnbind(var1, (DraweeDrawable)var2, this.controller);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      FrescoImageSpec.onUnmount(var1, (DraweeDrawable)var2);
   }

   protected int poolSize() {
      return 3;
   }

   public static class Builder extends Component.Builder<FrescoImage.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"controller"};
      ComponentContext mContext;
      FrescoImage mFrescoImage;
      private final BitSet mRequired = new BitSet(1);


      private void init(ComponentContext var1, int var2, int var3, FrescoImage var4) {
         super.init(var1, var2, var3, var4);
         this.mFrescoImage = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public FrescoImage.Builder actualImageScaleType(ScalingUtils.ScaleType var1) {
         this.mFrescoImage.actualImageScaleType = var1;
         return this;
      }

      public FrescoImage build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         FrescoImage var1 = this.mFrescoImage;
         this.release();
         return var1;
      }

      public FrescoImage.Builder colorFilter(ColorFilter var1) {
         this.mFrescoImage.colorFilter = var1;
         return this;
      }

      public FrescoImage.Builder controller(DraweeController var1) {
         this.mFrescoImage.controller = var1;
         this.mRequired.set(0);
         return this;
      }

      public FrescoImage.Builder fadeDuration(int var1) {
         this.mFrescoImage.fadeDuration = var1;
         return this;
      }

      public FrescoImage.Builder failureImage(Drawable var1) {
         this.mFrescoImage.failureImage = var1;
         return this;
      }

      public FrescoImage.Builder failureImageAttr(@AttrRes int var1) {
         this.mFrescoImage.failureImage = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         return this;
      }

      public FrescoImage.Builder failureImageAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mFrescoImage.failureImage = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         return this;
      }

      public FrescoImage.Builder failureImageRes(@DrawableRes int var1) {
         this.mFrescoImage.failureImage = this.mResourceResolver.resolveDrawableRes(var1);
         return this;
      }

      public FrescoImage.Builder failureImageScaleType(ScalingUtils.ScaleType var1) {
         this.mFrescoImage.failureImageScaleType = var1;
         return this;
      }

      public FrescoImage.Builder getThis() {
         return this;
      }

      public FrescoImage.Builder imageAspectRatio(float var1) {
         this.mFrescoImage.imageAspectRatio = var1;
         return this;
      }

      public FrescoImage.Builder imageAspectRatioAttr(@AttrRes int var1) {
         this.mFrescoImage.imageAspectRatio = this.mResourceResolver.resolveFloatAttr(var1, 0);
         return this;
      }

      public FrescoImage.Builder imageAspectRatioAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mFrescoImage.imageAspectRatio = this.mResourceResolver.resolveFloatAttr(var1, var2);
         return this;
      }

      public FrescoImage.Builder imageAspectRatioRes(@DimenRes int var1) {
         this.mFrescoImage.imageAspectRatio = this.mResourceResolver.resolveFloatRes(var1);
         return this;
      }

      public FrescoImage.Builder placeholderImage(Drawable var1) {
         this.mFrescoImage.placeholderImage = var1;
         return this;
      }

      public FrescoImage.Builder placeholderImageAttr(@AttrRes int var1) {
         this.mFrescoImage.placeholderImage = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         return this;
      }

      public FrescoImage.Builder placeholderImageAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mFrescoImage.placeholderImage = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         return this;
      }

      public FrescoImage.Builder placeholderImageFocusPoint(PointF var1) {
         this.mFrescoImage.placeholderImageFocusPoint = var1;
         return this;
      }

      public FrescoImage.Builder placeholderImageRes(@DrawableRes int var1) {
         this.mFrescoImage.placeholderImage = this.mResourceResolver.resolveDrawableRes(var1);
         return this;
      }

      public FrescoImage.Builder placeholderImageScaleType(ScalingUtils.ScaleType var1) {
         this.mFrescoImage.placeholderImageScaleType = var1;
         return this;
      }

      public FrescoImage.Builder progressBarImage(Drawable var1) {
         this.mFrescoImage.progressBarImage = var1;
         return this;
      }

      public FrescoImage.Builder progressBarImageAttr(@AttrRes int var1) {
         this.mFrescoImage.progressBarImage = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         return this;
      }

      public FrescoImage.Builder progressBarImageAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mFrescoImage.progressBarImage = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         return this;
      }

      public FrescoImage.Builder progressBarImageRes(@DrawableRes int var1) {
         this.mFrescoImage.progressBarImage = this.mResourceResolver.resolveDrawableRes(var1);
         return this;
      }

      public FrescoImage.Builder progressBarImageScaleType(ScalingUtils.ScaleType var1) {
         this.mFrescoImage.progressBarImageScaleType = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mFrescoImage = null;
         this.mContext = null;
      }

      public FrescoImage.Builder retryImage(Drawable var1) {
         this.mFrescoImage.retryImage = var1;
         return this;
      }

      public FrescoImage.Builder retryImageAttr(@AttrRes int var1) {
         this.mFrescoImage.retryImage = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         return this;
      }

      public FrescoImage.Builder retryImageAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mFrescoImage.retryImage = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         return this;
      }

      public FrescoImage.Builder retryImageRes(@DrawableRes int var1) {
         this.mFrescoImage.retryImage = this.mResourceResolver.resolveDrawableRes(var1);
         return this;
      }

      public FrescoImage.Builder retryImageScaleType(ScalingUtils.ScaleType var1) {
         this.mFrescoImage.retryImageScaleType = var1;
         return this;
      }

      public FrescoImage.Builder roundingParams(RoundingParams var1) {
         this.mFrescoImage.roundingParams = var1;
         return this;
      }
   }
}
