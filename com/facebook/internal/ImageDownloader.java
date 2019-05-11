package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.facebook.internal.ImageResponseCache;
import com.facebook.internal.UrlRedirectCache;
import com.facebook.internal.Utility;
import com.facebook.internal.WorkQueue;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {

   private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
   private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
   private static WorkQueue cacheReadQueue = new WorkQueue(2);
   private static WorkQueue downloadQueue = new WorkQueue(8);
   private static Handler handler;
   private static final Map<ImageDownloader.RequestKey, ImageDownloader.DownloaderContext> pendingRequests = new HashMap();


   public static boolean cancelRequest(ImageRequest param0) {
      // $FF: Couldn't be decompiled
   }

   public static void clearCache(Context var0) {
      ImageResponseCache.clearCache(var0);
      UrlRedirectCache.clearCache();
   }

   private static void download(ImageDownloader.RequestKey param0, Context param1) {
      // $FF: Couldn't be decompiled
   }

   public static void downloadAsync(ImageRequest param0) {
      // $FF: Couldn't be decompiled
   }

   private static void enqueueCacheRead(ImageRequest var0, ImageDownloader.RequestKey var1, boolean var2) {
      enqueueRequest(var0, var1, cacheReadQueue, new ImageDownloader.CacheReadWorkItem(var0.getContext(), var1, var2));
   }

   private static void enqueueDownload(ImageRequest var0, ImageDownloader.RequestKey var1) {
      enqueueRequest(var0, var1, downloadQueue, new ImageDownloader.DownloadImageWorkItem(var0.getContext(), var1));
   }

   private static void enqueueRequest(ImageRequest param0, ImageDownloader.RequestKey param1, WorkQueue param2, Runnable param3) {
      // $FF: Couldn't be decompiled
   }

   private static Handler getHandler() {
      synchronized(ImageDownloader.class){}

      Handler var0;
      try {
         if(handler == null) {
            handler = new Handler(Looper.getMainLooper());
         }

         var0 = handler;
      } finally {
         ;
      }

      return var0;
   }

   private static void issueResponse(ImageDownloader.RequestKey var0, final Exception var1, final Bitmap var2, final boolean var3) {
      ImageDownloader.DownloaderContext var5 = removePendingRequest(var0);
      if(var5 != null && !var5.isCancelled) {
         final ImageRequest var6 = var5.request;
         final ImageRequest.Callback var4 = var6.getCallback();
         if(var4 != null) {
            getHandler().post(new Runnable() {
               public void run() {
                  ImageResponse var1x = new ImageResponse(var6, var1, var3, var2);
                  var4.onCompleted(var1x);
               }
            });
         }
      }

   }

   public static void prioritizeRequest(ImageRequest param0) {
      // $FF: Couldn't be decompiled
   }

   private static void readFromCache(ImageDownloader.RequestKey var0, Context var1, boolean var2) {
      InputStream var8;
      label28: {
         boolean var3 = false;
         if(var2) {
            Uri var4 = UrlRedirectCache.getRedirectedUri(var0.uri);
            if(var4 != null) {
               InputStream var5 = ImageResponseCache.getCachedImageStream(var4, var1);
               var2 = var3;
               var8 = var5;
               if(var5 != null) {
                  var2 = true;
                  var8 = var5;
               }
               break label28;
            }
         }

         var8 = null;
         var2 = var3;
      }

      if(!var2) {
         var8 = ImageResponseCache.getCachedImageStream(var0.uri, var1);
      }

      if(var8 != null) {
         Bitmap var7 = BitmapFactory.decodeStream(var8);
         Utility.closeQuietly(var8);
         issueResponse(var0, (Exception)null, var7, var2);
      } else {
         ImageDownloader.DownloaderContext var6 = removePendingRequest(var0);
         if(var6 != null && !var6.isCancelled) {
            enqueueDownload(var6.request, var0);
         }

      }
   }

   private static ImageDownloader.DownloaderContext removePendingRequest(ImageDownloader.RequestKey param0) {
      // $FF: Couldn't be decompiled
   }

   static class CacheReadWorkItem implements Runnable {

      private boolean allowCachedRedirects;
      private Context context;
      private ImageDownloader.RequestKey key;


      CacheReadWorkItem(Context var1, ImageDownloader.RequestKey var2, boolean var3) {
         this.context = var1;
         this.key = var2;
         this.allowCachedRedirects = var3;
      }

      public void run() {
         ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
      }
   }

   static class DownloaderContext {

      boolean isCancelled;
      ImageRequest request;
      WorkQueue.WorkItem workItem;


      private DownloaderContext() {}

      // $FF: synthetic method
      DownloaderContext(Object var1) {
         this();
      }
   }

   static class DownloadImageWorkItem implements Runnable {

      private Context context;
      private ImageDownloader.RequestKey key;


      DownloadImageWorkItem(Context var1, ImageDownloader.RequestKey var2) {
         this.context = var1;
         this.key = var2;
      }

      public void run() {
         ImageDownloader.download(this.key, this.context);
      }
   }

   static class RequestKey {

      private static final int HASH_MULTIPLIER = 37;
      private static final int HASH_SEED = 29;
      Object tag;
      Uri uri;


      RequestKey(Uri var1, Object var2) {
         this.uri = var1;
         this.tag = var2;
      }

      public boolean equals(Object var1) {
         boolean var3 = false;
         boolean var2 = var3;
         if(var1 != null) {
            var2 = var3;
            if(var1 instanceof ImageDownloader.RequestKey) {
               ImageDownloader.RequestKey var4 = (ImageDownloader.RequestKey)var1;
               var2 = var3;
               if(var4.uri == this.uri) {
                  var2 = var3;
                  if(var4.tag == this.tag) {
                     var2 = true;
                  }
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         return (1073 + this.uri.hashCode()) * 37 + this.tag.hashCode();
      }
   }
}
