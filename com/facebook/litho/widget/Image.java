package com.facebook.litho.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.widget.ImageView.ScaleType;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Diff;
import com.facebook.litho.DrawableMatrix;
import com.facebook.litho.MatrixDrawable;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.ImageSpec;
import java.util.BitSet;

public final class Image extends Component {

   @Comparable(
      type = 13
   )
   @Prop(
      optional = false,
      resType = ResType.DRAWABLE
   )
   Drawable drawable;
   Integer drawableHeight;
   DrawableMatrix drawableMatrix;
   Integer drawableWidth;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ScaleType scaleType;


   private Image() {
      super("Image");
   }

   public static Image.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Image.Builder create(ComponentContext var0, int var1, int var2) {
      Image.Builder var3 = new Image.Builder();
      var3.init(var0, var1, var2, new Image());
      return var3;
   }

   public boolean callsShouldUpdateOnMount() {
      return true;
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   protected void copyInterStageImpl(Component var1) {
      Image var2 = (Image)var1;
      this.drawableHeight = var2.drawableHeight;
      this.drawableMatrix = var2.drawableMatrix;
      this.drawableWidth = var2.drawableWidth;
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
            Image var2 = (Image)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.drawable != null) {
                  if(!this.drawable.equals(var2.drawable)) {
                     return false;
                  }
               } else if(var2.drawable != null) {
                  return false;
               }

               if(this.scaleType != null) {
                  if(!this.scaleType.equals(var2.scaleType)) {
                     return false;
                  }
               } else if(var2.scaleType != null) {
                  return false;
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   protected boolean isMountSizeDependent() {
      return true;
   }

   public boolean isPureRender() {
      return true;
   }

   public Image makeShallowCopy() {
      Image var1 = (Image)super.makeShallowCopy();
      var1.drawableHeight = null;
      var1.drawableMatrix = null;
      var1.drawableWidth = null;
      return var1;
   }

   protected void onBind(ComponentContext var1, Object var2) {
      ImageSpec.onBind(var1, (MatrixDrawable)var2, this.drawableWidth, this.drawableHeight);
   }

   protected void onBoundsDefined(ComponentContext var1, ComponentLayout var2) {
      Output var3 = this.acquireOutput();
      Output var4 = this.acquireOutput();
      Output var5 = this.acquireOutput();
      ImageSpec.onBoundsDefined(var1, var2, this.drawable, this.scaleType, var3, var4, var5);
      this.drawableMatrix = (DrawableMatrix)var3.get();
      this.releaseOutput(var3);
      this.drawableWidth = (Integer)var4.get();
      this.releaseOutput(var4);
      this.drawableHeight = (Integer)var5.get();
      this.releaseOutput(var5);
   }

   protected Object onCreateMountContent(Context var1) {
      return ImageSpec.onCreateMountContent(var1);
   }

   protected void onLoadStyle(ComponentContext var1) {
      Output var2 = this.acquireOutput();
      Output var3 = this.acquireOutput();
      ImageSpec.onLoadStyle(var1, var2, var3);
      if(var2.get() != null) {
         this.drawable = (Drawable)var2.get();
      }

      this.releaseOutput(var2);
      if(var3.get() != null) {
         this.scaleType = (ScaleType)var3.get();
      }

      this.releaseOutput(var3);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      ImageSpec.onMeasure(var1, var2, var3, var4, var5, this.drawable);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      ImageSpec.onMount(var1, (MatrixDrawable)var2, this.drawable, this.drawableMatrix);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      ImageSpec.onUnmount(var1, (MatrixDrawable)var2, this.drawable);
   }

   protected int poolSize() {
      return 30;
   }

   protected boolean shouldUpdate(Component var1, Component var2) {
      Image var6 = (Image)var1;
      Image var5 = (Image)var2;
      Object var4 = null;
      ScaleType var8;
      if(var6 == null) {
         var8 = null;
      } else {
         var8 = var6.scaleType;
      }

      ScaleType var9;
      if(var5 == null) {
         var9 = null;
      } else {
         var9 = var5.scaleType;
      }

      Diff var7 = this.acquireDiff(var8, var9);
      Drawable var10;
      if(var6 == null) {
         var10 = null;
      } else {
         var10 = var6.drawable;
      }

      Drawable var11;
      if(var5 == null) {
         var11 = (Drawable)var4;
      } else {
         var11 = var5.drawable;
      }

      Diff var12 = this.acquireDiff(var10, var11);
      boolean var3 = ImageSpec.shouldUpdate(var7, var12);
      this.releaseDiff(var7);
      this.releaseDiff(var12);
      return var3;
   }

   public static class Builder extends Component.Builder<Image.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"drawable"};
      ComponentContext mContext;
      Image mImage;
      private final BitSet mRequired = new BitSet(1);


      private void init(ComponentContext var1, int var2, int var3, Image var4) {
         super.init(var1, var2, var3, var4);
         this.mImage = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public Image build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         Image var1 = this.mImage;
         this.release();
         return var1;
      }

      public Image.Builder drawable(Drawable var1) {
         this.mImage.drawable = var1;
         this.mRequired.set(0);
         return this;
      }

      public Image.Builder drawableAttr(@AttrRes int var1) {
         this.mImage.drawable = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         this.mRequired.set(0);
         return this;
      }

      public Image.Builder drawableAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mImage.drawable = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         this.mRequired.set(0);
         return this;
      }

      public Image.Builder drawableRes(@DrawableRes int var1) {
         this.mImage.drawable = this.mResourceResolver.resolveDrawableRes(var1);
         this.mRequired.set(0);
         return this;
      }

      public Image.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mImage = null;
         this.mContext = null;
      }

      public Image.Builder scaleType(ScaleType var1) {
         this.mImage.scaleType = var1;
         return this;
      }
   }
}
