package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.zaa;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import java.lang.ref.WeakReference;

public final class zad extends zaa {

   private WeakReference<ImageManager.OnImageLoadedListener> zanc;


   public zad(ImageManager.OnImageLoadedListener var1, Uri var2) {
      super(var2, 0);
      Asserts.checkNotNull(var1);
      this.zanc = new WeakReference(var1);
   }

   public final boolean equals(Object var1) {
      if(!(var1 instanceof zad)) {
         return false;
      } else if(this == var1) {
         return true;
      } else {
         zad var4 = (zad)var1;
         ImageManager.OnImageLoadedListener var2 = (ImageManager.OnImageLoadedListener)this.zanc.get();
         ImageManager.OnImageLoadedListener var3 = (ImageManager.OnImageLoadedListener)var4.zanc.get();
         return var3 != null && var2 != null && Objects.equal(var3, var2) && Objects.equal(var4.zamu, this.zamu);
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{this.zamu});
   }

   protected final void zaa(Drawable var1, boolean var2, boolean var3, boolean var4) {
      if(!var3) {
         ImageManager.OnImageLoadedListener var5 = (ImageManager.OnImageLoadedListener)this.zanc.get();
         if(var5 != null) {
            var5.onImageLoaded(this.zamu.uri, var1, var4);
         }
      }

   }
}
