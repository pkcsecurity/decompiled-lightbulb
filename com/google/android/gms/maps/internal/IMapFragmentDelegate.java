package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.internal.zzap;

public interface IMapFragmentDelegate extends IInterface {

   IObjectWrapper a(IObjectWrapper var1, IObjectWrapper var2, Bundle var3) throws RemoteException;

   void a() throws RemoteException;

   void a(Bundle var1) throws RemoteException;

   void a(IObjectWrapper var1, GoogleMapOptions var2, Bundle var3) throws RemoteException;

   void a(zzap var1) throws RemoteException;

   void b() throws RemoteException;

   void b(Bundle var1) throws RemoteException;

   void c() throws RemoteException;

   void c(Bundle var1) throws RemoteException;

   void d() throws RemoteException;

   void e() throws RemoteException;

   void f() throws RemoteException;

   void g() throws RemoteException;

   void h() throws RemoteException;
}
