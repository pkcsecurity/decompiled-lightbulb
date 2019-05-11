package com.facebook.litho;

import android.content.Context;
import android.support.v4.util.Pools;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.Size;
import com.facebook.litho.viewcompat.ViewBinder;
import com.facebook.litho.viewcompat.ViewCreator;

@Deprecated
public class ViewCompatComponent<V extends View> extends Component {

   private static final int UNSPECIFIED_POOL_SIZE = -1;
   private static final Pools.SynchronizedPool<ViewCompatComponent.Builder> sBuilderPool = new Pools.SynchronizedPool(2);
   private int mPoolSize;
   private ViewBinder<V> mViewBinder;
   private final ViewCreator mViewCreator;


   private ViewCompatComponent(ViewCreator var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("ViewCompatComponent_");
      var3.append(var2);
      super(var3.toString(), Integer.valueOf(System.identityHashCode(var1)));
      this.mPoolSize = -1;
      this.mViewCreator = var1;
   }

   public static <V extends View> ViewCompatComponent<V> get(ViewCreator<V> var0, String var1) {
      return new ViewCompatComponent(var0, var1);
   }

   void bind(ComponentContext var1, Object var2) {
      this.mViewBinder.bind((View)var2);
   }

   protected boolean canMeasure() {
      return true;
   }

   public ViewCompatComponent.Builder<V> create(ComponentContext var1) {
      ViewCompatComponent.Builder var3 = (ViewCompatComponent.Builder)sBuilderPool.acquire();
      ViewCompatComponent.Builder var2 = var3;
      if(var3 == null) {
         var2 = new ViewCompatComponent.Builder();
      }

      var2.init(var1, this);
      return var2;
   }

   public V createMountContent(Context var1) {
      return this.mViewCreator.createView(var1, (ViewGroup)null);
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.VIEW;
   }

   public boolean isEquivalentTo(Component var1) {
      return this == var1;
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      View var6 = (View)ComponentsPools.acquireMountContent(var1.getAndroidContext(), this);
      var6.setLayoutParams(new LayoutParams(var5.width, var5.height));
      this.mViewBinder.bind(var6);
      if(var6.getVisibility() == 8) {
         var5.width = 0;
         var5.height = 0;
      } else {
         var6.measure(var3, var4);
         var5.width = var6.getMeasuredWidth();
         var5.height = var6.getMeasuredHeight();
      }

      this.mViewBinder.unbind(var6);
      ComponentsPools.release(var1.getAndroidContext(), this, var6);
   }

   protected void onPrepare(ComponentContext var1) {
      this.mViewBinder.prepare();
   }

   protected int poolSize() {
      return this.mPoolSize == -1?super.poolSize():this.mPoolSize;
   }

   void unbind(ComponentContext var1, Object var2) {
      this.mViewBinder.unbind((View)var2);
   }

   public static final class Builder<V extends View> extends Component.Builder<ViewCompatComponent.Builder<V>> {

      private ViewCompatComponent mViewCompatComponent;


      private void init(ComponentContext var1, ViewCompatComponent var2) {
         super.init(var1, 0, 0, var2);
         this.mViewCompatComponent = var2;
      }

      public ViewCompatComponent<V> build() {
         if(this.mViewCompatComponent.mViewBinder == null) {
            throw new IllegalStateException("To create a ViewCompatComponent you must provide a ViewBinder.");
         } else {
            ViewCompatComponent var1 = this.mViewCompatComponent;
            this.release();
            return var1;
         }
      }

      public ViewCompatComponent.Builder<V> contentPoolSize(int var1) {
         this.mViewCompatComponent.mPoolSize = var1;
         return this;
      }

      public ViewCompatComponent.Builder<V> getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mViewCompatComponent = null;
         ViewCompatComponent.sBuilderPool.release(this);
      }

      public ViewCompatComponent.Builder<V> viewBinder(ViewBinder<V> var1) {
         this.mViewCompatComponent.mViewBinder = var1;
         return this;
      }
   }
}
