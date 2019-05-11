package com.facebook.litho.sections.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.widget.RecyclerBinderConfiguration;
import com.facebook.litho.sections.widget.RecyclerConfiguration;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.LayoutInfo;
import com.facebook.litho.widget.StaggeredGridLayoutInfo;
import javax.annotation.Nullable;

public class StaggeredGridRecyclerConfiguration<T extends Object & SectionTree.Target & Binder<RecyclerView>> implements RecyclerConfiguration {

   private final int mGapStrategy;
   private final int mNumSpans;
   private final int mOrientation;
   private final RecyclerBinderConfiguration mRecyclerBinderConfiguration;
   private final boolean mReverseLayout;


   @Deprecated
   public StaggeredGridRecyclerConfiguration(int var1) {
      this(var1, 1, false);
   }

   @Deprecated
   public StaggeredGridRecyclerConfiguration(int var1, int var2, boolean var3) {
      this(var1, var2, var3, StaggeredGridRecyclerConfiguration.Builder.RECYCLER_BINDER_CONFIGURATION);
   }

   @Deprecated
   public StaggeredGridRecyclerConfiguration(int var1, int var2, boolean var3, int var4, RecyclerBinderConfiguration var5) {
      this.mNumSpans = var1;
      this.mOrientation = var2;
      this.mReverseLayout = var3;
      this.mGapStrategy = var4;
      RecyclerBinderConfiguration var6 = var5;
      if(var5 == null) {
         var6 = StaggeredGridRecyclerConfiguration.Builder.RECYCLER_BINDER_CONFIGURATION;
      }

      this.mRecyclerBinderConfiguration = var6;
   }

   @Deprecated
   public StaggeredGridRecyclerConfiguration(int var1, int var2, boolean var3, RecyclerBinderConfiguration var4) {
      this(var1, var2, var3, 0, var4);
   }

   public static StaggeredGridRecyclerConfiguration.Builder create() {
      return new StaggeredGridRecyclerConfiguration.Builder();
   }

   @Deprecated
   public static StaggeredGridRecyclerConfiguration createWithRecyclerBinderConfiguration(int var0, RecyclerBinderConfiguration var1) {
      return new StaggeredGridRecyclerConfiguration(var0, 1, false, 0, var1);
   }

   public LayoutInfo getLayoutInfo(ComponentContext var1) {
      return new StaggeredGridLayoutInfo(this.mNumSpans, this.mOrientation, this.mReverseLayout, this.mGapStrategy);
   }

   public int getOrientation() {
      return this.mOrientation;
   }

   public RecyclerBinderConfiguration getRecyclerBinderConfiguration() {
      return this.mRecyclerBinderConfiguration;
   }

   @Nullable
   public SnapHelper getSnapHelper() {
      return null;
   }

   public int getSnapMode() {
      return Integer.MIN_VALUE;
   }

   public static class Builder {

      static final RecyclerBinderConfiguration RECYCLER_BINDER_CONFIGURATION = RecyclerBinderConfiguration.create().build();
      private int mGapStrategy = 0;
      private int mNumSpans = 2;
      private int mOrientation = 1;
      private RecyclerBinderConfiguration mRecyclerBinderConfiguration;
      private boolean mReverseLayout = false;


      Builder() {
         this.mRecyclerBinderConfiguration = RECYCLER_BINDER_CONFIGURATION;
      }

      public StaggeredGridRecyclerConfiguration build() {
         return new StaggeredGridRecyclerConfiguration(this.mNumSpans, this.mOrientation, this.mReverseLayout, this.mGapStrategy, this.mRecyclerBinderConfiguration);
      }

      public StaggeredGridRecyclerConfiguration.Builder gapStrategy(int var1) {
         this.mGapStrategy = var1;
         return this;
      }

      public StaggeredGridRecyclerConfiguration.Builder numSpans(int var1) {
         this.mNumSpans = var1;
         return this;
      }

      public StaggeredGridRecyclerConfiguration.Builder orientation(int var1) {
         this.mOrientation = var1;
         return this;
      }

      public StaggeredGridRecyclerConfiguration.Builder recyclerBinderConfiguration(RecyclerBinderConfiguration var1) {
         this.mRecyclerBinderConfiguration = var1;
         return this;
      }

      public StaggeredGridRecyclerConfiguration.Builder reverseLayout(boolean var1) {
         this.mReverseLayout = var1;
         return this;
      }
   }
}
