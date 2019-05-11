package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;

public interface zze extends IInterface {

   ICameraUpdateFactoryDelegate a() throws RemoteException;

   IMapFragmentDelegate a(IObjectWrapper var1) throws RemoteException;

   IMapViewDelegate a(IObjectWrapper var1, GoogleMapOptions var2) throws RemoteException;

   IStreetViewPanoramaViewDelegate a(IObjectWrapper var1, StreetViewPanoramaOptions var2) throws RemoteException;

   void a(IObjectWrapper var1, int var2) throws RemoteException;

   com.google.android.gms.internal.maps.zze b() throws RemoteException;

   IStreetViewPanoramaFragmentDelegate b(IObjectWrapper var1) throws RemoteException;
}
