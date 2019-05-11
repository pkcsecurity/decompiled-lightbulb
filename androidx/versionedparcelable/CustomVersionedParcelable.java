package androidx.versionedparcelable;

import android.support.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcelable;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class CustomVersionedParcelable implements VersionedParcelable {

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public void onPostParceling() {}

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public void onPreParceling(boolean var1) {}
}
