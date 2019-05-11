package com.facebook.cache.common;

import javax.annotation.Nullable;

public interface CacheErrorLogger {

   void logError(CacheErrorLogger.CacheErrorCategory var1, Class<?> var2, String var3, @Nullable Throwable var4);

   public static enum CacheErrorCategory {

      // $FF: synthetic field
      private static final CacheErrorLogger.CacheErrorCategory[] $VALUES = new CacheErrorLogger.CacheErrorCategory[]{READ_DECODE, READ_FILE, READ_FILE_NOT_FOUND, READ_INVALID_ENTRY, WRITE_ENCODE, WRITE_CREATE_TEMPFILE, WRITE_UPDATE_FILE_NOT_FOUND, WRITE_RENAME_FILE_TEMPFILE_NOT_FOUND, WRITE_RENAME_FILE_TEMPFILE_PARENT_NOT_FOUND, WRITE_RENAME_FILE_OTHER, WRITE_CREATE_DIR, WRITE_CALLBACK_ERROR, WRITE_INVALID_ENTRY, DELETE_FILE, EVICTION, GENERIC_IO, OTHER};
      DELETE_FILE("DELETE_FILE", 13),
      EVICTION("EVICTION", 14),
      GENERIC_IO("GENERIC_IO", 15),
      OTHER("OTHER", 16),
      READ_DECODE("READ_DECODE", 0),
      READ_FILE("READ_FILE", 1),
      READ_FILE_NOT_FOUND("READ_FILE_NOT_FOUND", 2),
      READ_INVALID_ENTRY("READ_INVALID_ENTRY", 3),
      WRITE_CALLBACK_ERROR("WRITE_CALLBACK_ERROR", 11),
      WRITE_CREATE_DIR("WRITE_CREATE_DIR", 10),
      WRITE_CREATE_TEMPFILE("WRITE_CREATE_TEMPFILE", 5),
      WRITE_ENCODE("WRITE_ENCODE", 4),
      WRITE_INVALID_ENTRY("WRITE_INVALID_ENTRY", 12),
      WRITE_RENAME_FILE_OTHER("WRITE_RENAME_FILE_OTHER", 9),
      WRITE_RENAME_FILE_TEMPFILE_NOT_FOUND("WRITE_RENAME_FILE_TEMPFILE_NOT_FOUND", 7),
      WRITE_RENAME_FILE_TEMPFILE_PARENT_NOT_FOUND("WRITE_RENAME_FILE_TEMPFILE_PARENT_NOT_FOUND", 8),
      WRITE_UPDATE_FILE_NOT_FOUND("WRITE_UPDATE_FILE_NOT_FOUND", 6);


      private CacheErrorCategory(String var1, int var2) {}
   }
}
