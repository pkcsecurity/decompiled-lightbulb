package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.WriterCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DiskStorage {

   void clearAll() throws IOException;

   boolean contains(String var1, Object var2) throws IOException;

   DiskStorage.DiskDumpInfo getDumpInfo() throws IOException;

   Collection<DiskStorage.Entry> getEntries() throws IOException;

   BinaryResource getResource(String var1, Object var2) throws IOException;

   String getStorageName();

   DiskStorage.Inserter insert(String var1, Object var2) throws IOException;

   boolean isEnabled();

   boolean isExternal();

   void purgeUnexpectedResources();

   long remove(DiskStorage.Entry var1) throws IOException;

   long remove(String var1) throws IOException;

   boolean touch(String var1, Object var2) throws IOException;

   public static class DiskDumpInfoEntry {

      public final String firstBits;
      public final String path;
      public final float size;
      public final String type;


      protected DiskDumpInfoEntry(String var1, String var2, float var3, String var4) {
         this.path = var1;
         this.type = var2;
         this.size = var3;
         this.firstBits = var4;
      }
   }

   public static class DiskDumpInfo {

      public List<DiskStorage.DiskDumpInfoEntry> entries = new ArrayList();
      public Map<String, Integer> typeCounts = new HashMap();


   }

   public interface Entry {

      String getId();

      BinaryResource getResource();

      long getSize();

      long getTimestamp();
   }

   public interface Inserter {

      boolean cleanUp();

      BinaryResource commit(Object var1) throws IOException;

      void writeData(WriterCallback var1, Object var2) throws IOException;
   }
}
