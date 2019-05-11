package android.arch.lifecycle;

import android.arch.lifecycle.GeneratedAdapter;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class CompositeGeneratedAdaptersObserver implements GenericLifecycleObserver {

   private final GeneratedAdapter[] a;


   public CompositeGeneratedAdaptersObserver(GeneratedAdapter[] var1) {
      this.a = var1;
   }

   public void a(LifecycleOwner var1, f$a var2) {
      i var6 = new i();
      GeneratedAdapter[] var7 = this.a;
      int var5 = var7.length;
      byte var4 = 0;

      int var3;
      for(var3 = 0; var3 < var5; ++var3) {
         var7[var3].a(var1, var2, false, var6);
      }

      var7 = this.a;
      var5 = var7.length;

      for(var3 = var4; var3 < var5; ++var3) {
         var7[var3].a(var1, var2, true, var6);
      }

   }
}
