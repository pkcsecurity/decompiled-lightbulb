package com.facebook.react.views.art;

import android.view.View;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.views.art.ARTGroupShadowNode;
import com.facebook.react.views.art.ARTGroupViewManager;
import com.facebook.react.views.art.ARTShapeShadowNode;
import com.facebook.react.views.art.ARTShapeViewManager;
import com.facebook.react.views.art.ARTTextShadowNode;
import com.facebook.react.views.art.ARTTextViewManager;

public class ARTRenderableViewManager extends ViewManager<View, ReactShadowNode> {

   static final String CLASS_GROUP = "ARTGroup";
   static final String CLASS_SHAPE = "ARTShape";
   static final String CLASS_TEXT = "ARTText";
   private final String mClassName;


   ARTRenderableViewManager(String var1) {
      this.mClassName = var1;
   }

   public static ARTRenderableViewManager createARTGroupViewManager() {
      return new ARTGroupViewManager();
   }

   public static ARTRenderableViewManager createARTShapeViewManager() {
      return new ARTShapeViewManager();
   }

   public static ARTRenderableViewManager createARTTextViewManager() {
      return new ARTTextViewManager();
   }

   public ReactShadowNode createShadowNodeInstance() {
      if("ARTGroup".equals(this.mClassName)) {
         return new ARTGroupShadowNode();
      } else if("ARTShape".equals(this.mClassName)) {
         return new ARTShapeShadowNode();
      } else if("ARTText".equals(this.mClassName)) {
         return new ARTTextShadowNode();
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Unexpected type ");
         var1.append(this.mClassName);
         throw new IllegalStateException(var1.toString());
      }
   }

   protected View createViewInstance(ThemedReactContext var1) {
      throw new IllegalStateException("ARTShape does not map into a native view");
   }

   public String getName() {
      return this.mClassName;
   }

   public Class<? extends ReactShadowNode> getShadowNodeClass() {
      if("ARTGroup".equals(this.mClassName)) {
         return ARTGroupShadowNode.class;
      } else if("ARTShape".equals(this.mClassName)) {
         return ARTShapeShadowNode.class;
      } else if("ARTText".equals(this.mClassName)) {
         return ARTTextShadowNode.class;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Unexpected type ");
         var1.append(this.mClassName);
         throw new IllegalStateException(var1.toString());
      }
   }

   public void updateExtraData(View var1, Object var2) {
      throw new IllegalStateException("ARTShape does not map into a native view");
   }
}
