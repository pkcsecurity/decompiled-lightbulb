package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.zzbp;

public interface IStreetViewPanoramaFragmentDelegate extends IInterface {

   IObjectWrapper a(IObjectWrapper var1, IObjectWrapper var2, Bundle var3) throws RemoteException;

   void a() throws RemoteException;

   void a(Bundle var1) throws RemoteException;

   void a(IObjectWrapper var1, StreetViewPanoramaOptions var2, Bundle var3) throws RemoteException;

   void a(zzbp var1) throws RemoteException;

   void b() throws RemoteException;

   void b(Bundle var1) throws RemoteException;

   void c() throws RemoteException;

   void d() throws RemoteException;

   void e() throws RemoteException;

   void f() throws RemoteException;

   void g() throws RemoteException;
}
