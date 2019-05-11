package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.litho.PerfEvent;
import com.facebook.litho.TreeProps;
import java.util.Map;
import java.util.Set;

public interface ComponentsLogger {

   void cancelPerfEvent(PerfEvent var1);

   void emitMessage(ComponentsLogger.LogLevel var1, String var2);

   void emitMessage(ComponentsLogger.LogLevel var1, String var2, int var3);

   @Nullable
   Map<String, String> getExtraAnnotations(TreeProps var1);

   Set<String> getKeyCollisionStackTraceBlacklist();

   Set<String> getKeyCollisionStackTraceKeywords();

   boolean isTracing(PerfEvent var1);

   void logPerfEvent(PerfEvent var1);

   PerfEvent newPerformanceEvent(int var1);

   public static enum LogLevel {

      // $FF: synthetic field
      private static final ComponentsLogger.LogLevel[] $VALUES = new ComponentsLogger.LogLevel[]{WARNING, ERROR, FATAL};
      ERROR("ERROR", 1),
      FATAL("FATAL", 2),
      WARNING("WARNING", 0);


      private LogLevel(String var1, int var2) {}
   }
}
