package com.facebook.react.views.picker;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.picker.ReactPicker;
import com.facebook.react.views.picker.ReactPickerManager;

@ReactModule(
   name = "AndroidDialogPicker"
)
public class ReactDialogPickerManager extends ReactPickerManager {

   protected static final String REACT_CLASS = "AndroidDialogPicker";


   protected ReactPicker createViewInstance(ThemedReactContext var1) {
      return new ReactPicker(var1, 0);
   }

   public String getName() {
      return "AndroidDialogPicker";
   }
}
