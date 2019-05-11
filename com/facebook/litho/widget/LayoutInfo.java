package com.facebook.litho.widget;

import android.support.v7.widget.RecyclerView;
import com.facebook.litho.widget.ComponentTreeHolder;
import com.facebook.litho.widget.RenderInfo;
import com.facebook.litho.widget.ViewportInfo;
import java.util.List;

public interface LayoutInfo extends ViewportInfo {

   int approximateRangeSize(int var1, int var2, int var3, int var4);

   int computeWrappedHeight(int var1, List<ComponentTreeHolder> var2);

   LayoutInfo.ViewportFiller createViewportFiller(int var1, int var2);

   int getChildHeightSpec(int var1, RenderInfo var2);

   int getChildWidthSpec(int var1, RenderInfo var2);

   RecyclerView.LayoutManager getLayoutManager();

   int getScrollDirection();

   void setRenderInfoCollection(LayoutInfo.RenderInfoCollection var1);

   public interface RenderInfoCollection {

      RenderInfo getRenderInfoAt(int var1);
   }

   public interface ViewportFiller {

      void add(RenderInfo var1, int var2, int var3);

      int getFill();

      boolean wantsMore();
   }
}
