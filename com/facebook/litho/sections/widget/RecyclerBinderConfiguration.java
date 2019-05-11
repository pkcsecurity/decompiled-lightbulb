package com.facebook.litho.sections.widget;

import android.support.annotation.Nullable;
import com.facebook.litho.ComponentLogParams;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.config.LayoutThreadPoolConfiguration;
import com.facebook.litho.sections.config.SectionsConfiguration;
import com.facebook.litho.widget.LayoutHandlerFactory;
import java.util.List;

public class RecyclerBinderConfiguration {

   private boolean mAsyncInitRange;
   private final boolean mCanPrefetchDisplayLists;
   private boolean mEnableStableIds;
   private boolean mHScrollAsyncMode;
   private boolean mHasDynamicItemHeight;
   @Nullable
   private List<ComponentLogParams> mInvalidStateLogParamsList;
   private final boolean mIsCircular;
   private final boolean mIsWrapContent;
   @Nullable
   private final LayoutHandlerFactory mLayoutHandlerFactory;
   private final float mRangeRatio;
   @Nullable
   private String mSplitLayoutTag;
   private LayoutThreadPoolConfiguration mThreadPoolConfiguration;
   private boolean mUseBackgroundChangeSets;
   private boolean mUseSharedLayoutStateFuture;


   @Deprecated
   public RecyclerBinderConfiguration(double var1) {
      this(var1, (LayoutHandlerFactory)null, false);
   }

   @Deprecated
   public RecyclerBinderConfiguration(double var1, @Nullable LayoutHandlerFactory var3) {
      this(var1, var3, false);
   }

   @Deprecated
   public RecyclerBinderConfiguration(double var1, @Nullable LayoutHandlerFactory var3, boolean var4) {
      this(var1, var3, var4, false);
   }

   @Deprecated
   public RecyclerBinderConfiguration(double var1, @Nullable LayoutHandlerFactory var3, boolean var4, boolean var5) {
      this(var1, var3, var4, var5, false);
   }

   @Deprecated
   public RecyclerBinderConfiguration(double var1, @Nullable LayoutHandlerFactory var3, boolean var4, boolean var5, boolean var6) {
      this.mUseBackgroundChangeSets = SectionsConfiguration.useBackgroundChangeSets;
      this.mUseSharedLayoutStateFuture = ComponentsConfiguration.useSharedLayoutStateFuture;
      this.mThreadPoolConfiguration = ComponentsConfiguration.threadPoolConfiguration;
      this.mAsyncInitRange = ComponentsConfiguration.asyncInitRange;
      float var7;
      if(var1 > 0.0D) {
         var7 = (float)var1;
      } else {
         var7 = 4.0F;
      }

      this.mRangeRatio = var7;
      this.mLayoutHandlerFactory = var3;
      this.mCanPrefetchDisplayLists = var4;
      this.mIsCircular = var5;
      this.mIsWrapContent = var6;
   }

   RecyclerBinderConfiguration(float var1, @Nullable LayoutHandlerFactory var2, boolean var3, boolean var4, boolean var5, @Nullable List<ComponentLogParams> var6, @Nullable String var7, LayoutThreadPoolConfiguration var8, boolean var9, boolean var10, boolean var11, boolean var12, boolean var13, boolean var14) {
      this.mUseBackgroundChangeSets = SectionsConfiguration.useBackgroundChangeSets;
      this.mUseSharedLayoutStateFuture = ComponentsConfiguration.useSharedLayoutStateFuture;
      this.mThreadPoolConfiguration = ComponentsConfiguration.threadPoolConfiguration;
      this.mAsyncInitRange = ComponentsConfiguration.asyncInitRange;
      this.mRangeRatio = var1;
      this.mLayoutHandlerFactory = var2;
      this.mCanPrefetchDisplayLists = var3;
      this.mIsCircular = var4;
      this.mIsWrapContent = var5;
      this.mInvalidStateLogParamsList = var6;
      this.mSplitLayoutTag = var7;
      this.mThreadPoolConfiguration = var8;
      this.mHasDynamicItemHeight = var9;
      this.mUseBackgroundChangeSets = var10;
      this.mHScrollAsyncMode = var11;
      this.mEnableStableIds = var12;
      this.mUseSharedLayoutStateFuture = var13;
      this.mAsyncInitRange = var14;
   }

   public static RecyclerBinderConfiguration.Builder create() {
      return new RecyclerBinderConfiguration.Builder();
   }

   public boolean canPrefetchDisplayLists() {
      return this.mCanPrefetchDisplayLists;
   }

   public boolean getAsyncInitRange() {
      return this.mAsyncInitRange;
   }

   public boolean getEnableStableIds() {
      return this.mEnableStableIds;
   }

   public boolean getHScrollAsyncMode() {
      return this.mHScrollAsyncMode;
   }

   @Nullable
   public List<ComponentLogParams> getInvalidStateLogParamsList() {
      return this.mInvalidStateLogParamsList;
   }

   @Nullable
   public LayoutHandlerFactory getLayoutHandlerFactory() {
      return this.mLayoutHandlerFactory;
   }

   public float getRangeRatio() {
      return this.mRangeRatio;
   }

   public String getSplitLayoutTag() {
      return this.mSplitLayoutTag;
   }

   public LayoutThreadPoolConfiguration getThreadPoolConfiguration() {
      return this.mThreadPoolConfiguration;
   }

   public boolean getUseBackgroundChangeSets() {
      return this.mUseBackgroundChangeSets;
   }

   public boolean getUseSharedLayoutStateFuture() {
      return this.mUseSharedLayoutStateFuture;
   }

   boolean hasDynamicItemHeight() {
      return this.mHasDynamicItemHeight;
   }

   public boolean isCircular() {
      return this.mIsCircular;
   }

   public boolean isWrapContent() {
      return this.mIsWrapContent;
   }

   public static class Builder {

      static final float DEFAULT_RANGE = 4.0F;
      public static final LayoutThreadPoolConfiguration DEFAULT_THREAD_POOL_CONFIG = ComponentsConfiguration.threadPoolConfiguration;
      private boolean mAsyncInitRange;
      private boolean mCanPrefetchDisplayLists;
      private boolean mCircular;
      private boolean mDynamicItemHeight;
      private boolean mEnableStableIds;
      private boolean mHScrollAsyncMode;
      @Nullable
      private List<ComponentLogParams> mInvalidStateLogParamsList;
      @Nullable
      private LayoutHandlerFactory mLayoutHandlerFactory;
      private float mRangeRatio;
      @Nullable
      private String mSplitLayoutTag;
      private LayoutThreadPoolConfiguration mThreadPoolConfiguration;
      private boolean mUseBackgroundChangeSets;
      private boolean mUseSharedLayoutStateFuture;
      private boolean mWrapContent;


      Builder() {
         this.mThreadPoolConfiguration = DEFAULT_THREAD_POOL_CONFIG;
         this.mRangeRatio = 4.0F;
         this.mCanPrefetchDisplayLists = false;
         this.mCircular = false;
         this.mWrapContent = false;
         this.mDynamicItemHeight = false;
         this.mHScrollAsyncMode = false;
         this.mEnableStableIds = false;
         this.mUseBackgroundChangeSets = SectionsConfiguration.useBackgroundChangeSets;
         this.mUseSharedLayoutStateFuture = ComponentsConfiguration.useSharedLayoutStateFuture;
         this.mAsyncInitRange = ComponentsConfiguration.asyncInitRange;
      }

      public RecyclerBinderConfiguration.Builder asyncInitRange(boolean var1) {
         this.mAsyncInitRange = var1;
         return this;
      }

      public RecyclerBinderConfiguration build() {
         return new RecyclerBinderConfiguration(this.mRangeRatio, this.mLayoutHandlerFactory, this.mCanPrefetchDisplayLists, this.mCircular, this.mWrapContent, this.mInvalidStateLogParamsList, this.mSplitLayoutTag, this.mThreadPoolConfiguration, this.mDynamicItemHeight, this.mUseBackgroundChangeSets, this.mHScrollAsyncMode, this.mEnableStableIds, this.mUseSharedLayoutStateFuture, this.mAsyncInitRange);
      }

      public RecyclerBinderConfiguration.Builder canPrefetchDisplayLists(boolean var1) {
         this.mCanPrefetchDisplayLists = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder enableStableIds(boolean var1) {
         this.mEnableStableIds = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder hScrollAsyncMode(boolean var1) {
         this.mHScrollAsyncMode = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder hasDynamicItemHeight(boolean var1) {
         this.mDynamicItemHeight = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder idleExecutor(@Nullable LayoutHandlerFactory var1) {
         this.mLayoutHandlerFactory = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder invalidStateLogParamsList(@Nullable List<ComponentLogParams> var1) {
         this.mInvalidStateLogParamsList = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder isCircular(boolean var1) {
         this.mCircular = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder rangeRatio(float var1) {
         if(var1 <= 0.0F) {
            var1 = 4.0F;
         }

         this.mRangeRatio = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder splitLayoutTag(@Nullable String var1) {
         this.mSplitLayoutTag = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder threadPoolConfiguration(@Nullable LayoutThreadPoolConfiguration var1) {
         if(var1 != null) {
            this.mThreadPoolConfiguration = var1;
            return this;
         } else {
            this.mThreadPoolConfiguration = DEFAULT_THREAD_POOL_CONFIG;
            return this;
         }
      }

      public RecyclerBinderConfiguration.Builder useBackgroundChangeSets(boolean var1) {
         this.mUseBackgroundChangeSets = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder useSharedLayoutStateFuture(boolean var1) {
         this.mUseSharedLayoutStateFuture = var1;
         return this;
      }

      public RecyclerBinderConfiguration.Builder wrapContent(boolean var1) {
         this.mWrapContent = var1;
         return this;
      }
   }
}
