package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerPropertyUpdater;
import com.facebook.react.uimanager.annotations.ReactPropertyHolder;
import java.util.Map;
import javax.annotation.Nullable;

@ReactPropertyHolder
public abstract class ViewManager<T extends View, C extends Object & ReactShadowNode> extends BaseJavaModule {

   public void addEventEmitters(ThemedReactContext var1, T var2) {}

   public C createShadowNodeInstance() {
      throw new RuntimeException("ViewManager subclasses must implement createShadowNodeInstance()");
   }

   public C createShadowNodeInstance(ReactApplicationContext var1) {
      return this.createShadowNodeInstance();
   }

   public final T createView(ThemedReactContext var1, JSResponderHandler var2) {
      View var3 = this.createViewInstance(var1);
      this.addEventEmitters(var1, var3);
      if(var3 instanceof ReactInterceptingViewGroup) {
         ((ReactInterceptingViewGroup)var3).setOnInterceptTouchEventListener(var2);
      }

      return var3;
   }

   public abstract T createViewInstance(ThemedReactContext var1);

   @Nullable
   public Map<String, Integer> getCommandsMap() {
      return null;
   }

   @Nullable
   public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
      return null;
   }

   @Nullable
   public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
      return null;
   }

   @Nullable
   public Map<String, Object> getExportedViewConstants() {
      return null;
   }

   public abstract String getName();

   public Map<String, String> getNativeProps() {
      return ViewManagerPropertyUpdater.getNativeProps(this.getClass(), this.getShadowNodeClass());
   }

   public abstract Class<? extends C> getShadowNodeClass();

   protected void onAfterUpdateTransaction(T var1) {}

   public void onDropViewInstance(T var1) {}

   public void receiveCommand(T var1, int var2, @Nullable ReadableArray var3) {}

   public abstract void updateExtraData(T var1, Object var2);

   public final void updateProperties(T var1, ReactStylesDiffMap var2) {
      ViewManagerPropertyUpdater.updateProps(this, var1, var2);
      this.onAfterUpdateTransaction(var1);
   }
}
