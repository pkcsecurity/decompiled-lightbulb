package com.facebook.litho.sections;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.litho.TreeProps;
import com.facebook.litho.sections.Change;
import com.facebook.litho.sections.Section;
import com.facebook.litho.widget.RenderInfo;
import com.facebook.litho.widget.TreePropsWrappedRenderInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadConfined("ANY")
public final class ChangeSet {

   @Nullable
   private ChangeSet.ChangeSetStats mChangeSetStats;
   private final List<Change> mChanges = new ArrayList();
   private int mFinalCount;
   private Section mSection;


   private static ChangeSet acquire() {
      return new ChangeSet();
   }

   static ChangeSet acquireChangeSet(int var0, Section var1, boolean var2) {
      ChangeSet var3 = acquire();
      var3.mFinalCount = var0;
      var3.mSection = var1;
      ChangeSet.ChangeSetStats var4;
      if(var2) {
         var4 = new ChangeSet.ChangeSetStats();
      } else {
         var4 = null;
      }

      var3.mChangeSetStats = var4;
      return var3;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public static ChangeSet acquireChangeSet(Section var0, boolean var1) {
      return acquireChangeSet(0, var0, var1);
   }

   private static int getChangeDelta(Change var0) {
      int var2 = var0.getType();
      int var1 = 1;
      if(var2 != -3) {
         if(var2 == -1) {
            return var0.getCount();
         }

         if(var2 != 1) {
            if(var2 != 3) {
               return 0;
            }

            return -1;
         }
      } else {
         var1 = -var0.getCount();
      }

      return var1;
   }

   static ChangeSet merge(ChangeSet var0, ChangeSet var1) {
      int var3 = 0;
      ChangeSet.ChangeSetStats var5 = null;
      ChangeSet var6 = acquireChangeSet((Section)null, false);
      int var2;
      if(var0 != null) {
         var2 = var0.mFinalCount;
      } else {
         var2 = 0;
      }

      if(var1 != null) {
         var3 = var1.mFinalCount;
      }

      List var7 = var6.mChanges;
      ChangeSet.ChangeSetStats var4;
      if(var0 != null) {
         var4 = var0.getChangeSetStats();
      } else {
         var4 = null;
      }

      if(var1 != null) {
         var5 = var1.getChangeSetStats();
      }

      Iterator var8;
      if(var0 != null) {
         var8 = var0.mChanges.iterator();

         while(var8.hasNext()) {
            var7.add(Change.copy((Change)var8.next()));
         }
      }

      if(var1 != null) {
         var8 = var1.mChanges.iterator();

         while(var8.hasNext()) {
            var7.add(Change.offset((Change)var8.next(), var2));
         }
      }

      var6.mFinalCount = var2 + var3;
      var6.mChangeSetStats = ChangeSet.ChangeSetStats.merge(var4, var5);
      return var6;
   }

   private static List<RenderInfo> wrapTreePropRenderInfos(List<RenderInfo> var0, @Nullable TreeProps var1) {
      if(var1 == null) {
         return var0;
      } else {
         ArrayList var3 = new ArrayList(var0.size());

         for(int var2 = 0; var2 < var0.size(); ++var2) {
            var3.add(new TreePropsWrappedRenderInfo((RenderInfo)var0.get(var2), var1));
         }

         return var3;
      }
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public void addChange(Change var1) {
      this.mChanges.add(var1);
      int var2 = getChangeDelta(var1);
      this.mFinalCount += var2;
      if(this.mChangeSetStats != null) {
         this.mChangeSetStats = this.mChangeSetStats.merge(ChangeSet.ChangeSetStats.fromChange(var1, var2));
      }

   }

   public void delete(int var1) {
      this.addChange(Change.remove(var1));
   }

   public void deleteRange(int var1, int var2) {
      this.addChange(Change.removeRange(var1, var2));
   }

   public Change getChangeAt(int var1) {
      return (Change)this.mChanges.get(var1);
   }

   public int getChangeCount() {
      return this.mChanges.size();
   }

   @Nullable
   public ChangeSet.ChangeSetStats getChangeSetStats() {
      return this.mChangeSetStats;
   }

   int getCount() {
      return this.mFinalCount;
   }

   public void insert(int var1, RenderInfo var2, @Nullable TreeProps var3) {
      if(this.mSection != null) {
         var2.addDebugInfo("section_global_key", this.mSection.getGlobalKey());
      }

      this.addChange(Change.insert(var1, new TreePropsWrappedRenderInfo(var2, var3)));
   }

   public void insertRange(int var1, int var2, List<RenderInfo> var3, @Nullable TreeProps var4) {
      if(this.mSection != null) {
         int var5 = 0;

         for(int var6 = var3.size(); var5 < var6; ++var5) {
            ((RenderInfo)var3.get(var5)).addDebugInfo("section_global_key", this.mSection.getGlobalKey());
         }
      }

      this.addChange(Change.insertRange(var1, var2, wrapTreePropRenderInfos(var3, var4)));
   }

   public void move(int var1, int var2) {
      this.addChange(Change.move(var1, var2));
   }

   void release() {
      Iterator var1 = this.mChanges.iterator();

      while(var1.hasNext()) {
         ((Change)var1.next()).release();
      }

      this.mChanges.clear();
      this.mChangeSetStats = null;
      this.mFinalCount = 0;
   }

   public void update(int var1, RenderInfo var2, @Nullable TreeProps var3) {
      this.addChange(Change.update(var1, new TreePropsWrappedRenderInfo(var2, var3)));
   }

   public void updateRange(int var1, int var2, List<RenderInfo> var3, @Nullable TreeProps var4) {
      this.addChange(Change.updateRange(var1, var2, wrapTreePropRenderInfos(var3, var4)));
   }

   static class ChangeSetStats {

      private final int mDeleteRangeCount;
      private final int mDeleteSingleCount;
      private final int mEffectiveChangesCount;
      private final int mInsertRangeCount;
      private final int mInsertSingleCount;
      private final int mMoveCount;
      private final int mUpdateRangeCount;
      private final int mUpdateSingleCount;


      ChangeSetStats() {
         this.mEffectiveChangesCount = 0;
         this.mInsertSingleCount = 0;
         this.mInsertRangeCount = 0;
         this.mDeleteSingleCount = 0;
         this.mDeleteRangeCount = 0;
         this.mUpdateSingleCount = 0;
         this.mUpdateRangeCount = 0;
         this.mMoveCount = 0;
      }

      ChangeSetStats(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         this.mEffectiveChangesCount = var1;
         this.mInsertSingleCount = var2;
         this.mInsertRangeCount = var3;
         this.mDeleteSingleCount = var4;
         this.mDeleteRangeCount = var5;
         this.mUpdateSingleCount = var6;
         this.mUpdateRangeCount = var7;
         this.mMoveCount = var8;
      }

      static ChangeSet.ChangeSetStats fromChange(Change var0, int var1) {
         int var2;
         byte var3;
         byte var4;
         int var5;
         int var6;
         byte var7;
         int var8;
         label55: {
            label56: {
               label57: {
                  label40: {
                     label39: {
                        switch(var0.getType()) {
                        case -3:
                           var5 = var0.getCount() + 0;
                           var4 = 0;
                           var6 = 0;
                           var7 = 0;
                           break label57;
                        case -2:
                           var2 = var0.getCount() + 0;
                           var4 = 0;
                           var6 = 0;
                           var7 = 0;
                           var5 = 0;
                           var3 = 0;
                           break label55;
                        case -1:
                           var6 = var0.getCount() + 0;
                           var4 = 0;
                           break label39;
                        case 0:
                           var8 = var0.getCount() + 0;
                           var4 = 0;
                           var6 = 0;
                           var7 = 0;
                           var5 = 0;
                           var3 = 0;
                           var2 = 0;
                           return new ChangeSet.ChangeSetStats(var1, var4, var6, var7, var5, var3, var2, var8);
                        case 1:
                           var4 = 1;
                           break;
                        case 2:
                           var4 = 0;
                           var6 = 0;
                           var7 = 0;
                           var5 = 0;
                           var3 = 1;
                           break label56;
                        case 3:
                           var4 = 0;
                           var6 = 0;
                           var7 = 1;
                           break label40;
                        default:
                           var4 = 0;
                        }

                        var6 = 0;
                     }

                     var7 = 0;
                  }

                  var5 = 0;
               }

               var3 = 0;
            }

            var2 = 0;
         }

         var8 = 0;
         return new ChangeSet.ChangeSetStats(var1, var4, var6, var7, var5, var3, var2, var8);
      }

      @Nullable
      static ChangeSet.ChangeSetStats merge(@Nullable ChangeSet.ChangeSetStats var0, @Nullable ChangeSet.ChangeSetStats var1) {
         return var0 == null?var1:(var1 == null?var0:var0.merge(var1));
      }

      public boolean equals(Object var1) {
         if(this == var1) {
            return true;
         } else if(var1 != null) {
            if(this.getClass() != var1.getClass()) {
               return false;
            } else {
               ChangeSet.ChangeSetStats var2 = (ChangeSet.ChangeSetStats)var1;
               return this.mEffectiveChangesCount != var2.mEffectiveChangesCount?false:(this.mInsertSingleCount != var2.mInsertSingleCount?false:(this.mInsertRangeCount != var2.mInsertRangeCount?false:(this.mDeleteSingleCount != var2.mDeleteSingleCount?false:(this.mDeleteRangeCount != var2.mDeleteRangeCount?false:(this.mUpdateSingleCount != var2.mUpdateSingleCount?false:(this.mUpdateRangeCount != var2.mUpdateRangeCount?false:this.mMoveCount == var2.mMoveCount))))));
            }
         } else {
            return false;
         }
      }

      public int getDeleteRangeCount() {
         return this.mDeleteRangeCount;
      }

      public int getDeleteSingleCount() {
         return this.mDeleteSingleCount;
      }

      public int getEffectiveChangesCount() {
         return this.mEffectiveChangesCount;
      }

      public int getInsertRangeCount() {
         return this.mInsertRangeCount;
      }

      public int getInsertSingleCount() {
         return this.mInsertSingleCount;
      }

      public int getMoveCount() {
         return this.mMoveCount;
      }

      public int getUpdateRangeCount() {
         return this.mUpdateRangeCount;
      }

      public int getUpdateSingleCount() {
         return this.mUpdateSingleCount;
      }

      public int hashCode() {
         return ((((((this.mEffectiveChangesCount * 31 + this.mInsertSingleCount) * 31 + this.mInsertRangeCount) * 31 + this.mDeleteSingleCount) * 31 + this.mDeleteRangeCount) * 31 + this.mUpdateSingleCount) * 31 + this.mUpdateRangeCount) * 31 + this.mMoveCount;
      }

      @Nullable
      ChangeSet.ChangeSetStats merge(@Nullable ChangeSet.ChangeSetStats var1) {
         if(var1 == null) {
            return null;
         } else {
            int var2 = var1.mEffectiveChangesCount;
            int var3 = this.mEffectiveChangesCount;
            int var4 = var1.mInsertSingleCount;
            int var5 = this.mInsertSingleCount;
            int var6 = var1.mInsertRangeCount;
            int var7 = this.mInsertRangeCount;
            int var8 = var1.mDeleteSingleCount;
            int var9 = this.mDeleteSingleCount;
            int var10 = var1.mDeleteRangeCount;
            int var11 = this.mDeleteRangeCount;
            int var12 = var1.mUpdateSingleCount;
            int var13 = this.mUpdateSingleCount;
            int var14 = var1.mUpdateRangeCount;
            return new ChangeSet.ChangeSetStats(var3 + var2, var5 + var4, var7 + var6, var9 + var8, var11 + var10, var13 + var12, this.mUpdateRangeCount + var14, var1.mMoveCount + this.mMoveCount);
         }
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("ChangeSetStats{mEffectiveChangesCount=");
         var1.append(this.mEffectiveChangesCount);
         var1.append(", mInsertSingleCount=");
         var1.append(this.mInsertSingleCount);
         var1.append(", mInsertRangeCount=");
         var1.append(this.mInsertRangeCount);
         var1.append(", mDeleteSingleCount=");
         var1.append(this.mDeleteSingleCount);
         var1.append(", mDeleteRangeCount=");
         var1.append(this.mDeleteRangeCount);
         var1.append(", mUpdateSingleCount=");
         var1.append(this.mUpdateSingleCount);
         var1.append(", mUpdateRangeCount=");
         var1.append(this.mUpdateRangeCount);
         var1.append(", mMoveCount=");
         var1.append(this.mMoveCount);
         var1.append('}');
         return var1.toString();
      }
   }
}
