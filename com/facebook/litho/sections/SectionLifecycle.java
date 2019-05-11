package com.facebook.litho.sections;

import android.support.annotation.Nullable;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.Diff;
import com.facebook.litho.EventDispatcher;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTrigger;
import com.facebook.litho.EventTriggerTarget;
import com.facebook.litho.Output;
import com.facebook.litho.StateContainer;
import com.facebook.litho.TreeProps;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.sections.ChangeSet;
import com.facebook.litho.sections.Children;
import com.facebook.litho.sections.FocusType;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.widget.SmoothScrollAlignmentType;

public abstract class SectionLifecycle implements EventDispatcher, EventTriggerTarget {

   public static void dispatchLoadingEvent(SectionContext var0, boolean var1, LoadingEvent.LoadingState var2, @Nullable Throwable var3) {
      EventHandler var5 = getLoadingEventHandler(var0);
      if(var5 != null) {
         LoadingEvent var4 = new LoadingEvent();
         var4.isEmpty = var1;
         var4.loadingState = var2;
         var4.t = var3;
         var5.dispatchEvent(var4);
      }

   }

   @Nullable
   protected static EventTrigger getEventTrigger(SectionContext var0, int var1, String var2) {
      if(var0.getSectionScope() == null) {
         return null;
      } else {
         SectionTree var3 = var0.getSectionTree();
         StringBuilder var4 = new StringBuilder();
         var4.append(var0.getSectionScope().getGlobalKey());
         var4.append(var1);
         var4.append(var2);
         EventTrigger var5 = var3.getEventTrigger(var4.toString());
         return var5 == null?null:var5;
      }
   }

   @Nullable
   public static EventHandler getLoadingEventHandler(SectionContext var0) {
      Section var2 = var0.getSectionScope();
      Section var1 = var2;
      if(var2 == null) {
         return null;
      } else {
         while(var1.getParent() != null) {
            if(var1.loadingEventHandler != null) {
               return var1.loadingEventHandler;
            }

            var1 = var1.getParent();
         }

         return var0.getTreeLoadingEventHandler();
      }
   }

   public static boolean isSectionIndexValid(SectionContext var0, String var1, int var2) {
      Section var3 = var0.getSectionScope();
      SectionTree var5 = var0.getSectionTree();
      if(var3 != null && var5 != null) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var3.getGlobalKey());
         var4.append(var1);
         return var5.isSectionIndexValid(var4.toString(), var2);
      } else {
         return false;
      }
   }

   protected static <E extends Object> EventHandler<E> newEventHandler(Section var0, int var1, Object[] var2) {
      EventHandler var3 = new EventHandler(var0, var1, var2);
      recordEventHandler(var0, var3);
      return var3;
   }

   protected static <E extends Object> EventHandler<E> newEventHandler(SectionContext var0, int var1, Object[] var2) {
      EventHandler var3 = var0.newEventHandler(var1, var2);
      recordEventHandler(var0.getSectionScope(), var3);
      return var3;
   }

   protected static <E extends Object> EventTrigger<E> newEventTrigger(SectionContext var0, String var1, int var2) {
      return var0.newEventTrigger(var1, var2);
   }

   private static void recordEventHandler(Section var0, EventHandler var1) {
      var0.getScopedContext().getSectionTree().recordEventHandler(var0, var1);
   }

   public static void requestFocus(SectionContext var0, int var1) {
      Section var2 = var0.getSectionScope();
      SectionTree var3 = var0.getSectionTree();
      if(var2 != null) {
         if(var3 != null) {
            var3.requestFocus(var2, var1);
         }
      }
   }

   public static void requestFocus(SectionContext var0, String var1) {
      requestFocus(var0, var1, FocusType.START);
   }

   public static void requestFocus(SectionContext var0, String var1, FocusType var2) {
      Section var3 = var0.getSectionScope();
      SectionTree var5 = var0.getSectionTree();
      if(var3 != null) {
         if(var5 != null) {
            StringBuilder var4 = new StringBuilder();
            var4.append(var3.getGlobalKey());
            var4.append(var1);
            var1 = var4.toString();
            switch(null.$SwitchMap$com$facebook$litho$sections$FocusType[var2.ordinal()]) {
            case 1:
               var5.requestFocusStart(var1);
               return;
            case 2:
               var5.requestFocusEnd(var1);
               return;
            default:
            }
         }
      }
   }

   public static void requestFocusWithOffset(SectionContext var0, int var1, int var2) {
      Section var3 = var0.getSectionScope();
      SectionTree var4 = var0.getSectionTree();
      if(var3 != null) {
         if(var4 != null) {
            var4.requestFocusWithOffset(var3, var1, var2);
         }
      }
   }

   public static void requestFocusWithOffset(SectionContext var0, String var1, int var2) {
      requestFocusWithOffset(var0, var1, 0, var2);
   }

   public static void requestFocusWithOffset(SectionContext var0, String var1, int var2, int var3) {
      Section var4 = var0.getSectionScope();
      SectionTree var6 = var0.getSectionTree();
      if(var4 != null) {
         if(var6 != null) {
            StringBuilder var5 = new StringBuilder();
            var5.append(var4.getGlobalKey());
            var5.append(var1);
            var6.requestFocusWithOffset(var5.toString(), var2, var3);
         }
      }
   }

   public static void requestSmoothFocus(SectionContext var0, int var1) {
      requestSmoothFocus(var0, "", var1, 0, SmoothScrollAlignmentType.DEFAULT);
   }

   public static void requestSmoothFocus(SectionContext var0, int var1, SmoothScrollAlignmentType var2) {
      requestSmoothFocus(var0, "", var1, 0, var2);
   }

   public static void requestSmoothFocus(SectionContext var0, String var1, int var2, int var3, SmoothScrollAlignmentType var4) {
      Section var5 = var0.getSectionScope();
      SectionTree var7 = var0.getSectionTree();
      if(var5 != null) {
         if(var7 != null) {
            StringBuilder var6 = new StringBuilder();
            var6.append(var5.getGlobalKey());
            var6.append(var1);
            var7.requestSmoothFocus(var6.toString(), var2, var3, var4);
         }
      }
   }

   @Nullable
   public Object acceptTriggerEvent(EventTrigger var1, Object var2, Object[] var3) {
      return null;
   }

   protected final <T extends Object> Diff<T> acquireDiff(T var1, T var2) {
      return ComponentsPools.acquireDiff(var1, var2);
   }

   protected Output acquireOutput() {
      return new Output();
   }

   protected void bindService(SectionContext var1) {}

   @Nullable
   public Children createChildren(SectionContext var1) {
      return null;
   }

   public void createInitialState(SectionContext var1) {}

   protected void createService(SectionContext var1) {}

   protected void dataBound(SectionContext var1) {}

   protected void dataRendered(SectionContext var1, boolean var2, boolean var3, long var4) {}

   @Nullable
   public Object dispatchOnEvent(EventHandler var1, Object var2) {
      return null;
   }

   public void generateChangeSet(SectionContext var1, ChangeSet var2, Section var3, Section var4) {}

   String getLogTag() {
      return this.getClass().getSimpleName();
   }

   @Nullable
   protected Object getService(Section var1) {
      return null;
   }

   protected TreeProps getTreePropsForChildren(SectionContext var1, TreeProps var2) {
      return var2;
   }

   public boolean isDiffSectionSpec() {
      return false;
   }

   protected void populateTreeProps(TreeProps var1) {}

   protected void refresh(SectionContext var1) {}

   protected void releaseDiff(Diff var1) {
      if(!ComponentsConfiguration.disablePools) {
         ComponentsPools.release(var1);
      }
   }

   protected void releaseOutput(Output var1) {}

   final boolean shouldComponentUpdate(Section var1, Section var2) {
      boolean var4 = false;
      boolean var3;
      if(var2 != null) {
         var3 = var2.isInvalidated() | false;
      } else {
         var3 = false;
      }

      if(var3 || this.shouldUpdate(var1, var2)) {
         var4 = true;
      }

      return var4;
   }

   protected boolean shouldUpdate(Section var1, Section var2) {
      return var1 != var2 && (var1 == null || !var1.isEquivalentTo(var2));
   }

   protected void transferService(SectionContext var1, Section var2, Section var3) {}

   public void transferState(SectionContext var1, StateContainer var2) {}

   protected void unbindService(SectionContext var1) {}

   protected void viewportChanged(SectionContext var1, int var2, int var3, int var4, int var5, int var6) {}

   public interface StateUpdate {

      void updateState(StateContainer var1, Section var2);
   }
}
