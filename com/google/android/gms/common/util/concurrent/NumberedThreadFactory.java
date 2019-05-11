package com.google.android.gms.common.util.concurrent;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.zza;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@KeepForSdk
public class NumberedThreadFactory implements ThreadFactory {

   private final int priority;
   private final ThreadFactory zzhp;
   private final String zzhq;
   private final AtomicInteger zzhr;


   @KeepForSdk
   public NumberedThreadFactory(String var1) {
      this(var1, 0);
   }

   private NumberedThreadFactory(String var1, int var2) {
      this.zzhr = new AtomicInteger();
      this.zzhp = Executors.defaultThreadFactory();
      this.zzhq = (String)Preconditions.checkNotNull(var1, "Name must not be null");
      this.priority = 0;
   }

   public Thread newThread(Runnable var1) {
      Thread var5 = this.zzhp.newThread(new zza(var1, 0));
      String var3 = this.zzhq;
      int var2 = this.zzhr.getAndIncrement();
      StringBuilder var4 = new StringBuilder(String.valueOf(var3).length() + 13);
      var4.append(var3);
      var4.append("[");
      var4.append(var2);
      var4.append("]");
      var5.setName(var4.toString());
      return var5;
   }
}
