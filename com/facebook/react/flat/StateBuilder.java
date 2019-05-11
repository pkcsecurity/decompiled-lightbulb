package com.facebook.react.flat;

import android.util.SparseIntArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.AbstractDrawCommand;
import com.facebook.react.flat.AndroidView;
import com.facebook.react.flat.AttachDetachListener;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.ElementsList;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.flat.FlatUIViewOperationQueue;
import com.facebook.react.flat.HorizontalDrawCommandManager;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.VerticalDrawCommandManager;
import com.facebook.react.uimanager.OnLayoutEvent;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.ArrayList;
import javax.annotation.Nullable;

final class StateBuilder {

   static final float[] EMPTY_FLOAT_ARRAY = new float[0];
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   static final SparseIntArray EMPTY_SPARSE_INT = new SparseIntArray();
   private static final boolean SKIP_UP_TO_DATE_NODES = true;
   private final ElementsList<AttachDetachListener> mAttachDetachListeners;
   @Nullable
   private FlatUIViewOperationQueue.DetachAllChildrenFromViews mDetachAllChildrenFromViews;
   private final ElementsList<DrawCommand> mDrawCommands;
   private final ElementsList<FlatShadowNode> mNativeChildren;
   private final ElementsList<NodeRegion> mNodeRegions;
   private final ArrayList<OnLayoutEvent> mOnLayoutEvents;
   private final FlatUIViewOperationQueue mOperationsQueue;
   private final ArrayList<Integer> mParentsForViewsToDrop;
   private final ArrayList<UIViewOperationQueue.UIOperation> mUpdateViewBoundsOperations;
   private final ArrayList<UIViewOperationQueue.UIOperation> mViewManagerCommands;
   private final ArrayList<FlatShadowNode> mViewsToDetach;
   private final ArrayList<FlatShadowNode> mViewsToDetachAllChildrenFrom;
   private final ArrayList<Integer> mViewsToDrop;


   StateBuilder(FlatUIViewOperationQueue var1) {
      this.mDrawCommands = new ElementsList(DrawCommand.EMPTY_ARRAY);
      this.mAttachDetachListeners = new ElementsList(AttachDetachListener.EMPTY_ARRAY);
      this.mNodeRegions = new ElementsList(NodeRegion.EMPTY_ARRAY);
      this.mNativeChildren = new ElementsList(FlatShadowNode.EMPTY_ARRAY);
      this.mViewsToDetachAllChildrenFrom = new ArrayList();
      this.mViewsToDetach = new ArrayList();
      this.mViewsToDrop = new ArrayList();
      this.mParentsForViewsToDrop = new ArrayList();
      this.mOnLayoutEvents = new ArrayList();
      this.mUpdateViewBoundsOperations = new ArrayList();
      this.mViewManagerCommands = new ArrayList();
      this.mOperationsQueue = var1;
   }

   private void addNativeChild(FlatShadowNode var1) {
      this.mNativeChildren.add(var1);
   }

   private void addNodeRegion(FlatShadowNode var1, float var2, float var3, float var4, float var5, boolean var6) {
      if(var2 != var4) {
         if(var3 != var5) {
            var1.updateNodeRegion(var2, var3, var4, var5, var6);
            if(var1.doesDraw()) {
               this.mNodeRegions.add(var1.getNodeRegion());
            }

         }
      }
   }

   private boolean collectStateForMountableNode(FlatShadowNode var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      boolean var13 = var1.hasNewLayout();
      boolean var15 = true;
      boolean var11;
      if(!var13 && !var1.isUpdated() && !var1.hasUnseenUpdates() && !var1.clipBoundsChanged(var6, var7, var8, var9)) {
         var11 = false;
      } else {
         var11 = true;
      }

      if(!var11) {
         return false;
      } else {
         var1.setClipBounds(var6, var7, var8, var9);
         this.mDrawCommands.start(var1.getDrawCommands());
         this.mAttachDetachListeners.start(var1.getAttachDetachListeners());
         this.mNodeRegions.start(var1.getNodeRegions());
         this.mNativeChildren.start(var1.getNativeChildren());
         boolean var14;
         if(var1 instanceof AndroidView) {
            AndroidView var16 = (AndroidView)var1;
            this.updateViewPadding(var16, var1.getReactTag());
            var14 = var16.needsCustomLayoutForChildren();
            var6 = Float.NEGATIVE_INFINITY;
            var7 = Float.NEGATIVE_INFINITY;
            var8 = Float.POSITIVE_INFINITY;
            var13 = true;
            var9 = Float.POSITIVE_INFINITY;
         } else {
            var13 = false;
            var14 = false;
         }

         if(!var13 && var1.isVirtualAnchor()) {
            this.addNodeRegion(var1, var2, var3, var4, var5, true);
         }

         var14 = this.collectStateRecursively(var1, var2, var3, var4, var5, var6, var7, var8, var9, var13, var14);
         DrawCommand[] var21 = (DrawCommand[])this.mDrawCommands.finish();
         boolean var10;
         if(var21 != null) {
            var1.setDrawCommands(var21);
            var10 = true;
         } else {
            var10 = false;
         }

         AttachDetachListener[] var22 = (AttachDetachListener[])this.mAttachDetachListeners.finish();
         if(var22 != null) {
            var1.setAttachDetachListeners(var22);
            var10 = true;
         }

         NodeRegion[] var23 = (NodeRegion[])this.mNodeRegions.finish();
         boolean var12;
         if(var23 != null) {
            var1.setNodeRegions(var23);
            var12 = true;
         } else {
            var12 = var10;
            if(var14) {
               var1.updateOverflowsContainer();
               var12 = var10;
            }
         }

         FlatShadowNode[] var24 = (FlatShadowNode[])this.mNativeChildren.finish();
         if(var12) {
            if(var1.clipsSubviews()) {
               float[] var17 = EMPTY_FLOAT_ARRAY;
               float[] var18 = EMPTY_FLOAT_ARRAY;
               SparseIntArray var25 = EMPTY_SPARSE_INT;
               if(var21 != null) {
                  var25 = new SparseIntArray();
                  var17 = new float[var21.length];
                  var18 = new float[var21.length];
                  if(var1.isHorizontal()) {
                     HorizontalDrawCommandManager.fillMaxMinArrays(var21, var17, var18, var25);
                  } else {
                     VerticalDrawCommandManager.fillMaxMinArrays(var21, var17, var18, var25);
                  }
               }

               float[] var19 = EMPTY_FLOAT_ARRAY;
               float[] var20 = EMPTY_FLOAT_ARRAY;
               if(var23 != null) {
                  var19 = new float[var23.length];
                  var20 = new float[var23.length];
                  if(var1.isHorizontal()) {
                     HorizontalDrawCommandManager.fillMaxMinArrays(var23, var19, var20);
                  } else {
                     VerticalDrawCommandManager.fillMaxMinArrays(var23, var19, var20);
                  }
               }

               if(var24 != null) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               this.mOperationsQueue.enqueueUpdateClippingMountState(var1.getReactTag(), var21, var25, var17, var18, var22, var23, var19, var20, var13);
            } else {
               this.mOperationsQueue.enqueueUpdateMountState(var1.getReactTag(), var21, var22, var23);
            }
         }

         if(var1.hasUnseenUpdates()) {
            var1.onCollectExtraUpdates(this.mOperationsQueue);
            var1.markUpdateSeen();
         }

         if(var24 != null) {
            this.updateNativeChildren(var1, var1.getNativeChildren(), var24);
         }

         var13 = var15;
         if(!var12) {
            var13 = var15;
            if(var24 == null) {
               if(var14) {
                  var13 = var15;
               } else {
                  var13 = false;
               }
            }
         }

         if(!var11 && var13) {
            StringBuilder var26 = new StringBuilder();
            var26.append("Node ");
            var26.append(var1.getReactTag());
            var26.append(" updated unexpectedly.");
            throw new RuntimeException(var26.toString());
         } else {
            return var13;
         }
      }
   }

   private boolean collectStateRecursively(FlatShadowNode var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, boolean var10, boolean var11) {
      if(var1.hasNewLayout()) {
         var1.markLayoutSeen();
      }

      float var12 = roundToPixel(var2);
      float var13 = roundToPixel(var3);
      float var14 = roundToPixel(var4);
      float var15 = roundToPixel(var5);
      if(var1.shouldNotifyOnLayout()) {
         OnLayoutEvent var19 = var1.obtainLayoutEvent(Math.round(var1.getLayoutX()), Math.round(var1.getLayoutY()), (int)(var14 - var12), (int)(var15 - var13));
         if(var19 != null) {
            this.mOnLayoutEvents.add(var19);
         }
      }

      if(var1.clipToBounds()) {
         var6 = Math.max(var2, var6);
         var7 = Math.max(var3, var7);
         var8 = Math.min(var4, var8);
         var9 = Math.min(var5, var9);
         var5 = var6;
         var4 = var7;
         var6 = var8;
         var7 = var9;
      } else {
         var5 = var6;
         var4 = var7;
         var6 = var8;
         var7 = var9;
      }

      var1.collectState(this, var12, var13, var14, var15, roundToPixel(var5), roundToPixel(var4), roundToPixel(var6), var7);
      int var17 = var1.getChildCount();
      int var16 = 0;

      boolean var18;
      for(var18 = false; var16 != var17; ++var16) {
         ReactShadowNodeImpl var20 = var1.getChildAt(var16);
         if(!var20.isVirtual()) {
            var18 |= this.processNodeAndCollectState((FlatShadowNode)var20, var2, var3, var5, var4, var6, var7, var10, var11);
         }
      }

      var1.resetUpdated();
      return var18;
   }

   private static int[] collectViewTags(ArrayList<FlatShadowNode> var0) {
      int var2 = var0.size();
      if(var2 == 0) {
         return EMPTY_INT_ARRAY;
      } else {
         int[] var3 = new int[var2];

         for(int var1 = 0; var1 < var2; ++var1) {
            var3[var1] = ((FlatShadowNode)var0.get(var1)).getReactTag();
         }

         return var3;
      }
   }

   private boolean processNodeAndCollectState(FlatShadowNode var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, boolean var9) {
      float var11 = var1.getLayoutWidth();
      float var10 = var1.getLayoutHeight();
      var2 += var1.getLayoutX();
      var3 += var1.getLayoutY();
      var11 += var2;
      var10 += var3;
      boolean var12 = var1.mountsToView();
      if(!var8) {
         this.addNodeRegion(var1, var2, var3, var11, var10, var12 ^ true);
      }

      if(var12) {
         this.ensureBackingViewIsCreated(var1);
         this.addNativeChild(var1);
         var12 = this.collectStateForMountableNode(var1, 0.0F, 0.0F, var11 - var2, var10 - var3, var4 - var2, var5 - var3, var6 - var2, var7 - var3);
         if(!var8) {
            this.mDrawCommands.add(var1.collectDrawView(var2, var3, var11, var10, var4, var5, var6, var7));
         }

         var8 = var12;
         if(!var9) {
            this.updateViewBounds(var1, var2, var3, var11, var10);
            return var12;
         }
      } else {
         var8 = this.collectStateRecursively(var1, var2, var3, var11, var10, var4, var5, var6, var7, false, false);
      }

      return var8;
   }

   private static float roundToPixel(float var0) {
      return (float)Math.floor((double)(var0 + 0.5F));
   }

   private void updateNativeChildren(FlatShadowNode var1, FlatShadowNode[] var2, FlatShadowNode[] var3) {
      var1.setNativeChildren(var3);
      if(this.mDetachAllChildrenFromViews == null) {
         this.mDetachAllChildrenFromViews = this.mOperationsQueue.enqueueDetachAllChildrenFromViews();
      }

      if(var2.length != 0) {
         this.mViewsToDetachAllChildrenFrom.add(var1);
      }

      int var7 = var1.getReactTag();
      int var4 = var3.length;
      byte var6 = 0;
      int var5;
      int[] var10;
      if(var4 == 0) {
         var10 = EMPTY_INT_ARRAY;
      } else {
         int[] var9 = new int[var4];
         int var8 = var3.length;
         var4 = 0;
         var5 = 0;

         while(true) {
            var10 = var9;
            if(var4 >= var8) {
               break;
            }

            var1 = var3[var4];
            if(var1.getNativeParentTag() == var7) {
               var9[var5] = -var1.getReactTag();
            } else {
               var9[var5] = var1.getReactTag();
            }

            var1.setNativeParentTag(-1);
            ++var5;
            ++var4;
         }
      }

      var5 = var2.length;

      for(var4 = 0; var4 < var5; ++var4) {
         FlatShadowNode var12 = var2[var4];
         if(var12.getNativeParentTag() == var7) {
            this.mViewsToDetach.add(var12);
            var12.setNativeParentTag(-1);
         }
      }

      int[] var11 = collectViewTags(this.mViewsToDetach);
      this.mViewsToDetach.clear();
      var5 = var3.length;

      for(var4 = var6; var4 < var5; ++var4) {
         var3[var4].setNativeParentTag(var7);
      }

      this.mOperationsQueue.enqueueUpdateViewGroup(var7, var10, var11);
   }

   private void updateViewBounds(FlatShadowNode var1, float var2, float var3, float var4, float var5) {
      int var6 = Math.round(var2);
      int var7 = Math.round(var3);
      int var8 = Math.round(var4);
      int var9 = Math.round(var5);
      if(var1.getViewLeft() != var6 || var1.getViewTop() != var7 || var1.getViewRight() != var8 || var1.getViewBottom() != var9) {
         var1.setViewBounds(var6, var7, var8, var9);
         int var10 = var1.getReactTag();
         this.mUpdateViewBoundsOperations.add(this.mOperationsQueue.createUpdateViewBounds(var10, var6, var7, var8, var9));
      }
   }

   private void updateViewPadding(AndroidView var1, int var2) {
      if(var1.isPaddingChanged()) {
         this.mOperationsQueue.enqueueSetPadding(var2, Math.round(var1.getPadding(0)), Math.round(var1.getPadding(1)), Math.round(var1.getPadding(2)), Math.round(var1.getPadding(3)));
         var1.resetPaddingChanged();
      }

   }

   void addAttachDetachListener(AttachDetachListener var1) {
      this.mAttachDetachListeners.add(var1);
   }

   void addDrawCommand(AbstractDrawCommand var1) {
      this.mDrawCommands.add(var1);
   }

   void afterUpdateViewHierarchy(EventDispatcher var1) {
      if(this.mDetachAllChildrenFromViews != null) {
         int[] var5 = collectViewTags(this.mViewsToDetachAllChildrenFrom);
         this.mViewsToDetachAllChildrenFrom.clear();
         this.mDetachAllChildrenFromViews.setViewsToDetachAllChildrenFrom(var5);
         this.mDetachAllChildrenFromViews = null;
      }

      int var4 = this.mUpdateViewBoundsOperations.size();
      byte var3 = 0;

      int var2;
      for(var2 = 0; var2 != var4; ++var2) {
         this.mOperationsQueue.enqueueFlatUIOperation((UIViewOperationQueue.UIOperation)this.mUpdateViewBoundsOperations.get(var2));
      }

      this.mUpdateViewBoundsOperations.clear();
      var4 = this.mViewManagerCommands.size();

      for(var2 = 0; var2 != var4; ++var2) {
         this.mOperationsQueue.enqueueFlatUIOperation((UIViewOperationQueue.UIOperation)this.mViewManagerCommands.get(var2));
      }

      this.mViewManagerCommands.clear();
      var4 = this.mOnLayoutEvents.size();

      for(var2 = var3; var2 != var4; ++var2) {
         var1.dispatchEvent((Event)this.mOnLayoutEvents.get(var2));
      }

      this.mOnLayoutEvents.clear();
      if(this.mViewsToDrop.size() > 0) {
         this.mOperationsQueue.enqueueDropViews(this.mViewsToDrop, this.mParentsForViewsToDrop);
         this.mViewsToDrop.clear();
         this.mParentsForViewsToDrop.clear();
      }

      this.mOperationsQueue.enqueueProcessLayoutRequests();
   }

   void applyUpdates(FlatShadowNode var1) {
      float var5 = var1.getLayoutWidth();
      float var4 = var1.getLayoutHeight();
      float var2 = var1.getLayoutX();
      float var3 = var1.getLayoutY();
      var5 += var2;
      var4 += var3;
      this.collectStateForMountableNode(var1, var2, var3, var5, var4, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
      this.updateViewBounds(var1, var2, var3, var5, var4);
   }

   void dropView(FlatShadowNode var1, int var2) {
      this.mViewsToDrop.add(Integer.valueOf(var1.getReactTag()));
      this.mParentsForViewsToDrop.add(Integer.valueOf(var2));
   }

   void enqueueCreateOrUpdateView(FlatShadowNode var1, @Nullable ReactStylesDiffMap var2) {
      if(var1.isBackingViewCreated()) {
         this.mOperationsQueue.enqueueUpdateProperties(var1.getReactTag(), var1.getViewClass(), var2);
      } else {
         this.mOperationsQueue.enqueueCreateView(var1.getThemedContext(), var1.getReactTag(), var1.getViewClass(), var2);
         var1.signalBackingViewIsCreated();
      }
   }

   void enqueueViewManagerCommand(int var1, int var2, ReadableArray var3) {
      this.mViewManagerCommands.add(this.mOperationsQueue.createViewManagerCommand(var1, var2, var3));
   }

   void ensureBackingViewIsCreated(FlatShadowNode var1) {
      if(!var1.isBackingViewCreated()) {
         int var2 = var1.getReactTag();
         this.mOperationsQueue.enqueueCreateView(var1.getThemedContext(), var2, var1.getViewClass(), (ReactStylesDiffMap)null);
         var1.signalBackingViewIsCreated();
      }
   }

   FlatUIViewOperationQueue getOperationsQueue() {
      return this.mOperationsQueue;
   }

   void removeRootView(int var1) {
      this.mViewsToDrop.add(Integer.valueOf(-var1));
      this.mParentsForViewsToDrop.add(Integer.valueOf(-1));
   }
}
