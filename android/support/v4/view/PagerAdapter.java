package android.support.v4.view;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public abstract class PagerAdapter {

   public static final int POSITION_NONE = -2;
   public static final int POSITION_UNCHANGED = -1;
   private final DataSetObservable mObservable = new DataSetObservable();
   private DataSetObserver mViewPagerObserver;


   @Deprecated
   public void destroyItem(@NonNull View var1, int var2, @NonNull Object var3) {
      throw new UnsupportedOperationException("Required method destroyItem was not overridden");
   }

   public void destroyItem(@NonNull ViewGroup var1, int var2, @NonNull Object var3) {
      this.destroyItem((View)var1, var2, var3);
   }

   @Deprecated
   public void finishUpdate(@NonNull View var1) {}

   public void finishUpdate(@NonNull ViewGroup var1) {
      this.finishUpdate((View)var1);
   }

   public abstract int getCount();

   public int getItemPosition(@NonNull Object var1) {
      return -1;
   }

   @Nullable
   public CharSequence getPageTitle(int var1) {
      return null;
   }

   public float getPageWidth(int var1) {
      return 1.0F;
   }

   @Deprecated
   @NonNull
   public Object instantiateItem(@NonNull View var1, int var2) {
      throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
   }

   @NonNull
   public Object instantiateItem(@NonNull ViewGroup var1, int var2) {
      return this.instantiateItem((View)var1, var2);
   }

   public abstract boolean isViewFromObject(@NonNull View var1, @NonNull Object var2);

   public void notifyDataSetChanged() {
      // $FF: Couldn't be decompiled
   }

   public void registerDataSetObserver(@NonNull DataSetObserver var1) {
      this.mObservable.registerObserver(var1);
   }

   public void restoreState(@Nullable Parcelable var1, @Nullable ClassLoader var2) {}

   @Nullable
   public Parcelable saveState() {
      return null;
   }

   @Deprecated
   public void setPrimaryItem(@NonNull View var1, int var2, @NonNull Object var3) {}

   public void setPrimaryItem(@NonNull ViewGroup var1, int var2, @NonNull Object var3) {
      this.setPrimaryItem((View)var1, var2, var3);
   }

   void setViewPagerObserver(DataSetObserver param1) {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   public void startUpdate(@NonNull View var1) {}

   public void startUpdate(@NonNull ViewGroup var1) {
      this.startUpdate((View)var1);
   }

   public void unregisterDataSetObserver(@NonNull DataSetObserver var1) {
      this.mObservable.unregisterObserver(var1);
   }
}
