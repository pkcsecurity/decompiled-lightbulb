package com.facebook.react.devsupport;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import com.facebook.react.packagerconnection.PackagerConnectionSettings;

@VisibleForTesting
public class DevInternalSettings implements OnSharedPreferenceChangeListener, DeveloperSettings {

   private static final String PREFS_ANIMATIONS_DEBUG_KEY = "animations_debug";
   private static final String PREFS_FPS_DEBUG_KEY = "fps_debug";
   private static final String PREFS_HOT_MODULE_REPLACEMENT_KEY = "hot_module_replacement";
   private static final String PREFS_INSPECTOR_DEBUG_KEY = "inspector_debug";
   private static final String PREFS_JS_DEV_MODE_DEBUG_KEY = "js_dev_mode_debug";
   private static final String PREFS_JS_MINIFY_DEBUG_KEY = "js_minify_debug";
   private static final String PREFS_RELOAD_ON_JS_CHANGE_KEY = "reload_on_js_change";
   private static final String PREFS_REMOTE_JS_DEBUG_KEY = "remote_js_debug";
   private final DevInternalSettings.Listener mListener;
   private final PackagerConnectionSettings mPackagerConnectionSettings;
   private final SharedPreferences mPreferences;


   public DevInternalSettings(Context var1, DevInternalSettings.Listener var2) {
      this.mListener = var2;
      this.mPreferences = PreferenceManager.getDefaultSharedPreferences(var1);
      this.mPreferences.registerOnSharedPreferenceChangeListener(this);
      this.mPackagerConnectionSettings = new PackagerConnectionSettings(var1);
   }

   public PackagerConnectionSettings getPackagerConnectionSettings() {
      return this.mPackagerConnectionSettings;
   }

   public boolean isAnimationFpsDebugEnabled() {
      return this.mPreferences.getBoolean("animations_debug", false);
   }

   public boolean isElementInspectorEnabled() {
      return this.mPreferences.getBoolean("inspector_debug", false);
   }

   public boolean isFpsDebugEnabled() {
      return this.mPreferences.getBoolean("fps_debug", false);
   }

   public boolean isHotModuleReplacementEnabled() {
      return this.mPreferences.getBoolean("hot_module_replacement", false);
   }

   public boolean isJSDevModeEnabled() {
      return this.mPreferences.getBoolean("js_dev_mode_debug", true);
   }

   public boolean isJSMinifyEnabled() {
      return this.mPreferences.getBoolean("js_minify_debug", false);
   }

   public boolean isReloadOnJSChangeEnabled() {
      return this.mPreferences.getBoolean("reload_on_js_change", false);
   }

   public boolean isRemoteJSDebugEnabled() {
      return this.mPreferences.getBoolean("remote_js_debug", false);
   }

   public void onSharedPreferenceChanged(SharedPreferences var1, String var2) {
      if(this.mListener != null && ("fps_debug".equals(var2) || "reload_on_js_change".equals(var2) || "js_dev_mode_debug".equals(var2) || "js_minify_debug".equals(var2))) {
         this.mListener.onInternalSettingsChanged();
      }

   }

   public void setElementInspectorEnabled(boolean var1) {
      this.mPreferences.edit().putBoolean("inspector_debug", var1).apply();
   }

   public void setFpsDebugEnabled(boolean var1) {
      this.mPreferences.edit().putBoolean("fps_debug", var1).apply();
   }

   public void setHotModuleReplacementEnabled(boolean var1) {
      this.mPreferences.edit().putBoolean("hot_module_replacement", var1).apply();
   }

   public void setReloadOnJSChangeEnabled(boolean var1) {
      this.mPreferences.edit().putBoolean("reload_on_js_change", var1).apply();
   }

   public void setRemoteJSDebugEnabled(boolean var1) {
      this.mPreferences.edit().putBoolean("remote_js_debug", var1).apply();
   }

   public interface Listener {

      void onInternalSettingsChanged();
   }
}
