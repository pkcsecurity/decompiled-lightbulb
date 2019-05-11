package com.facebook.litho.debug;

import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ThreadUtils;
import com.facebook.litho.Wrapper;
import com.facebook.litho.debug.DebugOverlayDrawable;
import java.util.ArrayList;
import java.util.List;

public class DebugOverlayController {

   private final List<Boolean> mCalculationOnMainThread = new ArrayList(10);


   public Component decorate(Component var1, ComponentContext var2) {
      return ((Column.Builder)Column.create(var2).foreground(new DebugOverlayDrawable(new ArrayList(this.mCalculationOnMainThread)))).child(Wrapper.create(var2).delegate(var1).flexGrow(1.0F)).build();
   }

   public void recordCalculationStart() {
      this.mCalculationOnMainThread.add(Boolean.valueOf(ThreadUtils.isMainThread()));
   }
}
