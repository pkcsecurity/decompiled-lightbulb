package com.facebook.litho.widget;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.ComponentCreator;
import com.facebook.litho.widget.LazySelectorComponentSpec;
import java.util.ArrayList;
import java.util.List;

public final class LazySelectorComponent extends Component {

   @Comparable(
      type = 5
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   List<ComponentCreator> components;


   private LazySelectorComponent() {
      super("LazySelectorComponent");
   }

   public static LazySelectorComponent.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static LazySelectorComponent.Builder create(ComponentContext var0, int var1, int var2) {
      LazySelectorComponent.Builder var3 = new LazySelectorComponent.Builder();
      var3.init(var0, var1, var2, new LazySelectorComponent());
      return var3;
   }

   public boolean isEquivalentTo(Component var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            LazySelectorComponent var2 = (LazySelectorComponent)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.components != null) {
                  if(!this.components.equals(var2.components)) {
                     return false;
                  }
               } else if(var2.components != null) {
                  return false;
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return LazySelectorComponentSpec.onCreateLayout(var1, this.components);
   }

   public static class Builder extends Component.Builder<LazySelectorComponent.Builder> {

      ComponentContext mContext;
      LazySelectorComponent mLazySelectorComponent;


      private void init(ComponentContext var1, int var2, int var3, LazySelectorComponent var4) {
         super.init(var1, var2, var3, var4);
         this.mLazySelectorComponent = var4;
         this.mContext = var1;
      }

      public LazySelectorComponent build() {
         LazySelectorComponent var1 = this.mLazySelectorComponent;
         this.release();
         return var1;
      }

      public LazySelectorComponent.Builder component(ComponentCreator var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mLazySelectorComponent.components == null) {
               this.mLazySelectorComponent.components = new ArrayList();
            }

            this.mLazySelectorComponent.components.add(var1);
            return this;
         }
      }

      public LazySelectorComponent.Builder components(List<ComponentCreator> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mLazySelectorComponent.components != null && !this.mLazySelectorComponent.components.isEmpty()) {
            this.mLazySelectorComponent.components.addAll(var1);
            return this;
         } else {
            this.mLazySelectorComponent.components = var1;
            return this;
         }
      }

      public LazySelectorComponent.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mLazySelectorComponent = null;
         this.mContext = null;
      }
   }
}
