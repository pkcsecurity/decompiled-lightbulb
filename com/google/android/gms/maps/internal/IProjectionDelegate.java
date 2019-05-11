package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

public interface IProjectionDelegate extends IInterface {

   IObjectWrapper a(LatLng var1) throws RemoteException;

   LatLng a(IObjectWrapper var1) throws RemoteException;

   VisibleRegion a() throws RemoteException;
}
