package android.support.customtabs;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import java.util.List;

public interface ICustomTabsService extends IInterface {

   Bundle extraCommand(String var1, Bundle var2) throws RemoteException;

   boolean mayLaunchUrl(ICustomTabsCallback var1, Uri var2, Bundle var3, List<Bundle> var4) throws RemoteException;

   boolean newSession(ICustomTabsCallback var1) throws RemoteException;

   int postMessage(ICustomTabsCallback var1, String var2, Bundle var3) throws RemoteException;

   boolean requestPostMessageChannel(ICustomTabsCallback var1, Uri var2) throws RemoteException;

   boolean updateVisuals(ICustomTabsCallback var1, Bundle var2) throws RemoteException;

   boolean validateRelationship(ICustomTabsCallback var1, int var2, Uri var3, Bundle var4) throws RemoteException;

   boolean warmup(long var1) throws RemoteException;

   static class Proxy implements ICustomTabsService {

      private IBinder mRemote;


      Proxy(IBinder var1) {
         this.mRemote = var1;
      }

      public IBinder asBinder() {
         return this.mRemote;
      }

      public Bundle extraCommand(String param1, Bundle param2) throws RemoteException {
         // $FF: Couldn't be decompiled
      }

      public String getInterfaceDescriptor() {
         return "android.support.customtabs.ICustomTabsService";
      }

      public boolean mayLaunchUrl(ICustomTabsCallback param1, Uri param2, Bundle param3, List<Bundle> param4) throws RemoteException {
         // $FF: Couldn't be decompiled
      }

      public boolean newSession(ICustomTabsCallback param1) throws RemoteException {
         // $FF: Couldn't be decompiled
      }

      public int postMessage(ICustomTabsCallback param1, String param2, Bundle param3) throws RemoteException {
         // $FF: Couldn't be decompiled
      }

      public boolean requestPostMessageChannel(ICustomTabsCallback param1, Uri param2) throws RemoteException {
         // $FF: Couldn't be decompiled
      }

      public boolean updateVisuals(ICustomTabsCallback param1, Bundle param2) throws RemoteException {
         // $FF: Couldn't be decompiled
      }

      public boolean validateRelationship(ICustomTabsCallback param1, int param2, Uri param3, Bundle param4) throws RemoteException {
         // $FF: Couldn't be decompiled
      }

      public boolean warmup(long param1) throws RemoteException {
         // $FF: Couldn't be decompiled
      }
   }

   public abstract static class Stub extends Binder implements ICustomTabsService {

      private static final String DESCRIPTOR = "android.support.customtabs.ICustomTabsService";
      static final int TRANSACTION_extraCommand = 5;
      static final int TRANSACTION_mayLaunchUrl = 4;
      static final int TRANSACTION_newSession = 3;
      static final int TRANSACTION_postMessage = 8;
      static final int TRANSACTION_requestPostMessageChannel = 7;
      static final int TRANSACTION_updateVisuals = 6;
      static final int TRANSACTION_validateRelationship = 9;
      static final int TRANSACTION_warmup = 2;


      public Stub() {
         this.attachInterface(this, "android.support.customtabs.ICustomTabsService");
      }

      public static ICustomTabsService asInterface(IBinder var0) {
         if(var0 == null) {
            return null;
         } else {
            IInterface var1 = var0.queryLocalInterface("android.support.customtabs.ICustomTabsService");
            return (ICustomTabsService)(var1 != null && var1 instanceof ICustomTabsService?(ICustomTabsService)var1:new ICustomTabsService.Proxy(var0));
         }
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
      }
   }
}
