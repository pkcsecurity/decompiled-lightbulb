package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

@KeepForSdk
public class DataBufferIterator<T extends Object> implements Iterator<T> {

   protected final DataBuffer<T> zalj;
   protected int zalk;


   public DataBufferIterator(DataBuffer<T> var1) {
      this.zalj = (DataBuffer)Preconditions.checkNotNull(var1);
      this.zalk = -1;
   }

   public boolean hasNext() {
      return this.zalk < this.zalj.getCount() - 1;
   }

   public T next() {
      int var1;
      if(!this.hasNext()) {
         var1 = this.zalk;
         StringBuilder var3 = new StringBuilder(46);
         var3.append("Cannot advance the iterator beyond ");
         var3.append(var1);
         throw new NoSuchElementException(var3.toString());
      } else {
         DataBuffer var2 = this.zalj;
         var1 = this.zalk + 1;
         this.zalk = var1;
         return var2.get(var1);
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
   }
}
