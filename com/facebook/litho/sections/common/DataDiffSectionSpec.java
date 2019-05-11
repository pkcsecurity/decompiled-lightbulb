package com.facebook.litho.sections.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.ComponentsSystrace;
import com.facebook.litho.Diff;
import com.facebook.litho.EventHandler;
import com.facebook.litho.LogTreePopulator;
import com.facebook.litho.PerfEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.sections.ChangeSet;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.annotations.DiffSectionSpec;
import com.facebook.litho.sections.annotations.OnDiff;
import com.facebook.litho.sections.common.DataDiffSection;
import com.facebook.litho.sections.common.OnCheckIsSameContentEvent;
import com.facebook.litho.sections.common.OnCheckIsSameItemEvent;
import com.facebook.litho.sections.common.RenderEvent;
import com.facebook.litho.sections.config.SectionsConfiguration;
import com.facebook.litho.widget.RecyclerBinderUpdateCallback;
import com.facebook.litho.widget.RenderInfo;
import java.util.ArrayList;
import java.util.List;

@DiffSectionSpec(
   events = {OnCheckIsSameContentEvent.class, OnCheckIsSameItemEvent.class, RenderEvent.class}
)
public class DataDiffSectionSpec<T extends Object> {

   @PropDefault
   public static final Boolean trimHeadAndTail = Boolean.valueOf(false);
   @PropDefault
   public static final Boolean trimSameInstancesOnly = Boolean.valueOf(false);


   private static boolean isDetectMovesEnabled(@Nullable Diff<Boolean> var0) {
      return var0 == null || var0.getNext() == null || ((Boolean)var0.getNext()).booleanValue();
   }

   @OnDiff
   public static <T extends Object> void onCreateChangeSet(SectionContext var0, ChangeSet var1, @Prop Diff<List<T>> var2, 
      @Prop(
         optional = true
      ) @Nullable Diff<Boolean> var3, 
      @Prop(
         optional = true
      ) Diff<Boolean> var4, 
      @Prop(
         optional = true
      ) Diff<Boolean> var5) {
      List var10 = (List)var2.getPrevious();
      List var11 = (List)var2.getNext();
      int var6;
      if(var10 == null) {
         var6 = 0;
      } else {
         var6 = var10.size();
      }

      EventHandler var12 = DataDiffSection.getRenderEventHandler(var0);
      var10 = null;
      DataDiffSectionSpec.ComponentRenderer var19 = new DataDiffSectionSpec.ComponentRenderer(var12, null);
      DataDiffSectionSpec.DiffSectionOperationExecutor var13 = new DataDiffSectionSpec.DiffSectionOperationExecutor(var1, null);
      boolean var9 = ComponentsSystrace.isTracing();
      boolean var7;
      if(var4 != null && var4.getNext() != null) {
         var7 = ((Boolean)var4.getNext()).booleanValue();
      } else {
         var7 = SectionsConfiguration.trimDataDiffSectionHeadAndTail;
      }

      boolean var8;
      if(var5 != null && var5.getNext() != null) {
         var8 = ((Boolean)var5.getNext()).booleanValue();
      } else {
         var8 = SectionsConfiguration.trimSameInstancesOnly;
      }

      DataDiffSectionSpec.Callback var15 = DataDiffSectionSpec.Callback.acquire(var0, (List)var2.getPrevious(), (List)var2.getNext(), var7, var8);
      ComponentsLogger var18 = var0.getLogger();
      PerfEvent var14;
      if(var18 == null) {
         var14 = var10;
      } else {
         var14 = LogTreePopulator.populatePerfEventFromLogger(var0, var18, var18.newPerformanceEvent(12));
      }

      if(var9) {
         ComponentsSystrace.beginSection("DiffUtil.calculateDiff");
      }

      DiffUtil.DiffResult var17 = DiffUtil.calculateDiff(var15, isDetectMovesEnabled(var3));
      if(var9) {
         ComponentsSystrace.endSection();
      }

      if(var14 != null) {
         var18.logPerfEvent(var14);
      }

      RecyclerBinderUpdateCallback var16 = RecyclerBinderUpdateCallback.acquire(var6, var11, var19, var13, var15.getTrimmedHeadItemsCount());
      var17.dispatchUpdatesTo((ListUpdateCallback)var16);
      DataDiffSectionSpec.Callback.release(var15);
      var16.applyChangeset(var0);
      RecyclerBinderUpdateCallback.release(var16);
   }

   static class DiffSectionOperationExecutor implements RecyclerBinderUpdateCallback.OperationExecutor {

      private final ChangeSet mChangeSet;


      private DiffSectionOperationExecutor(ChangeSet var1) {
         this.mChangeSet = var1;
      }

      // $FF: synthetic method
      DiffSectionOperationExecutor(ChangeSet var1, Object var2) {
         this(var1);
      }

      private static List<RenderInfo> extractComponentInfos(int var0, List<RecyclerBinderUpdateCallback.ComponentContainer> var1) {
         ArrayList var3 = new ArrayList(var0);

         for(int var2 = 0; var2 < var0; ++var2) {
            var3.add(((RecyclerBinderUpdateCallback.ComponentContainer)var1.get(var2)).getRenderInfo());
         }

         return var3;
      }

      public void executeOperations(ComponentContext var1, List<RecyclerBinderUpdateCallback.Operation> var2) {
         int var5 = var2.size();

         for(int var3 = 0; var3 < var5; ++var3) {
            RecyclerBinderUpdateCallback.Operation var6 = (RecyclerBinderUpdateCallback.Operation)var2.get(var3);
            List var7 = var6.getComponentContainers();
            int var4;
            if(var7 == null) {
               var4 = 1;
            } else {
               var4 = var7.size();
            }

            switch(var6.getType()) {
            case 0:
               if(var4 == 1) {
                  this.mChangeSet.insert(var6.getIndex(), ((RecyclerBinderUpdateCallback.ComponentContainer)var7.get(0)).getRenderInfo(), var1.getTreePropsCopy());
               } else {
                  var7 = extractComponentInfos(var4, var7);
                  this.mChangeSet.insertRange(var6.getIndex(), var4, var7, var1.getTreePropsCopy());
               }
               break;
            case 1:
               if(var4 == 1) {
                  this.mChangeSet.update(var6.getIndex(), ((RecyclerBinderUpdateCallback.ComponentContainer)var7.get(0)).getRenderInfo(), var1.getTreePropsCopy());
               } else {
                  var7 = extractComponentInfos(var4, var7);
                  this.mChangeSet.updateRange(var6.getIndex(), var4, var7, var1.getTreePropsCopy());
               }
               break;
            case 2:
               var4 = var6.getToIndex();
               if(var4 == 1) {
                  this.mChangeSet.delete(var6.getIndex());
               } else {
                  this.mChangeSet.deleteRange(var6.getIndex(), var4);
               }
               break;
            case 3:
               this.mChangeSet.move(var6.getIndex(), var6.getToIndex());
            }
         }

      }
   }

   static class ComponentRenderer implements RecyclerBinderUpdateCallback.ComponentRenderer {

      private final EventHandler<RenderEvent> mRenderEventEventHandler;


      private ComponentRenderer(EventHandler<RenderEvent> var1) {
         this.mRenderEventEventHandler = var1;
      }

      // $FF: synthetic method
      ComponentRenderer(EventHandler var1, Object var2) {
         this(var1);
      }

      public RenderInfo render(Object var1, int var2) {
         return DataDiffSection.dispatchRenderEvent(this.mRenderEventEventHandler, var2, var1, (Bundle)null);
      }
   }

   @VisibleForTesting
   static class Callback<T extends Object> extends DiffUtil.Callback {

      private static final Pools.Pool<DataDiffSectionSpec.Callback> sCallbackPool = new Pools.SynchronizedPool(2);
      private EventHandler<OnCheckIsSameContentEvent> mIsSameContentEventHandler;
      private EventHandler<OnCheckIsSameItemEvent> mIsSameItemEventHandler;
      private List<T> mNextData;
      private List<T> mPreviousData;
      private SectionContext mSectionContext;
      private int mTrimmedHeadItemsCount;


      @VisibleForTesting
      static <T extends Object> DataDiffSectionSpec.Callback<T> acquire(SectionContext var0, List<T> var1, List<T> var2, boolean var3, boolean var4) {
         DataDiffSectionSpec.Callback var6 = (DataDiffSectionSpec.Callback)sCallbackPool.acquire();
         DataDiffSectionSpec.Callback var5 = var6;
         if(var6 == null) {
            var5 = new DataDiffSectionSpec.Callback();
         }

         var5.init(var0, var1, var2, var3, var4);
         return var5;
      }

      private boolean areContentsTheSame(T var1, T var2) {
         return var1 == var2?true:(this.mIsSameContentEventHandler != null?DataDiffSection.dispatchOnCheckIsSameContentEvent(this.mIsSameContentEventHandler, var1, var2).booleanValue():var1.equals(var2));
      }

      private boolean areItemsTheSame(T var1, T var2) {
         return var1 == var2?true:(this.mIsSameItemEventHandler != null?DataDiffSection.dispatchOnCheckIsSameItemEvent(this.mIsSameItemEventHandler, var1, var2).booleanValue():var1.equals(var2));
      }

      private static void release(DataDiffSectionSpec.Callback var0) {
         var0.mNextData = null;
         var0.mPreviousData = null;
         var0.mSectionContext = null;
         var0.mIsSameItemEventHandler = null;
         var0.mIsSameContentEventHandler = null;
         var0.mTrimmedHeadItemsCount = 0;
         sCallbackPool.release(var0);
      }

      private static <T extends Object> boolean shouldTrim(T var0, T var1, boolean var2, DataDiffSectionSpec.Callback var3) {
         return var0 == var1?true:(var2?false:var3.areItemsTheSame(var0, var1) && var3.areContentsTheSame(var0, var1));
      }

      static <T extends Object> Diff<List<T>> trimHeadAndTail(List<T> var0, List<T> var1, boolean var2, DataDiffSectionSpec.Callback<T> var3) {
         int var11 = var0.size();
         int var12 = var1.size();
         int var8 = var11 - 1;
         int var9 = var12 - 1;
         byte var10 = 0;
         int var4 = 0;

         int var5;
         int var6;
         int var7;
         while(true) {
            var5 = var8;
            var6 = var9;
            var7 = var10;
            if(var4 >= var11) {
               break;
            }

            var5 = var8;
            var6 = var9;
            var7 = var10;
            if(var4 >= var12) {
               break;
            }

            var5 = var8;
            var6 = var9;
            var7 = var10;
            if(!shouldTrim(var0.get(var4), var1.get(var4), var2, var3)) {
               break;
            }

            ++var4;
         }

         while(var5 > var4 && var6 > var4 && shouldTrim(var0.get(var5), var1.get(var6), var2, var3)) {
            --var5;
            --var6;
            ++var7;
         }

         var3.mTrimmedHeadItemsCount = var4;
         return var4 <= 0 && var7 <= 0?ComponentsPools.acquireDiff(var0, var1):ComponentsPools.acquireDiff(var0.subList(var4, var11 - var7), var1.subList(var4, var12 - var7));
      }

      public boolean areContentsTheSame(int var1, int var2) {
         return this.areContentsTheSame(this.mPreviousData.get(var1), this.mNextData.get(var2));
      }

      public boolean areItemsTheSame(int var1, int var2) {
         return this.areItemsTheSame(this.mPreviousData.get(var1), this.mNextData.get(var2));
      }

      public int getNewListSize() {
         return this.mNextData == null?0:this.mNextData.size();
      }

      public int getOldListSize() {
         return this.mPreviousData == null?0:this.mPreviousData.size();
      }

      @VisibleForTesting
      int getTrimmedHeadItemsCount() {
         return this.mTrimmedHeadItemsCount;
      }

      void init(SectionContext var1, List<T> var2, List<T> var3, boolean var4, boolean var5) {
         this.mSectionContext = var1;
         this.mIsSameItemEventHandler = DataDiffSection.getOnCheckIsSameItemEventHandler(this.mSectionContext);
         this.mIsSameContentEventHandler = DataDiffSection.getOnCheckIsSameContentEventHandler(this.mSectionContext);
         if(var4 && var2 != null) {
            Diff var6 = trimHeadAndTail(var2, var3, var5, this);
            this.mPreviousData = (List)var6.getPrevious();
            this.mNextData = (List)var6.getNext();
         } else {
            this.mPreviousData = var2;
            this.mNextData = var3;
         }
      }
   }
}
