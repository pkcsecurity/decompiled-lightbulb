package android.arch.lifecycle;

import android.arch.lifecycle.GeneratedAdapter;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SingleGeneratedAdapterObserver implements GenericLifecycleObserver {

   private final GeneratedAdapter a;


   public SingleGeneratedAdapterObserver(GeneratedAdapter var1) {
      this.a = var1;
   }

   public void a(LifecycleOwner var1, f$a var2) {
      this.a.a(var1, var2, false, (i)null);
      this.a.a(var1, var2, true, (i)null);
   }
}
