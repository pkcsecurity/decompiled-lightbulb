package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Set;

@KeepForSdk
public final class ScopeUtil {

   @KeepForSdk
   public static String[] toScopeString(Set<Scope> var0) {
      Preconditions.checkNotNull(var0, "scopes can\'t be null.");
      Scope[] var3 = (Scope[])var0.toArray(new Scope[var0.size()]);
      Preconditions.checkNotNull(var3, "scopes can\'t be null.");
      String[] var2 = new String[var3.length];

      for(int var1 = 0; var1 < var3.length; ++var1) {
         var2[var1] = var3[var1].getScopeUri();
      }

      return var2;
   }
}
