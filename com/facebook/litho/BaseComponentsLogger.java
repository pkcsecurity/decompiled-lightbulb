package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.TreeProps;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BaseComponentsLogger implements ComponentsLogger {

   private static final Set<String> sStackTraceBlacklist = new HashSet();
   private static final Set<String> sStackTraceKeywords = new HashSet();


   static {
      sStackTraceKeywords.add("Spec.java");
      sStackTraceKeywords.add("Activity.java");
   }

   @Nullable
   public Map<String, String> getExtraAnnotations(TreeProps var1) {
      return null;
   }

   public Set<String> getKeyCollisionStackTraceBlacklist() {
      return Collections.unmodifiableSet(sStackTraceBlacklist);
   }

   public Set<String> getKeyCollisionStackTraceKeywords() {
      return Collections.unmodifiableSet(sStackTraceKeywords);
   }
}
