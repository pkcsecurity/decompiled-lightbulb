package com.facebook.litho.widget;

import com.facebook.litho.Component;
import com.facebook.litho.EventHandler;
import com.facebook.litho.RenderCompleteEvent;
import com.facebook.litho.viewcompat.ViewBinder;
import com.facebook.litho.viewcompat.ViewCreator;
import com.facebook.litho.widget.RenderInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class BaseRenderInfo implements RenderInfo {

   private static final String IS_FULL_SPAN = "is_full_span";
   private static final String IS_STICKY = "is_sticky";
   private static final String SPAN_SIZE = "span_size";
   @Nullable
   private final Map<String, Object> mCustomAttributes;
   @Nullable
   private Map<String, Object> mDebugInfo;


   BaseRenderInfo(BaseRenderInfo.Builder var1) {
      this.mCustomAttributes = var1.mCustomAttributes;
   }

   public void addDebugInfo(String var1, Object var2) {
      if(this.mDebugInfo == null) {
         this.mDebugInfo = Collections.synchronizedMap(new HashMap());
      }

      this.mDebugInfo.put(var1, var2);
   }

   public Component getComponent() {
      throw new UnsupportedOperationException();
   }

   @Nullable
   public Object getCustomAttribute(String var1) {
      return this.mCustomAttributes == null?null:this.mCustomAttributes.get(var1);
   }

   @Nullable
   public Object getDebugInfo(String var1) {
      return this.mDebugInfo == null?null:this.mDebugInfo.get(var1);
   }

   @Nullable
   public EventHandler<RenderCompleteEvent> getRenderCompleteEventHandler() {
      throw new UnsupportedOperationException();
   }

   public int getSpanSize() {
      return this.mCustomAttributes != null && this.mCustomAttributes.containsKey("span_size")?((Integer)this.mCustomAttributes.get("span_size")).intValue():1;
   }

   public ViewBinder getViewBinder() {
      throw new UnsupportedOperationException();
   }

   public ViewCreator getViewCreator() {
      throw new UnsupportedOperationException();
   }

   public int getViewType() {
      throw new UnsupportedOperationException();
   }

   public boolean hasCustomViewType() {
      return false;
   }

   public boolean isFullSpan() {
      return this.mCustomAttributes != null && this.mCustomAttributes.containsKey("is_full_span")?((Boolean)this.mCustomAttributes.get("is_full_span")).booleanValue():false;
   }

   public boolean isSticky() {
      return this.mCustomAttributes != null && this.mCustomAttributes.containsKey("is_sticky")?((Boolean)this.mCustomAttributes.get("is_sticky")).booleanValue():false;
   }

   public boolean rendersComponent() {
      return false;
   }

   public boolean rendersView() {
      return false;
   }

   public void setViewType(int var1) {
      throw new UnsupportedOperationException();
   }

   public abstract static class Builder<T extends Object> {

      @Nullable
      private Map<String, Object> mCustomAttributes;


      public T customAttribute(String var1, Object var2) {
         if(this.mCustomAttributes == null) {
            this.mCustomAttributes = Collections.synchronizedMap(new HashMap());
         }

         this.mCustomAttributes.put(var1, var2);
         return this;
      }

      public T isFullSpan(boolean var1) {
         return this.customAttribute("is_full_span", Boolean.valueOf(var1));
      }

      public T isSticky(boolean var1) {
         return this.customAttribute("is_sticky", Boolean.valueOf(var1));
      }

      void release() {
         this.mCustomAttributes = null;
      }

      public T spanSize(int var1) {
         return this.customAttribute("span_size", Integer.valueOf(var1));
      }
   }
}
