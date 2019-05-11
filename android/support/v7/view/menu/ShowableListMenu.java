package android.support.v7.view.menu;

import android.support.annotation.RestrictTo;
import android.widget.ListView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface ShowableListMenu {

   void dismiss();

   ListView getListView();

   boolean isShowing();

   void show();
}
