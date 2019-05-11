package com.facebook.litho;

import android.support.v4.util.Pools;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.Prop;
import java.util.BitSet;
import javax.annotation.Nullable;

public final class Wrapper extends Component {

   private static final Pools.SynchronizedPool<Wrapper.Builder> sBuilderPool = new Pools.SynchronizedPool(2);
   @Prop
   @Nullable
   Component delegate;


   private Wrapper() {
      super("Wrapper");
   }

   public static Wrapper.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Wrapper.Builder create(ComponentContext var0, int var1, int var2) {
      Wrapper.Builder var4 = (Wrapper.Builder)sBuilderPool.acquire();
      Wrapper.Builder var3 = var4;
      if(var4 == null) {
         var3 = new Wrapper.Builder();
      }

      var3.init(var0, var1, var2, new Wrapper());
      return var3;
   }

   protected boolean canResolve() {
      return true;
   }

   public boolean isEquivalentTo(Component var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            Wrapper var2 = (Wrapper)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.delegate != null) {
                  if(!this.delegate.equals(var2.delegate)) {
                     return false;
                  }
               } else if(var2.delegate != null) {
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
      return this;
   }

   protected ComponentLayout resolve(ComponentContext var1) {
      return this.delegate == null?ComponentContext.NULL_LAYOUT:var1.newLayoutBuilder(this.delegate, 0, 0);
   }

   public static class Builder extends Component.Builder<Wrapper.Builder> {

      private static final int REQUIRED_PROPS_COUNT = 1;
      private static final String[] REQUIRED_PROPS_NAMES = new String[]{"delegate"};
      private final BitSet mRequired = new BitSet(1);
      private Wrapper mWrapper;


      private void init(ComponentContext var1, int var2, int var3, Wrapper var4) {
         super.init(var1, var2, var3, var4);
         this.mWrapper = var4;
      }

      public Wrapper build() {
         checkArgs(1, this.mRequired, REQUIRED_PROPS_NAMES);
         Wrapper var1 = this.mWrapper;
         this.release();
         return var1;
      }

      public Wrapper.Builder delegate(Component var1) {
         this.mRequired.set(0);
         this.mWrapper.delegate = var1;
         return this;
      }

      public Wrapper.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mRequired.clear();
         this.mWrapper = null;
         Wrapper.sBuilderPool.release(this);
      }
   }
}
