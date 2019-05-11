package com.facebook.litho.reference;

import android.content.Context;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.reference.DrawableReference;
import com.facebook.litho.reference.Reference;
import com.facebook.litho.reference.ReferenceLifecycle;

final class DrawableReferenceLifecycle extends ReferenceLifecycle<ComparableDrawable> {

   private static final DrawableReferenceLifecycle sInstance = new DrawableReferenceLifecycle();


   public static DrawableReferenceLifecycle get() {
      return sInstance;
   }

   public static DrawableReferenceLifecycle syncGet() {
      synchronized(DrawableReferenceLifecycle.class){}

      DrawableReferenceLifecycle var0;
      try {
         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   protected ComparableDrawable onAcquire(Context var1, Reference<ComparableDrawable> var2) {
      return ((DrawableReference)var2).mDrawable;
   }

   protected boolean shouldUpdate(Reference<ComparableDrawable> var1, Reference<ComparableDrawable> var2) {
      return ((DrawableReference)var1).mDrawable.isEquivalentTo(((DrawableReference)var2).mDrawable) ^ true;
   }
}
