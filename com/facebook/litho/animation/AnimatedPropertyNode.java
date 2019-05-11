package com.facebook.litho.animation;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.facebook.litho.OutputUnitsAffinityGroup;
import com.facebook.litho.animation.AnimatedProperty;
import com.facebook.litho.dataflow.ValueNode;
import java.lang.ref.WeakReference;
import javax.annotation.Nullable;

public class AnimatedPropertyNode extends ValueNode {

   private final AnimatedProperty mAnimatedProperty;
   private final OutputUnitsAffinityGroup<WeakReference<Object>> mMountContentGroup = new OutputUnitsAffinityGroup();
   private boolean mUsingRenderThread;


   public AnimatedPropertyNode(OutputUnitsAffinityGroup<Object> var1, AnimatedProperty var2) {
      this.setMountContentGroupInner(var1);
      this.mAnimatedProperty = var2;
   }

   @Nullable
   private static Object resolveReference(WeakReference<Object> var0) {
      Object var1;
      if(var0 != null) {
         var1 = var0.get();
      } else {
         var1 = null;
      }

      if(var1 == null) {
         return null;
      } else if(var1 instanceof Drawable && ((Drawable)var1).getCallback() == null) {
         var0.clear();
         return null;
      } else {
         return var1;
      }
   }

   private void setMountContentGroupInner(OutputUnitsAffinityGroup<Object> var1) {
      this.mMountContentGroup.clean();
      if(var1 != null) {
         int var2 = 0;

         for(int var3 = var1.size(); var2 < var3; ++var2) {
            WeakReference var4 = new WeakReference(var1.getAt(var2));
            this.mMountContentGroup.add(var1.typeAt(var2), var4);
         }

      }
   }

   private void setValueInner(float var1) {
      if(!this.mUsingRenderThread) {
         int var2 = 0;

         for(int var3 = this.mMountContentGroup.size(); var2 < var3; ++var2) {
            Object var4 = resolveReference((WeakReference)this.mMountContentGroup.getAt(var2));
            if(var4 != null) {
               this.mAnimatedProperty.set(var4, var1);
            }
         }

      }
   }

   public float calculateValue(long var1) {
      boolean var4 = this.hasInput();
      Object var5 = resolveReference((WeakReference)this.mMountContentGroup.getMostSignificantUnit());
      if(var5 == null) {
         return var4?this.getInput().getValue():this.getValue();
      } else if(!var4) {
         return this.mAnimatedProperty.get(var5);
      } else {
         float var3 = this.getInput().getValue();
         this.setValueInner(var3);
         return var3;
      }
   }

   @Nullable
   View getSingleTargetView() {
      int var2 = this.mMountContentGroup.size();
      int var1 = 0;

      View var3;
      for(var3 = null; var1 < var2; ++var1) {
         Object var4 = resolveReference((WeakReference)this.mMountContentGroup.getAt(var1));
         if(var4 != null) {
            if(var3 != null) {
               return null;
            }

            if(!(var4 instanceof View)) {
               return null;
            }

            var3 = (View)var4;
         }
      }

      return var3;
   }

   public void setMountContentGroup(OutputUnitsAffinityGroup<Object> var1) {
      this.setMountContentGroupInner(var1);
      this.setValueInner(this.getValue());
   }

   void setUsingRenderThread(boolean var1) {
      this.mUsingRenderThread = var1;
   }

   public void setValue(float var1) {
      super.setValue(var1);
      this.setValueInner(var1);
   }
}
