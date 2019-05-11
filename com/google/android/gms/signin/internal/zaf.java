package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.signin.internal.zad;
import com.google.android.gms.signin.internal.zah;

public interface zaf extends IInterface {

   void a(int var1) throws RemoteException;

   void a(IAccountAccessor var1, int var2, boolean var3) throws RemoteException;

   void a(zah var1, zad var2) throws RemoteException;
}
