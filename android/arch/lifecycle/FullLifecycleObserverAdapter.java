package android.arch.lifecycle;

import android.arch.lifecycle.FullLifecycleObserver;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;

public class FullLifecycleObserverAdapter implements GenericLifecycleObserver {

   private final FullLifecycleObserver a;


   public FullLifecycleObserverAdapter(FullLifecycleObserver var1) {
      this.a = var1;
   }

   public void a(LifecycleOwner var1, f$a var2) {
      switch(null.a[var2.ordinal()]) {
      case 1:
         this.a.a(var1);
         return;
      case 2:
         this.a.b(var1);
         return;
      case 3:
         this.a.c(var1);
         return;
      case 4:
         this.a.d(var1);
         return;
      case 5:
         this.a.e(var1);
         return;
      case 6:
         this.a.f(var1);
         return;
      case 7:
         throw new IllegalArgumentException("ON_ANY must not been send by anybody");
      default:
      }
   }
}
