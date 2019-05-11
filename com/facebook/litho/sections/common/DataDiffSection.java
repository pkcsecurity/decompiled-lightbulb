package com.facebook.litho.sections.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import com.facebook.litho.Diff;
import com.facebook.litho.EventHandler;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.sections.ChangeSet;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.common.DataDiffSectionSpec;
import com.facebook.litho.sections.common.OnCheckIsSameContentEvent;
import com.facebook.litho.sections.common.OnCheckIsSameItemEvent;
import com.facebook.litho.sections.common.RenderEvent;
import com.facebook.litho.widget.RenderInfo;
import java.util.BitSet;
import java.util.List;

public final class DataDiffSection<T extends Object> extends Section {

   static final Pools.SynchronizedPool<OnCheckIsSameContentEvent> sOnCheckIsSameContentEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<OnCheckIsSameItemEvent> sOnCheckIsSameItemEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<RenderEvent> sRenderEventPool = new Pools.SynchronizedPool(2);
   @Comparable(
      type = 5
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   List<T> data;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   @Nullable
   Boolean detectMoves;
   EventHandler onCheckIsSameContentEventHandler;
   EventHandler onCheckIsSameItemEventHandler;
   EventHandler renderEventHandler;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Boolean trimHeadAndTail;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Boolean trimSameInstancesOnly;


   private DataDiffSection() {
      super("DataDiffSection");
      this.trimHeadAndTail = DataDiffSectionSpec.trimHeadAndTail;
      this.trimSameInstancesOnly = DataDiffSectionSpec.trimSameInstancesOnly;
   }

   public static <T extends Object> DataDiffSection.Builder<T> create(SectionContext var0) {
      DataDiffSection.Builder var1 = new DataDiffSection.Builder();
      var1.init(var0, new DataDiffSection());
      return var1;
   }

   static Boolean dispatchOnCheckIsSameContentEvent(EventHandler var0, Object var1, Object var2) {
      OnCheckIsSameContentEvent var4 = (OnCheckIsSameContentEvent)sOnCheckIsSameContentEventPool.acquire();
      OnCheckIsSameContentEvent var3 = var4;
      if(var4 == null) {
         var3 = new OnCheckIsSameContentEvent();
      }

      var3.previousItem = var1;
      var3.nextItem = var2;
      Boolean var5 = (Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var3);
      var3.previousItem = null;
      var3.nextItem = null;
      sOnCheckIsSameContentEventPool.release(var3);
      return var5;
   }

   static Boolean dispatchOnCheckIsSameItemEvent(EventHandler var0, Object var1, Object var2) {
      OnCheckIsSameItemEvent var4 = (OnCheckIsSameItemEvent)sOnCheckIsSameItemEventPool.acquire();
      OnCheckIsSameItemEvent var3 = var4;
      if(var4 == null) {
         var3 = new OnCheckIsSameItemEvent();
      }

      var3.previousItem = var1;
      var3.nextItem = var2;
      Boolean var5 = (Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var3);
      var3.previousItem = null;
      var3.nextItem = null;
      sOnCheckIsSameItemEventPool.release(var3);
      return var5;
   }

   static RenderInfo dispatchRenderEvent(EventHandler var0, int var1, Object var2, Bundle var3) {
      RenderEvent var5 = (RenderEvent)sRenderEventPool.acquire();
      RenderEvent var4 = var5;
      if(var5 == null) {
         var4 = new RenderEvent();
      }

      var4.index = var1;
      var4.model = var2;
      var4.loggingExtras = var3;
      RenderInfo var6 = (RenderInfo)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var4);
      var4.model = null;
      var4.loggingExtras = null;
      sRenderEventPool.release(var4);
      return var6;
   }

   public static EventHandler getOnCheckIsSameContentEventHandler(SectionContext var0) {
      return var0.getSectionScope() == null?null:((DataDiffSection)var0.getSectionScope()).onCheckIsSameContentEventHandler;
   }

   public static EventHandler getOnCheckIsSameItemEventHandler(SectionContext var0) {
      return var0.getSectionScope() == null?null:((DataDiffSection)var0.getSectionScope()).onCheckIsSameItemEventHandler;
   }

   public static EventHandler getRenderEventHandler(SectionContext var0) {
      return var0.getSectionScope() == null?null:((DataDiffSection)var0.getSectionScope()).renderEventHandler;
   }

   protected void generateChangeSet(SectionContext var1, ChangeSet var2, Section var3, Section var4) {
      DataDiffSection var7 = (DataDiffSection)var3;
      DataDiffSection var6 = (DataDiffSection)var4;
      Object var5 = null;
      List var11;
      if(var7 == null) {
         var11 = null;
      } else {
         var11 = var7.data;
      }

      List var12;
      if(var6 == null) {
         var12 = null;
      } else {
         var12 = var6.data;
      }

      Diff var8 = this.acquireDiff(var11, var12);
      Boolean var13;
      if(var7 == null) {
         var13 = null;
      } else {
         var13 = var7.detectMoves;
      }

      Boolean var14;
      if(var6 == null) {
         var14 = null;
      } else {
         var14 = var6.detectMoves;
      }

      Diff var9 = this.acquireDiff(var13, var14);
      if(var7 == null) {
         var13 = null;
      } else {
         var13 = var7.trimHeadAndTail;
      }

      if(var6 == null) {
         var14 = null;
      } else {
         var14 = var6.trimHeadAndTail;
      }

      Diff var10 = this.acquireDiff(var13, var14);
      if(var7 == null) {
         var13 = null;
      } else {
         var13 = var7.trimSameInstancesOnly;
      }

      if(var6 == null) {
         var14 = (Boolean)var5;
      } else {
         var14 = var6.trimSameInstancesOnly;
      }

      Diff var15 = this.acquireDiff(var13, var14);
      DataDiffSectionSpec.onCreateChangeSet(var1, var2, var8, var9, var10, var15);
      this.releaseDiff(var8);
      this.releaseDiff(var9);
      this.releaseDiff(var10);
      this.releaseDiff(var15);
   }

   protected boolean isDiffSectionSpec() {
      return true;
   }

   public boolean isEquivalentTo(Section var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            DataDiffSection var2 = (DataDiffSection)var1;
            if(this.data != null) {
               if(!this.data.equals(var2.data)) {
                  return false;
               }
            } else if(var2.data != null) {
               return false;
            }

            if(this.detectMoves != null) {
               if(!this.detectMoves.equals(var2.detectMoves)) {
                  return false;
               }
            } else if(var2.detectMoves != null) {
               return false;
            }

            if(this.trimHeadAndTail != null) {
               if(!this.trimHeadAndTail.equals(var2.trimHeadAndTail)) {
                  return false;
               }
            } else if(var2.trimHeadAndTail != null) {
               return false;
            }

            if(this.trimSameInstancesOnly != null) {
               if(!this.trimSameInstancesOnly.equals(var2.trimSameInstancesOnly)) {
                  return false;
               }
            } else if(var2.trimSameInstancesOnly != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static class Builder<T extends Object> extends Section.Builder<DataDiffSection.Builder<T>> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"data"};
      SectionContext mContext;
      DataDiffSection mDataDiffSection;
      private final BitSet mRequired = new BitSet(1);


      private void init(SectionContext var1, DataDiffSection var2) {
         super.init(var1, var2);
         this.mDataDiffSection = var2;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public DataDiffSection build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         DataDiffSection var1 = this.mDataDiffSection;
         this.release();
         return var1;
      }

      public DataDiffSection.Builder<T> data(List<T> var1) {
         this.mDataDiffSection.data = var1;
         this.mRequired.set(0);
         return this;
      }

      public DataDiffSection.Builder<T> detectMoves(@Nullable Boolean var1) {
         this.mDataDiffSection.detectMoves = var1;
         return this;
      }

      public DataDiffSection.Builder<T> getThis() {
         return this;
      }

      public DataDiffSection.Builder<T> key(String var1) {
         return (DataDiffSection.Builder)super.key(var1);
      }

      public DataDiffSection.Builder<T> loadingEventHandler(EventHandler<LoadingEvent> var1) {
         return (DataDiffSection.Builder)super.loadingEventHandler(var1);
      }

      public DataDiffSection.Builder<T> onCheckIsSameContentEventHandler(EventHandler var1) {
         this.mDataDiffSection.onCheckIsSameContentEventHandler = var1;
         return this;
      }

      public DataDiffSection.Builder<T> onCheckIsSameItemEventHandler(EventHandler var1) {
         this.mDataDiffSection.onCheckIsSameItemEventHandler = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mDataDiffSection = null;
         this.mContext = null;
      }

      public DataDiffSection.Builder<T> renderEventHandler(EventHandler var1) {
         this.mDataDiffSection.renderEventHandler = var1;
         return this;
      }

      public DataDiffSection.Builder<T> trimHeadAndTail(Boolean var1) {
         this.mDataDiffSection.trimHeadAndTail = var1;
         return this;
      }

      public DataDiffSection.Builder<T> trimSameInstancesOnly(Boolean var1) {
         this.mDataDiffSection.trimSameInstancesOnly = var1;
         return this;
      }
   }
}
