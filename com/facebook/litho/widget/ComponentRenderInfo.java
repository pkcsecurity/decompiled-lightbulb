package com.facebook.litho.widget;

import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.facebook.litho.RenderCompleteEvent;
import com.facebook.litho.widget.BaseRenderInfo;
import com.facebook.litho.widget.RenderInfo;

public class ComponentRenderInfo extends BaseRenderInfo {

   public static final String LAYOUT_DIFFING_ENABLED = "layout_diffing_enabled";
   private static final Pools.Pool<ComponentRenderInfo.Builder> sBuilderPool = new Pools.SynchronizedPool(2);
   private final Component mComponent;
   @Nullable
   private final EventHandler<RenderCompleteEvent> mRenderCompleteEventHandler;


   private ComponentRenderInfo(ComponentRenderInfo.Builder var1) {
      super(var1);
      if(var1.mComponent == null) {
         throw new IllegalStateException("Component must be provided.");
      } else {
         this.mComponent = var1.mComponent;
         this.mRenderCompleteEventHandler = var1.mRenderCompleteEventEventHandler;
      }
   }

   // $FF: synthetic method
   ComponentRenderInfo(ComponentRenderInfo.Builder var1, Object var2) {
      this(var1);
   }

   public static ComponentRenderInfo.Builder create() {
      ComponentRenderInfo.Builder var1 = (ComponentRenderInfo.Builder)sBuilderPool.acquire();
      ComponentRenderInfo.Builder var0 = var1;
      if(var1 == null) {
         var0 = new ComponentRenderInfo.Builder();
      }

      return var0;
   }

   public static RenderInfo createEmpty() {
      return create().component((Component)(new ComponentRenderInfo.EmptyComponent())).build();
   }

   public Component getComponent() {
      return this.mComponent;
   }

   public String getName() {
      return this.mComponent.getSimpleName();
   }

   @Nullable
   public EventHandler<RenderCompleteEvent> getRenderCompleteEventHandler() {
      return this.mRenderCompleteEventHandler;
   }

   public boolean rendersComponent() {
      return true;
   }

   public static class Builder extends BaseRenderInfo.Builder<ComponentRenderInfo.Builder> {

      private Component mComponent;
      private EventHandler<RenderCompleteEvent> mRenderCompleteEventEventHandler;


      public ComponentRenderInfo build() {
         ComponentRenderInfo var1 = new ComponentRenderInfo(this, null);
         this.release();
         return var1;
      }

      public ComponentRenderInfo.Builder component(Component.Builder var1) {
         return this.component(var1.build());
      }

      public ComponentRenderInfo.Builder component(Component var1) {
         this.mComponent = var1;
         return this;
      }

      void release() {
         super.release();
         this.mComponent = null;
         this.mRenderCompleteEventEventHandler = null;
         ComponentRenderInfo.sBuilderPool.release(this);
      }

      public ComponentRenderInfo.Builder renderCompleteHandler(EventHandler<RenderCompleteEvent> var1) {
         this.mRenderCompleteEventEventHandler = var1;
         return this;
      }
   }

   static class EmptyComponent extends Component {

      protected EmptyComponent() {
         super("EmptyComponent");
      }

      public boolean isEquivalentTo(Component var1) {
         return this == var1 || var1 != null && this.getClass() == var1.getClass();
      }

      protected Component onCreateLayout(ComponentContext var1) {
         return Column.create(var1).build();
      }
   }
}
