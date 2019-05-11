package androidx.media;

import android.media.AudioAttributes;
import android.support.annotation.RestrictTo;
import android.support.v4.media.AudioAttributesImplApi21;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class AudioAttributesImplApi21Parcelizer {

   public static AudioAttributesImplApi21 read(q var0) {
      AudioAttributesImplApi21 var1 = new AudioAttributesImplApi21();
      var1.mAudioAttributes = (AudioAttributes)var0.b(var1.mAudioAttributes, 1);
      var1.mLegacyStreamType = var0.b(var1.mLegacyStreamType, 2);
      return var1;
   }

   public static void write(AudioAttributesImplApi21 var0, q var1) {
      var1.a(false, false);
      var1.a(var0.mAudioAttributes, 1);
      var1.a(var0.mLegacyStreamType, 2);
   }
}
