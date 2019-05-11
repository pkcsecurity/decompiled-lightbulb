package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.images.zaa;
import com.google.android.gms.common.images.zab;
import com.google.android.gms.common.images.zac;
import com.google.android.gms.common.images.zad;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zak;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {

   private static final Object zamg = new Object();
   private static HashSet<Uri> zamh = new HashSet();
   private static ImageManager zami;
   private final Context mContext;
   private final Handler mHandler;
   private final ExecutorService zamj;
   private final ImageManager.zaa zamk;
   private final zak zaml;
   private final Map<zaa, ImageManager.ImageReceiver> zamm;
   private final Map<Uri, ImageManager.ImageReceiver> zamn;
   private final Map<Uri, Long> zamo;


   private ImageManager(Context var1, boolean var2) {
      this.mContext = var1.getApplicationContext();
      this.mHandler = new gh(Looper.getMainLooper());
      this.zamj = Executors.newFixedThreadPool(4);
      this.zamk = null;
      this.zaml = new zak();
      this.zamm = new HashMap();
      this.zamn = new HashMap();
      this.zamo = new HashMap();
   }

   public static ImageManager create(Context var0) {
      if(zami == null) {
         zami = new ImageManager(var0, false);
      }

      return zami;
   }

   // $FF: synthetic method
   static Bitmap zaa(ImageManager var0, zab var1) {
      return var0.zaa(var1);
   }

   private final Bitmap zaa(zab var1) {
      return this.zamk == null?null:(Bitmap)this.zamk.get(var1);
   }

   // $FF: synthetic method
   static Map zaa(ImageManager var0) {
      return var0.zamm;
   }

   private final void zaa(zaa var1) {
      Asserts.checkMainThread("ImageManager.loadImage() must be called in the main thread");
      (new ImageManager.zac(var1)).run();
   }

   // $FF: synthetic method
   static zak zac(ImageManager var0) {
      return var0.zaml;
   }

   // $FF: synthetic method
   static Object zacc() {
      return zamg;
   }

   // $FF: synthetic method
   static HashSet zacd() {
      return zamh;
   }

   // $FF: synthetic method
   static Map zad(ImageManager var0) {
      return var0.zamo;
   }

   // $FF: synthetic method
   static Map zae(ImageManager var0) {
      return var0.zamn;
   }

   // $FF: synthetic method
   static ImageManager.zaa zah(ImageManager var0) {
      return var0.zamk;
   }

   public final void loadImage(ImageView var1, int var2) {
      this.zaa((zaa)(new zac(var1, var2)));
   }

   public final void loadImage(ImageView var1, Uri var2) {
      this.zaa((zaa)(new zac(var1, var2)));
   }

   public final void loadImage(ImageView var1, Uri var2, int var3) {
      zac var4 = new zac(var1, var2);
      var4.zamw = var3;
      this.zaa((zaa)var4);
   }

   public final void loadImage(ImageManager.OnImageLoadedListener var1, Uri var2) {
      this.zaa((zaa)(new zad(var1, var2)));
   }

   public final void loadImage(ImageManager.OnImageLoadedListener var1, Uri var2, int var3) {
      zad var4 = new zad(var1, var2);
      var4.zamw = var3;
      this.zaa((zaa)var4);
   }

   final class zac implements Runnable {

      private final zaa zams;


      public zac(zaa var2) {
         this.zams = var2;
      }

      public final void run() {
         // $FF: Couldn't be decompiled
      }
   }

   public interface OnImageLoadedListener {

      void onImageLoaded(Uri var1, Drawable var2, boolean var3);
   }

   final class zab implements Runnable {

      private final Uri mUri;
      private final ParcelFileDescriptor zamr;


      public zab(Uri var2, ParcelFileDescriptor var3) {
         this.mUri = var2;
         this.zamr = var3;
      }

      public final void run() {
         Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can\'t be executed in the main thread");
         ParcelFileDescriptor var3 = this.zamr;
         boolean var1 = false;
         Bitmap var2 = null;
         if(var3 != null) {
            label27: {
               Bitmap var9;
               try {
                  var9 = BitmapFactory.decodeFileDescriptor(this.zamr.getFileDescriptor());
               } catch (OutOfMemoryError var8) {
                  String var4 = String.valueOf(this.mUri);
                  StringBuilder var5 = new StringBuilder(String.valueOf(var4).length() + 34);
                  var5.append("OOM while loading bitmap for uri: ");
                  var5.append(var4);
                  Log.e("ImageManager", var5.toString(), var8);
                  var1 = true;
                  break label27;
               }

               var2 = var9;
            }

            try {
               this.zamr.close();
            } catch (IOException var7) {
               Log.e("ImageManager", "closed failed", var7);
            }
         } else {
            var2 = null;
            var1 = false;
         }

         CountDownLatch var11 = new CountDownLatch(1);
         ImageManager.this.mHandler.post(ImageManager.this.new zad(this.mUri, var2, var1, var11));

         try {
            var11.await();
         } catch (InterruptedException var6) {
            String var10 = String.valueOf(this.mUri);
            StringBuilder var12 = new StringBuilder(String.valueOf(var10).length() + 32);
            var12.append("Latch interrupted while posting ");
            var12.append(var10);
            Log.w("ImageManager", var12.toString());
         }
      }
   }

   @KeepName
   final class ImageReceiver extends ResultReceiver {

      private final Uri mUri;
      private final ArrayList<zaa> zamp;


      ImageReceiver(Uri var2) {
         super(new gh(Looper.getMainLooper()));
         this.mUri = var2;
         this.zamp = new ArrayList();
      }

      // $FF: synthetic method
      static ArrayList zaa(ImageManager.ImageReceiver var0) {
         return var0.zamp;
      }

      public final void onReceiveResult(int var1, Bundle var2) {
         ParcelFileDescriptor var3 = (ParcelFileDescriptor)var2.getParcelable("com.google.android.gms.extra.fileDescriptor");
         ImageManager.this.zamj.execute(ImageManager.this.new zab(this.mUri, var3));
      }

      public final void zab(zaa var1) {
         Asserts.checkMainThread("ImageReceiver.addImageRequest() must be called in the main thread");
         this.zamp.add(var1);
      }

      public final void zac(zaa var1) {
         Asserts.checkMainThread("ImageReceiver.removeImageRequest() must be called in the main thread");
         this.zamp.remove(var1);
      }

      public final void zace() {
         Intent var1 = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
         var1.putExtra("com.google.android.gms.extras.uri", this.mUri);
         var1.putExtra("com.google.android.gms.extras.resultReceiver", this);
         var1.putExtra("com.google.android.gms.extras.priority", 3);
         ImageManager.this.mContext.sendBroadcast(var1);
      }
   }

   static final class zaa extends LruCache<zab, Bitmap> {

      // $FF: synthetic method
      protected final void entryRemoved(boolean var1, Object var2, Object var3, Object var4) {
         super.entryRemoved(var1, (zab)var2, (Bitmap)var3, (Bitmap)var4);
      }

      // $FF: synthetic method
      protected final int sizeOf(Object var1, Object var2) {
         Bitmap var3 = (Bitmap)var2;
         return var3.getHeight() * var3.getRowBytes();
      }
   }

   final class zad implements Runnable {

      private final Bitmap mBitmap;
      private final Uri mUri;
      private final CountDownLatch zadq;
      private boolean zamt;


      public zad(Uri var2, Bitmap var3, boolean var4, CountDownLatch var5) {
         this.mUri = var2;
         this.mBitmap = var3;
         this.zamt = var4;
         this.zadq = var5;
      }

      public final void run() {
         // $FF: Couldn't be decompiled
      }
   }
}
