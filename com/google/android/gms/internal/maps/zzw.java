package com.google.android.gms.internal.maps;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface zzw extends IInterface {

   void a() throws RemoteException;

   void a(float var1) throws RemoteException;

   void a(int var1) throws RemoteException;

   void a(List<LatLng> var1) throws RemoteException;

   void a(boolean var1) throws RemoteException;

   boolean a(zzw var1) throws RemoteException;

   int b() throws RemoteException;

   void b(float var1) throws RemoteException;

   void b(int var1) throws RemoteException;
}
