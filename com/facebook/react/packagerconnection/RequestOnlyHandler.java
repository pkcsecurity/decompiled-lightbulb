package com.facebook.react.packagerconnection;

import com.facebook.common.logging.FLog;
import com.facebook.react.packagerconnection.JSPackagerClient;
import com.facebook.react.packagerconnection.RequestHandler;
import com.facebook.react.packagerconnection.Responder;
import javax.annotation.Nullable;

public abstract class RequestOnlyHandler implements RequestHandler {

   private static final String TAG = JSPackagerClient.class.getSimpleName();


   public final void onNotification(@Nullable Object var1) {
      FLog.e(TAG, "Notification is not supported");
   }

   public abstract void onRequest(@Nullable Object var1, Responder var2);
}
