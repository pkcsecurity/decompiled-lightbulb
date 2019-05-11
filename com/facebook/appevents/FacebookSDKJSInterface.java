package com.facebook.appevents;

import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Logger;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

class FacebookSDKJSInterface {

   private static final String PARAMETER_FBSDK_PIXEL_REFERRAL = "_fb_pixel_referral_id";
   private static final String PROTOCOL = "fbmq-0.1";
   public static final String TAG = "FacebookSDKJSInterface";
   private Context context;


   public FacebookSDKJSInterface(Context var1) {
      this.context = var1;
   }

   private static Bundle jsonStringToBundle(String var0) {
      try {
         Bundle var2 = jsonToBundle(new JSONObject(var0));
         return var2;
      } catch (JSONException var1) {
         return new Bundle();
      }
   }

   private static Bundle jsonToBundle(JSONObject var0) throws JSONException {
      Bundle var1 = new Bundle();
      Iterator var2 = var0.keys();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.putString(var3, var0.getString(var3));
      }

      return var1;
   }

   @JavascriptInterface
   public String getProtocol() {
      return "fbmq-0.1";
   }

   @JavascriptInterface
   public void sendEvent(String var1, String var2, String var3) {
      if(var1 == null) {
         Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Can\'t bridge an event without a referral Pixel ID. Check your webview Pixel configuration");
      } else {
         AppEventsLogger var4 = AppEventsLogger.newLogger(this.context);
         Bundle var5 = jsonStringToBundle(var3);
         var5.putString("_fb_pixel_referral_id", var1);
         var4.logEvent(var2, var5);
      }
   }
}
