package com.facebook.react.packagerconnection;

import android.net.Uri.Builder;
import com.facebook.common.logging.FLog;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import com.facebook.react.packagerconnection.PackagerConnectionSettings;
import com.facebook.react.packagerconnection.ReconnectingWebSocket;
import com.facebook.react.packagerconnection.RequestHandler;
import com.facebook.react.packagerconnection.Responder;
import java.util.Map;
import okio.ByteString;
import org.json.JSONObject;

public final class JSPackagerClient implements ReconnectingWebSocket.MessageCallback {

   private static final String PACKAGER_CONNECTION_URL_FORMAT = "ws://%s/message?device=%s&app=%s&context=%s";
   private static final int PROTOCOL_VERSION = 2;
   private static final String TAG = "JSPackagerClient";
   private Map<String, RequestHandler> mRequestHandlers;
   private ReconnectingWebSocket mWebSocket;


   public JSPackagerClient(String var1, PackagerConnectionSettings var2, Map<String, RequestHandler> var3) {
      Builder var4 = new Builder();
      var4.scheme("ws").encodedAuthority(var2.getDebugServerHost()).appendPath("message").appendQueryParameter("device", AndroidInfoHelpers.getFriendlyDeviceName()).appendQueryParameter("app", var2.getPackageName()).appendQueryParameter("clientid", var1);
      this.mWebSocket = new ReconnectingWebSocket(var4.build().toString(), this, (ReconnectingWebSocket.ConnectionCallback)null);
      this.mRequestHandlers = var3;
   }

   private void abortOnMessage(Object var1, String var2) {
      if(var1 != null) {
         (new JSPackagerClient.ResponderImpl(var1)).error(var2);
      }

      String var4 = TAG;
      StringBuilder var3 = new StringBuilder();
      var3.append("Handling the message failed with reason: ");
      var3.append(var2);
      FLog.e(var4, var3.toString());
   }

   public void close() {
      this.mWebSocket.closeQuietly();
   }

   public void init() {
      this.mWebSocket.connect();
   }

   public void onMessage(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void onMessage(ByteString var1) {
      FLog.w(TAG, "Websocket received message with payload of unexpected type binary");
   }

   class ResponderImpl implements Responder {

      private Object mId;


      public ResponderImpl(Object var2) {
         this.mId = var2;
      }

      public void error(Object var1) {
         try {
            JSONObject var2 = new JSONObject();
            var2.put("version", 2);
            var2.put("id", this.mId);
            var2.put("error", var1);
            JSPackagerClient.this.mWebSocket.sendMessage(var2.toString());
         } catch (Exception var3) {
            FLog.e(JSPackagerClient.TAG, "Responding with error failed", (Throwable)var3);
         }
      }

      public void respond(Object var1) {
         try {
            JSONObject var2 = new JSONObject();
            var2.put("version", 2);
            var2.put("id", this.mId);
            var2.put("result", var1);
            JSPackagerClient.this.mWebSocket.sendMessage(var2.toString());
         } catch (Exception var3) {
            FLog.e(JSPackagerClient.TAG, "Responding failed", (Throwable)var3);
         }
      }
   }
}
