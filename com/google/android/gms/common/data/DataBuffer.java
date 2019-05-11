package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Releasable;
import java.util.Iterator;

public interface DataBuffer<T extends Object> extends Releasable, Iterable<T> {

   @Deprecated
   void close();

   T get(int var1);

   int getCount();

   @KeepForSdk
   Bundle getMetadata();

   @Deprecated
   boolean isClosed();

   Iterator<T> iterator();

   void release();

   Iterator<T> singleRefIterator();
}
