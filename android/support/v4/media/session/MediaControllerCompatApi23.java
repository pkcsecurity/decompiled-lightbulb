package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@RequiresApi(23)
class MediaControllerCompatApi23 {


   public static class TransportControls {

      public static void playFromUri(Object var0, Uri var1, Bundle var2) {
         ((android.media.session.MediaController.TransportControls)var0).playFromUri(var1, var2);
      }
   }
}
