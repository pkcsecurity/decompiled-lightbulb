package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookWebFallbackDialog extends WebDialog {

   private static final int OS_BACK_BUTTON_RESPONSE_TIMEOUT_MILLISECONDS = 1500;
   private static final String TAG = "com.facebook.internal.FacebookWebFallbackDialog";
   private boolean waitingForDialogToClose;


   private FacebookWebFallbackDialog(Context var1, String var2, String var3) {
      super(var1, var2);
      this.setExpectedRedirectUrl(var3);
   }

   public static FacebookWebFallbackDialog newInstance(Context var0, String var1, String var2) {
      WebDialog.initDefaultTheme(var0);
      return new FacebookWebFallbackDialog(var0, var1, var2);
   }

   public void cancel() {
      WebView var1 = this.getWebView();
      if(this.isPageFinished() && !this.isListenerCalled() && var1 != null && var1.isShown()) {
         if(!this.waitingForDialogToClose) {
            this.waitingForDialogToClose = true;
            StringBuilder var2 = new StringBuilder();
            var2.append("javascript:");
            var2.append("(function() {  var event = document.createEvent(\'Event\');  event.initEvent(\'fbPlatformDialogMustClose\',true,true);  document.dispatchEvent(event);})();");
            var1.loadUrl(var2.toString());
            (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
               public void run() {
                  FacebookWebFallbackDialog.super.cancel();
               }
            }, 1500L);
         }
      } else {
         super.cancel();
      }
   }

   protected Bundle parseResponseUri(String var1) {
      Bundle var3 = Utility.parseUrlQueryString(Uri.parse(var1).getQuery());
      var1 = var3.getString("bridge_args");
      var3.remove("bridge_args");
      if(!Utility.isNullOrEmpty(var1)) {
         try {
            var3.putBundle("com.facebook.platform.protocol.BRIDGE_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(var1)));
         } catch (JSONException var5) {
            Utility.logd(TAG, "Unable to parse bridge_args JSON", var5);
         }
      }

      String var2 = var3.getString("method_results");
      var3.remove("method_results");
      if(!Utility.isNullOrEmpty(var2)) {
         var1 = var2;
         if(Utility.isNullOrEmpty(var2)) {
            var1 = "{}";
         }

         try {
            var3.putBundle("com.facebook.platform.protocol.RESULT_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(var1)));
         } catch (JSONException var4) {
            Utility.logd(TAG, "Unable to parse bridge_args JSON", var4);
         }
      }

      var3.remove("version");
      var3.putInt("com.facebook.platform.protocol.PROTOCOL_VERSION", NativeProtocol.getLatestKnownVersion());
      return var3;
   }
}
