package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
public class DataBufferRef {

   @KeepForSdk
   protected final DataHolder mDataHolder;
   @KeepForSdk
   protected int mDataRow;
   private int zalm;


   @KeepForSdk
   public DataBufferRef(DataHolder var1, int var2) {
      this.mDataHolder = (DataHolder)Preconditions.checkNotNull(var1);
      this.zag(var2);
   }

   @KeepForSdk
   protected void copyToBuffer(String var1, CharArrayBuffer var2) {
      this.mDataHolder.zaa(var1, this.mDataRow, this.zalm, var2);
   }

   public boolean equals(Object var1) {
      if(var1 instanceof DataBufferRef) {
         DataBufferRef var2 = (DataBufferRef)var1;
         return Objects.equal(Integer.valueOf(var2.mDataRow), Integer.valueOf(this.mDataRow)) && Objects.equal(Integer.valueOf(var2.zalm), Integer.valueOf(this.zalm)) && var2.mDataHolder == this.mDataHolder;
      } else {
         return false;
      }
   }

   @KeepForSdk
   protected boolean getBoolean(String var1) {
      return this.mDataHolder.getBoolean(var1, this.mDataRow, this.zalm);
   }

   @KeepForSdk
   protected byte[] getByteArray(String var1) {
      return this.mDataHolder.getByteArray(var1, this.mDataRow, this.zalm);
   }

   @KeepForSdk
   protected int getDataRow() {
      return this.mDataRow;
   }

   @KeepForSdk
   protected double getDouble(String var1) {
      return this.mDataHolder.zab(var1, this.mDataRow, this.zalm);
   }

   @KeepForSdk
   protected float getFloat(String var1) {
      return this.mDataHolder.zaa(var1, this.mDataRow, this.zalm);
   }

   @KeepForSdk
   protected int getInteger(String var1) {
      return this.mDataHolder.getInteger(var1, this.mDataRow, this.zalm);
   }

   @KeepForSdk
   protected long getLong(String var1) {
      return this.mDataHolder.getLong(var1, this.mDataRow, this.zalm);
   }

   @KeepForSdk
   protected String getString(String var1) {
      return this.mDataHolder.getString(var1, this.mDataRow, this.zalm);
   }

   @KeepForSdk
   public boolean hasColumn(String var1) {
      return this.mDataHolder.hasColumn(var1);
   }

   @KeepForSdk
   protected boolean hasNull(String var1) {
      return this.mDataHolder.hasNull(var1, this.mDataRow, this.zalm);
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.mDataRow), Integer.valueOf(this.zalm), this.mDataHolder});
   }

   @KeepForSdk
   public boolean isDataValid() {
      return !this.mDataHolder.isClosed();
   }

   @KeepForSdk
   protected Uri parseUri(String var1) {
      var1 = this.mDataHolder.getString(var1, this.mDataRow, this.zalm);
      return var1 == null?null:Uri.parse(var1);
   }

   protected final void zag(int var1) {
      boolean var2;
      if(var1 >= 0 && var1 < this.mDataHolder.getCount()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2);
      this.mDataRow = var1;
      this.zalm = this.mDataHolder.getWindowIndex(this.mDataRow);
   }
}
