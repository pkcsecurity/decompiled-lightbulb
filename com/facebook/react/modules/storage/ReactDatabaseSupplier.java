package com.facebook.react.modules.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import com.facebook.common.logging.FLog;
import javax.annotation.Nullable;

public class ReactDatabaseSupplier extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "RKStorage";
   private static final int DATABASE_VERSION = 1;
   static final String KEY_COLUMN = "key";
   private static final int SLEEP_TIME_MS = 30;
   static final String TABLE_CATALYST = "catalystLocalStorage";
   static final String VALUE_COLUMN = "value";
   static final String VERSION_TABLE_CREATE = "CREATE TABLE catalystLocalStorage (key TEXT PRIMARY KEY, value TEXT NOT NULL)";
   @Nullable
   private static ReactDatabaseSupplier sReactDatabaseSupplierInstance;
   private Context mContext;
   @Nullable
   private SQLiteDatabase mDb;
   private long mMaximumDatabaseSize = 6291456L;


   private ReactDatabaseSupplier(Context var1) {
      super(var1, "RKStorage", (CursorFactory)null, 1);
      this.mContext = var1;
   }

   private void closeDatabase() {
      synchronized(this){}

      try {
         if(this.mDb != null && this.mDb.isOpen()) {
            this.mDb.close();
            this.mDb = null;
         }
      } finally {
         ;
      }

   }

   private boolean deleteDatabase() {
      synchronized(this){}

      boolean var1;
      try {
         this.closeDatabase();
         var1 = this.mContext.deleteDatabase("RKStorage");
      } finally {
         ;
      }

      return var1;
   }

   public static void deleteInstance() {
      sReactDatabaseSupplierInstance = null;
   }

   public static ReactDatabaseSupplier getInstance(Context var0) {
      if(sReactDatabaseSupplierInstance == null) {
         sReactDatabaseSupplierInstance = new ReactDatabaseSupplier(var0.getApplicationContext());
      }

      return sReactDatabaseSupplierInstance;
   }

   void clear() {
      synchronized(this){}

      try {
         this.get().delete("catalystLocalStorage", (String)null, (String[])null);
      } finally {
         ;
      }

   }

   public void clearAndCloseDatabase() throws RuntimeException {
      synchronized(this){}

      try {
         this.clear();
         this.closeDatabase();
         FLog.d("ReactNative", "Cleaned RKStorage");
         return;
      } catch (Exception var4) {
         if(!this.deleteDatabase()) {
            throw new RuntimeException("Clearing and deleting database RKStorage failed");
         }

         FLog.d("ReactNative", "Deleted Local Database RKStorage");
      } finally {
         ;
      }

   }

   boolean ensureDatabase() {
      // $FF: Couldn't be decompiled
   }

   public SQLiteDatabase get() {
      synchronized(this){}

      SQLiteDatabase var1;
      try {
         this.ensureDatabase();
         var1 = this.mDb;
      } finally {
         ;
      }

      return var1;
   }

   public void onCreate(SQLiteDatabase var1) {
      var1.execSQL("CREATE TABLE catalystLocalStorage (key TEXT PRIMARY KEY, value TEXT NOT NULL)");
   }

   public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
      if(var2 != var3) {
         this.deleteDatabase();
         this.onCreate(var1);
      }

   }

   public void setMaximumSize(long var1) {
      synchronized(this){}

      try {
         this.mMaximumDatabaseSize = var1;
         if(this.mDb != null) {
            this.mDb.setMaximumSize(this.mMaximumDatabaseSize);
         }
      } finally {
         ;
      }

   }
}
