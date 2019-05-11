package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ISignInButtonCreator;
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.common.internal.zah;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import fl.a;

public final class SignInButtonCreator extends fl<ISignInButtonCreator> {

   private static final SignInButtonCreator zape = new SignInButtonCreator();


   private SignInButtonCreator() {
      super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
   }

   public static View createView(Context var0, int var1, int var2) throws a {
      return zape.zaa(var0, var1, var2);
   }

   private final View zaa(Context var1, int var2, int var3) throws a {
      try {
         SignInButtonConfig var8 = new SignInButtonConfig(var2, var3, (Scope[])null);
         IObjectWrapper var5 = ObjectWrapper.a((Object)var1);
         View var7 = (View)ObjectWrapper.a(((ISignInButtonCreator)this.getRemoteCreatorInstance(var1)).newSignInButtonFromConfig(var5, var8));
         return var7;
      } catch (Exception var6) {
         StringBuilder var4 = new StringBuilder(64);
         var4.append("Could not get button with size ");
         var4.append(var2);
         var4.append(" and color ");
         var4.append(var3);
         throw new a(var4.toString(), var6);
      }
   }

   public final ISignInButtonCreator getRemoteCreator(IBinder var1) {
      if(var1 == null) {
         return null;
      } else {
         IInterface var2 = var1.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
         return (ISignInButtonCreator)(var2 instanceof ISignInButtonCreator?(ISignInButtonCreator)var2:new zah(var1));
      }
   }
}
