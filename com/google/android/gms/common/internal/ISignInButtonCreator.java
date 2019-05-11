package com.google.android.gms.common.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.dynamic.IObjectWrapper;

public interface ISignInButtonCreator extends IInterface {

   IObjectWrapper newSignInButton(IObjectWrapper var1, int var2, int var3) throws RemoteException;

   IObjectWrapper newSignInButtonFromConfig(IObjectWrapper var1, SignInButtonConfig var2) throws RemoteException;
}
