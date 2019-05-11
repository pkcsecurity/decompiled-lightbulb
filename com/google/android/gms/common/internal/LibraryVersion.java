package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.ConcurrentHashMap;

@KeepForSdk
public class LibraryVersion {

   private static final GmsLogger zzel = new GmsLogger("LibraryVersion", "");
   private static LibraryVersion zzem = new LibraryVersion();
   private ConcurrentHashMap<String, String> zzen = new ConcurrentHashMap();


   @KeepForSdk
   public static LibraryVersion getInstance() {
      return zzem;
   }

   @KeepForSdk
   public String getVersion(@NonNull String param1) {
      // $FF: Couldn't be decompiled
   }
}
