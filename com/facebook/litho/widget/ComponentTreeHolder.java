package com.facebook.litho.widget;

import android.support.v4.util.Pools;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.LayoutHandler;
import com.facebook.litho.Size;
import com.facebook.litho.StateHandler;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ComponentTreeHolder {

   static final int RENDER_ADDED = 1;
   static final int RENDER_DRAWN = 2;
   static final int RENDER_UNINITIALIZED = 0;
   private static final int UNINITIALIZED = -1;
   private static final Pools.SynchronizedPool<ComponentTreeHolder> sComponentTreeHoldersPool = new Pools.SynchronizedPool(8);
   private static final AtomicInteger sIdGenerator = new AtomicInteger(1);
   private boolean mCanPreallocateOnDefaultHandler;
   @Nullable
   @GuardedBy
   private ComponentTree mComponentTree;
   private ComponentTreeHolder.ComponentTreeMeasureListenerFactory mComponentTreeMeasureListenerFactory;
   private boolean mHasMounted = false;
   private int mId;
   private boolean mIsInserted = true;
   private final AtomicBoolean mIsReleased = new AtomicBoolean(false);
   private boolean mIsTreeValid;
   @GuardedBy
   private int mLastMeasuredHeight;
   @GuardedBy
   private int mLastRequestedHeightSpec = -1;
   @GuardedBy
   private int mLastRequestedWidthSpec = -1;
   @Nullable
   private LayoutHandler mLayoutHandler;
   @Nullable
   @GuardedBy
   private ComponentTree.NewLayoutStateReadyListener mPendingNewLayoutListener;
   private LayoutHandler mPreallocateMountContentHandler;
   @GuardedBy
   private RenderInfo mRenderInfo;
   private final AtomicInteger mRenderState = new AtomicInteger(0);
   private boolean mShouldPreallocatePerMountSpec;
   private String mSplitLayoutTag;
   @GuardedBy
   private StateHandler mStateHandler;
   private boolean mUseSharedLayoutStateFuture;


   @GuardedBy
   private void acquireAnimationState() {
      if(this.mComponentTree != null) {
         this.mHasMounted = this.mComponentTree.hasMounted();
      }
   }

   private void acquireId() {
      this.mId = sIdGenerator.getAndIncrement();
   }

   @GuardedBy
   private void acquireStateHandler() {
      if(this.mComponentTree != null) {
         this.mStateHandler = this.mComponentTree.acquireStateHandler();
      }
   }

   public static ComponentTreeHolder.Builder create() {
      return new ComponentTreeHolder.Builder(null);
   }

   @GuardedBy
   private void ensureComponentTree(ComponentContext var1) {
      if(this.mComponentTree == null) {
         Object var3 = this.mRenderInfo.getCustomAttribute("clip_children");
         boolean var2;
         if(var3 == null) {
            var2 = true;
         } else {
            var2 = ((Boolean)var3).booleanValue();
         }

         var3 = this.mRenderInfo.getCustomAttribute("layout_diffing_enabled");
         ComponentTree.Builder var4 = ComponentTree.create(var1, this.mRenderInfo.getComponent());
         if(var3 != null) {
            var4.layoutDiffing(((Boolean)var3).booleanValue());
         }

         ComponentTree.Builder var6 = var4.layoutThreadHandler(this.mLayoutHandler).stateHandler(this.mStateHandler).shouldClipChildren(var2).preAllocateMountContentHandler(this.mPreallocateMountContentHandler).preallocateOnDefaultHandler(this.mCanPreallocateOnDefaultHandler).shouldPreallocateMountContentPerMountSpec(this.mShouldPreallocatePerMountSpec).useSharedLayoutStateFuture(this.mUseSharedLayoutStateFuture);
         ComponentTree.MeasureListener var5;
         if(this.mComponentTreeMeasureListenerFactory == null) {
            var5 = null;
         } else {
            var5 = this.mComponentTreeMeasureListenerFactory.create(this);
         }

         this.mComponentTree = var6.measureListener(var5).splitLayoutTag(this.mSplitLayoutTag).hasMounted(this.mHasMounted).build();
         if(this.mPendingNewLayoutListener != null) {
            this.mComponentTree.setNewLayoutStateReadyListener(this.mPendingNewLayoutListener);
         }
      }

   }

   @GuardedBy
   private void releaseTree() {
      if(this.mComponentTree != null) {
         this.mComponentTree.release();
         this.mComponentTree = null;
      }

      this.mIsTreeValid = false;
   }

   public void acquireStateAndReleaseTree() {
      synchronized(this){}

      try {
         this.acquireStateHandler();
         this.acquireAnimationState();
         this.releaseTree();
      } finally {
         ;
      }

   }

   void checkWorkingRangeAndDispatch(int var1, int var2, int var3, int var4, int var5) {
      synchronized(this){}

      try {
         if(this.mComponentTree != null) {
            this.mComponentTree.checkWorkingRangeAndDispatch(var1, var2, var3, var4, var5);
         }
      } finally {
         ;
      }

   }

   void clearStateHandler() {
      synchronized(this){}

      try {
         this.mStateHandler = null;
      } finally {
         ;
      }

   }

   public void computeLayoutAsync(ComponentContext param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public void computeLayoutSync(ComponentContext param1, int param2, int param3, Size param4) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public ComponentTree getComponentTree() {
      synchronized(this){}

      ComponentTree var1;
      try {
         var1 = this.mComponentTree;
      } finally {
         ;
      }

      return var1;
   }

   int getId() {
      return this.mId;
   }

   int getMeasuredHeight() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.mLastMeasuredHeight;
      } finally {
         ;
      }

      return var1;
   }

   public RenderInfo getRenderInfo() {
      synchronized(this){}

      RenderInfo var1;
      try {
         var1 = this.mRenderInfo;
      } finally {
         ;
      }

      return var1;
   }

   int getRenderState() {
      return this.mRenderState.get();
   }

   public boolean hasCompletedLatestLayout() {
      synchronized(this){}
      boolean var4 = false;

      boolean var1;
      label67: {
         label56: {
            try {
               var4 = true;
               if(this.mRenderInfo.rendersView()) {
                  var4 = false;
                  break label56;
               }

               if(this.mComponentTree == null) {
                  var4 = false;
                  break label67;
               }

               var1 = this.mComponentTree.hasCompatibleLayout(this.mLastRequestedWidthSpec, this.mLastRequestedHeightSpec);
               var4 = false;
            } finally {
               if(var4) {
                  ;
               }
            }

            if(!var1) {
               break label67;
            }
         }

         var1 = true;
         return var1;
      }

      var1 = false;
      return var1;
   }

   void invalidateTree() {
      synchronized(this){}

      try {
         this.mIsTreeValid = false;
      } finally {
         ;
      }

   }

   public boolean isInserted() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.mIsInserted;
      } finally {
         ;
      }

      return var1;
   }

   public boolean isReleased() {
      return this.mIsReleased.get();
   }

   public boolean isTreeValid() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.mIsTreeValid;
      } finally {
         ;
      }

      return var1;
   }

   public boolean isTreeValidForSizeSpecs(int var1, int var2) {
      synchronized(this){}
      boolean var6 = false;

      boolean var3;
      label53: {
         try {
            var6 = true;
            if(!this.isTreeValid()) {
               var6 = false;
               break label53;
            }

            if(this.mLastRequestedWidthSpec != var1) {
               var6 = false;
               break label53;
            }

            var1 = this.mLastRequestedHeightSpec;
            var6 = false;
         } finally {
            if(var6) {
               ;
            }
         }

         if(var1 == var2) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   public void release() {
      synchronized(this){}

      try {
         this.releaseTree();
         this.clearStateHandler();
         this.mRenderInfo = null;
         this.mLayoutHandler = null;
         this.mPreallocateMountContentHandler = null;
         this.mShouldPreallocatePerMountSpec = false;
         this.mCanPreallocateOnDefaultHandler = false;
         this.mUseSharedLayoutStateFuture = false;
         this.mPendingNewLayoutListener = null;
         this.mLastRequestedWidthSpec = -1;
         this.mLastRequestedHeightSpec = -1;
         this.mIsInserted = true;
         this.mHasMounted = false;
         this.mRenderState.set(0);
         if(this.mIsReleased.getAndSet(true)) {
            throw new RuntimeException("Releasing already released ComponentTreeHolder!");
         }

         sComponentTreeHoldersPool.release(this);
      } finally {
         ;
      }

   }

   public void setInserted(boolean var1) {
      synchronized(this){}

      try {
         this.mIsInserted = var1;
      } finally {
         ;
      }

   }

   void setMeasuredHeight(int var1) {
      synchronized(this){}

      try {
         this.mLastMeasuredHeight = var1;
      } finally {
         ;
      }

   }

   void setNewLayoutReadyListener(@Nullable ComponentTree.NewLayoutStateReadyListener var1) {
      synchronized(this){}

      try {
         if(this.mComponentTree != null) {
            this.mComponentTree.setNewLayoutStateReadyListener(var1);
         } else {
            this.mPendingNewLayoutListener = var1;
         }
      } finally {
         ;
      }

   }

   public void setRenderInfo(RenderInfo var1) {
      synchronized(this){}

      try {
         this.invalidateTree();
         this.mRenderInfo = var1;
      } finally {
         ;
      }

   }

   void setRenderState(@ComponentTreeHolder.RenderState int var1) {
      this.mRenderState.set(var1);
   }

   public void updateLayoutHandler(@Nullable LayoutHandler var1) {
      synchronized(this){}

      try {
         this.mLayoutHandler = var1;
         if(this.mComponentTree != null) {
            this.mComponentTree.updateLayoutThreadHandler(var1);
         }
      } finally {
         ;
      }

   }

   public void updateMeasureListener(@Nullable ComponentTree.MeasureListener var1) {
      synchronized(this){}

      try {
         if(this.mComponentTree != null) {
            this.mComponentTree.updateMeasureListener(var1);
         }
      } finally {
         ;
      }

   }

   public @interface RenderState {
   }

   interface ComponentTreeMeasureListenerFactory {

      @Nullable
      ComponentTree.MeasureListener create(ComponentTreeHolder var1);
   }

   public static class Builder {

      private boolean canCacheDrawingDisplayLists;
      private boolean canPreallocateOnDefaultHandler;
      private boolean canPrefetchDisplayLists;
      private ComponentTreeHolder.ComponentTreeMeasureListenerFactory componentTreeMeasureListenerFactory;
      private LayoutHandler layoutHandler;
      @Nullable
      private LayoutHandler preallocateMountContentHandler;
      private RenderInfo renderInfo;
      private boolean shouldPreallocatePerMountSpec;
      private String splitLayoutTag;
      private boolean useSharedLayoutStateFuture;


      private Builder() {}

      // $FF: synthetic method
      Builder(Object var1) {
         this();
      }

      private void ensureMandatoryParams() {
         if(this.renderInfo == null) {
            throw new IllegalArgumentException("A RenderInfo must be specified to create a ComponentTreeHolder");
         }
      }

      public ComponentTreeHolder build() {
         this.ensureMandatoryParams();
         ComponentTreeHolder var2 = (ComponentTreeHolder)ComponentTreeHolder.sComponentTreeHoldersPool.acquire();
         ComponentTreeHolder var1 = var2;
         if(var2 == null) {
            var1 = new ComponentTreeHolder();
         }

         var1.mRenderInfo = this.renderInfo;
         var1.mLayoutHandler = this.layoutHandler;
         var1.mPreallocateMountContentHandler = this.preallocateMountContentHandler;
         var1.mCanPreallocateOnDefaultHandler = this.canPreallocateOnDefaultHandler;
         var1.mShouldPreallocatePerMountSpec = this.shouldPreallocatePerMountSpec;
         var1.mUseSharedLayoutStateFuture = this.useSharedLayoutStateFuture;
         var1.mComponentTreeMeasureListenerFactory = this.componentTreeMeasureListenerFactory;
         var1.mSplitLayoutTag = this.splitLayoutTag;
         var1.acquireId();
         var1.mIsReleased.set(false);
         return var1;
      }

      public ComponentTreeHolder.Builder canCacheDrawingDisplayLists(boolean var1) {
         this.canCacheDrawingDisplayLists = var1;
         return this;
      }

      public ComponentTreeHolder.Builder canPreallocateOnDefaultHandler(boolean var1) {
         this.canPreallocateOnDefaultHandler = var1;
         return this;
      }

      public ComponentTreeHolder.Builder canPrefetchDisplayLists(boolean var1) {
         this.canPrefetchDisplayLists = var1;
         return this;
      }

      public ComponentTreeHolder.Builder componentTreeMeasureListenerFactory(ComponentTreeHolder.ComponentTreeMeasureListenerFactory var1) {
         this.componentTreeMeasureListenerFactory = var1;
         return this;
      }

      public ComponentTreeHolder.Builder layoutHandler(LayoutHandler var1) {
         this.layoutHandler = var1;
         return this;
      }

      public ComponentTreeHolder.Builder preallocateMountContentHandler(@Nullable LayoutHandler var1) {
         this.preallocateMountContentHandler = var1;
         return this;
      }

      public ComponentTreeHolder.Builder renderInfo(RenderInfo var1) {
         RenderInfo var2 = var1;
         if(var1 == null) {
            var2 = ComponentRenderInfo.createEmpty();
         }

         this.renderInfo = var2;
         return this;
      }

      public ComponentTreeHolder.Builder shouldPreallocatePerMountSpec(boolean var1) {
         this.shouldPreallocatePerMountSpec = var1;
         return this;
      }

      public ComponentTreeHolder.Builder splitLayoutTag(String var1) {
         this.splitLayoutTag = var1;
         return this;
      }

      public ComponentTreeHolder.Builder useSharedLayoutStateFuture(boolean var1) {
         this.useSharedLayoutStateFuture = var1;
         return this;
      }
   }
}
