package com.facebook.react.views.modal;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.modal.ModalHostShadowNode;
import com.facebook.react.views.modal.ReactModalHostView;
import com.facebook.react.views.modal.RequestCloseEvent;
import com.facebook.react.views.modal.ShowEvent;
import java.util.Map;

@ReactModule(
   name = "RCTModalHostView"
)
public class ReactModalHostManager extends ViewGroupManager<ReactModalHostView> {

   protected static final String REACT_CLASS = "RCTModalHostView";


   protected void addEventEmitters(ThemedReactContext var1, final ReactModalHostView var2) {
      final EventDispatcher var3 = ((UIManagerModule)var1.getNativeModule(UIManagerModule.class)).getEventDispatcher();
      var2.setOnRequestCloseListener(new ReactModalHostView.OnRequestCloseListener() {
         public void onRequestClose(DialogInterface var1) {
            var3.dispatchEvent(new RequestCloseEvent(var2.getId()));
         }
      });
      var2.setOnShowListener(new OnShowListener() {
         public void onShow(DialogInterface var1) {
            var3.dispatchEvent(new ShowEvent(var2.getId()));
         }
      });
   }

   public LayoutShadowNode createShadowNodeInstance() {
      return new ModalHostShadowNode();
   }

   protected ReactModalHostView createViewInstance(ThemedReactContext var1) {
      return new ReactModalHostView(var1);
   }

   public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
      return MapBuilder.builder().put("topRequestClose", MapBuilder.of("registrationName", "onRequestClose")).put("topShow", MapBuilder.of("registrationName", "onShow")).build();
   }

   public String getName() {
      return "RCTModalHostView";
   }

   public Class<? extends LayoutShadowNode> getShadowNodeClass() {
      return ModalHostShadowNode.class;
   }

   protected void onAfterUpdateTransaction(ReactModalHostView var1) {
      super.onAfterUpdateTransaction(var1);
      var1.showOrUpdate();
   }

   public void onDropViewInstance(ReactModalHostView var1) {
      super.onDropViewInstance(var1);
      var1.onDropInstance();
   }

   @ReactProp(
      name = "animationType"
   )
   public void setAnimationType(ReactModalHostView var1, String var2) {
      var1.setAnimationType(var2);
   }

   @ReactProp(
      name = "hardwareAccelerated"
   )
   public void setHardwareAccelerated(ReactModalHostView var1, boolean var2) {
      var1.setHardwareAccelerated(var2);
   }

   @ReactProp(
      name = "transparent"
   )
   public void setTransparent(ReactModalHostView var1, boolean var2) {
      var1.setTransparent(var2);
   }
}
