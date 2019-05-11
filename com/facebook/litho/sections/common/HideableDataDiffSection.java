package com.facebook.litho.sections.common;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.sections.Children;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.SectionLifecycle;
import com.facebook.litho.sections.common.GetUniqueIdentifierEvent;
import com.facebook.litho.sections.common.HideItemEvent;
import com.facebook.litho.sections.common.HideableDataDiffSectionSpec;
import com.facebook.litho.sections.common.OnCheckIsSameContentEvent;
import com.facebook.litho.sections.common.OnCheckIsSameItemEvent;
import com.facebook.litho.sections.common.RenderEvent;
import com.facebook.litho.sections.common.RenderWithHideItemHandlerEvent;
import com.facebook.litho.widget.RenderInfo;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;

public final class HideableDataDiffSection<T extends Object> extends Section {

   static final Pools.SynchronizedPool<GetUniqueIdentifierEvent> sGetUniqueIdentifierEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<RenderWithHideItemHandlerEvent> sRenderWithHideItemHandlerEventPool = new Pools.SynchronizedPool(2);
   @Comparable(
      type = 5
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   List<T> data;
   EventHandler getUniqueIdentifierEventHandler;
   @Comparable(
      type = 12
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   EventHandler<GetUniqueIdentifierEvent> getUniqueIdentifierHandler;
   @Comparable(
      type = 14
   )
   private HideableDataDiffSection.HideableDataDiffSectionStateContainer mStateContainer = new HideableDataDiffSection.HideableDataDiffSectionStateContainer();
   @Comparable(
      type = 12
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   EventHandler<OnCheckIsSameContentEvent> onSameContentEventHandler;
   @Comparable(
      type = 12
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   EventHandler<OnCheckIsSameItemEvent> onSameItemEventHandler;
   @Comparable(
      type = 12
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   EventHandler<RenderWithHideItemHandlerEvent> renderWithHideItemHandler;
   EventHandler renderWithHideItemHandlerEventHandler;


   private HideableDataDiffSection() {
      super("HideableDataDiffSection");
   }

   public static <T extends Object> HideableDataDiffSection.Builder<T> create(SectionContext var0) {
      HideableDataDiffSection.Builder var1 = new HideableDataDiffSection.Builder();
      var1.init(var0, new HideableDataDiffSection());
      return var1;
   }

   private HideableDataDiffSection.OnBlacklistUpdateStateUpdate createOnBlacklistUpdateStateUpdate(Object var1, EventHandler<GetUniqueIdentifierEvent> var2) {
      return new HideableDataDiffSection.OnBlacklistUpdateStateUpdate(var1, var2);
   }

   static Object dispatchGetUniqueIdentifierEvent(EventHandler var0, Object var1) {
      GetUniqueIdentifierEvent var3 = (GetUniqueIdentifierEvent)sGetUniqueIdentifierEventPool.acquire();
      GetUniqueIdentifierEvent var2 = var3;
      if(var3 == null) {
         var2 = new GetUniqueIdentifierEvent();
      }

      var2.model = var1;
      Object var4 = var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var2);
      var2.model = null;
      sGetUniqueIdentifierEventPool.release(var2);
      return var4;
   }

   static RenderInfo dispatchRenderWithHideItemHandlerEvent(EventHandler var0, int var1, Object var2, EventHandler<HideItemEvent> var3, Bundle var4) {
      RenderWithHideItemHandlerEvent var6 = (RenderWithHideItemHandlerEvent)sRenderWithHideItemHandlerEventPool.acquire();
      RenderWithHideItemHandlerEvent var5 = var6;
      if(var6 == null) {
         var5 = new RenderWithHideItemHandlerEvent();
      }

      var5.index = var1;
      var5.model = var2;
      var5.hideItemHandler = var3;
      var5.loggingExtras = var4;
      RenderInfo var7 = (RenderInfo)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var5);
      var5.model = null;
      var5.hideItemHandler = null;
      var5.loggingExtras = null;
      sRenderWithHideItemHandlerEventPool.release(var5);
      return var7;
   }

   public static EventHandler getGetUniqueIdentifierEventHandler(SectionContext var0) {
      return var0.getSectionScope() == null?null:((HideableDataDiffSection)var0.getSectionScope()).getUniqueIdentifierEventHandler;
   }

   public static EventHandler getRenderWithHideItemHandlerEventHandler(SectionContext var0) {
      return var0.getSectionScope() == null?null:((HideableDataDiffSection)var0.getSectionScope()).renderWithHideItemHandlerEventHandler;
   }

   protected static void onBlacklistUpdate(SectionContext var0, Object var1, EventHandler<GetUniqueIdentifierEvent> var2) {
      Section var3 = var0.getSectionScope();
      if(var3 != null) {
         var0.updateStateSync(((HideableDataDiffSection)var3).createOnBlacklistUpdateStateUpdate(var1, var2), "HideableDataDiffSection.onBlacklistUpdate");
      }
   }

   protected static void onBlacklistUpdateAsync(SectionContext var0, Object var1, EventHandler<GetUniqueIdentifierEvent> var2) {
      Section var3 = var0.getSectionScope();
      if(var3 != null) {
         var0.updateStateAsync(((HideableDataDiffSection)var3).createOnBlacklistUpdateStateUpdate(var1, var2), "HideableDataDiffSection.onBlacklistUpdate");
      }
   }

   protected static void onBlacklistUpdateSync(SectionContext var0, Object var1, EventHandler<GetUniqueIdentifierEvent> var2) {
      Section var3 = var0.getSectionScope();
      if(var3 != null) {
         var0.updateStateSync(((HideableDataDiffSection)var3).createOnBlacklistUpdateStateUpdate(var1, var2), "HideableDataDiffSection.onBlacklistUpdate");
      }
   }

   public static EventHandler<HideItemEvent> onHideItem(SectionContext var0) {
      return newEventHandler(var0, 995720820, new Object[]{var0});
   }

   private void onHideItem(HasEventDispatcher var1, SectionContext var2, Object var3) {
      HideableDataDiffSectionSpec.onHideItem(var2, var3, ((HideableDataDiffSection)var1).getUniqueIdentifierHandler);
   }

   public static EventHandler<RenderEvent> onRenderEvent(SectionContext var0) {
      return newEventHandler(var0, -1172416699, new Object[]{var0});
   }

   private RenderInfo onRenderEvent(HasEventDispatcher var1, SectionContext var2, int var3, Object var4, Bundle var5) {
      return HideableDataDiffSectionSpec.onRenderEvent(var2, var3, var4, var5, ((HideableDataDiffSection)var1).renderWithHideItemHandler);
   }

   protected Children createChildren(SectionContext var1) {
      return HideableDataDiffSectionSpec.onCreateChildren(var1, this.mStateContainer.blacklistState, this.data, this.getUniqueIdentifierHandler, this.onSameItemEventHandler, this.onSameContentEventHandler);
   }

   protected void createInitialState(SectionContext var1) {
      StateValue var2 = new StateValue();
      HideableDataDiffSectionSpec.onCreateInitialState(var1, var2);
      this.mStateContainer.blacklistState = (HashSet)var2.get();
   }

   public Object dispatchOnEvent(EventHandler var1, Object var2) {
      int var3 = var1.id;
      if(var3 != -1172416699) {
         if(var3 != 995720820) {
            return null;
         } else {
            HideItemEvent var5 = (HideItemEvent)var2;
            this.onHideItem(var1.mHasEventDispatcher, (SectionContext)var1.params[0], var5.model);
            return null;
         }
      } else {
         RenderEvent var4 = (RenderEvent)var2;
         return this.onRenderEvent(var1.mHasEventDispatcher, (SectionContext)var1.params[0], var4.index, var4.model, var4.loggingExtras);
      }
   }

   protected StateContainer getStateContainer() {
      return this.mStateContainer;
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
            HideableDataDiffSection var2 = (HideableDataDiffSection)var1;
            if(this.data != null) {
               if(!this.data.equals(var2.data)) {
                  return false;
               }
            } else if(var2.data != null) {
               return false;
            }

            if(this.getUniqueIdentifierHandler != null) {
               if(!this.getUniqueIdentifierHandler.isEquivalentTo(var2.getUniqueIdentifierHandler)) {
                  return false;
               }
            } else if(var2.getUniqueIdentifierHandler != null) {
               return false;
            }

            if(this.onSameContentEventHandler != null) {
               if(!this.onSameContentEventHandler.isEquivalentTo(var2.onSameContentEventHandler)) {
                  return false;
               }
            } else if(var2.onSameContentEventHandler != null) {
               return false;
            }

            if(this.onSameItemEventHandler != null) {
               if(!this.onSameItemEventHandler.isEquivalentTo(var2.onSameItemEventHandler)) {
                  return false;
               }
            } else if(var2.onSameItemEventHandler != null) {
               return false;
            }

            if(this.renderWithHideItemHandler != null) {
               if(!this.renderWithHideItemHandler.isEquivalentTo(var2.renderWithHideItemHandler)) {
                  return false;
               }
            } else if(var2.renderWithHideItemHandler != null) {
               return false;
            }

            if(this.mStateContainer.blacklistState != null) {
               if(!this.mStateContainer.blacklistState.equals(var2.mStateContainer.blacklistState)) {
                  return false;
               }
            } else if(var2.mStateContainer.blacklistState != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public HideableDataDiffSection makeShallowCopy(boolean var1) {
      HideableDataDiffSection var2 = (HideableDataDiffSection)super.makeShallowCopy(var1);
      if(!var1) {
         var2.mStateContainer = new HideableDataDiffSection.HideableDataDiffSectionStateContainer();
      }

      return var2;
   }

   protected void transferState(SectionContext var1, StateContainer var2) {
      HideableDataDiffSection.HideableDataDiffSectionStateContainer var3 = (HideableDataDiffSection.HideableDataDiffSectionStateContainer)var2;
      this.mStateContainer.blacklistState = var3.blacklistState;
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class HideableDataDiffSectionStateContainer<T extends Object> implements StateContainer {

      @Comparable(
         type = 5
      )
      @State
      HashSet blacklistState;


   }

   static class OnBlacklistUpdateStateUpdate implements SectionLifecycle.StateUpdate {

      private EventHandler<GetUniqueIdentifierEvent> mGetUniqueIdentifierHandlerParam;
      private Object mModelObject;


      OnBlacklistUpdateStateUpdate(Object var1, EventHandler<GetUniqueIdentifierEvent> var2) {
         this.mModelObject = var1;
         this.mGetUniqueIdentifierHandlerParam = var2;
      }

      public void updateState(StateContainer var1, Section var2) {
         HideableDataDiffSection.HideableDataDiffSectionStateContainer var4 = (HideableDataDiffSection.HideableDataDiffSectionStateContainer)var1;
         HideableDataDiffSection var5 = (HideableDataDiffSection)var2;
         StateValue var3 = new StateValue();
         var3.set(var4.blacklistState);
         HideableDataDiffSectionSpec.onBlacklistUpdate(var3, this.mModelObject, this.mGetUniqueIdentifierHandlerParam);
         var5.mStateContainer.blacklistState = (HashSet)var3.get();
      }
   }

   public static class Builder<T extends Object> extends Section.Builder<HideableDataDiffSection.Builder<T>> {

      private final int REQUIRED_PROPS_COUNT = 3;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"data", "getUniqueIdentifierHandler", "renderWithHideItemHandler"};
      SectionContext mContext;
      HideableDataDiffSection mHideableDataDiffSection;
      private final BitSet mRequired = new BitSet(3);


      private void init(SectionContext var1, HideableDataDiffSection var2) {
         super.init(var1, var2);
         this.mHideableDataDiffSection = var2;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public HideableDataDiffSection build() {
         checkArgs(3, this.mRequired, this.REQUIRED_PROPS_NAMES);
         HideableDataDiffSection var1 = this.mHideableDataDiffSection;
         this.release();
         return var1;
      }

      public HideableDataDiffSection.Builder<T> data(List<T> var1) {
         this.mHideableDataDiffSection.data = var1;
         this.mRequired.set(0);
         return this;
      }

      public HideableDataDiffSection.Builder<T> getThis() {
         return this;
      }

      public HideableDataDiffSection.Builder<T> getUniqueIdentifierEventHandler(EventHandler var1) {
         this.mHideableDataDiffSection.getUniqueIdentifierEventHandler = var1;
         return this;
      }

      public HideableDataDiffSection.Builder<T> getUniqueIdentifierHandler(EventHandler<GetUniqueIdentifierEvent> var1) {
         this.mHideableDataDiffSection.getUniqueIdentifierHandler = var1;
         this.mRequired.set(1);
         return this;
      }

      public HideableDataDiffSection.Builder<T> key(String var1) {
         return (HideableDataDiffSection.Builder)super.key(var1);
      }

      public HideableDataDiffSection.Builder<T> loadingEventHandler(EventHandler<LoadingEvent> var1) {
         return (HideableDataDiffSection.Builder)super.loadingEventHandler(var1);
      }

      public HideableDataDiffSection.Builder<T> onSameContentEventHandler(EventHandler<OnCheckIsSameContentEvent> var1) {
         this.mHideableDataDiffSection.onSameContentEventHandler = var1;
         return this;
      }

      public HideableDataDiffSection.Builder<T> onSameItemEventHandler(EventHandler<OnCheckIsSameItemEvent> var1) {
         this.mHideableDataDiffSection.onSameItemEventHandler = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mHideableDataDiffSection = null;
         this.mContext = null;
      }

      public HideableDataDiffSection.Builder<T> renderWithHideItemHandler(EventHandler<RenderWithHideItemHandlerEvent> var1) {
         this.mHideableDataDiffSection.renderWithHideItemHandler = var1;
         this.mRequired.set(2);
         return this;
      }

      public HideableDataDiffSection.Builder<T> renderWithHideItemHandlerEventHandler(EventHandler var1) {
         this.mHideableDataDiffSection.renderWithHideItemHandlerEventHandler = var1;
         return this;
      }
   }
}
