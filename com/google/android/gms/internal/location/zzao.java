package com.google.android.gms.internal.location;

import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzaq;
import com.google.android.gms.internal.location.zzbf;
import com.google.android.gms.internal.location.zzo;
import com.google.android.gms.location.LocationSettingsRequest;

public interface zzao extends IInterface {

   Location a(String var1) throws RemoteException;

   void a(zzbf var1) throws RemoteException;

   void a(zzo var1) throws RemoteException;

   void a(LocationSettingsRequest var1, zzaq var2, String var3) throws RemoteException;

   void a(boolean var1) throws RemoteException;
}
