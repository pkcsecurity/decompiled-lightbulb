package com.facebook.imageformat;

import javax.annotation.Nullable;

public class ImageFormat {

   public static final ImageFormat UNKNOWN = new ImageFormat("UNKNOWN", (String)null);
   private final String mFileExtension;
   private final String mName;


   public ImageFormat(String var1, @Nullable String var2) {
      this.mName = var1;
      this.mFileExtension = var2;
   }

   @Nullable
   public String getFileExtension() {
      return this.mFileExtension;
   }

   public String getName() {
      return this.mName;
   }

   public String toString() {
      return this.getName();
   }

   public interface FormatChecker {

      @Nullable
      ImageFormat determineFormat(byte[] var1, int var2);

      int getHeaderSize();
   }
}
