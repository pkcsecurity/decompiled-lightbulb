package com.facebook.react.views.text.frescosupport;

import android.view.View;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.views.text.frescosupport.FrescoBasedReactTextInlineImageShadowNode;
import javax.annotation.Nullable;

@ReactModule(
   name = "RCTTextInlineImage"
)
public class FrescoBasedReactTextInlineImageViewManager extends ViewManager<View, FrescoBasedReactTextInlineImageShadowNode> {

   protected static final String REACT_CLASS = "RCTTextInlineImage";
   @Nullable
   private final Object mCallerContext;
   @Nullable
   private final AbstractDraweeControllerBuilder mDraweeControllerBuilder;


   public FrescoBasedReactTextInlineImageViewManager() {
      this((AbstractDraweeControllerBuilder)null, (Object)null);
   }

   public FrescoBasedReactTextInlineImageViewManager(@Nullable AbstractDraweeControllerBuilder var1, @Nullable Object var2) {
      this.mDraweeControllerBuilder = var1;
      this.mCallerContext = var2;
   }

   public FrescoBasedReactTextInlineImageShadowNode createShadowNodeInstance() {
      Object var1;
      if(this.mDraweeControllerBuilder != null) {
         var1 = this.mDraweeControllerBuilder;
      } else {
         var1 = Fresco.newDraweeControllerBuilder();
      }

      return new FrescoBasedReactTextInlineImageShadowNode((AbstractDraweeControllerBuilder)var1, this.mCallerContext);
   }

   public View createViewInstance(ThemedReactContext var1) {
      throw new IllegalStateException("RCTTextInlineImage doesn\'t map into a native view");
   }

   public String getName() {
      return "RCTTextInlineImage";
   }

   public Class<FrescoBasedReactTextInlineImageShadowNode> getShadowNodeClass() {
      return FrescoBasedReactTextInlineImageShadowNode.class;
   }

   public void updateExtraData(View var1, Object var2) {}
}
