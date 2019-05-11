package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.zaa;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@KeepForSdk
@ShowFirstParty
@SafeParcelable.Class(
   creator = "BitmapTeleporterCreator"
)
public class BitmapTeleporter extends AbstractSafeParcelable implements ReflectedParcelable {

   @KeepForSdk
   public static final Creator<BitmapTeleporter> CREATOR = new zaa();
   @SafeParcelable.Field(
      id = 3
   )
   private final int mType;
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @SafeParcelable.Field(
      id = 2
   )
   private ParcelFileDescriptor zalf;
   private Bitmap zalg;
   private boolean zalh;
   private File zali;


   @SafeParcelable.Constructor
   BitmapTeleporter(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) ParcelFileDescriptor var2, 
      @SafeParcelable.Param(
         id = 3
      ) int var3) {
      this.zale = var1;
      this.zalf = var2;
      this.mType = var3;
      this.zalg = null;
      this.zalh = false;
   }

   @KeepForSdk
   public BitmapTeleporter(Bitmap var1) {
      this.zale = 1;
      this.zalf = null;
      this.mType = 0;
      this.zalg = var1;
      this.zalh = true;
   }

   private static void zaa(Closeable var0) {
      try {
         var0.close();
      } catch (IOException var1) {
         Log.w("BitmapTeleporter", "Could not close stream", var1);
      }
   }

   private final FileOutputStream zabz() {
      if(this.zali == null) {
         throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
      } else {
         File var1;
         try {
            var1 = File.createTempFile("teleporter", ".tmp", this.zali);
         } catch (IOException var4) {
            throw new IllegalStateException("Could not create temporary file", var4);
         }

         FileOutputStream var2;
         try {
            var2 = new FileOutputStream(var1);
            this.zalf = ParcelFileDescriptor.open(var1, 268435456);
         } catch (FileNotFoundException var3) {
            throw new IllegalStateException("Temporary file is somehow already deleted");
         }

         var1.delete();
         return var2;
      }
   }

   @KeepForSdk
   public Bitmap get() {
      if(!this.zalh) {
         DataInputStream var3 = new DataInputStream(new AutoCloseInputStream(this.zalf));

         int var1;
         int var2;
         Config var4;
         byte[] var5;
         try {
            var5 = new byte[var3.readInt()];
            var1 = var3.readInt();
            var2 = var3.readInt();
            var4 = Config.valueOf(var3.readUTF());
            var3.read(var5);
         } catch (IOException var8) {
            throw new IllegalStateException("Could not read from parcel file descriptor", var8);
         } finally {
            zaa(var3);
         }

         ByteBuffer var10 = ByteBuffer.wrap(var5);
         Bitmap var11 = Bitmap.createBitmap(var1, var2, var4);
         var11.copyPixelsFromBuffer(var10);
         this.zalg = var11;
         this.zalh = true;
      }

      return this.zalg;
   }

   @KeepForSdk
   public void release() {
      if(!this.zalh) {
         try {
            this.zalf.close();
            return;
         } catch (IOException var2) {
            Log.w("BitmapTeleporter", "Could not close PFD", var2);
         }
      }

   }

   @KeepForSdk
   public void setTempDir(File var1) {
      if(var1 == null) {
         throw new NullPointerException("Cannot set null temp directory");
      } else {
         this.zali = var1;
      }
   }

   public void writeToParcel(Parcel var1, int var2) {
      if(this.zalf == null) {
         Bitmap var5 = this.zalg;
         ByteBuffer var4 = ByteBuffer.allocate(var5.getRowBytes() * var5.getHeight());
         var5.copyPixelsToBuffer(var4);
         byte[] var6 = var4.array();
         DataOutputStream var11 = new DataOutputStream(new BufferedOutputStream(this.zabz()));

         try {
            var11.writeInt(var6.length);
            var11.writeInt(var5.getWidth());
            var11.writeInt(var5.getHeight());
            var11.writeUTF(var5.getConfig().toString());
            var11.write(var6);
         } catch (IOException var9) {
            throw new IllegalStateException("Could not write into unlinked file", var9);
         } finally {
            zaa(var11);
         }
      }

      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeParcelable(var1, 2, this.zalf, var2 | 1, false);
      SafeParcelWriter.writeInt(var1, 3, this.mType);
      SafeParcelWriter.finishObjectHeader(var1, var3);
      this.zalf = null;
   }
}
