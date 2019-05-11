package android.support.v4.app;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {

   public static void enableDebugLogging(boolean var0) {
      LoaderManagerImpl.DEBUG = var0;
   }

   @NonNull
   public static <T extends Object & LifecycleOwner & ViewModelStoreOwner> LoaderManager getInstance(@NonNull T var0) {
      return new LoaderManagerImpl(var0, ((ViewModelStoreOwner)var0).getViewModelStore());
   }

   @MainThread
   public abstract void destroyLoader(int var1);

   @Deprecated
   public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

   @Nullable
   public abstract <D extends Object> Loader<D> getLoader(int var1);

   public boolean hasRunningLoaders() {
      return false;
   }

   @MainThread
   @NonNull
   public abstract <D extends Object> Loader<D> initLoader(int var1, @Nullable Bundle var2, @NonNull LoaderManager.LoaderCallbacks<D> var3);

   public abstract void markForRedelivery();

   @MainThread
   @NonNull
   public abstract <D extends Object> Loader<D> restartLoader(int var1, @Nullable Bundle var2, @NonNull LoaderManager.LoaderCallbacks<D> var3);

   public interface LoaderCallbacks<D extends Object> {

      @MainThread
      @NonNull
      Loader<D> onCreateLoader(int var1, @Nullable Bundle var2);

      @MainThread
      void onLoadFinished(@NonNull Loader<D> var1, D var2);

      @MainThread
      void onLoaderReset(@NonNull Loader<D> var1);
   }
}
