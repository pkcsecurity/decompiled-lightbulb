package com.facebook.litho;

import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.EventTrigger;
import javax.annotation.Nullable;

@ThreadSafe
public interface EventTriggerTarget {

   @Nullable
   Object acceptTriggerEvent(EventTrigger var1, Object var2, Object[] var3);
}
