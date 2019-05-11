package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.net.Uri;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

public class QualifiedResourceFetchProducer extends LocalFetchProducer {

   public static final String PRODUCER_NAME = "QualifiedResourceFetchProducer";
   private final ContentResolver mContentResolver;


   public QualifiedResourceFetchProducer(Executor var1, PooledByteBufferFactory var2, ContentResolver var3) {
      super(var1, var2);
      this.mContentResolver = var3;
   }

   protected EncodedImage getEncodedImage(ImageRequest var1) throws IOException {
      Uri var2 = var1.getSourceUri();
      return this.getEncodedImage(this.mContentResolver.openInputStream(var2), -1);
   }

   protected String getProducerName() {
      return "QualifiedResourceFetchProducer";
   }
}
