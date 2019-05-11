package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

@KeepForSdk
public class ListenerHolders {

   private final Set<ListenerHolder<?>> zajn = Collections.newSetFromMap(new WeakHashMap());


   @KeepForSdk
   public static <L extends Object> ListenerHolder<L> createListenerHolder(@NonNull L var0, @NonNull Looper var1, @NonNull String var2) {
      Preconditions.checkNotNull(var0, "Listener must not be null");
      Preconditions.checkNotNull(var1, "Looper must not be null");
      Preconditions.checkNotNull(var2, "Listener type must not be null");
      return new ListenerHolder(var1, var0, var2);
   }

   @KeepForSdk
   public static <L extends Object> ListenerHolder.ListenerKey<L> createListenerKey(@NonNull L var0, @NonNull String var1) {
      Preconditions.checkNotNull(var0, "Listener must not be null");
      Preconditions.checkNotNull(var1, "Listener type must not be null");
      Preconditions.checkNotEmpty(var1, "Listener type must not be empty");
      return new ListenerHolder.ListenerKey(var0, var1);
   }

   public final void release() {
      Iterator var1 = this.zajn.iterator();

      while(var1.hasNext()) {
         ((ListenerHolder)var1.next()).clear();
      }

      this.zajn.clear();
   }

   public final <L extends Object> ListenerHolder<L> zaa(@NonNull L var1, @NonNull Looper var2, @NonNull String var3) {
      ListenerHolder var4 = createListenerHolder(var1, var2, var3);
      this.zajn.add(var4);
      return var4;
   }
}
