package com.facebook.litho;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import com.facebook.litho.EventHandler;
import com.facebook.litho.InvisibleEvent;
import com.facebook.litho.UnfocusedVisibleEvent;
import com.facebook.litho.VisibilityChangedEvent;

class VisibilityItem {

   private static final int FLAG_BOTTOM_EDGE_VISIBLE = 16;
   private static final int FLAG_FOCUSED_RANGE = 32;
   private static final int FLAG_LEFT_EDGE_VISIBLE = 2;
   private static final int FLAG_RIGHT_EDGE_VISIBLE = 8;
   private static final int FLAG_TOP_EDGE_VISIBLE = 4;
   private boolean mDoNotClearInThisPass;
   private int mFlags = 0;
   private String mGlobalKey;
   @Nullable
   private EventHandler<InvisibleEvent> mInvisibleHandler = null;
   @Nullable
   private EventHandler<UnfocusedVisibleEvent> mUnfocusedHandler = null;
   @Nullable
   private EventHandler<VisibilityChangedEvent> mVisibilityChangedHandler = null;


   boolean doNotClearInThisPass() {
      return this.mDoNotClearInThisPass;
   }

   String getGlobalKey() {
      return this.mGlobalKey;
   }

   @Nullable
   EventHandler<InvisibleEvent> getInvisibleHandler() {
      return this.mInvisibleHandler;
   }

   @Nullable
   EventHandler<UnfocusedVisibleEvent> getUnfocusedHandler() {
      return this.mUnfocusedHandler;
   }

   @Nullable
   EventHandler<VisibilityChangedEvent> getVisibilityChangedHandler() {
      return this.mVisibilityChangedHandler;
   }

   boolean isInFocusedRange() {
      return (this.mFlags & 32) != 0;
   }

   boolean isInFullImpressionRange() {
      return (this.mFlags & 30) == 30;
   }

   void release() {
      this.mFlags = 0;
      this.mInvisibleHandler = null;
      this.mUnfocusedHandler = null;
      this.mVisibilityChangedHandler = null;
      this.mDoNotClearInThisPass = false;
   }

   void setDoNotClearInThisPass(boolean var1) {
      this.mDoNotClearInThisPass = var1;
   }

   void setFocusedRange(boolean var1) {
      if(var1) {
         this.mFlags |= 32;
      } else {
         this.mFlags &= -33;
      }
   }

   void setGlobalKey(String var1) {
      this.mGlobalKey = var1;
   }

   void setInvisibleHandler(EventHandler<InvisibleEvent> var1) {
      this.mInvisibleHandler = var1;
   }

   void setUnfocusedHandler(EventHandler<UnfocusedVisibleEvent> var1) {
      this.mUnfocusedHandler = var1;
   }

   void setVisibilityChangedHandler(@Nullable EventHandler<VisibilityChangedEvent> var1) {
      this.mVisibilityChangedHandler = var1;
   }

   void setVisibleEdges(Rect var1, Rect var2) {
      if(var1.top == var2.top) {
         this.mFlags |= 4;
      }

      if(var1.bottom == var2.bottom) {
         this.mFlags |= 16;
      }

      if(var1.left == var2.left) {
         this.mFlags |= 2;
      }

      if(var1.right == var2.right) {
         this.mFlags |= 8;
      }

   }
}
