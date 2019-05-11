package com.google.android.gms.common.util.concurrent;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.zza;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@KeepForSdk
public class NamedThreadFactory implements ThreadFactory {

   private final String name;
   private final int priority;
   private final ThreadFactory zzhp;


   @KeepForSdk
   public NamedThreadFactory(String var1) {
      this(var1, 0);
   }

   private NamedThreadFactory(String var1, int var2) {
      this.zzhp = Executors.defaultThreadFactory();
      this.name = (String)Preconditions.checkNotNull(var1, "Name must not be null");
      this.priority = 0;
   }

   public Thread newThread(Runnable var1) {
      Thread var2 = this.zzhp.newThread(new zza(var1, 0));
      var2.setName(this.name);
      return var2;
   }
}
