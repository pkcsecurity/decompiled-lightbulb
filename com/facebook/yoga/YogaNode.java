package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaConfig;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import com.facebook.yoga.YogaWrap;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

@DoNotStrip
public class YogaNode {

   private static final int BORDER = 4;
   private static final int MARGIN = 1;
   private static final int PADDING = 2;
   private YogaBaselineFunction mBaselineFunction;
   @DoNotStrip
   private float mBorderBottom = 0.0F;
   @DoNotStrip
   private float mBorderLeft = 0.0F;
   @DoNotStrip
   private float mBorderRight = 0.0F;
   @DoNotStrip
   private float mBorderTop = 0.0F;
   private List<YogaNode> mChildren;
   private Object mData;
   @DoNotStrip
   private int mEdgeSetFlag = 0;
   @DoNotStrip
   private boolean mHasNewLayout = true;
   private boolean mHasSetPosition = false;
   @DoNotStrip
   private float mHeight = Float.NaN;
   @DoNotStrip
   private int mLayoutDirection = 0;
   @DoNotStrip
   private float mLeft = Float.NaN;
   @DoNotStrip
   private float mMarginBottom = 0.0F;
   @DoNotStrip
   private float mMarginLeft = 0.0F;
   @DoNotStrip
   private float mMarginRight = 0.0F;
   @DoNotStrip
   private float mMarginTop = 0.0F;
   private YogaMeasureFunction mMeasureFunction;
   private long mNativePointer;
   @DoNotStrip
   private float mPaddingBottom = 0.0F;
   @DoNotStrip
   private float mPaddingLeft = 0.0F;
   @DoNotStrip
   private float mPaddingRight = 0.0F;
   @DoNotStrip
   private float mPaddingTop = 0.0F;
   private YogaNode mParent;
   @DoNotStrip
   private float mTop = Float.NaN;
   @DoNotStrip
   private float mWidth = Float.NaN;


   static {
      SoLoader.loadLibrary("yoga");
   }

   public YogaNode() {
      this.mNativePointer = this.jni_YGNodeNew();
      if(this.mNativePointer == 0L) {
         throw new IllegalStateException("Failed to allocate native memory");
      }
   }

   public YogaNode(YogaConfig var1) {
      this.mNativePointer = this.jni_YGNodeNewWithConfig(var1.mNativePointer);
      if(this.mNativePointer == 0L) {
         throw new IllegalStateException("Failed to allocate native memory");
      }
   }

   private native void jni_YGNodeCalculateLayout(long var1, float var3, float var4);

   private native void jni_YGNodeCopyStyle(long var1, long var3);

   private native void jni_YGNodeFree(long var1);

   static native int jni_YGNodeGetInstanceCount();

   private native void jni_YGNodeInsertChild(long var1, long var3, int var5);

   private native boolean jni_YGNodeIsDirty(long var1);

   private native void jni_YGNodeMarkDirty(long var1);

   private native long jni_YGNodeNew();

   private native long jni_YGNodeNewWithConfig(long var1);

   private native void jni_YGNodePrint(long var1);

   private native void jni_YGNodeRemoveChild(long var1, long var3);

   private native void jni_YGNodeReset(long var1);

   private native void jni_YGNodeSetHasBaselineFunc(long var1, boolean var3);

   private native void jni_YGNodeSetHasMeasureFunc(long var1, boolean var3);

   private native int jni_YGNodeStyleGetAlignContent(long var1);

   private native int jni_YGNodeStyleGetAlignItems(long var1);

   private native int jni_YGNodeStyleGetAlignSelf(long var1);

   private native float jni_YGNodeStyleGetAspectRatio(long var1);

   private native float jni_YGNodeStyleGetBorder(long var1, int var3);

   private native int jni_YGNodeStyleGetDirection(long var1);

   private native int jni_YGNodeStyleGetDisplay(long var1);

   private native Object jni_YGNodeStyleGetFlexBasis(long var1);

   private native int jni_YGNodeStyleGetFlexDirection(long var1);

   private native float jni_YGNodeStyleGetFlexGrow(long var1);

   private native float jni_YGNodeStyleGetFlexShrink(long var1);

   private native Object jni_YGNodeStyleGetHeight(long var1);

   private native int jni_YGNodeStyleGetJustifyContent(long var1);

   private native Object jni_YGNodeStyleGetMargin(long var1, int var3);

   private native Object jni_YGNodeStyleGetMaxHeight(long var1);

   private native Object jni_YGNodeStyleGetMaxWidth(long var1);

   private native Object jni_YGNodeStyleGetMinHeight(long var1);

   private native Object jni_YGNodeStyleGetMinWidth(long var1);

   private native int jni_YGNodeStyleGetOverflow(long var1);

   private native Object jni_YGNodeStyleGetPadding(long var1, int var3);

   private native Object jni_YGNodeStyleGetPosition(long var1, int var3);

   private native int jni_YGNodeStyleGetPositionType(long var1);

   private native Object jni_YGNodeStyleGetWidth(long var1);

   private native void jni_YGNodeStyleSetAlignContent(long var1, int var3);

   private native void jni_YGNodeStyleSetAlignItems(long var1, int var3);

   private native void jni_YGNodeStyleSetAlignSelf(long var1, int var3);

   private native void jni_YGNodeStyleSetAspectRatio(long var1, float var3);

   private native void jni_YGNodeStyleSetBorder(long var1, int var3, float var4);

   private native void jni_YGNodeStyleSetDirection(long var1, int var3);

   private native void jni_YGNodeStyleSetDisplay(long var1, int var3);

   private native void jni_YGNodeStyleSetFlex(long var1, float var3);

   private native void jni_YGNodeStyleSetFlexBasis(long var1, float var3);

   private native void jni_YGNodeStyleSetFlexBasisAuto(long var1);

   private native void jni_YGNodeStyleSetFlexBasisPercent(long var1, float var3);

   private native void jni_YGNodeStyleSetFlexDirection(long var1, int var3);

   private native void jni_YGNodeStyleSetFlexGrow(long var1, float var3);

   private native void jni_YGNodeStyleSetFlexShrink(long var1, float var3);

   private native void jni_YGNodeStyleSetFlexWrap(long var1, int var3);

   private native void jni_YGNodeStyleSetHeight(long var1, float var3);

   private native void jni_YGNodeStyleSetHeightAuto(long var1);

   private native void jni_YGNodeStyleSetHeightPercent(long var1, float var3);

   private native void jni_YGNodeStyleSetJustifyContent(long var1, int var3);

   private native void jni_YGNodeStyleSetMargin(long var1, int var3, float var4);

   private native void jni_YGNodeStyleSetMarginAuto(long var1, int var3);

   private native void jni_YGNodeStyleSetMarginPercent(long var1, int var3, float var4);

   private native void jni_YGNodeStyleSetMaxHeight(long var1, float var3);

   private native void jni_YGNodeStyleSetMaxHeightPercent(long var1, float var3);

   private native void jni_YGNodeStyleSetMaxWidth(long var1, float var3);

   private native void jni_YGNodeStyleSetMaxWidthPercent(long var1, float var3);

   private native void jni_YGNodeStyleSetMinHeight(long var1, float var3);

   private native void jni_YGNodeStyleSetMinHeightPercent(long var1, float var3);

   private native void jni_YGNodeStyleSetMinWidth(long var1, float var3);

   private native void jni_YGNodeStyleSetMinWidthPercent(long var1, float var3);

   private native void jni_YGNodeStyleSetOverflow(long var1, int var3);

   private native void jni_YGNodeStyleSetPadding(long var1, int var3, float var4);

   private native void jni_YGNodeStyleSetPaddingPercent(long var1, int var3, float var4);

   private native void jni_YGNodeStyleSetPosition(long var1, int var3, float var4);

   private native void jni_YGNodeStyleSetPositionPercent(long var1, int var3, float var4);

   private native void jni_YGNodeStyleSetPositionType(long var1, int var3);

   private native void jni_YGNodeStyleSetWidth(long var1, float var3);

   private native void jni_YGNodeStyleSetWidthAuto(long var1);

   private native void jni_YGNodeStyleSetWidthPercent(long var1, float var3);

   public void addChildAt(YogaNode var1, int var2) {
      if(var1.mParent != null) {
         throw new IllegalStateException("Child already has a parent, it must be removed first.");
      } else {
         if(this.mChildren == null) {
            this.mChildren = new ArrayList(4);
         }

         this.mChildren.add(var2, var1);
         var1.mParent = this;
         this.jni_YGNodeInsertChild(this.mNativePointer, var1.mNativePointer, var2);
      }
   }

   @DoNotStrip
   public final float baseline(float var1, float var2) {
      return this.mBaselineFunction.baseline(this, var1, var2);
   }

   public void calculateLayout(float var1, float var2) {
      this.jni_YGNodeCalculateLayout(this.mNativePointer, var1, var2);
   }

   public void copyStyle(YogaNode var1) {
      this.jni_YGNodeCopyStyle(this.mNativePointer, var1.mNativePointer);
   }

   public void dirty() {
      this.jni_YGNodeMarkDirty(this.mNativePointer);
   }

   protected void finalize() throws Throwable {
      try {
         this.jni_YGNodeFree(this.mNativePointer);
      } finally {
         super.finalize();
      }

   }

   public YogaAlign getAlignContent() {
      return YogaAlign.fromInt(this.jni_YGNodeStyleGetAlignContent(this.mNativePointer));
   }

   public YogaAlign getAlignItems() {
      return YogaAlign.fromInt(this.jni_YGNodeStyleGetAlignItems(this.mNativePointer));
   }

   public YogaAlign getAlignSelf() {
      return YogaAlign.fromInt(this.jni_YGNodeStyleGetAlignSelf(this.mNativePointer));
   }

   public float getAspectRatio() {
      return this.jni_YGNodeStyleGetAspectRatio(this.mNativePointer);
   }

   public float getBorder(YogaEdge var1) {
      return (this.mEdgeSetFlag & 4) != 4?Float.NaN:this.jni_YGNodeStyleGetBorder(this.mNativePointer, var1.intValue());
   }

   public YogaNode getChildAt(int var1) {
      return (YogaNode)this.mChildren.get(var1);
   }

   public int getChildCount() {
      return this.mChildren == null?0:this.mChildren.size();
   }

   public Object getData() {
      return this.mData;
   }

   public YogaDisplay getDisplay() {
      return YogaDisplay.fromInt(this.jni_YGNodeStyleGetDisplay(this.mNativePointer));
   }

   public YogaValue getFlexBasis() {
      return (YogaValue)this.jni_YGNodeStyleGetFlexBasis(this.mNativePointer);
   }

   public YogaFlexDirection getFlexDirection() {
      return YogaFlexDirection.fromInt(this.jni_YGNodeStyleGetFlexDirection(this.mNativePointer));
   }

   public float getFlexGrow() {
      return this.jni_YGNodeStyleGetFlexGrow(this.mNativePointer);
   }

   public float getFlexShrink() {
      return this.jni_YGNodeStyleGetFlexShrink(this.mNativePointer);
   }

   public YogaValue getHeight() {
      return (YogaValue)this.jni_YGNodeStyleGetHeight(this.mNativePointer);
   }

   public YogaJustify getJustifyContent() {
      return YogaJustify.fromInt(this.jni_YGNodeStyleGetJustifyContent(this.mNativePointer));
   }

   public float getLayoutBorder(YogaEdge var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaEdge[var1.ordinal()]) {
      case 1:
         return this.mBorderLeft;
      case 2:
         return this.mBorderTop;
      case 3:
         return this.mBorderRight;
      case 4:
         return this.mBorderBottom;
      case 5:
         if(this.getLayoutDirection() == YogaDirection.RTL) {
            return this.mBorderRight;
         }

         return this.mBorderLeft;
      case 6:
         if(this.getLayoutDirection() == YogaDirection.RTL) {
            return this.mBorderLeft;
         }

         return this.mBorderRight;
      default:
         throw new IllegalArgumentException("Cannot get layout border of multi-edge shorthands");
      }
   }

   public YogaDirection getLayoutDirection() {
      return YogaDirection.fromInt(this.mLayoutDirection);
   }

   public float getLayoutHeight() {
      return this.mHeight;
   }

   public float getLayoutMargin(YogaEdge var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaEdge[var1.ordinal()]) {
      case 1:
         return this.mMarginLeft;
      case 2:
         return this.mMarginTop;
      case 3:
         return this.mMarginRight;
      case 4:
         return this.mMarginBottom;
      case 5:
         if(this.getLayoutDirection() == YogaDirection.RTL) {
            return this.mMarginRight;
         }

         return this.mMarginLeft;
      case 6:
         if(this.getLayoutDirection() == YogaDirection.RTL) {
            return this.mMarginLeft;
         }

         return this.mMarginRight;
      default:
         throw new IllegalArgumentException("Cannot get layout margins of multi-edge shorthands");
      }
   }

   public float getLayoutPadding(YogaEdge var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaEdge[var1.ordinal()]) {
      case 1:
         return this.mPaddingLeft;
      case 2:
         return this.mPaddingTop;
      case 3:
         return this.mPaddingRight;
      case 4:
         return this.mPaddingBottom;
      case 5:
         if(this.getLayoutDirection() == YogaDirection.RTL) {
            return this.mPaddingRight;
         }

         return this.mPaddingLeft;
      case 6:
         if(this.getLayoutDirection() == YogaDirection.RTL) {
            return this.mPaddingLeft;
         }

         return this.mPaddingRight;
      default:
         throw new IllegalArgumentException("Cannot get layout paddings of multi-edge shorthands");
      }
   }

   public float getLayoutWidth() {
      return this.mWidth;
   }

   public float getLayoutX() {
      return this.mLeft;
   }

   public float getLayoutY() {
      return this.mTop;
   }

   public YogaValue getMargin(YogaEdge var1) {
      return (this.mEdgeSetFlag & 1) != 1?YogaValue.UNDEFINED:(YogaValue)this.jni_YGNodeStyleGetMargin(this.mNativePointer, var1.intValue());
   }

   public YogaValue getMaxHeight() {
      return (YogaValue)this.jni_YGNodeStyleGetMaxHeight(this.mNativePointer);
   }

   public YogaValue getMaxWidth() {
      return (YogaValue)this.jni_YGNodeStyleGetMaxWidth(this.mNativePointer);
   }

   public YogaValue getMinHeight() {
      return (YogaValue)this.jni_YGNodeStyleGetMinHeight(this.mNativePointer);
   }

   public YogaValue getMinWidth() {
      return (YogaValue)this.jni_YGNodeStyleGetMinWidth(this.mNativePointer);
   }

   public YogaOverflow getOverflow() {
      return YogaOverflow.fromInt(this.jni_YGNodeStyleGetOverflow(this.mNativePointer));
   }

   public YogaValue getPadding(YogaEdge var1) {
      return (this.mEdgeSetFlag & 2) != 2?YogaValue.UNDEFINED:(YogaValue)this.jni_YGNodeStyleGetPadding(this.mNativePointer, var1.intValue());
   }

   @Nullable
   public YogaNode getParent() {
      return this.mParent;
   }

   public YogaValue getPosition(YogaEdge var1) {
      return !this.mHasSetPosition?YogaValue.UNDEFINED:(YogaValue)this.jni_YGNodeStyleGetPosition(this.mNativePointer, var1.intValue());
   }

   public YogaPositionType getPositionType() {
      return YogaPositionType.fromInt(this.jni_YGNodeStyleGetPositionType(this.mNativePointer));
   }

   public YogaDirection getStyleDirection() {
      return YogaDirection.fromInt(this.jni_YGNodeStyleGetDirection(this.mNativePointer));
   }

   public YogaValue getWidth() {
      return (YogaValue)this.jni_YGNodeStyleGetWidth(this.mNativePointer);
   }

   public boolean hasNewLayout() {
      return this.mHasNewLayout;
   }

   public int indexOf(YogaNode var1) {
      return this.mChildren == null?-1:this.mChildren.indexOf(var1);
   }

   public boolean isDirty() {
      return this.jni_YGNodeIsDirty(this.mNativePointer);
   }

   public boolean isMeasureDefined() {
      return this.mMeasureFunction != null;
   }

   public void markLayoutSeen() {
      this.mHasNewLayout = false;
   }

   @DoNotStrip
   public final long measure(float var1, int var2, float var3, int var4) {
      if(!this.isMeasureDefined()) {
         throw new RuntimeException("Measure function isn\'t defined!");
      } else {
         return this.mMeasureFunction.measure(this, var1, YogaMeasureMode.fromInt(var2), var3, YogaMeasureMode.fromInt(var4));
      }
   }

   public void print() {
      this.jni_YGNodePrint(this.mNativePointer);
   }

   public YogaNode removeChildAt(int var1) {
      YogaNode var2 = (YogaNode)this.mChildren.remove(var1);
      var2.mParent = null;
      this.jni_YGNodeRemoveChild(this.mNativePointer, var2.mNativePointer);
      return var2;
   }

   public void reset() {
      this.mEdgeSetFlag = 0;
      this.mHasSetPosition = false;
      this.mHasNewLayout = true;
      this.mWidth = Float.NaN;
      this.mHeight = Float.NaN;
      this.mTop = Float.NaN;
      this.mLeft = Float.NaN;
      this.mMarginLeft = 0.0F;
      this.mMarginTop = 0.0F;
      this.mMarginRight = 0.0F;
      this.mMarginBottom = 0.0F;
      this.mPaddingLeft = 0.0F;
      this.mPaddingTop = 0.0F;
      this.mPaddingRight = 0.0F;
      this.mPaddingBottom = 0.0F;
      this.mBorderLeft = 0.0F;
      this.mBorderTop = 0.0F;
      this.mBorderRight = 0.0F;
      this.mBorderBottom = 0.0F;
      this.mLayoutDirection = 0;
      this.mMeasureFunction = null;
      this.mBaselineFunction = null;
      this.mData = null;
      this.jni_YGNodeReset(this.mNativePointer);
   }

   public void setAlignContent(YogaAlign var1) {
      this.jni_YGNodeStyleSetAlignContent(this.mNativePointer, var1.intValue());
   }

   public void setAlignItems(YogaAlign var1) {
      this.jni_YGNodeStyleSetAlignItems(this.mNativePointer, var1.intValue());
   }

   public void setAlignSelf(YogaAlign var1) {
      this.jni_YGNodeStyleSetAlignSelf(this.mNativePointer, var1.intValue());
   }

   public void setAspectRatio(float var1) {
      this.jni_YGNodeStyleSetAspectRatio(this.mNativePointer, var1);
   }

   public void setBaselineFunction(YogaBaselineFunction var1) {
      this.mBaselineFunction = var1;
      long var2 = this.mNativePointer;
      boolean var4;
      if(var1 != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      this.jni_YGNodeSetHasBaselineFunc(var2, var4);
   }

   public void setBorder(YogaEdge var1, float var2) {
      this.mEdgeSetFlag |= 4;
      this.jni_YGNodeStyleSetBorder(this.mNativePointer, var1.intValue(), var2);
   }

   public void setData(Object var1) {
      this.mData = var1;
   }

   public void setDirection(YogaDirection var1) {
      this.jni_YGNodeStyleSetDirection(this.mNativePointer, var1.intValue());
   }

   public void setDisplay(YogaDisplay var1) {
      this.jni_YGNodeStyleSetDisplay(this.mNativePointer, var1.intValue());
   }

   public void setFlex(float var1) {
      this.jni_YGNodeStyleSetFlex(this.mNativePointer, var1);
   }

   public void setFlexBasis(float var1) {
      this.jni_YGNodeStyleSetFlexBasis(this.mNativePointer, var1);
   }

   public void setFlexBasisAuto() {
      this.jni_YGNodeStyleSetFlexBasisAuto(this.mNativePointer);
   }

   public void setFlexBasisPercent(float var1) {
      this.jni_YGNodeStyleSetFlexBasisPercent(this.mNativePointer, var1);
   }

   public void setFlexDirection(YogaFlexDirection var1) {
      this.jni_YGNodeStyleSetFlexDirection(this.mNativePointer, var1.intValue());
   }

   public void setFlexGrow(float var1) {
      this.jni_YGNodeStyleSetFlexGrow(this.mNativePointer, var1);
   }

   public void setFlexShrink(float var1) {
      this.jni_YGNodeStyleSetFlexShrink(this.mNativePointer, var1);
   }

   public void setHeight(float var1) {
      this.jni_YGNodeStyleSetHeight(this.mNativePointer, var1);
   }

   public void setHeightAuto() {
      this.jni_YGNodeStyleSetHeightAuto(this.mNativePointer);
   }

   public void setHeightPercent(float var1) {
      this.jni_YGNodeStyleSetHeightPercent(this.mNativePointer, var1);
   }

   public void setJustifyContent(YogaJustify var1) {
      this.jni_YGNodeStyleSetJustifyContent(this.mNativePointer, var1.intValue());
   }

   public void setMargin(YogaEdge var1, float var2) {
      this.mEdgeSetFlag |= 1;
      this.jni_YGNodeStyleSetMargin(this.mNativePointer, var1.intValue(), var2);
   }

   public void setMarginAuto(YogaEdge var1) {
      this.mEdgeSetFlag |= 1;
      this.jni_YGNodeStyleSetMarginAuto(this.mNativePointer, var1.intValue());
   }

   public void setMarginPercent(YogaEdge var1, float var2) {
      this.mEdgeSetFlag |= 1;
      this.jni_YGNodeStyleSetMarginPercent(this.mNativePointer, var1.intValue(), var2);
   }

   public void setMaxHeight(float var1) {
      this.jni_YGNodeStyleSetMaxHeight(this.mNativePointer, var1);
   }

   public void setMaxHeightPercent(float var1) {
      this.jni_YGNodeStyleSetMaxHeightPercent(this.mNativePointer, var1);
   }

   public void setMaxWidth(float var1) {
      this.jni_YGNodeStyleSetMaxWidth(this.mNativePointer, var1);
   }

   public void setMaxWidthPercent(float var1) {
      this.jni_YGNodeStyleSetMaxWidthPercent(this.mNativePointer, var1);
   }

   public void setMeasureFunction(YogaMeasureFunction var1) {
      this.mMeasureFunction = var1;
      long var2 = this.mNativePointer;
      boolean var4;
      if(var1 != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      this.jni_YGNodeSetHasMeasureFunc(var2, var4);
   }

   public void setMinHeight(float var1) {
      this.jni_YGNodeStyleSetMinHeight(this.mNativePointer, var1);
   }

   public void setMinHeightPercent(float var1) {
      this.jni_YGNodeStyleSetMinHeightPercent(this.mNativePointer, var1);
   }

   public void setMinWidth(float var1) {
      this.jni_YGNodeStyleSetMinWidth(this.mNativePointer, var1);
   }

   public void setMinWidthPercent(float var1) {
      this.jni_YGNodeStyleSetMinWidthPercent(this.mNativePointer, var1);
   }

   public void setOverflow(YogaOverflow var1) {
      this.jni_YGNodeStyleSetOverflow(this.mNativePointer, var1.intValue());
   }

   public void setPadding(YogaEdge var1, float var2) {
      this.mEdgeSetFlag |= 2;
      this.jni_YGNodeStyleSetPadding(this.mNativePointer, var1.intValue(), var2);
   }

   public void setPaddingPercent(YogaEdge var1, float var2) {
      this.mEdgeSetFlag |= 2;
      this.jni_YGNodeStyleSetPaddingPercent(this.mNativePointer, var1.intValue(), var2);
   }

   public void setPosition(YogaEdge var1, float var2) {
      this.mHasSetPosition = true;
      this.jni_YGNodeStyleSetPosition(this.mNativePointer, var1.intValue(), var2);
   }

   public void setPositionPercent(YogaEdge var1, float var2) {
      this.mHasSetPosition = true;
      this.jni_YGNodeStyleSetPositionPercent(this.mNativePointer, var1.intValue(), var2);
   }

   public void setPositionType(YogaPositionType var1) {
      this.jni_YGNodeStyleSetPositionType(this.mNativePointer, var1.intValue());
   }

   public void setWidth(float var1) {
      this.jni_YGNodeStyleSetWidth(this.mNativePointer, var1);
   }

   public void setWidthAuto() {
      this.jni_YGNodeStyleSetWidthAuto(this.mNativePointer);
   }

   public void setWidthPercent(float var1) {
      this.jni_YGNodeStyleSetWidthPercent(this.mNativePointer, var1);
   }

   public void setWrap(YogaWrap var1) {
      this.jni_YGNodeStyleSetFlexWrap(this.mNativePointer, var1.intValue());
   }
}
