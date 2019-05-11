package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.common.R;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import javax.annotation.Nullable;

@KeepForSdk
public class StringResourceValueReader {

   private final Resources zzeu;
   private final String zzev;


   public StringResourceValueReader(Context var1) {
      Preconditions.checkNotNull(var1);
      this.zzeu = var1.getResources();
      this.zzev = this.zzeu.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
   }

   @Nullable
   @KeepForSdk
   public String getString(String var1) {
      int var2 = this.zzeu.getIdentifier(var1, "string", this.zzev);
      return var2 == 0?null:this.zzeu.getString(var2);
   }
}
