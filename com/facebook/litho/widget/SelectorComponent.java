package com.facebook.litho.widget;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.SelectorComponentSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SelectorComponent extends Component {

   @Comparable(
      type = 6
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   List<Component> components;


   private SelectorComponent() {
      super("SelectorComponent");
   }

   public static SelectorComponent.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static SelectorComponent.Builder create(ComponentContext var0, int var1, int var2) {
      SelectorComponent.Builder var3 = new SelectorComponent.Builder();
      var3.init(var0, var1, var2, new SelectorComponent());
      return var3;
   }

   public boolean isEquivalentTo(Component var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         SelectorComponent var2 = (SelectorComponent)var1;
         if(this.getId() == var2.getId()) {
            return true;
         } else {
            if(this.components != null) {
               if(var2.components == null) {
                  return false;
               }

               if(this.components.size() != var2.components.size()) {
                  return false;
               }

               Iterator var3 = this.components.iterator();
               Iterator var4 = var2.components.iterator();

               while(var3.hasNext() && var4.hasNext()) {
                  if(!((Component)var3.next()).isEquivalentTo((Component)var4.next())) {
                     return false;
                  }
               }
            } else if(var2.components != null) {
               return false;
            }

            return true;
         }
      }
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return SelectorComponentSpec.onCreateLayout(var1, this.components);
   }

   public static class Builder extends Component.Builder<SelectorComponent.Builder> {

      ComponentContext mContext;
      SelectorComponent mSelectorComponent;


      private void init(ComponentContext var1, int var2, int var3, SelectorComponent var4) {
         super.init(var1, var2, var3, var4);
         this.mSelectorComponent = var4;
         this.mContext = var1;
      }

      public SelectorComponent build() {
         SelectorComponent var1 = this.mSelectorComponent;
         this.release();
         return var1;
      }

      public SelectorComponent.Builder component(Component.Builder<?> var1) {
         if(var1 == null) {
            return this;
         } else {
            this.component(var1.build());
            return this;
         }
      }

      public SelectorComponent.Builder component(Component var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mSelectorComponent.components == null) {
               this.mSelectorComponent.components = new ArrayList();
            }

            this.mSelectorComponent.components.add(var1);
            return this;
         }
      }

      public SelectorComponent.Builder components(List<Component> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mSelectorComponent.components != null && !this.mSelectorComponent.components.isEmpty()) {
            this.mSelectorComponent.components.addAll(var1);
            return this;
         } else {
            this.mSelectorComponent.components = var1;
            return this;
         }
      }

      public SelectorComponent.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mSelectorComponent = null;
         this.mContext = null;
      }
   }
}
