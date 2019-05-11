package com.google.firebase.analytics.connector;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

public interface AnalyticsConnector {

   @KeepForSdk
   void a(@NonNull String var1, @NonNull String var2, Bundle var3);

   @KeepForSdk
   void a(@NonNull String var1, @NonNull String var2, Object var3);

   @KeepForSdk
   public interface AnalyticsConnectorHandle {
   }

   @KeepForSdk
   public interface AnalyticsConnectorListener {
   }
}
