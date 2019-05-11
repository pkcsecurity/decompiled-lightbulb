package com.facebook.litho;

import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.ViewOutlineProvider;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.CommonUtils;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.DispatchPopulateAccessibilityEventEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FocusChangedEvent;
import com.facebook.litho.InterceptTouchEvent;
import com.facebook.litho.InternalNode;
import com.facebook.litho.LongClickEvent;
import com.facebook.litho.OnInitializeAccessibilityEventEvent;
import com.facebook.litho.OnInitializeAccessibilityNodeInfoEvent;
import com.facebook.litho.OnPopulateAccessibilityEventEvent;
import com.facebook.litho.OnRequestSendAccessibilityEventEvent;
import com.facebook.litho.PerformAccessibilityActionEvent;
import com.facebook.litho.SendAccessibilityEventEvent;
import com.facebook.litho.SendAccessibilityEventUncheckedEvent;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.config.ComponentsConfiguration;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadConfined("ANY")
class NodeInfo {

   static final int ENABLED_SET_FALSE = 2;
   static final int ENABLED_SET_TRUE = 1;
   static final int ENABLED_UNSET = 0;
   static final int FOCUS_SET_FALSE = 2;
   static final int FOCUS_SET_TRUE = 1;
   static final int FOCUS_UNSET = 0;
   private static final int PFLAG_ACCESSIBILITY_ROLE_IS_SET = 4194304;
   private static final int PFLAG_ALPHA_IS_SET = 1048576;
   private static final int PFLAG_CLICK_HANDLER_IS_SET = 8;
   private static final int PFLAG_CLIP_CHILDREN_IS_SET = 8388608;
   private static final int PFLAG_CLIP_TO_OUTLINE_IS_SET = 65536;
   private static final int PFLAG_CONTENT_DESCRIPTION_IS_SET = 1;
   private static final int PFLAG_DISPATCH_POPULATE_ACCESSIBILITY_EVENT_HANDLER_IS_SET = 64;
   private static final int PFLAG_FOCUS_CHANGE_HANDLER_IS_SET = 131072;
   private static final int PFLAG_INTERCEPT_TOUCH_HANDLER_IS_SET = 262144;
   private static final int PFLAG_LONG_CLICK_HANDLER_IS_SET = 16;
   private static final int PFLAG_ON_INITIALIZE_ACCESSIBILITY_EVENT_HANDLER_IS_SET = 128;
   private static final int PFLAG_ON_INITIALIZE_ACCESSIBILITY_NODE_INFO_HANDLER_IS_SET = 256;
   private static final int PFLAG_ON_POPULATE_ACCESSIBILITY_EVENT_HANDLER_IS_SET = 512;
   private static final int PFLAG_ON_REQUEST_SEND_ACCESSIBILITY_EVENT_HANDLER_IS_SET = 1024;
   private static final int PFLAG_OUTINE_PROVIDER_IS_SET = 32768;
   private static final int PFLAG_PERFORM_ACCESSIBILITY_ACTION_HANDLER_IS_SET = 2048;
   private static final int PFLAG_ROTATION_IS_SET = 2097152;
   private static final int PFLAG_SCALE_IS_SET = 524288;
   private static final int PFLAG_SEND_ACCESSIBILITY_EVENT_HANDLER_IS_SET = 4096;
   private static final int PFLAG_SEND_ACCESSIBILITY_EVENT_UNCHECKED_HANDLER_IS_SET = 8192;
   private static final int PFLAG_SHADOW_ELEVATION_IS_SET = 16384;
   private static final int PFLAG_TOUCH_HANDLER_IS_SET = 32;
   private static final int PFLAG_VIEW_TAGS_IS_SET = 4;
   private static final int PFLAG_VIEW_TAG_IS_SET = 2;
   static final int SELECTED_SET_FALSE = 2;
   static final int SELECTED_SET_TRUE = 1;
   static final int SELECTED_UNSET = 0;
   private String mAccessibilityRole;
   private float mAlpha = 1.0F;
   private EventHandler<ClickEvent> mClickHandler;
   private boolean mClipChildren = true;
   private boolean mClipToOutline;
   private CharSequence mContentDescription;
   private EventHandler<DispatchPopulateAccessibilityEventEvent> mDispatchPopulateAccessibilityEventHandler;
   private int mEnabledState = 0;
   private EventHandler<FocusChangedEvent> mFocusChangeHandler;
   private int mFocusState = 0;
   private EventHandler<InterceptTouchEvent> mInterceptTouchHandler;
   private EventHandler<LongClickEvent> mLongClickHandler;
   private EventHandler<OnInitializeAccessibilityEventEvent> mOnInitializeAccessibilityEventHandler;
   private EventHandler<OnInitializeAccessibilityNodeInfoEvent> mOnInitializeAccessibilityNodeInfoHandler;
   private EventHandler<OnPopulateAccessibilityEventEvent> mOnPopulateAccessibilityEventHandler;
   private EventHandler<OnRequestSendAccessibilityEventEvent> mOnRequestSendAccessibilityEventHandler;
   private ViewOutlineProvider mOutlineProvider;
   private EventHandler<PerformAccessibilityActionEvent> mPerformAccessibilityActionHandler;
   private int mPrivateFlags;
   private final AtomicInteger mReferenceCount = new AtomicInteger(0);
   private float mRotation = 0.0F;
   private float mScale = 1.0F;
   private int mSelectedState = 0;
   private EventHandler<SendAccessibilityEventEvent> mSendAccessibilityEventHandler;
   private EventHandler<SendAccessibilityEventUncheckedEvent> mSendAccessibilityEventUncheckedHandler;
   private float mShadowElevation;
   private EventHandler<TouchEvent> mTouchHandler;
   private Object mViewTag;
   @Nullable
   private SparseArray<Object> mViewTags;


   static NodeInfo acquire() {
      NodeInfo var0 = ComponentsPools.acquireNodeInfo();
      if(var0.mReferenceCount.getAndSet(1) != 0) {
         throw new IllegalStateException("The NodeInfo reference acquired from the pool  wasn\'t correctly released.");
      } else {
         return var0;
      }
   }

   NodeInfo acquireRef() {
      if(this.mReferenceCount.getAndIncrement() < 1) {
         throw new IllegalStateException("The NodeInfo being acquired wasn\'t correctly initialized.");
      } else {
         return this;
      }
   }

   void copyInto(InternalNode var1) {
      if((this.mPrivateFlags & 8) != 0) {
         var1.clickHandler(this.mClickHandler);
      }

      if((this.mPrivateFlags & 16) != 0) {
         var1.longClickHandler(this.mLongClickHandler);
      }

      if((this.mPrivateFlags & 131072) != 0) {
         var1.focusChangeHandler(this.mFocusChangeHandler);
      }

      if((this.mPrivateFlags & 32) != 0) {
         var1.touchHandler(this.mTouchHandler);
      }

      if((this.mPrivateFlags & 262144) != 0) {
         var1.interceptTouchHandler(this.mInterceptTouchHandler);
      }

      if((this.mPrivateFlags & 4194304) != 0) {
         var1.accessibilityRole(this.mAccessibilityRole);
      }

      if((this.mPrivateFlags & 64) != 0) {
         var1.dispatchPopulateAccessibilityEventHandler(this.mDispatchPopulateAccessibilityEventHandler);
      }

      if((this.mPrivateFlags & 128) != 0) {
         var1.onInitializeAccessibilityEventHandler(this.mOnInitializeAccessibilityEventHandler);
      }

      if((this.mPrivateFlags & 256) != 0) {
         var1.onInitializeAccessibilityNodeInfoHandler(this.mOnInitializeAccessibilityNodeInfoHandler);
      }

      if((this.mPrivateFlags & 512) != 0) {
         var1.onPopulateAccessibilityEventHandler(this.mOnPopulateAccessibilityEventHandler);
      }

      if((this.mPrivateFlags & 1024) != 0) {
         var1.onRequestSendAccessibilityEventHandler(this.mOnRequestSendAccessibilityEventHandler);
      }

      if((this.mPrivateFlags & 2048) != 0) {
         var1.performAccessibilityActionHandler(this.mPerformAccessibilityActionHandler);
      }

      if((this.mPrivateFlags & 4096) != 0) {
         var1.sendAccessibilityEventHandler(this.mSendAccessibilityEventHandler);
      }

      if((this.mPrivateFlags & 8192) != 0) {
         var1.sendAccessibilityEventUncheckedHandler(this.mSendAccessibilityEventUncheckedHandler);
      }

      int var2 = this.mPrivateFlags;
      boolean var4 = true;
      if((var2 & 1) != 0) {
         var1.contentDescription(this.mContentDescription);
      }

      if((this.mPrivateFlags & 16384) != 0) {
         var1.shadowElevationPx(this.mShadowElevation);
      }

      if((this.mPrivateFlags & '\u8000') != 0) {
         var1.outlineProvider(this.mOutlineProvider);
      }

      if((this.mPrivateFlags & 65536) != 0) {
         var1.clipToOutline(this.mClipToOutline);
      }

      if((this.mPrivateFlags & 8388608) != 0) {
         var1.clipChildren(this.mClipChildren);
      }

      if(this.mViewTag != null) {
         var1.viewTag(this.mViewTag);
      }

      if(this.mViewTags != null) {
         var1.viewTags(this.mViewTags);
      }

      boolean var3;
      if(this.getFocusState() != 0) {
         if(this.getFocusState() == 1) {
            var3 = true;
         } else {
            var3 = false;
         }

         var1.focusable(var3);
      }

      if(this.getEnabledState() != 0) {
         if(this.getEnabledState() == 1) {
            var3 = true;
         } else {
            var3 = false;
         }

         var1.enabled(var3);
      }

      if(this.getSelectedState() != 0) {
         if(this.getSelectedState() == 1) {
            var3 = var4;
         } else {
            var3 = false;
         }

         var1.selected(var3);
      }

      if((this.mPrivateFlags & 524288) != 0) {
         var1.scale(this.mScale);
      }

      if((this.mPrivateFlags & 1048576) != 0) {
         var1.alpha(this.mAlpha);
      }

      if((this.mPrivateFlags & 2097152) != 0) {
         var1.rotation(this.mRotation);
      }

   }

   String getAccessibilityRole() {
      return this.mAccessibilityRole;
   }

   float getAlpha() {
      return this.mAlpha;
   }

   EventHandler<ClickEvent> getClickHandler() {
      return this.mClickHandler;
   }

   public boolean getClipChildren() {
      return this.mClipChildren;
   }

   public boolean getClipToOutline() {
      return this.mClipToOutline;
   }

   CharSequence getContentDescription() {
      return this.mContentDescription;
   }

   EventHandler<DispatchPopulateAccessibilityEventEvent> getDispatchPopulateAccessibilityEventHandler() {
      return this.mDispatchPopulateAccessibilityEventHandler;
   }

   int getEnabledState() {
      return this.mEnabledState;
   }

   EventHandler<FocusChangedEvent> getFocusChangeHandler() {
      return this.mFocusChangeHandler;
   }

   int getFocusState() {
      return this.mFocusState;
   }

   EventHandler<InterceptTouchEvent> getInterceptTouchHandler() {
      return this.mInterceptTouchHandler;
   }

   EventHandler<LongClickEvent> getLongClickHandler() {
      return this.mLongClickHandler;
   }

   EventHandler<OnInitializeAccessibilityEventEvent> getOnInitializeAccessibilityEventHandler() {
      return this.mOnInitializeAccessibilityEventHandler;
   }

   EventHandler<OnInitializeAccessibilityNodeInfoEvent> getOnInitializeAccessibilityNodeInfoHandler() {
      return this.mOnInitializeAccessibilityNodeInfoHandler;
   }

   EventHandler<OnPopulateAccessibilityEventEvent> getOnPopulateAccessibilityEventHandler() {
      return this.mOnPopulateAccessibilityEventHandler;
   }

   EventHandler<OnRequestSendAccessibilityEventEvent> getOnRequestSendAccessibilityEventHandler() {
      return this.mOnRequestSendAccessibilityEventHandler;
   }

   ViewOutlineProvider getOutlineProvider() {
      return this.mOutlineProvider;
   }

   EventHandler<PerformAccessibilityActionEvent> getPerformAccessibilityActionHandler() {
      return this.mPerformAccessibilityActionHandler;
   }

   float getRotation() {
      return this.mRotation;
   }

   float getScale() {
      return this.mScale;
   }

   int getSelectedState() {
      return this.mSelectedState;
   }

   EventHandler<SendAccessibilityEventEvent> getSendAccessibilityEventHandler() {
      return this.mSendAccessibilityEventHandler;
   }

   EventHandler<SendAccessibilityEventUncheckedEvent> getSendAccessibilityEventUncheckedHandler() {
      return this.mSendAccessibilityEventUncheckedHandler;
   }

   float getShadowElevation() {
      return this.mShadowElevation;
   }

   EventHandler<TouchEvent> getTouchHandler() {
      return this.mTouchHandler;
   }

   Object getViewTag() {
      return this.mViewTag;
   }

   @Nullable
   SparseArray<Object> getViewTags() {
      return this.mViewTags;
   }

   boolean hasFocusChangeHandler() {
      return this.mFocusChangeHandler != null;
   }

   boolean hasTouchEventHandlers() {
      return this.mClickHandler != null || this.mLongClickHandler != null || this.mTouchHandler != null || this.mInterceptTouchHandler != null;
   }

   boolean isAlphaSet() {
      return (this.mPrivateFlags & 1048576) != 0;
   }

   public boolean isClipChildrenSet() {
      return (this.mPrivateFlags & 8388608) != 0;
   }

   public boolean isEquivalentTo(NodeInfo var1) {
      return this == var1?true:(var1 == null?false:(!CommonUtils.equals(this.mAccessibilityRole, var1.mAccessibilityRole)?false:(this.mAlpha != var1.mAlpha?false:(!CommonUtils.equals(this.mClickHandler, var1.mClickHandler)?false:(this.mClipToOutline != var1.mClipToOutline?false:(this.mClipChildren != var1.mClipChildren?false:(!CommonUtils.equals(this.mContentDescription, var1.mContentDescription)?false:(!CommonUtils.equals(this.mDispatchPopulateAccessibilityEventHandler, var1.mDispatchPopulateAccessibilityEventHandler)?false:(this.mEnabledState != var1.mEnabledState?false:(!CommonUtils.equals(this.mFocusChangeHandler, var1.mFocusChangeHandler)?false:(this.mFocusState != var1.mFocusState?false:(!CommonUtils.equals(this.mInterceptTouchHandler, var1.mInterceptTouchHandler)?false:(!CommonUtils.equals(this.mLongClickHandler, var1.mLongClickHandler)?false:(!CommonUtils.equals(this.mOnInitializeAccessibilityEventHandler, var1.mOnInitializeAccessibilityEventHandler)?false:(!CommonUtils.equals(this.mOnInitializeAccessibilityNodeInfoHandler, var1.mOnInitializeAccessibilityNodeInfoHandler)?false:(!CommonUtils.equals(this.mOnPopulateAccessibilityEventHandler, var1.mOnPopulateAccessibilityEventHandler)?false:(!CommonUtils.equals(this.mOnRequestSendAccessibilityEventHandler, var1.mOnRequestSendAccessibilityEventHandler)?false:(!CommonUtils.equals(this.mOutlineProvider, var1.mOutlineProvider)?false:(!CommonUtils.equals(this.mPerformAccessibilityActionHandler, var1.mPerformAccessibilityActionHandler)?false:(this.mRotation != var1.mRotation?false:(this.mScale != var1.mScale?false:(this.mSelectedState != var1.mSelectedState?false:(!CommonUtils.equals(this.mSendAccessibilityEventHandler, var1.mSendAccessibilityEventHandler)?false:(!CommonUtils.equals(this.mSendAccessibilityEventUncheckedHandler, var1.mSendAccessibilityEventUncheckedHandler)?false:(this.mShadowElevation != var1.mShadowElevation?false:(!CommonUtils.equals(this.mTouchHandler, var1.mTouchHandler)?false:(!CommonUtils.equals(this.mViewTag, var1.mViewTag)?false:CommonUtils.equals(this.mViewTags, var1.mViewTags))))))))))))))))))))))))))));
   }

   boolean isRotationSet() {
      return (this.mPrivateFlags & 2097152) != 0;
   }

   boolean isScaleSet() {
      return (this.mPrivateFlags & 524288) != 0;
   }

   boolean needsAccessibilityDelegate() {
      return this.mOnInitializeAccessibilityEventHandler != null || this.mOnInitializeAccessibilityNodeInfoHandler != null || this.mOnPopulateAccessibilityEventHandler != null || this.mOnRequestSendAccessibilityEventHandler != null || this.mPerformAccessibilityActionHandler != null || this.mDispatchPopulateAccessibilityEventHandler != null || this.mSendAccessibilityEventHandler != null || this.mSendAccessibilityEventUncheckedHandler != null || this.mAccessibilityRole != null;
   }

   void release() {
      int var1 = this.mReferenceCount.decrementAndGet();
      if(var1 < 0) {
         throw new IllegalStateException("Trying to release a recycled NodeInfo.");
      } else if(var1 <= 0) {
         if(!ComponentsConfiguration.disablePools) {
            this.mContentDescription = null;
            this.mViewTag = null;
            this.mViewTags = null;
            this.mClickHandler = null;
            this.mLongClickHandler = null;
            this.mFocusChangeHandler = null;
            this.mTouchHandler = null;
            this.mInterceptTouchHandler = null;
            this.mAccessibilityRole = null;
            this.mDispatchPopulateAccessibilityEventHandler = null;
            this.mOnInitializeAccessibilityEventHandler = null;
            this.mOnPopulateAccessibilityEventHandler = null;
            this.mOnInitializeAccessibilityNodeInfoHandler = null;
            this.mOnRequestSendAccessibilityEventHandler = null;
            this.mPerformAccessibilityActionHandler = null;
            this.mSendAccessibilityEventHandler = null;
            this.mSendAccessibilityEventUncheckedHandler = null;
            this.mFocusState = 0;
            this.mEnabledState = 0;
            this.mSelectedState = 0;
            this.mPrivateFlags = 0;
            this.mShadowElevation = 0.0F;
            this.mOutlineProvider = null;
            this.mClipToOutline = false;
            this.mClipChildren = true;
            this.mScale = 1.0F;
            this.mAlpha = 1.0F;
            this.mRotation = 0.0F;
            ComponentsPools.release(this);
         }
      }
   }

   void setAccessibilityRole(String var1) {
      this.mPrivateFlags |= 4194304;
      this.mAccessibilityRole = var1;
   }

   void setAlpha(float var1) {
      this.mAlpha = var1;
      this.mPrivateFlags |= 1048576;
   }

   void setClickHandler(EventHandler<ClickEvent> var1) {
      this.mPrivateFlags |= 8;
      this.mClickHandler = var1;
   }

   public void setClipChildren(boolean var1) {
      this.mPrivateFlags |= 8388608;
      this.mClipChildren = var1;
   }

   public void setClipToOutline(boolean var1) {
      this.mPrivateFlags |= 65536;
      this.mClipToOutline = var1;
   }

   void setContentDescription(CharSequence var1) {
      this.mPrivateFlags |= 1;
      this.mContentDescription = var1;
   }

   void setDispatchPopulateAccessibilityEventHandler(EventHandler<DispatchPopulateAccessibilityEventEvent> var1) {
      this.mPrivateFlags |= 64;
      this.mDispatchPopulateAccessibilityEventHandler = var1;
   }

   void setEnabled(boolean var1) {
      if(var1) {
         this.mEnabledState = 1;
      } else {
         this.mEnabledState = 2;
      }
   }

   void setFocusChangeHandler(EventHandler<FocusChangedEvent> var1) {
      this.mPrivateFlags |= 131072;
      this.mFocusChangeHandler = var1;
   }

   void setFocusable(boolean var1) {
      if(var1) {
         this.mFocusState = 1;
      } else {
         this.mFocusState = 2;
      }
   }

   void setInterceptTouchHandler(EventHandler<InterceptTouchEvent> var1) {
      this.mPrivateFlags |= 262144;
      this.mInterceptTouchHandler = var1;
   }

   void setLongClickHandler(EventHandler<LongClickEvent> var1) {
      this.mPrivateFlags |= 16;
      this.mLongClickHandler = var1;
   }

   void setOnInitializeAccessibilityEventHandler(EventHandler<OnInitializeAccessibilityEventEvent> var1) {
      this.mPrivateFlags |= 128;
      this.mOnInitializeAccessibilityEventHandler = var1;
   }

   void setOnInitializeAccessibilityNodeInfoHandler(EventHandler<OnInitializeAccessibilityNodeInfoEvent> var1) {
      this.mPrivateFlags |= 256;
      this.mOnInitializeAccessibilityNodeInfoHandler = var1;
   }

   void setOnPopulateAccessibilityEventHandler(EventHandler<OnPopulateAccessibilityEventEvent> var1) {
      this.mPrivateFlags |= 512;
      this.mOnPopulateAccessibilityEventHandler = var1;
   }

   void setOnRequestSendAccessibilityEventHandler(EventHandler<OnRequestSendAccessibilityEventEvent> var1) {
      this.mPrivateFlags |= 1024;
      this.mOnRequestSendAccessibilityEventHandler = var1;
   }

   public void setOutlineProvider(ViewOutlineProvider var1) {
      this.mPrivateFlags |= '\u8000';
      this.mOutlineProvider = var1;
   }

   void setPerformAccessibilityActionHandler(EventHandler<PerformAccessibilityActionEvent> var1) {
      this.mPrivateFlags |= 2048;
      this.mPerformAccessibilityActionHandler = var1;
   }

   void setRotation(float var1) {
      this.mRotation = var1;
      this.mPrivateFlags |= 2097152;
   }

   void setScale(float var1) {
      this.mScale = var1;
      this.mPrivateFlags |= 524288;
   }

   void setSelected(boolean var1) {
      if(var1) {
         this.mSelectedState = 1;
      } else {
         this.mSelectedState = 2;
      }
   }

   void setSendAccessibilityEventHandler(EventHandler<SendAccessibilityEventEvent> var1) {
      this.mPrivateFlags |= 4096;
      this.mSendAccessibilityEventHandler = var1;
   }

   void setSendAccessibilityEventUncheckedHandler(EventHandler<SendAccessibilityEventUncheckedEvent> var1) {
      this.mPrivateFlags |= 8192;
      this.mSendAccessibilityEventUncheckedHandler = var1;
   }

   public void setShadowElevation(float var1) {
      this.mPrivateFlags |= 16384;
      this.mShadowElevation = var1;
   }

   void setTouchHandler(EventHandler<TouchEvent> var1) {
      this.mPrivateFlags |= 32;
      this.mTouchHandler = var1;
   }

   void setViewTag(Object var1) {
      this.mPrivateFlags |= 2;
      this.mViewTag = var1;
   }

   void setViewTags(SparseArray<Object> var1) {
      this.mPrivateFlags |= 4;
      this.mViewTags = var1;
   }

   void updateWith(NodeInfo var1) {
      if((var1.mPrivateFlags & 8) != 0) {
         this.mClickHandler = var1.mClickHandler;
      }

      if((var1.mPrivateFlags & 16) != 0) {
         this.mLongClickHandler = var1.mLongClickHandler;
      }

      if((var1.mPrivateFlags & 131072) != 0) {
         this.mFocusChangeHandler = var1.mFocusChangeHandler;
      }

      if((var1.mPrivateFlags & 32) != 0) {
         this.mTouchHandler = var1.mTouchHandler;
      }

      if((var1.mPrivateFlags & 262144) != 0) {
         this.mInterceptTouchHandler = var1.mInterceptTouchHandler;
      }

      if((var1.mPrivateFlags & 4194304) != 0) {
         this.mAccessibilityRole = var1.mAccessibilityRole;
      }

      if((var1.mPrivateFlags & 64) != 0) {
         this.mDispatchPopulateAccessibilityEventHandler = var1.mDispatchPopulateAccessibilityEventHandler;
      }

      if((var1.mPrivateFlags & 128) != 0) {
         this.mOnInitializeAccessibilityEventHandler = var1.mOnInitializeAccessibilityEventHandler;
      }

      if((var1.mPrivateFlags & 256) != 0) {
         this.mOnInitializeAccessibilityNodeInfoHandler = var1.mOnInitializeAccessibilityNodeInfoHandler;
      }

      if((var1.mPrivateFlags & 512) != 0) {
         this.mOnPopulateAccessibilityEventHandler = var1.mOnPopulateAccessibilityEventHandler;
      }

      if((var1.mPrivateFlags & 1024) != 0) {
         this.mOnRequestSendAccessibilityEventHandler = var1.mOnRequestSendAccessibilityEventHandler;
      }

      if((var1.mPrivateFlags & 2048) != 0) {
         this.mPerformAccessibilityActionHandler = var1.mPerformAccessibilityActionHandler;
      }

      if((var1.mPrivateFlags & 4096) != 0) {
         this.mSendAccessibilityEventHandler = var1.mSendAccessibilityEventHandler;
      }

      if((var1.mPrivateFlags & 8192) != 0) {
         this.mSendAccessibilityEventUncheckedHandler = var1.mSendAccessibilityEventUncheckedHandler;
      }

      if((var1.mPrivateFlags & 1) != 0) {
         this.mContentDescription = var1.mContentDescription;
      }

      if((var1.mPrivateFlags & 16384) != 0) {
         this.mShadowElevation = var1.mShadowElevation;
      }

      if((var1.mPrivateFlags & '\u8000') != 0) {
         this.mOutlineProvider = var1.mOutlineProvider;
      }

      if((var1.mPrivateFlags & 65536) != 0) {
         this.mClipToOutline = var1.mClipToOutline;
      }

      if(var1.isClipChildrenSet()) {
         this.setClipChildren(var1.mClipChildren);
      }

      if(var1.mViewTag != null) {
         this.mViewTag = var1.mViewTag;
      }

      if(var1.mViewTags != null) {
         this.mViewTags = var1.mViewTags;
      }

      if(var1.getFocusState() != 0) {
         this.mFocusState = var1.getFocusState();
      }

      if(var1.getEnabledState() != 0) {
         this.mEnabledState = var1.getEnabledState();
      }

      if(var1.getSelectedState() != 0) {
         this.mSelectedState = var1.getSelectedState();
      }

      if((var1.mPrivateFlags & 524288) != 0) {
         this.mScale = var1.mScale;
      }

      if((var1.mPrivateFlags & 1048576) != 0) {
         this.mAlpha = var1.mAlpha;
      }

      if((var1.mPrivateFlags & 2097152) != 0) {
         this.mRotation = var1.mRotation;
      }

   }
}
