package com.facebook.litho.widget;

import android.support.annotation.Nullable;
import com.facebook.litho.Component;
import com.facebook.litho.EventHandler;
import com.facebook.litho.RenderCompleteEvent;
import com.facebook.litho.TreeProps;
import com.facebook.litho.viewcompat.ViewBinder;
import com.facebook.litho.viewcompat.ViewCreator;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;

public class TreePropsWrappedRenderInfo implements RenderInfo {

   private final RenderInfo mRenderInfo;
   private final TreeProps mTreeProps;


   public TreePropsWrappedRenderInfo(@Nullable RenderInfo var1, @Nullable TreeProps var2) {
      RenderInfo var3 = var1;
      if(var1 == null) {
         var3 = ComponentRenderInfo.createEmpty();
      }

      this.mRenderInfo = var3;
      this.mTreeProps = var2;
   }

   public void addDebugInfo(String var1, Object var2) {
      this.mRenderInfo.addDebugInfo(var1, var2);
   }

   public Component getComponent() {
      return this.mRenderInfo.getComponent();
   }

   @Nullable
   public Object getCustomAttribute(String var1) {
      return this.mRenderInfo.getCustomAttribute(var1);
   }

   @javax.annotation.Nullable
   public Object getDebugInfo(String var1) {
      return this.mRenderInfo.getDebugInfo(var1);
   }

   public String getName() {
      return this.mRenderInfo.getName();
   }

   @Nullable
   public EventHandler<RenderCompleteEvent> getRenderCompleteEventHandler() {
      return this.mRenderInfo.getRenderCompleteEventHandler();
   }

   public int getSpanSize() {
      return this.mRenderInfo.getSpanSize();
   }

   public TreeProps getTreeProps() {
      return this.mTreeProps;
   }

   public ViewBinder getViewBinder() {
      return this.mRenderInfo.getViewBinder();
   }

   public ViewCreator getViewCreator() {
      return this.mRenderInfo.getViewCreator();
   }

   public int getViewType() {
      return this.mRenderInfo.getViewType();
   }

   public boolean hasCustomViewType() {
      return this.mRenderInfo.hasCustomViewType();
   }

   public boolean isFullSpan() {
      return this.mRenderInfo.isFullSpan();
   }

   public boolean isSticky() {
      return this.mRenderInfo.isSticky();
   }

   public boolean rendersComponent() {
      return this.mRenderInfo.rendersComponent();
   }

   public boolean rendersView() {
      return this.mRenderInfo.rendersView();
   }

   public void setViewType(int var1) {
      this.mRenderInfo.setViewType(var1);
   }
}
