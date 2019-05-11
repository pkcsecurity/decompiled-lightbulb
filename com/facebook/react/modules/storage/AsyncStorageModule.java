package com.facebook.react.modules.storage;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.common.ModuleDataCleaner;
import com.facebook.react.modules.storage.AsyncStorageErrorUtil;
import com.facebook.react.modules.storage.ReactDatabaseSupplier;

@ReactModule(
   name = "AsyncSQLiteDBStorage"
)
public final class AsyncStorageModule extends ReactContextBaseJavaModule implements ModuleDataCleaner.Cleanable {

   private static final int MAX_SQL_KEYS = 999;
   protected static final String NAME = "AsyncSQLiteDBStorage";
   private ReactDatabaseSupplier mReactDatabaseSupplier;
   private boolean mShuttingDown = false;


   public AsyncStorageModule(ReactApplicationContext var1) {
      super(var1);
      this.mReactDatabaseSupplier = ReactDatabaseSupplier.getInstance(var1);
   }

   // $FF: synthetic method
   static boolean access$000(AsyncStorageModule var0) {
      return var0.ensureDatabase();
   }

   private boolean ensureDatabase() {
      return !this.mShuttingDown && this.mReactDatabaseSupplier.ensureDatabase();
   }

   @ReactMethod
   public void clear(final Callback var1) {
      (new GuardedAsyncTask(this.getReactApplicationContext()) {
         protected void doInBackgroundGuarded(Void ... var1x) {
            if(!AsyncStorageModule.this.mReactDatabaseSupplier.ensureDatabase()) {
               var1.invoke(new Object[]{AsyncStorageErrorUtil.getDBError((String)null)});
            } else {
               try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.clear();
                  var1.invoke(new Object[0]);
               } catch (Exception var2) {
                  FLog.w("ReactNative", var2.getMessage(), (Throwable)var2);
                  var1.invoke(new Object[]{AsyncStorageErrorUtil.getError((String)null, var2.getMessage())});
               }
            }
         }
      }).execute(new Void[0]);
   }

   public void clearSensitiveData() {
      this.mReactDatabaseSupplier.clearAndCloseDatabase();
   }

   @ReactMethod
   public void getAllKeys(final Callback var1) {
      (new GuardedAsyncTask(this.getReactApplicationContext()) {
         protected void doInBackgroundGuarded(Void ... param1) {
            // $FF: Couldn't be decompiled
         }
      }).execute(new Void[0]);
   }

   public String getName() {
      return "AsyncSQLiteDBStorage";
   }

   public void initialize() {
      super.initialize();
      this.mShuttingDown = false;
   }

   @ReactMethod
   public void multiGet(final ReadableArray var1, final Callback var2) {
      if(var1 == null) {
         var2.invoke(new Object[]{AsyncStorageErrorUtil.getInvalidKeyError((String)null), null});
      } else {
         (new GuardedAsyncTask(this.getReactApplicationContext()) {
            protected void doInBackgroundGuarded(Void ... param1) {
               // $FF: Couldn't be decompiled
            }
         }).execute(new Void[0]);
      }
   }

   @ReactMethod
   public void multiMerge(final ReadableArray var1, final Callback var2) {
      (new GuardedAsyncTask(this.getReactApplicationContext()) {
         protected void doInBackgroundGuarded(Void ... param1) {
            // $FF: Couldn't be decompiled
         }
      }).execute(new Void[0]);
   }

   @ReactMethod
   public void multiRemove(final ReadableArray var1, final Callback var2) {
      if(var1.size() == 0) {
         var2.invoke(new Object[]{AsyncStorageErrorUtil.getInvalidKeyError((String)null)});
      } else {
         (new GuardedAsyncTask(this.getReactApplicationContext()) {
            protected void doInBackgroundGuarded(Void ... param1) {
               // $FF: Couldn't be decompiled
            }
         }).execute(new Void[0]);
      }
   }

   @ReactMethod
   public void multiSet(final ReadableArray var1, final Callback var2) {
      if(var1.size() == 0) {
         var2.invoke(new Object[]{AsyncStorageErrorUtil.getInvalidKeyError((String)null)});
      } else {
         (new GuardedAsyncTask(this.getReactApplicationContext()) {
            protected void doInBackgroundGuarded(Void ... param1) {
               // $FF: Couldn't be decompiled
            }
         }).execute(new Void[0]);
      }
   }

   public void onCatalystInstanceDestroy() {
      this.mShuttingDown = true;
   }
}
