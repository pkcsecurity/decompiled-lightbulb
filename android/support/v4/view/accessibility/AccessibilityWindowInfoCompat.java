package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.accessibility.AccessibilityWindowInfo;

public class AccessibilityWindowInfoCompat {

   public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
   public static final int TYPE_APPLICATION = 1;
   public static final int TYPE_INPUT_METHOD = 2;
   public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
   public static final int TYPE_SYSTEM = 3;
   private static final int UNDEFINED = -1;
   private Object mInfo;


   private AccessibilityWindowInfoCompat(Object var1) {
      this.mInfo = var1;
   }

   public static AccessibilityWindowInfoCompat obtain() {
      return VERSION.SDK_INT >= 21?wrapNonNullInstance(AccessibilityWindowInfo.obtain()):null;
   }

   public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat var0) {
      return VERSION.SDK_INT >= 21?(var0 == null?null:wrapNonNullInstance(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo)var0.mInfo))):null;
   }

   private static String typeToString(int var0) {
      switch(var0) {
      case 1:
         return "TYPE_APPLICATION";
      case 2:
         return "TYPE_INPUT_METHOD";
      case 3:
         return "TYPE_SYSTEM";
      case 4:
         return "TYPE_ACCESSIBILITY_OVERLAY";
      default:
         return "<UNKNOWN>";
      }
   }

   static AccessibilityWindowInfoCompat wrapNonNullInstance(Object var0) {
      return var0 != null?new AccessibilityWindowInfoCompat(var0):null;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         AccessibilityWindowInfoCompat var2 = (AccessibilityWindowInfoCompat)var1;
         if(this.mInfo == null) {
            if(var2.mInfo != null) {
               return false;
            }
         } else if(!this.mInfo.equals(var2.mInfo)) {
            return false;
         }

         return true;
      }
   }

   public AccessibilityNodeInfoCompat getAnchor() {
      return VERSION.SDK_INT >= 24?AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getAnchor()):null;
   }

   public void getBoundsInScreen(Rect var1) {
      if(VERSION.SDK_INT >= 21) {
         ((AccessibilityWindowInfo)this.mInfo).getBoundsInScreen(var1);
      }

   }

   public AccessibilityWindowInfoCompat getChild(int var1) {
      return VERSION.SDK_INT >= 21?wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getChild(var1)):null;
   }

   public int getChildCount() {
      return VERSION.SDK_INT >= 21?((AccessibilityWindowInfo)this.mInfo).getChildCount():0;
   }

   public int getId() {
      return VERSION.SDK_INT >= 21?((AccessibilityWindowInfo)this.mInfo).getId():-1;
   }

   public int getLayer() {
      return VERSION.SDK_INT >= 21?((AccessibilityWindowInfo)this.mInfo).getLayer():-1;
   }

   public AccessibilityWindowInfoCompat getParent() {
      return VERSION.SDK_INT >= 21?wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getParent()):null;
   }

   public AccessibilityNodeInfoCompat getRoot() {
      return VERSION.SDK_INT >= 21?AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getRoot()):null;
   }

   public CharSequence getTitle() {
      return VERSION.SDK_INT >= 24?((AccessibilityWindowInfo)this.mInfo).getTitle():null;
   }

   public int getType() {
      return VERSION.SDK_INT >= 21?((AccessibilityWindowInfo)this.mInfo).getType():-1;
   }

   public int hashCode() {
      return this.mInfo == null?0:this.mInfo.hashCode();
   }

   public boolean isAccessibilityFocused() {
      return VERSION.SDK_INT >= 21?((AccessibilityWindowInfo)this.mInfo).isAccessibilityFocused():true;
   }

   public boolean isActive() {
      return VERSION.SDK_INT >= 21?((AccessibilityWindowInfo)this.mInfo).isActive():true;
   }

   public boolean isFocused() {
      return VERSION.SDK_INT >= 21?((AccessibilityWindowInfo)this.mInfo).isFocused():true;
   }

   public void recycle() {
      if(VERSION.SDK_INT >= 21) {
         ((AccessibilityWindowInfo)this.mInfo).recycle();
      }

   }

   public String toString() {
      StringBuilder var3 = new StringBuilder();
      Rect var4 = new Rect();
      this.getBoundsInScreen(var4);
      var3.append("AccessibilityWindowInfo[");
      var3.append("id=");
      var3.append(this.getId());
      var3.append(", type=");
      var3.append(typeToString(this.getType()));
      var3.append(", layer=");
      var3.append(this.getLayer());
      var3.append(", bounds=");
      var3.append(var4);
      var3.append(", focused=");
      var3.append(this.isFocused());
      var3.append(", active=");
      var3.append(this.isActive());
      var3.append(", hasParent=");
      AccessibilityWindowInfoCompat var5 = this.getParent();
      boolean var2 = false;
      boolean var1;
      if(var5 != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      var3.append(var1);
      var3.append(", hasChildren=");
      var1 = var2;
      if(this.getChildCount() > 0) {
         var1 = true;
      }

      var3.append(var1);
      var3.append(']');
      return var3.toString();
   }
}
