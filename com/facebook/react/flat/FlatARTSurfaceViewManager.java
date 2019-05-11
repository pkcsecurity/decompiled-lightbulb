package com.facebook.react.flat;

import com.facebook.react.flat.FlatARTSurfaceViewShadowNode;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.art.ARTSurfaceView;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;

public class FlatARTSurfaceViewManager extends BaseViewManager<ARTSurfaceView, FlatARTSurfaceViewShadowNode> {

   private static final YogaMeasureFunction MEASURE_FUNCTION = new YogaMeasureFunction() {
      public long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5) {
         throw new IllegalStateException("SurfaceView should have explicit width and height set");
      }
   };
   static final String REACT_CLASS = "ARTSurfaceView";


   public FlatARTSurfaceViewShadowNode createShadowNodeInstance() {
      FlatARTSurfaceViewShadowNode var1 = new FlatARTSurfaceViewShadowNode();
      var1.setMeasureFunction(MEASURE_FUNCTION);
      return var1;
   }

   protected ARTSurfaceView createViewInstance(ThemedReactContext var1) {
      return new ARTSurfaceView(var1);
   }

   public String getName() {
      return "ARTSurfaceView";
   }

   public Class<FlatARTSurfaceViewShadowNode> getShadowNodeClass() {
      return FlatARTSurfaceViewShadowNode.class;
   }

   public void updateExtraData(ARTSurfaceView var1, Object var2) {
      var1.setSurfaceTextureListener((FlatARTSurfaceViewShadowNode)var2);
   }
}
