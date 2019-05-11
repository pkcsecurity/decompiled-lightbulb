package com.facebook.react.modules.debug;

import android.widget.Toast;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.debug.FpsDebugFrameCallback;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import java.util.Locale;
import javax.annotation.Nullable;

@ReactModule(
   name = "AnimationsDebugModule"
)
public class AnimationsDebugModule extends ReactContextBaseJavaModule {

   protected static final String NAME = "AnimationsDebugModule";
   @Nullable
   private final DeveloperSettings mCatalystSettings;
   @Nullable
   private FpsDebugFrameCallback mFrameCallback;


   public AnimationsDebugModule(ReactApplicationContext var1, DeveloperSettings var2) {
      super(var1);
      this.mCatalystSettings = var2;
   }

   public String getName() {
      return "AnimationsDebugModule";
   }

   public void onCatalystInstanceDestroy() {
      if(this.mFrameCallback != null) {
         this.mFrameCallback.stop();
         this.mFrameCallback = null;
      }

   }

   @ReactMethod
   public void startRecordingFps() {
      if(this.mCatalystSettings != null) {
         if(this.mCatalystSettings.isAnimationFpsDebugEnabled()) {
            if(this.mFrameCallback != null) {
               throw new JSApplicationCausedNativeException("Already recording FPS!");
            } else {
               this.mFrameCallback = new FpsDebugFrameCallback(ChoreographerCompat.getInstance(), this.getReactApplicationContext());
               this.mFrameCallback.startAndRecordFpsAtEachFrame();
            }
         }
      }
   }

   @ReactMethod
   public void stopRecordingFps(double var1) {
      if(this.mFrameCallback != null) {
         this.mFrameCallback.stop();
         FpsDebugFrameCallback.FpsInfo var3 = this.mFrameCallback.getFpsInfo((long)var1);
         if(var3 == null) {
            Toast.makeText(this.getReactApplicationContext(), "Unable to get FPS info", 1);
         } else {
            String var4 = String.format(Locale.US, "FPS: %.2f, %d frames (%d expected)", new Object[]{Double.valueOf(var3.fps), Integer.valueOf(var3.totalFrames), Integer.valueOf(var3.totalExpectedFrames)});
            String var5 = String.format(Locale.US, "JS FPS: %.2f, %d frames (%d expected)", new Object[]{Double.valueOf(var3.jsFps), Integer.valueOf(var3.totalJsFrames), Integer.valueOf(var3.totalExpectedFrames)});
            StringBuilder var6 = new StringBuilder();
            var6.append(var4);
            var6.append("\n");
            var6.append(var5);
            var6.append("\nTotal Time MS: ");
            var6.append(String.format(Locale.US, "%d", new Object[]{Integer.valueOf(var3.totalTimeMs)}));
            String var7 = var6.toString();
            FLog.d("ReactNative", var7);
            Toast.makeText(this.getReactApplicationContext(), var7, 1).show();
         }

         this.mFrameCallback = null;
      }
   }
}
