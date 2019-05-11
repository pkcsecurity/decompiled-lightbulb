package com.facebook.litho.widget;

import android.view.ViewGroup;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.EventHandler;
import com.facebook.litho.Size;
import com.facebook.litho.widget.ReMeasureEvent;
import com.facebook.litho.widget.ViewportInfo;
import javax.annotation.Nullable;

public interface Binder<V extends ViewGroup> {

   void bind(V var1);

   boolean canMeasure();

   ComponentTree getComponentAt(int var1);

   boolean isWrapContent();

   void measure(Size var1, int var2, int var3, EventHandler<ReMeasureEvent> var4);

   void mount(V var1);

   void setCanMeasure(boolean var1);

   void setSize(int var1, int var2);

   void setViewportChangedListener(@Nullable ViewportInfo.ViewportChanged var1);

   void unbind(V var1);

   void unmount(V var1);
}
