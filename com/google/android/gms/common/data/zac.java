package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zac implements Creator<DataHolder> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var4 = SafeParcelReader.validateObjectHeader(var1);
      String[] var9 = null;
      Object var7 = var9;
      Object var8 = var9;
      int var3 = 0;
      int var2 = 0;

      while(var1.dataPosition() < var4) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if(var6 != 1000) {
            switch(var6) {
            case 1:
               var9 = SafeParcelReader.createStringArray(var1, var5);
               break;
            case 2:
               var7 = (CursorWindow[])SafeParcelReader.createTypedArray(var1, var5, CursorWindow.CREATOR);
               break;
            case 3:
               var2 = SafeParcelReader.readInt(var1, var5);
               break;
            case 4:
               var8 = SafeParcelReader.createBundle(var1, var5);
               break;
            default:
               SafeParcelReader.skipUnknownField(var1, var5);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var4);
      DataHolder var10 = new DataHolder(var3, var9, (CursorWindow[])var7, var2, (Bundle)var8);
      var10.zaca();
      return var10;
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new DataHolder[var1];
   }
}
