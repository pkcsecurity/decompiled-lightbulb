package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataBufferIterator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.SingleRefDataBufferIterator;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T extends Object> implements DataBuffer<T> {

   protected final DataHolder mDataHolder;


   @KeepForSdk
   protected AbstractDataBuffer(DataHolder var1) {
      this.mDataHolder = var1;
   }

   @Deprecated
   public final void close() {
      this.release();
   }

   public abstract T get(int var1);

   public int getCount() {
      return this.mDataHolder == null?0:this.mDataHolder.getCount();
   }

   public Bundle getMetadata() {
      return this.mDataHolder.getMetadata();
   }

   @Deprecated
   public boolean isClosed() {
      return this.mDataHolder == null || this.mDataHolder.isClosed();
   }

   public Iterator<T> iterator() {
      return new DataBufferIterator(this);
   }

   public void release() {
      if(this.mDataHolder != null) {
         this.mDataHolder.close();
      }

   }

   public Iterator<T> singleRefIterator() {
      return new SingleRefDataBufferIterator(this);
   }
}
