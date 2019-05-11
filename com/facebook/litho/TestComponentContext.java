package com.facebook.litho;

import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.InternalNode;
import com.facebook.litho.StateHandler;
import com.facebook.litho.TestComponent;

class TestComponentContext extends ComponentContext {

   TestComponentContext(ComponentContext var1) {
      super(var1);
   }

   TestComponentContext(ComponentContext var1, StateHandler var2) {
      super(var1, var2);
   }

   TestComponentContext makeNewCopy() {
      return new TestComponentContext(this);
   }

   public InternalNode newLayoutBuilder(Component var1, @AttrRes int var2, @StyleRes int var3) {
      if(var1.canResolve()) {
         return super.newLayoutBuilder(var1, var2, var3);
      } else {
         InternalNode var4 = ComponentsPools.acquireInternalNode(this);
         var1.updateInternalChildState(this);
         var4.appendComponent(new TestComponent(var1));
         return var4;
      }
   }

   InternalNode resolveLayout(Component var1) {
      if(var1.canResolve()) {
         return super.resolveLayout(var1);
      } else {
         InternalNode var2 = ComponentsPools.acquireInternalNode(this);
         var2.appendComponent(new TestComponent(var1));
         return var2;
      }
   }
}
