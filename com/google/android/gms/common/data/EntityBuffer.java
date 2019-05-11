package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;

@KeepForSdk
public abstract class EntityBuffer<T extends Object> extends AbstractDataBuffer<T> {

   private boolean zamd = false;
   private ArrayList<Integer> zame;


   @KeepForSdk
   protected EntityBuffer(DataHolder var1) {
      super(var1);
   }

   private final void zacb() {
      // $FF: Couldn't be decompiled
   }

   private final int zah(int var1) {
      if(var1 >= 0 && var1 < this.zame.size()) {
         return ((Integer)this.zame.get(var1)).intValue();
      } else {
         StringBuilder var2 = new StringBuilder(53);
         var2.append("Position ");
         var2.append(var1);
         var2.append(" is out of bounds for this buffer");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   @KeepForSdk
   public final T get(int var1) {
      this.zacb();
      int var4 = this.zah(var1);
      byte var3 = 0;
      int var2 = var3;
      if(var1 >= 0) {
         if(var1 == this.zame.size()) {
            var2 = var3;
         } else {
            if(var1 == this.zame.size() - 1) {
               var2 = this.mDataHolder.getCount() - ((Integer)this.zame.get(var1)).intValue();
            } else {
               var2 = ((Integer)this.zame.get(var1 + 1)).intValue() - ((Integer)this.zame.get(var1)).intValue();
            }

            if(var2 == 1) {
               var1 = this.zah(var1);
               int var5 = this.mDataHolder.getWindowIndex(var1);
               String var6 = this.getChildDataMarkerColumn();
               if(var6 != null && this.mDataHolder.getString(var6, var1, var5) == null) {
                  var2 = var3;
               }
            }
         }
      }

      return this.getEntry(var4, var2);
   }

   @KeepForSdk
   protected String getChildDataMarkerColumn() {
      return null;
   }

   @KeepForSdk
   public int getCount() {
      this.zacb();
      return this.zame.size();
   }

   @KeepForSdk
   protected abstract T getEntry(int var1, int var2);

   @KeepForSdk
   protected abstract String getPrimaryDataMarkerColumn();
}
