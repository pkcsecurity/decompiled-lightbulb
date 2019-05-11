package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.ViewManagersPropertyCache;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ViewManagerPropertyUpdater {

   private static final Map<Class<?>, ViewManagerPropertyUpdater.ShadowNodeSetter<?>> SHADOW_NODE_SETTER_MAP = new HashMap();
   private static final String TAG = "ViewManagerPropertyUpdater";
   private static final Map<Class<?>, ViewManagerPropertyUpdater.ViewManagerSetter<?, ?>> VIEW_MANAGER_SETTER_MAP = new HashMap();


   public static void clear() {
      ViewManagersPropertyCache.clear();
      VIEW_MANAGER_SETTER_MAP.clear();
      SHADOW_NODE_SETTER_MAP.clear();
   }

   private static <T extends Object> T findGeneratedSetter(Class<?> var0) {
      String var1 = var0.getName();

      StringBuilder var2;
      try {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append("$$PropsSetter");
         Object var6 = Class.forName(var2.toString()).newInstance();
         return var6;
      } catch (ClassNotFoundException var3) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Could not find generated setter for ");
         var5.append(var0);
         FLog.w("ViewManagerPropertyUpdater", var5.toString());
         return null;
      } catch (IllegalAccessException var4) {
         var2 = new StringBuilder();
         var2.append("Unable to instantiate methods getter for ");
         var2.append(var1);
         throw new RuntimeException(var2.toString(), var4);
      }
   }

   private static <T extends ViewManager, V extends View> ViewManagerPropertyUpdater.ViewManagerSetter<T, V> findManagerSetter(Class<? extends ViewManager> var0) {
      ViewManagerPropertyUpdater.ViewManagerSetter var2 = (ViewManagerPropertyUpdater.ViewManagerSetter)VIEW_MANAGER_SETTER_MAP.get(var0);
      Object var1 = var2;
      if(var2 == null) {
         var2 = (ViewManagerPropertyUpdater.ViewManagerSetter)findGeneratedSetter(var0);
         var1 = var2;
         if(var2 == null) {
            var1 = new ViewManagerPropertyUpdater.FallbackViewManagerSetter(var0, null);
         }

         VIEW_MANAGER_SETTER_MAP.put(var0, var1);
      }

      return (ViewManagerPropertyUpdater.ViewManagerSetter)var1;
   }

   private static <T extends Object & ReactShadowNode> ViewManagerPropertyUpdater.ShadowNodeSetter<T> findNodeSetter(Class<? extends ReactShadowNode> var0) {
      ViewManagerPropertyUpdater.ShadowNodeSetter var2 = (ViewManagerPropertyUpdater.ShadowNodeSetter)SHADOW_NODE_SETTER_MAP.get(var0);
      Object var1 = var2;
      if(var2 == null) {
         var2 = (ViewManagerPropertyUpdater.ShadowNodeSetter)findGeneratedSetter(var0);
         var1 = var2;
         if(var2 == null) {
            var1 = new ViewManagerPropertyUpdater.FallbackShadowNodeSetter(var0, null);
         }

         SHADOW_NODE_SETTER_MAP.put(var0, var1);
      }

      return (ViewManagerPropertyUpdater.ShadowNodeSetter)var1;
   }

   public static Map<String, String> getNativeProps(Class<? extends ViewManager> var0, Class<? extends ReactShadowNode> var1) {
      HashMap var2 = new HashMap();
      findManagerSetter(var0).getProperties(var2);
      findNodeSetter(var1).getProperties(var2);
      return var2;
   }

   public static <T extends Object & ReactShadowNode> void updateProps(T var0, ReactStylesDiffMap var1) {
      ViewManagerPropertyUpdater.ShadowNodeSetter var2 = findNodeSetter(var0.getClass());
      ReadableMapKeySetIterator var3 = var1.mBackingMap.keySetIterator();

      while(var3.hasNextKey()) {
         var2.setProperty(var0, var3.nextKey(), var1);
      }

   }

   public static <T extends ViewManager, V extends View> void updateProps(T var0, V var1, ReactStylesDiffMap var2) {
      ViewManagerPropertyUpdater.ViewManagerSetter var3 = findManagerSetter(var0.getClass());
      ReadableMapKeySetIterator var4 = var2.mBackingMap.keySetIterator();

      while(var4.hasNextKey()) {
         var3.setProperty(var0, var1, var4.nextKey(), var2);
      }

   }

   public interface ShadowNodeSetter<T extends Object & ReactShadowNode> extends ViewManagerPropertyUpdater.Settable {

      void setProperty(T var1, String var2, ReactStylesDiffMap var3);
   }

   static class FallbackShadowNodeSetter<T extends Object & ReactShadowNode> implements ViewManagerPropertyUpdater.ShadowNodeSetter<T> {

      private final Map<String, ViewManagersPropertyCache.PropSetter> mPropSetters;


      private FallbackShadowNodeSetter(Class<? extends ReactShadowNode> var1) {
         this.mPropSetters = ViewManagersPropertyCache.getNativePropSettersForShadowNodeClass(var1);
      }

      // $FF: synthetic method
      FallbackShadowNodeSetter(Class var1, Object var2) {
         this(var1);
      }

      public void getProperties(Map<String, String> var1) {
         Iterator var2 = this.mPropSetters.values().iterator();

         while(var2.hasNext()) {
            ViewManagersPropertyCache.PropSetter var3 = (ViewManagersPropertyCache.PropSetter)var2.next();
            var1.put(var3.getPropName(), var3.getPropType());
         }

      }

      public void setProperty(ReactShadowNode var1, String var2, ReactStylesDiffMap var3) {
         ViewManagersPropertyCache.PropSetter var4 = (ViewManagersPropertyCache.PropSetter)this.mPropSetters.get(var2);
         if(var4 != null) {
            var4.updateShadowNodeProp(var1, var3);
         }

      }
   }

   static class FallbackViewManagerSetter<T extends ViewManager, V extends View> implements ViewManagerPropertyUpdater.ViewManagerSetter<T, V> {

      private final Map<String, ViewManagersPropertyCache.PropSetter> mPropSetters;


      private FallbackViewManagerSetter(Class<? extends ViewManager> var1) {
         this.mPropSetters = ViewManagersPropertyCache.getNativePropSettersForViewManagerClass(var1);
      }

      // $FF: synthetic method
      FallbackViewManagerSetter(Class var1, Object var2) {
         this(var1);
      }

      public void getProperties(Map<String, String> var1) {
         Iterator var2 = this.mPropSetters.values().iterator();

         while(var2.hasNext()) {
            ViewManagersPropertyCache.PropSetter var3 = (ViewManagersPropertyCache.PropSetter)var2.next();
            var1.put(var3.getPropName(), var3.getPropType());
         }

      }

      public void setProperty(T var1, V var2, String var3, ReactStylesDiffMap var4) {
         ViewManagersPropertyCache.PropSetter var5 = (ViewManagersPropertyCache.PropSetter)this.mPropSetters.get(var3);
         if(var5 != null) {
            var5.updateViewProp(var1, var2, var4);
         }

      }
   }

   public interface Settable {

      void getProperties(Map<String, String> var1);
   }

   public interface ViewManagerSetter<T extends ViewManager, V extends View> extends ViewManagerPropertyUpdater.Settable {

      void setProperty(T var1, V var2, String var3, ReactStylesDiffMap var4);
   }
}
