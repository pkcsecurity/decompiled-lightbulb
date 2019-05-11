package com.google.android.gms.common.sqlite;

import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public class CursorWrapper extends android.database.CursorWrapper implements CrossProcessCursor {

   private AbstractWindowedCursor zzez;


   @KeepForSdk
   public CursorWrapper(Cursor var1) {
      super(var1);

      for(int var2 = 0; var2 < 10 && var1 instanceof android.database.CursorWrapper; ++var2) {
         var1 = ((android.database.CursorWrapper)var1).getWrappedCursor();
      }

      if(!(var1 instanceof AbstractWindowedCursor)) {
         String var3 = String.valueOf(var1.getClass().getName());
         if(var3.length() != 0) {
            var3 = "Unknown type: ".concat(var3);
         } else {
            var3 = new String("Unknown type: ");
         }

         throw new IllegalArgumentException(var3);
      } else {
         this.zzez = (AbstractWindowedCursor)var1;
      }
   }

   @KeepForSdk
   public void fillWindow(int var1, CursorWindow var2) {
      this.zzez.fillWindow(var1, var2);
   }

   @KeepForSdk
   public CursorWindow getWindow() {
      return this.zzez.getWindow();
   }

   // $FF: synthetic method
   public Cursor getWrappedCursor() {
      return this.zzez;
   }

   public boolean onMove(int var1, int var2) {
      return this.zzez.onMove(var1, var2);
   }

   @KeepForSdk
   public void setWindow(CursorWindow var1) {
      this.zzez.setWindow(var1);
   }
}
