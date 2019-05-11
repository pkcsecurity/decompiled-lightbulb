package com.facebook.react.flat;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.AttachDetachListener;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.FlatNativeViewHierarchyManager;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.NoSuchNativeViewException;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.TouchTargetHelper;
import com.facebook.react.uimanager.UIViewOperationQueue;
import java.util.ArrayList;
import javax.annotation.Nullable;

final class FlatUIViewOperationQueue extends UIViewOperationQueue {

   private static final int[] MEASURE_BUFFER = new int[4];
   private final FlatNativeViewHierarchyManager mNativeViewHierarchyManager;
   private final FlatUIViewOperationQueue.ProcessLayoutRequests mProcessLayoutRequests = new FlatUIViewOperationQueue.ProcessLayoutRequests(null);


   public FlatUIViewOperationQueue(ReactApplicationContext var1, FlatNativeViewHierarchyManager var2, int var3) {
      super(var1, var2, var3);
      this.mNativeViewHierarchyManager = var2;
   }

   public FlatUIViewOperationQueue.UpdateViewBounds createUpdateViewBounds(int var1, int var2, int var3, int var4, int var5) {
      return new FlatUIViewOperationQueue.UpdateViewBounds(var1, var2, var3, var4, var5, null);
   }

   public FlatUIViewOperationQueue.ViewManagerCommand createViewManagerCommand(int var1, int var2, @Nullable ReadableArray var3) {
      return new FlatUIViewOperationQueue.ViewManagerCommand(var1, var2, var3);
   }

   public FlatUIViewOperationQueue.DetachAllChildrenFromViews enqueueDetachAllChildrenFromViews() {
      FlatUIViewOperationQueue.DetachAllChildrenFromViews var1 = new FlatUIViewOperationQueue.DetachAllChildrenFromViews();
      this.enqueueUIOperation(var1);
      return var1;
   }

   public void enqueueDropViews(ArrayList<Integer> var1, ArrayList<Integer> var2) {
      this.enqueueUIOperation(new FlatUIViewOperationQueue.DropViews(var1, var2, null));
   }

   public void enqueueFindTargetForTouch(int var1, float var2, float var3, Callback var4) {
      this.enqueueUIOperation(new FlatUIViewOperationQueue.FindTargetForTouchOperation(var1, var2, var3, var4, null));
   }

   void enqueueFlatUIOperation(UIViewOperationQueue.UIOperation var1) {
      this.enqueueUIOperation(var1);
   }

   public void enqueueMeasureVirtualView(int var1, float var2, float var3, float var4, float var5, boolean var6, Callback var7) {
      this.enqueueUIOperation(new FlatUIViewOperationQueue.MeasureVirtualView(var1, var2, var3, var4, var5, var6, var7, null));
   }

   public void enqueueProcessLayoutRequests() {
      this.enqueueUIOperation(this.mProcessLayoutRequests);
   }

   public void enqueueSetPadding(int var1, int var2, int var3, int var4, int var5) {
      this.enqueueUIOperation(new FlatUIViewOperationQueue.SetPadding(var1, var2, var3, var4, var5, null));
   }

   public void enqueueUpdateClippingMountState(int var1, @Nullable DrawCommand[] var2, SparseIntArray var3, float[] var4, float[] var5, @Nullable AttachDetachListener[] var6, @Nullable NodeRegion[] var7, float[] var8, float[] var9, boolean var10) {
      this.enqueueUIOperation(new FlatUIViewOperationQueue.UpdateClippingMountState(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, null));
   }

   public void enqueueUpdateMountState(int var1, @Nullable DrawCommand[] var2, @Nullable AttachDetachListener[] var3, @Nullable NodeRegion[] var4) {
      this.enqueueUIOperation(new FlatUIViewOperationQueue.UpdateMountState(var1, var2, var3, var4, null));
   }

   public void enqueueUpdateViewGroup(int var1, int[] var2, int[] var3) {
      this.enqueueUIOperation(new FlatUIViewOperationQueue.UpdateViewGroup(var1, var2, var3, null));
   }

   final class SetPadding implements UIViewOperationQueue.UIOperation {

      private final int mPaddingBottom;
      private final int mPaddingLeft;
      private final int mPaddingRight;
      private final int mPaddingTop;
      private final int mReactTag;


      private SetPadding(int var2, int var3, int var4, int var5, int var6) {
         this.mReactTag = var2;
         this.mPaddingLeft = var3;
         this.mPaddingTop = var4;
         this.mPaddingRight = var5;
         this.mPaddingBottom = var6;
      }

      // $FF: synthetic method
      SetPadding(int var2, int var3, int var4, int var5, int var6, Object var7) {
         this(var2, var3, var4, var5, var6);
      }

      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.setPadding(this.mReactTag, this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
      }
   }

   public final class DetachAllChildrenFromViews implements UIViewOperationQueue.UIOperation {

      @Nullable
      private int[] mViewsToDetachAllChildrenFrom;


      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.detachAllChildrenFromViews(this.mViewsToDetachAllChildrenFrom);
      }

      public void setViewsToDetachAllChildrenFrom(int[] var1) {
         this.mViewsToDetachAllChildrenFrom = var1;
      }
   }

   final class UpdateClippingMountState implements UIViewOperationQueue.UIOperation {

      @Nullable
      private final AttachDetachListener[] mAttachDetachListeners;
      private final float[] mCommandMaxBot;
      private final float[] mCommandMinTop;
      @Nullable
      private final DrawCommand[] mDrawCommands;
      private final SparseIntArray mDrawViewIndexMap;
      @Nullable
      private final NodeRegion[] mNodeRegions;
      private final int mReactTag;
      private final float[] mRegionMaxBot;
      private final float[] mRegionMinTop;
      private final boolean mWillMountViews;


      private UpdateClippingMountState(int var2, @Nullable DrawCommand[] var3, SparseIntArray var4, float[] var5, float[] var6, @Nullable AttachDetachListener[] var7, @Nullable NodeRegion[] var8, float[] var9, float[] var10, boolean var11) {
         this.mReactTag = var2;
         this.mDrawCommands = var3;
         this.mDrawViewIndexMap = var4;
         this.mCommandMaxBot = var5;
         this.mCommandMinTop = var6;
         this.mAttachDetachListeners = var7;
         this.mNodeRegions = var8;
         this.mRegionMaxBot = var9;
         this.mRegionMinTop = var10;
         this.mWillMountViews = var11;
      }

      // $FF: synthetic method
      UpdateClippingMountState(int var2, DrawCommand[] var3, SparseIntArray var4, float[] var5, float[] var6, AttachDetachListener[] var7, NodeRegion[] var8, float[] var9, float[] var10, boolean var11, Object var12) {
         this(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
      }

      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateClippingMountState(this.mReactTag, this.mDrawCommands, this.mDrawViewIndexMap, this.mCommandMaxBot, this.mCommandMinTop, this.mAttachDetachListeners, this.mNodeRegions, this.mRegionMaxBot, this.mRegionMinTop, this.mWillMountViews);
      }
   }

   final class FindTargetForTouchOperation implements UIViewOperationQueue.UIOperation {

      private final int[] NATIVE_VIEW_BUFFER;
      private final Callback mCallback;
      private final int mReactTag;
      private final float mTargetX;
      private final float mTargetY;


      private FindTargetForTouchOperation(int var2, float var3, float var4, Callback var5) {
         this.NATIVE_VIEW_BUFFER = new int[1];
         this.mReactTag = var2;
         this.mTargetX = var3;
         this.mTargetY = var4;
         this.mCallback = var5;
      }

      // $FF: synthetic method
      FindTargetForTouchOperation(int var2, float var3, float var4, Callback var5, Object var6) {
         this(var2, var3, var4, var5);
      }

      public void execute() {
         try {
            FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, FlatUIViewOperationQueue.MEASURE_BUFFER);
         } catch (IllegalViewOperationException var11) {
            this.mCallback.invoke(new Object[0]);
            return;
         }

         float var2 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[0];
         float var1 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[1];
         View var7 = FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.getView(this.mReactTag);
         int var6 = TouchTargetHelper.findTargetTagForTouch(this.mTargetX, this.mTargetY, (ViewGroup)var7, this.NATIVE_VIEW_BUFFER);

         try {
            FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.NATIVE_VIEW_BUFFER[0], FlatUIViewOperationQueue.MEASURE_BUFFER);
         } catch (IllegalViewOperationException var10) {
            this.mCallback.invoke(new Object[0]);
            return;
         }

         NodeRegion var8 = NodeRegion.EMPTY;
         boolean var5;
         if(this.NATIVE_VIEW_BUFFER[0] == var6) {
            var5 = true;
         } else {
            var5 = false;
         }

         NodeRegion var12 = var8;
         if(!var5) {
            View var9 = FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.getView(this.NATIVE_VIEW_BUFFER[0]);
            var12 = var8;
            if(var9 instanceof FlatViewGroup) {
               var12 = ((FlatViewGroup)var9).getNodeRegionForTag(this.mReactTag);
            }
         }

         if(var12 != NodeRegion.EMPTY) {
            var6 = var12.mTag;
         }

         var2 = PixelUtil.toDIPFromPixel(var12.getLeft() + (float)FlatUIViewOperationQueue.MEASURE_BUFFER[0] - var2);
         float var3 = PixelUtil.toDIPFromPixel(var12.getTop() + (float)FlatUIViewOperationQueue.MEASURE_BUFFER[1] - var1);
         if(var5) {
            var1 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[2];
         } else {
            var1 = var12.getRight() - var12.getLeft();
         }

         float var4 = PixelUtil.toDIPFromPixel(var1);
         if(var5) {
            var1 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[3];
         } else {
            var1 = var12.getBottom() - var12.getTop();
         }

         var1 = PixelUtil.toDIPFromPixel(var1);
         this.mCallback.invoke(new Object[]{Integer.valueOf(var6), Float.valueOf(var2), Float.valueOf(var3), Float.valueOf(var4), Float.valueOf(var1)});
      }
   }

   final class UpdateViewGroup implements UIViewOperationQueue.UIOperation {

      private final int mReactTag;
      private final int[] mViewsToAdd;
      private final int[] mViewsToDetach;


      private UpdateViewGroup(int var2, int[] var3, int[] var4) {
         this.mReactTag = var2;
         this.mViewsToAdd = var3;
         this.mViewsToDetach = var4;
      }

      // $FF: synthetic method
      UpdateViewGroup(int var2, int[] var3, int[] var4, Object var5) {
         this(var2, var3, var4);
      }

      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateViewGroup(this.mReactTag, this.mViewsToAdd, this.mViewsToDetach);
      }
   }

   final class ProcessLayoutRequests implements UIViewOperationQueue.UIOperation {

      private ProcessLayoutRequests() {}

      // $FF: synthetic method
      ProcessLayoutRequests(Object var2) {
         this();
      }

      public void execute() {
         FlatViewGroup.processLayoutRequests();
      }
   }

   final class DropViews implements UIViewOperationQueue.UIOperation {

      private final SparseIntArray mViewsToDrop;


      private DropViews(ArrayList var2, ArrayList var3) {
         SparseIntArray var6 = new SparseIntArray();
         int var5 = var2.size();

         for(int var4 = 0; var4 < var5; ++var4) {
            var6.put(((Integer)var2.get(var4)).intValue(), ((Integer)var3.get(var4)).intValue());
         }

         this.mViewsToDrop = var6;
      }

      // $FF: synthetic method
      DropViews(ArrayList var2, ArrayList var3, Object var4) {
         this(var2, var3);
      }

      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.dropViews(this.mViewsToDrop);
      }
   }

   final class UpdateMountState implements UIViewOperationQueue.UIOperation {

      @Nullable
      private final AttachDetachListener[] mAttachDetachListeners;
      @Nullable
      private final DrawCommand[] mDrawCommands;
      @Nullable
      private final NodeRegion[] mNodeRegions;
      private final int mReactTag;


      private UpdateMountState(int var2, @Nullable DrawCommand[] var3, @Nullable AttachDetachListener[] var4, @Nullable NodeRegion[] var5) {
         this.mReactTag = var2;
         this.mDrawCommands = var3;
         this.mAttachDetachListeners = var4;
         this.mNodeRegions = var5;
      }

      // $FF: synthetic method
      UpdateMountState(int var2, DrawCommand[] var3, AttachDetachListener[] var4, NodeRegion[] var5, Object var6) {
         this(var2, var3, var4, var5);
      }

      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateMountState(this.mReactTag, this.mDrawCommands, this.mAttachDetachListeners, this.mNodeRegions);
      }
   }

   final class MeasureVirtualView implements UIViewOperationQueue.UIOperation {

      private final Callback mCallback;
      private final int mReactTag;
      private final boolean mRelativeToWindow;
      private final float mScaledHeight;
      private final float mScaledWidth;
      private final float mScaledX;
      private final float mScaledY;


      private MeasureVirtualView(int var2, float var3, float var4, float var5, float var6, boolean var7, Callback var8) {
         this.mReactTag = var2;
         this.mScaledX = var3;
         this.mScaledY = var4;
         this.mScaledWidth = var5;
         this.mScaledHeight = var6;
         this.mCallback = var8;
         this.mRelativeToWindow = var7;
      }

      // $FF: synthetic method
      MeasureVirtualView(int var2, float var3, float var4, float var5, float var6, boolean var7, Callback var8, Object var9) {
         this(var2, var3, var4, var5, var6, var7, var8);
      }

      public void execute() {
         try {
            if(this.mRelativeToWindow) {
               FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measureInWindow(this.mReactTag, FlatUIViewOperationQueue.MEASURE_BUFFER);
            } else {
               FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, FlatUIViewOperationQueue.MEASURE_BUFFER);
            }
         } catch (NoSuchNativeViewException var6) {
            this.mCallback.invoke(new Object[0]);
            return;
         }

         float var2 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[0];
         float var4 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[1];
         float var3 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[2];
         float var1 = (float)FlatUIViewOperationQueue.MEASURE_BUFFER[3];
         var2 = PixelUtil.toDIPFromPixel(this.mScaledX * var3 + var2);
         var4 = PixelUtil.toDIPFromPixel(this.mScaledY * var1 + var4);
         var3 = PixelUtil.toDIPFromPixel(this.mScaledWidth * var3);
         var1 = PixelUtil.toDIPFromPixel(this.mScaledHeight * var1);
         if(this.mRelativeToWindow) {
            this.mCallback.invoke(new Object[]{Float.valueOf(var2), Float.valueOf(var4), Float.valueOf(var3), Float.valueOf(var1)});
         } else {
            this.mCallback.invoke(new Object[]{Integer.valueOf(0), Integer.valueOf(0), Float.valueOf(var3), Float.valueOf(var1), Float.valueOf(var2), Float.valueOf(var4)});
         }
      }
   }

   public final class UpdateViewBounds implements UIViewOperationQueue.UIOperation {

      private final int mBottom;
      private final int mLeft;
      private final int mReactTag;
      private final int mRight;
      private final int mTop;


      private UpdateViewBounds(int var2, int var3, int var4, int var5, int var6) {
         this.mReactTag = var2;
         this.mLeft = var3;
         this.mTop = var4;
         this.mRight = var5;
         this.mBottom = var6;
      }

      // $FF: synthetic method
      UpdateViewBounds(int var2, int var3, int var4, int var5, int var6, Object var7) {
         this(var2, var3, var4, var5, var6);
      }

      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateViewBounds(this.mReactTag, this.mLeft, this.mTop, this.mRight, this.mBottom);
      }
   }

   public final class ViewManagerCommand implements UIViewOperationQueue.UIOperation {

      @Nullable
      private final ReadableArray mArgs;
      private final int mCommand;
      private final int mReactTag;


      public ViewManagerCommand(int var2, int var3, @Nullable ReadableArray var4) {
         this.mReactTag = var2;
         this.mCommand = var3;
         this.mArgs = var4;
      }

      public void execute() {
         FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.dispatchCommand(this.mReactTag, this.mCommand, this.mArgs);
      }
   }
}
