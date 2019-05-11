package android.arch.lifecycle;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public interface GenericLifecycleObserver extends LifecycleObserver {

   void a(LifecycleOwner var1, f$a var2);
}
