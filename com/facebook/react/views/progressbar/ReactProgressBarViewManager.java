package com.facebook.react.views.progressbar;

import android.content.Context;
import android.widget.ProgressBar;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.progressbar.ProgressBarContainerView;
import com.facebook.react.views.progressbar.ProgressBarShadowNode;
import javax.annotation.Nullable;

@ReactModule(
   name = "AndroidProgressBar"
)
public class ReactProgressBarViewManager extends BaseViewManager<ProgressBarContainerView, ProgressBarShadowNode> {

   static final String DEFAULT_STYLE = "Normal";
   static final String PROP_ANIMATING = "animating";
   static final String PROP_INDETERMINATE = "indeterminate";
   static final String PROP_PROGRESS = "progress";
   static final String PROP_STYLE = "styleAttr";
   protected static final String REACT_CLASS = "AndroidProgressBar";
   private static Object sProgressBarCtorLock = new Object();


   public static ProgressBar createProgressBar(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   static int getStyleFromString(@Nullable String var0) {
      if(var0 == null) {
         throw new JSApplicationIllegalArgumentException("ProgressBar needs to have a style, null received");
      } else if(var0.equals("Horizontal")) {
         return 16842872;
      } else if(var0.equals("Small")) {
         return 16842873;
      } else if(var0.equals("Large")) {
         return 16842874;
      } else if(var0.equals("Inverse")) {
         return 16843399;
      } else if(var0.equals("SmallInverse")) {
         return 16843400;
      } else if(var0.equals("LargeInverse")) {
         return 16843401;
      } else if(var0.equals("Normal")) {
         return 16842871;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Unknown ProgressBar style: ");
         var1.append(var0);
         throw new JSApplicationIllegalArgumentException(var1.toString());
      }
   }

   public ProgressBarShadowNode createShadowNodeInstance() {
      return new ProgressBarShadowNode();
   }

   protected ProgressBarContainerView createViewInstance(ThemedReactContext var1) {
      return new ProgressBarContainerView(var1);
   }

   public String getName() {
      return "AndroidProgressBar";
   }

   public Class<ProgressBarShadowNode> getShadowNodeClass() {
      return ProgressBarShadowNode.class;
   }

   protected void onAfterUpdateTransaction(ProgressBarContainerView var1) {
      var1.apply();
   }

   @ReactProp(
      name = "animating"
   )
   public void setAnimating(ProgressBarContainerView var1, boolean var2) {
      var1.setAnimating(var2);
   }

   @ReactProp(
      customType = "Color",
      name = "color"
   )
   public void setColor(ProgressBarContainerView var1, @Nullable Integer var2) {
      var1.setColor(var2);
   }

   @ReactProp(
      name = "indeterminate"
   )
   public void setIndeterminate(ProgressBarContainerView var1, boolean var2) {
      var1.setIndeterminate(var2);
   }

   @ReactProp(
      name = "progress"
   )
   public void setProgress(ProgressBarContainerView var1, double var2) {
      var1.setProgress(var2);
   }

   @ReactProp(
      name = "styleAttr"
   )
   public void setStyle(ProgressBarContainerView var1, @Nullable String var2) {
      var1.setStyle(var2);
   }

   public void updateExtraData(ProgressBarContainerView var1, Object var2) {}
}
