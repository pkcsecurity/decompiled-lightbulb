package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
public class DataBufferSafeParcelable<T extends Object & SafeParcelable> extends AbstractDataBuffer<T> {

   private static final String[] zaln = new String[]{"data"};
   private final Creator<T> zalo;


   @KeepForSdk
   public DataBufferSafeParcelable(DataHolder var1, Creator<T> var2) {
      super(var1);
      this.zalo = var2;
   }

   @KeepForSdk
   public static <T extends Object & SafeParcelable> void addValue(DataHolder.Builder var0, T var1) {
      Parcel var2 = Parcel.obtain();
      var1.writeToParcel(var2, 0);
      ContentValues var3 = new ContentValues();
      var3.put("data", var2.marshall());
      var0.withRow(var3);
      var2.recycle();
   }

   @KeepForSdk
   public static DataHolder.Builder buildDataHolder() {
      return DataHolder.builder(zaln);
   }

   @KeepForSdk
   public T get(int var1) {
      byte[] var3 = this.mDataHolder.getByteArray("data", var1, this.mDataHolder.getWindowIndex(var1));
      Parcel var2 = Parcel.obtain();
      var2.unmarshall(var3, 0, var3.length);
      var2.setDataPosition(0);
      SafeParcelable var4 = (SafeParcelable)this.zalo.createFromParcel(var2);
      var2.recycle();
      return var4;
   }
}
