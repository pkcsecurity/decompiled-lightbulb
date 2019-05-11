package com.facebook.litho;

import android.support.annotation.VisibleForTesting;
import com.facebook.litho.Component;
import java.util.HashMap;
import java.util.Map;

public class WorkingRangeStatusHandler {

   static final int STATUS_IN_RANGE = 1;
   static final int STATUS_OUT_OF_RANGE = 2;
   static final int STATUS_UNINITIALIZED = 0;
   private final Map<String, Integer> mStatus = new HashMap();


   private static String generateKey(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append("_");
      var2.append(var1);
      return var2.toString();
   }

   @WorkingRangeStatusHandler.WorkingRangeStatus
   private int getStatus(String var1, Component var2) {
      var1 = generateKey(var1, var2.getGlobalKey());
      return this.mStatus.containsKey(var1)?((Integer)this.mStatus.get(var1)).intValue():0;
   }

   void clear() {
      this.mStatus.clear();
   }

   @VisibleForTesting
   Map<String, Integer> getStatus() {
      return this.mStatus;
   }

   boolean isInRange(String var1, Component var2) {
      return this.getStatus(var1, var2) == 1;
   }

   void setEnteredRangeStatus(String var1, Component var2) {
      this.setStatus(var1, var2, 1);
   }

   void setExitedRangeStatus(String var1, Component var2) {
      this.setStatus(var1, var2, 2);
   }

   @VisibleForTesting
   void setStatus(String var1, Component var2, @WorkingRangeStatusHandler.WorkingRangeStatus int var3) {
      String var4 = var2.getGlobalKey();
      this.mStatus.put(generateKey(var1, var4), Integer.valueOf(var3));
   }

   public @interface WorkingRangeStatus {
   }
}
