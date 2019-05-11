package com.facebook.litho.reference;

import android.content.Context;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ResourceResolver;
import com.facebook.litho.reference.ReferenceLifecycle;

@Deprecated
public abstract class Reference<L extends Object> {

   private final ReferenceLifecycle<L> mLifecycle;


   protected Reference(ReferenceLifecycle<L> var1) {
      this.mLifecycle = var1;
   }

   public static <T extends Object> T acquire(Context var0, Reference<T> var1) {
      return var1.mLifecycle.onAcquire(var0, var1);
   }

   public static <T extends Object> void release(Context var0, T var1, Reference<T> var2) {
      var2.mLifecycle.onRelease(var0, var1, var2);
   }

   public static boolean shouldUpdate(Reference var0, Reference var1) {
      return var0 != null?var0.mLifecycle.shouldReferenceUpdate(var0, var1):var1 != null;
   }

   public abstract String getSimpleName();

   public abstract static class Builder<L extends Object> {

      protected ResourceResolver mResourceResolver;


      public abstract Reference<L> build();

      public final void init(ComponentContext var1, Reference<L> var2) {
         this.mResourceResolver = new ResourceResolver(var1);
      }

      protected void release() {
         this.mResourceResolver = null;
      }
   }
}
