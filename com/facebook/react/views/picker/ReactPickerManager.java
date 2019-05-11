package com.facebook.react.views.picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.picker.ReactPicker;
import com.facebook.react.views.picker.events.PickerItemSelectEvent;
import javax.annotation.Nullable;

public abstract class ReactPickerManager extends SimpleViewManager<ReactPicker> {

   protected void addEventEmitters(ThemedReactContext var1, ReactPicker var2) {
      var2.setOnSelectListener(new ReactPickerManager.PickerEventEmitter(var2, ((UIManagerModule)var1.getNativeModule(UIManagerModule.class)).getEventDispatcher()));
   }

   protected void onAfterUpdateTransaction(ReactPicker var1) {
      super.onAfterUpdateTransaction(var1);
      var1.updateStagedSelection();
   }

   @ReactProp(
      customType = "Color",
      name = "color"
   )
   public void setColor(ReactPicker var1, @Nullable Integer var2) {
      var1.setPrimaryColor(var2);
      ReactPickerManager.ReactPickerAdapter var3 = (ReactPickerManager.ReactPickerAdapter)var1.getAdapter();
      if(var3 != null) {
         var3.setPrimaryTextColor(var2);
      }

   }

   @ReactProp(
      defaultBoolean = true,
      name = "enabled"
   )
   public void setEnabled(ReactPicker var1, boolean var2) {
      var1.setEnabled(var2);
   }

   @ReactProp(
      name = "items"
   )
   public void setItems(ReactPicker var1, @Nullable ReadableArray var2) {
      if(var2 == null) {
         var1.setAdapter((SpinnerAdapter)null);
      } else {
         ReadableMap[] var4 = new ReadableMap[var2.size()];

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            var4[var3] = var2.getMap(var3);
         }

         ReactPickerManager.ReactPickerAdapter var5 = new ReactPickerManager.ReactPickerAdapter(var1.getContext(), var4);
         var5.setPrimaryTextColor(var1.getPrimaryColor());
         var1.setAdapter(var5);
      }
   }

   @ReactProp(
      name = "prompt"
   )
   public void setPrompt(ReactPicker var1, @Nullable String var2) {
      var1.setPrompt(var2);
   }

   @ReactProp(
      name = "selected"
   )
   public void setSelected(ReactPicker var1, int var2) {
      var1.setStagedSelection(var2);
   }

   static class ReactPickerAdapter extends ArrayAdapter<ReadableMap> {

      private final LayoutInflater mInflater;
      @Nullable
      private Integer mPrimaryTextColor;


      public ReactPickerAdapter(Context var1, ReadableMap[] var2) {
         super(var1, 0, var2);
         this.mInflater = (LayoutInflater)Assertions.assertNotNull(var1.getSystemService("layout_inflater"));
      }

      private View getView(int var1, View var2, ViewGroup var3, boolean var4) {
         ReadableMap var6 = (ReadableMap)this.getItem(var1);
         View var5 = var2;
         if(var2 == null) {
            if(var4) {
               var1 = 17367049;
            } else {
               var1 = 17367048;
            }

            var5 = this.mInflater.inflate(var1, var3, false);
         }

         TextView var7 = (TextView)var5;
         var7.setText(var6.getString("label"));
         if(!var4 && this.mPrimaryTextColor != null) {
            var7.setTextColor(this.mPrimaryTextColor.intValue());
            return var5;
         } else {
            if(var6.hasKey("color") && !var6.isNull("color")) {
               var7.setTextColor(var6.getInt("color"));
            }

            return var5;
         }
      }

      public View getDropDownView(int var1, View var2, ViewGroup var3) {
         return this.getView(var1, var2, var3, true);
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         return this.getView(var1, var2, var3, false);
      }

      public void setPrimaryTextColor(@Nullable Integer var1) {
         this.mPrimaryTextColor = var1;
         this.notifyDataSetChanged();
      }
   }

   static class PickerEventEmitter implements ReactPicker.OnSelectListener {

      private final EventDispatcher mEventDispatcher;
      private final ReactPicker mReactPicker;


      public PickerEventEmitter(ReactPicker var1, EventDispatcher var2) {
         this.mReactPicker = var1;
         this.mEventDispatcher = var2;
      }

      public void onItemSelected(int var1) {
         this.mEventDispatcher.dispatchEvent(new PickerItemSelectEvent(this.mReactPicker.getId(), var1));
      }
   }
}
