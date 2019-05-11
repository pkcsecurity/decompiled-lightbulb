package com.facebook.litho;

import android.content.Context;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.PoolWithDebugInfo;

public interface MountContentPool<T extends Object> extends PoolWithDebugInfo {

   T acquire(Context var1, ComponentLifecycle var2);

   void maybePreallocateContent(Context var1, ComponentLifecycle var2);

   void release(T var1);
}
