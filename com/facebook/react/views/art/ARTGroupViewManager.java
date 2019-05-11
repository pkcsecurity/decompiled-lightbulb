package com.facebook.react.views.art;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.views.art.ARTRenderableViewManager;

@ReactModule(
   name = "ARTGroup"
)
public class ARTGroupViewManager extends ARTRenderableViewManager {

   ARTGroupViewManager() {
      super("ARTGroup");
   }
}
