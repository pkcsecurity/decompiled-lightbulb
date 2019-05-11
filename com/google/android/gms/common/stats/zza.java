package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.stats.WakeLockEvent;
import java.util.List;

public final class zza implements Creator<WakeLockEvent> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var7 = SafeParcelReader.validateObjectHeader(var1);
      long var13 = 0L;
      long var9 = var13;
      long var11 = var13;
      String var20 = null;
      Object var15 = var20;
      String var16 = var20;
      String var17 = var20;
      String var18 = var20;
      String var19 = var20;
      int var6 = 0;
      int var5 = 0;
      int var4 = 0;
      int var3 = 0;
      float var2 = 0.0F;

      while(var1.dataPosition() < var7) {
         int var8 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var8)) {
         case 1:
            var6 = SafeParcelReader.readInt(var1, var8);
            break;
         case 2:
            var13 = SafeParcelReader.readLong(var1, var8);
            break;
         case 3:
         case 7:
         case 9:
         default:
            SafeParcelReader.skipUnknownField(var1, var8);
            break;
         case 4:
            var20 = SafeParcelReader.createString(var1, var8);
            break;
         case 5:
            var4 = SafeParcelReader.readInt(var1, var8);
            break;
         case 6:
            var15 = SafeParcelReader.createStringList(var1, var8);
            break;
         case 8:
            var9 = SafeParcelReader.readLong(var1, var8);
            break;
         case 10:
            var17 = SafeParcelReader.createString(var1, var8);
            break;
         case 11:
            var5 = SafeParcelReader.readInt(var1, var8);
            break;
         case 12:
            var16 = SafeParcelReader.createString(var1, var8);
            break;
         case 13:
            var18 = SafeParcelReader.createString(var1, var8);
            break;
         case 14:
            var3 = SafeParcelReader.readInt(var1, var8);
            break;
         case 15:
            var2 = SafeParcelReader.readFloat(var1, var8);
            break;
         case 16:
            var11 = SafeParcelReader.readLong(var1, var8);
            break;
         case 17:
            var19 = SafeParcelReader.createString(var1, var8);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var7);
      return new WakeLockEvent(var6, var13, var5, var20, var4, (List)var15, var16, var9, var3, var17, var18, var2, var11, var19);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new WakeLockEvent[var1];
   }
}
