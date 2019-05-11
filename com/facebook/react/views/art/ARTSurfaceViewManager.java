package com.facebook.react.views.art;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.art.ARTSurfaceView;
import com.facebook.react.views.art.ARTSurfaceViewShadowNode;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;

@ReactModule(
   name = "ARTSurfaceView"
)
public class ARTSurfaceViewManager extends BaseViewManager<ARTSurfaceView, ARTSurfaceViewShadowNode> {

   private static final YogaMeasureFunction MEASURE_FUNCTION = new YogaMeasureFunction() {
      public long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5) {
         throw new IllegalStateException("SurfaceView should have explicit width and height set");
      }
   };
   protected static final String REACT_CLASS = "ARTSurfaceView";


   public ARTSurfaceViewShadowNode createShadowNodeInstance() {
      ARTSurfaceViewShadowNode var1 = new ARTSurfaceViewShadowNode();
      var1.setMeasureFunction(MEASURE_FUNCTION);
      return var1;
   }

   protected ARTSurfaceView createViewInstance(ThemedReactContext var1) {
      return new ARTSurfaceView(var1);
   }

   public String getName() {
      return "ARTSurfaceView";
   }

   public Class<ARTSurfaceViewShadowNode> getShadowNodeClass() {
      return ARTSurfaceViewShadowNode.class;
   }

   public void setBackgroundColor(ARTSurfaceView var1, int var2) {}

   public void updateExtraData(ARTSurfaceView var1, Object var2) {
      var1.setSurfaceTextureListener((ARTSurfaceViewShadowNode)var2);
   }
}
