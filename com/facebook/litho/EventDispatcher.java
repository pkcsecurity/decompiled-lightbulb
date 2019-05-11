package com.facebook.litho;

import com.facebook.litho.EventHandler;
import javax.annotation.Nullable;

public interface EventDispatcher {

   @Nullable
   Object dispatchOnEvent(EventHandler var1, Object var2);
}
