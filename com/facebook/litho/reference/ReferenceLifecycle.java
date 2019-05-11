package com.facebook.litho.reference;

import android.content.Context;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.Diff;
import com.facebook.litho.reference.Reference;

@Deprecated
public abstract class ReferenceLifecycle<T extends Object> {

   protected final <R extends Object> Diff<R> acquireDiff(R var1, R var2) {
      return ComponentsPools.acquireDiff(var1, var2);
   }

   protected abstract T onAcquire(Context var1, Reference<T> var2);

   protected void onRelease(Context var1, T var2, Reference<T> var3) {}

   protected void releaseDiff(Diff var1) {
      ComponentsPools.release(var1);
   }

   public final boolean shouldReferenceUpdate(Reference<T> var1, Reference<T> var2) {
      return var1 == null?var2 != null:(var2 == null?true:(var1.getClass() != var2.getClass()?true:this.shouldUpdate(var1, var2)));
   }

   protected boolean shouldUpdate(Reference<T> var1, Reference<T> var2) {
      return var1.equals(var2) ^ true;
   }
}
