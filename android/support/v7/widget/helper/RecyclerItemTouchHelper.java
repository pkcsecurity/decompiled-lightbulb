package android.support.v7.widget.helper;

import android.support.v7.widget.helper.ItemTouchHelper;

public class RecyclerItemTouchHelper extends ItemTouchHelper {

   public RecyclerItemTouchHelper(ItemTouchHelper.Callback var1) {
      super(var1);
   }

   public ItemTouchHelper.Callback getCallback() {
      return this.mCallback;
   }
}
