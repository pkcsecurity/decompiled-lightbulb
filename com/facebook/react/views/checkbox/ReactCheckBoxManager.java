package com.facebook.react.views.checkbox;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.checkbox.ReactCheckBox;
import com.facebook.react.views.checkbox.ReactCheckBoxEvent;

public class ReactCheckBoxManager extends SimpleViewManager<ReactCheckBox> {

   private static final OnCheckedChangeListener ON_CHECKED_CHANGE_LISTENER = new OnCheckedChangeListener() {
      public void onCheckedChanged(CompoundButton var1, boolean var2) {
         ((UIManagerModule)((ReactContext)var1.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactCheckBoxEvent(var1.getId(), var2));
      }
   };
   private static final String REACT_CLASS = "AndroidCheckBox";


   protected void addEventEmitters(ThemedReactContext var1, ReactCheckBox var2) {
      var2.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
   }

   protected ReactCheckBox createViewInstance(ThemedReactContext var1) {
      return new ReactCheckBox(var1);
   }

   public String getName() {
      return "AndroidCheckBox";
   }

   @ReactProp(
      defaultBoolean = true,
      name = "enabled"
   )
   public void setEnabled(ReactCheckBox var1, boolean var2) {
      var1.setEnabled(var2);
   }

   @ReactProp(
      name = "on"
   )
   public void setOn(ReactCheckBox var1, boolean var2) {
      var1.setOnCheckedChangeListener((OnCheckedChangeListener)null);
      var1.setOn(var2);
      var1.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
   }
}
