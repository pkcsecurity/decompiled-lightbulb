package com.facebook.react.modules.blob;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.support.annotation.Nullable;
import com.facebook.react.ReactApplication;
import com.facebook.react.modules.blob.BlobModule;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class BlobProvider extends ContentProvider {

   public int delete(Uri var1, String var2, String[] var3) {
      return 0;
   }

   @Nullable
   public String getType(Uri var1) {
      return null;
   }

   @Nullable
   public Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      return true;
   }

   public ParcelFileDescriptor openFile(Uri var1, String var2) throws FileNotFoundException {
      if(!var2.equals("r")) {
         StringBuilder var10 = new StringBuilder();
         var10.append("Cannot open ");
         var10.append(var1.toString());
         var10.append(" in mode \'");
         var10.append(var2);
         var10.append("\'");
         throw new FileNotFoundException(var10.toString());
      } else {
         Context var7 = this.getContext().getApplicationContext();
         BlobModule var8;
         if(var7 instanceof ReactApplication) {
            var8 = (BlobModule)((ReactApplication)var7).getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getNativeModule(BlobModule.class);
         } else {
            var8 = null;
         }

         if(var8 == null) {
            throw new RuntimeException("No blob module associated with BlobProvider");
         } else {
            byte[] var11 = var8.resolve(var1);
            if(var11 == null) {
               StringBuilder var12 = new StringBuilder();
               var12.append("Cannot open ");
               var12.append(var1.toString());
               var12.append(", blob not found.");
               throw new FileNotFoundException(var12.toString());
            } else {
               ParcelFileDescriptor[] var3;
               try {
                  var3 = ParcelFileDescriptor.createPipe();
               } catch (IOException var5) {
                  return null;
               }

               ParcelFileDescriptor var6 = var3[0];
               AutoCloseOutputStream var9 = new AutoCloseOutputStream(var3[1]);

               try {
                  var9.write(var11);
                  var9.close();
                  return var6;
               } catch (IOException var4) {
                  return null;
               }
            }
         }
      }
   }

   @Nullable
   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      return null;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }
}
