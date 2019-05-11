package com.facebook.litho;

import com.facebook.litho.Component;

class TestComponent extends Component {

   private final Component mWrappedComponent;


   TestComponent(Component var1) {
      super(var1.getSimpleName());
      this.mWrappedComponent = var1;
   }

   Component getWrappedComponent() {
      return this.mWrappedComponent;
   }

   public boolean isEquivalentTo(Component var1) {
      return this == var1;
   }
}
