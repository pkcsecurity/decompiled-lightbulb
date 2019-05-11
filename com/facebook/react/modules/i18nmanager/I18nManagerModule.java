package com.facebook.react.modules.i18nmanager;

import android.content.Context;
import com.facebook.react.bridge.ContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ReactModule(
   name = "I18nManager"
)
public class I18nManagerModule extends ContextBaseJavaModule {

   private final I18nUtil sharedI18nUtilInstance = I18nUtil.getInstance();


   public I18nManagerModule(Context var1) {
      super(var1);
   }

   @ReactMethod
   public void allowRTL(boolean var1) {
      this.sharedI18nUtilInstance.allowRTL(this.getContext(), var1);
   }

   @ReactMethod
   public void forceRTL(boolean var1) {
      this.sharedI18nUtilInstance.forceRTL(this.getContext(), var1);
   }

   public Map<String, Object> getConstants() {
      Context var1 = this.getContext();
      Locale var2 = var1.getResources().getConfiguration().locale;
      HashMap var3 = MapBuilder.newHashMap();
      var3.put("isRTL", Boolean.valueOf(this.sharedI18nUtilInstance.isRTL(var1)));
      var3.put("doLeftAndRightSwapInRTL", Boolean.valueOf(this.sharedI18nUtilInstance.doLeftAndRightSwapInRTL(var1)));
      var3.put("localeIdentifier", var2.toString());
      return var3;
   }

   public String getName() {
      return "I18nManager";
   }

   @ReactMethod
   public void swapLeftAndRightInRTL(boolean var1) {
      this.sharedI18nUtilInstance.swapLeftAndRightInRTL(this.getContext(), var1);
   }
}
