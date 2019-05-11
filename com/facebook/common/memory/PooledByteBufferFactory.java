package com.facebook.common.memory;

import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface PooledByteBufferFactory {

   PooledByteBuffer newByteBuffer(int var1);

   PooledByteBuffer newByteBuffer(InputStream var1) throws IOException;

   PooledByteBuffer newByteBuffer(InputStream var1, int var2) throws IOException;

   PooledByteBuffer newByteBuffer(byte[] var1);

   PooledByteBufferOutputStream newOutputStream();

   PooledByteBufferOutputStream newOutputStream(int var1);
}
