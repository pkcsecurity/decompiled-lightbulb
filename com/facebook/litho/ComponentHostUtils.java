package com.facebook.litho;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import com.facebook.litho.ImageContent;
import com.facebook.litho.MountItem;
import com.facebook.litho.NodeInfo;
import com.facebook.litho.TextContent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ComponentHostUtils {

   static <T extends Object> boolean existsScrapItemAt(int var0, SparseArrayCompat<T> var1) {
      return var1 != null && var1.get(var0) != null;
   }

   static List<?> extractContent(SparseArrayCompat<MountItem> var0) {
      int var2 = var0.size();
      int var1 = 0;
      if(var2 == 1) {
         return Collections.singletonList(((MountItem)var0.valueAt(0)).getMountableContent());
      } else {
         ArrayList var3;
         for(var3 = new ArrayList(var2); var1 < var2; ++var1) {
            var3.add(((MountItem)var0.valueAt(var1)).getMountableContent());
         }

         return var3;
      }
   }

   static ImageContent extractImageContent(List<?> var0) {
      int var2 = var0.size();
      int var1 = 0;
      if(var2 == 1) {
         Object var5 = var0.get(0);
         return var5 instanceof ImageContent?(ImageContent)var5:ImageContent.EMPTY;
      } else {
         final ArrayList var3;
         for(var3 = new ArrayList(); var1 < var2; ++var1) {
            Object var4 = var0.get(var1);
            if(var4 instanceof ImageContent) {
               var3.addAll(((ImageContent)var4).getImageItems());
            }
         }

         return new ImageContent() {
            public List<Drawable> getImageItems() {
               return var3;
            }
         };
      }
   }

   static TextContent extractTextContent(List<?> var0) {
      int var2 = var0.size();
      int var1 = 0;
      if(var2 == 1) {
         Object var5 = var0.get(0);
         return var5 instanceof TextContent?(TextContent)var5:TextContent.EMPTY;
      } else {
         final ArrayList var3;
         for(var3 = new ArrayList(); var1 < var2; ++var1) {
            Object var4 = var0.get(var1);
            if(var4 instanceof TextContent) {
               var3.addAll(((TextContent)var4).getTextItems());
            }
         }

         return new TextContent() {
            public List<CharSequence> getTextItems() {
               return var3;
            }
         };
      }
   }

   static void maybeInvalidateAccessibilityState(MountItem var0) {
      if(var0.isAccessible()) {
         var0.getHost().invalidateAccessibilityState();
      }

   }

   static void maybeSetDrawableState(View var0, Drawable var1, int var2, NodeInfo var3) {
      boolean var4;
      if((var3 == null || !var3.hasTouchEventHandlers()) && !MountItem.isDuplicateParentState(var2)) {
         var4 = false;
      } else {
         var4 = true;
      }

      if(var4 && var1.isStateful()) {
         var1.setState(var0.getDrawableState());
      }

   }

   static void mountDrawable(View var0, Drawable var1, Rect var2, int var3, NodeInfo var4) {
      boolean var5;
      if(var0.getVisibility() == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      var1.setVisible(var5, false);
      var1.setCallback(var0);
      maybeSetDrawableState(var0, var1, var3, var4);
      var0.invalidate(var2);
   }

   static <T extends Object> void moveItem(int var0, int var1, SparseArrayCompat<T> var2, SparseArrayCompat<T> var3) {
      Object var5;
      if(existsScrapItemAt(var0, var3)) {
         Object var4 = var3.get(var0);
         var3.remove(var0);
         var5 = var4;
      } else {
         var5 = var2.get(var0);
         var2.remove(var0);
      }

      var2.put(var1, var5);
   }

   static <T extends Object> void removeItem(int var0, SparseArrayCompat<T> var1, SparseArrayCompat<T> var2) {
      if(existsScrapItemAt(var0, var2)) {
         var2.remove(var0);
      } else {
         var1.remove(var0);
      }
   }

   static <T extends Object> void scrapItemAt(int var0, SparseArrayCompat<T> var1, SparseArrayCompat<T> var2) {
      if(var1 != null) {
         if(var2 != null) {
            Object var3 = var1.get(var0);
            if(var3 != null) {
               var2.put(var0, var3);
            }

         }
      }
   }
}
