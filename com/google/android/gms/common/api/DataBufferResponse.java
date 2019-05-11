package com.google.android.gms.common.api;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import java.util.Iterator;

@KeepForSdk
public class DataBufferResponse<T extends Object, R extends AbstractDataBuffer<T> & Result> extends Response<R> implements DataBuffer<T> {

   @KeepForSdk
   public DataBufferResponse() {}

   @KeepForSdk
   public DataBufferResponse(@NonNull R var1) {
      super(var1);
   }

   public void close() {
      ((AbstractDataBuffer)this.getResult()).close();
   }

   public T get(int var1) {
      return ((AbstractDataBuffer)this.getResult()).get(var1);
   }

   public int getCount() {
      return ((AbstractDataBuffer)this.getResult()).getCount();
   }

   public Bundle getMetadata() {
      return ((AbstractDataBuffer)this.getResult()).getMetadata();
   }

   public boolean isClosed() {
      return ((AbstractDataBuffer)this.getResult()).isClosed();
   }

   public Iterator<T> iterator() {
      return ((AbstractDataBuffer)this.getResult()).iterator();
   }

   public void release() {
      ((AbstractDataBuffer)this.getResult()).release();
   }

   public Iterator<T> singleRefIterator() {
      return ((AbstractDataBuffer)this.getResult()).singleRefIterator();
   }
}
