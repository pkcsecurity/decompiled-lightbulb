package com.facebook.litho.widget;

import com.facebook.litho.LayoutHandler;
import com.facebook.litho.widget.RenderInfo;
import javax.annotation.Nullable;

public interface LayoutHandlerFactory {

   @Nullable
   LayoutHandler createLayoutCalculationHandler(RenderInfo var1);

   boolean shouldUpdateLayoutHandler(RenderInfo var1, RenderInfo var2);
}
