package com.facebook.drawee.backends.pipeline;

import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.Suppliers;
import com.facebook.drawee.backends.pipeline.DrawableFactory;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerFactory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class DraweeConfig {

   @Nullable
   private final ImmutableList<DrawableFactory> mCustomDrawableFactories;
   private final Supplier<Boolean> mDebugOverlayEnabledSupplier;
   @Nullable
   private final PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;


   private DraweeConfig(DraweeConfig.Builder var1) {
      ImmutableList var2;
      if(var1.mCustomDrawableFactories != null) {
         var2 = ImmutableList.copyOf(var1.mCustomDrawableFactories);
      } else {
         var2 = null;
      }

      this.mCustomDrawableFactories = var2;
      Supplier var3;
      if(var1.mDebugOverlayEnabledSupplier != null) {
         var3 = var1.mDebugOverlayEnabledSupplier;
      } else {
         var3 = Suppliers.of(Boolean.valueOf(false));
      }

      this.mDebugOverlayEnabledSupplier = var3;
      this.mPipelineDraweeControllerFactory = var1.mPipelineDraweeControllerFactory;
   }

   // $FF: synthetic method
   DraweeConfig(DraweeConfig.Builder var1, Object var2) {
      this(var1);
   }

   public static DraweeConfig.Builder newBuilder() {
      return new DraweeConfig.Builder();
   }

   @Nullable
   public ImmutableList<DrawableFactory> getCustomDrawableFactories() {
      return this.mCustomDrawableFactories;
   }

   public Supplier<Boolean> getDebugOverlayEnabledSupplier() {
      return this.mDebugOverlayEnabledSupplier;
   }

   @Nullable
   public PipelineDraweeControllerFactory getPipelineDraweeControllerFactory() {
      return this.mPipelineDraweeControllerFactory;
   }

   public static class Builder {

      private List<DrawableFactory> mCustomDrawableFactories;
      private Supplier<Boolean> mDebugOverlayEnabledSupplier;
      private PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;


      public DraweeConfig.Builder addCustomDrawableFactory(DrawableFactory var1) {
         if(this.mCustomDrawableFactories == null) {
            this.mCustomDrawableFactories = new ArrayList();
         }

         this.mCustomDrawableFactories.add(var1);
         return this;
      }

      public DraweeConfig build() {
         return new DraweeConfig(this, null);
      }

      public DraweeConfig.Builder setDebugOverlayEnabledSupplier(Supplier<Boolean> var1) {
         Preconditions.checkNotNull(var1);
         this.mDebugOverlayEnabledSupplier = var1;
         return this;
      }

      public DraweeConfig.Builder setDrawDebugOverlay(boolean var1) {
         return this.setDebugOverlayEnabledSupplier(Suppliers.of(Boolean.valueOf(var1)));
      }

      public DraweeConfig.Builder setPipelineDraweeControllerFactory(PipelineDraweeControllerFactory var1) {
         this.mPipelineDraweeControllerFactory = var1;
         return this;
      }
   }
}
