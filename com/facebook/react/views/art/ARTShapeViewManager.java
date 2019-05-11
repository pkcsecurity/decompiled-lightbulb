package com.facebook.react.views.art;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.views.art.ARTRenderableViewManager;

@ReactModule(
   name = "ARTShape"
)
public class ARTShapeViewManager extends ARTRenderableViewManager {

   ARTShapeViewManager() {
      super("ARTShape");
   }
}
