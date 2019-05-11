package com.facebook.litho.sections.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.widget.RecyclerBinderConfiguration;
import com.facebook.litho.sections.widget.RecyclerConfiguration;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.GridLayoutInfo;
import com.facebook.litho.widget.LayoutInfo;
import javax.annotation.Nullable;

public class GridRecyclerConfiguration<T extends Object & SectionTree.Target & Binder<RecyclerView>> implements RecyclerConfiguration {

   private final boolean mAllowMeasureOverride;
   private final int mNumColumns;
   private final int mOrientation;
   private final RecyclerBinderConfiguration mRecyclerBinderConfiguration;
   private final boolean mReverseLayout;


   @Deprecated
   public GridRecyclerConfiguration(int var1) {
      this(1, var1, false);
   }

   @Deprecated
   public GridRecyclerConfiguration(int var1, int var2, boolean var3) {
      this(var1, var2, var3, GridRecyclerConfiguration.Builder.RECYCLER_BINDER_CONFIGURATION);
   }

   @Deprecated
   public GridRecyclerConfiguration(int var1, int var2, boolean var3, RecyclerBinderConfiguration var4) {
      this(var1, var2, var3, var4, false);
   }

   @Deprecated
   public GridRecyclerConfiguration(int var1, int var2, boolean var3, RecyclerBinderConfiguration var4, boolean var5) {
      this.mOrientation = var1;
      this.mNumColumns = var2;
      this.mReverseLayout = var3;
      RecyclerBinderConfiguration var6 = var4;
      if(var4 == null) {
         var6 = GridRecyclerConfiguration.Builder.RECYCLER_BINDER_CONFIGURATION;
      }

      this.mRecyclerBinderConfiguration = var6;
      this.mAllowMeasureOverride = var5;
   }

   public static GridRecyclerConfiguration.Builder create() {
      return new GridRecyclerConfiguration.Builder();
   }

   @Deprecated
   public static GridRecyclerConfiguration createWithRecyclerBinderConfiguration(int var0, RecyclerBinderConfiguration var1) {
      return new GridRecyclerConfiguration(1, var0, false, var1);
   }

   public LayoutInfo getLayoutInfo(ComponentContext var1) {
      return new GridLayoutInfo(var1.getAndroidContext(), this.mNumColumns, this.mOrientation, this.mReverseLayout, this.mAllowMeasureOverride);
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
      private boolean mAllowMeasureOverride = false;
      private int mNumColumns = 2;
      private int mOrientation = 1;
      private RecyclerBinderConfiguration mRecyclerBinderConfiguration;
      private boolean mReverseLayout = false;


      Builder() {
         this.mRecyclerBinderConfiguration = RECYCLER_BINDER_CONFIGURATION;
      }

      public GridRecyclerConfiguration.Builder allowMeasureOverride(boolean var1) {
         this.mAllowMeasureOverride = var1;
         return this;
      }

      public GridRecyclerConfiguration build() {
         return new GridRecyclerConfiguration(this.mOrientation, this.mNumColumns, this.mReverseLayout, this.mRecyclerBinderConfiguration, this.mAllowMeasureOverride);
      }

      public GridRecyclerConfiguration.Builder numColumns(int var1) {
         this.mNumColumns = var1;
         return this;
      }

      public GridRecyclerConfiguration.Builder orientation(int var1) {
         this.mOrientation = var1;
         return this;
      }

      public GridRecyclerConfiguration.Builder recyclerBinderConfiguration(RecyclerBinderConfiguration var1) {
         this.mRecyclerBinderConfiguration = var1;
         return this;
      }

      public GridRecyclerConfiguration.Builder reverseLayout(boolean var1) {
         this.mReverseLayout = var1;
         return this;
      }
   }
}
