package com.facebook.litho;

import android.graphics.Rect;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.DiffNode;
import com.facebook.litho.LayoutState;
import com.facebook.litho.LithoView;
import com.facebook.litho.StateHandler;
import com.facebook.litho.TestComponent;
import com.facebook.litho.TestComponentContext;
import com.facebook.litho.TreeProps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TestComponentTree extends ComponentTree {

   private TestComponentTree(ComponentTree.Builder var1) {
      super(var1);
   }

   // $FF: synthetic method
   TestComponentTree(ComponentTree.Builder var1, Object var2) {
      this(var1);
   }

   public static TestComponentTree.Builder create(ComponentContext var0, Component var1) {
      return new TestComponentTree.Builder(var0, var1, null);
   }

   private static List<Component> extractSubComponents(DiffNode var0) {
      if(var0 == null) {
         return Collections.emptyList();
      } else {
         ArrayList var1 = new ArrayList();
         if(var0.getChildCount() == 0) {
            if(var0.getComponent() != null && var0.getComponent() instanceof TestComponent) {
               var1.add(((TestComponent)var0.getComponent()).getWrappedComponent());
            }

            return var1;
         } else {
            Iterator var2 = var0.getChildren().iterator();

            while(var2.hasNext()) {
               var1.addAll(extractSubComponents((DiffNode)var2.next()));
            }

            return var1;
         }
      }
   }

   @VisibleForTesting
   public void attach() {
      super.attach();
   }

   protected LayoutState calculateLayoutState(ComponentContext var1, Component var2, int var3, int var4, boolean var5, @Nullable LayoutState var6, TreeProps var7, int var8, String var9) {
      return LayoutState.calculate(new TestComponentContext(ComponentContext.withComponentTree(new TestComponentContext(var1), this), new StateHandler()), var2, this.mId, var3, var4, var5, var6, true, false, var8, var9);
   }

   public List<Component> getSubComponents() {
      return extractSubComponents(this.getMainThreadLayoutState().getDiffTree());
   }

   @VisibleForTesting
   public void measure(int var1, int var2, int[] var3, boolean var4) {
      super.measure(var1, var2, var3, var4);
   }

   @VisibleForTesting
   public void mountComponent(Rect var1, boolean var2) {
      super.mountComponent(var1, var2);
   }

   @VisibleForTesting
   public void setLithoView(@NonNull LithoView var1) {
      super.setLithoView(var1);
   }

   public static class Builder extends ComponentTree.Builder {

      private Builder(ComponentContext var1, Component var2) {
         super(var1, var2);
      }

      // $FF: synthetic method
      Builder(ComponentContext var1, Component var2, Object var3) {
         this(var1, var2);
      }

      public TestComponentTree build() {
         return new TestComponentTree(this, null);
      }

      public TestComponentTree.Builder incrementalMount(boolean var1) {
         return (TestComponentTree.Builder)super.incrementalMount(var1);
      }

      public TestComponentTree.Builder layoutDiffing(boolean var1) {
         return (TestComponentTree.Builder)super.layoutDiffing(var1);
      }

      public TestComponentTree.Builder layoutThreadLooper(Looper var1) {
         return (TestComponentTree.Builder)super.layoutThreadLooper(var1);
      }
   }
}
