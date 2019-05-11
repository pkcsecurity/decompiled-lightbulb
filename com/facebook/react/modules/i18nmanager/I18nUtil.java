package com.facebook.react.modules.i18nmanager;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.support.v4.text.TextUtilsCompat;
import java.util.Locale;

public class I18nUtil {

   private static final String KEY_FOR_PERFS_MAKE_RTL_FLIP_LEFT_AND_RIGHT_STYLES = "RCTI18nUtil_makeRTLFlipLeftAndRightStyles";
   private static final String KEY_FOR_PREFS_ALLOWRTL = "RCTI18nUtil_allowRTL";
   private static final String KEY_FOR_PREFS_FORCERTL = "RCTI18nUtil_forceRTL";
   private static final String SHARED_PREFS_NAME = "com.facebook.react.modules.i18nmanager.I18nUtil";
   private static I18nUtil sharedI18nUtilInstance;


   public static I18nUtil getInstance() {
      if(sharedI18nUtilInstance == null) {
         sharedI18nUtilInstance = new I18nUtil();
      }

      return sharedI18nUtilInstance;
   }

   private boolean isDevicePreferredLanguageRTL() {
      return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
   }

   private boolean isPrefSet(Context var1, String var2, boolean var3) {
      return var1.getSharedPreferences("com.facebook.react.modules.i18nmanager.I18nUtil", 0).getBoolean(var2, var3);
   }

   private boolean isRTLAllowed(Context var1) {
      return this.isPrefSet(var1, "RCTI18nUtil_allowRTL", true);
   }

   private boolean isRTLForced(Context var1) {
      return this.isPrefSet(var1, "RCTI18nUtil_forceRTL", false);
   }

   private void setPref(Context var1, String var2, boolean var3) {
      Editor var4 = var1.getSharedPreferences("com.facebook.react.modules.i18nmanager.I18nUtil", 0).edit();
      var4.putBoolean(var2, var3);
      var4.apply();
   }

   public void allowRTL(Context var1, boolean var2) {
      this.setPref(var1, "RCTI18nUtil_allowRTL", var2);
   }

   public boolean doLeftAndRightSwapInRTL(Context var1) {
      return this.isPrefSet(var1, "RCTI18nUtil_makeRTLFlipLeftAndRightStyles", true);
   }

   public void forceRTL(Context var1, boolean var2) {
      this.setPref(var1, "RCTI18nUtil_forceRTL", var2);
   }

   public boolean isRTL(Context var1) {
      return this.isRTLForced(var1)?true:this.isRTLAllowed(var1) && this.isDevicePreferredLanguageRTL();
   }

   public void swapLeftAndRightInRTL(Context var1, boolean var2) {
      this.setPref(var1, "RCTI18nUtil_makeRTLFlipLeftAndRightStyles", var2);
   }
}
