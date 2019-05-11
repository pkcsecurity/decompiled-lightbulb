package com.facebook.litho.reference;

import android.graphics.drawable.Drawable;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.drawable.DefaultComparableDrawable;
import com.facebook.litho.reference.DrawableReferenceLifecycle;
import com.facebook.litho.reference.Reference;
import javax.annotation.Nullable;

public final class DrawableReference extends Reference<ComparableDrawable> {

   final ComparableDrawable mDrawable;


   private DrawableReference(ComparableDrawable var1) {
      DrawableReferenceLifecycle var2;
      if(ComponentsConfiguration.isDrawableReferenceNonSynchronized) {
         var2 = DrawableReferenceLifecycle.get();
      } else {
         var2 = DrawableReferenceLifecycle.syncGet();
      }

      super(var2);
      this.mDrawable = var1;
   }

   public static Reference<ComparableDrawable> create(ComparableDrawable var0) {
      return new DrawableReference(var0);
   }

   private static boolean isDefaultComparableDrawableReference(Reference<? extends Drawable> var0) {
      return var0 instanceof DrawableReference && DefaultComparableDrawable.isDefaultComparableDrawable(((DrawableReference)var0).mDrawable);
   }

   public static boolean isEquivalentWithExperiment(@Nullable Reference<? extends Drawable> var0, @Nullable Reference<? extends Drawable> var1) {
      boolean var2 = false;
      if(var0 == null) {
         if(var1 == null) {
            var2 = true;
         }

         return var2;
      } else {
         return var1 == null?false:(ComponentsConfiguration.areDefaultComparableDrawablesAlwaysEquivalent && isDefaultComparableDrawableReference(var0) && isDefaultComparableDrawableReference(var1)?true:Reference.shouldUpdate(var0, var1) ^ true);
      }
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof DrawableReference)) {
         return false;
      } else {
         DrawableReference var2 = (DrawableReference)var1;
         return this.mDrawable == var2.mDrawable;
      }
   }

   public String getSimpleName() {
      return "DrawableReference";
   }

   public int hashCode() {
      return this.mDrawable != null?this.mDrawable.hashCode():0;
   }
}
