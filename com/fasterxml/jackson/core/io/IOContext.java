package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;

public final class IOContext {

   protected byte[] _base64Buffer = null;
   protected final BufferRecycler _bufferRecycler;
   protected char[] _concatCBuffer = null;
   protected JsonEncoding _encoding;
   protected final boolean _managedResource;
   protected char[] _nameCopyBuffer = null;
   protected byte[] _readIOBuffer = null;
   protected final Object _sourceRef;
   protected char[] _tokenCBuffer = null;
   protected byte[] _writeEncodingBuffer = null;


   public IOContext(BufferRecycler var1, Object var2, boolean var3) {
      this._bufferRecycler = var1;
      this._sourceRef = var2;
      this._managedResource = var3;
   }

   private final void _verifyAlloc(Object var1) {
      if(var1 != null) {
         throw new IllegalStateException("Trying to call same allocXxx() method second time");
      }
   }

   private final void _verifyRelease(Object var1, Object var2) {
      if(var1 != var2) {
         throw new IllegalArgumentException("Trying to release buffer not owned by the context");
      }
   }

   public byte[] allocBase64Buffer() {
      this._verifyAlloc(this._base64Buffer);
      byte[] var1 = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER);
      this._base64Buffer = var1;
      return var1;
   }

   public char[] allocConcatBuffer() {
      this._verifyAlloc(this._concatCBuffer);
      char[] var1 = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER);
      this._concatCBuffer = var1;
      return var1;
   }

   public char[] allocNameCopyBuffer(int var1) {
      this._verifyAlloc(this._nameCopyBuffer);
      char[] var2 = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, var1);
      this._nameCopyBuffer = var2;
      return var2;
   }

   public byte[] allocReadIOBuffer() {
      this._verifyAlloc(this._readIOBuffer);
      byte[] var1 = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER);
      this._readIOBuffer = var1;
      return var1;
   }

   public char[] allocTokenBuffer() {
      this._verifyAlloc(this._tokenCBuffer);
      char[] var1 = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER);
      this._tokenCBuffer = var1;
      return var1;
   }

   public byte[] allocWriteEncodingBuffer() {
      this._verifyAlloc(this._writeEncodingBuffer);
      byte[] var1 = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER);
      this._writeEncodingBuffer = var1;
      return var1;
   }

   public TextBuffer constructTextBuffer() {
      return new TextBuffer(this._bufferRecycler);
   }

   public JsonEncoding getEncoding() {
      return this._encoding;
   }

   public Object getSourceReference() {
      return this._sourceRef;
   }

   public boolean isResourceManaged() {
      return this._managedResource;
   }

   public void releaseBase64Buffer(byte[] var1) {
      if(var1 != null) {
         this._verifyRelease(var1, this._base64Buffer);
         this._base64Buffer = null;
         this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER, var1);
      }

   }

   public void releaseConcatBuffer(char[] var1) {
      if(var1 != null) {
         this._verifyRelease(var1, this._concatCBuffer);
         this._concatCBuffer = null;
         this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER, var1);
      }

   }

   public void releaseNameCopyBuffer(char[] var1) {
      if(var1 != null) {
         this._verifyRelease(var1, this._nameCopyBuffer);
         this._nameCopyBuffer = null;
         this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, var1);
      }

   }

   public void releaseReadIOBuffer(byte[] var1) {
      if(var1 != null) {
         this._verifyRelease(var1, this._readIOBuffer);
         this._readIOBuffer = null;
         this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER, var1);
      }

   }

   public void releaseTokenBuffer(char[] var1) {
      if(var1 != null) {
         this._verifyRelease(var1, this._tokenCBuffer);
         this._tokenCBuffer = null;
         this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER, var1);
      }

   }

   public void releaseWriteEncodingBuffer(byte[] var1) {
      if(var1 != null) {
         this._verifyRelease(var1, this._writeEncodingBuffer);
         this._writeEncodingBuffer = null;
         this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER, var1);
      }

   }

   public void setEncoding(JsonEncoding var1) {
      this._encoding = var1;
   }
}
