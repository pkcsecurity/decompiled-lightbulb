package com.facebook.common.memory;

import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.references.ResourceReleaser;

public interface Pool<V extends Object> extends MemoryTrimmable, ResourceReleaser<V> {

   V get(int var1);

   void release(V var1);
}
