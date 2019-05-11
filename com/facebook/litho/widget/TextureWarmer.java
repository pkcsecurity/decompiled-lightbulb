package com.facebook.litho.widget;

import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.VisibleForTesting;
import android.text.Layout;
import java.lang.ref.WeakReference;

public class TextureWarmer {

   private static final String TAG = "com.facebook.litho.widget.TextureWarmer";
   private static final int WARMER_THREAD_PRIORITY = 14;
   private static TextureWarmer sInstance;
   private final TextureWarmer.WarmerHandler mHandler;


   private TextureWarmer() {
      HandlerThread var1 = new HandlerThread(TAG, 14);
      var1.start();
      this.mHandler = new TextureWarmer.WarmerHandler(var1.getLooper(), null);
   }

   public static TextureWarmer getInstance() {
      synchronized(TextureWarmer.class){}

      TextureWarmer var0;
      try {
         if(sInstance == null) {
            sInstance = new TextureWarmer();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   @VisibleForTesting
   Looper getWarmerLooper() {
      return this.mHandler.getLooper();
   }

   public void warmDrawable(TextureWarmer.WarmDrawable var1) {
      this.mHandler.obtainMessage(1, new WeakReference(var1)).sendToTarget();
   }

   public void warmLayout(Layout var1) {
      this.mHandler.obtainMessage(0, new WeakReference(var1)).sendToTarget();
   }

   public static class WarmDrawable {

      private final Drawable drawable;
      private final int height;
      private final int width;


      public WarmDrawable(Drawable var1, int var2, int var3) {
         this.drawable = var1;
         this.width = var2;
         this.height = var3;
      }

      // $FF: synthetic method
      static int access$100(TextureWarmer.WarmDrawable var0) {
         return var0.width;
      }

      // $FF: synthetic method
      static int access$200(TextureWarmer.WarmDrawable var0) {
         return var0.height;
      }

      // $FF: synthetic method
      static Drawable access$300(TextureWarmer.WarmDrawable var0) {
         return var0.drawable;
      }
   }

   static final class WarmerHandler extends Handler {

      public static final int WARM_DRAWABLE = 1;
      public static final int WARM_LAYOUT = 0;
      private final Picture mPicture;


      private WarmerHandler(Looper var1) {
         super(var1);

         Picture var3;
         try {
            var3 = new Picture();
         } catch (RuntimeException var2) {
            var3 = null;
         }

         this.mPicture = var3;
      }

      // $FF: synthetic method
      WarmerHandler(Looper var1, Object var2) {
         this(var1);
      }

      public void handleMessage(Message param1) {
         // $FF: Couldn't be decompiled
      }
   }
}
