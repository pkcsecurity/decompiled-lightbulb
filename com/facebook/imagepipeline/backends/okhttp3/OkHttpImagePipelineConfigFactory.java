package com.facebook.imagepipeline.backends.okhttp3;

import android.content.Context;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import okhttp3.OkHttpClient;

public class OkHttpImagePipelineConfigFactory {

   public static ImagePipelineConfig.Builder newBuilder(Context var0, OkHttpClient var1) {
      return ImagePipelineConfig.newBuilder(var0).setNetworkFetcher(new OkHttpNetworkFetcher(var1));
   }
}
