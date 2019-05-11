package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.DrawCommandManager;
import com.facebook.react.flat.DrawView;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.flat.ViewResolver;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nullable;

abstract class ClippingDrawCommandManager extends DrawCommandManager {

   private static final String TAG = "ClippingDrawCommandManager";
   private final SparseArray<View> mClippedSubviews;
   protected final Rect mClippingRect;
   private final ArrayList<ReactClippingViewGroup> mClippingViewGroups;
   protected float[] mCommandMaxBottom;
   protected float[] mCommandMinTop;
   private DrawCommand[] mDrawCommands;
   private SparseIntArray mDrawViewIndexMap;
   private final FlatViewGroup mFlatViewGroup;
   private NodeRegion[] mNodeRegions;
   protected float[] mRegionMaxBottom;
   protected float[] mRegionMinTop;
   private int mStart;
   private int mStop;
   private final ArrayList<View> mViewsToKeep;
   private final SparseArray<View> mViewsToRemove;


   ClippingDrawCommandManager(FlatViewGroup var1, DrawCommand[] var2) {
      this.mDrawCommands = DrawCommand.EMPTY_ARRAY;
      this.mCommandMaxBottom = StateBuilder.EMPTY_FLOAT_ARRAY;
      this.mCommandMinTop = StateBuilder.EMPTY_FLOAT_ARRAY;
      this.mNodeRegions = NodeRegion.EMPTY_ARRAY;
      this.mRegionMaxBottom = StateBuilder.EMPTY_FLOAT_ARRAY;
      this.mRegionMinTop = StateBuilder.EMPTY_FLOAT_ARRAY;
      this.mDrawViewIndexMap = StateBuilder.EMPTY_SPARSE_INT;
      this.mClippedSubviews = new SparseArray();
      this.mClippingRect = new Rect();
      this.mViewsToRemove = new SparseArray();
      this.mViewsToKeep = new ArrayList();
      this.mClippingViewGroups = new ArrayList();
      this.mFlatViewGroup = var1;
      this.initialSetup(var2);
   }

   private static boolean animating(View var0) {
      Animation var1 = var0.getAnimation();
      return var1 != null && !var1.hasEnded();
   }

   private void clip(int var1, View var2) {
      this.mClippedSubviews.put(var1, var2);
   }

   private void initialSetup(DrawCommand[] var1) {
      this.mountDrawCommands(var1, this.mDrawViewIndexMap, this.mCommandMaxBottom, this.mCommandMinTop, true);
      this.updateClippingRect();
   }

   private boolean isClipped(int var1) {
      return this.mClippedSubviews.get(var1) != null;
   }

   private boolean isNotClipped(int var1) {
      return this.mClippedSubviews.get(var1) == null;
   }

   private void unclip(int var1) {
      this.mClippedSubviews.remove(var1);
   }

   private void updateClippingRecursively() {
      int var2 = this.mClippingViewGroups.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ReactClippingViewGroup var3 = (ReactClippingViewGroup)this.mClippingViewGroups.get(var1);
         if(this.isNotClipped(((View)var3).getId())) {
            var3.updateClippingRect();
         }
      }

   }

   private void updateClippingToCurrentRect() {
      int var2 = this.mFlatViewGroup.getChildCount();
      int var4 = 0;

      int var1;
      View var9;
      for(var1 = 0; var1 < var2; ++var1) {
         var9 = this.mFlatViewGroup.getChildAt(var1);
         if(!this.withinBounds(this.mDrawViewIndexMap.get(var9.getId())) && !animating(var9)) {
            this.mViewsToRemove.append(var1, var9);
            this.clip(var9.getId(), var9);
         } else {
            this.mViewsToKeep.add(var9);
         }
      }

      var2 = this.mViewsToRemove.size();
      boolean var3;
      if(var2 > 2) {
         var3 = true;
      } else {
         var3 = false;
      }

      var1 = var2;
      if(var3) {
         this.mFlatViewGroup.detachAllViewsFromParent();

         for(var1 = 0; var1 < var2; ++var1) {
            this.mFlatViewGroup.removeDetachedView((View)this.mViewsToRemove.valueAt(var1));
         }
      } else {
         while(true) {
            var2 = var1 - 1;
            if(var1 <= 0) {
               break;
            }

            this.mFlatViewGroup.removeViewsInLayout(this.mViewsToRemove.keyAt(var2), 1);
            var1 = var2;
         }
      }

      this.mViewsToRemove.clear();
      var2 = this.mStart;
      int var7 = this.mViewsToKeep.size();

      int var5;
      for(var1 = 0; var4 < var7; var2 = var5) {
         var9 = (View)this.mViewsToKeep.get(var4);
         int var8 = this.mDrawViewIndexMap.get(var9.getId());
         var5 = var2;
         int var6 = var1;
         if(var2 <= var8) {
            while(var2 != var8) {
               var5 = var1;
               if(this.mDrawCommands[var2] instanceof DrawView) {
                  DrawView var10 = (DrawView)this.mDrawCommands[var2];
                  this.mFlatViewGroup.addViewInLayout((View)Assertions.assumeNotNull(this.mClippedSubviews.get(var10.reactTag)), var1);
                  this.unclip(var10.reactTag);
                  var5 = var1 + 1;
               }

               ++var2;
               var1 = var5;
            }

            var5 = var2 + 1;
            var6 = var1;
         }

         if(var3) {
            this.mFlatViewGroup.attachViewToParent(var9, var6);
         }

         var1 = var6 + 1;
         ++var4;
      }

      this.mViewsToKeep.clear();

      while(var2 < this.mStop) {
         int var11 = var1;
         if(this.mDrawCommands[var2] instanceof DrawView) {
            DrawView var12 = (DrawView)this.mDrawCommands[var2];
            this.mFlatViewGroup.addViewInLayout((View)Assertions.assumeNotNull(this.mClippedSubviews.get(var12.reactTag)), var1);
            this.unclip(var12.reactTag);
            var11 = var1 + 1;
         }

         ++var2;
         var1 = var11;
      }

   }

   private boolean withinBounds(int var1) {
      return this.mStart <= var1 && var1 < this.mStop;
   }

   @Nullable
   public NodeRegion anyNodeRegionWithinBounds(float var1, float var2) {
      int var3 = this.regionStopIndex(var1, var2);

      while(true) {
         int var4 = var3 - 1;
         if(var3 <= 0) {
            break;
         }

         NodeRegion var5 = this.mNodeRegions[var4];
         if(this.regionAboveTouch(var4, var1, var2)) {
            break;
         }

         if(var5.withinBounds(var1, var2)) {
            return var5;
         }

         var3 = var4;
      }

      return null;
   }

   abstract int commandStartIndex();

   abstract int commandStopIndex(int var1);

   void debugDraw(Canvas var1) {
      DrawCommand[] var4 = this.mDrawCommands;
      int var3 = var4.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         DrawCommand var5 = var4[var2];
         if(var5 instanceof DrawView) {
            if(this.isNotClipped(((DrawView)var5).reactTag)) {
               var5.debugDraw(this.mFlatViewGroup, var1);
            }
         } else {
            var5.debugDraw(this.mFlatViewGroup, var1);
         }
      }

   }

   public void draw(Canvas var1) {
      int var2 = this.mStart;
      int var5 = this.mFlatViewGroup.getChildCount();
      int var4 = 0;

      while(true) {
         int var3 = var2;
         if(var4 >= var5) {
            for(; var3 < this.mStop; var3 = var2) {
               DrawCommand[] var7 = this.mDrawCommands;
               var2 = var3 + 1;
               DrawCommand var9 = var7[var3];
               if(var9 instanceof DrawView) {
                  String var10 = TAG;
                  StringBuilder var8 = new StringBuilder();
                  var8.append("Unexpected DrawView command at index ");
                  var8.append(var2 - 1);
                  var8.append(" with mStop=");
                  var8.append(this.mStop);
                  var8.append(". ");
                  var8.append(Arrays.toString(this.mDrawCommands));
                  FLog.w(var10, var8.toString());
               } else {
                  var9.draw(this.mFlatViewGroup, var1);
               }
            }

            return;
         }

         int var6 = this.mDrawViewIndexMap.get(this.mFlatViewGroup.getChildAt(var4).getId());
         if(this.mStop < var6) {
            while(true) {
               var3 = var2;
               if(var2 >= this.mStop) {
                  break;
               }

               this.mDrawCommands[var2].draw(this.mFlatViewGroup, var1);
               ++var2;
            }
         } else {
            var3 = var2;
            if(var2 <= var6) {
               while(var2 < var6) {
                  this.mDrawCommands[var2].draw(this.mFlatViewGroup, var1);
                  ++var2;
               }

               var3 = var2 + 1;
            }
         }

         this.mDrawCommands[var6].draw(this.mFlatViewGroup, var1);
         ++var4;
         var2 = var3;
      }
   }

   public void getClippingRect(Rect var1) {
      var1.set(this.mClippingRect);
   }

   public SparseArray<View> getDetachedViews() {
      return this.mClippedSubviews;
   }

   public void mountDrawCommands(DrawCommand[] var1, SparseIntArray var2, float[] var3, float[] var4, boolean var5) {
      this.mDrawCommands = var1;
      this.mCommandMaxBottom = var3;
      this.mCommandMinTop = var4;
      this.mDrawViewIndexMap = var2;
      if(this.mClippingRect.bottom != this.mClippingRect.top) {
         this.mStart = this.commandStartIndex();
         this.mStop = this.commandStopIndex(this.mStart);
         if(!var5) {
            this.updateClippingToCurrentRect();
         }
      }

   }

   public void mountNodeRegions(NodeRegion[] var1, float[] var2, float[] var3) {
      this.mNodeRegions = var1;
      this.mRegionMaxBottom = var2;
      this.mRegionMinTop = var3;
   }

   public void mountViews(ViewResolver var1, int[] var2, int[] var3) {
      this.mClippingViewGroups.clear();
      int var9 = var2.length;
      byte var7 = 0;

      int var4;
      int var6;
      for(var4 = 0; var4 < var9; ++var4) {
         int var8 = var2[var4];
         boolean var5;
         if(var8 > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         var6 = var8;
         if(!var5) {
            var6 = -var8;
         }

         var6 = this.mDrawViewIndexMap.get(var6);
         DrawView var10 = (DrawView)this.mDrawCommands[var6];
         View var11 = var1.getView(var10.reactTag);
         ensureViewHasNoParent(var11);
         if(var11 instanceof ReactClippingViewGroup) {
            ReactClippingViewGroup var12 = (ReactClippingViewGroup)var11;
            if(var12.getRemoveClippedSubviews()) {
               this.mClippingViewGroups.add(var12);
            }
         }

         if(var5) {
            var10.mWasMounted = true;
            if(!animating(var11) && !this.withinBounds(var6)) {
               this.clip(var10.reactTag, var11);
            } else {
               this.mFlatViewGroup.addViewInLayout(var11);
            }
         } else if(var10.mWasMounted) {
            if(this.isNotClipped(var10.reactTag)) {
               this.mFlatViewGroup.attachViewToParent(var11);
            }
         } else {
            var10.mWasMounted = true;
            if(!animating(var11) && !this.withinBounds(var6)) {
               if(this.isNotClipped(var10.reactTag)) {
                  this.mFlatViewGroup.removeDetachedView(var11);
                  this.clip(var10.reactTag, var11);
               }
            } else if(this.isClipped(var10.reactTag)) {
               this.mFlatViewGroup.addViewInLayout(var11);
               this.unclip(var10.reactTag);
            } else {
               this.mFlatViewGroup.attachViewToParent(var11);
            }
         }
      }

      int var14 = var3.length;

      for(var4 = var7; var4 < var14; ++var4) {
         var6 = var3[var4];
         View var13 = var1.getView(var6);
         if(var13.getParent() != null) {
            throw new RuntimeException("Trying to remove view not owned by FlatViewGroup");
         }

         this.mFlatViewGroup.removeDetachedView(var13);
         this.unclip(var6);
      }

   }

   void onClippedViewDropped(View var1) {
      this.unclip(var1.getId());
      this.mFlatViewGroup.removeDetachedView(var1);
   }

   abstract boolean regionAboveTouch(int var1, float var2, float var3);

   abstract int regionStopIndex(float var1, float var2);

   public boolean updateClippingRect() {
      ReactClippingViewGroupHelper.calculateClippingRect(this.mFlatViewGroup, this.mClippingRect);
      if(this.mFlatViewGroup.getParent() != null) {
         if(this.mClippingRect.top == this.mClippingRect.bottom) {
            return false;
         } else {
            int var1 = this.commandStartIndex();
            int var2 = this.commandStopIndex(var1);
            if(this.mStart <= var1 && var2 <= this.mStop) {
               this.updateClippingRecursively();
               return false;
            } else {
               this.mStart = var1;
               this.mStop = var2;
               this.updateClippingToCurrentRect();
               this.updateClippingRecursively();
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Nullable
   public NodeRegion virtualNodeRegionWithinBounds(float var1, float var2) {
      int var3 = this.regionStopIndex(var1, var2);

      while(true) {
         int var4 = var3 - 1;
         if(var3 <= 0) {
            break;
         }

         NodeRegion var5 = this.mNodeRegions[var4];
         if(var5.mIsVirtual) {
            if(this.regionAboveTouch(var4, var1, var2)) {
               break;
            }

            if(var5.withinBounds(var1, var2)) {
               return var5;
            }
         }

         var3 = var4;
      }

      return null;
   }
}
