package com.facebook.react.uimanager;

import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;
import com.facebook.react.R;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.AccessibilityHelper;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.MatrixMathHelper;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactZIndexedViewGroup;
import com.facebook.react.uimanager.TransformHelper;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.util.ReactFindViewUtil;

public abstract class BaseViewManager<T extends View, C extends LayoutShadowNode> extends ViewManager<T, C> {

   private static final float CAMERA_DISTANCE_NORMALIZATION_MULTIPLIER = 5.0F;
   private static final int PERSPECTIVE_ARRAY_INVERTED_CAMERA_DISTANCE_INDEX = 2;
   private static final String PROP_ACCESSIBILITY_COMPONENT_TYPE = "accessibilityComponentType";
   private static final String PROP_ACCESSIBILITY_LABEL = "accessibilityLabel";
   private static final String PROP_ACCESSIBILITY_LIVE_REGION = "accessibilityLiveRegion";
   private static final String PROP_BACKGROUND_COLOR = "backgroundColor";
   private static final String PROP_ELEVATION = "elevation";
   private static final String PROP_IMPORTANT_FOR_ACCESSIBILITY = "importantForAccessibility";
   public static final String PROP_NATIVE_ID = "nativeID";
   private static final String PROP_RENDER_TO_HARDWARE_TEXTURE = "renderToHardwareTextureAndroid";
   private static final String PROP_ROTATION = "rotation";
   private static final String PROP_SCALE_X = "scaleX";
   private static final String PROP_SCALE_Y = "scaleY";
   public static final String PROP_TEST_ID = "testID";
   private static final String PROP_TRANSFORM = "transform";
   private static final String PROP_TRANSLATE_X = "translateX";
   private static final String PROP_TRANSLATE_Y = "translateY";
   private static final String PROP_Z_INDEX = "zIndex";
   private static MatrixMathHelper.MatrixDecompositionContext sMatrixDecompositionContext = new MatrixMathHelper.MatrixDecompositionContext();
   private static double[] sTransformDecompositionArray = new double[16];


   private static void resetTransformProperty(View var0) {
      var0.setTranslationX(PixelUtil.toPixelFromDIP(0.0F));
      var0.setTranslationY(PixelUtil.toPixelFromDIP(0.0F));
      var0.setRotation(0.0F);
      var0.setRotationX(0.0F);
      var0.setRotationY(0.0F);
      var0.setScaleX(1.0F);
      var0.setScaleY(1.0F);
      var0.setCameraDistance(0.0F);
   }

   private static void setTransformProperty(View var0, ReadableArray var1) {
      TransformHelper.processTransform(var1, sTransformDecompositionArray);
      MatrixMathHelper.decomposeMatrix(sTransformDecompositionArray, sMatrixDecompositionContext);
      var0.setTranslationX(PixelUtil.toPixelFromDIP((float)sMatrixDecompositionContext.translation[0]));
      var0.setTranslationY(PixelUtil.toPixelFromDIP((float)sMatrixDecompositionContext.translation[1]));
      var0.setRotation((float)sMatrixDecompositionContext.rotationDegrees[2]);
      var0.setRotationX((float)sMatrixDecompositionContext.rotationDegrees[0]);
      var0.setRotationY((float)sMatrixDecompositionContext.rotationDegrees[1]);
      var0.setScaleX((float)sMatrixDecompositionContext.scale[0]);
      var0.setScaleY((float)sMatrixDecompositionContext.scale[1]);
      double[] var4 = sMatrixDecompositionContext.perspective;
      if(var4.length > 2) {
         float var3 = (float)var4[2];
         float var2 = var3;
         if(var3 == 0.0F) {
            var2 = 7.8125E-4F;
         }

         var2 = -1.0F / var2;
         var0.setCameraDistance(DisplayMetricsHolder.getScreenDisplayMetrics().density * var2 * 5.0F);
      }

   }

   @ReactProp(
      name = "accessibilityComponentType"
   )
   public void setAccessibilityComponentType(T var1, String var2) {
      AccessibilityHelper.updateAccessibilityComponentType(var1, var2);
   }

   @ReactProp(
      name = "accessibilityLabel"
   )
   public void setAccessibilityLabel(T var1, String var2) {
      var1.setContentDescription(var2);
   }

   @ReactProp(
      name = "accessibilityLiveRegion"
   )
   public void setAccessibilityLiveRegion(T var1, String var2) {
      if(VERSION.SDK_INT >= 19) {
         if(var2 != null && !var2.equals("none")) {
            if(var2.equals("polite")) {
               var1.setAccessibilityLiveRegion(1);
               return;
            }

            if(var2.equals("assertive")) {
               var1.setAccessibilityLiveRegion(2);
               return;
            }
         } else {
            var1.setAccessibilityLiveRegion(0);
         }
      }

   }

   @ReactProp(
      customType = "Color",
      defaultInt = 0,
      name = "backgroundColor"
   )
   public void setBackgroundColor(T var1, int var2) {
      var1.setBackgroundColor(var2);
   }

   @ReactProp(
      name = "elevation"
   )
   public void setElevation(T var1, float var2) {
      if(VERSION.SDK_INT >= 21) {
         var1.setElevation(PixelUtil.toPixelFromDIP(var2));
      }

   }

   @ReactProp(
      name = "importantForAccessibility"
   )
   public void setImportantForAccessibility(T var1, String var2) {
      if(var2 != null && !var2.equals("auto")) {
         if(var2.equals("yes")) {
            var1.setImportantForAccessibility(1);
            return;
         }

         if(var2.equals("no")) {
            var1.setImportantForAccessibility(2);
            return;
         }

         if(var2.equals("no-hide-descendants")) {
            var1.setImportantForAccessibility(4);
            return;
         }
      } else {
         var1.setImportantForAccessibility(0);
      }

   }

   @ReactProp(
      name = "nativeID"
   )
   public void setNativeId(T var1, String var2) {
      var1.setTag(R.id.view_tag_native_id, var2);
      ReactFindViewUtil.notifyViewRendered(var1);
   }

   @ReactProp(
      defaultFloat = 1.0F,
      name = "opacity"
   )
   public void setOpacity(T var1, float var2) {
      var1.setAlpha(var2);
   }

   @ReactProp(
      name = "renderToHardwareTextureAndroid"
   )
   public void setRenderToHardwareTexture(T var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 2;
      } else {
         var3 = 0;
      }

      var1.setLayerType(var3, (Paint)null);
   }

   @ReactProp(
      name = "rotation"
   )
   @Deprecated
   public void setRotation(T var1, float var2) {
      var1.setRotation(var2);
   }

   @ReactProp(
      defaultFloat = 1.0F,
      name = "scaleX"
   )
   @Deprecated
   public void setScaleX(T var1, float var2) {
      var1.setScaleX(var2);
   }

   @ReactProp(
      defaultFloat = 1.0F,
      name = "scaleY"
   )
   @Deprecated
   public void setScaleY(T var1, float var2) {
      var1.setScaleY(var2);
   }

   @ReactProp(
      name = "testID"
   )
   public void setTestId(T var1, String var2) {
      var1.setTag(R.id.react_test_id, var2);
      var1.setTag(var2);
   }

   @ReactProp(
      name = "transform"
   )
   public void setTransform(T var1, ReadableArray var2) {
      if(var2 == null) {
         resetTransformProperty(var1);
      } else {
         setTransformProperty(var1, var2);
      }
   }

   @ReactProp(
      defaultFloat = 0.0F,
      name = "translateX"
   )
   @Deprecated
   public void setTranslateX(T var1, float var2) {
      var1.setTranslationX(PixelUtil.toPixelFromDIP(var2));
   }

   @ReactProp(
      defaultFloat = 0.0F,
      name = "translateY"
   )
   @Deprecated
   public void setTranslateY(T var1, float var2) {
      var1.setTranslationY(PixelUtil.toPixelFromDIP(var2));
   }

   @ReactProp(
      name = "zIndex"
   )
   public void setZIndex(T var1, float var2) {
      ViewGroupManager.setViewZIndex(var1, Math.round(var2));
      ViewParent var3 = var1.getParent();
      if(var3 != null && var3 instanceof ReactZIndexedViewGroup) {
         ((ReactZIndexedViewGroup)var3).updateDrawingOrder();
      }

   }
}
