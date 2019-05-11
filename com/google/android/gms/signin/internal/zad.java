package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.signin.internal.zaa;
import com.google.android.gms.signin.internal.zaj;

public interface zad extends IInterface {

   void zaa(ConnectionResult var1, zaa var2) throws RemoteException;

   void zaa(Status var1, GoogleSignInAccount var2) throws RemoteException;

   void zab(zaj var1) throws RemoteException;

   void zag(Status var1) throws RemoteException;

   void zah(Status var1) throws RemoteException;
}
