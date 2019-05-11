package android.arch.lifecycle;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import e.a;

public class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {

   private final Object a;
   private final a b;


   public ReflectiveGenericLifecycleObserver(Object var1) {
      this.a = var1;
      this.b = e.a.b(this.a.getClass());
   }

   public void a(LifecycleOwner var1, f$a var2) {
      this.b.a(var1, var2, this.a);
   }
}
