package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.common.server.converter.zab;
import com.google.android.gms.common.server.response.FastJsonResponse;

@SafeParcelable.Class(
   creator = "ConverterWrapperCreator"
)
public final class zaa extends AbstractSafeParcelable {

   public static final Creator<zaa> CREATOR = new zab();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @SafeParcelable.Field(
      getter = "getStringToIntConverter",
      id = 2
   )
   private final StringToIntConverter zapk;


   @SafeParcelable.Constructor
   zaa(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) StringToIntConverter var2) {
      this.zale = var1;
      this.zapk = var2;
   }

   private zaa(StringToIntConverter var1) {
      this.zale = 1;
      this.zapk = var1;
   }

   public static zaa zaa(FastJsonResponse.FieldConverter<?, ?> var0) {
      if(var0 instanceof StringToIntConverter) {
         return new zaa((StringToIntConverter)var0);
      } else {
         throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
      }
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeParcelable(var1, 2, this.zapk, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final FastJsonResponse.FieldConverter<?, ?> zaci() {
      if(this.zapk != null) {
         return this.zapk;
      } else {
         throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
      }
   }
}
