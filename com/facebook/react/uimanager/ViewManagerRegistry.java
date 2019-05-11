package com.facebook.react.uimanager;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public final class ViewManagerRegistry {

   @Nullable
   private final UIManagerModule.ViewManagerResolver mViewManagerResolver;
   private final Map<String, ViewManager> mViewManagers;


   public ViewManagerRegistry(UIManagerModule.ViewManagerResolver var1) {
      this.mViewManagers = MapBuilder.newHashMap();
      this.mViewManagerResolver = var1;
   }

   public ViewManagerRegistry(List<ViewManager> var1) {
      HashMap var2 = MapBuilder.newHashMap();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         ViewManager var3 = (ViewManager)var4.next();
         var2.put(var3.getName(), var3);
      }

      this.mViewManagers = var2;
      this.mViewManagerResolver = null;
   }

   public ViewManagerRegistry(Map<String, ViewManager> var1) {
      if(var1 == null) {
         var1 = MapBuilder.newHashMap();
      }

      this.mViewManagers = (Map)var1;
      this.mViewManagerResolver = null;
   }

   public ViewManager get(String var1) {
      ViewManager var2 = (ViewManager)this.mViewManagers.get(var1);
      if(var2 != null) {
         return var2;
      } else {
         if(this.mViewManagerResolver != null) {
            var2 = this.mViewManagerResolver.getViewManager(var1);
            if(var2 != null) {
               this.mViewManagers.put(var1, var2);
               return var2;
            }
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("No ViewManager defined for class ");
         var3.append(var1);
         throw new IllegalViewOperationException(var3.toString());
      }
   }
}
