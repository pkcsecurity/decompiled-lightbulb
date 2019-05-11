package com.facebook.react.devsupport;

import com.facebook.react.bridge.DefaultNativeModuleCallExceptionHandler;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.devsupport.interfaces.DevOptionHandler;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.ErrorCustomizer;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.devsupport.interfaces.StackFrame;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import java.io.File;
import javax.annotation.Nullable;

public class DisabledDevSupportManager implements DevSupportManager {

   private final DefaultNativeModuleCallExceptionHandler mDefaultNativeModuleCallExceptionHandler = new DefaultNativeModuleCallExceptionHandler();


   public void addCustomDevOption(String var1, DevOptionHandler var2) {}

   @Nullable
   public File downloadBundleResourceFromUrlSync(String var1, File var2) {
      return null;
   }

   public DeveloperSettings getDevSettings() {
      return null;
   }

   public boolean getDevSupportEnabled() {
      return false;
   }

   public String getDownloadedJSBundleFile() {
      return null;
   }

   public String getJSBundleURLForRemoteDebugging() {
      return null;
   }

   @Nullable
   public StackFrame[] getLastErrorStack() {
      return null;
   }

   @Nullable
   public String getLastErrorTitle() {
      return null;
   }

   public String getSourceMapUrl() {
      return null;
   }

   public String getSourceUrl() {
      return null;
   }

   public void handleException(Exception var1) {
      this.mDefaultNativeModuleCallExceptionHandler.handleException(var1);
   }

   public void handleReloadJS() {}

   public boolean hasUpToDateJSBundleInCache() {
      return false;
   }

   public void hideRedboxDialog() {}

   public void isPackagerRunning(PackagerStatusCallback var1) {}

   public void onNewReactContextCreated(ReactContext var1) {}

   public void onReactInstanceDestroyed(ReactContext var1) {}

   public void registerErrorCustomizer(ErrorCustomizer var1) {}

   public void reloadJSFromServer(String var1) {}

   public void reloadSettings() {}

   public void setDevSupportEnabled(boolean var1) {}

   public void showDevOptionsDialog() {}

   public void showNewJSError(String var1, ReadableArray var2, int var3) {}

   public void showNewJavaError(String var1, Throwable var2) {}

   public void startInspector() {}

   public void stopInspector() {}

   public void updateJSError(String var1, ReadableArray var2, int var3) {}
}
