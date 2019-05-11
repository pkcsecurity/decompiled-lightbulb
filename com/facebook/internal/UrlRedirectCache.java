package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.Logger;
import java.io.IOException;

class UrlRedirectCache {

   private static final String REDIRECT_CONTENT_TAG;
   static final String TAG = "UrlRedirectCache";
   private static FileLruCache urlRedirectCache;


   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(TAG);
      var0.append("_Redirect");
      REDIRECT_CONTENT_TAG = var0.toString();
   }

   static void cacheUriRedirect(Uri param0, Uri param1) {
      // $FF: Couldn't be decompiled
   }

   static void clearCache() {
      try {
         getCache().clearCache();
      } catch (IOException var4) {
         LoggingBehavior var1 = LoggingBehavior.CACHE;
         String var2 = TAG;
         StringBuilder var3 = new StringBuilder();
         var3.append("clearCache failed ");
         var3.append(var4.getMessage());
         Logger.log(var1, 5, var2, var3.toString());
      }
   }

   static FileLruCache getCache() throws IOException {
      synchronized(UrlRedirectCache.class){}

      FileLruCache var0;
      try {
         if(urlRedirectCache == null) {
            urlRedirectCache = new FileLruCache(TAG, new FileLruCache.Limits());
         }

         var0 = urlRedirectCache;
      } finally {
         ;
      }

      return var0;
   }

   static Uri getRedirectedUri(Uri param0) {
      // $FF: Couldn't be decompiled
   }
}
