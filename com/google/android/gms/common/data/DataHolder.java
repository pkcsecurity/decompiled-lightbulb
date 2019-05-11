package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.data.zab;
import com.google.android.gms.common.data.zac;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.sqlite.CursorWrapper;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

@KeepForSdk
@KeepName
@SafeParcelable.Class(
   creator = "DataHolderCreator",
   validate = true
)
public final class DataHolder extends AbstractSafeParcelable implements Closeable {

   @KeepForSdk
   public static final Creator<DataHolder> CREATOR = new zac();
   private static final DataHolder.Builder zalx = new zab(new String[0], (String)null);
   private boolean mClosed;
   @SafeParcelable.VersionField(
      id = 1000
   )
   private final int zale;
   @SafeParcelable.Field(
      getter = "getColumns",
      id = 1
   )
   private final String[] zalp;
   private Bundle zalq;
   @SafeParcelable.Field(
      getter = "getWindows",
      id = 2
   )
   private final CursorWindow[] zalr;
   @SafeParcelable.Field(
      getter = "getStatusCode",
      id = 3
   )
   private final int zals;
   @SafeParcelable.Field(
      getter = "getMetadata",
      id = 4
   )
   private final Bundle zalt;
   private int[] zalu;
   private int zalv;
   private boolean zalw;


   @SafeParcelable.Constructor
   DataHolder(
      @SafeParcelable.Param(
         id = 1000
      ) int var1, 
      @SafeParcelable.Param(
         id = 1
      ) String[] var2, 
      @SafeParcelable.Param(
         id = 2
      ) CursorWindow[] var3, 
      @SafeParcelable.Param(
         id = 3
      ) int var4, 
      @SafeParcelable.Param(
         id = 4
      ) Bundle var5) {
      this.mClosed = false;
      this.zalw = true;
      this.zale = var1;
      this.zalp = var2;
      this.zalr = var3;
      this.zals = var4;
      this.zalt = var5;
   }

   @KeepForSdk
   public DataHolder(Cursor var1, int var2, Bundle var3) {
      this(new CursorWrapper(var1), var2, var3);
   }

   private DataHolder(DataHolder.Builder var1, int var2, Bundle var3) {
      this(var1.zalp, zaa(var1, -1), var2, (Bundle)null);
   }

   private DataHolder(DataHolder.Builder var1, int var2, Bundle var3, int var4) {
      this(var1.zalp, zaa(var1, -1), var2, var3);
   }

   // $FF: synthetic method
   DataHolder(DataHolder.Builder var1, int var2, Bundle var3, int var4, zab var5) {
      this(var1, var2, var3, -1);
   }

   // $FF: synthetic method
   DataHolder(DataHolder.Builder var1, int var2, Bundle var3, zab var4) {
      this(var1, var2, (Bundle)null);
   }

   private DataHolder(CursorWrapper var1, int var2, Bundle var3) {
      this(var1.getColumnNames(), zaa(var1), var2, var3);
   }

   @KeepForSdk
   public DataHolder(String[] var1, CursorWindow[] var2, int var3, Bundle var4) {
      this.mClosed = false;
      this.zalw = true;
      this.zale = 1;
      this.zalp = (String[])Preconditions.checkNotNull(var1);
      this.zalr = (CursorWindow[])Preconditions.checkNotNull(var2);
      this.zals = var3;
      this.zalt = var4;
      this.zaca();
   }

   @KeepForSdk
   public static DataHolder.Builder builder(String[] var0) {
      return new DataHolder.Builder(var0, (String)null, (zab)null);
   }

   @KeepForSdk
   public static DataHolder empty(int var0) {
      return new DataHolder(zalx, var0, (Bundle)null);
   }

   private final void zaa(String var1, int var2) {
      if(this.zalq != null && this.zalq.containsKey(var1)) {
         if(this.isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
         } else if(var2 < 0 || var2 >= this.zalv) {
            throw new CursorIndexOutOfBoundsException(var2, this.zalv);
         }
      } else {
         var1 = String.valueOf(var1);
         if(var1.length() != 0) {
            var1 = "No such column: ".concat(var1);
         } else {
            var1 = new String("No such column: ");
         }

         throw new IllegalArgumentException(var1);
      }
   }

   private static CursorWindow[] zaa(DataHolder.Builder param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static CursorWindow[] zaa(CursorWrapper param0) {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public final void close() {
      // $FF: Couldn't be decompiled
   }

   protected final void finalize() throws Throwable {
      try {
         if(this.zalw && this.zalr.length > 0 && !this.isClosed()) {
            this.close();
            String var1 = this.toString();
            StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 178);
            var2.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
            var2.append(var1);
            var2.append(")");
            Log.e("DataBuffer", var2.toString());
         }
      } finally {
         super.finalize();
      }

   }

   @KeepForSdk
   public final boolean getBoolean(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return Long.valueOf(this.zalr[var3].getLong(var2, this.zalq.getInt(var1))).longValue() == 1L;
   }

   @KeepForSdk
   public final byte[] getByteArray(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zalr[var3].getBlob(var2, this.zalq.getInt(var1));
   }

   @KeepForSdk
   public final int getCount() {
      return this.zalv;
   }

   @KeepForSdk
   public final int getInteger(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zalr[var3].getInt(var2, this.zalq.getInt(var1));
   }

   @KeepForSdk
   public final long getLong(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zalr[var3].getLong(var2, this.zalq.getInt(var1));
   }

   @KeepForSdk
   public final Bundle getMetadata() {
      return this.zalt;
   }

   @KeepForSdk
   public final int getStatusCode() {
      return this.zals;
   }

   @KeepForSdk
   public final String getString(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zalr[var3].getString(var2, this.zalq.getInt(var1));
   }

   @KeepForSdk
   public final int getWindowIndex(int var1) {
      int var3 = 0;
      boolean var4;
      if(var1 >= 0 && var1 < this.zalv) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4);

      int var2;
      while(true) {
         var2 = var3;
         if(var3 >= this.zalu.length) {
            break;
         }

         if(var1 < this.zalu[var3]) {
            var2 = var3 - 1;
            break;
         }

         ++var3;
      }

      var1 = var2;
      if(var2 == this.zalu.length) {
         var1 = var2 - 1;
      }

      return var1;
   }

   @KeepForSdk
   public final boolean hasColumn(String var1) {
      return this.zalq.containsKey(var1);
   }

   @KeepForSdk
   public final boolean hasNull(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zalr[var3].isNull(var2, this.zalq.getInt(var1));
   }

   @KeepForSdk
   public final boolean isClosed() {
      // $FF: Couldn't be decompiled
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeStringArray(var1, 1, this.zalp, false);
      SafeParcelWriter.writeTypedArray(var1, 2, this.zalr, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.getStatusCode());
      SafeParcelWriter.writeBundle(var1, 4, this.getMetadata(), false);
      SafeParcelWriter.writeInt(var1, 1000, this.zale);
      SafeParcelWriter.finishObjectHeader(var1, var3);
      if((var2 & 1) != 0) {
         this.close();
      }

   }

   public final float zaa(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zalr[var3].getFloat(var2, this.zalq.getInt(var1));
   }

   public final void zaa(String var1, int var2, int var3, CharArrayBuffer var4) {
      this.zaa(var1, var2);
      this.zalr[var3].copyStringToBuffer(var2, this.zalq.getInt(var1), var4);
   }

   public final double zab(String var1, int var2, int var3) {
      this.zaa(var1, var2);
      return this.zalr[var3].getDouble(var2, this.zalq.getInt(var1));
   }

   public final void zaca() {
      this.zalq = new Bundle();
      byte var3 = 0;

      int var1;
      for(var1 = 0; var1 < this.zalp.length; ++var1) {
         this.zalq.putInt(this.zalp[var1], var1);
      }

      this.zalu = new int[this.zalr.length];
      int var2 = 0;

      for(var1 = var3; var1 < this.zalr.length; ++var1) {
         this.zalu[var1] = var2;
         int var4 = this.zalr[var1].getStartPosition();
         var2 += this.zalr[var1].getNumRows() - (var2 - var4);
      }

      this.zalv = var2;
   }

   public static final class zaa extends RuntimeException {

      public zaa(String var1) {
         super(var1);
      }
   }

   @KeepForSdk
   public static class Builder {

      private final String[] zalp;
      private final ArrayList<HashMap<String, Object>> zaly;
      private final String zalz;
      private final HashMap<Object, Integer> zama;
      private boolean zamb;
      private String zamc;


      private Builder(String[] var1, String var2) {
         this.zalp = (String[])Preconditions.checkNotNull(var1);
         this.zaly = new ArrayList();
         this.zalz = var2;
         this.zama = new HashMap();
         this.zamb = false;
         this.zamc = null;
      }

      // $FF: synthetic method
      Builder(String[] var1, String var2, zab var3) {
         this(var1, (String)null);
      }

      // $FF: synthetic method
      static ArrayList zab(DataHolder.Builder var0) {
         return var0.zaly;
      }

      @KeepForSdk
      public DataHolder build(int var1) {
         return new DataHolder(this, var1, (Bundle)null, (zab)null);
      }

      @KeepForSdk
      public DataHolder build(int var1, Bundle var2) {
         return new DataHolder(this, var1, var2, -1, (zab)null);
      }

      @KeepForSdk
      public DataHolder.Builder withRow(ContentValues var1) {
         Asserts.checkNotNull(var1);
         HashMap var2 = new HashMap(var1.size());
         Iterator var4 = var1.valueSet().iterator();

         while(var4.hasNext()) {
            Entry var3 = (Entry)var4.next();
            var2.put((String)var3.getKey(), var3.getValue());
         }

         return this.zaa(var2);
      }

      public DataHolder.Builder zaa(HashMap<String, Object> var1) {
         int var2;
         label22: {
            Asserts.checkNotNull(var1);
            if(this.zalz != null) {
               Object var3 = var1.get(this.zalz);
               if(var3 != null) {
                  Integer var4 = (Integer)this.zama.get(var3);
                  if(var4 != null) {
                     var2 = var4.intValue();
                     break label22;
                  }

                  this.zama.put(var3, Integer.valueOf(this.zaly.size()));
               }
            }

            var2 = -1;
         }

         if(var2 == -1) {
            this.zaly.add(var1);
         } else {
            this.zaly.remove(var2);
            this.zaly.add(var2, var1);
         }

         this.zamb = false;
         return this;
      }
   }
}
