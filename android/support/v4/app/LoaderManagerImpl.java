package android.support.v4.app;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

class LoaderManagerImpl extends LoaderManager {

   static boolean DEBUG;
   static final String TAG = "LoaderManager";
   @NonNull
   private final LifecycleOwner mLifecycleOwner;
   @NonNull
   private final LoaderManagerImpl.LoaderViewModel mLoaderViewModel;


   LoaderManagerImpl(@NonNull LifecycleOwner var1, @NonNull o var2) {
      this.mLifecycleOwner = var1;
      this.mLoaderViewModel = LoaderManagerImpl.LoaderViewModel.getInstance(var2);
   }

   @MainThread
   @NonNull
   private <D extends Object> Loader<D> createAndInstallLoader(int param1, @Nullable Bundle param2, @NonNull LoaderManager.LoaderCallbacks<D> param3, @Nullable Loader<D> param4) {
      // $FF: Couldn't be decompiled
   }

   @MainThread
   public void destroyLoader(int var1) {
      if(this.mLoaderViewModel.isCreatingLoader()) {
         throw new IllegalStateException("Called while creating a loader");
      } else if(Looper.getMainLooper() != Looper.myLooper()) {
         throw new IllegalStateException("destroyLoader must be called on the main thread");
      } else {
         if(DEBUG) {
            StringBuilder var2 = new StringBuilder();
            var2.append("destroyLoader in ");
            var2.append(this);
            var2.append(" of ");
            var2.append(var1);
            Log.v("LoaderManager", var2.toString());
         }

         LoaderManagerImpl.LoaderInfo var3 = this.mLoaderViewModel.getLoader(var1);
         if(var3 != null) {
            var3.destroy(true);
            this.mLoaderViewModel.removeLoader(var1);
         }

      }
   }

   @Deprecated
   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      this.mLoaderViewModel.dump(var1, var2, var3, var4);
   }

   @Nullable
   public <D extends Object> Loader<D> getLoader(int var1) {
      if(this.mLoaderViewModel.isCreatingLoader()) {
         throw new IllegalStateException("Called while creating a loader");
      } else {
         LoaderManagerImpl.LoaderInfo var2 = this.mLoaderViewModel.getLoader(var1);
         return var2 != null?var2.getLoader():null;
      }
   }

   public boolean hasRunningLoaders() {
      return this.mLoaderViewModel.hasRunningLoaders();
   }

   @MainThread
   @NonNull
   public <D extends Object> Loader<D> initLoader(int var1, @Nullable Bundle var2, @NonNull LoaderManager.LoaderCallbacks<D> var3) {
      if(this.mLoaderViewModel.isCreatingLoader()) {
         throw new IllegalStateException("Called while creating a loader");
      } else if(Looper.getMainLooper() != Looper.myLooper()) {
         throw new IllegalStateException("initLoader must be called on the main thread");
      } else {
         LoaderManagerImpl.LoaderInfo var4 = this.mLoaderViewModel.getLoader(var1);
         if(DEBUG) {
            StringBuilder var5 = new StringBuilder();
            var5.append("initLoader in ");
            var5.append(this);
            var5.append(": args=");
            var5.append(var2);
            Log.v("LoaderManager", var5.toString());
         }

         if(var4 == null) {
            return this.createAndInstallLoader(var1, var2, var3, (Loader)null);
         } else {
            if(DEBUG) {
               StringBuilder var6 = new StringBuilder();
               var6.append("  Re-using existing loader ");
               var6.append(var4);
               Log.v("LoaderManager", var6.toString());
            }

            return var4.setCallback(this.mLifecycleOwner, var3);
         }
      }
   }

   public void markForRedelivery() {
      this.mLoaderViewModel.markForRedelivery();
   }

   @MainThread
   @NonNull
   public <D extends Object> Loader<D> restartLoader(int var1, @Nullable Bundle var2, @NonNull LoaderManager.LoaderCallbacks<D> var3) {
      if(this.mLoaderViewModel.isCreatingLoader()) {
         throw new IllegalStateException("Called while creating a loader");
      } else if(Looper.getMainLooper() != Looper.myLooper()) {
         throw new IllegalStateException("restartLoader must be called on the main thread");
      } else {
         if(DEBUG) {
            StringBuilder var4 = new StringBuilder();
            var4.append("restartLoader in ");
            var4.append(this);
            var4.append(": args=");
            var4.append(var2);
            Log.v("LoaderManager", var4.toString());
         }

         LoaderManagerImpl.LoaderInfo var5 = this.mLoaderViewModel.getLoader(var1);
         Loader var6 = null;
         if(var5 != null) {
            var6 = var5.destroy(false);
         }

         return this.createAndInstallLoader(var1, var2, var3, var6);
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);
      var1.append("LoaderManager{");
      var1.append(Integer.toHexString(System.identityHashCode(this)));
      var1.append(" in ");
      DebugUtils.buildShortClassTag(this.mLifecycleOwner, var1);
      var1.append("}}");
      return var1.toString();
   }

   public static class LoaderInfo<D extends Object> extends j<D> implements Loader.OnLoadCompleteListener<D> {

      @Nullable
      private final Bundle mArgs;
      private final int mId;
      private LifecycleOwner mLifecycleOwner;
      @NonNull
      private final Loader<D> mLoader;
      private LoaderManagerImpl.LoaderObserver<D> mObserver;
      private Loader<D> mPriorLoader;


      LoaderInfo(int var1, @Nullable Bundle var2, @NonNull Loader<D> var3, @Nullable Loader<D> var4) {
         this.mId = var1;
         this.mArgs = var2;
         this.mLoader = var3;
         this.mPriorLoader = var4;
         this.mLoader.registerListener(var1, this);
      }

      @MainThread
      Loader<D> destroy(boolean var1) {
         if(LoaderManagerImpl.DEBUG) {
            StringBuilder var2 = new StringBuilder();
            var2.append("  Destroying: ");
            var2.append(this);
            Log.v("LoaderManager", var2.toString());
         }

         this.mLoader.cancelLoad();
         this.mLoader.abandon();
         LoaderManagerImpl.LoaderObserver var3 = this.mObserver;
         if(var3 != null) {
            this.removeObserver(var3);
            if(var1) {
               var3.reset();
            }
         }

         this.mLoader.unregisterListener(this);
         if((var3 == null || var3.hasDeliveredData()) && !var1) {
            return this.mLoader;
         } else {
            this.mLoader.reset();
            return this.mPriorLoader;
         }
      }

      public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
         var3.print(var1);
         var3.print("mId=");
         var3.print(this.mId);
         var3.print(" mArgs=");
         var3.println(this.mArgs);
         var3.print(var1);
         var3.print("mLoader=");
         var3.println(this.mLoader);
         Loader var5 = this.mLoader;
         StringBuilder var6 = new StringBuilder();
         var6.append(var1);
         var6.append("  ");
         var5.dump(var6.toString(), var2, var3, var4);
         if(this.mObserver != null) {
            var3.print(var1);
            var3.print("mCallbacks=");
            var3.println(this.mObserver);
            LoaderManagerImpl.LoaderObserver var7 = this.mObserver;
            StringBuilder var8 = new StringBuilder();
            var8.append(var1);
            var8.append("  ");
            var7.dump(var8.toString(), var3);
         }

         var3.print(var1);
         var3.print("mData=");
         var3.println(this.getLoader().dataToString(this.getValue()));
         var3.print(var1);
         var3.print("mStarted=");
         var3.println(this.hasActiveObservers());
      }

      @NonNull
      Loader<D> getLoader() {
         return this.mLoader;
      }

      boolean isCallbackWaitingForData() {
         boolean var1 = this.hasActiveObservers();
         boolean var2 = false;
         if(!var1) {
            return false;
         } else {
            var1 = var2;
            if(this.mObserver != null) {
               var1 = var2;
               if(!this.mObserver.hasDeliveredData()) {
                  var1 = true;
               }
            }

            return var1;
         }
      }

      void markForRedelivery() {
         LifecycleOwner var1 = this.mLifecycleOwner;
         LoaderManagerImpl.LoaderObserver var2 = this.mObserver;
         if(var1 != null && var2 != null) {
            super.removeObserver(var2);
            this.observe(var1, var2);
         }

      }

      protected void onActive() {
         if(LoaderManagerImpl.DEBUG) {
            StringBuilder var1 = new StringBuilder();
            var1.append("  Starting: ");
            var1.append(this);
            Log.v("LoaderManager", var1.toString());
         }

         this.mLoader.startLoading();
      }

      protected void onInactive() {
         if(LoaderManagerImpl.DEBUG) {
            StringBuilder var1 = new StringBuilder();
            var1.append("  Stopping: ");
            var1.append(this);
            Log.v("LoaderManager", var1.toString());
         }

         this.mLoader.stopLoading();
      }

      public void onLoadComplete(@NonNull Loader<D> var1, @Nullable D var2) {
         if(LoaderManagerImpl.DEBUG) {
            StringBuilder var3 = new StringBuilder();
            var3.append("onLoadComplete: ");
            var3.append(this);
            Log.v("LoaderManager", var3.toString());
         }

         if(Looper.myLooper() == Looper.getMainLooper()) {
            this.setValue(var2);
         } else {
            if(LoaderManagerImpl.DEBUG) {
               Log.w("LoaderManager", "onLoadComplete was incorrectly called on a background thread");
            }

            this.postValue(var2);
         }
      }

      public void removeObserver(@NonNull Observer<? super D> var1) {
         super.removeObserver(var1);
         this.mLifecycleOwner = null;
         this.mObserver = null;
      }

      @MainThread
      @NonNull
      Loader<D> setCallback(@NonNull LifecycleOwner var1, @NonNull LoaderManager.LoaderCallbacks<D> var2) {
         LoaderManagerImpl.LoaderObserver var3 = new LoaderManagerImpl.LoaderObserver(this.mLoader, var2);
         this.observe(var1, var3);
         if(this.mObserver != null) {
            this.removeObserver(this.mObserver);
         }

         this.mLifecycleOwner = var1;
         this.mObserver = var3;
         return this.mLoader;
      }

      public void setValue(D var1) {
         super.setValue(var1);
         if(this.mPriorLoader != null) {
            this.mPriorLoader.reset();
            this.mPriorLoader = null;
         }

      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(64);
         var1.append("LoaderInfo{");
         var1.append(Integer.toHexString(System.identityHashCode(this)));
         var1.append(" #");
         var1.append(this.mId);
         var1.append(" : ");
         DebugUtils.buildShortClassTag(this.mLoader, var1);
         var1.append("}}");
         return var1.toString();
      }
   }

   static class LoaderObserver<D extends Object> implements Observer<D> {

      @NonNull
      private final LoaderManager.LoaderCallbacks<D> mCallback;
      private boolean mDeliveredData = false;
      @NonNull
      private final Loader<D> mLoader;


      LoaderObserver(@NonNull Loader<D> var1, @NonNull LoaderManager.LoaderCallbacks<D> var2) {
         this.mLoader = var1;
         this.mCallback = var2;
      }

      public void dump(String var1, PrintWriter var2) {
         var2.print(var1);
         var2.print("mDeliveredData=");
         var2.println(this.mDeliveredData);
      }

      boolean hasDeliveredData() {
         return this.mDeliveredData;
      }

      public void onChanged(@Nullable D var1) {
         if(LoaderManagerImpl.DEBUG) {
            StringBuilder var2 = new StringBuilder();
            var2.append("  onLoadFinished in ");
            var2.append(this.mLoader);
            var2.append(": ");
            var2.append(this.mLoader.dataToString(var1));
            Log.v("LoaderManager", var2.toString());
         }

         this.mCallback.onLoadFinished(this.mLoader, var1);
         this.mDeliveredData = true;
      }

      @MainThread
      void reset() {
         if(this.mDeliveredData) {
            if(LoaderManagerImpl.DEBUG) {
               StringBuilder var1 = new StringBuilder();
               var1.append("  Resetting: ");
               var1.append(this.mLoader);
               Log.v("LoaderManager", var1.toString());
            }

            this.mCallback.onLoaderReset(this.mLoader);
         }

      }

      public String toString() {
         return this.mCallback.toString();
      }
   }

   static class LoaderViewModel extends m {

      private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
         @NonNull
         public <T extends m> T create(@NonNull Class<T> var1) {
            return new LoaderManagerImpl.LoaderViewModel();
         }
      };
      private boolean mCreatingLoader = false;
      private SparseArrayCompat<LoaderManagerImpl.LoaderInfo> mLoaders = new SparseArrayCompat();


      @NonNull
      static LoaderManagerImpl.LoaderViewModel getInstance(o var0) {
         return (LoaderManagerImpl.LoaderViewModel)(new ViewModelProvider(var0, FACTORY)).a(LoaderManagerImpl.LoaderViewModel.class);
      }

      public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
         if(this.mLoaders.size() > 0) {
            var3.print(var1);
            var3.println("Loaders:");
            StringBuilder var6 = new StringBuilder();
            var6.append(var1);
            var6.append("    ");
            String var8 = var6.toString();

            for(int var5 = 0; var5 < this.mLoaders.size(); ++var5) {
               LoaderManagerImpl.LoaderInfo var7 = (LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var5);
               var3.print(var1);
               var3.print("  #");
               var3.print(this.mLoaders.keyAt(var5));
               var3.print(": ");
               var3.println(var7.toString());
               var7.dump(var8, var2, var3, var4);
            }
         }

      }

      void finishCreatingLoader() {
         this.mCreatingLoader = false;
      }

      <D extends Object> LoaderManagerImpl.LoaderInfo<D> getLoader(int var1) {
         return (LoaderManagerImpl.LoaderInfo)this.mLoaders.get(var1);
      }

      boolean hasRunningLoaders() {
         int var2 = this.mLoaders.size();

         for(int var1 = 0; var1 < var2; ++var1) {
            if(((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var1)).isCallbackWaitingForData()) {
               return true;
            }
         }

         return false;
      }

      boolean isCreatingLoader() {
         return this.mCreatingLoader;
      }

      void markForRedelivery() {
         int var2 = this.mLoaders.size();

         for(int var1 = 0; var1 < var2; ++var1) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var1)).markForRedelivery();
         }

      }

      protected void onCleared() {
         super.onCleared();
         int var2 = this.mLoaders.size();

         for(int var1 = 0; var1 < var2; ++var1) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var1)).destroy(true);
         }

         this.mLoaders.clear();
      }

      void putLoader(int var1, @NonNull LoaderManagerImpl.LoaderInfo var2) {
         this.mLoaders.put(var1, var2);
      }

      void removeLoader(int var1) {
         this.mLoaders.remove(var1);
      }

      void startCreatingLoader() {
         this.mCreatingLoader = true;
      }
   }
}
