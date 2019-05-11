package com.google.android.gms.security;

import android.content.Context;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.lang.reflect.Method;

public class ProviderInstaller {

   private static final GoogleApiAvailabilityLight a = GoogleApiAvailabilityLight.getInstance();
   private static final Object b = new Object();
   private static Method c;


   public static void a(Context param0) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
      // $FF: Couldn't be decompiled
   }

   public interface ProviderInstallListener {
   }
}
