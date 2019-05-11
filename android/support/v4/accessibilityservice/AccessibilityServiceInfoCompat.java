package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class AccessibilityServiceInfoCompat {

   public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
   public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
   public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
   public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
   public static final int FEEDBACK_ALL_MASK = -1;
   public static final int FEEDBACK_BRAILLE = 32;
   public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
   public static final int FLAG_REPORT_VIEW_IDS = 16;
   public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
   public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
   public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;


   @NonNull
   public static String capabilityToString(int var0) {
      if(var0 != 4) {
         if(var0 != 8) {
            switch(var0) {
            case 1:
               return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
            case 2:
               return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
            default:
               return "UNKNOWN";
            }
         } else {
            return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
         }
      } else {
         return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
      }
   }

   @NonNull
   public static String feedbackTypeToString(int var0) {
      StringBuilder var2 = new StringBuilder();
      var2.append("[");

      while(var0 > 0) {
         int var1 = 1 << Integer.numberOfTrailingZeros(var0);
         var0 &= ~var1;
         if(var2.length() > 1) {
            var2.append(", ");
         }

         if(var1 != 4) {
            if(var1 != 8) {
               if(var1 != 16) {
                  switch(var1) {
                  case 1:
                     var2.append("FEEDBACK_SPOKEN");
                     break;
                  case 2:
                     var2.append("FEEDBACK_HAPTIC");
                  }
               } else {
                  var2.append("FEEDBACK_GENERIC");
               }
            } else {
               var2.append("FEEDBACK_VISUAL");
            }
         } else {
            var2.append("FEEDBACK_AUDIBLE");
         }
      }

      var2.append("]");
      return var2.toString();
   }

   @Nullable
   public static String flagToString(int var0) {
      if(var0 != 4) {
         if(var0 != 8) {
            if(var0 != 16) {
               if(var0 != 32) {
                  switch(var0) {
                  case 1:
                     return "DEFAULT";
                  case 2:
                     return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
                  default:
                     return null;
                  }
               } else {
                  return "FLAG_REQUEST_FILTER_KEY_EVENTS";
               }
            } else {
               return "FLAG_REPORT_VIEW_IDS";
            }
         } else {
            return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
         }
      } else {
         return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
      }
   }

   public static int getCapabilities(@NonNull AccessibilityServiceInfo var0) {
      return VERSION.SDK_INT >= 18?var0.getCapabilities():(var0.getCanRetrieveWindowContent()?1:0);
   }

   @Nullable
   public static String loadDescription(@NonNull AccessibilityServiceInfo var0, @NonNull PackageManager var1) {
      return VERSION.SDK_INT >= 16?var0.loadDescription(var1):var0.getDescription();
   }
}
