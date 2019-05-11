package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zaa implements Creator<BitmapTeleporter> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var4 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      ParcelFileDescriptor var6 = null;
      int var2 = 0;

      while(var1.dataPosition() < var4) {
         int var5 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var5)) {
         case 1:
            var3 = SafeParcelReader.readInt(var1, var5);
            break;
         case 2:
            var6 = (ParcelFileDescriptor)SafeParcelReader.createParcelable(var1, var5, ParcelFileDescriptor.CREATOR);
            break;
         case 3:
            var2 = SafeParcelReader.readInt(var1, var5);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var4);
      return new BitmapTeleporter(var3, var6, var2);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new BitmapTeleporter[var1];
   }
}
