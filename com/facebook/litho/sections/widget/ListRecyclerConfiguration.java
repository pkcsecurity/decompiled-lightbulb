package com.facebook.litho.sections.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.widget.LinearLayoutInfoFactory;
import com.facebook.litho.sections.widget.RecyclerBinderConfiguration;
import com.facebook.litho.sections.widget.RecyclerConfiguration;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.LayoutInfo;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.SnapUtil;
import javax.annotation.Nullable;

public class ListRecyclerConfiguration<T extends Object & SectionTree.Target & Binder<RecyclerView>> implements RecyclerConfiguration {

   private final LinearLayoutInfoFactory mLinearLayoutInfoFactory;
   private final int mOrientation;
   private final RecyclerBinderConfiguration mRecyclerBinderConfiguration;
   private final boolean mReverseLayout;
   private final int mSnapMode;


   @Deprecated
   public ListRecyclerConfiguration() {
      this(1, false, Integer.MIN_VALUE);
   }

   @Deprecated
   public ListRecyclerConfiguration(int var1, boolean var2) {
      this(var1, var2, Integer.MIN_VALUE);
   }

   @Deprecated
   public ListRecyclerConfiguration(int var1, boolean var2, int var3) {
      this(var1, var2, var3, ListRecyclerConfiguration.Builder.RECYCLER_BINDER_CONFIGURATION);
   }

   @Deprecated
   public ListRecyclerConfiguration(int var1, boolean var2, int var3, RecyclerBinderConfiguration var4) {
      this(var1, var2, var3, var4, ListRecyclerConfiguration.Builder.LINEAR_LAYOUT_INFO_FACTORY);
   }

   @Deprecated
   public ListRecyclerConfiguration(int var1, boolean var2, int var3, @Nullable RecyclerBinderConfiguration var4, @Nullable LinearLayoutInfoFactory var5) {
      if(var1 == 1 && var3 != Integer.MIN_VALUE && var3 != -1) {
         throw new UnsupportedOperationException("Only snap to start is implemented for vertical lists");
      } else {
         this.mOrientation = var1;
         this.mReverseLayout = var2;
         this.mSnapMode = var3;
         RecyclerBinderConfiguration var6 = var4;
         if(var4 == null) {
            var6 = ListRecyclerConfiguration.Builder.RECYCLER_BINDER_CONFIGURATION;
         }

         this.mRecyclerBinderConfiguration = var6;
         LinearLayoutInfoFactory var7 = var5;
         if(var5 == null) {
            var7 = ListRecyclerConfiguration.Builder.LINEAR_LAYOUT_INFO_FACTORY;
         }

         this.mLinearLayoutInfoFactory = var7;
      }
   }

   public static ListRecyclerConfiguration.Builder create() {
      return new ListRecyclerConfiguration.Builder();
   }

   @Deprecated
   public static ListRecyclerConfiguration createWithRecyclerBinderConfiguration(RecyclerBinderConfiguration var0) {
      return new ListRecyclerConfiguration(1, false, Integer.MIN_VALUE, var0, ListRecyclerConfiguration.Builder.LINEAR_LAYOUT_INFO_FACTORY);
   }

   public LayoutInfo getLayoutInfo(ComponentContext var1) {
      return this.mLinearLayoutInfoFactory.createLinearLayoutInfo(var1.getAndroidContext(), this.mOrientation, this.mReverseLayout);
   }

   public int getOrientation() {
      return this.mOrientation;
   }

   public RecyclerBinderConfiguration getRecyclerBinderConfiguration() {
      return this.mRecyclerBinderConfiguration;
   }

   @Nullable
   public SnapHelper getSnapHelper() {
      return SnapUtil.getSnapHelper(this.mSnapMode);
   }

   public int getSnapMode() {
      return this.mSnapMode;
   }

   static class DefaultLinearLayoutInfoFactory implements LinearLayoutInfoFactory {

      private DefaultLinearLayoutInfoFactory() {}

      // $FF: synthetic method
      DefaultLinearLayoutInfoFactory(Object var1) {
         this();
      }

      public LinearLayoutInfo createLinearLayoutInfo(Context var1, int var2, boolean var3) {
         return new LinearLayoutInfo(var1, var2, var3);
      }
   }

   public static class Builder {

      static final LinearLayoutInfoFactory LINEAR_LAYOUT_INFO_FACTORY = new ListRecyclerConfiguration.DefaultLinearLayoutInfoFactory(null);
      static final RecyclerBinderConfiguration RECYCLER_BINDER_CONFIGURATION = RecyclerBinderConfiguration.create().build();
      private LinearLayoutInfoFactory mLinearLayoutInfoFactory;
      private int mOrientation = 1;
      private RecyclerBinderConfiguration mRecyclerBinderConfiguration;
      private boolean mReverseLayout = false;
      private int mSnapMode = Integer.MIN_VALUE;


      Builder() {
         this.mRecyclerBinderConfiguration = RECYCLER_BINDER_CONFIGURATION;
         this.mLinearLayoutInfoFactory = LINEAR_LAYOUT_INFO_FACTORY;
      }

      private static void validate(ListRecyclerConfiguration var0) {
         int var1 = var0.getSnapMode();
         if(var0.getOrientation() == 1 && var1 != Integer.MIN_VALUE && var1 != -1) {
            throw new UnsupportedOperationException("Only snap to start is implemented for vertical lists");
         }
      }

      public ListRecyclerConfiguration build() {
         ListRecyclerConfiguration var1 = new ListRecyclerConfiguration(this.mOrientation, this.mReverseLayout, this.mSnapMode, this.mRecyclerBinderConfiguration, this.mLinearLayoutInfoFactory);
         validate(var1);
         return var1;
      }

      public ListRecyclerConfiguration.Builder linearLayoutInfoFactory(LinearLayoutInfoFactory var1) {
         this.mLinearLayoutInfoFactory = var1;
         return this;
      }

      public ListRecyclerConfiguration.Builder orientation(int var1) {
         this.mOrientation = var1;
         return this;
      }

      public ListRecyclerConfiguration.Builder recyclerBinderConfiguration(RecyclerBinderConfiguration var1) {
         this.mRecyclerBinderConfiguration = var1;
         return this;
      }

      public ListRecyclerConfiguration.Builder reverseLayout(boolean var1) {
         this.mReverseLayout = var1;
         return this;
      }

      public ListRecyclerConfiguration.Builder snapMode(int var1) {
         this.mSnapMode = var1;
         return this;
      }
   }
}
