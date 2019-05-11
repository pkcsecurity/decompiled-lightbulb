package com.google.android.gms.internal.maps;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;

public interface zzt extends IInterface {

   void a() throws RemoteException;

   void a(float var1) throws RemoteException;

   void a(float var1, float var2) throws RemoteException;

   void a(IObjectWrapper var1) throws RemoteException;

   void a(LatLng var1) throws RemoteException;

   void a(String var1) throws RemoteException;

   void a(boolean var1) throws RemoteException;

   boolean a(zzt var1) throws RemoteException;

   LatLng b() throws RemoteException;

   void b(float var1, float var2) throws RemoteException;

   void b(String var1) throws RemoteException;

   void b(boolean var1) throws RemoteException;

   void c() throws RemoteException;

   void d() throws RemoteException;

   int e() throws RemoteException;
}
