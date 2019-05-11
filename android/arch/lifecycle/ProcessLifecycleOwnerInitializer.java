package android.arch.lifecycle;

import android.arch.lifecycle.LifecycleDispatcher;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ProcessLifecycleOwnerInitializer extends ContentProvider {

   public int delete(@NonNull Uri var1, String var2, String[] var3) {
      return 0;
   }

   @Nullable
   public String getType(@NonNull Uri var1) {
      return null;
   }

   @Nullable
   public Uri insert(@NonNull Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      LifecycleDispatcher.a(this.getContext());
      k.a(this.getContext());
      return true;
   }

   @Nullable
   public Cursor query(@NonNull Uri var1, String[] var2, String var3, String[] var4, String var5) {
      return null;
   }

   public int update(@NonNull Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }
}
