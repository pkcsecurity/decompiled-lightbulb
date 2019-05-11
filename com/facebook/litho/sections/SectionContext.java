package com.facebook.litho.sections;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTrigger;
import com.facebook.litho.TreeProps;
import com.facebook.litho.sections.KeyHandler;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionLifecycle;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.SectionTreeLoadingEventHandler;
import java.lang.ref.WeakReference;

public class SectionContext extends ComponentContext {

   private KeyHandler mKeyHandler;
   private WeakReference<Section> mScope;
   private SectionTree mSectionTree;
   private EventHandler<LoadingEvent> mTreeLoadingEventHandler;


   public SectionContext(Context var1) {
      this(var1, (String)null, (ComponentsLogger)null);
   }

   public SectionContext(Context var1, String var2, ComponentsLogger var3) {
      this(var1, var2, var3, (TreeProps)null);
   }

   public SectionContext(Context var1, String var2, ComponentsLogger var3, @Nullable TreeProps var4) {
      super(var1, var2, var3);
      super.setTreeProps(var4);
      this.mKeyHandler = new KeyHandler();
   }

   public SectionContext(ComponentContext var1) {
      this(var1.getAndroidContext(), var1.getLogTag(), var1.getLogger(), var1.getTreePropsCopy());
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public static SectionContext withScope(SectionContext var0, Section var1) {
      SectionContext var2 = new SectionContext(var0);
      var2.mSectionTree = var0.mSectionTree;
      var2.mTreeLoadingEventHandler = var0.mTreeLoadingEventHandler;
      var2.mScope = new WeakReference(var1);
      return var2;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public static SectionContext withSectionTree(SectionContext var0, SectionTree var1) {
      var0 = new SectionContext(var0);
      var0.mSectionTree = var1;
      var0.mTreeLoadingEventHandler = new SectionTreeLoadingEventHandler(var1);
      return var0;
   }

   KeyHandler getKeyHandler() {
      return this.mKeyHandler;
   }

   public Section getSectionScope() {
      return (Section)this.mScope.get();
   }

   @Nullable
   SectionTree getSectionTree() {
      return this.mSectionTree;
   }

   EventHandler<LoadingEvent> getTreeLoadingEventHandler() {
      return this.mTreeLoadingEventHandler;
   }

   @Nullable
   protected TreeProps getTreeProps() {
      return super.getTreeProps();
   }

   public <E extends Object> EventHandler<E> newEventHandler(int var1, Object[] var2) {
      Section var3 = (Section)this.mScope.get();
      if(var3 == null) {
         throw new IllegalStateException("Called newEventHandler on a released Section");
      } else {
         return new EventHandler(var3, var1, var2);
      }
   }

   <E extends Object> EventTrigger<E> newEventTrigger(String var1, int var2) {
      Section var3;
      if(this.mScope == null) {
         var3 = null;
      } else {
         var3 = (Section)this.mScope.get();
      }

      String var4;
      if(var3 == null) {
         var4 = "";
      } else {
         var4 = var3.getGlobalKey();
      }

      return new EventTrigger(var4, var2, var1);
   }

   public void setTreeProps(TreeProps var1) {
      super.setTreeProps(var1);
   }

   public void updateStateAsync(SectionLifecycle.StateUpdate var1, String var2) {
      Section var3 = (Section)this.mScope.get();
      SectionTree var4 = this.mSectionTree;
      if(var4 != null) {
         if(var3 != null) {
            var4.updateStateAsync(var3.getGlobalKey(), var1, var2);
         }
      }
   }

   public void updateStateLazy(SectionLifecycle.StateUpdate var1) {
      this.mSectionTree.updateStateLazy(((Section)this.mScope.get()).getGlobalKey(), var1);
   }

   public void updateStateSync(SectionLifecycle.StateUpdate var1, String var2) {
      Section var3 = (Section)this.mScope.get();
      SectionTree var4 = this.mSectionTree;
      if(var4 != null) {
         if(var3 != null) {
            var4.updateState(var3.getGlobalKey(), var1, var2);
         }
      }
   }
}
