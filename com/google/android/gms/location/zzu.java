package com.google.android.gms.location;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;

public interface zzu extends IInterface {

   void a(LocationAvailability var1) throws RemoteException;

   void a(LocationResult var1) throws RemoteException;
}
