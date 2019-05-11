package com.facebook.react.flat;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.flat.DrawImageWithDrawee;
import com.facebook.react.flat.FlatViewManager;
import com.facebook.react.flat.RCTImageView;
import com.facebook.react.views.image.GlobalImageLoadListener;
import javax.annotation.Nullable;

public final class RCTImageViewManager extends FlatViewManager {

   static final String REACT_CLASS = "RCTImageView";
   @Nullable
   private final Object mCallerContext;
   @Nullable
   private AbstractDraweeControllerBuilder mDraweeControllerBuilder;
   @Nullable
   private GlobalImageLoadListener mGlobalImageLoadListener;


   public RCTImageViewManager() {
      this((AbstractDraweeControllerBuilder)null, (Object)null);
   }

   public RCTImageViewManager(AbstractDraweeControllerBuilder var1, @Nullable GlobalImageLoadListener var2, Object var3) {
      this.mDraweeControllerBuilder = var1;
      this.mGlobalImageLoadListener = var2;
      this.mCallerContext = var3;
   }

   public RCTImageViewManager(AbstractDraweeControllerBuilder var1, Object var2) {
      this(var1, (GlobalImageLoadListener)null, var2);
   }

   public RCTImageView createShadowNodeInstance() {
      return new RCTImageView(new DrawImageWithDrawee(this.mGlobalImageLoadListener));
   }

   public Object getCallerContext() {
      return this.mCallerContext;
   }

   public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
      if(this.mDraweeControllerBuilder == null) {
         this.mDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
      }

      return this.mDraweeControllerBuilder;
   }

   public String getName() {
      return "RCTImageView";
   }

   public Class<RCTImageView> getShadowNodeClass() {
      return RCTImageView.class;
   }
}
