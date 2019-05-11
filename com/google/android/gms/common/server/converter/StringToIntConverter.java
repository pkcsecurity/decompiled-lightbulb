package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.converter.zac;
import com.google.android.gms.common.server.converter.zad;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@KeepForSdk
@SafeParcelable.Class(
   creator = "StringToIntConverterCreator"
)
public final class StringToIntConverter extends AbstractSafeParcelable implements FastJsonResponse.FieldConverter<String, Integer> {

   public static final Creator<StringToIntConverter> CREATOR = new zac();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   private final HashMap<String, Integer> zapl;
   private final SparseArray<String> zapm;
   @SafeParcelable.Field(
      getter = "getSerializedMap",
      id = 2
   )
   private final ArrayList<StringToIntConverter.zaa> zapn;


   @KeepForSdk
   public StringToIntConverter() {
      this.zale = 1;
      this.zapl = new HashMap();
      this.zapm = new SparseArray();
      this.zapn = null;
   }

   @SafeParcelable.Constructor
   StringToIntConverter(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) ArrayList<StringToIntConverter.zaa> var2) {
      this.zale = var1;
      this.zapl = new HashMap();
      this.zapm = new SparseArray();
      this.zapn = null;
      var2 = (ArrayList)var2;
      int var3 = var2.size();
      var1 = 0;

      while(var1 < var3) {
         Object var4 = var2.get(var1);
         ++var1;
         StringToIntConverter.zaa var5 = (StringToIntConverter.zaa)var4;
         this.add(var5.zapo, var5.zapp);
      }

   }

   @KeepForSdk
   public final StringToIntConverter add(String var1, int var2) {
      this.zapl.put(var1, Integer.valueOf(var2));
      this.zapm.put(var2, var1);
      return this;
   }

   // $FF: synthetic method
   public final Object convert(Object var1) {
      String var3 = (String)var1;
      Integer var2 = (Integer)this.zapl.get(var3);
      Integer var4 = var2;
      if(var2 == null) {
         var4 = (Integer)this.zapl.get("gms_unknown");
      }

      return var4;
   }

   // $FF: synthetic method
   public final Object convertBack(Object var1) {
      Integer var2 = (Integer)var1;
      String var3 = (String)this.zapm.get(var2.intValue());
      return var3 == null && this.zapl.containsKey("gms_unknown")?"gms_unknown":var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.zapl.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var3.add(new StringToIntConverter.zaa(var5, ((Integer)this.zapl.get(var5)).intValue()));
      }

      SafeParcelWriter.writeTypedList(var1, 2, var3, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final int zacj() {
      return 7;
   }

   public final int zack() {
      return 0;
   }

   @SafeParcelable.Class(
      creator = "StringToIntConverterEntryCreator"
   )
   public static final class zaa extends AbstractSafeParcelable {

      public static final Creator<StringToIntConverter.zaa> CREATOR = new zad();
      @SafeParcelable.VersionField(
         id = 1
      )
      private final int versionCode;
      @SafeParcelable.Field(
         id = 2
      )
      final String zapo;
      @SafeParcelable.Field(
         id = 3
      )
      final int zapp;


      @SafeParcelable.Constructor
      zaa(
         @SafeParcelable.Param(
            id = 1
         ) int var1, 
         @SafeParcelable.Param(
            id = 2
         ) String var2, 
         @SafeParcelable.Param(
            id = 3
         ) int var3) {
         this.versionCode = var1;
         this.zapo = var2;
         this.zapp = var3;
      }

      zaa(String var1, int var2) {
         this.versionCode = 1;
         this.zapo = var1;
         this.zapp = var2;
      }

      public final void writeToParcel(Parcel var1, int var2) {
         var2 = SafeParcelWriter.beginObjectHeader(var1);
         SafeParcelWriter.writeInt(var1, 1, this.versionCode);
         SafeParcelWriter.writeString(var1, 2, this.zapo, false);
         SafeParcelWriter.writeInt(var1, 3, this.zapp);
         SafeParcelWriter.finishObjectHeader(var1, var2);
      }
   }
}
