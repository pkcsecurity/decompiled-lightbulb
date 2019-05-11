package com.facebook.react.packagerconnection;

import com.facebook.react.packagerconnection.Responder;
import javax.annotation.Nullable;

public interface RequestHandler {

   void onNotification(@Nullable Object var1);

   void onRequest(@Nullable Object var1, Responder var2);
}
