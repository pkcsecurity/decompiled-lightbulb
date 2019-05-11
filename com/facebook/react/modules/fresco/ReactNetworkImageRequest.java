package com.facebook.react.modules.fresco;

import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.ReadableMap;

public class ReactNetworkImageRequest extends ImageRequest {

   private final ReadableMap mHeaders;


   protected ReactNetworkImageRequest(ImageRequestBuilder var1, ReadableMap var2) {
      super(var1);
      this.mHeaders = var2;
   }

   public static ReactNetworkImageRequest fromBuilderWithHeaders(ImageRequestBuilder var0, ReadableMap var1) {
      return new ReactNetworkImageRequest(var0, var1);
   }

   public ReadableMap getHeaders() {
      return this.mHeaders;
   }
}
