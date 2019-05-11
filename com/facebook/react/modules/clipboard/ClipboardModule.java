package com.facebook.react.modules.clipboard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.facebook.react.bridge.ContextBaseJavaModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(
   name = "Clipboard"
)
public class ClipboardModule extends ContextBaseJavaModule {

   public ClipboardModule(Context var1) {
      super(var1);
   }

   private ClipboardManager getClipboardService() {
      Context var1 = this.getContext();
      this.getContext();
      return (ClipboardManager)var1.getSystemService("clipboard");
   }

   public String getName() {
      return "Clipboard";
   }

   @ReactMethod
   public void getString(Promise param1) {
      // $FF: Couldn't be decompiled
   }

   @ReactMethod
   @SuppressLint({"DeprecatedMethod"})
   public void setString(String var1) {
      if(VERSION.SDK_INT >= 11) {
         ClipData var2 = ClipData.newPlainText((CharSequence)null, var1);
         this.getClipboardService().setPrimaryClip(var2);
      } else {
         this.getClipboardService().setText(var1);
      }
   }
}
