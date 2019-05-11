package com.facebook.react.devsupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

public class MultipartStreamReader {

   private static final String CRLF = "\r\n";
   private final String mBoundary;
   private final BufferedSource mSource;


   public MultipartStreamReader(BufferedSource var1, String var2) {
      this.mSource = var1;
      this.mBoundary = var2;
   }

   private void emitChunk(Buffer var1, boolean var2, MultipartStreamReader.ChunkCallback var3) throws IOException {
      ByteString var6 = ByteString.encodeUtf8("\r\n\r\n");
      long var4 = var1.indexOf(var6);
      if(var4 == -1L) {
         var3.execute((Map)null, var1, var2);
      } else {
         Buffer var7 = new Buffer();
         Buffer var8 = new Buffer();
         var1.read(var7, var4);
         var1.skip((long)var6.size());
         var1.readAll(var8);
         var3.execute(this.parseHeaders(var7), var8, var2);
      }
   }

   private Map<String, String> parseHeaders(Buffer var1) {
      HashMap var5 = new HashMap();
      String[] var7 = var1.readUtf8().split("\r\n");
      int var3 = var7.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         String var6 = var7[var2];
         int var4 = var6.indexOf(":");
         if(var4 != -1) {
            var5.put(var6.substring(0, var4).trim(), var6.substring(var4 + 1).trim());
         }
      }

      return var5;
   }

   public boolean readAllParts(MultipartStreamReader.ChunkCallback var1) throws IOException {
      StringBuilder var9 = new StringBuilder();
      var9.append("\r\n--");
      var9.append(this.mBoundary);
      var9.append("\r\n");
      ByteString var14 = ByteString.encodeUtf8(var9.toString());
      StringBuilder var10 = new StringBuilder();
      var10.append("\r\n--");
      var10.append(this.mBoundary);
      var10.append("--");
      var10.append("\r\n");
      ByteString var13 = ByteString.encodeUtf8(var10.toString());
      Buffer var11 = new Buffer();
      long var2 = 0L;

      label30:
      while(true) {
         long var4 = var2;

         do {
            long var6 = Math.max(var4 - (long)var13.size(), var2);
            var4 = var11.indexOf(var14, var6);
            boolean var8;
            if(var4 == -1L) {
               var4 = var11.indexOf(var13, var6);
               var8 = true;
            } else {
               var8 = false;
            }

            if(var4 != -1L) {
               if(var2 > 0L) {
                  Buffer var12 = new Buffer();
                  var11.skip(var2);
                  var11.read(var12, var4 - var2);
                  this.emitChunk(var12, var8, var1);
               } else {
                  var11.skip(var4);
               }

               if(var8) {
                  return true;
               }

               var2 = (long)var14.size();
               continue label30;
            }

            var4 = var11.size();
         } while(this.mSource.read(var11, (long)4096) > 0L);

         return false;
      }
   }

   public interface ChunkCallback {

      void execute(Map<String, String> var1, Buffer var2, boolean var3) throws IOException;
   }
}
