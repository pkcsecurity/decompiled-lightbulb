package android.support.v4.media;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.media.AudioAttributesCompat;
import android.support.v4.media.AudioAttributesImpl;
import android.util.Log;
import java.lang.reflect.Method;

@TargetApi(21)
public class AudioAttributesImplApi21 implements AudioAttributesImpl {

   private static final String TAG = "AudioAttributesCompat21";
   static Method sAudioAttributesToLegacyStreamType;
   AudioAttributes mAudioAttributes;
   int mLegacyStreamType;


   AudioAttributesImplApi21() {
      this.mLegacyStreamType = -1;
   }

   AudioAttributesImplApi21(AudioAttributes var1) {
      this(var1, -1);
   }

   AudioAttributesImplApi21(AudioAttributes var1, int var2) {
      this.mLegacyStreamType = -1;
      this.mAudioAttributes = var1;
      this.mLegacyStreamType = var2;
   }

   public static AudioAttributesImpl fromBundle(Bundle var0) {
      if(var0 == null) {
         return null;
      } else {
         AudioAttributes var1 = (AudioAttributes)var0.getParcelable("android.support.v4.media.audio_attrs.FRAMEWORKS");
         return var1 == null?null:new AudioAttributesImplApi21(var1, var0.getInt("android.support.v4.media.audio_attrs.LEGACY_STREAM_TYPE", -1));
      }
   }

   static Method getAudioAttributesToLegacyStreamTypeMethod() {
      try {
         if(sAudioAttributesToLegacyStreamType == null) {
            sAudioAttributesToLegacyStreamType = AudioAttributes.class.getMethod("toLegacyStreamType", new Class[]{AudioAttributes.class});
         }
      } catch (NoSuchMethodException var1) {
         return null;
      }

      return sAudioAttributesToLegacyStreamType;
   }

   public boolean equals(Object var1) {
      if(!(var1 instanceof AudioAttributesImplApi21)) {
         return false;
      } else {
         AudioAttributesImplApi21 var2 = (AudioAttributesImplApi21)var1;
         return this.mAudioAttributes.equals(var2.mAudioAttributes);
      }
   }

   public Object getAudioAttributes() {
      return this.mAudioAttributes;
   }

   public int getContentType() {
      return this.mAudioAttributes.getContentType();
   }

   public int getFlags() {
      return this.mAudioAttributes.getFlags();
   }

   public int getLegacyStreamType() {
      if(this.mLegacyStreamType != -1) {
         return this.mLegacyStreamType;
      } else {
         Method var2 = getAudioAttributesToLegacyStreamTypeMethod();
         if(var2 == null) {
            StringBuilder var5 = new StringBuilder();
            var5.append("No AudioAttributes#toLegacyStreamType() on API: ");
            var5.append(VERSION.SDK_INT);
            Log.w("AudioAttributesCompat21", var5.toString());
            return -1;
         } else {
            try {
               int var1 = ((Integer)var2.invoke((Object)null, new Object[]{this.mAudioAttributes})).intValue();
               return var1;
            } catch (IllegalAccessException var4) {
               StringBuilder var3 = new StringBuilder();
               var3.append("getLegacyStreamType() failed on API: ");
               var3.append(VERSION.SDK_INT);
               Log.w("AudioAttributesCompat21", var3.toString(), var4);
               return -1;
            }
         }
      }
   }

   public int getRawLegacyStreamType() {
      return this.mLegacyStreamType;
   }

   public int getUsage() {
      return this.mAudioAttributes.getUsage();
   }

   public int getVolumeControlStream() {
      return VERSION.SDK_INT >= 26?this.mAudioAttributes.getVolumeControlStream():AudioAttributesCompat.toVolumeStreamType(true, this.getFlags(), this.getUsage());
   }

   public int hashCode() {
      return this.mAudioAttributes.hashCode();
   }

   @NonNull
   public Bundle toBundle() {
      Bundle var1 = new Bundle();
      var1.putParcelable("android.support.v4.media.audio_attrs.FRAMEWORKS", this.mAudioAttributes);
      if(this.mLegacyStreamType != -1) {
         var1.putInt("android.support.v4.media.audio_attrs.LEGACY_STREAM_TYPE", this.mLegacyStreamType);
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("AudioAttributesCompat: audioattributes=");
      var1.append(this.mAudioAttributes);
      return var1.toString();
   }
}
