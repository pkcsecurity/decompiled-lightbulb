package com.facebook.share.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

@Deprecated
final class LikeStatusClient extends PlatformServiceClient {

   private String objectId;


   LikeStatusClient(Context var1, String var2, String var3) {
      super(var1, 65542, 65543, 20141001, var2);
      this.objectId = var3;
   }

   protected void populateRequestBundle(Bundle var1) {
      var1.putString("com.facebook.platform.extra.OBJECT_ID", this.objectId);
   }
}
