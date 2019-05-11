package com.facebook.litho;

import android.support.v4.util.Pools;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.InternalNode;
import com.facebook.litho.SplitBackgroundLayoutConfiguration;
import com.facebook.litho.SplitLayoutResolver;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaWrap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public final class Row extends Component {

   private static final Pools.SynchronizedPool<Row.Builder> sBuilderPool = new Pools.SynchronizedPool(2);
   @Prop(
      optional = true
   )
   @Nullable
   private YogaAlign alignContent;
   @Prop(
      optional = true
   )
   @Nullable
   private YogaAlign alignItems;
   @Prop(
      optional = true
   )
   @Nullable
   List<Component> children;
   @Prop(
      optional = true
   )
   @Nullable
   private YogaJustify justifyContent;
   @Prop(
      optional = true
   )
   private boolean reverse;
   @Prop(
      optional = true
   )
   @Nullable
   private YogaWrap wrap;


   private Row() {
      super("Row");
   }

   public static Row.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Row.Builder create(ComponentContext var0, int var1, int var2) {
      Row.Builder var4 = (Row.Builder)sBuilderPool.acquire();
      Row.Builder var3 = var4;
      if(var4 == null) {
         var3 = new Row.Builder();
      }

      var3.init(var0, var1, var2, new Row());
      return var3;
   }

   protected boolean canResolve() {
      return true;
   }

   public boolean isEquivalentTo(Component var1) {
      if(this == var1) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         Row var4 = (Row)var1;
         if(this.getId() == var4.getId()) {
            return true;
         } else {
            if(this.children != null) {
               if(var4.children == null) {
                  return false;
               }

               if(this.children.size() != var4.children.size()) {
                  return false;
               }

               int var3 = this.children.size();

               for(int var2 = 0; var2 < var3; ++var2) {
                  if(!((Component)this.children.get(var2)).isEquivalentTo((Component)var4.children.get(var2))) {
                     return false;
                  }
               }
            } else if(var4.children != null) {
               return false;
            }

            if(this.alignItems != null) {
               if(!this.alignItems.equals(var4.alignItems)) {
                  return false;
               }
            } else if(var4.alignItems != null) {
               return false;
            }

            if(this.alignContent != null) {
               if(!this.alignContent.equals(var4.alignContent)) {
                  return false;
               }
            } else if(var4.alignContent != null) {
               return false;
            }

            if(this.justifyContent != null) {
               if(!this.justifyContent.equals(var4.justifyContent)) {
                  return false;
               }
            } else if(var4.justifyContent != null) {
               return false;
            }

            return this.reverse == var4.reverse;
         }
      }
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return this;
   }

   protected ComponentLayout resolve(ComponentContext var1) {
      boolean var2 = false;
      InternalNode var4 = var1.newLayoutBuilder(0, 0);
      if(!ComponentsConfiguration.enableSkipYogaPropExperiment || this.children != null) {
         YogaFlexDirection var3;
         if(this.reverse) {
            var3 = YogaFlexDirection.ROW_REVERSE;
         } else {
            var3 = YogaFlexDirection.ROW;
         }

         var4.flexDirection(var3);
      }

      if(this.alignItems != null) {
         var4.alignItems(this.alignItems);
      }

      if(this.alignContent != null) {
         var4.alignContent(this.alignContent);
      }

      if(this.justifyContent != null) {
         var4.justifyContent(this.justifyContent);
      }

      if(this.wrap != null) {
         var4.wrap(this.wrap);
      }

      if(this.children != null) {
         if(SplitBackgroundLayoutConfiguration.isSplitLayoutEnabled(this)) {
            var2 = SplitLayoutResolver.resolveLayouts(var1, this.children, var4);
         }

         if(!var2) {
            Iterator var5 = this.children.iterator();

            while(var5.hasNext()) {
               var4.child((Component)var5.next());
            }
         }
      }

      return var4;
   }

   public static class Builder extends Component.ContainerBuilder<Row.Builder> {

      ComponentContext mContext;
      Row mRow;


      private void init(ComponentContext var1, int var2, int var3, Row var4) {
         super.init(var1, var2, var3, var4);
         this.mRow = var4;
         this.mContext = var1;
      }

      public Row.Builder alignContent(YogaAlign var1) {
         this.mRow.alignContent = var1;
         return this;
      }

      public Row.Builder alignItems(YogaAlign var1) {
         this.mRow.alignItems = var1;
         return this;
      }

      public Row build() {
         Row var1 = this.mRow;
         this.release();
         return var1;
      }

      public Row.Builder child(@Nullable Component.Builder<?> var1) {
         return var1 == null?this:this.child(var1.build());
      }

      public Row.Builder child(@Nullable Component var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mRow.children == null) {
               this.mRow.children = new ArrayList();
            }

            this.mRow.children.add(var1);
            return this;
         }
      }

      public Row.Builder getThis() {
         return this;
      }

      public Row.Builder justifyContent(YogaJustify var1) {
         this.mRow.justifyContent = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mRow = null;
         this.mContext = null;
         Row.sBuilderPool.release(this);
      }

      public Row.Builder reverse(boolean var1) {
         this.mRow.reverse = var1;
         return this;
      }

      public Row.Builder wrap(YogaWrap var1) {
         this.mRow.wrap = var1;
         return this;
      }
   }
}
