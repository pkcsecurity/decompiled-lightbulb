package com.facebook.react.views.image;

import android.graphics.PorterDuff.Mode;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.image.GlobalImageLoadListener;
import com.facebook.react.views.image.ImageLoadEvent;
import com.facebook.react.views.image.ImageResizeMethod;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;
import com.facebook.yoga.YogaConstants;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "RCTImageView"
)
public class ReactImageManager extends SimpleViewManager<ReactImageView> {

   protected static final String REACT_CLASS = "RCTImageView";
   @Nullable
   private final Object mCallerContext;
   @Nullable
   private AbstractDraweeControllerBuilder mDraweeControllerBuilder;
   @Nullable
   private GlobalImageLoadListener mGlobalImageLoadListener;


   public ReactImageManager() {
      this.mDraweeControllerBuilder = null;
      this.mCallerContext = null;
   }

   public ReactImageManager(AbstractDraweeControllerBuilder var1, @Nullable GlobalImageLoadListener var2, Object var3) {
      this.mDraweeControllerBuilder = var1;
      this.mGlobalImageLoadListener = var2;
      this.mCallerContext = var3;
   }

   public ReactImageManager(AbstractDraweeControllerBuilder var1, Object var2) {
      this(var1, (GlobalImageLoadListener)null, var2);
   }

   public ReactImageView createViewInstance(ThemedReactContext var1) {
      return new ReactImageView(var1, this.getDraweeControllerBuilder(), this.mGlobalImageLoadListener, this.getCallerContext());
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

   @Nullable
   public Map getExportedCustomDirectEventTypeConstants() {
      return MapBuilder.of(ImageLoadEvent.eventNameForType(4), MapBuilder.of("registrationName", "onLoadStart"), ImageLoadEvent.eventNameForType(2), MapBuilder.of("registrationName", "onLoad"), ImageLoadEvent.eventNameForType(1), MapBuilder.of("registrationName", "onError"), ImageLoadEvent.eventNameForType(3), MapBuilder.of("registrationName", "onLoadEnd"));
   }

   public String getName() {
      return "RCTImageView";
   }

   protected void onAfterUpdateTransaction(ReactImageView var1) {
      super.onAfterUpdateTransaction(var1);
      var1.maybeUpdateView();
   }

   @ReactProp(
      name = "blurRadius"
   )
   public void setBlurRadius(ReactImageView var1, float var2) {
      var1.setBlurRadius(var2);
   }

   @ReactProp(
      customType = "Color",
      name = "borderColor"
   )
   public void setBorderColor(ReactImageView var1, @Nullable Integer var2) {
      if(var2 == null) {
         var1.setBorderColor(0);
      } else {
         var1.setBorderColor(var2.intValue());
      }
   }

   @ReactPropGroup(
      defaultFloat = Float.NaN,
      names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"}
   )
   public void setBorderRadius(ReactImageView var1, int var2, float var3) {
      float var4 = var3;
      if(!YogaConstants.isUndefined(var3)) {
         var4 = PixelUtil.toPixelFromDIP(var3);
      }

      if(var2 == 0) {
         var1.setBorderRadius(var4);
      } else {
         var1.setBorderRadius(var4, var2 - 1);
      }
   }

   @ReactProp(
      name = "borderWidth"
   )
   public void setBorderWidth(ReactImageView var1, float var2) {
      var1.setBorderWidth(var2);
   }

   @ReactProp(
      name = "fadeDuration"
   )
   public void setFadeDuration(ReactImageView var1, int var2) {
      var1.setFadeDuration(var2);
   }

   @ReactProp(
      name = "headers"
   )
   public void setHeaders(ReactImageView var1, ReadableMap var2) {
      var1.setHeaders(var2);
   }

   @ReactProp(
      name = "shouldNotifyLoadEvents"
   )
   public void setLoadHandlersRegistered(ReactImageView var1, boolean var2) {
      var1.setShouldNotifyLoadEvents(var2);
   }

   @ReactProp(
      name = "loadingIndicatorSrc"
   )
   public void setLoadingIndicatorSource(ReactImageView var1, @Nullable String var2) {
      var1.setLoadingIndicatorSource(var2);
   }

   @ReactProp(
      name = "overlayColor"
   )
   public void setOverlayColor(ReactImageView var1, @Nullable Integer var2) {
      if(var2 == null) {
         var1.setOverlayColor(0);
      } else {
         var1.setOverlayColor(var2.intValue());
      }
   }

   @ReactProp(
      name = "progressiveRenderingEnabled"
   )
   public void setProgressiveRenderingEnabled(ReactImageView var1, boolean var2) {
      var1.setProgressiveRenderingEnabled(var2);
   }

   @ReactProp(
      name = "resizeMethod"
   )
   public void setResizeMethod(ReactImageView var1, @Nullable String var2) {
      if(var2 != null && !"auto".equals(var2)) {
         if("resize".equals(var2)) {
            var1.setResizeMethod(ImageResizeMethod.RESIZE);
         } else if("scale".equals(var2)) {
            var1.setResizeMethod(ImageResizeMethod.SCALE);
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Invalid resize method: \'");
            var3.append(var2);
            var3.append("\'");
            throw new JSApplicationIllegalArgumentException(var3.toString());
         }
      } else {
         var1.setResizeMethod(ImageResizeMethod.AUTO);
      }
   }

   @ReactProp(
      name = "resizeMode"
   )
   public void setResizeMode(ReactImageView var1, @Nullable String var2) {
      var1.setScaleType(ImageResizeMode.toScaleType(var2));
   }

   @ReactProp(
      name = "src"
   )
   public void setSource(ReactImageView var1, @Nullable ReadableArray var2) {
      var1.setSource(var2);
   }

   @ReactProp(
      customType = "Color",
      name = "tintColor"
   )
   public void setTintColor(ReactImageView var1, @Nullable Integer var2) {
      if(var2 == null) {
         var1.clearColorFilter();
      } else {
         var1.setColorFilter(var2.intValue(), Mode.SRC_IN);
      }
   }
}
