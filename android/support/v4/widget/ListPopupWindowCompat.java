package android.support.v4.widget;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListPopupWindow;

public final class ListPopupWindowCompat {

   @Nullable
   public static OnTouchListener createDragToOpenListener(@NonNull ListPopupWindow var0, @NonNull View var1) {
      return VERSION.SDK_INT >= 19?var0.createDragToOpenListener(var1):null;
   }

   @Deprecated
   public static OnTouchListener createDragToOpenListener(Object var0, View var1) {
      return createDragToOpenListener((ListPopupWindow)var0, var1);
   }
}
