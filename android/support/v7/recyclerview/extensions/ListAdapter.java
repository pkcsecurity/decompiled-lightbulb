package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import java.util.List;

public abstract class ListAdapter<T extends Object, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

   private final AsyncListDiffer<T> mHelper;


   protected ListAdapter(@NonNull AsyncDifferConfig<T> var1) {
      this.mHelper = new AsyncListDiffer(new AdapterListUpdateCallback(this), var1);
   }

   protected ListAdapter(@NonNull DiffUtil.ItemCallback<T> var1) {
      this.mHelper = new AsyncListDiffer(new AdapterListUpdateCallback(this), (new AsyncDifferConfig.Builder(var1)).build());
   }

   protected T getItem(int var1) {
      return this.mHelper.getCurrentList().get(var1);
   }

   public int getItemCount() {
      return this.mHelper.getCurrentList().size();
   }

   public void submitList(@Nullable List<T> var1) {
      this.mHelper.submitList(var1);
   }
}
