package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zae implements Creator<WebImage> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var5 = SafeParcelReader.validateObjectHeader(var1);
      int var4 = 0;
      Uri var7 = null;
      int var3 = 0;
      int var2 = 0;

      while(var1.dataPosition() < var5) {
         int var6 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var6)) {
         case 1:
            var4 = SafeParcelReader.readInt(var1, var6);
            break;
         case 2:
            var7 = (Uri)SafeParcelReader.createParcelable(var1, var6, Uri.CREATOR);
            break;
         case 3:
            var3 = SafeParcelReader.readInt(var1, var6);
            break;
         case 4:
            var2 = SafeParcelReader.readInt(var1, var6);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var6);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var5);
      return new WebImage(var4, var7, var3, var2);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new WebImage[var1];
   }
}
