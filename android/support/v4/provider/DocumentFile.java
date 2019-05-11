package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.provider.RawDocumentFile;
import android.support.v4.provider.SingleDocumentFile;
import android.support.v4.provider.TreeDocumentFile;
import java.io.File;

public abstract class DocumentFile {

   static final String TAG = "DocumentFile";
   @Nullable
   private final DocumentFile mParent;


   DocumentFile(@Nullable DocumentFile var1) {
      this.mParent = var1;
   }

   @NonNull
   public static DocumentFile fromFile(@NonNull File var0) {
      return new RawDocumentFile((DocumentFile)null, var0);
   }

   @Nullable
   public static DocumentFile fromSingleUri(@NonNull Context var0, @NonNull Uri var1) {
      return VERSION.SDK_INT >= 19?new SingleDocumentFile((DocumentFile)null, var0, var1):null;
   }

   @Nullable
   public static DocumentFile fromTreeUri(@NonNull Context var0, @NonNull Uri var1) {
      return VERSION.SDK_INT >= 21?new TreeDocumentFile((DocumentFile)null, var0, DocumentsContract.buildDocumentUriUsingTree(var1, DocumentsContract.getTreeDocumentId(var1))):null;
   }

   public static boolean isDocumentUri(@NonNull Context var0, @Nullable Uri var1) {
      return VERSION.SDK_INT >= 19?DocumentsContract.isDocumentUri(var0, var1):false;
   }

   public abstract boolean canRead();

   public abstract boolean canWrite();

   @Nullable
   public abstract DocumentFile createDirectory(@NonNull String var1);

   @Nullable
   public abstract DocumentFile createFile(@NonNull String var1, @NonNull String var2);

   public abstract boolean delete();

   public abstract boolean exists();

   @Nullable
   public DocumentFile findFile(@NonNull String var1) {
      DocumentFile[] var4 = this.listFiles();
      int var3 = var4.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         DocumentFile var5 = var4[var2];
         if(var1.equals(var5.getName())) {
            return var5;
         }
      }

      return null;
   }

   @Nullable
   public abstract String getName();

   @Nullable
   public DocumentFile getParentFile() {
      return this.mParent;
   }

   @Nullable
   public abstract String getType();

   @NonNull
   public abstract Uri getUri();

   public abstract boolean isDirectory();

   public abstract boolean isFile();

   public abstract boolean isVirtual();

   public abstract long lastModified();

   public abstract long length();

   @NonNull
   public abstract DocumentFile[] listFiles();

   public abstract boolean renameTo(@NonNull String var1);
}
