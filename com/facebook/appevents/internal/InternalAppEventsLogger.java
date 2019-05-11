package com.facebook.appevents.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility;
import java.math.BigDecimal;
import java.util.Currency;

class InternalAppEventsLogger extends AppEventsLogger {

   InternalAppEventsLogger(Context var1) {
      this(Utility.getActivityName(var1), (String)null, (AccessToken)null);
   }

   InternalAppEventsLogger(String var1, String var2, AccessToken var3) {
      super(var1, var2, var3);
   }

   protected void logEventImplicitly(String var1, BigDecimal var2, Currency var3, Bundle var4) {
      super.logEventImplicitly(var1, var2, var3, var4);
   }

   protected void logPurchaseImplicitlyInternal(BigDecimal var1, Currency var2, Bundle var3) {
      super.logPurchaseImplicitlyInternal(var1, var2, var3);
   }
}
