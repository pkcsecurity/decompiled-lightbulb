package com.facebook.litho.sections;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;
import java.util.ArrayList;
import java.util.List;

@VisibleForTesting(
   otherwise = 3
)
public final class Change {

   public static final int DELETE = 3;
   public static final int DELETE_RANGE = -3;
   private static final List<RenderInfo> EMPTY = new ArrayList();
   public static final int INSERT = 1;
   public static final int INSERT_RANGE = -1;
   public static final int MOVE = 0;
   public static final int UPDATE = 2;
   public static final int UPDATE_RANGE = -2;
   private int mCount;
   private int mIndex;
   private RenderInfo mRenderInfo;
   private List<RenderInfo> mRenderInfos;
   private int mToIndex;
   private int mType;


   private Change(int var1, int var2, int var3, int var4, @Nullable RenderInfo var5, @Nullable List<RenderInfo> var6) {
      this.mType = var1;
      this.mIndex = var2;
      this.mToIndex = var3;
      this.mCount = var4;
      RenderInfo var7 = var5;
      if(var5 == null) {
         var7 = ComponentRenderInfo.createEmpty();
      }

      this.mRenderInfo = var7;
      List var8 = var6;
      if(var6 == null) {
         var8 = EMPTY;
      }

      this.mRenderInfos = var8;
   }

   private static Change acquire(int var0, int var1, int var2, int var3, RenderInfo var4, List<RenderInfo> var5) {
      return new Change(var0, var1, var2, var3, var4, var5);
   }

   private static Change acquireMoveChange(int var0, int var1) {
      return acquire(0, var0, var1, 1, (RenderInfo)null, (List)null);
   }

   private static Change acquireRangedChange(int var0, int var1, int var2, List<RenderInfo> var3) {
      return acquire(var0, var1, -1, var2, (RenderInfo)null, var3);
   }

   private static Change acquireSingularChange(int var0, int var1, RenderInfo var2) {
      return acquire(var0, var1, -1, 1, var2, (List)null);
   }

   static Change copy(Change var0) {
      return acquire(var0.mType, var0.mIndex, var0.mToIndex, var0.mCount, var0.mRenderInfo, var0.mRenderInfos);
   }

   static Change insert(int var0, RenderInfo var1) {
      return acquireSingularChange(1, var0, var1);
   }

   static Change insertRange(int var0, int var1, List<RenderInfo> var2) {
      return acquireRangedChange(-1, var0, var1, var2);
   }

   static Change move(int var0, int var1) {
      return acquireMoveChange(var0, var1);
   }

   static Change offset(Change var0, int var1) {
      int var2;
      if(var0.mToIndex >= 0) {
         var2 = var0.mToIndex + var1;
      } else {
         var2 = -1;
      }

      return acquire(var0.mType, var0.mIndex + var1, var2, var0.mCount, var0.mRenderInfo, var0.mRenderInfos);
   }

   static Change remove(int var0) {
      return acquireSingularChange(3, var0, ComponentRenderInfo.createEmpty());
   }

   static Change removeRange(int var0, int var1) {
      return acquireRangedChange(-3, var0, var1, EMPTY);
   }

   static Change update(int var0, RenderInfo var1) {
      return acquireSingularChange(2, var0, var1);
   }

   static Change updateRange(int var0, int var1, List<RenderInfo> var2) {
      return acquireRangedChange(-2, var0, var1, var2);
   }

   public int getCount() {
      return this.mCount;
   }

   int getIndex() {
      return this.mIndex;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public RenderInfo getRenderInfo() {
      return this.mRenderInfo;
   }

   List<RenderInfo> getRenderInfos() {
      return this.mRenderInfos;
   }

   int getToIndex() {
      return this.mToIndex;
   }

   public int getType() {
      return this.mType;
   }

   void release() {
      this.mRenderInfo = null;
      this.mRenderInfos = null;
   }
}
