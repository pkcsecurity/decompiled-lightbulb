package com.facebook.react.uimanager;

import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.RadioButton;

class AccessibilityHelper {

   private static final String BUTTON = "button";
   private static final AccessibilityDelegate BUTTON_DELEGATE = new AccessibilityDelegate() {
      public void onInitializeAccessibilityEvent(View var1, AccessibilityEvent var2) {
         super.onInitializeAccessibilityEvent(var1, var2);
         var2.setClassName(Button.class.getName());
      }
      public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfo var2) {
         super.onInitializeAccessibilityNodeInfo(var1, var2);
         var2.setClassName(Button.class.getName());
      }
   };
   private static final String RADIOBUTTON_CHECKED = "radiobutton_checked";
   private static final AccessibilityDelegate RADIOBUTTON_CHECKED_DELEGATE = new AccessibilityDelegate() {
      public void onInitializeAccessibilityEvent(View var1, AccessibilityEvent var2) {
         super.onInitializeAccessibilityEvent(var1, var2);
         var2.setClassName(RadioButton.class.getName());
         var2.setChecked(true);
      }
      public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfo var2) {
         super.onInitializeAccessibilityNodeInfo(var1, var2);
         var2.setClassName(RadioButton.class.getName());
         var2.setCheckable(true);
         var2.setChecked(true);
      }
   };
   private static final String RADIOBUTTON_UNCHECKED = "radiobutton_unchecked";
   private static final AccessibilityDelegate RADIOBUTTON_UNCHECKED_DELEGATE = new AccessibilityDelegate() {
      public void onInitializeAccessibilityEvent(View var1, AccessibilityEvent var2) {
         super.onInitializeAccessibilityEvent(var1, var2);
         var2.setClassName(RadioButton.class.getName());
         var2.setChecked(false);
      }
      public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfo var2) {
         super.onInitializeAccessibilityNodeInfo(var1, var2);
         var2.setClassName(RadioButton.class.getName());
         var2.setCheckable(true);
         var2.setChecked(false);
      }
   };


   public static void sendAccessibilityEvent(View var0, int var1) {
      var0.sendAccessibilityEvent(var1);
   }

   public static void updateAccessibilityComponentType(View var0, String var1) {
      if(var1 == null) {
         var0.setAccessibilityDelegate((AccessibilityDelegate)null);
      } else {
         byte var2 = -1;
         int var3 = var1.hashCode();
         if(var3 != -1377687758) {
            if(var3 != -1320494052) {
               if(var3 == -714126251 && var1.equals("radiobutton_checked")) {
                  var2 = 1;
               }
            } else if(var1.equals("radiobutton_unchecked")) {
               var2 = 2;
            }
         } else if(var1.equals("button")) {
            var2 = 0;
         }

         switch(var2) {
         case 0:
            var0.setAccessibilityDelegate(BUTTON_DELEGATE);
            return;
         case 1:
            var0.setAccessibilityDelegate(RADIOBUTTON_CHECKED_DELEGATE);
            return;
         case 2:
            var0.setAccessibilityDelegate(RADIOBUTTON_UNCHECKED_DELEGATE);
            return;
         default:
            var0.setAccessibilityDelegate((AccessibilityDelegate)null);
         }
      }
   }
}
