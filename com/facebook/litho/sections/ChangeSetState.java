package com.facebook.litho.sections;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.SparseArray;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.ComponentsSystrace;
import com.facebook.litho.PerfEvent;
import com.facebook.litho.sections.Change;
import com.facebook.litho.sections.ChangeSet;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.SectionsLogEventUtils;
import com.facebook.litho.sections.logger.SectionsDebugLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChangeSetState {

   private static final List<Section> sEmptyList = new ArrayList();
   private ChangeSet mChangeSet;
   private Section mCurrentRoot;
   private Section mNewRoot;
   private List<Section> mRemovedComponents = new ArrayList();


   private static SparseArray<ChangeSet> acquireChangeSetSparseArray() {
      return new SparseArray();
   }

   private static ChangeSetState acquireChangeSetState() {
      return new ChangeSetState();
   }

   private static void checkCount(Section var0, Section var1, ChangeSetState var2) {
      byte var4 = 0;
      boolean var3;
      if((var0 == null || var0.getCount() >= 0) && (var1 == null || var1.getCount() >= 0)) {
         var3 = false;
      } else {
         var3 = true;
      }

      if(var3) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Changet count is below 0! ");
         var5.append("Current section: ");
         if(var0 == null) {
            var5.append("null; ");
         } else {
            StringBuilder var6 = new StringBuilder();
            var6.append(var0.getSimpleName());
            var6.append(" , key=");
            var6.append(var0.getGlobalKey());
            var6.append(", count=");
            var6.append(var0.getCount());
            var6.append(", childrenSize=");
            var6.append(var0.getChildren().size());
            var6.append("; ");
            var5.append(var6.toString());
         }

         var5.append("Next section: ");
         if(var1 == null) {
            var5.append("null; ");
         } else {
            StringBuilder var7 = new StringBuilder();
            var7.append(var1.getSimpleName());
            var7.append(" , key=");
            var7.append(var1.getGlobalKey());
            var7.append(", count=");
            var7.append(var1.getCount());
            var7.append(", childrenSize=");
            var7.append(var1.getChildren().size());
            var7.append("; ");
            var5.append(var7.toString());
         }

         var5.append("Changes: [");
         ChangeSet var8 = var2.mChangeSet;

         for(int var11 = var4; var11 < var8.getCount(); ++var11) {
            Change var9 = var8.getChangeAt(var11);
            StringBuilder var10 = new StringBuilder();
            var10.append(var9.getType());
            var10.append(" ");
            var10.append(var9.getIndex());
            var10.append(" ");
            var10.append(var9.getToIndex());
            var5.append(var10.toString());
            var5.append(", ");
         }

         var5.append("]");
         throw new IllegalStateException(var5.toString());
      }
   }

   static ChangeSetState generateChangeSet(SectionContext var0, @Nullable Section var1, Section var2, SectionsDebugLogger var3, String var4, String var5, String var6, boolean var7) {
      ChangeSetState var9 = acquireChangeSetState();
      var9.mCurrentRoot = var1;
      var9.mNewRoot = var2;
      ComponentsLogger var10 = var0.getLogger();
      PerfEvent var11 = SectionsLogEventUtils.getSectionsPerformanceEvent(var0, 13, var1, var2);
      if(var1 != null && var2 != null && !var1.getSimpleName().equals(var2.getSimpleName())) {
         var9.mChangeSet = ChangeSet.merge(generateChangeSetRecursive(var0, var1, (Section)null, var9.mRemovedComponents, var3, var4, var5, var6, Thread.currentThread().getName(), var7), generateChangeSetRecursive(var0, (Section)null, var2, var9.mRemovedComponents, var3, var4, var5, var6, Thread.currentThread().getName(), var7));
      } else {
         var9.mChangeSet = generateChangeSetRecursive(var0, var1, var2, var9.mRemovedComponents, var3, var4, var5, var6, Thread.currentThread().getName(), var7);
      }

      if(var10 != null && var11 != null) {
         int var8;
         if(var1 == null) {
            var8 = -1;
         } else {
            var8 = var1.getCount();
         }

         var11.markerAnnotate("current_root_count", var8);
         var11.markerAnnotate("change_count", var9.mChangeSet.getChangeCount());
         var11.markerAnnotate("final_count", var9.mChangeSet.getCount());
         ChangeSet.ChangeSetStats var12 = var9.mChangeSet.getChangeSetStats();
         if(var12 != null) {
            var11.markerAnnotate("changeset_effective_count", var12.getEffectiveChangesCount());
            var11.markerAnnotate("changeset_insert_single_count", var12.getInsertSingleCount());
            var11.markerAnnotate("changeset_insert_range_count", var12.getInsertRangeCount());
            var11.markerAnnotate("changeset_delete_single_count", var12.getDeleteSingleCount());
            var11.markerAnnotate("changeset_delete_range_count", var12.getDeleteRangeCount());
            var11.markerAnnotate("changeset_update_single_count", var12.getUpdateSingleCount());
            var11.markerAnnotate("changeset_update_range_count", var12.getUpdateRangeCount());
            var11.markerAnnotate("changeset_move_count", var12.getMoveCount());
         }

         var10.logPerfEvent(var11);
      }

      checkCount(var1, var2, var9);
      return var9;
   }

   private static ChangeSet generateChangeSetRecursive(SectionContext var0, Section var1, Section var2, List<Section> var3, SectionsDebugLogger var4, String var5, String var6, String var7, String var8, boolean var9) {
      byte var12 = 0;
      boolean var10;
      if(var1 == null) {
         var10 = true;
      } else {
         var10 = false;
      }

      boolean var11;
      if(var2 == null) {
         var11 = true;
      } else {
         var11 = false;
      }

      if(var10 && var11) {
         throw new IllegalStateException("Both currentRoot and newRoot are null.");
      } else {
         ChangeSet var23;
         int var32;
         int var33;
         if(var11) {
            var33 = var1.getCount();
            var3.add(var1);
            var23 = ChangeSet.acquireChangeSet(var1.getCount(), var2, var9);

            for(var32 = 0; var32 < var33; ++var32) {
               var23.addChange(Change.remove(0));
            }

            return var23;
         } else {
            String var16 = updatePrefix(var1, var6);
            String var17 = updatePrefix(var2, var7);
            if(!var10 && !var2.shouldComponentUpdate(var1, var2)) {
               var23 = ChangeSet.acquireChangeSet(var1.getCount(), var2, var9);
               var2.setCount(var23.getCount());
               var4.logShouldUpdate(var5, var1, var2, var16, var17, Boolean.valueOf(false), var8);
               return var23;
            } else {
               var4.logShouldUpdate(var5, var1, var2, var16, var17, Boolean.valueOf(true), var8);
               if(var2.isDiffSectionSpec()) {
                  boolean var15 = ComponentsSystrace.isTracing();
                  if(var15) {
                     ComponentsSystrace.ArgsBuilder var27 = ComponentsSystrace.beginSectionWithArgs("generateChangeSet");
                     String var24;
                     if(var10) {
                        var24 = "<null>";
                     } else {
                        var24 = var1.getKey();
                     }

                     var27.arg("current_root", var24).arg("update_prefix", var16).flush();
                  }

                  if(var10) {
                     var32 = var12;
                  } else {
                     var32 = var1.getCount();
                  }

                  var23 = ChangeSet.acquireChangeSet(var32, var2, var9);
                  var2.generateChangeSet(var2.getScopedContext(), var23, var1, var2);
                  var2.setCount(var23.getCount());
                  if(var15) {
                     ComponentsSystrace.endSection();
                  }

                  return var23;
               } else {
                  ChangeSet var28 = ChangeSet.acquireChangeSet(var2, var9);
                  Map var18 = Section.acquireChildrenMap(var1);
                  Map var19 = Section.acquireChildrenMap(var2);
                  Object var31;
                  if(var1 == null) {
                     var31 = sEmptyList;
                  } else {
                     var31 = new ArrayList(var1.getChildren());
                  }

                  List var20 = var2.getChildren();
                  var32 = 0;
                  var33 = -1;
                  int var34 = -1;

                  ChangeSet var25;
                  for(var25 = var28; var32 < var20.size(); ++var32) {
                     String var21 = ((Section)var20.get(var32)).getGlobalKey();
                     if(var18.containsKey(var21)) {
                        Pair var29 = (Pair)var18.get(var21);
                        Section var22 = (Section)var29.first;
                        int var14 = ((Integer)var29.second).intValue();
                        if(var33 > var14) {
                           int var13;
                           for(var13 = 0; var13 < var22.getCount(); ++var13) {
                              var25.addChange(Change.move(getPreviousChildrenCount((List)var31, var21), var34));
                           }

                           ((List)var31).remove(var14);
                           ((List)var31).add(var33, var22);
                           var14 = ((List)var31).size();

                           for(var13 = 0; var13 < var14; ++var13) {
                              Section var30 = (Section)((List)var31).get(var13);
                              Pair var35 = (Pair)var18.get(var30.getGlobalKey());
                              if(((Integer)var35.second).intValue() != var13) {
                                 var18.put(var30.getGlobalKey(), new Pair(var35.first, Integer.valueOf(var13)));
                              }
                           }
                        } else {
                           var28 = var25;
                           var25 = var25;
                           if(var14 > var33) {
                              var34 = getPreviousChildrenCount((List)var31, var21) + ((Section)((List)var31).get(var14)).getCount() - 1;
                              var33 = var14;
                              var25 = var28;
                           }
                        }
                     }
                  }

                  var32 = 0;
                  SparseArray var26 = generateChildrenChangeSets(var0, var18, var19, (List)var31, var20, var3, var4, var5, var16, var17, var8, var9);
                  var33 = var26.size();

                  for(var23 = var25; var32 < var33; ++var32) {
                     var25 = (ChangeSet)var26.valueAt(var32);
                     var23 = ChangeSet.merge(var23, var25);
                     if(var25 != null) {
                        var25.release();
                     }
                  }

                  releaseChangeSetSparseArray(var26);
                  var2.setCount(var23.getCount());
                  return var23;
               }
            }
         }
      }
   }

   private static SparseArray<ChangeSet> generateChildrenChangeSets(SectionContext var0, Map<String, Pair<Section, Integer>> var1, Map<String, Pair<Section, Integer>> var2, List<Section> var3, List<Section> var4, List<Section> var5, SectionsDebugLogger var6, String var7, String var8, String var9, String var10, boolean var11) {
      SparseArray var15 = acquireChangeSetSparseArray();
      int var13 = 0;

      int var12;
      for(var12 = 0; var12 < var3.size(); ++var12) {
         String var16 = ((Section)var3.get(var12)).getGlobalKey();
         Section var17 = (Section)var3.get(var12);
         if(var2.get(var16) == null) {
            var15.put(var12, generateChangeSetRecursive(var0, var17, (Section)null, var5, var6, var7, var8, var9, var10, var11));
         }
      }

      for(int var14 = 0; var13 < var4.size(); ++var13) {
         Section var18 = (Section)var4.get(var13);
         Pair var20 = (Pair)var1.get(var18.getGlobalKey());
         if(var20 != null) {
            var12 = ((Integer)var20.second).intValue();
         } else {
            var12 = -1;
         }

         ChangeSet var19;
         ChangeSet var21;
         if(var12 < 0) {
            var21 = (ChangeSet)var15.get(var14);
            var19 = generateChangeSetRecursive(var0, (Section)null, var18, var5, var6, var7, var8, var9, var10, var11);
            var15.put(var14, ChangeSet.merge(var21, var19));
            if(var21 != null) {
               var21.release();
            }

            var19.release();
         } else {
            var21 = (ChangeSet)var15.get(var12);
            var19 = generateChangeSetRecursive(var0, (Section)var3.get(var12), var18, var5, var6, var7, var8, var9, var10, var11);
            var15.put(var12, ChangeSet.merge(var21, var19));
            if(var21 != null) {
               var21.release();
            }

            var19.release();
            var14 = var12;
         }
      }

      Section.releaseChildrenMap(var1);
      Section.releaseChildrenMap(var2);
      return var15;
   }

   private static final int getPreviousChildrenCount(List<Section> var0, String var1) {
      Iterator var4 = var0.iterator();

      int var2;
      Section var3;
      for(var2 = 0; var4.hasNext(); var2 += var3.getCount()) {
         var3 = (Section)var4.next();
         if(var3.getGlobalKey().equals(var1)) {
            return var2;
         }
      }

      return var2;
   }

   private static void releaseChangeSetSparseArray(SparseArray<ChangeSet> var0) {}

   private static final String updatePrefix(Section var0, String var1) {
      if(var0 != null && var0.getParent() == null) {
         return var0.getClass().getSimpleName();
      } else if(var0 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append("->");
         var2.append(var0.getClass().getSimpleName());
         return var2.toString();
      } else {
         return "";
      }
   }

   ChangeSet getChangeSet() {
      return this.mChangeSet;
   }

   Section getCurrentRoot() {
      return this.mCurrentRoot;
   }

   Section getNewRoot() {
      return this.mNewRoot;
   }

   List<Section> getRemovedComponents() {
      return this.mRemovedComponents;
   }

   void release() {
      this.mRemovedComponents.clear();
   }
}
