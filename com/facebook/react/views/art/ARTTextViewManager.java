package com.facebook.react.views.art;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.views.art.ARTRenderableViewManager;

@ReactModule(
   name = "ARTText"
)
public class ARTTextViewManager extends ARTRenderableViewManager {

   ARTTextViewManager() {
      super("ARTText");
   }
}
