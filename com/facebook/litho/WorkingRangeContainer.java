package com.facebook.litho;

import android.support.annotation.VisibleForTesting;
import com.facebook.litho.Component;
import com.facebook.litho.WorkingRange;
import com.facebook.litho.WorkingRangeStatusHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

class WorkingRangeContainer {

   @Nullable
   private Map<String, WorkingRangeContainer.RangeTuple> mWorkingRanges;


   static boolean isEnteringRange(WorkingRange var0, int var1, int var2, int var3, int var4, int var5) {
      return var0.shouldEnterRange(var1, var2, var3, var4, var5);
   }

   static boolean isExitingRange(WorkingRange var0, int var1, int var2, int var3, int var4, int var5) {
      return var0.shouldExitRange(var1, var2, var3, var4, var5);
   }

   void checkWorkingRangeAndDispatch(int var1, int var2, int var3, int var4, int var5, WorkingRangeStatusHandler var6) {
      if(this.mWorkingRanges != null) {
         Iterator var7 = this.mWorkingRanges.keySet().iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            WorkingRangeContainer.RangeTuple var11 = (WorkingRangeContainer.RangeTuple)this.mWorkingRanges.get(var8);
            Iterator var9 = var11.mComponents.iterator();

            while(var9.hasNext()) {
               Component var10 = (Component)var9.next();
               if(!var6.isInRange(var11.mName, var10) && isEnteringRange(var11.mWorkingRange, var1, var2, var3, var4, var5)) {
                  var10.dispatchOnEnteredRange(var11.mName);
                  var6.setEnteredRangeStatus(var11.mName, var10);
               } else if(var6.isInRange(var11.mName, var10) && isExitingRange(var11.mWorkingRange, var1, var2, var3, var4, var5)) {
                  var10.dispatchOnExitedRange(var11.mName);
                  var6.setExitedRangeStatus(var11.mName, var10);
               }
            }
         }

      }
   }

   void dispatchOnExitedRangeIfNeeded(WorkingRangeStatusHandler var1) {
      if(this.mWorkingRanges != null) {
         Iterator var2 = this.mWorkingRanges.keySet().iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            WorkingRangeContainer.RangeTuple var6 = (WorkingRangeContainer.RangeTuple)this.mWorkingRanges.get(var3);
            Iterator var4 = var6.mComponents.iterator();

            while(var4.hasNext()) {
               Component var5 = (Component)var4.next();
               if(var1.isInRange(var6.mName, var5)) {
                  var5.dispatchOnExitedRange(var6.mName);
               }
            }
         }

      }
   }

   @VisibleForTesting
   Map<String, WorkingRangeContainer.RangeTuple> getWorkingRangesForTestOnly() {
      return (Map)(this.mWorkingRanges != null?this.mWorkingRanges:new LinkedHashMap());
   }

   void registerWorkingRange(String var1, WorkingRange var2, Component var3) {
      if(this.mWorkingRanges == null) {
         this.mWorkingRanges = new LinkedHashMap();
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append("_");
      var4.append(var2.hashCode());
      String var6 = var4.toString();
      WorkingRangeContainer.RangeTuple var5 = (WorkingRangeContainer.RangeTuple)this.mWorkingRanges.get(var6);
      if(var5 == null) {
         this.mWorkingRanges.put(var6, new WorkingRangeContainer.RangeTuple(var1, var2, var3));
      } else {
         var5.addComponent(var3);
      }
   }

   static class Registration {

      final Component mComponent;
      final String mName;
      final WorkingRange mWorkingRange;


      Registration(String var1, WorkingRange var2, Component var3) {
         this.mName = var1;
         this.mWorkingRange = var2;
         this.mComponent = var3;
      }
   }

   @VisibleForTesting
   static class RangeTuple {

      final List<Component> mComponents;
      final String mName;
      final WorkingRange mWorkingRange;


      RangeTuple(String var1, WorkingRange var2, Component var3) {
         this.mName = var1;
         this.mWorkingRange = var2;
         this.mComponents = new ArrayList();
         this.mComponents.add(var3);
      }

      void addComponent(Component var1) {
         this.mComponents.add(var1);
      }
   }
}
