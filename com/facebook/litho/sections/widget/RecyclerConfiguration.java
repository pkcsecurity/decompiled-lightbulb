package com.facebook.litho.sections.widget;

import android.support.v7.widget.SnapHelper;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.sections.widget.RecyclerBinderConfiguration;
import com.facebook.litho.widget.LayoutInfo;
import javax.annotation.Nullable;

public interface RecyclerConfiguration {

   LayoutInfo getLayoutInfo(ComponentContext var1);

   int getOrientation();

   RecyclerBinderConfiguration getRecyclerBinderConfiguration();

   @Nullable
   SnapHelper getSnapHelper();

   int getSnapMode();
}
