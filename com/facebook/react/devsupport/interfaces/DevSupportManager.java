package com.facebook.react.devsupport.interfaces;

import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.devsupport.interfaces.DevOptionHandler;
import com.facebook.react.devsupport.interfaces.ErrorCustomizer;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.devsupport.interfaces.StackFrame;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import java.io.File;
import javax.annotation.Nullable;

public interface DevSupportManager extends NativeModuleCallExceptionHandler {

   void addCustomDevOption(String var1, DevOptionHandler var2);

   @Nullable
   File downloadBundleResourceFromUrlSync(String var1, File var2);

   DeveloperSettings getDevSettings();

   boolean getDevSupportEnabled();

   String getDownloadedJSBundleFile();

   String getJSBundleURLForRemoteDebugging();

   @Nullable
   StackFrame[] getLastErrorStack();

   @Nullable
   String getLastErrorTitle();

   String getSourceMapUrl();

   String getSourceUrl();

   void handleReloadJS();

   boolean hasUpToDateJSBundleInCache();

   void hideRedboxDialog();

   void isPackagerRunning(PackagerStatusCallback var1);

   void onNewReactContextCreated(ReactContext var1);

   void onReactInstanceDestroyed(ReactContext var1);

   void registerErrorCustomizer(ErrorCustomizer var1);

   void reloadJSFromServer(String var1);

   void reloadSettings();

   void setDevSupportEnabled(boolean var1);

   void showDevOptionsDialog();

   void showNewJSError(String var1, ReadableArray var2, int var3);

   void showNewJavaError(String var1, Throwable var2);

   void startInspector();

   void stopInspector();

   void updateJSError(String var1, ReadableArray var2, int var3);
}
