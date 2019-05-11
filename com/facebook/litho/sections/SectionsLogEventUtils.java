package com.facebook.litho.sections;

import android.support.annotation.Nullable;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.LogTreePopulator;
import com.facebook.litho.PerfEvent;
import com.facebook.litho.sections.Section;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SectionsLogEventUtils {

   static String applyNewChangeSetSourceToString(int var0) {
      switch(var0) {
      case -1:
         return "none";
      case 0:
         return "setRoot";
      case 1:
         return "setRootAsync";
      case 2:
         return "updateState";
      case 3:
         return "updateStateAsync";
      default:
         throw new IllegalStateException("Unknown source");
      }
   }

   @Nullable
   public static PerfEvent getSectionsPerformanceEvent(ComponentContext var0, int var1, Section var2, Section var3) {
      ComponentsLogger var4 = var0.getLogger();
      if(var4 == null) {
         return null;
      } else {
         PerfEvent var6 = LogTreePopulator.populatePerfEventFromLogger(var0, var4, var4.newPerformanceEvent(var1));
         if(var6 != null) {
            String var5;
            if(var2 == null) {
               var5 = "null";
            } else {
               var5 = var2.getSimpleName();
            }

            var6.markerAnnotate("section_current", var5);
            if(var3 == null) {
               var5 = "null";
            } else {
               var5 = var3.getSimpleName();
            }

            var6.markerAnnotate("section_next", var5);
         }

         return var6;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ApplyNewChangeSet {

      int NONE = -1;
      int SET_ROOT = 0;
      int SET_ROOT_ASYNC = 1;
      int UPDATE_STATE = 2;
      int UPDATE_STATE_ASYNC = 3;

   }
}
