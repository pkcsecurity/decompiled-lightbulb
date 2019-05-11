package com.facebook.react.modules.network;

import com.facebook.react.modules.network.ProgressListener;
import java.io.IOException;
import javax.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {

   @Nullable
   private BufferedSource mBufferedSource;
   private final ProgressListener mProgressListener;
   private final ResponseBody mResponseBody;
   private long mTotalBytesRead;


   public ProgressResponseBody(ResponseBody var1, ProgressListener var2) {
      this.mResponseBody = var1;
      this.mProgressListener = var2;
      this.mTotalBytesRead = 0L;
   }

   private Source source(final Source var1) {
      return new ForwardingSource(var1) {
         public long read(Buffer var1, long var2) throws IOException {
            long var4 = super.read(var1, var2);
            ProgressResponseBody var9 = ProgressResponseBody.this;
            long var6 = ProgressResponseBody.this.mTotalBytesRead;
            if(var4 != -1L) {
               var2 = var4;
            } else {
               var2 = 0L;
            }

            var9.mTotalBytesRead = var6 + var2;
            ProgressListener var10 = ProgressResponseBody.this.mProgressListener;
            var2 = ProgressResponseBody.this.mTotalBytesRead;
            var6 = ProgressResponseBody.this.mResponseBody.contentLength();
            boolean var8;
            if(var4 == -1L) {
               var8 = true;
            } else {
               var8 = false;
            }

            var10.onProgress(var2, var6, var8);
            return var4;
         }
      };
   }

   public long contentLength() {
      return this.mResponseBody.contentLength();
   }

   public MediaType contentType() {
      return this.mResponseBody.contentType();
   }

   public BufferedSource source() {
      if(this.mBufferedSource == null) {
         this.mBufferedSource = Okio.buffer(this.source(this.mResponseBody.source()));
      }

      return this.mBufferedSource;
   }

   public long totalBytesRead() {
      return this.mTotalBytesRead;
   }
}
