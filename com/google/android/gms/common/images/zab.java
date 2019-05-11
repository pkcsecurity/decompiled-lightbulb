package com.google.android.gms.common.images;

import android.net.Uri;
import com.google.android.gms.common.internal.Objects;

final class zab {

   public final Uri uri;


   public zab(Uri var1) {
      this.uri = var1;
   }

   public final boolean equals(Object var1) {
      return !(var1 instanceof zab)?false:(this == var1?true:Objects.equal(((zab)var1).uri, this.uri));
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{this.uri});
   }
}
