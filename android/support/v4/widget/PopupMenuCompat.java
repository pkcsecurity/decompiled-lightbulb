package android.support.v4.widget;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View.OnTouchListener;
import android.widget.PopupMenu;

public final class PopupMenuCompat {

   @Nullable
   public static OnTouchListener getDragToOpenListener(@NonNull Object var0) {
      return VERSION.SDK_INT >= 19?((PopupMenu)var0).getDragToOpenListener():null;
   }
}
