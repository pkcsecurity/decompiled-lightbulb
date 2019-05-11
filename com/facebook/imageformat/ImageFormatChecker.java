package com.facebook.imageformat;

import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.imageformat.DefaultImageFormatChecker;
import com.facebook.imageformat.ImageFormat;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class ImageFormatChecker {

   private static ImageFormatChecker sInstance;
   @Nullable
   private List<ImageFormat.FormatChecker> mCustomImageFormatCheckers;
   private final ImageFormat.FormatChecker mDefaultFormatChecker = new DefaultImageFormatChecker();
   private int mMaxHeaderLength;


   private ImageFormatChecker() {
      this.updateMaxHeaderLength();
   }

   public static ImageFormat getImageFormat(InputStream var0) throws IOException {
      return getInstance().determineImageFormat(var0);
   }

   public static ImageFormat getImageFormat(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static ImageFormat getImageFormat_WrapIOException(InputStream var0) {
      try {
         ImageFormat var2 = getImageFormat(var0);
         return var2;
      } catch (IOException var1) {
         throw Throwables.propagate(var1);
      }
   }

   public static ImageFormatChecker getInstance() {
      synchronized(ImageFormatChecker.class){}

      ImageFormatChecker var0;
      try {
         if(sInstance == null) {
            sInstance = new ImageFormatChecker();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   private static int readHeaderFromStream(int var0, InputStream var1, byte[] var2) throws IOException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      boolean var3;
      if(var2.length >= var0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      if(var1.markSupported()) {
         try {
            var1.mark(var0);
            var0 = ByteStreams.read(var1, var2, 0, var0);
         } finally {
            var1.reset();
         }

         return var0;
      } else {
         return ByteStreams.read(var1, var2, 0, var0);
      }
   }

   private void updateMaxHeaderLength() {
      this.mMaxHeaderLength = this.mDefaultFormatChecker.getHeaderSize();
      ImageFormat.FormatChecker var2;
      if(this.mCustomImageFormatCheckers != null) {
         for(Iterator var1 = this.mCustomImageFormatCheckers.iterator(); var1.hasNext(); this.mMaxHeaderLength = Math.max(this.mMaxHeaderLength, var2.getHeaderSize())) {
            var2 = (ImageFormat.FormatChecker)var1.next();
         }
      }

   }

   public ImageFormat determineImageFormat(InputStream var1) throws IOException {
      Preconditions.checkNotNull(var1);
      byte[] var3 = new byte[this.mMaxHeaderLength];
      int var2 = readHeaderFromStream(this.mMaxHeaderLength, var1, var3);
      if(this.mCustomImageFormatCheckers != null) {
         Iterator var5 = this.mCustomImageFormatCheckers.iterator();

         while(var5.hasNext()) {
            ImageFormat var4 = ((ImageFormat.FormatChecker)var5.next()).determineFormat(var3, var2);
            if(var4 != null && var4 != ImageFormat.UNKNOWN) {
               return var4;
            }
         }
      }

      ImageFormat var7 = this.mDefaultFormatChecker.determineFormat(var3, var2);
      ImageFormat var6 = var7;
      if(var7 == null) {
         var6 = ImageFormat.UNKNOWN;
      }

      return var6;
   }

   public void setCustomImageFormatCheckers(@Nullable List<ImageFormat.FormatChecker> var1) {
      this.mCustomImageFormatCheckers = var1;
      this.updateMaxHeaderLength();
   }
}
