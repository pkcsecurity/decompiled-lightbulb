package com.facebook.react.flat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.RCTImageView;
import javax.annotation.Nullable;

final class DraweeRequestHelper {

   private static AbstractDraweeControllerBuilder sControllerBuilder;
   private static GenericDraweeHierarchyBuilder sHierarchyBuilder;
   private int mAttachCounter;
   private final DraweeController mDraweeController;


   DraweeRequestHelper(ImageRequest var1, @Nullable ImageRequest var2, ControllerListener var3) {
      AbstractDraweeControllerBuilder var4 = sControllerBuilder.setImageRequest(var1).setCallerContext(RCTImageView.getCallerContext()).setControllerListener(var3);
      if(var2 != null) {
         var4.setLowResImageRequest(var2);
      }

      AbstractDraweeController var5 = var4.build();
      var5.setHierarchy(sHierarchyBuilder.build());
      this.mDraweeController = var5;
   }

   static void setDraweeControllerBuilder(AbstractDraweeControllerBuilder var0) {
      sControllerBuilder = var0;
   }

   static void setResources(Resources var0) {
      sHierarchyBuilder = new GenericDraweeHierarchyBuilder(var0);
   }

   void attach(FlatViewGroup.InvalidateCallback var1) {
      ++this.mAttachCounter;
      if(this.mAttachCounter == 1) {
         this.getDrawable().setCallback((Callback)var1.get());
         this.mDraweeController.onAttach();
      }

   }

   void detach() {
      --this.mAttachCounter;
      if(this.mAttachCounter == 0) {
         this.mDraweeController.onDetach();
      }

   }

   Drawable getDrawable() {
      return this.getHierarchy().getTopLevelDrawable();
   }

   GenericDraweeHierarchy getHierarchy() {
      return (GenericDraweeHierarchy)Assertions.assumeNotNull(this.mDraweeController.getHierarchy());
   }
}
