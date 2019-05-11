package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.response.FastJsonResponse;

public final class zai implements Creator<FastJsonResponse.Field> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var6 = SafeParcelReader.validateObjectHeader(var1);
      String var12 = null;
      String var10 = var12;
      Object var11 = var12;
      int var5 = 0;
      int var4 = 0;
      boolean var9 = false;
      int var3 = 0;
      boolean var8 = false;
      int var2 = 0;

      while(var1.dataPosition() < var6) {
         int var7 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var7)) {
         case 1:
            var5 = SafeParcelReader.readInt(var1, var7);
            break;
         case 2:
            var4 = SafeParcelReader.readInt(var1, var7);
            break;
         case 3:
            var9 = SafeParcelReader.readBoolean(var1, var7);
            break;
         case 4:
            var3 = SafeParcelReader.readInt(var1, var7);
            break;
         case 5:
            var8 = SafeParcelReader.readBoolean(var1, var7);
            break;
         case 6:
            var12 = SafeParcelReader.createString(var1, var7);
            break;
         case 7:
            var2 = SafeParcelReader.readInt(var1, var7);
            break;
         case 8:
            var10 = SafeParcelReader.createString(var1, var7);
            break;
         case 9:
            var11 = (com.google.android.gms.common.server.converter.zaa)SafeParcelReader.createParcelable(var1, var7, com.google.android.gms.common.server.converter.zaa.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var6);
      return new FastJsonResponse.Field(var5, var4, var9, var3, var8, var12, var2, var10, (com.google.android.gms.common.server.converter.zaa)var11);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new FastJsonResponse.Field[var1];
   }
}
