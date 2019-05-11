package com.facebook.litho.widget;

import com.facebook.litho.Component;
import com.facebook.litho.EventHandler;
import com.facebook.litho.RenderCompleteEvent;
import com.facebook.litho.viewcompat.ViewBinder;
import com.facebook.litho.viewcompat.ViewCreator;
import javax.annotation.Nullable;

public interface RenderInfo {

   String CLIP_CHILDREN = "clip_children";


   void addDebugInfo(String var1, Object var2);

   Component getComponent();

   @Nullable
   Object getCustomAttribute(String var1);

   @Nullable
   Object getDebugInfo(String var1);

   String getName();

   @Nullable
   EventHandler<RenderCompleteEvent> getRenderCompleteEventHandler();

   int getSpanSize();

   ViewBinder getViewBinder();

   ViewCreator getViewCreator();

   int getViewType();

   boolean hasCustomViewType();

   boolean isFullSpan();

   boolean isSticky();

   boolean rendersComponent();

   boolean rendersView();

   void setViewType(int var1);
}
