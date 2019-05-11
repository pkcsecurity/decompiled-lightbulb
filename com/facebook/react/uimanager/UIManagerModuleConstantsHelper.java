package com.facebook.react.uimanager;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerModuleConstants;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

class UIManagerModuleConstantsHelper {

   static Map<String, Object> createConstants(UIManagerModule.ViewManagerResolver var0) {
      Map var1 = UIManagerModuleConstants.getConstants();
      var1.put("ViewManagerNames", var0.getViewManagerNames());
      return var1;
   }

   static Map<String, Object> createConstants(List<ViewManager> var0, @Nullable Map<String, Object> var1, @Nullable Map<String, Object> var2) {
      Map var3 = UIManagerModuleConstants.getConstants();
      Map var4 = UIManagerModuleConstants.getBubblingEventTypeConstants();
      Map var5 = UIManagerModuleConstants.getDirectEventTypeConstants();
      if(var1 != null) {
         var1.putAll(var4);
      }

      if(var2 != null) {
         var2.putAll(var5);
      }

      Iterator var10 = var0.iterator();

      while(var10.hasNext()) {
         ViewManager var7 = (ViewManager)var10.next();
         String var6 = var7.getName();
         SystraceMessage.beginSection(0L, "UIManagerModuleConstantsHelper.createConstants").arg("ViewManager", var6).arg("Lazy", Boolean.valueOf(false)).flush();

         try {
            Map var11 = createConstantsForViewManager(var7, (Map)null, (Map)null, var1, var2);
            if(!var11.isEmpty()) {
               var3.put(var6, var11);
            }
         } finally {
            Systrace.endSection(0L);
         }
      }

      var3.put("genericBubblingEventTypes", var4);
      var3.put("genericDirectEventTypes", var5);
      return var3;
   }

   static Map<String, Object> createConstantsForViewManager(ViewManager var0, @Nullable Map var1, @Nullable Map var2, @Nullable Map var3, @Nullable Map var4) {
      HashMap var5 = MapBuilder.newHashMap();
      Map var6 = var0.getExportedCustomBubblingEventTypeConstants();
      if(var6 != null) {
         recursiveMerge(var3, var6);
         recursiveMerge(var6, var1);
         var5.put("bubblingEventTypes", var6);
      } else if(var1 != null) {
         var5.put("bubblingEventTypes", var1);
      }

      var1 = var0.getExportedCustomDirectEventTypeConstants();
      if(var1 != null) {
         recursiveMerge(var4, var1);
         recursiveMerge(var1, var2);
         var5.put("directEventTypes", var1);
      } else if(var2 != null) {
         var5.put("directEventTypes", var2);
      }

      var1 = var0.getExportedViewConstants();
      if(var1 != null) {
         var5.put("Constants", var1);
      }

      var1 = var0.getCommandsMap();
      if(var1 != null) {
         var5.put("Commands", var1);
      }

      Map var7 = var0.getNativeProps();
      if(!var7.isEmpty()) {
         var5.put("NativeProps", var7);
      }

      return var5;
   }

   private static void recursiveMerge(@Nullable Map var0, @Nullable Map var1) {
      if(var0 != null && var1 != null) {
         if(!var1.isEmpty()) {
            Iterator var2 = var1.keySet().iterator();

            while(var2.hasNext()) {
               Object var3 = var2.next();
               Object var4 = var1.get(var3);
               Object var5 = var0.get(var3);
               if(var5 != null && var4 instanceof Map && var5 instanceof Map) {
                  recursiveMerge((Map)var5, (Map)var4);
               } else {
                  var0.put(var3, var4);
               }
            }

         }
      }
   }
}
