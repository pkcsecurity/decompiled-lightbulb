package com.facebook.react.views.picker;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.picker.ReactPicker;
import com.facebook.react.views.picker.ReactPickerManager;

@ReactModule(
   name = "AndroidDropdownPicker"
)
public class ReactDropdownPickerManager extends ReactPickerManager {

   protected static final String REACT_CLASS = "AndroidDropdownPicker";


   protected ReactPicker createViewInstance(ThemedReactContext var1) {
      return new ReactPicker(var1, 1);
   }

   public String getName() {
      return "AndroidDropdownPicker";
   }
}
