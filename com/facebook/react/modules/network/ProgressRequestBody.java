package com.facebook.react.modules.network;

import com.facebook.react.modules.network.ProgressListener;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {

   private BufferedSink mBufferedSink;
   private final ProgressListener mProgressListener;
   private final RequestBody mRequestBody;


   public ProgressRequestBody(RequestBody var1, ProgressListener var2) {
      this.mRequestBody = var1;
      this.mProgressListener = var2;
   }

   private Sink sink(final Sink var1) {
      return new ForwardingSink(var1) {

         long bytesWritten = 0L;
         long contentLength = 0L;

         public void write(Buffer var1, long var2) throws IOException {
            super.write(var1, var2);
            if(this.contentLength == 0L) {
               this.contentLength = ProgressRequestBody.this.contentLength();
            }

            this.bytesWritten += var2;
            ProgressListener var7 = ProgressRequestBody.this.mProgressListener;
            var2 = this.bytesWritten;
            long var4 = this.contentLength;
            boolean var6;
            if(this.bytesWritten == this.contentLength) {
               var6 = true;
            } else {
               var6 = false;
            }

            var7.onProgress(var2, var4, var6);
         }
      };
   }

   public long contentLength() throws IOException {
      return this.mRequestBody.contentLength();
   }

   public MediaType contentType() {
      return this.mRequestBody.contentType();
   }

   public void writeTo(BufferedSink var1) throws IOException {
      if(this.mBufferedSink == null) {
         this.mBufferedSink = Okio.buffer(this.sink(var1));
      }

      this.mRequestBody.writeTo(this.mBufferedSink);
      this.mBufferedSink.flush();
   }
}
