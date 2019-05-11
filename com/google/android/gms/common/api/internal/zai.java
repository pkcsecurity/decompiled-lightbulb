package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

public final class zai<O extends Object & Api.ApiOptions> {

   private final Api<O> mApi;
   private final O zabh;
   private final boolean zact;
   private final int zacu;


   private zai(Api<O> var1) {
      this.zact = true;
      this.mApi = var1;
      this.zabh = null;
      this.zacu = System.identityHashCode(this);
   }

   private zai(Api<O> var1, O var2) {
      this.zact = false;
      this.mApi = var1;
      this.zabh = var2;
      this.zacu = Objects.hashCode(new Object[]{this.mApi, this.zabh});
   }

   public static <O extends Object & Api.ApiOptions> zai<O> zaa(Api<O> var0) {
      return new zai(var0);
   }

   public static <O extends Object & Api.ApiOptions> zai<O> zaa(Api<O> var0, O var1) {
      return new zai(var0, var1);
   }

   public final boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof zai)) {
         return false;
      } else {
         zai var2 = (zai)var1;
         return !this.zact && !var2.zact && Objects.equal(this.mApi, var2.mApi) && Objects.equal(this.zabh, var2.zabh);
      }
   }

   public final int hashCode() {
      return this.zacu;
   }

   public final String zan() {
      return this.mApi.getName();
   }
}
