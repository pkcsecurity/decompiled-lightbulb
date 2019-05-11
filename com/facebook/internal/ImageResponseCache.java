package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

class ImageResponseCache {

   static final String TAG = "ImageResponseCache";
   private static FileLruCache imageCache;


   static void clearCache(Context var0) {
      try {
         getCache(var0).clearCache();
      } catch (IOException var4) {
         LoggingBehavior var1 = LoggingBehavior.CACHE;
         String var2 = TAG;
         StringBuilder var3 = new StringBuilder();
         var3.append("clearCache failed ");
         var3.append(var4.getMessage());
         Logger.log(var1, 5, var2, var3.toString());
      }
   }

   static FileLruCache getCache(Context var0) throws IOException {
      synchronized(ImageResponseCache.class){}

      FileLruCache var3;
      try {
         if(imageCache == null) {
            imageCache = new FileLruCache(TAG, new FileLruCache.Limits());
         }

         var3 = imageCache;
      } finally {
         ;
      }

      return var3;
   }

   static InputStream getCachedImageStream(Uri var0, Context var1) {
      if(var0 != null && isCDNURL(var0)) {
         try {
            InputStream var3 = getCache(var1).get(var0.toString());
            return var3;
         } catch (IOException var2) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, var2.toString());
         }
      }

      return null;
   }

   static InputStream interceptAndCacheImageStream(Context var0, HttpURLConnection var1) throws IOException {
      InputStream var2;
      if(var1.getResponseCode() == 200) {
         Uri var4 = Uri.parse(var1.getURL().toString());
         InputStream var3 = var1.getInputStream();
         var2 = var3;

         try {
            if(isCDNURL(var4)) {
               InputStream var6 = getCache(var0).interceptAndPut(var4.toString(), new ImageResponseCache.BufferedHttpInputStream(var3, var1));
               return var6;
            }
         } catch (IOException var5) {
            return var3;
         }
      } else {
         var2 = null;
      }

      return var2;
   }

   private static boolean isCDNURL(Uri var0) {
      if(var0 != null) {
         String var1 = var0.getHost();
         if(var1.endsWith("fbcdn.net")) {
            return true;
         }

         if(var1.startsWith("fbcdn") && var1.endsWith("akamaihd.net")) {
            return true;
         }
      }

      return false;
   }

   static class BufferedHttpInputStream extends BufferedInputStream {

      HttpURLConnection connection;


      BufferedHttpInputStream(InputStream var1, HttpURLConnection var2) {
         super(var1, 8192);
         this.connection = var2;
      }

      public void close() throws IOException {
         super.close();
         Utility.disconnectQuietly(this.connection);
      }
   }
}
