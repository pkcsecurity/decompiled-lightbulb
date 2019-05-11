package com.facebook.litho.sections.common;

import android.os.Bundle;
import com.facebook.litho.EventHandler;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;
import com.facebook.litho.sections.Children;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.annotations.GroupSectionSpec;
import com.facebook.litho.sections.annotations.OnCreateChildren;
import com.facebook.litho.sections.common.DataDiffSection;
import com.facebook.litho.sections.common.GetUniqueIdentifierEvent;
import com.facebook.litho.sections.common.HideItemEvent;
import com.facebook.litho.sections.common.HideableDataDiffSection;
import com.facebook.litho.sections.common.OnCheckIsSameContentEvent;
import com.facebook.litho.sections.common.OnCheckIsSameItemEvent;
import com.facebook.litho.sections.common.RenderEvent;
import com.facebook.litho.sections.common.RenderWithHideItemHandlerEvent;
import com.facebook.litho.widget.RenderInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@GroupSectionSpec(
   events = {RenderWithHideItemHandlerEvent.class, GetUniqueIdentifierEvent.class}
)
public class HideableDataDiffSectionSpec<T extends Object> {

   @OnUpdateState
   public static void onBlacklistUpdate(StateValue<HashSet> var0, @Param Object var1, @Param EventHandler<GetUniqueIdentifierEvent> var2) {
      HashSet var3 = new HashSet((Collection)var0.get());
      var3.add(HideableDataDiffSection.dispatchGetUniqueIdentifierEvent(var2, var1));
      var0.set(var3);
   }

   @OnCreateChildren
   protected static <T extends Object> Children onCreateChildren(SectionContext var0, @State HashSet var1, @Prop List<T> var2, @Prop EventHandler<GetUniqueIdentifierEvent> var3, 
      @Prop(
         optional = true
      ) EventHandler<OnCheckIsSameItemEvent> var4, 
      @Prop(
         optional = true
      ) EventHandler<OnCheckIsSameContentEvent> var5) {
      return Children.create().child((Section.Builder)DataDiffSection.create(var0).data(removeBlacklistedItems(var0, var2, var1, var3)).renderEventHandler(HideableDataDiffSection.onRenderEvent(var0)).onCheckIsSameContentEventHandler(var5).onCheckIsSameItemEventHandler(var4)).build();
   }

   @OnCreateInitialState
   public static <T extends Object> void onCreateInitialState(SectionContext var0, StateValue<HashSet> var1) {
      var1.set(new HashSet());
   }

   @OnEvent(HideItemEvent.class)
   public static void onHideItem(SectionContext var0, Object var1, @Prop EventHandler<GetUniqueIdentifierEvent> var2) {
      HideableDataDiffSection.onBlacklistUpdate(var0, var1, var2);
   }

   @OnEvent(RenderEvent.class)
   protected static RenderInfo onRenderEvent(SectionContext var0, int var1, Object var2, Bundle var3, @Prop EventHandler<RenderWithHideItemHandlerEvent> var4) {
      return HideableDataDiffSection.dispatchRenderWithHideItemHandlerEvent(var4, var1, var2, HideableDataDiffSection.onHideItem(var0), var3);
   }

   private static <T extends Object> List<T> removeBlacklistedItems(SectionContext var0, List<T> var1, HashSet var2, EventHandler<GetUniqueIdentifierEvent> var3) {
      ArrayList var7 = new ArrayList();
      int var5 = var1.size();

      for(int var4 = 0; var4 < var5; ++var4) {
         Object var6 = var1.get(var4);
         if(!var2.contains(HideableDataDiffSection.dispatchGetUniqueIdentifierEvent(var3, var6))) {
            var7.add(var6);
         }
      }

      return var7;
   }
}
