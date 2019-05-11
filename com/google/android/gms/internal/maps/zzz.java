package com.google.android.gms.internal.maps;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface zzz extends IInterface {

   void a(float var1) throws RemoteException;

   void a(int var1) throws RemoteException;

   void a(List<LatLng> var1) throws RemoteException;

   void a(boolean var1) throws RemoteException;

   boolean a(zzz var1) throws RemoteException;

   void b() throws RemoteException;

   void b(float var1) throws RemoteException;

   int c() throws RemoteException;
}
