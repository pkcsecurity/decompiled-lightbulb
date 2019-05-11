package com.facebook.react.modules.fresco;

import android.support.annotation.Nullable;
import com.facebook.common.logging.FLog;
import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.common.ModuleDataCleaner;
import com.facebook.react.modules.fresco.ReactOkHttpNetworkFetcher;
import com.facebook.react.modules.fresco.SystraceRequestListener;
import com.facebook.react.modules.network.CookieJarContainer;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import com.facebook.react.modules.network.OkHttpClientProvider;
import com.facebook.soloader.SoLoader;
import java.util.HashSet;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

@ReactModule(
   name = "FrescoModule"
)
public class FrescoModule extends ReactContextBaseJavaModule implements LifecycleEventListener, ModuleDataCleaner.Cleanable {

   private static boolean sHasBeenInitialized;
   private final boolean mClearOnDestroy;
   @Nullable
   private ImagePipelineConfig mConfig;


   public FrescoModule(ReactApplicationContext var1) {
      this(var1, true, (ImagePipelineConfig)null);
   }

   public FrescoModule(ReactApplicationContext var1, boolean var2) {
      this(var1, var2, (ImagePipelineConfig)null);
   }

   public FrescoModule(ReactApplicationContext var1, boolean var2, @Nullable ImagePipelineConfig var3) {
      super(var1);
      this.mClearOnDestroy = var2;
      this.mConfig = var3;
   }

   private static ImagePipelineConfig getDefaultConfig(ReactContext var0) {
      return getDefaultConfigBuilder(var0).build();
   }

   public static ImagePipelineConfig.Builder getDefaultConfigBuilder(ReactContext var0) {
      HashSet var1 = new HashSet();
      var1.add(new SystraceRequestListener());
      OkHttpClient var2 = OkHttpClientProvider.createClient();
      ((CookieJarContainer)var2.cookieJar()).setCookieJar(new JavaNetCookieJar(new ForwardingCookieHandler(var0)));
      return OkHttpImagePipelineConfigFactory.newBuilder(var0.getApplicationContext(), var2).setNetworkFetcher(new ReactOkHttpNetworkFetcher(var2)).setDownsampleEnabled(false).setRequestListeners(var1);
   }

   public static boolean hasBeenInitialized() {
      return sHasBeenInitialized;
   }

   public void clearSensitiveData() {
      Fresco.getImagePipeline().clearCaches();
   }

   public String getName() {
      return "FrescoModule";
   }

   public void initialize() {
      super.initialize();
      this.getReactApplicationContext().addLifecycleEventListener(this);
      if(!hasBeenInitialized()) {
         SoLoaderShim.setHandler(new FrescoModule.FrescoHandler(null));
         if(this.mConfig == null) {
            this.mConfig = getDefaultConfig(this.getReactApplicationContext());
         }

         Fresco.initialize(this.getReactApplicationContext().getApplicationContext(), this.mConfig);
         sHasBeenInitialized = true;
      } else if(this.mConfig != null) {
         FLog.w("ReactNative", "Fresco has already been initialized with a different config. The new Fresco configuration will be ignored!");
      }

      this.mConfig = null;
   }

   public void onHostDestroy() {
      if(hasBeenInitialized() && this.mClearOnDestroy) {
         Fresco.getImagePipeline().clearMemoryCaches();
      }

   }

   public void onHostPause() {}

   public void onHostResume() {}

   static class FrescoHandler implements SoLoaderShim.Handler {

      private FrescoHandler() {}

      // $FF: synthetic method
      FrescoHandler(Object var1) {
         this();
      }

      public void loadLibrary(String var1) {
         SoLoader.loadLibrary(var1);
      }
   }
}
