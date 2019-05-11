package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.images.zae;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(
   creator = "WebImageCreator"
)
public final class WebImage extends AbstractSafeParcelable {

   public static final Creator<WebImage> CREATOR = new zae();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @SafeParcelable.Field(
      getter = "getWidth",
      id = 3
   )
   private final int zand;
   @SafeParcelable.Field(
      getter = "getHeight",
      id = 4
   )
   private final int zane;
   @SafeParcelable.Field(
      getter = "getUrl",
      id = 2
   )
   private final Uri zanf;


   @SafeParcelable.Constructor
   WebImage(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) Uri var2, 
      @SafeParcelable.Param(
         id = 3
      ) int var3, 
      @SafeParcelable.Param(
         id = 4
      ) int var4) {
      this.zale = var1;
      this.zanf = var2;
      this.zand = var3;
      this.zane = var4;
   }

   public WebImage(Uri var1) throws IllegalArgumentException {
      this(var1, 0, 0);
   }

   public WebImage(Uri var1, int var2, int var3) throws IllegalArgumentException {
      this(1, var1, var2, var3);
      if(var1 == null) {
         throw new IllegalArgumentException("url cannot be null");
      } else if(var2 < 0 || var3 < 0) {
         throw new IllegalArgumentException("width and height must not be negative");
      }
   }

   @KeepForSdk
   public WebImage(JSONObject var1) throws IllegalArgumentException {
      this(zaa(var1), var1.optInt("width", 0), var1.optInt("height", 0));
   }

   private static Uri zaa(JSONObject var0) {
      if(var0.has("url")) {
         try {
            Uri var2 = Uri.parse(var0.getString("url"));
            return var2;
         } catch (JSONException var1) {
            ;
         }
      }

      return null;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(!(var1 instanceof WebImage)) {
            return false;
         } else {
            WebImage var2 = (WebImage)var1;
            return Objects.equal(this.zanf, var2.zanf) && this.zand == var2.zand && this.zane == var2.zane;
         }
      } else {
         return false;
      }
   }

   public final int getHeight() {
      return this.zane;
   }

   public final Uri getUrl() {
      return this.zanf;
   }

   public final int getWidth() {
      return this.zand;
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{this.zanf, Integer.valueOf(this.zand), Integer.valueOf(this.zane)});
   }

   @KeepForSdk
   public final JSONObject toJson() {
      JSONObject var1 = new JSONObject();

      try {
         var1.put("url", this.zanf.toString());
         var1.put("width", this.zand);
         var1.put("height", this.zane);
         return var1;
      } catch (JSONException var3) {
         return var1;
      }
   }

   public final String toString() {
      return String.format(Locale.US, "Image %dx%d %s", new Object[]{Integer.valueOf(this.zand), Integer.valueOf(this.zane), this.zanf.toString()});
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeParcelable(var1, 2, this.getUrl(), var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.getWidth());
      SafeParcelWriter.writeInt(var1, 4, this.getHeight());
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
