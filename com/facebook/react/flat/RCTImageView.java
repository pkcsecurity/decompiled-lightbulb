package com.facebook.react.flat;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.AbstractDrawCommand;
import com.facebook.react.flat.AttachDetachListener;
import com.facebook.react.flat.DrawImage;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ImageResizeMode;
import javax.annotation.Nullable;

class RCTImageView<T extends AbstractDrawCommand & DrawImage> extends FlatShadowNode {

   static Object sCallerContext = RCTImageView.class;
   private T mDrawImage;


   RCTImageView(T var1) {
      this.mDrawImage = var1;
   }

   static Object getCallerContext() {
      return sCallerContext;
   }

   private T getMutableDrawImage() {
      if(this.mDrawImage.isFrozen()) {
         this.mDrawImage = this.mDrawImage.mutableCopy();
         this.invalidate();
      }

      return this.mDrawImage;
   }

   static void setCallerContext(Object var0) {
      sCallerContext = var0;
   }

   protected void collectState(StateBuilder var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      super.collectState(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      if(((DrawImage)this.mDrawImage).hasImageRequest()) {
         this.mDrawImage = this.mDrawImage.updateBoundsAndFreeze(var2, var3, var4, var5, var6, var7, var8, var9);
         var1.addDrawCommand(this.mDrawImage);
         var1.addAttachDetachListener((AttachDetachListener)this.mDrawImage);
      }

   }

   boolean doesDraw() {
      return ((DrawImage)this.mDrawImage).hasImageRequest() || super.doesDraw();
   }

   public void setBorder(int var1, float var2) {
      super.setBorder(var1, var2);
      if(var1 == 8 && ((DrawImage)this.mDrawImage).getBorderWidth() != var2) {
         ((DrawImage)this.getMutableDrawImage()).setBorderWidth(var2);
      }

   }

   @ReactProp(
      customType = "Color",
      name = "borderColor"
   )
   public void setBorderColor(int var1) {
      if(((DrawImage)this.mDrawImage).getBorderColor() != var1) {
         ((DrawImage)this.getMutableDrawImage()).setBorderColor(var1);
      }

   }

   @ReactProp(
      name = "borderRadius"
   )
   public void setBorderRadius(float var1) {
      if(((DrawImage)this.mDrawImage).getBorderRadius() != var1) {
         ((DrawImage)this.getMutableDrawImage()).setBorderRadius(PixelUtil.toPixelFromDIP(var1));
      }

   }

   @ReactProp(
      name = "fadeDuration"
   )
   public void setFadeDuration(int var1) {
      ((DrawImage)this.getMutableDrawImage()).setFadeDuration(var1);
   }

   @ReactProp(
      name = "progressiveRenderingEnabled"
   )
   public void setProgressiveRenderingEnabled(boolean var1) {
      ((DrawImage)this.getMutableDrawImage()).setProgressiveRenderingEnabled(var1);
   }

   @ReactProp(
      name = "resizeMode"
   )
   public void setResizeMode(@Nullable String var1) {
      ScalingUtils.ScaleType var2 = ImageResizeMode.toScaleType(var1);
      if(((DrawImage)this.mDrawImage).getScaleType() != var2) {
         ((DrawImage)this.getMutableDrawImage()).setScaleType(var2);
      }

   }

   @ReactProp(
      name = "shouldNotifyLoadEvents"
   )
   public void setShouldNotifyLoadEvents(boolean var1) {
      DrawImage var3 = (DrawImage)this.getMutableDrawImage();
      int var2;
      if(var1) {
         var2 = this.getReactTag();
      } else {
         var2 = 0;
      }

      var3.setReactTag(var2);
   }

   @ReactProp(
      name = "src"
   )
   public void setSource(@Nullable ReadableArray var1) {
      ((DrawImage)this.getMutableDrawImage()).setSource(this.getThemedContext(), var1);
   }

   @ReactProp(
      name = "tintColor"
   )
   public void setTintColor(int var1) {
      ((DrawImage)this.getMutableDrawImage()).setTintColor(var1);
   }
}
