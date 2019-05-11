package android.support.v4.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.os.CancellationSignal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader extends AsyncTaskLoader<Cursor> {

   CancellationSignal mCancellationSignal;
   Cursor mCursor;
   final Loader.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver();
   String[] mProjection;
   String mSelection;
   String[] mSelectionArgs;
   String mSortOrder;
   Uri mUri;


   public CursorLoader(@NonNull Context var1) {
      super(var1);
   }

   public CursorLoader(@NonNull Context var1, @NonNull Uri var2, @Nullable String[] var3, @Nullable String var4, @Nullable String[] var5, @Nullable String var6) {
      super(var1);
      this.mUri = var2;
      this.mProjection = var3;
      this.mSelection = var4;
      this.mSelectionArgs = var5;
      this.mSortOrder = var6;
   }

   public void cancelLoadInBackground() {
      // $FF: Couldn't be decompiled
   }

   public void deliverResult(Cursor var1) {
      if(this.isReset()) {
         if(var1 != null) {
            var1.close();
         }

      } else {
         Cursor var2 = this.mCursor;
         this.mCursor = var1;
         if(this.isStarted()) {
            super.deliverResult(var1);
         }

         if(var2 != null && var2 != var1 && !var2.isClosed()) {
            var2.close();
         }

      }
   }

   @Deprecated
   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      super.dump(var1, var2, var3, var4);
      var3.print(var1);
      var3.print("mUri=");
      var3.println(this.mUri);
      var3.print(var1);
      var3.print("mProjection=");
      var3.println(Arrays.toString(this.mProjection));
      var3.print(var1);
      var3.print("mSelection=");
      var3.println(this.mSelection);
      var3.print(var1);
      var3.print("mSelectionArgs=");
      var3.println(Arrays.toString(this.mSelectionArgs));
      var3.print(var1);
      var3.print("mSortOrder=");
      var3.println(this.mSortOrder);
      var3.print(var1);
      var3.print("mCursor=");
      var3.println(this.mCursor);
      var3.print(var1);
      var3.print("mContentChanged=");
      var3.println(this.mContentChanged);
   }

   @Nullable
   public String[] getProjection() {
      return this.mProjection;
   }

   @Nullable
   public String getSelection() {
      return this.mSelection;
   }

   @Nullable
   public String[] getSelectionArgs() {
      return this.mSelectionArgs;
   }

   @Nullable
   public String getSortOrder() {
      return this.mSortOrder;
   }

   @NonNull
   public Uri getUri() {
      return this.mUri;
   }

   public Cursor loadInBackground() {
      // $FF: Couldn't be decompiled
   }

   public void onCanceled(Cursor var1) {
      if(var1 != null && !var1.isClosed()) {
         var1.close();
      }

   }

   protected void onReset() {
      super.onReset();
      this.onStopLoading();
      if(this.mCursor != null && !this.mCursor.isClosed()) {
         this.mCursor.close();
      }

      this.mCursor = null;
   }

   protected void onStartLoading() {
      if(this.mCursor != null) {
         this.deliverResult(this.mCursor);
      }

      if(this.takeContentChanged() || this.mCursor == null) {
         this.forceLoad();
      }

   }

   protected void onStopLoading() {
      this.cancelLoad();
   }

   public void setProjection(@Nullable String[] var1) {
      this.mProjection = var1;
   }

   public void setSelection(@Nullable String var1) {
      this.mSelection = var1;
   }

   public void setSelectionArgs(@Nullable String[] var1) {
      this.mSelectionArgs = var1;
   }

   public void setSortOrder(@Nullable String var1) {
      this.mSortOrder = var1;
   }

   public void setUri(@NonNull Uri var1) {
      this.mUri = var1;
   }
}
