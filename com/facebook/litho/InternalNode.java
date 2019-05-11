package com.facebook.litho;

import android.animation.StateListAnimator;
import android.content.res.TypedArray;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ViewOutlineProvider;
import com.facebook.infer.annotation.ReturnsOwnership;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.litho.Border;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.DebugComponent;
import com.facebook.litho.DelegatingEventHandler;
import com.facebook.litho.DiffNode;
import com.facebook.litho.DispatchPopulateAccessibilityEventEvent;
import com.facebook.litho.Edges;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FastMath;
import com.facebook.litho.FocusChangedEvent;
import com.facebook.litho.FocusedVisibleEvent;
import com.facebook.litho.FullImpressionVisibleEvent;
import com.facebook.litho.InvisibleEvent;
import com.facebook.litho.Layout;
import com.facebook.litho.LongClickEvent;
import com.facebook.litho.NodeInfo;
import com.facebook.litho.OnInitializeAccessibilityEventEvent;
import com.facebook.litho.OnInitializeAccessibilityNodeInfoEvent;
import com.facebook.litho.OnPopulateAccessibilityEventEvent;
import com.facebook.litho.OnRequestSendAccessibilityEventEvent;
import com.facebook.litho.PerformAccessibilityActionEvent;
import com.facebook.litho.R;
import com.facebook.litho.SendAccessibilityEventEvent;
import com.facebook.litho.SendAccessibilityEventUncheckedEvent;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.Transition;
import com.facebook.litho.TreeProps;
import com.facebook.litho.TypedArrayUtils;
import com.facebook.litho.UnfocusedVisibleEvent;
import com.facebook.litho.VisibilityChangedEvent;
import com.facebook.litho.VisibleEvent;
import com.facebook.litho.WorkingRangeContainer;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.config.YogaDefaults;
import com.facebook.litho.drawable.ComparableColorDrawable;
import com.facebook.litho.drawable.ComparableDrawable;
import com.facebook.litho.drawable.ComparableResDrawable;
import com.facebook.litho.drawable.DefaultComparableDrawable;
import com.facebook.litho.reference.DrawableReference;
import com.facebook.litho.reference.Reference;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaWrap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

@ThreadConfined("ANY")
class InternalNode implements ComponentLayout {

   private static final long PFLAG_ALIGN_SELF_IS_SET = 2L;
   private static final long PFLAG_ASPECT_RATIO_IS_SET = 67108864L;
   private static final long PFLAG_BACKGROUND_IS_SET = 262144L;
   private static final long PFLAG_BORDER_IS_SET = 268435456L;
   private static final long PFLAG_DUPLICATE_PARENT_STATE_IS_SET = 256L;
   private static final long PFLAG_FLEX_BASIS_IS_SET = 64L;
   private static final long PFLAG_FLEX_GROW_IS_SET = 16L;
   private static final long PFLAG_FLEX_IS_SET = 8L;
   private static final long PFLAG_FLEX_SHRINK_IS_SET = 32L;
   private static final long PFLAG_FOCUSED_HANDLER_IS_SET = 2097152L;
   private static final long PFLAG_FOREGROUND_IS_SET = 524288L;
   private static final long PFLAG_FULL_IMPRESSION_HANDLER_IS_SET = 4194304L;
   private static final long PFLAG_HEIGHT_IS_SET = 32768L;
   private static final long PFLAG_IMPORTANT_FOR_ACCESSIBILITY_IS_SET = 128L;
   private static final long PFLAG_INVISIBLE_HANDLER_IS_SET = 8388608L;
   private static final long PFLAG_LAYOUT_DIRECTION_IS_SET = 1L;
   private static final long PFLAG_MARGIN_IS_SET = 512L;
   private static final long PFLAG_MAX_HEIGHT_IS_SET = 131072L;
   private static final long PFLAG_MAX_WIDTH_IS_SET = 16384L;
   private static final long PFLAG_MIN_HEIGHT_IS_SET = 65536L;
   private static final long PFLAG_MIN_WIDTH_IS_SET = 8192L;
   private static final long PFLAG_PADDING_IS_SET = 1024L;
   private static final long PFLAG_POSITION_IS_SET = 2048L;
   private static final long PFLAG_POSITION_TYPE_IS_SET = 4L;
   private static final long PFLAG_STATE_LIST_ANIMATOR_RES_SET = 1073741824L;
   private static final long PFLAG_STATE_LIST_ANIMATOR_SET = 536870912L;
   private static final long PFLAG_TOUCH_EXPANSION_IS_SET = 33554432L;
   private static final long PFLAG_TRANSITION_KEY_IS_SET = 134217728L;
   private static final long PFLAG_UNFOCUSED_HANDLER_IS_SET = 16777216L;
   private static final long PFLAG_VISIBLE_HANDLER_IS_SET = 1048576L;
   private static final long PFLAG_VISIBLE_RECT_CHANGED_HANDLER_IS_SET = 2147483648L;
   private static final long PFLAG_WIDTH_IS_SET = 4096L;
   private static final boolean SUPPORTS_RTL;
   @Nullable
   private Reference<? extends Drawable> mBackground;
   private final int[] mBorderColors = new int[4];
   @Nullable
   private PathEffect mBorderPathEffect;
   private final float[] mBorderRadius = new float[4];
   private boolean mCachedMeasuresValid;
   private ComponentContext mComponentContext;
   @ThreadConfined("ANY")
   private final List<Component> mComponents = new ArrayList(1);
   @Nullable
   private ArrayList<Component> mComponentsNeedingPreviousRenderData;
   private Set<DebugComponent> mDebugComponents = new HashSet();
   private DiffNode mDiffNode;
   private boolean mDuplicateParentState;
   @Nullable
   private EventHandler<FocusedVisibleEvent> mFocusedHandler;
   private boolean mForceViewWrapping;
   @Nullable
   private ComparableDrawable mForeground;
   @Nullable
   private EventHandler<FullImpressionVisibleEvent> mFullImpressionHandler;
   private int mImportantForAccessibility = 0;
   @Nullable
   private EventHandler<InvisibleEvent> mInvisibleHandler;
   private boolean mIsNestedTreeHolder;
   private boolean[] mIsPaddingPercent;
   private int mLastHeightSpec = -1;
   private float mLastMeasuredHeight = -1.0F;
   private float mLastMeasuredWidth = -1.0F;
   private int mLastWidthSpec = -1;
   private InternalNode mNestedTree;
   private Edges mNestedTreeBorderWidth;
   private InternalNode mNestedTreeHolder;
   private Edges mNestedTreePadding;
   private NodeInfo mNodeInfo;
   private TreeProps mPendingTreeProps;
   private long mPrivateFlags;
   private float mResolvedHeight = Float.NaN;
   private float mResolvedTouchExpansionLeft = Float.NaN;
   private float mResolvedTouchExpansionRight = Float.NaN;
   private float mResolvedWidth = Float.NaN;
   private float mResolvedX = Float.NaN;
   private float mResolvedY = Float.NaN;
   @Nullable
   private StateListAnimator mStateListAnimator;
   @DrawableRes
   private int mStateListAnimatorRes;
   private String mTestKey;
   private Edges mTouchExpansion;
   private String mTransitionKey;
   @Nullable
   private ArrayList<Transition> mTransitions;
   @Nullable
   private EventHandler<UnfocusedVisibleEvent> mUnfocusedHandler;
   @Nullable
   private EventHandler<VisibilityChangedEvent> mVisibilityChangedHandler;
   @Nullable
   private EventHandler<VisibleEvent> mVisibleHandler;
   private float mVisibleHeightRatio;
   private float mVisibleWidthRatio;
   @Nullable
   private ArrayList<WorkingRangeContainer.Registration> mWorkingRangeRegistrations;
   YogaNode mYogaNode;


   static {
      boolean var0;
      if(VERSION.SDK_INT >= 17) {
         var0 = true;
      } else {
         var0 = false;
      }

      SUPPORTS_RTL = var0;
   }

   private static <A extends Object> List<A> addOrCreateList(@Nullable List<A> var0, A var1) {
      Object var2 = var0;
      if(var0 == null) {
         var2 = new LinkedList();
      }

      ((List)var2).add(var1);
      return (List)var2;
   }

   @Nullable
   private static <T extends Object> EventHandler<T> addVisibilityHandler(@Nullable EventHandler<T> var0, @Nullable EventHandler<T> var1) {
      return (EventHandler)(var0 == null?var1:(var1 == null?var0:new DelegatingEventHandler(var0, var1)));
   }

   private void applyOverridesRecursive(InternalNode var1) {
      if(ComponentsConfiguration.isDebugModeEnabled) {
         DebugComponent.applyOverrides(this.mComponentContext, var1);
         int var2 = 0;

         for(int var3 = var1.getChildCount(); var2 < var3; ++var2) {
            this.applyOverridesRecursive(var1.getChildAt(var2));
         }

         if(var1.hasNestedTree()) {
            this.applyOverridesRecursive(var1.getNestedTree());
         }
      }

   }

   static void assertContextSpecificStyleNotSet(InternalNode var0) {
      long var1 = var0.mPrivateFlags;
      List var4 = null;
      if((var1 & 2L) != 0L) {
         var4 = addOrCreateList((List)null, "alignSelf");
      }

      List var3 = var4;
      if((var0.mPrivateFlags & 4L) != 0L) {
         var3 = addOrCreateList(var4, "positionType");
      }

      var4 = var3;
      if((var0.mPrivateFlags & 8L) != 0L) {
         var4 = addOrCreateList(var3, "flex");
      }

      var3 = var4;
      if((var0.mPrivateFlags & 16L) != 0L) {
         var3 = addOrCreateList(var4, "flexGrow");
      }

      var4 = var3;
      if((var0.mPrivateFlags & 512L) != 0L) {
         var4 = addOrCreateList(var3, "margin");
      }

      if(var4 != null) {
         String var5 = TextUtils.join(", ", var4);
         StringBuilder var6 = new StringBuilder();
         var6.append("You should not set ");
         var6.append(var5);
         var6.append(" to a root layout in ");
         var6.append(var0.getRootComponent());
         throw new IllegalStateException(var6.toString());
      }
   }

   private static boolean getDrawablePadding(Drawable var0, Rect var1) {
      var0.getPadding(var1);
      return var1.bottom != 0 || var1.top != 0 || var1.left != 0 || var1.right != 0;
   }

   @ReturnsOwnership
   private Edges getNestedTreePadding() {
      if(this.mNestedTreePadding == null) {
         this.mNestedTreePadding = ComponentsPools.acquireEdges();
      }

      return this.mNestedTreePadding;
   }

   private NodeInfo getOrCreateNodeInfo() {
      if(this.mNodeInfo == null) {
         this.mNodeInfo = NodeInfo.acquire();
      }

      return this.mNodeInfo;
   }

   static boolean hasValidLayoutDirectionInNestedTree(InternalNode var0, InternalNode var1) {
      long var4 = var1.mPrivateFlags;
      boolean var6 = false;
      boolean var2;
      if((var4 & 1L) != 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      boolean var3;
      if(var1.getResolvedLayoutDirection() == var0.getResolvedLayoutDirection()) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(var2 || var3) {
         var6 = true;
      }

      return var6;
   }

   private boolean isPaddingPercent(YogaEdge var1) {
      return this.mIsPaddingPercent != null && this.mIsPaddingPercent[var1.intValue()];
   }

   private boolean maybeSkipLayoutProp(long var1) {
      return ComponentsConfiguration.enableSkipYogaPropExperiment && (this.mPrivateFlags & var1) != 0L;
   }

   private float resolveHorizontalEdges(Edges var1, YogaEdge var2) {
      boolean var5;
      if(this.mYogaNode.getLayoutDirection() == YogaDirection.RTL) {
         var5 = true;
      } else {
         var5 = false;
      }

      YogaEdge var6;
      switch(null.$SwitchMap$com$facebook$yoga$YogaEdge[var2.ordinal()]) {
      case 1:
         if(var5) {
            var6 = YogaEdge.END;
         } else {
            var6 = YogaEdge.START;
         }
         break;
      case 2:
         if(var5) {
            var6 = YogaEdge.START;
         } else {
            var6 = YogaEdge.END;
         }
         break;
      default:
         StringBuilder var7 = new StringBuilder();
         var7.append("Not an horizontal padding edge: ");
         var7.append(var2);
         throw new IllegalArgumentException(var7.toString());
      }

      float var4 = var1.getRaw(var6);
      float var3 = var4;
      if(YogaConstants.isUndefined(var4)) {
         var3 = var1.get(var2);
      }

      return var3;
   }

   private void setIsPaddingPercent(YogaEdge var1, boolean var2) {
      if(this.mIsPaddingPercent == null && var2) {
         this.mIsPaddingPercent = new boolean[YogaEdge.ALL.intValue() + 1];
      }

      if(this.mIsPaddingPercent != null) {
         this.mIsPaddingPercent[var1.intValue()] = var2;
      }

   }

   private <T extends Drawable> void setPaddingFromDrawableReference(@Nullable Reference<T> var1) {
      if(var1 != null) {
         Drawable var2 = (Drawable)Reference.acquire(this.mComponentContext.getAndroidContext(), var1);
         if(var2 != null) {
            Rect var3 = ComponentsPools.acquireRect();
            if(getDrawablePadding(var2, var3)) {
               this.paddingPx(YogaEdge.LEFT, var3.left);
               this.paddingPx(YogaEdge.TOP, var3.top);
               this.paddingPx(YogaEdge.RIGHT, var3.right);
               this.paddingPx(YogaEdge.BOTTOM, var3.bottom);
            }

            Reference.release(this.mComponentContext.getAndroidContext(), var2, var1);
            ComponentsPools.release(var3);
         }

      }
   }

   private boolean shouldApplyTouchExpansion() {
      return this.mTouchExpansion != null && this.mNodeInfo != null && this.mNodeInfo.hasTouchEventHandlers();
   }

   InternalNode accessibilityRole(String var1) {
      this.getOrCreateNodeInfo().setAccessibilityRole(var1);
      return this;
   }

   void addChildAt(InternalNode var1, int var2) {
      this.mYogaNode.addChildAt(var1.mYogaNode, var2);
   }

   void addComponentNeedingPreviousRenderData(Component var1) {
      if(this.mComponentsNeedingPreviousRenderData == null) {
         this.mComponentsNeedingPreviousRenderData = new ArrayList(1);
      }

      this.mComponentsNeedingPreviousRenderData.add(var1);
   }

   void addTransition(Transition var1) {
      if(this.mTransitions == null) {
         this.mTransitions = new ArrayList(1);
      }

      this.mTransitions.add(var1);
   }

   void addWorkingRanges(List<WorkingRangeContainer.Registration> var1) {
      if(this.mWorkingRangeRegistrations == null) {
         this.mWorkingRangeRegistrations = new ArrayList(var1.size());
      }

      this.mWorkingRangeRegistrations.addAll(var1);
   }

   InternalNode alignContent(YogaAlign var1) {
      if(ComponentsConfiguration.enableSkipYogaPropExperiment && var1 == YogaDefaults.ALIGN_CONTENT) {
         return this;
      } else {
         this.mYogaNode.setAlignContent(var1);
         return this;
      }
   }

   InternalNode alignItems(YogaAlign var1) {
      if(ComponentsConfiguration.enableSkipYogaPropExperiment && var1 == YogaDefaults.ALIGN_ITEM) {
         return this;
      } else {
         this.mYogaNode.setAlignItems(var1);
         return this;
      }
   }

   InternalNode alignSelf(YogaAlign var1) {
      if(this.maybeSkipLayoutProp(2L) && var1 == YogaDefaults.ALIGN_SELF) {
         return this;
      } else {
         this.mPrivateFlags |= 2L;
         this.mYogaNode.setAlignSelf(var1);
         return this;
      }
   }

   InternalNode alpha(float var1) {
      this.wrapInView();
      this.getOrCreateNodeInfo().setAlpha(var1);
      return this;
   }

   void appendComponent(Component var1) {
      this.mComponents.add(var1);
   }

   void applyAttributes(TypedArray var1) {
      int var4 = var1.getIndexCount();

      for(int var3 = 0; var3 < var4; ++var3) {
         int var5 = var1.getIndex(var3);
         if(var5 == R.styleable.ComponentLayout_android_layout_width) {
            var5 = var1.getLayoutDimension(var5, -1);
            if(var5 >= 0) {
               this.widthPx(var5);
            }
         } else if(var5 == R.styleable.ComponentLayout_android_layout_height) {
            var5 = var1.getLayoutDimension(var5, -1);
            if(var5 >= 0) {
               this.heightPx(var5);
            }
         } else if(var5 == R.styleable.ComponentLayout_android_minHeight) {
            this.minHeightPx(var1.getDimensionPixelSize(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_minWidth) {
            this.minWidthPx(var1.getDimensionPixelSize(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_paddingLeft) {
            this.paddingPx(YogaEdge.LEFT, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_paddingTop) {
            this.paddingPx(YogaEdge.TOP, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_paddingRight) {
            this.paddingPx(YogaEdge.RIGHT, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_paddingBottom) {
            this.paddingPx(YogaEdge.BOTTOM, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_paddingStart && SUPPORTS_RTL) {
            this.paddingPx(YogaEdge.START, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_paddingEnd && SUPPORTS_RTL) {
            this.paddingPx(YogaEdge.END, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_padding) {
            this.paddingPx(YogaEdge.ALL, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_layout_marginLeft) {
            this.marginPx(YogaEdge.LEFT, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_layout_marginTop) {
            this.marginPx(YogaEdge.TOP, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_layout_marginRight) {
            this.marginPx(YogaEdge.RIGHT, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_layout_marginBottom) {
            this.marginPx(YogaEdge.BOTTOM, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_layout_marginStart && SUPPORTS_RTL) {
            this.marginPx(YogaEdge.START, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_layout_marginEnd && SUPPORTS_RTL) {
            this.marginPx(YogaEdge.END, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_layout_margin) {
            this.marginPx(YogaEdge.ALL, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_importantForAccessibility && VERSION.SDK_INT >= 16) {
            this.importantForAccessibility(var1.getInt(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_android_duplicateParentState) {
            this.duplicateParentState(var1.getBoolean(var5, false));
         } else if(var5 == R.styleable.ComponentLayout_android_background) {
            if(TypedArrayUtils.isColorAttribute(var1, R.styleable.ComponentLayout_android_background)) {
               this.backgroundColor(var1.getColor(var5, 0));
            } else {
               this.backgroundRes(var1.getResourceId(var5, -1));
            }
         } else if(var5 == R.styleable.ComponentLayout_android_foreground) {
            if(TypedArrayUtils.isColorAttribute(var1, R.styleable.ComponentLayout_android_foreground)) {
               this.foregroundColor(var1.getColor(var5, 0));
            } else {
               this.foregroundRes(var1.getResourceId(var5, -1));
            }
         } else if(var5 == R.styleable.ComponentLayout_android_contentDescription) {
            this.contentDescription(var1.getString(var5));
         } else if(var5 == R.styleable.ComponentLayout_flex_direction) {
            this.flexDirection(YogaFlexDirection.fromInt(var1.getInteger(var5, 0)));
         } else if(var5 == R.styleable.ComponentLayout_flex_wrap) {
            this.wrap(YogaWrap.fromInt(var1.getInteger(var5, 0)));
         } else if(var5 == R.styleable.ComponentLayout_flex_justifyContent) {
            this.justifyContent(YogaJustify.fromInt(var1.getInteger(var5, 0)));
         } else if(var5 == R.styleable.ComponentLayout_flex_alignItems) {
            this.alignItems(YogaAlign.fromInt(var1.getInteger(var5, 0)));
         } else if(var5 == R.styleable.ComponentLayout_flex_alignSelf) {
            this.alignSelf(YogaAlign.fromInt(var1.getInteger(var5, 0)));
         } else if(var5 == R.styleable.ComponentLayout_flex_positionType) {
            this.positionType(YogaPositionType.fromInt(var1.getInteger(var5, 0)));
         } else if(var5 == R.styleable.ComponentLayout_flex) {
            float var2 = var1.getFloat(var5, -1.0F);
            if(var2 >= 0.0F) {
               this.flex(var2);
            }
         } else if(var5 == R.styleable.ComponentLayout_flex_left) {
            this.positionPx(YogaEdge.LEFT, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_flex_top) {
            this.positionPx(YogaEdge.TOP, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_flex_right) {
            this.positionPx(YogaEdge.RIGHT, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_flex_bottom) {
            this.positionPx(YogaEdge.BOTTOM, var1.getDimensionPixelOffset(var5, 0));
         } else if(var5 == R.styleable.ComponentLayout_flex_layoutDirection) {
            this.layoutDirection(YogaDirection.fromInt(var1.getInteger(var5, -1)));
         }
      }

   }

   boolean areCachedMeasuresValid() {
      return this.mCachedMeasuresValid;
   }

   InternalNode aspectRatio(float var1) {
      if(this.maybeSkipLayoutProp(67108864L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 67108864L;
         this.mYogaNode.setAspectRatio(var1);
         return this;
      }
   }

   @Deprecated
   InternalNode background(@Nullable Drawable var1) {
      if(var1 instanceof ComparableDrawable) {
         return this.background((ComparableDrawable)var1);
      } else {
         DefaultComparableDrawable var2;
         if(var1 != null) {
            var2 = DefaultComparableDrawable.create(var1);
         } else {
            var2 = null;
         }

         return this.background((ComparableDrawable)var2);
      }
   }

   InternalNode background(@Nullable ComparableDrawable var1) {
      Reference var2;
      if(var1 != null) {
         var2 = DrawableReference.create(var1);
      } else {
         var2 = null;
      }

      return this.background(var2);
   }

   @Deprecated
   InternalNode background(@Nullable Reference<? extends Drawable> var1) {
      this.mPrivateFlags |= 262144L;
      this.mBackground = var1;
      this.setPaddingFromDrawableReference(var1);
      return this;
   }

   InternalNode backgroundColor(@ColorInt int var1) {
      return ComponentsConfiguration.enableComparableDrawable?this.background((ComparableDrawable)ComparableColorDrawable.create(var1)):this.background((Drawable)(new ColorDrawable(var1)));
   }

   InternalNode backgroundRes(@DrawableRes int var1) {
      return var1 == 0?this.background((ComparableDrawable)null):(ComponentsConfiguration.enableComparableDrawable?this.background((ComparableDrawable)ComparableResDrawable.create(this.mComponentContext.getAndroidContext(), var1)):this.background(ContextCompat.getDrawable(this.mComponentContext.getAndroidContext(), var1)));
   }

   InternalNode border(Border var1) {
      this.mPrivateFlags |= 268435456L;
      int var3 = var1.mEdgeWidths.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         this.setBorderWidth(Border.edgeFromIndex(var2), var1.mEdgeWidths[var2]);
      }

      System.arraycopy(var1.mEdgeColors, 0, this.mBorderColors, 0, this.mBorderColors.length);
      System.arraycopy(var1.mRadius, 0, this.mBorderRadius, 0, this.mBorderRadius.length);
      this.mBorderPathEffect = var1.mPathEffect;
      return this;
   }

   void calculateLayout() {
      this.calculateLayout(Float.NaN, Float.NaN);
   }

   void calculateLayout(float var1, float var2) {
      this.applyOverridesRecursive(this);
      this.mYogaNode.calculateLayout(var1, var2);
   }

   InternalNode child(Component.Builder<?> var1) {
      if(var1 != null) {
         this.child(var1.build());
      }

      return this;
   }

   InternalNode child(Component var1) {
      return var1 != null?this.child(Layout.create(this.mComponentContext, var1)):this;
   }

   InternalNode child(InternalNode var1) {
      if(var1 != null && var1 != ComponentContext.NULL_LAYOUT) {
         this.addChildAt(var1, this.mYogaNode.getChildCount());
      }

      return this;
   }

   InternalNode clickHandler(EventHandler<ClickEvent> var1) {
      this.getOrCreateNodeInfo().setClickHandler(var1);
      return this;
   }

   InternalNode clipChildren(boolean var1) {
      this.getOrCreateNodeInfo().setClipChildren(var1);
      return this;
   }

   InternalNode clipToOutline(boolean var1) {
      this.getOrCreateNodeInfo().setClipToOutline(var1);
      return this;
   }

   InternalNode contentDescription(CharSequence var1) {
      this.getOrCreateNodeInfo().setContentDescription(var1);
      return this;
   }

   void copyInto(InternalNode var1) {
      if(this.mNodeInfo != null) {
         if(var1.mNodeInfo == null) {
            var1.mNodeInfo = this.mNodeInfo.acquireRef();
         } else {
            var1.mNodeInfo.updateWith(this.mNodeInfo);
         }
      }

      if((var1.mPrivateFlags & 1L) == 0L || var1.getResolvedLayoutDirection() == YogaDirection.INHERIT) {
         var1.layoutDirection(this.getResolvedLayoutDirection());
      }

      if((var1.mPrivateFlags & 128L) == 0L || var1.mImportantForAccessibility == 0) {
         var1.mImportantForAccessibility = this.mImportantForAccessibility;
      }

      if((this.mPrivateFlags & 256L) != 0L) {
         var1.mDuplicateParentState = this.mDuplicateParentState;
      }

      if((this.mPrivateFlags & 262144L) != 0L) {
         var1.mBackground = this.mBackground;
      }

      if((this.mPrivateFlags & 524288L) != 0L) {
         var1.mForeground = this.mForeground;
      }

      if(this.mForceViewWrapping) {
         var1.mForceViewWrapping = true;
      }

      if((this.mPrivateFlags & 1048576L) != 0L) {
         var1.mVisibleHandler = this.mVisibleHandler;
      }

      if((this.mPrivateFlags & 2097152L) != 0L) {
         var1.mFocusedHandler = this.mFocusedHandler;
      }

      if((this.mPrivateFlags & 4194304L) != 0L) {
         var1.mFullImpressionHandler = this.mFullImpressionHandler;
      }

      if((this.mPrivateFlags & 8388608L) != 0L) {
         var1.mInvisibleHandler = this.mInvisibleHandler;
      }

      if((this.mPrivateFlags & 16777216L) != 0L) {
         var1.mUnfocusedHandler = this.mUnfocusedHandler;
      }

      if((this.mPrivateFlags & 2147483648L) != 0L) {
         var1.mVisibilityChangedHandler = this.mVisibilityChangedHandler;
      }

      if(this.mTestKey != null) {
         var1.mTestKey = this.mTestKey;
      }

      YogaNode var2;
      if((this.mPrivateFlags & 1024L) != 0L) {
         if(this.mNestedTreePadding == null) {
            throw new IllegalStateException("copyInto() must be used when resolving a nestedTree. If padding was set on the holder node, we must have a mNestedTreePadding instance");
         }

         var2 = var1.mYogaNode;
         var1.mPrivateFlags |= 1024L;
         if(this.isPaddingPercent(YogaEdge.LEFT)) {
            var2.setPaddingPercent(YogaEdge.LEFT, this.mNestedTreePadding.getRaw(YogaEdge.LEFT));
         } else {
            var2.setPadding(YogaEdge.LEFT, this.mNestedTreePadding.getRaw(YogaEdge.LEFT));
         }

         if(this.isPaddingPercent(YogaEdge.TOP)) {
            var2.setPaddingPercent(YogaEdge.TOP, this.mNestedTreePadding.getRaw(YogaEdge.TOP));
         } else {
            var2.setPadding(YogaEdge.TOP, this.mNestedTreePadding.getRaw(YogaEdge.TOP));
         }

         if(this.isPaddingPercent(YogaEdge.RIGHT)) {
            var2.setPaddingPercent(YogaEdge.RIGHT, this.mNestedTreePadding.getRaw(YogaEdge.RIGHT));
         } else {
            var2.setPadding(YogaEdge.RIGHT, this.mNestedTreePadding.getRaw(YogaEdge.RIGHT));
         }

         if(this.isPaddingPercent(YogaEdge.BOTTOM)) {
            var2.setPaddingPercent(YogaEdge.BOTTOM, this.mNestedTreePadding.getRaw(YogaEdge.BOTTOM));
         } else {
            var2.setPadding(YogaEdge.BOTTOM, this.mNestedTreePadding.getRaw(YogaEdge.BOTTOM));
         }

         if(this.isPaddingPercent(YogaEdge.VERTICAL)) {
            var2.setPaddingPercent(YogaEdge.VERTICAL, this.mNestedTreePadding.getRaw(YogaEdge.VERTICAL));
         } else {
            var2.setPadding(YogaEdge.VERTICAL, this.mNestedTreePadding.getRaw(YogaEdge.VERTICAL));
         }

         if(this.isPaddingPercent(YogaEdge.HORIZONTAL)) {
            var2.setPaddingPercent(YogaEdge.HORIZONTAL, this.mNestedTreePadding.getRaw(YogaEdge.HORIZONTAL));
         } else {
            var2.setPadding(YogaEdge.HORIZONTAL, this.mNestedTreePadding.getRaw(YogaEdge.HORIZONTAL));
         }

         if(this.isPaddingPercent(YogaEdge.START)) {
            var2.setPaddingPercent(YogaEdge.START, this.mNestedTreePadding.getRaw(YogaEdge.START));
         } else {
            var2.setPadding(YogaEdge.START, this.mNestedTreePadding.getRaw(YogaEdge.START));
         }

         if(this.isPaddingPercent(YogaEdge.END)) {
            var2.setPaddingPercent(YogaEdge.END, this.mNestedTreePadding.getRaw(YogaEdge.END));
         } else {
            var2.setPadding(YogaEdge.END, this.mNestedTreePadding.getRaw(YogaEdge.END));
         }

         if(this.isPaddingPercent(YogaEdge.ALL)) {
            var2.setPaddingPercent(YogaEdge.ALL, this.mNestedTreePadding.getRaw(YogaEdge.ALL));
         } else {
            var2.setPadding(YogaEdge.ALL, this.mNestedTreePadding.getRaw(YogaEdge.ALL));
         }
      }

      if((this.mPrivateFlags & 268435456L) != 0L) {
         if(this.mNestedTreeBorderWidth == null) {
            throw new IllegalStateException("copyInto() must be used when resolving a nestedTree. If border width was set on the holder node, we must have a mNestedTreeBorderWidth instance");
         }

         var2 = var1.mYogaNode;
         var1.mPrivateFlags |= 268435456L;
         var2.setBorder(YogaEdge.LEFT, this.mNestedTreeBorderWidth.getRaw(YogaEdge.LEFT));
         var2.setBorder(YogaEdge.TOP, this.mNestedTreeBorderWidth.getRaw(YogaEdge.TOP));
         var2.setBorder(YogaEdge.RIGHT, this.mNestedTreeBorderWidth.getRaw(YogaEdge.RIGHT));
         var2.setBorder(YogaEdge.BOTTOM, this.mNestedTreeBorderWidth.getRaw(YogaEdge.BOTTOM));
         var2.setBorder(YogaEdge.VERTICAL, this.mNestedTreeBorderWidth.getRaw(YogaEdge.VERTICAL));
         var2.setBorder(YogaEdge.HORIZONTAL, this.mNestedTreeBorderWidth.getRaw(YogaEdge.HORIZONTAL));
         var2.setBorder(YogaEdge.START, this.mNestedTreeBorderWidth.getRaw(YogaEdge.START));
         var2.setBorder(YogaEdge.END, this.mNestedTreeBorderWidth.getRaw(YogaEdge.END));
         var2.setBorder(YogaEdge.ALL, this.mNestedTreeBorderWidth.getRaw(YogaEdge.ALL));
         System.arraycopy(this.mBorderColors, 0, var1.mBorderColors, 0, this.mBorderColors.length);
         System.arraycopy(this.mBorderRadius, 0, var1.mBorderRadius, 0, this.mBorderRadius.length);
      }

      if((this.mPrivateFlags & 134217728L) != 0L) {
         var1.mTransitionKey = this.mTransitionKey;
      }

      if(this.mVisibleHeightRatio != 0.0F) {
         var1.mVisibleHeightRatio = this.mVisibleHeightRatio;
      }

      if(this.mVisibleWidthRatio != 0.0F) {
         var1.mVisibleWidthRatio = this.mVisibleWidthRatio;
      }

      if((this.mPrivateFlags & 536870912L) != 0L) {
         var1.mStateListAnimator = this.mStateListAnimator;
      }

      if((this.mPrivateFlags & 1073741824L) != 0L) {
         var1.mStateListAnimatorRes = this.mStateListAnimatorRes;
      }

   }

   InternalNode dispatchPopulateAccessibilityEventHandler(EventHandler<DispatchPopulateAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setDispatchPopulateAccessibilityEventHandler(var1);
      return this;
   }

   InternalNode duplicateParentState(boolean var1) {
      this.mPrivateFlags |= 256L;
      this.mDuplicateParentState = var1;
      return this;
   }

   InternalNode enabled(boolean var1) {
      this.getOrCreateNodeInfo().setEnabled(var1);
      return this;
   }

   InternalNode flex(float var1) {
      if(this.maybeSkipLayoutProp(8L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 8L;
         this.mYogaNode.setFlex(var1);
         return this;
      }
   }

   InternalNode flexBasisAuto() {
      this.mYogaNode.setFlexBasisAuto();
      return this;
   }

   InternalNode flexBasisPercent(float var1) {
      if(this.maybeSkipLayoutProp(64L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 64L;
         this.mYogaNode.setFlexBasisPercent(var1);
         return this;
      }
   }

   InternalNode flexBasisPx(@Px int var1) {
      if(this.maybeSkipLayoutProp(64L) && var1 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 64L;
         this.mYogaNode.setFlexBasis((float)var1);
         return this;
      }
   }

   InternalNode flexDirection(YogaFlexDirection var1) {
      if(ComponentsConfiguration.enableSkipYogaPropExperiment && var1 == YogaDefaults.FLEX_DIRECTION) {
         return this;
      } else {
         this.mYogaNode.setFlexDirection(var1);
         return this;
      }
   }

   InternalNode flexGrow(float var1) {
      if(this.maybeSkipLayoutProp(16L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 16L;
         this.mYogaNode.setFlexGrow(var1);
         return this;
      }
   }

   InternalNode flexShrink(float var1) {
      if(this.maybeSkipLayoutProp(32L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 32L;
         this.mYogaNode.setFlexShrink(var1);
         return this;
      }
   }

   InternalNode focusChangeHandler(EventHandler<FocusChangedEvent> var1) {
      this.getOrCreateNodeInfo().setFocusChangeHandler(var1);
      return this;
   }

   InternalNode focusable(boolean var1) {
      this.getOrCreateNodeInfo().setFocusable(var1);
      return this;
   }

   InternalNode focusedHandler(@Nullable EventHandler<FocusedVisibleEvent> var1) {
      this.mPrivateFlags |= 2097152L;
      this.mFocusedHandler = addVisibilityHandler(this.mFocusedHandler, var1);
      return this;
   }

   @Deprecated
   InternalNode foreground(@Nullable Drawable var1) {
      DefaultComparableDrawable var2;
      if(var1 != null) {
         var2 = DefaultComparableDrawable.create(var1);
      } else {
         var2 = null;
      }

      return this.foreground((ComparableDrawable)var2);
   }

   InternalNode foreground(@Nullable ComparableDrawable var1) {
      this.mPrivateFlags |= 524288L;
      this.mForeground = var1;
      return this;
   }

   InternalNode foregroundColor(@ColorInt int var1) {
      return ComponentsConfiguration.enableComparableDrawable?this.foreground((ComparableDrawable)ComparableColorDrawable.create(var1)):this.foreground((Drawable)(new ColorDrawable(var1)));
   }

   InternalNode foregroundRes(@DrawableRes int var1) {
      return var1 == 0?this.foreground((ComparableDrawable)null):(ComponentsConfiguration.enableComparableDrawable?this.foreground((ComparableDrawable)ComparableResDrawable.create(this.mComponentContext.getAndroidContext(), var1)):this.foreground(ContextCompat.getDrawable(this.mComponentContext.getAndroidContext(), var1)));
   }

   InternalNode fullImpressionHandler(@Nullable EventHandler<FullImpressionVisibleEvent> var1) {
      this.mPrivateFlags |= 4194304L;
      this.mFullImpressionHandler = addVisibilityHandler(this.mFullImpressionHandler, var1);
      return this;
   }

   @Nullable
   public Reference<? extends Drawable> getBackground() {
      return this.mBackground;
   }

   int[] getBorderColors() {
      return this.mBorderColors;
   }

   @Nullable
   PathEffect getBorderPathEffect() {
      return this.mBorderPathEffect;
   }

   float[] getBorderRadius() {
      return this.mBorderRadius;
   }

   @Nullable
   InternalNode getChildAt(int var1) {
      return this.mYogaNode.getChildAt(var1) == null?null:(InternalNode)this.mYogaNode.getChildAt(var1).getData();
   }

   int getChildCount() {
      return this.mYogaNode.getChildCount();
   }

   int getChildIndex(InternalNode var1) {
      int var3 = this.mYogaNode.getChildCount();

      for(int var2 = 0; var2 < var3; ++var2) {
         if(this.mYogaNode.getChildAt(var2) == var1.mYogaNode) {
            return var2;
         }
      }

      return -1;
   }

   EventHandler<ClickEvent> getClickHandler() {
      return this.getOrCreateNodeInfo().getClickHandler();
   }

   List<Component> getComponents() {
      return this.mComponents;
   }

   @Nullable
   ArrayList<Component> getComponentsNeedingPreviousRenderData() {
      return this.mComponentsNeedingPreviousRenderData;
   }

   ComponentContext getContext() {
      return this.mComponentContext;
   }

   DiffNode getDiffNode() {
      return this.mDiffNode;
   }

   @Nullable
   EventHandler<FocusedVisibleEvent> getFocusedHandler() {
      return this.mFocusedHandler;
   }

   @Nullable
   public ComparableDrawable getForeground() {
      return this.mForeground;
   }

   @Nullable
   EventHandler<FullImpressionVisibleEvent> getFullImpressionHandler() {
      return this.mFullImpressionHandler;
   }

   @Px
   public int getHeight() {
      if(YogaConstants.isUndefined(this.mResolvedHeight)) {
         this.mResolvedHeight = this.mYogaNode.getLayoutHeight();
      }

      return (int)this.mResolvedHeight;
   }

   int getImportantForAccessibility() {
      return this.mImportantForAccessibility;
   }

   @Nullable
   EventHandler<InvisibleEvent> getInvisibleHandler() {
      return this.mInvisibleHandler;
   }

   public int getLastHeightSpec() {
      return this.mLastHeightSpec;
   }

   float getLastMeasuredHeight() {
      return this.mLastMeasuredHeight;
   }

   float getLastMeasuredWidth() {
      return this.mLastMeasuredWidth;
   }

   public int getLastWidthSpec() {
      return this.mLastWidthSpec;
   }

   int getLayoutBorder(YogaEdge var1) {
      return FastMath.round(this.mYogaNode.getLayoutBorder(var1));
   }

   float getMaxHeight() {
      return this.mYogaNode.getMaxHeight().value;
   }

   float getMaxWidth() {
      return this.mYogaNode.getMaxWidth().value;
   }

   float getMinHeight() {
      return this.mYogaNode.getMinHeight().value;
   }

   float getMinWidth() {
      return this.mYogaNode.getMinWidth().value;
   }

   @Nullable
   InternalNode getNestedTree() {
      return this.mNestedTree;
   }

   InternalNode getNestedTreeHolder() {
      return this.mNestedTreeHolder;
   }

   NodeInfo getNodeInfo() {
      return this.mNodeInfo;
   }

   @Px
   public int getPaddingBottom() {
      return FastMath.round(this.mYogaNode.getLayoutPadding(YogaEdge.BOTTOM));
   }

   @Px
   public int getPaddingLeft() {
      return FastMath.round(this.mYogaNode.getLayoutPadding(YogaEdge.LEFT));
   }

   @Px
   public int getPaddingRight() {
      return FastMath.round(this.mYogaNode.getLayoutPadding(YogaEdge.RIGHT));
   }

   @Px
   public int getPaddingTop() {
      return FastMath.round(this.mYogaNode.getLayoutPadding(YogaEdge.TOP));
   }

   @Nullable
   InternalNode getParent() {
      return this.mYogaNode != null && this.mYogaNode.getParent() != null?(InternalNode)this.mYogaNode.getParent().getData():null;
   }

   public TreeProps getPendingTreeProps() {
      return this.mPendingTreeProps;
   }

   public YogaDirection getResolvedLayoutDirection() {
      return this.mYogaNode.getLayoutDirection();
   }

   @Nullable
   Component getRootComponent() {
      return this.mComponents.size() == 0?null:(Component)this.mComponents.get(0);
   }

   @Nullable
   StateListAnimator getStateListAnimator() {
      return this.mStateListAnimator;
   }

   @DrawableRes
   int getStateListAnimatorRes() {
      return this.mStateListAnimatorRes;
   }

   YogaDirection getStyleDirection() {
      return this.mYogaNode.getStyleDirection();
   }

   float getStyleHeight() {
      return this.mYogaNode.getHeight().value;
   }

   float getStyleWidth() {
      return this.mYogaNode.getWidth().value;
   }

   String getTestKey() {
      return this.mTestKey;
   }

   Edges getTouchExpansion() {
      return this.mTouchExpansion;
   }

   int getTouchExpansionBottom() {
      return !this.shouldApplyTouchExpansion()?0:FastMath.round(this.mTouchExpansion.get(YogaEdge.BOTTOM));
   }

   int getTouchExpansionLeft() {
      if(!this.shouldApplyTouchExpansion()) {
         return 0;
      } else {
         if(YogaConstants.isUndefined(this.mResolvedTouchExpansionLeft)) {
            this.mResolvedTouchExpansionLeft = this.resolveHorizontalEdges(this.mTouchExpansion, YogaEdge.LEFT);
         }

         return FastMath.round(this.mResolvedTouchExpansionLeft);
      }
   }

   int getTouchExpansionRight() {
      if(!this.shouldApplyTouchExpansion()) {
         return 0;
      } else {
         if(YogaConstants.isUndefined(this.mResolvedTouchExpansionRight)) {
            this.mResolvedTouchExpansionRight = this.resolveHorizontalEdges(this.mTouchExpansion, YogaEdge.RIGHT);
         }

         return FastMath.round(this.mResolvedTouchExpansionRight);
      }
   }

   int getTouchExpansionTop() {
      return !this.shouldApplyTouchExpansion()?0:FastMath.round(this.mTouchExpansion.get(YogaEdge.TOP));
   }

   String getTransitionKey() {
      return this.mTransitionKey;
   }

   @Nullable
   ArrayList<Transition> getTransitions() {
      return this.mTransitions;
   }

   @Nullable
   EventHandler<UnfocusedVisibleEvent> getUnfocusedHandler() {
      return this.mUnfocusedHandler;
   }

   @Nullable
   EventHandler<VisibilityChangedEvent> getVisibilityChangedHandler() {
      return this.mVisibilityChangedHandler;
   }

   @Nullable
   EventHandler<VisibleEvent> getVisibleHandler() {
      return this.mVisibleHandler;
   }

   float getVisibleHeightRatio() {
      return this.mVisibleHeightRatio;
   }

   float getVisibleWidthRatio() {
      return this.mVisibleWidthRatio;
   }

   @Px
   public int getWidth() {
      if(YogaConstants.isUndefined(this.mResolvedWidth)) {
         this.mResolvedWidth = this.mYogaNode.getLayoutWidth();
      }

      return (int)this.mResolvedWidth;
   }

   @Nullable
   ArrayList<WorkingRangeContainer.Registration> getWorkingRangeRegistrations() {
      return this.mWorkingRangeRegistrations;
   }

   @Px
   public int getX() {
      if(YogaConstants.isUndefined(this.mResolvedX)) {
         this.mResolvedX = this.mYogaNode.getLayoutX();
      }

      return (int)this.mResolvedX;
   }

   @Px
   public int getY() {
      if(YogaConstants.isUndefined(this.mResolvedY)) {
         this.mResolvedY = this.mYogaNode.getLayoutY();
      }

      return (int)this.mResolvedY;
   }

   protected boolean hasBorderColor() {
      int[] var3 = this.mBorderColors;
      int var2 = var3.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         if(var3[var1] != 0) {
            return true;
         }
      }

      return false;
   }

   boolean hasNestedTree() {
      return this.mNestedTree != null;
   }

   boolean hasNewLayout() {
      return this.mYogaNode.hasNewLayout();
   }

   boolean hasStateListAnimatorResSet() {
      return (this.mPrivateFlags & 1073741824L) != 0L;
   }

   boolean hasTouchExpansion() {
      return (this.mPrivateFlags & 33554432L) != 0L;
   }

   boolean hasTransitionKey() {
      return TextUtils.isEmpty(this.mTransitionKey) ^ true;
   }

   public boolean hasVisibilityHandlers() {
      return this.mVisibleHandler != null || this.mFocusedHandler != null || this.mUnfocusedHandler != null || this.mFullImpressionHandler != null || this.mInvisibleHandler != null || this.mVisibilityChangedHandler != null;
   }

   InternalNode heightAuto() {
      this.mYogaNode.setHeightAuto();
      return this;
   }

   InternalNode heightPercent(float var1) {
      if(this.maybeSkipLayoutProp(32768L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 32768L;
         this.mYogaNode.setHeightPercent(var1);
         return this;
      }
   }

   InternalNode heightPx(@Px int var1) {
      if(this.maybeSkipLayoutProp(32768L) && var1 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 32768L;
         this.mYogaNode.setHeight((float)var1);
         return this;
      }
   }

   InternalNode importantForAccessibility(int var1) {
      this.mPrivateFlags |= 128L;
      this.mImportantForAccessibility = var1;
      return this;
   }

   void init(YogaNode var1, ComponentContext var2) {
      if(var1 != null) {
         var1.setData(this);
      }

      this.mYogaNode = var1;
      this.mComponentContext = var2;
   }

   InternalNode interceptTouchHandler(EventHandler var1) {
      this.getOrCreateNodeInfo().setInterceptTouchHandler(var1);
      return this;
   }

   InternalNode invisibleHandler(@Nullable EventHandler<InvisibleEvent> var1) {
      this.mPrivateFlags |= 8388608L;
      this.mInvisibleHandler = addVisibilityHandler(this.mInvisibleHandler, var1);
      return this;
   }

   boolean isDuplicateParentStateEnabled() {
      return this.mDuplicateParentState;
   }

   boolean isForceViewWrapping() {
      return this.mForceViewWrapping;
   }

   boolean isInitialized() {
      return this.mYogaNode != null && this.mComponentContext != null;
   }

   boolean isNestedTreeHolder() {
      return this.mIsNestedTreeHolder;
   }

   public boolean isPaddingSet() {
      return (this.mPrivateFlags & 1024L) != 0L;
   }

   InternalNode justifyContent(YogaJustify var1) {
      if(ComponentsConfiguration.enableSkipYogaPropExperiment && var1 == YogaDefaults.JUSTIFY_CONTENT) {
         return this;
      } else {
         this.mYogaNode.setJustifyContent(var1);
         return this;
      }
   }

   InternalNode layoutDirection(YogaDirection var1) {
      if(this.maybeSkipLayoutProp(1L) && var1 == YogaDefaults.DIRECTION) {
         return this;
      } else {
         this.mPrivateFlags |= 1L;
         this.mYogaNode.setDirection(var1);
         return this;
      }
   }

   InternalNode longClickHandler(EventHandler<LongClickEvent> var1) {
      this.getOrCreateNodeInfo().setLongClickHandler(var1);
      return this;
   }

   InternalNode marginAuto(YogaEdge var1) {
      this.mPrivateFlags |= 512L;
      this.mYogaNode.setMarginAuto(var1);
      return this;
   }

   InternalNode marginPercent(YogaEdge var1, float var2) {
      if(this.maybeSkipLayoutProp(512L) && var2 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 512L;
         this.mYogaNode.setMarginPercent(var1, var2);
         return this;
      }
   }

   InternalNode marginPx(YogaEdge var1, @Px int var2) {
      if(this.maybeSkipLayoutProp(512L) && var2 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 512L;
         this.mYogaNode.setMargin(var1, (float)var2);
         return this;
      }
   }

   void markIsNestedTreeHolder(TreeProps var1) {
      this.mIsNestedTreeHolder = true;
      this.mPendingTreeProps = TreeProps.copy(var1);
   }

   void markLayoutSeen() {
      this.mYogaNode.markLayoutSeen();
   }

   InternalNode maxHeightPercent(float var1) {
      if(this.maybeSkipLayoutProp(131072L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 131072L;
         this.mYogaNode.setMaxHeightPercent(var1);
         return this;
      }
   }

   InternalNode maxHeightPx(@Px int var1) {
      if(this.maybeSkipLayoutProp(131072L) && var1 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 131072L;
         this.mYogaNode.setMaxHeight((float)var1);
         return this;
      }
   }

   InternalNode maxWidthPercent(float var1) {
      if(this.maybeSkipLayoutProp(16384L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 16384L;
         this.mYogaNode.setMaxWidthPercent(var1);
         return this;
      }
   }

   InternalNode maxWidthPx(@Px int var1) {
      if(this.maybeSkipLayoutProp(16384L) && var1 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 16384L;
         this.mYogaNode.setMaxWidth((float)var1);
         return this;
      }
   }

   InternalNode minHeightPercent(float var1) {
      if(this.maybeSkipLayoutProp(65536L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 65536L;
         this.mYogaNode.setMinHeightPercent(var1);
         return this;
      }
   }

   InternalNode minHeightPx(@Px int var1) {
      if(this.maybeSkipLayoutProp(65536L) && var1 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 65536L;
         this.mYogaNode.setMinHeight((float)var1);
         return this;
      }
   }

   InternalNode minWidthPercent(float var1) {
      if(this.maybeSkipLayoutProp(8192L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 8192L;
         this.mYogaNode.setMinWidthPercent(var1);
         return this;
      }
   }

   InternalNode minWidthPx(@Px int var1) {
      if(this.maybeSkipLayoutProp(8192L) && var1 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 8192L;
         this.mYogaNode.setMinWidth((float)var1);
         return this;
      }
   }

   InternalNode onInitializeAccessibilityEventHandler(EventHandler<OnInitializeAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setOnInitializeAccessibilityEventHandler(var1);
      return this;
   }

   InternalNode onInitializeAccessibilityNodeInfoHandler(EventHandler<OnInitializeAccessibilityNodeInfoEvent> var1) {
      this.getOrCreateNodeInfo().setOnInitializeAccessibilityNodeInfoHandler(var1);
      return this;
   }

   InternalNode onPopulateAccessibilityEventHandler(EventHandler<OnPopulateAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setOnPopulateAccessibilityEventHandler(var1);
      return this;
   }

   InternalNode onRequestSendAccessibilityEventHandler(EventHandler<OnRequestSendAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setOnRequestSendAccessibilityEventHandler(var1);
      return this;
   }

   InternalNode outlineProvider(ViewOutlineProvider var1) {
      this.getOrCreateNodeInfo().setOutlineProvider(var1);
      return this;
   }

   InternalNode paddingPercent(YogaEdge var1, float var2) {
      if(this.maybeSkipLayoutProp(1024L) && var2 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 1024L;
         if(this.mIsNestedTreeHolder) {
            this.getNestedTreePadding().set(var1, var2);
            this.setIsPaddingPercent(var1, true);
            return this;
         } else {
            this.mYogaNode.setPaddingPercent(var1, var2);
            return this;
         }
      }
   }

   InternalNode paddingPx(YogaEdge var1, @Px int var2) {
      if(this.maybeSkipLayoutProp(1024L) && var2 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 1024L;
         if(this.mIsNestedTreeHolder) {
            this.getNestedTreePadding().set(var1, (float)var2);
            this.setIsPaddingPercent(var1, false);
            return this;
         } else {
            this.mYogaNode.setPadding(var1, (float)var2);
            return this;
         }
      }
   }

   InternalNode performAccessibilityActionHandler(EventHandler<PerformAccessibilityActionEvent> var1) {
      this.getOrCreateNodeInfo().setPerformAccessibilityActionHandler(var1);
      return this;
   }

   InternalNode positionPercent(YogaEdge var1, float var2) {
      this.mPrivateFlags |= 2048L;
      this.mYogaNode.setPositionPercent(var1, var2);
      return this;
   }

   InternalNode positionPx(YogaEdge var1, @Px int var2) {
      this.mPrivateFlags |= 2048L;
      this.mYogaNode.setPosition(var1, (float)var2);
      return this;
   }

   InternalNode positionType(YogaPositionType var1) {
      if(this.maybeSkipLayoutProp(4L) && var1 == YogaDefaults.POSITION_TYPE) {
         return this;
      } else {
         this.mPrivateFlags |= 4L;
         this.mYogaNode.setPositionType(var1);
         return this;
      }
   }

   YogaDirection recursivelyResolveLayoutDirection() {
      YogaNode var1;
      for(var1 = this.mYogaNode; var1 != null && var1.getLayoutDirection() == YogaDirection.INHERIT; var1 = var1.getParent()) {
         ;
      }

      return var1 == null?YogaDirection.INHERIT:var1.getLayoutDirection();
   }

   void registerDebugComponent(DebugComponent var1) {
      this.mDebugComponents.add(var1);
   }

   void release() {
      if(!ComponentsConfiguration.disablePools) {
         if(this.mYogaNode != null) {
            if(this.mYogaNode.getParent() != null || this.mYogaNode.getChildCount() > 0) {
               throw new IllegalStateException("You should not free an attached Internalnode");
            }

            ComponentsPools.release(this.mYogaNode);
            this.mYogaNode = null;
         }

         this.mDebugComponents.clear();
         this.mResolvedTouchExpansionLeft = Float.NaN;
         this.mResolvedTouchExpansionRight = Float.NaN;
         this.mResolvedX = Float.NaN;
         this.mResolvedY = Float.NaN;
         this.mResolvedWidth = Float.NaN;
         this.mResolvedHeight = Float.NaN;
         this.mComponentContext = null;
         this.mComponents.clear();
         this.mNestedTree = null;
         this.mNestedTreeHolder = null;
         if(this.mNodeInfo != null) {
            this.mNodeInfo.release();
            this.mNodeInfo = null;
         }

         this.mImportantForAccessibility = 0;
         this.mDuplicateParentState = false;
         this.mBackground = null;
         this.mForeground = null;
         this.mForceViewWrapping = false;
         this.mVisibleHeightRatio = 0.0F;
         this.mVisibleWidthRatio = 0.0F;
         this.mVisibleHandler = null;
         this.mFocusedHandler = null;
         this.mUnfocusedHandler = null;
         this.mFullImpressionHandler = null;
         this.mInvisibleHandler = null;
         this.mPrivateFlags = 0L;
         this.mTransitionKey = null;
         Arrays.fill(this.mBorderColors, 0);
         Arrays.fill(this.mBorderRadius, 0.0F);
         this.mIsPaddingPercent = null;
         if(this.mTouchExpansion != null) {
            ComponentsPools.release(this.mTouchExpansion);
            this.mTouchExpansion = null;
         }

         if(this.mNestedTreePadding != null) {
            ComponentsPools.release(this.mNestedTreePadding);
            this.mNestedTreePadding = null;
         }

         if(this.mNestedTreeBorderWidth != null) {
            ComponentsPools.release(this.mNestedTreeBorderWidth);
            this.mNestedTreeBorderWidth = null;
         }

         this.mLastWidthSpec = -1;
         this.mLastHeightSpec = -1;
         this.mLastMeasuredHeight = -1.0F;
         this.mLastMeasuredWidth = -1.0F;
         this.mDiffNode = null;
         this.mCachedMeasuresValid = false;
         this.mIsNestedTreeHolder = false;
         this.mTestKey = null;
         this.mPendingTreeProps = null;
         this.mTransitions = null;
         this.mComponentsNeedingPreviousRenderData = null;
         this.mWorkingRangeRegistrations = null;
         this.mStateListAnimator = null;
         this.mStateListAnimatorRes = 0;
         ComponentsPools.release(this);
      }
   }

   InternalNode removeChildAt(int var1) {
      return (InternalNode)this.mYogaNode.removeChildAt(var1).getData();
   }

   InternalNode rotation(float var1) {
      this.wrapInView();
      this.getOrCreateNodeInfo().setRotation(var1);
      return this;
   }

   InternalNode scale(float var1) {
      this.wrapInView();
      this.getOrCreateNodeInfo().setScale(var1);
      return this;
   }

   InternalNode selected(boolean var1) {
      this.getOrCreateNodeInfo().setSelected(var1);
      return this;
   }

   InternalNode sendAccessibilityEventHandler(EventHandler<SendAccessibilityEventEvent> var1) {
      this.getOrCreateNodeInfo().setSendAccessibilityEventHandler(var1);
      return this;
   }

   InternalNode sendAccessibilityEventUncheckedHandler(EventHandler<SendAccessibilityEventUncheckedEvent> var1) {
      this.getOrCreateNodeInfo().setSendAccessibilityEventUncheckedHandler(var1);
      return this;
   }

   void setBaselineFunction(YogaBaselineFunction var1) {
      this.mYogaNode.setBaselineFunction(var1);
   }

   void setBorderWidth(YogaEdge var1, @Px int var2) {
      if(this.mIsNestedTreeHolder) {
         if(this.mNestedTreeBorderWidth == null) {
            this.mNestedTreeBorderWidth = ComponentsPools.acquireEdges();
         }

         this.mNestedTreeBorderWidth.set(var1, (float)var2);
      } else {
         this.mYogaNode.setBorder(var1, (float)var2);
      }
   }

   public void setCachedMeasuresValid(boolean var1) {
      this.mCachedMeasuresValid = var1;
   }

   void setDiffNode(DiffNode var1) {
      this.mDiffNode = var1;
   }

   public void setLastHeightSpec(int var1) {
      this.mLastHeightSpec = var1;
   }

   void setLastMeasuredHeight(float var1) {
      this.mLastMeasuredHeight = var1;
   }

   void setLastMeasuredWidth(float var1) {
      this.mLastMeasuredWidth = var1;
   }

   public void setLastWidthSpec(int var1) {
      this.mLastWidthSpec = var1;
   }

   void setMeasureFunction(YogaMeasureFunction var1) {
      this.mYogaNode.setMeasureFunction(var1);
   }

   void setNestedTree(InternalNode var1) {
      var1.mNestedTreeHolder = this;
      this.mNestedTree = var1;
   }

   void setStyleHeightFromSpec(int var1) {
      int var2 = SizeSpec.getMode(var1);
      if(var2 != Integer.MIN_VALUE) {
         if(var2 != 0) {
            if(var2 == 1073741824) {
               this.mYogaNode.setHeight((float)SizeSpec.getSize(var1));
            }
         } else {
            this.mYogaNode.setHeight(Float.NaN);
         }
      } else {
         this.mYogaNode.setMaxHeight((float)SizeSpec.getSize(var1));
      }
   }

   void setStyleWidthFromSpec(int var1) {
      int var2 = SizeSpec.getMode(var1);
      if(var2 != Integer.MIN_VALUE) {
         if(var2 != 0) {
            if(var2 == 1073741824) {
               this.mYogaNode.setWidth((float)SizeSpec.getSize(var1));
            }
         } else {
            this.mYogaNode.setWidth(Float.NaN);
         }
      } else {
         this.mYogaNode.setMaxWidth((float)SizeSpec.getSize(var1));
      }
   }

   InternalNode shadowElevationPx(float var1) {
      this.getOrCreateNodeInfo().setShadowElevation(var1);
      return this;
   }

   boolean shouldDrawBorders() {
      return this.hasBorderColor() && (this.mYogaNode.getLayoutBorder(YogaEdge.LEFT) != 0.0F || this.mYogaNode.getLayoutBorder(YogaEdge.TOP) != 0.0F || this.mYogaNode.getLayoutBorder(YogaEdge.RIGHT) != 0.0F || this.mYogaNode.getLayoutBorder(YogaEdge.BOTTOM) != 0.0F);
   }

   InternalNode stateListAnimator(StateListAnimator var1) {
      this.mPrivateFlags |= 536870912L;
      this.mStateListAnimator = var1;
      this.wrapInView();
      return this;
   }

   InternalNode stateListAnimatorRes(@DrawableRes int var1) {
      this.mPrivateFlags |= 1073741824L;
      this.mStateListAnimatorRes = var1;
      this.wrapInView();
      return this;
   }

   InternalNode testKey(String var1) {
      this.mTestKey = var1;
      return this;
   }

   InternalNode touchExpansionPx(YogaEdge var1, @Px int var2) {
      if(this.mTouchExpansion == null) {
         this.mTouchExpansion = ComponentsPools.acquireEdges();
      }

      this.mPrivateFlags |= 33554432L;
      this.mTouchExpansion.set(var1, (float)var2);
      return this;
   }

   InternalNode touchHandler(EventHandler<TouchEvent> var1) {
      this.getOrCreateNodeInfo().setTouchHandler(var1);
      return this;
   }

   InternalNode transitionKey(String var1) {
      if(VERSION.SDK_INT >= 14 && !TextUtils.isEmpty(var1)) {
         this.mPrivateFlags |= 134217728L;
         this.mTransitionKey = var1;
      }

      return this;
   }

   InternalNode unfocusedHandler(@Nullable EventHandler<UnfocusedVisibleEvent> var1) {
      this.mPrivateFlags |= 16777216L;
      this.mUnfocusedHandler = addVisibilityHandler(this.mUnfocusedHandler, var1);
      return this;
   }

   InternalNode viewTag(Object var1) {
      this.getOrCreateNodeInfo().setViewTag(var1);
      return this;
   }

   InternalNode viewTags(SparseArray<Object> var1) {
      this.getOrCreateNodeInfo().setViewTags(var1);
      return this;
   }

   InternalNode visibilityChangedHandler(@Nullable EventHandler<VisibilityChangedEvent> var1) {
      this.mPrivateFlags |= 2147483648L;
      this.mVisibilityChangedHandler = addVisibilityHandler(this.mVisibilityChangedHandler, var1);
      return this;
   }

   InternalNode visibleHandler(@Nullable EventHandler<VisibleEvent> var1) {
      this.mPrivateFlags |= 1048576L;
      this.mVisibleHandler = addVisibilityHandler(this.mVisibleHandler, var1);
      return this;
   }

   InternalNode visibleHeightRatio(float var1) {
      this.mVisibleHeightRatio = var1;
      return this;
   }

   InternalNode visibleWidthRatio(float var1) {
      this.mVisibleWidthRatio = var1;
      return this;
   }

   InternalNode widthAuto() {
      this.mYogaNode.setWidthAuto();
      return this;
   }

   InternalNode widthPercent(float var1) {
      if(this.maybeSkipLayoutProp(4096L) && var1 == 0.0F) {
         return this;
      } else {
         this.mPrivateFlags |= 4096L;
         this.mYogaNode.setWidthPercent(var1);
         return this;
      }
   }

   InternalNode widthPx(@Px int var1) {
      if(this.maybeSkipLayoutProp(4096L) && var1 == 0) {
         return this;
      } else {
         this.mPrivateFlags |= 4096L;
         this.mYogaNode.setWidth((float)var1);
         return this;
      }
   }

   InternalNode wrap(YogaWrap var1) {
      if(ComponentsConfiguration.enableSkipYogaPropExperiment && var1 == YogaDefaults.FLEX_WRAP) {
         return this;
      } else {
         this.mYogaNode.setWrap(var1);
         return this;
      }
   }

   InternalNode wrapInView() {
      this.mForceViewWrapping = true;
      return this;
   }
}
