package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

final class GetTokenClient extends PlatformServiceClient {

   GetTokenClient(Context var1, String var2) {
      super(var1, 65536, 65537, 20121101, var2);
   }

   protected void populateRequestBundle(Bundle var1) {}
}
