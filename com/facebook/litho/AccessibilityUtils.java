package com.facebook.litho;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat;
import android.view.accessibility.AccessibilityManager;
import java.util.Iterator;
import java.util.List;

public class AccessibilityUtils {

   private static volatile boolean cachedIsAccessibilityEnabled;
   private static volatile boolean isCachedIsAccessibilityEnabledSet;
   public static boolean isCachingEnabled;


   public static boolean enabledServiceCanFocusAndRetrieveWindowContent(AccessibilityManager var0) {
      List var2 = var0.getEnabledAccessibilityServiceList(-1);
      if(var2 == null) {
         return false;
      } else {
         Iterator var3 = var2.iterator();

         AccessibilityServiceInfo var1;
         do {
            if(!var3.hasNext()) {
               return false;
            }

            var1 = (AccessibilityServiceInfo)var3.next();
         } while((var1.eventTypes & '\u8000') != '\u8000' || (AccessibilityServiceInfoCompat.getCapabilities(var1) & 1) != 1);

         return true;
      }
   }

   public static void invalidateCachedIsAccessibilityEnabled() {
      synchronized(AccessibilityUtils.class){}

      try {
         isCachedIsAccessibilityEnabledSet = false;
      } finally {
         ;
      }

   }

   public static boolean isAccessibilityEnabled(Context var0) {
      if(isCachingEnabled) {
         if(!isCachedIsAccessibilityEnabledSet) {
            updateCachedIsAccessibilityEnabled((AccessibilityManager)var0.getSystemService("accessibility"));
         }

         return cachedIsAccessibilityEnabled;
      } else {
         return isAccessibilityEnabled((AccessibilityManager)var0.getSystemService("accessibility"));
      }
   }

   public static boolean isAccessibilityEnabled(AccessibilityManager var0) {
      if(isCachingEnabled) {
         if(!isCachedIsAccessibilityEnabledSet) {
            updateCachedIsAccessibilityEnabled(var0);
         }

         return cachedIsAccessibilityEnabled;
      } else {
         return Boolean.getBoolean("is_accessibility_enabled") || isRunningApplicableAccessibilityService(var0);
      }
   }

   public static boolean isRunningApplicableAccessibilityService(AccessibilityManager var0) {
      boolean var1 = false;
      if(var0 == null) {
         return false;
      } else if(!var0.isEnabled()) {
         return false;
      } else if(!isCachingEnabled) {
         return var0.isTouchExplorationEnabled();
      } else {
         if(var0.isTouchExplorationEnabled() || enabledServiceCanFocusAndRetrieveWindowContent(var0)) {
            var1 = true;
         }

         return var1;
      }
   }

   private static void updateCachedIsAccessibilityEnabled(AccessibilityManager param0) {
      // $FF: Couldn't be decompiled
   }
}
