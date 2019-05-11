package com.facebook.litho.sections;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.widget.SmoothScrollAlignmentType;

class FocusDispatcher {

   @Nullable
   private FocusDispatcher.FocusRequest mFocusRequest;
   @Nullable
   private LoadingEvent.LoadingState mLoadingState;
   private final SectionTree.Target mTarget;
   private boolean mWaitForDataBound;


   FocusDispatcher(SectionTree.Target var1) {
      this.mTarget = var1;
   }

   private void queueRequest(int var1, int var2) {
      this.mFocusRequest = new FocusDispatcher.FocusRequest(var1, var2, null);
   }

   private boolean shouldDispatchRequests() {
      return this.isLoadingCompleted() && !this.mWaitForDataBound;
   }

   @UiThread
   boolean isLoadingCompleted() {
      return this.mLoadingState == null || this.mLoadingState == LoadingEvent.LoadingState.FAILED || this.mLoadingState == LoadingEvent.LoadingState.SUCCEEDED;
   }

   @UiThread
   void maybeDispatchFocusRequests() {
      if(this.mFocusRequest != null) {
         if(this.shouldDispatchRequests()) {
            this.mTarget.requestFocusWithOffset(this.mFocusRequest.index, this.mFocusRequest.offset);
            this.mFocusRequest = null;
         }
      }
   }

   @UiThread
   void requestFocus(int var1) {
      this.requestFocusWithOffset(var1, 0);
   }

   @UiThread
   void requestFocusWithOffset(int var1, int var2) {
      if(this.shouldDispatchRequests()) {
         this.mTarget.requestFocusWithOffset(var1, var2);
      } else {
         this.queueRequest(var1, var2);
      }
   }

   @UiThread
   void requestSmoothFocus(int var1, int var2, SmoothScrollAlignmentType var3) {
      if(this.shouldDispatchRequests()) {
         this.mTarget.requestSmoothFocus(var1, var2, var3);
      } else {
         this.queueRequest(var1, 0);
      }
   }

   @UiThread
   void setLoadingState(LoadingEvent.LoadingState var1) {
      this.mLoadingState = var1;
   }

   @UiThread
   void waitForDataBound(boolean var1) {
      this.mWaitForDataBound = var1;
   }

   static class FocusRequest {

      private final int index;
      private final int offset;


      private FocusRequest(int var1, int var2) {
         this.index = var1;
         this.offset = var2;
      }

      // $FF: synthetic method
      FocusRequest(int var1, int var2, Object var3) {
         this(var1, var2);
      }
   }
}
