package com.facebook.internal;

import android.graphics.Bitmap;
import com.facebook.internal.ImageRequest;

public class ImageResponse {

   private Bitmap bitmap;
   private Exception error;
   private boolean isCachedRedirect;
   private ImageRequest request;


   ImageResponse(ImageRequest var1, Exception var2, boolean var3, Bitmap var4) {
      this.request = var1;
      this.error = var2;
      this.bitmap = var4;
      this.isCachedRedirect = var3;
   }

   public Bitmap getBitmap() {
      return this.bitmap;
   }

   public Exception getError() {
      return this.error;
   }

   public ImageRequest getRequest() {
      return this.request;
   }

   public boolean isCachedRedirect() {
      return this.isCachedRedirect;
   }
}
