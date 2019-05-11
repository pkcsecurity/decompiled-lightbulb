package android.support.v7.util;

import android.support.annotation.Nullable;

public interface ListUpdateCallback {

   void onChanged(int var1, int var2, @Nullable Object var3);

   void onInserted(int var1, int var2);

   void onMoved(int var1, int var2);

   void onRemoved(int var1, int var2);
}
