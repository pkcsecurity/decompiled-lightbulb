package com.facebook.litho;

import android.util.Log;
import com.facebook.litho.Component;
import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

   private static Map<String, Component> componentMap;
   private static Map<String, Component> emptyComponentMap;
   private static Map<String, Object> expressionContext;


   private ComponentManager() {
      componentMap = new HashMap();
      emptyComponentMap = new HashMap();
      expressionContext = new HashMap();
   }

   // $FF: synthetic method
   ComponentManager(Object var1) {
      this();
   }

   public static ComponentManager getInstance() {
      return ComponentManager.ComponentInstance.INSTANCE;
   }

   public Component getComponent(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("component size:");
      var2.append(componentMap.size());
      Log.i("ComponentManager", var2.toString());
      return (Component)componentMap.get(var1);
   }

   public Component getEmptyComponent(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("component size:");
      var2.append(componentMap.size());
      Log.i("ComponentManager", var2.toString());
      return (Component)emptyComponentMap.get(var1);
   }

   public Object getExpressContext(String var1) {
      return expressionContext.get(var1);
   }

   public void putComponent(String var1, Component var2) {
      componentMap.put(var1, var2);
   }

   public void putEmptyComponent(String var1, Component var2) {
      emptyComponentMap.put(var1, var2);
   }

   public void putExpressionContext(String var1, Object var2) {
      expressionContext.put(var1, var2);
   }

   public void release() {
      componentMap.clear();
      emptyComponentMap.clear();
      expressionContext.clear();
   }

   public void releaseComponent(String var1) {
      if(componentMap != null) {
         componentMap.remove(var1);
      }

      if(emptyComponentMap != null) {
         emptyComponentMap.remove(var1);
      }

   }

   static class ComponentInstance {

      private static final ComponentManager INSTANCE = new ComponentManager(null);


   }
}
