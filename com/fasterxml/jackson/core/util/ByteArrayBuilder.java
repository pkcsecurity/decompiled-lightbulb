package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.util.BufferRecycler;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder extends OutputStream {

   static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
   private static final int INITIAL_BLOCK_SIZE = 500;
   private static final int MAX_BLOCK_SIZE = 262144;
   private static final byte[] NO_BYTES = new byte[0];
   private final BufferRecycler _bufferRecycler;
   private byte[] _currBlock;
   private int _currBlockPtr;
   private final LinkedList<byte[]> _pastBlocks;
   private int _pastLen;


   public ByteArrayBuilder() {
      this((BufferRecycler)null);
   }

   public ByteArrayBuilder(int var1) {
      this((BufferRecycler)null, var1);
   }

   public ByteArrayBuilder(BufferRecycler var1) {
      this(var1, 500);
   }

   public ByteArrayBuilder(BufferRecycler var1, int var2) {
      this._pastBlocks = new LinkedList();
      this._bufferRecycler = var1;
      if(var1 == null) {
         this._currBlock = new byte[var2];
      } else {
         this._currBlock = var1.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER);
      }
   }

   private void _allocMore() {
      this._pastLen += this._currBlock.length;
      int var2 = Math.max(this._pastLen >> 1, 1000);
      int var1 = var2;
      if(var2 > 262144) {
         var1 = 262144;
      }

      this._pastBlocks.add(this._currBlock);
      this._currBlock = new byte[var1];
      this._currBlockPtr = 0;
   }

   public void append(int var1) {
      if(this._currBlockPtr >= this._currBlock.length) {
         this._allocMore();
      }

      byte[] var3 = this._currBlock;
      int var2 = this._currBlockPtr;
      this._currBlockPtr = var2 + 1;
      var3[var2] = (byte)var1;
   }

   public void appendThreeBytes(int var1) {
      if(this._currBlockPtr + 2 < this._currBlock.length) {
         byte[] var3 = this._currBlock;
         int var2 = this._currBlockPtr;
         this._currBlockPtr = var2 + 1;
         var3[var2] = (byte)(var1 >> 16);
         var3 = this._currBlock;
         var2 = this._currBlockPtr;
         this._currBlockPtr = var2 + 1;
         var3[var2] = (byte)(var1 >> 8);
         var3 = this._currBlock;
         var2 = this._currBlockPtr;
         this._currBlockPtr = var2 + 1;
         var3[var2] = (byte)var1;
      } else {
         this.append(var1 >> 16);
         this.append(var1 >> 8);
         this.append(var1);
      }
   }

   public void appendTwoBytes(int var1) {
      if(this._currBlockPtr + 1 < this._currBlock.length) {
         byte[] var3 = this._currBlock;
         int var2 = this._currBlockPtr;
         this._currBlockPtr = var2 + 1;
         var3[var2] = (byte)(var1 >> 8);
         var3 = this._currBlock;
         var2 = this._currBlockPtr;
         this._currBlockPtr = var2 + 1;
         var3[var2] = (byte)var1;
      } else {
         this.append(var1 >> 8);
         this.append(var1);
      }
   }

   public void close() {}

   public byte[] completeAndCoalesce(int var1) {
      this._currBlockPtr = var1;
      return this.toByteArray();
   }

   public byte[] finishCurrentSegment() {
      this._allocMore();
      return this._currBlock;
   }

   public void flush() {}

   public byte[] getCurrentSegment() {
      return this._currBlock;
   }

   public int getCurrentSegmentLength() {
      return this._currBlockPtr;
   }

   public void release() {
      this.reset();
      if(this._bufferRecycler != null && this._currBlock != null) {
         this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER, this._currBlock);
         this._currBlock = null;
      }

   }

   public void reset() {
      this._pastLen = 0;
      this._currBlockPtr = 0;
      if(!this._pastBlocks.isEmpty()) {
         this._pastBlocks.clear();
      }

   }

   public byte[] resetAndGetFirstSegment() {
      this.reset();
      return this._currBlock;
   }

   public void setCurrentSegmentLength(int var1) {
      this._currBlockPtr = var1;
   }

   public byte[] toByteArray() {
      int var2 = this._pastLen + this._currBlockPtr;
      if(var2 == 0) {
         return NO_BYTES;
      } else {
         byte[] var4 = new byte[var2];
         Iterator var5 = this._pastBlocks.iterator();

         int var1;
         int var3;
         for(var1 = 0; var5.hasNext(); var1 += var3) {
            byte[] var6 = (byte[])var5.next();
            var3 = var6.length;
            System.arraycopy(var6, 0, var4, var1, var3);
         }

         System.arraycopy(this._currBlock, 0, var4, var1, this._currBlockPtr);
         var1 += this._currBlockPtr;
         if(var1 != var2) {
            StringBuilder var7 = new StringBuilder();
            var7.append("Internal error: total len assumed to be ");
            var7.append(var2);
            var7.append(", copied ");
            var7.append(var1);
            var7.append(" bytes");
            throw new RuntimeException(var7.toString());
         } else {
            if(!this._pastBlocks.isEmpty()) {
               this.reset();
            }

            return var4;
         }
      }
   }

   public void write(int var1) {
      this.append(var1);
   }

   public void write(byte[] var1) {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) {
      int var4 = var2;

      while(true) {
         int var6 = Math.min(this._currBlock.length - this._currBlockPtr, var3);
         int var5 = var4;
         var2 = var3;
         if(var6 > 0) {
            System.arraycopy(var1, var4, this._currBlock, this._currBlockPtr, var6);
            var5 = var4 + var6;
            this._currBlockPtr += var6;
            var2 = var3 - var6;
         }

         if(var2 <= 0) {
            return;
         }

         this._allocMore();
         var4 = var5;
         var3 = var2;
      }
   }
}
