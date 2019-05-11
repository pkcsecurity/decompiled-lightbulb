package com.facebook.imagepipeline.producers;

import android.content.res.Resources;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

public class LocalResourceFetchProducer extends LocalFetchProducer {

   public static final String PRODUCER_NAME = "LocalResourceFetchProducer";
   private final Resources mResources;


   public LocalResourceFetchProducer(Executor var1, PooledByteBufferFactory var2, Resources var3) {
      super(var1, var2);
      this.mResources = var3;
   }

   private int getLength(ImageRequest param1) {
      // $FF: Couldn't be decompiled
   }

   private static int getResourceId(ImageRequest var0) {
      return Integer.parseInt(var0.getSourceUri().getPath().substring(1));
   }

   protected EncodedImage getEncodedImage(ImageRequest var1) throws IOException {
      return this.getEncodedImage(this.mResources.openRawResource(getResourceId(var1)), this.getLength(var1));
   }

   protected String getProducerName() {
      return "LocalResourceFetchProducer";
   }
}
