package com.facebook.litho;

import android.animation.StateListAnimator;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.StyleRes;
import android.util.SparseArray;
import android.view.ViewOutlineProvider;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.litho.ArrayBatchAllocator;
import com.facebook.litho.Border;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.CommonProps;
import com.facebook.litho.CommonPropsCopyable;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.DispatchPopulateAccessibilityEventEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FocusChangedEvent;
import com.facebook.litho.FocusedVisibleEvent;
import com.facebook.litho.FullImpressionVisibleEvent;
import com.facebook.litho.InterceptTouchEvent;
import com.facebook.litho.InternalNode;
import com.facebook.litho.InvisibleEvent;
import com.facebook.litho.LongClickEvent;
import com.facebook.litho.NodeInfo;
import com.facebook.litho.OnInitializeAccessibilityEventEvent;
import com.facebook.litho.OnInitializeAccessibilityNodeInfoEvent;
import com.facebook.litho.OnPopulateAccessibilityEventEvent;
import com.facebook.litho.OnRequestSendAccessibilityEventEvent;
import com.facebook.litho.PerformAccessibilityActionEvent;
import com.facebook.litho.SendAccessibilityEventEvent;
import com.facebook.litho.SendAccessibilityEventUncheckedEvent;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.UnfocusedVisibleEvent;
import com.facebook.litho.VisibilityChangedEvent;
import com.facebook.litho.VisibleEvent;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.reference.Reference;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadConfined("ANY")
class CommonPropsHolder implements CommonProps, CommonPropsCopyable {

   private static final byte PFLAG_BACKGROUND_IS_SET = 32;
   private static final byte PFLAG_HEIGHT_IS_SET = 16;
   private static final byte PFLAG_POSITION_IS_SET = 4;
   private static final byte PFLAG_POSITION_TYPE_IS_SET = 2;
   private static final byte PFLAG_TEST_KEY_IS_SET = 64;
   private static final byte PFLAG_WIDTH_IS_SET = 8;
   @Nullable
   private Reference<? extends Drawable> mBackground;
   @AttrRes
   private int mDefStyleAttr;
   @StyleRes
   private int mDefStyleRes;
   private int mHeightPx;
   @Nullable
   private NodeInfo mNodeInfo;
   @Nullable
   private CommonPropsHolder.OtherProps mOtherProps;
   @Nullable
   private YogaPositionType mPositionType;
   @Nullable
   private CommonPropsHolder.YogaEdgesWithInts mPositions;
   private byte mPrivateFlags;
   @Nullable
   private String mTestKey;
   private int mWidthPx;
   private boolean mWrapInView;


   private NodeInfo getOrCreateNodeInfo() {
      if(this.mNodeInfo == null) {
         this.mNodeInfo = NodeInfo.acquire();
      }

      return this.mNodeInfo;
   }

   private CommonPropsHolder.OtherProps getOrCreateOtherProps() {
      if(this.mOtherProps == null) {
         this.mOtherProps = new CommonPropsHolder.OtherProps(null);
      }

      return this.mOtherProps;
   }

   void accessibilityRole(@Nullable String var1) {
      this.getOrCreateNodeInfo().setAccessibilityRole(var1);
   }

   void alignSelf(@Nullable YogaAlign var1) {
      this.getOrCreateOtherProps().alignSelf(var1);
   }

   void alpha(float var1) {
      this.getOrCreateNodeInfo().setAlpha(var1);
   }

   void aspectRatio(float var1) {
      this.getOrCreateOtherProps().aspectRatio(var1);
   }

   void background(@Nullable Reference<? extends Drawable> var1) {
      this.mPrivateFlags = (byte)(this.mPrivateFlags | 32);
      this.mBackground = var1;
   }

   void border(Border var1) {
      this.getOrCreateOtherProps().border(var1);
   }

   void clickHandler(EventHandler<ClickEvent> var1) {
      this.getOrCreateNodeInfo().setClickHandler(var1);
   }

   void clipChildren(boolean var1) {
      this.getOrCreateNodeInfo().setClipChildren(var1);
   }

   void clipToOutline(boolean var1) {
      this.getOrCreateNodeInfo().setClipToOutline(var1);
   }

   void contentDescription(@Nullable CharSequence var1) {
      this.getOrCreateNodeInfo().setContentDescription(var1);
   }

   public void copyInto(ComponentContext var1, InternalNode var2) {
      var1.applyStyle(var2, this.mDefStyleAttr, this.mDefStyleRes);
      if(this.mNodeInfo != null) {
         this.mNodeInfo.copyInto(var2);
      }

      if((long)(this.mPrivateFlags & 32) != 0L) {
         var2.background(this.mBackground);
      }

      if((long)(this.mPrivateFlags & 64) != 0L) {
         var2.testKey(this.mTestKey);
      }

      if((long)(this.mPrivateFlags & 2) != 0L) {
         var2.positionType(this.mPositionType);
      }

      if((long)(this.mPrivateFlags & 4) != 0L) {
         for(int var3 = 0; var3 < this.mPositions.size(); ++var3) {
            var2.positionPx(this.mPositions.getEdge(var3), this.mPositions.getValue(var3));
         }
      }

      if((long)(this.mPrivateFlags & 8) != 0L) {
         var2.widthPx(this.mWidthPx);
      }

      if((long)(this.mPrivateFlags & 16) != 0L) {
         var2.heightPx(this.mHeightPx);
      }

      if(this.mWrapInView) {
         var2.wrapInView();
      }

      if(this.mOtherProps != null) {
         this.mOtherProps.copyInto(var2);
      }

   }

   void dispatchPopulateAccessibilityEventHandler(@Nullable EventHandler<DispatchPopulateAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setDispatchPopulateAccessibilityEventHandler(var1);
   }

   void duplicateParentState(boolean var1) {
      this.getOrCreateOtherProps().duplicateParentState(var1);
   }

   void enabled(boolean var1) {
      this.getOrCreateNodeInfo().setEnabled(var1);
   }

   void flex(float var1) {
      this.getOrCreateOtherProps().flex(var1);
   }

   void flexBasisPercent(float var1) {
      this.getOrCreateOtherProps().flexBasisPercent(var1);
   }

   void flexBasisPx(@Px int var1) {
      this.getOrCreateOtherProps().flexBasisPx(var1);
   }

   void flexGrow(float var1) {
      this.getOrCreateOtherProps().flexGrow(var1);
   }

   void flexShrink(float var1) {
      this.getOrCreateOtherProps().flexShrink(var1);
   }

   void focusChangeHandler(EventHandler<FocusChangedEvent> var1) {
      this.getOrCreateNodeInfo().setFocusChangeHandler(var1);
   }

   void focusable(boolean var1) {
      this.getOrCreateNodeInfo().setFocusable(var1);
   }

   void focusedHandler(@Nullable EventHandler<FocusedVisibleEvent> var1) {
      this.getOrCreateOtherProps().focusedHandler(var1);
   }

   void foreground(@Nullable ComparableDrawable var1) {
      this.getOrCreateOtherProps().foreground(var1);
   }

   void fullImpressionHandler(@Nullable EventHandler<FullImpressionVisibleEvent> var1) {
      this.getOrCreateOtherProps().fullImpressionHandler(var1);
   }

   @Nullable
   public Reference<? extends Drawable> getBackground() {
      return this.mBackground;
   }

   @Nullable
   public EventHandler<ClickEvent> getClickHandler() {
      return this.getOrCreateNodeInfo().getClickHandler();
   }

   @Nullable
   public EventHandler<FocusChangedEvent> getFocusChangeHandler() {
      return this.getOrCreateNodeInfo().getFocusChangeHandler();
   }

   public boolean getFocusable() {
      return this.getOrCreateNodeInfo().getFocusState() == 1;
   }

   @Nullable
   public EventHandler<InterceptTouchEvent> getInterceptTouchHandler() {
      return this.getOrCreateNodeInfo().getInterceptTouchHandler();
   }

   @Nullable
   public EventHandler<LongClickEvent> getLongClickHandler() {
      return this.getOrCreateNodeInfo().getLongClickHandler();
   }

   @Nullable
   public EventHandler<TouchEvent> getTouchHandler() {
      return this.getOrCreateNodeInfo().getTouchHandler();
   }

   @Nullable
   public String getTransitionKey() {
      return this.getOrCreateOtherProps().mTransitionKey;
   }

   void heightPercent(float var1) {
      this.getOrCreateOtherProps().heightPercent(var1);
   }

   void heightPx(@Px int var1) {
      this.mPrivateFlags = (byte)(this.mPrivateFlags | 16);
      this.mHeightPx = var1;
   }

   void importantForAccessibility(int var1) {
      this.getOrCreateOtherProps().importantForAccessibility(var1);
   }

   void interceptTouchHandler(EventHandler<InterceptTouchEvent> var1) {
      this.getOrCreateNodeInfo().setInterceptTouchHandler(var1);
   }

   void invisibleHandler(@Nullable EventHandler<InvisibleEvent> var1) {
      this.getOrCreateOtherProps().invisibleHandler(var1);
   }

   void layoutDirection(@Nullable YogaDirection var1) {
      this.getOrCreateOtherProps().layoutDirection(var1);
   }

   void longClickHandler(EventHandler<LongClickEvent> var1) {
      this.getOrCreateNodeInfo().setLongClickHandler(var1);
   }

   void marginAuto(YogaEdge var1) {
      this.getOrCreateOtherProps().marginAuto(var1);
   }

   void marginPercent(YogaEdge var1, float var2) {
      this.getOrCreateOtherProps().marginPercent(var1, var2);
   }

   void marginPx(YogaEdge var1, @Px int var2) {
      this.getOrCreateOtherProps().marginPx(var1, var2);
   }

   void maxHeightPercent(float var1) {
      this.getOrCreateOtherProps().maxHeightPercent(var1);
   }

   void maxHeightPx(@Px int var1) {
      this.getOrCreateOtherProps().maxHeightPx(var1);
   }

   void maxWidthPercent(float var1) {
      this.getOrCreateOtherProps().maxWidthPercent(var1);
   }

   void maxWidthPx(@Px int var1) {
      this.getOrCreateOtherProps().maxWidthPx(var1);
   }

   void minHeightPercent(float var1) {
      this.getOrCreateOtherProps().minHeightPercent(var1);
   }

   void minHeightPx(@Px int var1) {
      this.getOrCreateOtherProps().minHeightPx(var1);
   }

   void minWidthPercent(float var1) {
      this.getOrCreateOtherProps().minWidthPercent(var1);
   }

   void minWidthPx(@Px int var1) {
      this.getOrCreateOtherProps().minWidthPx(var1);
   }

   void onInitializeAccessibilityEventHandler(@Nullable EventHandler<OnInitializeAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setOnInitializeAccessibilityEventHandler(var1);
   }

   void onInitializeAccessibilityNodeInfoHandler(@Nullable EventHandler<OnInitializeAccessibilityNodeInfoEvent> var1) {
      this.getOrCreateNodeInfo().setOnInitializeAccessibilityNodeInfoHandler(var1);
   }

   void onPopulateAccessibilityEventHandler(@Nullable EventHandler<OnPopulateAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setOnPopulateAccessibilityEventHandler(var1);
   }

   void onRequestSendAccessibilityEventHandler(@Nullable EventHandler<OnRequestSendAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setOnRequestSendAccessibilityEventHandler(var1);
   }

   void outlineProvider(@Nullable ViewOutlineProvider var1) {
      this.getOrCreateNodeInfo().setOutlineProvider(var1);
   }

   void paddingPercent(YogaEdge var1, float var2) {
      this.getOrCreateOtherProps().paddingPercent(var1, var2);
   }

   void paddingPx(YogaEdge var1, @Px int var2) {
      this.getOrCreateOtherProps().paddingPx(var1, var2);
   }

   void performAccessibilityActionHandler(@Nullable EventHandler<PerformAccessibilityActionEvent> var1) {
      this.getOrCreateNodeInfo().setPerformAccessibilityActionHandler(var1);
   }

   void positionPercent(@Nullable YogaEdge var1, float var2) {
      this.getOrCreateOtherProps().positionPercent(var1, var2);
   }

   void positionPx(@Nullable YogaEdge var1, @Px int var2) {
      this.mPrivateFlags = (byte)(this.mPrivateFlags | 4);
      if(this.mPositions == null) {
         this.mPositions = new CommonPropsHolder.YogaEdgesWithInts();
      }

      this.mPositions.add(var1, var2);
   }

   void positionType(@Nullable YogaPositionType var1) {
      this.mPrivateFlags = (byte)(this.mPrivateFlags | 2);
      this.mPositionType = var1;
   }

   void rotation(float var1) {
      this.getOrCreateNodeInfo().setRotation(var1);
   }

   void scale(float var1) {
      this.getOrCreateNodeInfo().setScale(var1);
   }

   void selected(boolean var1) {
      this.getOrCreateNodeInfo().setSelected(var1);
   }

   void sendAccessibilityEventHandler(@Nullable EventHandler<SendAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setSendAccessibilityEventHandler(var1);
   }

   void sendAccessibilityEventUncheckedHandler(@Nullable EventHandler<SendAccessibilityEventUncheckedEvent> var1) {
      this.getOrCreateNodeInfo().setSendAccessibilityEventUncheckedHandler(var1);
   }

   void setStyle(@AttrRes int var1, @StyleRes int var2) {
      this.mDefStyleAttr = var1;
      this.mDefStyleRes = var2;
   }

   void shadowElevationPx(float var1) {
      this.getOrCreateNodeInfo().setShadowElevation(var1);
   }

   void stateListAnimator(@Nullable StateListAnimator var1) {
      this.getOrCreateOtherProps().stateListAnimator(var1);
   }

   void stateListAnimatorRes(@DrawableRes int var1) {
      this.getOrCreateOtherProps().stateListAnimatorRes(var1);
   }

   void testKey(String var1) {
      this.mPrivateFlags = (byte)(this.mPrivateFlags | 64);
      this.mTestKey = var1;
   }

   void touchExpansionPx(@Nullable YogaEdge var1, @Px int var2) {
      this.getOrCreateOtherProps().touchExpansionPx(var1, var2);
   }

   void touchHandler(EventHandler<TouchEvent> var1) {
      this.getOrCreateNodeInfo().setTouchHandler(var1);
   }

   void transitionKey(@Nullable String var1) {
      this.getOrCreateOtherProps().transitionKey(var1);
   }

   void unfocusedHandler(@Nullable EventHandler<UnfocusedVisibleEvent> var1) {
      this.getOrCreateOtherProps().unfocusedHandler(var1);
   }

   void viewTag(@Nullable Object var1) {
      this.getOrCreateNodeInfo().setViewTag(var1);
   }

   void viewTags(@Nullable SparseArray<Object> var1) {
      this.getOrCreateNodeInfo().setViewTags(var1);
   }

   void visibilityChangedHandler(@Nullable EventHandler<VisibilityChangedEvent> var1) {
      this.getOrCreateOtherProps().visibilityChangedHandler(var1);
   }

   void visibleHandler(@Nullable EventHandler<VisibleEvent> var1) {
      this.getOrCreateOtherProps().visibleHandler(var1);
   }

   void visibleHeightRatio(float var1) {
      this.getOrCreateOtherProps().visibleHeightRatio(var1);
   }

   void visibleWidthRatio(float var1) {
      this.getOrCreateOtherProps().visibleWidthRatio(var1);
   }

   void widthPercent(float var1) {
      this.getOrCreateOtherProps().widthPercent(var1);
   }

   void widthPx(@Px int var1) {
      this.mPrivateFlags = (byte)(this.mPrivateFlags | 8);
      this.mWidthPx = var1;
   }

   void wrapInView() {
      this.mWrapInView = true;
   }

   static class YogaEdgesWithInts {

      private long mEdges;
      @Nullable
      private int[] mValues;


      private int addEdge(YogaEdge var1) {
         int var2 = this.lookup(0);
         int var3 = var2 + 1;
         this.insert(var3, var1.intValue());
         this.insert(0, var3);
         return var2;
      }

      private static int[] createArray(int var0) {
         return ComponentsConfiguration.variableArrayBatchAllocatorEnabled && var0 == 2?ArrayBatchAllocator.newArrayOfSize2():new int[var0];
      }

      private void insert(int var1, int var2) {
         long var3 = this.mEdges;
         var1 *= 4;
         this.mEdges = var3 & ~(15L << var1);
         this.mEdges |= (long)var2 << var1;
      }

      private int lookup(int var1) {
         return (int)(this.mEdges >> var1 * 4 & 15L);
      }

      public void add(YogaEdge var1, int var2) {
         int var3 = this.addEdge(var1);
         if(var2 != 0) {
            if(this.mValues == null) {
               this.mValues = createArray(Math.max(2, var3 + 1));
            } else if(var3 >= this.mValues.length) {
               int[] var4 = this.mValues;
               this.mValues = new int[Math.min(var4.length * 2, YogaEdge.values().length)];
               System.arraycopy(var4, 0, this.mValues, 0, var4.length);
            }

            this.mValues[var3] = var2;
         }

      }

      public YogaEdge getEdge(int var1) {
         return YogaEdge.fromInt(this.lookup(var1 + 1));
      }

      public int getValue(int var1) {
         return this.mValues != null && this.mValues.length > var1?this.mValues[var1]:0;
      }

      public int size() {
         return this.lookup(0);
      }
   }

   static class YogaEdgesWithFloats {

      private YogaEdge[] mEdges;
      private int mNumEntries;
      private int mSize;
      private float[] mValues;


      private YogaEdgesWithFloats() {
         this.mEdges = new YogaEdge[2];
         this.mValues = new float[2];
         this.mSize = 2;
      }

      // $FF: synthetic method
      YogaEdgesWithFloats(Object var1) {
         this();
      }

      private void add(YogaEdge var1, float var2) {
         if(this.mNumEntries == this.mSize) {
            this.increaseSize();
         }

         this.mEdges[this.mNumEntries] = var1;
         this.mValues[this.mNumEntries] = var2;
         ++this.mNumEntries;
      }

      private void increaseSize() {
         YogaEdge[] var1 = this.mEdges;
         float[] var2 = this.mValues;
         this.mSize *= 2;
         this.mEdges = new YogaEdge[this.mSize];
         this.mValues = new float[this.mSize];
         System.arraycopy(var1, 0, this.mEdges, 0, this.mNumEntries);
         System.arraycopy(var2, 0, this.mValues, 0, this.mNumEntries);
      }
   }

   static class OtherProps {

      private static final long PFLAG_ALIGN_SELF_IS_SET = 2L;
      private static final long PFLAG_ASPECT_RATIO_IS_SET = 4194304L;
      private static final long PFLAG_BORDER_IS_SET = 137438953472L;
      private static final long PFLAG_DUPLICATE_PARENT_STATE_IS_SET = 128L;
      private static final long PFLAG_FLEX_BASIS_IS_SET = 32L;
      private static final long PFLAG_FLEX_BASIS_PERCENT_IS_SET = 134217728L;
      private static final long PFLAG_FLEX_GROW_IS_SET = 8L;
      private static final long PFLAG_FLEX_IS_SET = 4L;
      private static final long PFLAG_FLEX_SHRINK_IS_SET = 16L;
      private static final long PFLAG_FOCUSED_HANDLER_IS_SET = 131072L;
      private static final long PFLAG_FOREGROUND_IS_SET = 32768L;
      private static final long PFLAG_FULL_IMPRESSION_HANDLER_IS_SET = 262144L;
      private static final long PFLAG_HEIGHT_PERCENT_IS_SET = 17179869184L;
      private static final long PFLAG_IMPORTANT_FOR_ACCESSIBILITY_IS_SET = 64L;
      private static final long PFLAG_INVISIBLE_HANDLER_IS_SET = 524288L;
      private static final long PFLAG_LAYOUT_DIRECTION_IS_SET = 1L;
      private static final long PFLAG_MARGIN_AUTO_IS_SET = 536870912L;
      private static final long PFLAG_MARGIN_IS_SET = 256L;
      private static final long PFLAG_MARGIN_PERCENT_IS_SET = 268435456L;
      private static final long PFLAG_MAX_HEIGHT_IS_SET = 16384L;
      private static final long PFLAG_MAX_HEIGHT_PERCENT_IS_SET = 68719476736L;
      private static final long PFLAG_MAX_WIDTH_IS_SET = 4096L;
      private static final long PFLAG_MAX_WIDTH_PERCENT_IS_SET = 8589934592L;
      private static final long PFLAG_MIN_HEIGHT_IS_SET = 8192L;
      private static final long PFLAG_MIN_HEIGHT_PERCENT_IS_SET = 34359738368L;
      private static final long PFLAG_MIN_WIDTH_IS_SET = 2048L;
      private static final long PFLAG_MIN_WIDTH_PERCENT_IS_SET = 4294967296L;
      private static final long PFLAG_PADDING_IS_SET = 512L;
      private static final long PFLAG_PADDING_PERCENT_IS_SET = 1073741824L;
      private static final long PFLAG_POSITION_PERCENT_IS_SET = 1024L;
      private static final long PFLAG_STATE_LIST_ANIMATOR_IS_SET = 274877906944L;
      private static final long PFLAG_STATE_LIST_ANIMATOR_RES_IS_SET = 549755813888L;
      private static final long PFLAG_TOUCH_EXPANSION_IS_SET = 2097152L;
      private static final long PFLAG_TRANSITION_KEY_IS_SET = 8388608L;
      private static final long PFLAG_UNFOCUSED_HANDLER_IS_SET = 1048576L;
      private static final long PFLAG_VISIBILITY_CHANGED_HANDLER_IS_SET = 1099511627776L;
      private static final long PFLAG_VISIBLE_HANDLER_IS_SET = 65536L;
      private static final long PFLAG_VISIBLE_HEIGHT_RATIO_IS_SET = 33554432L;
      private static final long PFLAG_VISIBLE_WIDTH_RATIO_IS_SET = 67108864L;
      private static final long PFLAG_WIDTH_PERCENT_IS_SET = 2147483648L;
      private static final long PFLAG_WRAP_IN_VIEW_IS_SET = 16777216L;
      @Nullable
      private YogaAlign mAlignSelf;
      private float mAspectRatio;
      @Nullable
      private Border mBorder;
      private boolean mDuplicateParentState;
      private float mFlex;
      private float mFlexBasisPercent;
      @Px
      private int mFlexBasisPx;
      private float mFlexGrow;
      private float mFlexShrink;
      @Nullable
      private EventHandler<FocusedVisibleEvent> mFocusedHandler;
      @Nullable
      private ComparableDrawable mForeground;
      @Nullable
      private EventHandler<FullImpressionVisibleEvent> mFullImpressionHandler;
      private float mHeightPercent;
      private int mImportantForAccessibility;
      @Nullable
      private EventHandler<InvisibleEvent> mInvisibleHandler;
      @Nullable
      private YogaDirection mLayoutDirection;
      @Nullable
      private List<YogaEdge> mMarginAutos;
      @Nullable
      private CommonPropsHolder.YogaEdgesWithFloats mMarginPercents;
      @Nullable
      private CommonPropsHolder.YogaEdgesWithInts mMargins;
      private float mMaxHeightPercent;
      @Px
      private int mMaxHeightPx;
      private float mMaxWidthPercent;
      @Px
      private int mMaxWidthPx;
      private float mMinHeightPercent;
      @Px
      private int mMinHeightPx;
      private float mMinWidthPercent;
      @Px
      private int mMinWidthPx;
      @Nullable
      private CommonPropsHolder.YogaEdgesWithFloats mPaddingPercents;
      @Nullable
      private CommonPropsHolder.YogaEdgesWithInts mPaddings;
      @Nullable
      private CommonPropsHolder.YogaEdgesWithFloats mPositionPercents;
      private long mPrivateFlags;
      @Nullable
      private StateListAnimator mStateListAnimator;
      @DrawableRes
      private int mStateListAnimatorRes;
      @Nullable
      private CommonPropsHolder.YogaEdgesWithInts mTouchExpansions;
      @Nullable
      private String mTransitionKey;
      @Nullable
      private EventHandler<UnfocusedVisibleEvent> mUnfocusedHandler;
      @Nullable
      private EventHandler<VisibilityChangedEvent> mVisibilityChangedHandler;
      @Nullable
      private EventHandler<VisibleEvent> mVisibleHandler;
      private float mVisibleHeightRatio;
      private float mVisibleWidthRatio;
      private float mWidthPercent;


      private OtherProps() {}

      // $FF: synthetic method
      OtherProps(Object var1) {
         this();
      }

      private void alignSelf(YogaAlign var1) {
         this.mPrivateFlags |= 2L;
         this.mAlignSelf = var1;
      }

      private void aspectRatio(float var1) {
         this.mPrivateFlags |= 4194304L;
         this.mAspectRatio = var1;
      }

      private void border(@Nullable Border var1) {
         if(var1 != null) {
            this.mPrivateFlags |= 137438953472L;
            this.mBorder = var1;
         }

      }

      private void duplicateParentState(boolean var1) {
         this.mPrivateFlags |= 128L;
         this.mDuplicateParentState = var1;
      }

      private void flex(float var1) {
         this.mPrivateFlags |= 4L;
         this.mFlex = var1;
      }

      private void flexBasisPercent(float var1) {
         this.mPrivateFlags |= 134217728L;
         this.mFlexBasisPercent = var1;
      }

      private void flexBasisPx(@Px int var1) {
         this.mPrivateFlags |= 32L;
         this.mFlexBasisPx = var1;
      }

      private void flexGrow(float var1) {
         this.mPrivateFlags |= 8L;
         this.mFlexGrow = var1;
      }

      private void flexShrink(float var1) {
         this.mPrivateFlags |= 16L;
         this.mFlexShrink = var1;
      }

      private void focusedHandler(@Nullable EventHandler<FocusedVisibleEvent> var1) {
         this.mPrivateFlags |= 131072L;
         this.mFocusedHandler = var1;
      }

      private void foreground(@Nullable ComparableDrawable var1) {
         this.mPrivateFlags |= 32768L;
         this.mForeground = var1;
      }

      private void fullImpressionHandler(@Nullable EventHandler<FullImpressionVisibleEvent> var1) {
         this.mPrivateFlags |= 262144L;
         this.mFullImpressionHandler = var1;
      }

      private void heightPercent(float var1) {
         this.mPrivateFlags |= 17179869184L;
         this.mHeightPercent = var1;
      }

      private void importantForAccessibility(int var1) {
         this.mPrivateFlags |= 64L;
         this.mImportantForAccessibility = var1;
      }

      private void invisibleHandler(@Nullable EventHandler<InvisibleEvent> var1) {
         this.mPrivateFlags |= 524288L;
         this.mInvisibleHandler = var1;
      }

      private void layoutDirection(YogaDirection var1) {
         this.mPrivateFlags |= 1L;
         this.mLayoutDirection = var1;
      }

      private void marginAuto(@Nullable YogaEdge var1) {
         this.mPrivateFlags |= 536870912L;
         if(this.mMarginAutos == null) {
            this.mMarginAutos = new ArrayList(2);
         }

         this.mMarginAutos.add(var1);
      }

      private void marginPercent(@Nullable YogaEdge var1, float var2) {
         this.mPrivateFlags |= 268435456L;
         if(this.mMarginPercents == null) {
            this.mMarginPercents = new CommonPropsHolder.YogaEdgesWithFloats(null);
         }

         this.mMarginPercents.add(var1, var2);
      }

      private void marginPx(@Nullable YogaEdge var1, @Px int var2) {
         this.mPrivateFlags |= 256L;
         if(this.mMargins == null) {
            this.mMargins = new CommonPropsHolder.YogaEdgesWithInts();
         }

         this.mMargins.add(var1, var2);
      }

      private void maxHeightPercent(float var1) {
         this.mPrivateFlags |= 68719476736L;
         this.mMaxHeightPercent = var1;
      }

      private void maxHeightPx(@Px int var1) {
         this.mPrivateFlags |= 16384L;
         this.mMaxHeightPx = var1;
      }

      private void maxWidthPercent(float var1) {
         this.mPrivateFlags |= 8589934592L;
         this.mMaxWidthPercent = var1;
      }

      private void maxWidthPx(@Px int var1) {
         this.mPrivateFlags |= 4096L;
         this.mMaxWidthPx = var1;
      }

      private void minHeightPercent(float var1) {
         this.mPrivateFlags |= 34359738368L;
         this.mMinHeightPercent = var1;
      }

      private void minHeightPx(@Px int var1) {
         this.mPrivateFlags |= 8192L;
         this.mMinHeightPx = var1;
      }

      private void minWidthPercent(float var1) {
         this.mPrivateFlags |= 4294967296L;
         this.mMinWidthPercent = var1;
      }

      private void minWidthPx(@Px int var1) {
         this.mPrivateFlags |= 2048L;
         this.mMinWidthPx = var1;
      }

      private void paddingPercent(@Nullable YogaEdge var1, float var2) {
         this.mPrivateFlags |= 1073741824L;
         if(this.mPaddingPercents == null) {
            this.mPaddingPercents = new CommonPropsHolder.YogaEdgesWithFloats(null);
         }

         this.mPaddingPercents.add(var1, var2);
      }

      private void paddingPx(@Nullable YogaEdge var1, @Px int var2) {
         this.mPrivateFlags |= 512L;
         if(this.mPaddings == null) {
            this.mPaddings = new CommonPropsHolder.YogaEdgesWithInts();
         }

         this.mPaddings.add(var1, var2);
      }

      private void positionPercent(YogaEdge var1, float var2) {
         this.mPrivateFlags |= 1024L;
         if(this.mPositionPercents == null) {
            this.mPositionPercents = new CommonPropsHolder.YogaEdgesWithFloats(null);
         }

         this.mPositionPercents.add(var1, var2);
      }

      private void stateListAnimator(StateListAnimator var1) {
         this.mPrivateFlags |= 274877906944L;
         this.mStateListAnimator = var1;
      }

      private void stateListAnimatorRes(@DrawableRes int var1) {
         this.mPrivateFlags |= 549755813888L;
         this.mStateListAnimatorRes = var1;
      }

      private void touchExpansionPx(YogaEdge var1, @Px int var2) {
         this.mPrivateFlags |= 2097152L;
         if(this.mTouchExpansions == null) {
            this.mTouchExpansions = new CommonPropsHolder.YogaEdgesWithInts();
         }

         this.mTouchExpansions.add(var1, var2);
      }

      private void transitionKey(String var1) {
         this.mPrivateFlags |= 8388608L;
         this.mTransitionKey = var1;
      }

      private void unfocusedHandler(@Nullable EventHandler<UnfocusedVisibleEvent> var1) {
         this.mPrivateFlags |= 1048576L;
         this.mUnfocusedHandler = var1;
      }

      private void visibilityChangedHandler(@Nullable EventHandler<VisibilityChangedEvent> var1) {
         this.mPrivateFlags |= 1099511627776L;
         this.mVisibilityChangedHandler = var1;
      }

      private void visibleHandler(@Nullable EventHandler<VisibleEvent> var1) {
         this.mPrivateFlags |= 65536L;
         this.mVisibleHandler = var1;
      }

      private void visibleHeightRatio(float var1) {
         this.mPrivateFlags |= 33554432L;
         this.mVisibleHeightRatio = var1;
      }

      private void visibleWidthRatio(float var1) {
         this.mPrivateFlags |= 67108864L;
         this.mVisibleWidthRatio = var1;
      }

      private void widthPercent(float var1) {
         this.mPrivateFlags |= 2147483648L;
         this.mWidthPercent = var1;
      }

      void copyInto(InternalNode var1) {
         if((this.mPrivateFlags & 1L) != 0L) {
            var1.layoutDirection(this.mLayoutDirection);
         }

         if((this.mPrivateFlags & 64L) != 0L) {
            var1.importantForAccessibility(this.mImportantForAccessibility);
         }

         if((this.mPrivateFlags & 128L) != 0L) {
            var1.duplicateParentState(this.mDuplicateParentState);
         }

         if((this.mPrivateFlags & 32768L) != 0L) {
            var1.foreground(this.mForeground);
         }

         if((this.mPrivateFlags & 16777216L) != 0L) {
            var1.wrapInView();
         }

         if((this.mPrivateFlags & 65536L) != 0L) {
            var1.visibleHandler(this.mVisibleHandler);
         }

         if((this.mPrivateFlags & 131072L) != 0L) {
            var1.focusedHandler(this.mFocusedHandler);
         }

         if((this.mPrivateFlags & 262144L) != 0L) {
            var1.fullImpressionHandler(this.mFullImpressionHandler);
         }

         if((this.mPrivateFlags & 524288L) != 0L) {
            var1.invisibleHandler(this.mInvisibleHandler);
         }

         if((this.mPrivateFlags & 1048576L) != 0L) {
            var1.unfocusedHandler(this.mUnfocusedHandler);
         }

         if((this.mPrivateFlags & 1099511627776L) != 0L) {
            var1.visibilityChangedHandler(this.mVisibilityChangedHandler);
         }

         if((this.mPrivateFlags & 8388608L) != 0L) {
            var1.transitionKey(this.mTransitionKey);
         }

         if((this.mPrivateFlags & 33554432L) != 0L) {
            var1.visibleHeightRatio(this.mVisibleHeightRatio);
         }

         if((this.mPrivateFlags & 67108864L) != 0L) {
            var1.visibleWidthRatio(this.mVisibleWidthRatio);
         }

         if((this.mPrivateFlags & 2L) != 0L) {
            var1.alignSelf(this.mAlignSelf);
         }

         long var4 = this.mPrivateFlags;
         byte var3 = 0;
         int var2;
         if((var4 & 1024L) != 0L) {
            for(var2 = 0; var2 < this.mPositionPercents.mNumEntries; ++var2) {
               var1.positionPercent(this.mPositionPercents.mEdges[var2], this.mPositionPercents.mValues[var2]);
            }
         }

         if((this.mPrivateFlags & 4L) != 0L) {
            var1.flex(this.mFlex);
         }

         if((this.mPrivateFlags & 8L) != 0L) {
            var1.flexGrow(this.mFlexGrow);
         }

         if((this.mPrivateFlags & 16L) != 0L) {
            var1.flexShrink(this.mFlexShrink);
         }

         if((this.mPrivateFlags & 32L) != 0L) {
            var1.flexBasisPx(this.mFlexBasisPx);
         }

         if((this.mPrivateFlags & 134217728L) != 0L) {
            var1.flexBasisPercent(this.mFlexBasisPercent);
         }

         if((this.mPrivateFlags & 2147483648L) != 0L) {
            var1.widthPercent(this.mWidthPercent);
         }

         if((this.mPrivateFlags & 2048L) != 0L) {
            var1.minWidthPx(this.mMinWidthPx);
         }

         if((this.mPrivateFlags & 4294967296L) != 0L) {
            var1.minWidthPercent(this.mMinWidthPercent);
         }

         if((this.mPrivateFlags & 4096L) != 0L) {
            var1.maxWidthPx(this.mMaxWidthPx);
         }

         if((this.mPrivateFlags & 8589934592L) != 0L) {
            var1.maxWidthPercent(this.mMaxWidthPercent);
         }

         if((this.mPrivateFlags & 17179869184L) != 0L) {
            var1.heightPercent(this.mHeightPercent);
         }

         if((this.mPrivateFlags & 8192L) != 0L) {
            var1.minHeightPx(this.mMinHeightPx);
         }

         if((this.mPrivateFlags & 34359738368L) != 0L) {
            var1.minHeightPercent(this.mMinHeightPercent);
         }

         if((this.mPrivateFlags & 16384L) != 0L) {
            var1.maxHeightPx(this.mMaxHeightPx);
         }

         if((this.mPrivateFlags & 68719476736L) != 0L) {
            var1.maxHeightPercent(this.mMaxHeightPercent);
         }

         if((this.mPrivateFlags & 4194304L) != 0L) {
            var1.aspectRatio(this.mAspectRatio);
         }

         if((this.mPrivateFlags & 256L) != 0L) {
            for(var2 = 0; var2 < this.mMargins.size(); ++var2) {
               var1.marginPx(this.mMargins.getEdge(var2), this.mMargins.getValue(var2));
            }
         }

         if((this.mPrivateFlags & 268435456L) != 0L) {
            for(var2 = 0; var2 < this.mMarginPercents.mNumEntries; ++var2) {
               var1.marginPercent(this.mMarginPercents.mEdges[var2], this.mMarginPercents.mValues[var2]);
            }
         }

         if((this.mPrivateFlags & 536870912L) != 0L) {
            Iterator var6 = this.mMarginAutos.iterator();

            while(var6.hasNext()) {
               var1.marginAuto((YogaEdge)var6.next());
            }
         }

         if((this.mPrivateFlags & 512L) != 0L) {
            for(var2 = 0; var2 < this.mPaddings.size(); ++var2) {
               var1.paddingPx(this.mPaddings.getEdge(var2), this.mPaddings.getValue(var2));
            }
         }

         if((this.mPrivateFlags & 1073741824L) != 0L) {
            for(var2 = 0; var2 < this.mPaddingPercents.mNumEntries; ++var2) {
               var1.paddingPercent(this.mPaddingPercents.mEdges[var2], this.mPaddingPercents.mValues[var2]);
            }
         }

         if((this.mPrivateFlags & 2097152L) != 0L) {
            for(var2 = var3; var2 < this.mTouchExpansions.size(); ++var2) {
               var1.touchExpansionPx(this.mTouchExpansions.getEdge(var2), this.mTouchExpansions.getValue(var2));
            }
         }

         if((this.mPrivateFlags & 137438953472L) != 0L) {
            var1.border(this.mBorder);
         }

         if((this.mPrivateFlags & 274877906944L) != 0L) {
            var1.stateListAnimator(this.mStateListAnimator);
         }

         if((this.mPrivateFlags & 549755813888L) != 0L) {
            var1.stateListAnimatorRes(this.mStateListAnimatorRes);
         }

      }
   }
}
