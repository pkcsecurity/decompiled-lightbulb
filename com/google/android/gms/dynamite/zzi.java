package com.google.android.gms.dynamite;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public interface zzi extends IInterface {

   int a() throws RemoteException;

   int a(IObjectWrapper var1, String var2, boolean var3) throws RemoteException;

   IObjectWrapper a(IObjectWrapper var1, String var2, int var3) throws RemoteException;

   int b(IObjectWrapper var1, String var2, boolean var3) throws RemoteException;

   IObjectWrapper b(IObjectWrapper var1, String var2, int var3) throws RemoteException;
}
