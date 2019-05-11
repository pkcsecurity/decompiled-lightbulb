package com.facebook.imagepipeline.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.provider.BaseColumns;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.cache.MediaVariationsIndex;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.MediaVariations;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class MediaVariationsIndexDatabase implements MediaVariationsIndex {

   private static final String[] PROJECTION = new String[]{"cache_choice", "cache_key", "width", "height"};
   private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS media_variations_index";
   private static final String TAG = "MediaVariationsIndexDatabase";
   @GuardedBy
   private final MediaVariationsIndexDatabase.LazyIndexDbOpenHelper mDbHelper;
   private final Executor mReadExecutor;
   private final Executor mWriteExecutor;


   public MediaVariationsIndexDatabase(Context var1, Executor var2, Executor var3) {
      this.mDbHelper = new MediaVariationsIndexDatabase.LazyIndexDbOpenHelper(var1, null);
      this.mReadExecutor = var2;
      this.mWriteExecutor = var3;
   }

   public Task<MediaVariations> getCachedVariants(final String var1, final MediaVariations.Builder var2) {
      try {
         Task var4 = Task.a(new Callable() {
            public MediaVariations call() throws Exception {
               return MediaVariationsIndexDatabase.this.getCachedVariantsSync(var1, var2);
            }
         }, this.mReadExecutor);
         return var4;
      } catch (Exception var3) {
         FLog.w(TAG, var3, "Failed to schedule query task for %s", new Object[]{var1});
         return Task.a(var3);
      }
   }

   @VisibleForTesting
   protected MediaVariations getCachedVariantsSync(String param1, MediaVariations.Builder param2) {
      // $FF: Couldn't be decompiled
   }

   public void saveCachedVariant(final String var1, final ImageRequest.CacheChoice var2, final CacheKey var3, final EncodedImage var4) {
      this.mWriteExecutor.execute(new Runnable() {
         public void run() {
            MediaVariationsIndexDatabase.this.saveCachedVariantSync(var1, var2, var3, var4);
         }
      });
   }

   protected void saveCachedVariantSync(String param1, ImageRequest.CacheChoice param2, CacheKey param3, EncodedImage param4) {
      // $FF: Couldn't be decompiled
   }

   static class LazyIndexDbOpenHelper {

      private final Context mContext;
      @Nullable
      private MediaVariationsIndexDatabase.IndexDbOpenHelper mIndexDbOpenHelper;


      private LazyIndexDbOpenHelper(Context var1) {
         this.mContext = var1;
      }

      // $FF: synthetic method
      LazyIndexDbOpenHelper(Context var1, Object var2) {
         this(var1);
      }

      public SQLiteDatabase getWritableDatabase() {
         synchronized(this){}

         SQLiteDatabase var1;
         try {
            if(this.mIndexDbOpenHelper == null) {
               this.mIndexDbOpenHelper = new MediaVariationsIndexDatabase.IndexDbOpenHelper(this.mContext);
            }

            var1 = this.mIndexDbOpenHelper.getWritableDatabase();
         } finally {
            ;
         }

         return var1;
      }
   }

   static final class IndexEntry implements BaseColumns {

      public static final String COLUMN_NAME_CACHE_CHOICE = "cache_choice";
      public static final String COLUMN_NAME_CACHE_KEY = "cache_key";
      public static final String COLUMN_NAME_HEIGHT = "height";
      public static final String COLUMN_NAME_MEDIA_ID = "media_id";
      public static final String COLUMN_NAME_RESOURCE_ID = "resource_id";
      public static final String COLUMN_NAME_WIDTH = "width";
      public static final String TABLE_NAME = "media_variations_index";


   }

   static class IndexDbOpenHelper extends SQLiteOpenHelper {

      public static final String DATABASE_NAME = "FrescoMediaVariationsIndex.db";
      public static final int DATABASE_VERSION = 2;
      private static final String INTEGER_TYPE = " INTEGER";
      private static final String SQL_CREATE_ENTRIES = "CREATE TABLE media_variations_index (_id INTEGER PRIMARY KEY,media_id TEXT,width INTEGER,height INTEGER,cache_choice TEXT,cache_key TEXT,resource_id TEXT UNIQUE )";
      private static final String SQL_CREATE_INDEX = "CREATE INDEX index_media_id ON media_variations_index (media_id)";
      private static final String TEXT_TYPE = " TEXT";


      public IndexDbOpenHelper(Context var1) {
         super(var1, "FrescoMediaVariationsIndex.db", (CursorFactory)null, 2);
      }

      public void onCreate(SQLiteDatabase var1) {
         var1.beginTransaction();

         try {
            var1.execSQL("CREATE TABLE media_variations_index (_id INTEGER PRIMARY KEY,media_id TEXT,width INTEGER,height INTEGER,cache_choice TEXT,cache_key TEXT,resource_id TEXT UNIQUE )");
            var1.execSQL("CREATE INDEX index_media_id ON media_variations_index (media_id)");
            var1.setTransactionSuccessful();
         } finally {
            var1.endTransaction();
         }

      }

      public void onDowngrade(SQLiteDatabase var1, int var2, int var3) {
         this.onUpgrade(var1, var2, var3);
      }

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         var1.beginTransaction();

         try {
            var1.execSQL("DROP TABLE IF EXISTS media_variations_index");
            var1.setTransactionSuccessful();
         } finally {
            var1.endTransaction();
         }

         this.onCreate(var1);
      }
   }
}
