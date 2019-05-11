package com.google.android.gms.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

@Deprecated
public interface SettingsApi {

   PendingResult<LocationSettingsResult> a(GoogleApiClient var1, LocationSettingsRequest var2);
}
