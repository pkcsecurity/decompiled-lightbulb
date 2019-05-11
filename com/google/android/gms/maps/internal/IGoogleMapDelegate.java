package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.internal.zzab;
import com.google.android.gms.maps.internal.zzaj;
import com.google.android.gms.maps.internal.zzal;
import com.google.android.gms.maps.internal.zzan;
import com.google.android.gms.maps.internal.zzar;
import com.google.android.gms.maps.internal.zzat;
import com.google.android.gms.maps.internal.zzbs;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzh;
import com.google.android.gms.maps.internal.zzl;
import com.google.android.gms.maps.internal.zzn;
import com.google.android.gms.maps.internal.zzr;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public interface IGoogleMapDelegate extends IInterface {

   com.google.android.gms.internal.maps.zzh a(CircleOptions var1) throws RemoteException;

   com.google.android.gms.internal.maps.zzt a(MarkerOptions var1) throws RemoteException;

   com.google.android.gms.internal.maps.zzw a(PolygonOptions var1) throws RemoteException;

   com.google.android.gms.internal.maps.zzz a(PolylineOptions var1) throws RemoteException;

   CameraPosition a() throws RemoteException;

   void a(int var1) throws RemoteException;

   void a(IObjectWrapper var1) throws RemoteException;

   void a(IObjectWrapper var1, int var2, zzc var3) throws RemoteException;

   void a(zzab var1) throws RemoteException;

   void a(zzaj var1) throws RemoteException;

   void a(zzal var1) throws RemoteException;

   void a(zzan var1) throws RemoteException;

   void a(zzar var1) throws RemoteException;

   void a(zzat var1) throws RemoteException;

   void a(zzbs var1, IObjectWrapper var2) throws RemoteException;

   void a(zzh var1) throws RemoteException;

   void a(zzl var1) throws RemoteException;

   void a(zzn var1) throws RemoteException;

   void a(zzr var1) throws RemoteException;

   void a(boolean var1) throws RemoteException;

   IUiSettingsDelegate b() throws RemoteException;

   void b(IObjectWrapper var1) throws RemoteException;

   boolean b(boolean var1) throws RemoteException;

   IProjectionDelegate c() throws RemoteException;

   void c(boolean var1) throws RemoteException;

   void d(boolean var1) throws RemoteException;
}
