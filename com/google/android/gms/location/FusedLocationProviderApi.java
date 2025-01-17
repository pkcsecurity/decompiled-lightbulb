package com.google.android.gms.location;

import android.location.Location;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

@Deprecated
public interface FusedLocationProviderApi {

   @RequiresPermission(
      anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
   )
   Location a(GoogleApiClient var1);

   PendingResult<Status> a(GoogleApiClient var1, LocationListener var2);

   @RequiresPermission(
      anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
   )
   PendingResult<Status> a(GoogleApiClient var1, LocationRequest var2, LocationListener var3);
}
