package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.zab;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zak;

public abstract class zaa {

   final zab zamu;
   private int zamv = 0;
   protected int zamw = 0;
   private boolean zamx = false;
   private boolean zamy = true;
   private boolean zamz = false;
   private boolean zana = true;


   public zaa(Uri var1, int var2) {
      this.zamu = new zab(var1);
      this.zamw = var2;
   }

   final void zaa(Context var1, Bitmap var2, boolean var3) {
      Asserts.checkNotNull(var2);
      this.zaa(new BitmapDrawable(var1.getResources(), var2), var3, false, true);
   }

   final void zaa(Context var1, zak var2) {
      if(this.zana) {
         this.zaa((Drawable)null, false, true, false);
      }

   }

   final void zaa(Context var1, zak var2, boolean var3) {
      Drawable var5;
      if(this.zamw != 0) {
         int var4 = this.zamw;
         var5 = var1.getResources().getDrawable(var4);
      } else {
         var5 = null;
      }

      this.zaa(var5, var3, false, false);
   }

   protected abstract void zaa(Drawable var1, boolean var2, boolean var3, boolean var4);

   protected final boolean zaa(boolean var1, boolean var2) {
      return this.zamy && !var2 && !var1;
   }
}
