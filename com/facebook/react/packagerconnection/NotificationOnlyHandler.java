package com.facebook.react.packagerconnection;

import com.facebook.common.logging.FLog;
import com.facebook.react.packagerconnection.JSPackagerClient;
import com.facebook.react.packagerconnection.RequestHandler;
import com.facebook.react.packagerconnection.Responder;
import javax.annotation.Nullable;

public abstract class NotificationOnlyHandler implements RequestHandler {

   private static final String TAG = JSPackagerClient.class.getSimpleName();


   public abstract void onNotification(@Nullable Object var1);

   public final void onRequest(@Nullable Object var1, Responder var2) {
      var2.error("Request is not supported");
      FLog.e(TAG, "Request is not supported");
   }
}
