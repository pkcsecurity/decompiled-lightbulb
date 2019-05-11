package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public interface ICameraUpdateFactoryDelegate extends IInterface {

   IObjectWrapper a(LatLng var1) throws RemoteException;

   IObjectWrapper a(LatLng var1, float var2) throws RemoteException;

   IObjectWrapper a(LatLngBounds var1, int var2) throws RemoteException;

   IObjectWrapper a(LatLngBounds var1, int var2, int var3, int var4) throws RemoteException;
}
