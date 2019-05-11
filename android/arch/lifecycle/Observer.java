package android.arch.lifecycle;

import android.support.annotation.Nullable;

public interface Observer<T extends Object> {

   void onChanged(@Nullable T var1);
}
