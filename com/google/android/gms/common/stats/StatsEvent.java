package com.google.android.gms.common.stats;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@KeepForSdk
public abstract class StatsEvent extends AbstractSafeParcelable implements ReflectedParcelable {

   public abstract int getEventType();

   public abstract long getTimeMillis();

   public String toString() {
      long var2 = this.getTimeMillis();
      int var1 = this.getEventType();
      long var4 = this.zzu();
      String var6 = this.zzv();
      StringBuilder var7 = new StringBuilder(String.valueOf(var6).length() + 53);
      var7.append(var2);
      var7.append("\t");
      var7.append(var1);
      var7.append("\t");
      var7.append(var4);
      var7.append(var6);
      return var7.toString();
   }

   public abstract long zzu();

   public abstract String zzv();

   @KeepForSdk
   public interface Types {

      @KeepForSdk
      int EVENT_TYPE_ACQUIRE_WAKE_LOCK = 7;
      @KeepForSdk
      int EVENT_TYPE_RELEASE_WAKE_LOCK = 8;

   }
}
