package com.facebook.react.views.imagehelper;

import android.content.Context;
import android.net.Uri;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import javax.annotation.Nullable;

public class ImageSource {

   private boolean isResource;
   private double mSize;
   private String mSource;
   @Nullable
   private Uri mUri;


   public ImageSource(Context var1, String var2) {
      this(var1, var2, 0.0D, 0.0D);
   }

   public ImageSource(Context var1, String var2, double var3, double var5) {
      this.mSource = var2;
      this.mSize = var3 * var5;
      this.mUri = this.computeUri(var1);
   }

   private Uri computeLocalUri(Context var1) {
      this.isResource = true;
      return ResourceDrawableIdHelper.getInstance().getResourceDrawableUri(var1, this.mSource);
   }

   private Uri computeUri(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public double getSize() {
      return this.mSize;
   }

   public String getSource() {
      return this.mSource;
   }

   public Uri getUri() {
      return (Uri)Assertions.assertNotNull(this.mUri);
   }

   public boolean isResource() {
      return this.isResource;
   }
}
