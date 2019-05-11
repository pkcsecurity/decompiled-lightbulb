package com.facebook.litho;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import com.facebook.litho.Component;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FocusedVisibleEvent;
import com.facebook.litho.FullImpressionVisibleEvent;
import com.facebook.litho.InvisibleEvent;
import com.facebook.litho.UnfocusedVisibleEvent;
import com.facebook.litho.VisibilityChangedEvent;
import com.facebook.litho.VisibleEvent;

class VisibilityOutput {

   private final Rect mBounds = new Rect();
   private Component mComponent;
   private EventHandler<FocusedVisibleEvent> mFocusedEventHandler;
   private EventHandler<FullImpressionVisibleEvent> mFullImpressionEventHandler;
   private long mId;
   private EventHandler<InvisibleEvent> mInvisibleEventHandler;
   private EventHandler<UnfocusedVisibleEvent> mUnfocusedEventHandler;
   @Nullable
   private EventHandler<VisibilityChangedEvent> mVisibilityChangedEventHandler;
   private EventHandler<VisibleEvent> mVisibleEventHandler;
   private float mVisibleHeightRatio;
   private float mVisibleWidthRatio;


   Rect getBounds() {
      return this.mBounds;
   }

   Component getComponent() {
      return this.mComponent;
   }

   EventHandler<FocusedVisibleEvent> getFocusedEventHandler() {
      return this.mFocusedEventHandler;
   }

   EventHandler<FullImpressionVisibleEvent> getFullImpressionEventHandler() {
      return this.mFullImpressionEventHandler;
   }

   long getId() {
      return this.mId;
   }

   EventHandler<InvisibleEvent> getInvisibleEventHandler() {
      return this.mInvisibleEventHandler;
   }

   EventHandler<UnfocusedVisibleEvent> getUnfocusedEventHandler() {
      return this.mUnfocusedEventHandler;
   }

   @Nullable
   EventHandler<VisibilityChangedEvent> getVisibilityChangedEventHandler() {
      return this.mVisibilityChangedEventHandler;
   }

   EventHandler<VisibleEvent> getVisibleEventHandler() {
      return this.mVisibleEventHandler;
   }

   float getVisibleHeightRatio() {
      return this.mVisibleHeightRatio;
   }

   float getVisibleWidthRatio() {
      return this.mVisibleWidthRatio;
   }

   void release() {
      this.mVisibleHeightRatio = 0.0F;
      this.mVisibleWidthRatio = 0.0F;
      this.mComponent = null;
      this.mVisibleEventHandler = null;
      this.mFocusedEventHandler = null;
      this.mUnfocusedEventHandler = null;
      this.mFullImpressionEventHandler = null;
      this.mInvisibleEventHandler = null;
      this.mVisibilityChangedEventHandler = null;
      this.mBounds.setEmpty();
   }

   void setBounds(int var1, int var2, int var3, int var4) {
      this.mBounds.set(var1, var2, var3, var4);
   }

   void setBounds(Rect var1) {
      this.mBounds.set(var1);
   }

   void setComponent(Component var1) {
      this.mComponent = var1;
   }

   void setFocusedEventHandler(EventHandler<FocusedVisibleEvent> var1) {
      this.mFocusedEventHandler = var1;
   }

   void setFullImpressionEventHandler(EventHandler<FullImpressionVisibleEvent> var1) {
      this.mFullImpressionEventHandler = var1;
   }

   void setId(long var1) {
      this.mId = var1;
   }

   void setInvisibleEventHandler(EventHandler<InvisibleEvent> var1) {
      this.mInvisibleEventHandler = var1;
   }

   void setUnfocusedEventHandler(EventHandler<UnfocusedVisibleEvent> var1) {
      this.mUnfocusedEventHandler = var1;
   }

   void setVisibilityChangedEventHandler(@Nullable EventHandler<VisibilityChangedEvent> var1) {
      this.mVisibilityChangedEventHandler = var1;
   }

   void setVisibleEventHandler(EventHandler<VisibleEvent> var1) {
      this.mVisibleEventHandler = var1;
   }

   void setVisibleHeightRatio(float var1) {
      this.mVisibleHeightRatio = var1;
   }

   void setVisibleWidthRatio(float var1) {
      this.mVisibleWidthRatio = var1;
   }
}
