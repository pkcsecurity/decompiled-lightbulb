package android.support.v7.widget.util;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

public abstract class SortedListAdapterCallback<T2 extends Object> extends SortedList.Callback<T2> {

   final RecyclerView.Adapter mAdapter;


   public SortedListAdapterCallback(RecyclerView.Adapter var1) {
      this.mAdapter = var1;
   }

   public void onChanged(int var1, int var2) {
      this.mAdapter.notifyItemRangeChanged(var1, var2);
   }

   public void onChanged(int var1, int var2, Object var3) {
      this.mAdapter.notifyItemRangeChanged(var1, var2, var3);
   }

   public void onInserted(int var1, int var2) {
      this.mAdapter.notifyItemRangeInserted(var1, var2);
   }

   public void onMoved(int var1, int var2) {
      this.mAdapter.notifyItemMoved(var1, var2);
   }

   public void onRemoved(int var1, int var2) {
      this.mAdapter.notifyItemRangeRemoved(var1, var2);
   }
}
