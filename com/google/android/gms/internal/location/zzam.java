package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IInterface;
import android.os.RemoteException;

public interface zzam extends IInterface {

   void a(int var1, PendingIntent var2) throws RemoteException;

   void a(int var1, String[] var2) throws RemoteException;

   void b(int var1, String[] var2) throws RemoteException;
}
