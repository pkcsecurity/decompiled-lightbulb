package androidx.media;

import android.support.annotation.RestrictTo;
import android.support.v4.media.AudioAttributesImplBase;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class AudioAttributesImplBaseParcelizer {

   public static AudioAttributesImplBase read(q var0) {
      AudioAttributesImplBase var1 = new AudioAttributesImplBase();
      var1.mUsage = var0.b(var1.mUsage, 1);
      var1.mContentType = var0.b(var1.mContentType, 2);
      var1.mFlags = var0.b(var1.mFlags, 3);
      var1.mLegacyStream = var0.b(var1.mLegacyStream, 4);
      return var1;
   }

   public static void write(AudioAttributesImplBase var0, q var1) {
      var1.a(false, false);
      var1.a(var0.mUsage, 1);
      var1.a(var0.mContentType, 2);
      var1.a(var0.mFlags, 3);
      var1.a(var0.mLegacyStream, 4);
   }
}
