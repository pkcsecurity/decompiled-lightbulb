package com.facebook.litho.widget;

import android.support.v4.util.Pools;
import com.facebook.litho.viewcompat.ViewBinder;
import com.facebook.litho.viewcompat.ViewCreator;
import com.facebook.litho.widget.BaseRenderInfo;

public class ViewRenderInfo extends BaseRenderInfo {

   private static final Pools.Pool<ViewRenderInfo.Builder> sBuilderPool = new Pools.SynchronizedPool(2);
   private final boolean mHasCustomViewType;
   private final ViewBinder mViewBinder;
   private final ViewCreator mViewCreator;
   private int mViewType;


   private ViewRenderInfo(ViewRenderInfo.Builder var1) {
      super(var1);
      this.mViewBinder = var1.viewBinder;
      this.mViewCreator = var1.viewCreator;
      this.mHasCustomViewType = var1.hasCustomViewType;
      if(this.mHasCustomViewType) {
         this.mViewType = var1.viewType;
      }

   }

   // $FF: synthetic method
   ViewRenderInfo(ViewRenderInfo.Builder var1, Object var2) {
      this(var1);
   }

   public static ViewRenderInfo.Builder create() {
      ViewRenderInfo.Builder var1 = (ViewRenderInfo.Builder)sBuilderPool.acquire();
      ViewRenderInfo.Builder var0 = var1;
      if(var1 == null) {
         var0 = new ViewRenderInfo.Builder();
      }

      return var0;
   }

   public String getName() {
      StringBuilder var1 = new StringBuilder();
      var1.append("View (viewType=");
      var1.append(this.mViewType);
      var1.append(")");
      return var1.toString();
   }

   public ViewBinder getViewBinder() {
      return this.mViewBinder;
   }

   public ViewCreator getViewCreator() {
      return this.mViewCreator;
   }

   public int getViewType() {
      return this.mViewType;
   }

   public boolean hasCustomViewType() {
      return this.mHasCustomViewType;
   }

   public boolean rendersView() {
      return true;
   }

   public void setViewType(int var1) {
      if(this.mHasCustomViewType) {
         throw new UnsupportedOperationException("Cannot override custom view type.");
      } else {
         this.mViewType = var1;
      }
   }

   public static class Builder extends BaseRenderInfo.Builder<ViewRenderInfo.Builder> {

      private boolean hasCustomViewType = false;
      private ViewBinder viewBinder;
      private ViewCreator viewCreator;
      private int viewType = 0;


      public ViewRenderInfo build() {
         if(this.viewCreator != null && this.viewBinder != null) {
            ViewRenderInfo var1 = new ViewRenderInfo(this, null);
            this.release();
            return var1;
         } else {
            throw new IllegalStateException("Both viewCreator and viewBinder must be provided.");
         }
      }

      public ViewRenderInfo.Builder customViewType(int var1) {
         this.hasCustomViewType = true;
         this.viewType = var1;
         return this;
      }

      public ViewRenderInfo.Builder isFullSpan(boolean var1) {
         throw new UnsupportedOperationException("ViewRenderInfo does not support isFullSpan.");
      }

      void release() {
         super.release();
         this.viewBinder = null;
         this.viewCreator = null;
         this.hasCustomViewType = false;
         this.viewType = 0;
         ViewRenderInfo.sBuilderPool.release(this);
      }

      public ViewRenderInfo.Builder viewBinder(ViewBinder var1) {
         this.viewBinder = var1;
         return this;
      }

      public ViewRenderInfo.Builder viewCreator(ViewCreator var1) {
         this.viewCreator = var1;
         return this;
      }
   }
}
