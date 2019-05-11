package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompatApi21;

@RequiresApi(23)
class MediaSessionCompatApi23 {

   public static Object createCallback(MediaSessionCompatApi23.Callback var0) {
      return new MediaSessionCompatApi23.CallbackProxy(var0);
   }

   static class CallbackProxy<T extends Object & MediaSessionCompatApi23.Callback> extends MediaSessionCompatApi21.CallbackProxy<T> {

      public CallbackProxy(T var1) {
         super(var1);
      }

      public void onPlayFromUri(Uri var1, Bundle var2) {
         MediaSessionCompat.ensureClassLoader(var2);
         ((MediaSessionCompatApi23.Callback)this.mCallback).onPlayFromUri(var1, var2);
      }
   }

   public interface Callback extends MediaSessionCompatApi21.Callback {

      void onPlayFromUri(Uri var1, Bundle var2);
   }
}
