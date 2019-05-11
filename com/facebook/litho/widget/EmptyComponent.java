package com.facebook.litho.widget;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.EmptyComponentSpec;

public final class EmptyComponent extends Component {

   private EmptyComponent() {
      super("EmptyComponent");
   }

   public static EmptyComponent.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static EmptyComponent.Builder create(ComponentContext var0, int var1, int var2) {
      EmptyComponent.Builder var3 = new EmptyComponent.Builder();
      var3.init(var0, var1, var2, new EmptyComponent());
      return var3;
   }

   public boolean isEquivalentTo(Component var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         EmptyComponent var2 = (EmptyComponent)var1;
         return this.getId() == var2.getId()?true:true;
      } else {
         return false;
      }
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return EmptyComponentSpec.onCreateLayout(var1);
   }

   public static class Builder extends Component.Builder<EmptyComponent.Builder> {

      ComponentContext mContext;
      EmptyComponent mEmptyComponent;


      private void init(ComponentContext var1, int var2, int var3, EmptyComponent var4) {
         super.init(var1, var2, var3, var4);
         this.mEmptyComponent = var4;
         this.mContext = var1;
      }

      public EmptyComponent build() {
         EmptyComponent var1 = this.mEmptyComponent;
         this.release();
         return var1;
      }

      public EmptyComponent.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mEmptyComponent = null;
         this.mContext = null;
      }
   }
}
