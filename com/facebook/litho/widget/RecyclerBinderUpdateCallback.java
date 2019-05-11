package com.facebook.litho.widget;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.support.v7.util.ListUpdateCallback;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentsReporter;
import com.facebook.litho.ComponentsSystrace;
import com.facebook.litho.widget.DataDiffModelName;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.RecyclerBinderOperationExecutor;
import com.facebook.litho.widget.RenderInfo;
import java.util.ArrayList;
import java.util.List;

public class RecyclerBinderUpdateCallback<T extends Object> implements ListUpdateCallback {

   private static final Pools.SynchronizedPool<RecyclerBinderUpdateCallback> sUpdatesCallbackPool = new Pools.SynchronizedPool(4);
   private RecyclerBinderUpdateCallback.ComponentRenderer mComponentRenderer;
   private List<T> mData;
   private int mHeadOffset;
   private int mOldDataSize;
   private RecyclerBinderUpdateCallback.OperationExecutor mOperationExecutor;
   private List<RecyclerBinderUpdateCallback.Operation> mOperations;
   private List<RecyclerBinderUpdateCallback.ComponentContainer> mPlaceholders;


   public static <T extends Object> RecyclerBinderUpdateCallback<T> acquire(int var0, List<T> var1, RecyclerBinderUpdateCallback.ComponentRenderer<T> var2, RecyclerBinder var3) {
      return acquire(var0, var1, var2, new RecyclerBinderOperationExecutor(var3), 0);
   }

   public static <T extends Object> RecyclerBinderUpdateCallback<T> acquire(int var0, List<T> var1, RecyclerBinderUpdateCallback.ComponentRenderer<T> var2, RecyclerBinderUpdateCallback.OperationExecutor var3, int var4) {
      RecyclerBinderUpdateCallback var6 = (RecyclerBinderUpdateCallback)sUpdatesCallbackPool.acquire();
      RecyclerBinderUpdateCallback var5 = var6;
      if(var6 == null) {
         var5 = new RecyclerBinderUpdateCallback();
      }

      var5.init(var0, var1, var2, var3, var4);
      return var5;
   }

   private static String getModelName(Object var0) {
      return var0 instanceof DataDiffModelName?((DataDiffModelName)var0).getName():var0.getClass().getSimpleName();
   }

   private void init(int var1, List<T> var2, RecyclerBinderUpdateCallback.ComponentRenderer<T> var3, RecyclerBinderUpdateCallback.OperationExecutor var4, int var5) {
      this.mOldDataSize = var1;
      this.mData = var2;
      this.mComponentRenderer = var3;
      this.mOperationExecutor = var4;
      this.mHeadOffset = var5;
      this.mOperations = new ArrayList();
      this.mPlaceholders = new ArrayList();

      for(var5 = 0; var5 < var1; ++var5) {
         this.mPlaceholders.add(RecyclerBinderUpdateCallback.ComponentContainer.acquire());
      }

   }

   private void logErrorForInconsistentSize(ComponentContext var1) {
      StringBuilder var6 = new StringBuilder();
      var6.append("Inconsistent size between mPlaceholders(");
      var6.append(this.mPlaceholders.size());
      var6.append(") and mData(");
      var6.append(this.mData.size());
      var6.append("); ");
      var6.append("mOperations: [");
      int var4 = this.mOperations.size();
      byte var3 = 0;

      int var2;
      for(var2 = 0; var2 < var4; ++var2) {
         RecyclerBinderUpdateCallback.Operation var5 = (RecyclerBinderUpdateCallback.Operation)this.mOperations.get(var2);
         var6.append("[type=");
         var6.append(var5.getType());
         var6.append(", index=");
         var6.append(var5.getIndex());
         var6.append(", toIndex=");
         var6.append(var5.getToIndex());
         if(var5.mComponentContainers != null) {
            var6.append(", count=");
            var6.append(var5.mComponentContainers.size());
         }

         var6.append("], ");
      }

      var6.append("]; ");
      var6.append("mData: [");
      var4 = this.mData.size();

      for(var2 = var3; var2 < var4; ++var2) {
         var6.append("[");
         var6.append(this.mData.get(var2));
         var6.append("], ");
      }

      var6.append("]");
      ComponentsReporter.emitMessage(ComponentsReporter.LogLevel.ERROR, var6.toString());
   }

   public static <T extends Object> void release(RecyclerBinderUpdateCallback<T> var0) {
      List var3 = var0.mOperations;
      int var2 = var3.size();

      int var1;
      for(var1 = 0; var1 < var2; ++var1) {
         ((RecyclerBinderUpdateCallback.Operation)var3.get(var1)).release();
      }

      var0.mOperations = null;
      var0.mData = null;
      var2 = var0.mPlaceholders.size();

      for(var1 = 0; var1 < var2; ++var1) {
         ((RecyclerBinderUpdateCallback.ComponentContainer)var0.mPlaceholders.get(var1)).release();
      }

      var0.mComponentRenderer = null;
      var0.mOperationExecutor = null;
      var0.mHeadOffset = 0;
      sUpdatesCallbackPool.release(var0);
   }

   public void applyChangeset(ComponentContext var1) {
      boolean var4 = ComponentsSystrace.isTracing();
      List var5 = this.mData;
      int var2 = 0;
      int var3;
      if(var5 != null && this.mData.size() != this.mPlaceholders.size()) {
         this.logErrorForInconsistentSize(var1);
         var3 = this.mPlaceholders.size();

         for(var2 = 0; var2 < var3; ++var2) {
            ((RecyclerBinderUpdateCallback.ComponentContainer)this.mPlaceholders.get(var2)).release();
         }

         var3 = this.mOperations.size();

         for(var2 = 0; var2 < var3; ++var2) {
            ((RecyclerBinderUpdateCallback.Operation)this.mOperations.get(var2)).release();
         }

         var3 = this.mData.size();
         ArrayList var10 = new ArrayList(var3);

         for(var2 = 0; var2 < var3; ++var2) {
            Object var11 = this.mData.get(var2);
            RecyclerBinderUpdateCallback.ComponentContainer var7 = RecyclerBinderUpdateCallback.ComponentContainer.acquire();
            if(var4) {
               StringBuilder var8 = new StringBuilder();
               var8.append("renderInfo:");
               var8.append(getModelName(var11));
               ComponentsSystrace.beginSection(var8.toString());
            }

            var7.mRenderInfo = this.mComponentRenderer.render(var11, var2);
            if(var4) {
               ComponentsSystrace.endSection();
            }

            var10.add(var2, var7);
         }

         this.mOperations = new ArrayList();
         this.mOperations.add(RecyclerBinderUpdateCallback.Operation.acquire(2, 0, this.mOldDataSize, (List)null));
         this.mOperations.add(RecyclerBinderUpdateCallback.Operation.acquire(0, 0, -1, var10));
      } else {
         for(var3 = this.mPlaceholders.size(); var2 < var3; ++var2) {
            if(((RecyclerBinderUpdateCallback.ComponentContainer)this.mPlaceholders.get(var2)).mNeedsComputation) {
               Object var9 = this.mData.get(var2);
               if(var4) {
                  StringBuilder var6 = new StringBuilder();
                  var6.append("renderInfo:");
                  var6.append(getModelName(var9));
                  ComponentsSystrace.beginSection(var6.toString());
               }

               ((RecyclerBinderUpdateCallback.ComponentContainer)this.mPlaceholders.get(var2)).mRenderInfo = this.mComponentRenderer.render(var9, var2);
               if(var4) {
                  ComponentsSystrace.endSection();
               }
            }
         }
      }

      if(var4) {
         ComponentsSystrace.beginSection("executeOperations");
      }

      this.mOperationExecutor.executeOperations(var1, this.mOperations);
      if(var4) {
         ComponentsSystrace.endSection();
      }

   }

   @VisibleForTesting
   List<RecyclerBinderUpdateCallback.Operation> getOperations() {
      return this.mOperations;
   }

   public void onChanged(int var1, int var2, Object var3) {
      ArrayList var6 = new ArrayList();
      int var4 = var1 + this.mHeadOffset;

      for(var1 = 0; var1 < var2; ++var1) {
         RecyclerBinderUpdateCallback.ComponentContainer var5 = (RecyclerBinderUpdateCallback.ComponentContainer)this.mPlaceholders.get(var4 + var1);
         var5.mNeedsComputation = true;
         var6.add(var5);
      }

      this.mOperations.add(RecyclerBinderUpdateCallback.Operation.acquire(1, var4, -1, var6));
   }

   public void onInserted(int var1, int var2) {
      ArrayList var4 = new ArrayList();
      int var3 = var1 + this.mHeadOffset;

      for(var1 = 0; var1 < var2; ++var1) {
         RecyclerBinderUpdateCallback.ComponentContainer var5 = RecyclerBinderUpdateCallback.ComponentContainer.acquire();
         var5.mNeedsComputation = true;
         this.mPlaceholders.add(var3 + var1, var5);
         var4.add(var5);
      }

      this.mOperations.add(RecyclerBinderUpdateCallback.Operation.acquire(0, var3, -1, var4));
   }

   public void onMoved(int var1, int var2) {
      var1 += this.mHeadOffset;
      var2 += this.mHeadOffset;
      this.mOperations.add(RecyclerBinderUpdateCallback.Operation.acquire(3, var1, var2, (List)null));
      RecyclerBinderUpdateCallback.ComponentContainer var3 = (RecyclerBinderUpdateCallback.ComponentContainer)this.mPlaceholders.remove(var1);
      this.mPlaceholders.add(var2, var3);
   }

   public void onRemoved(int var1, int var2) {
      int var3 = var1 + this.mHeadOffset;

      for(var1 = 0; var1 < var2; ++var1) {
         ((RecyclerBinderUpdateCallback.ComponentContainer)this.mPlaceholders.remove(var3)).release();
      }

      this.mOperations.add(RecyclerBinderUpdateCallback.Operation.acquire(2, var3, var2, (List)null));
   }

   public interface ComponentRenderer<T extends Object> {

      RenderInfo render(T var1, int var2);
   }

   public static class Operation {

      public static final int DELETE = 2;
      public static final int INSERT = 0;
      public static final int MOVE = 3;
      public static final int UPDATE = 1;
      private static final Pools.SynchronizedPool<RecyclerBinderUpdateCallback.Operation> sOperationsPool = new Pools.SynchronizedPool(8);
      private List<RecyclerBinderUpdateCallback.ComponentContainer> mComponentContainers;
      private int mIndex;
      private int mToIndex;
      private int mType;


      private static RecyclerBinderUpdateCallback.Operation acquire(int var0, int var1, int var2, List<RecyclerBinderUpdateCallback.ComponentContainer> var3) {
         RecyclerBinderUpdateCallback.Operation var5 = (RecyclerBinderUpdateCallback.Operation)sOperationsPool.acquire();
         RecyclerBinderUpdateCallback.Operation var4 = var5;
         if(var5 == null) {
            var4 = new RecyclerBinderUpdateCallback.Operation();
         }

         var4.init(var0, var1, var2, var3);
         return var4;
      }

      private void init(int var1, int var2, int var3, List<RecyclerBinderUpdateCallback.ComponentContainer> var4) {
         this.mType = var1;
         this.mIndex = var2;
         this.mToIndex = var3;
         this.mComponentContainers = var4;
      }

      private void release() {
         if(this.mComponentContainers != null) {
            this.mComponentContainers.clear();
            this.mComponentContainers = null;
         }

         sOperationsPool.release(this);
      }

      public List<RecyclerBinderUpdateCallback.ComponentContainer> getComponentContainers() {
         return this.mComponentContainers;
      }

      public int getIndex() {
         return this.mIndex;
      }

      public int getToIndex() {
         return this.mToIndex;
      }

      public int getType() {
         return this.mType;
      }
   }

   public interface OperationExecutor {

      void executeOperations(@Nullable ComponentContext var1, List<RecyclerBinderUpdateCallback.Operation> var2);
   }

   public static class ComponentContainer {

      private static final Pools.SynchronizedPool<RecyclerBinderUpdateCallback.ComponentContainer> sComponentContainerPool = new Pools.SynchronizedPool(8);
      private boolean mNeedsComputation = false;
      private RenderInfo mRenderInfo;


      public static RecyclerBinderUpdateCallback.ComponentContainer acquire() {
         RecyclerBinderUpdateCallback.ComponentContainer var1 = (RecyclerBinderUpdateCallback.ComponentContainer)sComponentContainerPool.acquire();
         RecyclerBinderUpdateCallback.ComponentContainer var0 = var1;
         if(var1 == null) {
            var0 = new RecyclerBinderUpdateCallback.ComponentContainer();
         }

         return var0;
      }

      public RenderInfo getRenderInfo() {
         return this.mRenderInfo;
      }

      public void release() {
         this.mRenderInfo = null;
         this.mNeedsComputation = false;
         sComponentContainerPool.release(this);
      }
   }
}
