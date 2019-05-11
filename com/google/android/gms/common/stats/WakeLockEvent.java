package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.stats.StatsEvent;
import com.google.android.gms.common.stats.zza;
import java.util.List;

@SafeParcelable.Class(
   creator = "WakeLockEventCreator"
)
public final class WakeLockEvent extends StatsEvent {

   public static final Creator<WakeLockEvent> CREATOR = new zza();
   @SafeParcelable.Field(
      getter = "getTimeout",
      id = 16
   )
   private final long mTimeout;
   @SafeParcelable.Field(
      getter = "getTimeMillis",
      id = 2
   )
   private final long zzfo;
   @SafeParcelable.Field(
      getter = "getEventType",
      id = 11
   )
   private int zzfp;
   @SafeParcelable.Field(
      getter = "getWakeLockName",
      id = 4
   )
   private final String zzfq;
   @SafeParcelable.Field(
      getter = "getSecondaryWakeLockName",
      id = 10
   )
   private final String zzfr;
   @SafeParcelable.Field(
      getter = "getCodePackage",
      id = 17
   )
   private final String zzfs;
   @SafeParcelable.Field(
      getter = "getWakeLockType",
      id = 5
   )
   private final int zzft;
   @SafeParcelable.Field(
      getter = "getCallingPackages",
      id = 6
   )
   private final List<String> zzfu;
   @SafeParcelable.Field(
      getter = "getEventKey",
      id = 12
   )
   private final String zzfv;
   @SafeParcelable.Field(
      getter = "getElapsedRealtime",
      id = 8
   )
   private final long zzfw;
   @SafeParcelable.Field(
      getter = "getDeviceState",
      id = 14
   )
   private int zzfx;
   @SafeParcelable.Field(
      getter = "getHostPackage",
      id = 13
   )
   private final String zzfy;
   @SafeParcelable.Field(
      getter = "getBeginPowerPercentage",
      id = 15
   )
   private final float zzfz;
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zzg;
   private long zzga;


   @SafeParcelable.Constructor
   WakeLockEvent(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) long var2, 
      @SafeParcelable.Param(
         id = 11
      ) int var4, 
      @SafeParcelable.Param(
         id = 4
      ) String var5, 
      @SafeParcelable.Param(
         id = 5
      ) int var6, 
      @SafeParcelable.Param(
         id = 6
      ) List<String> var7, 
      @SafeParcelable.Param(
         id = 12
      ) String var8, 
      @SafeParcelable.Param(
         id = 8
      ) long var9, 
      @SafeParcelable.Param(
         id = 14
      ) int var11, 
      @SafeParcelable.Param(
         id = 10
      ) String var12, 
      @SafeParcelable.Param(
         id = 13
      ) String var13, 
      @SafeParcelable.Param(
         id = 15
      ) float var14, 
      @SafeParcelable.Param(
         id = 16
      ) long var15, 
      @SafeParcelable.Param(
         id = 17
      ) String var17) {
      this.zzg = var1;
      this.zzfo = var2;
      this.zzfp = var4;
      this.zzfq = var5;
      this.zzfr = var12;
      this.zzfs = var17;
      this.zzft = var6;
      this.zzga = -1L;
      this.zzfu = var7;
      this.zzfv = var8;
      this.zzfw = var9;
      this.zzfx = var11;
      this.zzfy = var13;
      this.zzfz = var14;
      this.mTimeout = var15;
   }

   public WakeLockEvent(long var1, int var3, String var4, int var5, List<String> var6, String var7, long var8, int var10, String var11, String var12, float var13, long var14, String var16) {
      this(2, var1, var3, var4, var5, var6, var7, var8, var10, var11, var12, var13, var14, var16);
   }

   public final int getEventType() {
      return this.zzfp;
   }

   public final long getTimeMillis() {
      return this.zzfo;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzg);
      SafeParcelWriter.writeLong(var1, 2, this.getTimeMillis());
      SafeParcelWriter.writeString(var1, 4, this.zzfq, false);
      SafeParcelWriter.writeInt(var1, 5, this.zzft);
      SafeParcelWriter.writeStringList(var1, 6, this.zzfu, false);
      SafeParcelWriter.writeLong(var1, 8, this.zzfw);
      SafeParcelWriter.writeString(var1, 10, this.zzfr, false);
      SafeParcelWriter.writeInt(var1, 11, this.getEventType());
      SafeParcelWriter.writeString(var1, 12, this.zzfv, false);
      SafeParcelWriter.writeString(var1, 13, this.zzfy, false);
      SafeParcelWriter.writeInt(var1, 14, this.zzfx);
      SafeParcelWriter.writeFloat(var1, 15, this.zzfz);
      SafeParcelWriter.writeLong(var1, 16, this.mTimeout);
      SafeParcelWriter.writeString(var1, 17, this.zzfs, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final long zzu() {
      return this.zzga;
   }

   public final String zzv() {
      String var8 = this.zzfq;
      int var2 = this.zzft;
      String var4;
      if(this.zzfu == null) {
         var4 = "";
      } else {
         var4 = TextUtils.join(",", this.zzfu);
      }

      int var3 = this.zzfx;
      String var5;
      if(this.zzfr == null) {
         var5 = "";
      } else {
         var5 = this.zzfr;
      }

      String var6;
      if(this.zzfy == null) {
         var6 = "";
      } else {
         var6 = this.zzfy;
      }

      float var1 = this.zzfz;
      String var7;
      if(this.zzfs == null) {
         var7 = "";
      } else {
         var7 = this.zzfs;
      }

      StringBuilder var9 = new StringBuilder(String.valueOf(var8).length() + 45 + String.valueOf(var4).length() + String.valueOf(var5).length() + String.valueOf(var6).length() + String.valueOf(var7).length());
      var9.append("\t");
      var9.append(var8);
      var9.append("\t");
      var9.append(var2);
      var9.append("\t");
      var9.append(var4);
      var9.append("\t");
      var9.append(var3);
      var9.append("\t");
      var9.append(var5);
      var9.append("\t");
      var9.append(var6);
      var9.append("\t");
      var9.append(var1);
      var9.append("\t");
      var9.append(var7);
      return var9.toString();
   }
}
