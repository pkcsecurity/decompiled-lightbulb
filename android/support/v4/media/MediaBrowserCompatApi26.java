package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.List;

@RequiresApi(26)
class MediaBrowserCompatApi26 {

   static Object createSubscriptionCallback(MediaBrowserCompatApi26.SubscriptionCallback var0) {
      return new MediaBrowserCompatApi26.SubscriptionCallbackProxy(var0);
   }

   public static void subscribe(Object var0, String var1, Bundle var2, Object var3) {
      ((MediaBrowser)var0).subscribe(var1, var2, (android.media.browse.MediaBrowser.SubscriptionCallback)var3);
   }

   public static void unsubscribe(Object var0, String var1, Object var2) {
      ((MediaBrowser)var0).unsubscribe(var1, (android.media.browse.MediaBrowser.SubscriptionCallback)var2);
   }

   static class SubscriptionCallbackProxy<T extends Object & MediaBrowserCompatApi26.SubscriptionCallback> extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {

      SubscriptionCallbackProxy(T var1) {
         super(var1);
      }

      public void onChildrenLoaded(@NonNull String var1, List<MediaItem> var2, @NonNull Bundle var3) {
         MediaSessionCompat.ensureClassLoader(var3);
         ((MediaBrowserCompatApi26.SubscriptionCallback)this.mSubscriptionCallback).onChildrenLoaded(var1, var2, var3);
      }

      public void onError(@NonNull String var1, @NonNull Bundle var2) {
         MediaSessionCompat.ensureClassLoader(var2);
         ((MediaBrowserCompatApi26.SubscriptionCallback)this.mSubscriptionCallback).onError(var1, var2);
      }
   }

   interface SubscriptionCallback extends MediaBrowserCompatApi21.SubscriptionCallback {

      void onChildrenLoaded(@NonNull String var1, List<?> var2, @NonNull Bundle var3);

      void onError(@NonNull String var1, @NonNull Bundle var2);
   }
}
