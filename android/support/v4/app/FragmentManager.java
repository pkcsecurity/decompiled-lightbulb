package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public abstract class FragmentManager {

   public static final int POP_BACK_STACK_INCLUSIVE = 1;


   public static void enableDebugLogging(boolean var0) {
      FragmentManagerImpl.DEBUG = var0;
   }

   public abstract void addOnBackStackChangedListener(@NonNull FragmentManager.OnBackStackChangedListener var1);

   @NonNull
   public abstract FragmentTransaction beginTransaction();

   public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

   public abstract boolean executePendingTransactions();

   @Nullable
   public abstract Fragment findFragmentById(@IdRes int var1);

   @Nullable
   public abstract Fragment findFragmentByTag(@Nullable String var1);

   @NonNull
   public abstract FragmentManager.BackStackEntry getBackStackEntryAt(int var1);

   public abstract int getBackStackEntryCount();

   @Nullable
   public abstract Fragment getFragment(@NonNull Bundle var1, @NonNull String var2);

   @NonNull
   public abstract List<Fragment> getFragments();

   @Nullable
   public abstract Fragment getPrimaryNavigationFragment();

   public abstract boolean isDestroyed();

   public abstract boolean isStateSaved();

   @Deprecated
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public FragmentTransaction openTransaction() {
      return this.beginTransaction();
   }

   public abstract void popBackStack();

   public abstract void popBackStack(int var1, int var2);

   public abstract void popBackStack(@Nullable String var1, int var2);

   public abstract boolean popBackStackImmediate();

   public abstract boolean popBackStackImmediate(int var1, int var2);

   public abstract boolean popBackStackImmediate(@Nullable String var1, int var2);

   public abstract void putFragment(@NonNull Bundle var1, @NonNull String var2, @NonNull Fragment var3);

   public abstract void registerFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks var1, boolean var2);

   public abstract void removeOnBackStackChangedListener(@NonNull FragmentManager.OnBackStackChangedListener var1);

   @Nullable
   public abstract Fragment.SavedState saveFragmentInstanceState(Fragment var1);

   public abstract void unregisterFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks var1);

   public interface OnBackStackChangedListener {

      void onBackStackChanged();
   }

   public abstract static class FragmentLifecycleCallbacks {

      public void onFragmentActivityCreated(@NonNull FragmentManager var1, @NonNull Fragment var2, @Nullable Bundle var3) {}

      public void onFragmentAttached(@NonNull FragmentManager var1, @NonNull Fragment var2, @NonNull Context var3) {}

      public void onFragmentCreated(@NonNull FragmentManager var1, @NonNull Fragment var2, @Nullable Bundle var3) {}

      public void onFragmentDestroyed(@NonNull FragmentManager var1, @NonNull Fragment var2) {}

      public void onFragmentDetached(@NonNull FragmentManager var1, @NonNull Fragment var2) {}

      public void onFragmentPaused(@NonNull FragmentManager var1, @NonNull Fragment var2) {}

      public void onFragmentPreAttached(@NonNull FragmentManager var1, @NonNull Fragment var2, @NonNull Context var3) {}

      public void onFragmentPreCreated(@NonNull FragmentManager var1, @NonNull Fragment var2, @Nullable Bundle var3) {}

      public void onFragmentResumed(@NonNull FragmentManager var1, @NonNull Fragment var2) {}

      public void onFragmentSaveInstanceState(@NonNull FragmentManager var1, @NonNull Fragment var2, @NonNull Bundle var3) {}

      public void onFragmentStarted(@NonNull FragmentManager var1, @NonNull Fragment var2) {}

      public void onFragmentStopped(@NonNull FragmentManager var1, @NonNull Fragment var2) {}

      public void onFragmentViewCreated(@NonNull FragmentManager var1, @NonNull Fragment var2, @NonNull View var3, @Nullable Bundle var4) {}

      public void onFragmentViewDestroyed(@NonNull FragmentManager var1, @NonNull Fragment var2) {}
   }

   public interface BackStackEntry {

      @Nullable
      CharSequence getBreadCrumbShortTitle();

      @StringRes
      int getBreadCrumbShortTitleRes();

      @Nullable
      CharSequence getBreadCrumbTitle();

      @StringRes
      int getBreadCrumbTitleRes();

      int getId();

      @Nullable
      String getName();
   }
}
