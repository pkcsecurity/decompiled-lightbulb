package com.fasterxml.jackson.core.util;


public class BufferRecycler {

   public static final int DEFAULT_WRITE_CONCAT_BUFFER_LEN = 2000;
   protected final byte[][] _byteBuffers = new byte[BufferRecycler.ByteBufferType.values().length][];
   protected final char[][] _charBuffers = new char[BufferRecycler.CharBufferType.values().length][];


   private byte[] balloc(int var1) {
      return new byte[var1];
   }

   private char[] calloc(int var1) {
      return new char[var1];
   }

   public final byte[] allocByteBuffer(BufferRecycler.ByteBufferType var1) {
      int var2 = var1.ordinal();
      byte[] var3 = this._byteBuffers[var2];
      if(var3 == null) {
         return this.balloc(var1.size);
      } else {
         this._byteBuffers[var2] = null;
         return var3;
      }
   }

   public final char[] allocCharBuffer(BufferRecycler.CharBufferType var1) {
      return this.allocCharBuffer(var1, 0);
   }

   public final char[] allocCharBuffer(BufferRecycler.CharBufferType var1, int var2) {
      int var3 = var2;
      if(var1.size > var2) {
         var3 = var1.size;
      }

      var2 = var1.ordinal();
      char[] var4 = this._charBuffers[var2];
      if(var4 != null && var4.length >= var3) {
         this._charBuffers[var2] = null;
         return var4;
      } else {
         return this.calloc(var3);
      }
   }

   public final void releaseByteBuffer(BufferRecycler.ByteBufferType var1, byte[] var2) {
      this._byteBuffers[var1.ordinal()] = var2;
   }

   public final void releaseCharBuffer(BufferRecycler.CharBufferType var1, char[] var2) {
      this._charBuffers[var1.ordinal()] = var2;
   }

   public static enum ByteBufferType {

      // $FF: synthetic field
      private static final BufferRecycler.ByteBufferType[] $VALUES = new BufferRecycler.ByteBufferType[]{READ_IO_BUFFER, WRITE_ENCODING_BUFFER, WRITE_CONCAT_BUFFER, BASE64_CODEC_BUFFER};
      BASE64_CODEC_BUFFER("BASE64_CODEC_BUFFER", 3, 2000),
      READ_IO_BUFFER("READ_IO_BUFFER", 0, 4000),
      WRITE_CONCAT_BUFFER("WRITE_CONCAT_BUFFER", 2, 2000),
      WRITE_ENCODING_BUFFER("WRITE_ENCODING_BUFFER", 1, 4000);
      protected final int size;


      private ByteBufferType(String var1, int var2, int var3) {
         this.size = var3;
      }
   }

   public static enum CharBufferType {

      // $FF: synthetic field
      private static final BufferRecycler.CharBufferType[] $VALUES = new BufferRecycler.CharBufferType[]{TOKEN_BUFFER, CONCAT_BUFFER, TEXT_BUFFER, NAME_COPY_BUFFER};
      CONCAT_BUFFER("CONCAT_BUFFER", 1, 2000),
      NAME_COPY_BUFFER("NAME_COPY_BUFFER", 3, 200),
      TEXT_BUFFER("TEXT_BUFFER", 2, 200),
      TOKEN_BUFFER("TOKEN_BUFFER", 0, 2000);
      protected final int size;


      private CharBufferType(String var1, int var2, int var3) {
         this.size = var3;
      }
   }
}
