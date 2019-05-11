package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.ShowFirstParty;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.concurrent.GuardedBy;

@ShowFirstParty
public abstract class zac {

   private static final Object sLock = new Object();
   @GuardedBy
   private static final Map<Object, zac> zacj = new WeakHashMap();


   public abstract void remove(int var1);
}
