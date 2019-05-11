package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataBufferIterator;
import com.google.android.gms.common.data.DataBufferRef;
import java.util.NoSuchElementException;

@KeepForSdk
public class SingleRefDataBufferIterator<T extends Object> extends DataBufferIterator<T> {

   private T zamf;


   public SingleRefDataBufferIterator(DataBuffer<T> var1) {
      super(var1);
   }

   public T next() {
      if(!this.hasNext()) {
         int var1 = this.zalk;
         StringBuilder var4 = new StringBuilder(46);
         var4.append("Cannot advance the iterator beyond ");
         var4.append(var1);
         throw new NoSuchElementException(var4.toString());
      } else {
         ++this.zalk;
         if(this.zalk == 0) {
            this.zamf = this.zalj.get(0);
            if(!(this.zamf instanceof DataBufferRef)) {
               String var2 = String.valueOf(this.zamf.getClass());
               StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 44);
               var3.append("DataBuffer reference of type ");
               var3.append(var2);
               var3.append(" is not movable");
               throw new IllegalStateException(var3.toString());
            }
         } else {
            ((DataBufferRef)this.zamf).zag(this.zalk);
         }

         return this.zamf;
      }
   }
}
