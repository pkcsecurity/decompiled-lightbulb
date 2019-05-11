package com.facebook.litho;

import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;

class PlaceholderComponent extends Component {

   protected PlaceholderComponent() {
      super("PlaceholderComponent");
   }

   public static PlaceholderComponent createAndBuild() {
      return new PlaceholderComponent();
   }

   protected boolean canResolve() {
      return true;
   }

   public boolean isEquivalentTo(Component var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         Column var2 = (Column)var1;
         return this.getId() == var2.getId()?true:true;
      } else {
         return false;
      }
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return this;
   }

   protected ComponentLayout resolve(ComponentContext var1) {
      return var1.newLayoutBuilder(0, 0);
   }
}
