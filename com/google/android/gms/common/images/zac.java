package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.zaa;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.base.zaj;
import java.lang.ref.WeakReference;

public final class zac extends zaa {

   private WeakReference<ImageView> zanb;


   public zac(ImageView var1, int var2) {
      super((Uri)null, var2);
      Asserts.checkNotNull(var1);
      this.zanb = new WeakReference(var1);
   }

   public zac(ImageView var1, Uri var2) {
      super(var2, 0);
      Asserts.checkNotNull(var1);
      this.zanb = new WeakReference(var1);
   }

   public final boolean equals(Object var1) {
      if(!(var1 instanceof zac)) {
         return false;
      } else if(this == var1) {
         return true;
      } else {
         zac var2 = (zac)var1;
         ImageView var3 = (ImageView)this.zanb.get();
         ImageView var4 = (ImageView)var2.zanb.get();
         return var4 != null && var3 != null && Objects.equal(var4, var3);
      }
   }

   public final int hashCode() {
      return 0;
   }

   protected final void zaa(Drawable var1, boolean var2, boolean var3, boolean var4) {
      ImageView var11 = (ImageView)this.zanb.get();
      if(var11 != null) {
         int var6 = 0;
         boolean var5;
         if(!var3 && !var4) {
            var5 = true;
         } else {
            var5 = false;
         }

         if(var5 && var11 instanceof zaj) {
            int var7 = zaj.zach();
            if(this.zamw != 0 && var7 == this.zamw) {
               return;
            }
         }

         var2 = this.zaa(var2, var3);
         Object var9 = null;
         Object var8 = var1;
         if(var2) {
            Drawable var10 = var11.getDrawable();
            Drawable var13;
            if(var10 != null) {
               var13 = var10;
               if(var10 instanceof gc) {
                  var13 = ((gc)var10).a();
               }
            } else {
               var13 = null;
            }

            var8 = new gc(var13, var1);
         }

         var11.setImageDrawable((Drawable)var8);
         if(var11 instanceof zaj) {
            Uri var12 = (Uri)var9;
            if(var4) {
               var12 = this.zamu.uri;
            }

            zaj.zaa(var12);
            if(var5) {
               var6 = this.zamw;
            }

            zaj.zai(var6);
         }

         if(var2) {
            ((gc)var8).a(250);
         }
      }

   }
}
