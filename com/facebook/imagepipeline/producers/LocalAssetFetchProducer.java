package com.facebook.imagepipeline.producers;

import android.content.res.AssetManager;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

public class LocalAssetFetchProducer extends LocalFetchProducer {

   public static final String PRODUCER_NAME = "LocalAssetFetchProducer";
   private final AssetManager mAssetManager;


   public LocalAssetFetchProducer(Executor var1, PooledByteBufferFactory var2, AssetManager var3) {
      super(var1, var2);
      this.mAssetManager = var3;
   }

   private static String getAssetName(ImageRequest var0) {
      return var0.getSourceUri().getPath().substring(1);
   }

   private int getLength(ImageRequest param1) {
      // $FF: Couldn't be decompiled
   }

   protected EncodedImage getEncodedImage(ImageRequest var1) throws IOException {
      return this.getEncodedImage(this.mAssetManager.open(getAssetName(var1), 2), this.getLength(var1));
   }

   protected String getProducerName() {
      return "LocalAssetFetchProducer";
   }
}
