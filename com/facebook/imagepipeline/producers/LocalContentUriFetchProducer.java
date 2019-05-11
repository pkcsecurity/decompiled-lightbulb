package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalContentUriFetchProducer extends LocalFetchProducer {

   public static final String PRODUCER_NAME = "LocalContentUriFetchProducer";
   private static final String[] PROJECTION = new String[]{"_id", "_data"};
   private final ContentResolver mContentResolver;


   public LocalContentUriFetchProducer(Executor var1, PooledByteBufferFactory var2, ContentResolver var3) {
      super(var1, var2);
      this.mContentResolver = var3;
   }

   @Nullable
   private EncodedImage getCameraImage(Uri param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static int getLength(String var0) {
      return var0 == null?-1:(int)(new File(var0)).length();
   }

   protected EncodedImage getEncodedImage(ImageRequest var1) throws IOException {
      Uri var2 = var1.getSourceUri();
      if(UriUtil.isLocalContactUri(var2)) {
         InputStream var4;
         if(var2.toString().endsWith("/photo")) {
            var4 = this.mContentResolver.openInputStream(var2);
         } else {
            var4 = Contacts.openContactPhotoInputStream(this.mContentResolver, var2);
            if(var4 == null) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Contact photo does not exist: ");
               var5.append(var2);
               throw new IOException(var5.toString());
            }
         }

         return this.getEncodedImage(var4, -1);
      } else {
         if(UriUtil.isLocalCameraUri(var2)) {
            EncodedImage var3 = this.getCameraImage(var2);
            if(var3 != null) {
               return var3;
            }
         }

         return this.getEncodedImage(this.mContentResolver.openInputStream(var2), -1);
      }
   }

   protected String getProducerName() {
      return "LocalContentUriFetchProducer";
   }
}
