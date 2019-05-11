package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

final class LoginStatusClient extends PlatformServiceClient {

   static final long DEFAULT_TOAST_DURATION_MS = 5000L;
   private final String graphApiVersion;
   private final String loggerRef;
   private final long toastDurationMs;


   LoginStatusClient(Context var1, String var2, String var3, String var4, long var5) {
      super(var1, 65546, 65547, 20170411, var2);
      this.loggerRef = var3;
      this.graphApiVersion = var4;
      this.toastDurationMs = var5;
   }

   protected void populateRequestBundle(Bundle var1) {
      var1.putString("com.facebook.platform.extra.LOGGER_REF", this.loggerRef);
      var1.putString("com.facebook.platform.extra.GRAPH_API_VERSION", this.graphApiVersion);
      var1.putLong("com.facebook.platform.extra.EXTRA_TOAST_DURATION_MS", this.toastDurationMs);
   }
}
