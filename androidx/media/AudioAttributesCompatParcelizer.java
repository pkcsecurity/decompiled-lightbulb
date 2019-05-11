package androidx.media;

import android.support.annotation.RestrictTo;
import android.support.v4.media.AudioAttributesCompat;
import android.support.v4.media.AudioAttributesImpl;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class AudioAttributesCompatParcelizer {

   public static AudioAttributesCompat read(q var0) {
      AudioAttributesCompat var1 = new AudioAttributesCompat();
      var1.mImpl = (AudioAttributesImpl)var0.b(var1.mImpl, 1);
      return var1;
   }

   public static void write(AudioAttributesCompat var0, q var1) {
      var1.a(false, false);
      var1.a(var0.mImpl, 1);
   }
}
