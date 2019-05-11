package com.facebook;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.ProfileManager;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import org.json.JSONObject;

public final class Profile implements Parcelable {

   public static final Creator<Profile> CREATOR = new Creator() {
      public Profile createFromParcel(Parcel var1) {
         return new Profile(var1, null);
      }
      public Profile[] newArray(int var1) {
         return new Profile[var1];
      }
   };
   private static final String FIRST_NAME_KEY = "first_name";
   private static final String ID_KEY = "id";
   private static final String LAST_NAME_KEY = "last_name";
   private static final String LINK_URI_KEY = "link_uri";
   private static final String MIDDLE_NAME_KEY = "middle_name";
   private static final String NAME_KEY = "name";
   private static final String TAG = "Profile";
   @Nullable
   private final String firstName;
   @Nullable
   private final String id;
   @Nullable
   private final String lastName;
   @Nullable
   private final Uri linkUri;
   @Nullable
   private final String middleName;
   @Nullable
   private final String name;


   private Profile(Parcel var1) {
      this.id = var1.readString();
      this.firstName = var1.readString();
      this.middleName = var1.readString();
      this.lastName = var1.readString();
      this.name = var1.readString();
      String var2 = var1.readString();
      Uri var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = Uri.parse(var2);
      }

      this.linkUri = var3;
   }

   // $FF: synthetic method
   Profile(Parcel var1, Object var2) {
      this(var1);
   }

   public Profile(String var1, @Nullable String var2, @Nullable String var3, @Nullable String var4, @Nullable String var5, @Nullable Uri var6) {
      Validate.notNullOrEmpty(var1, "id");
      this.id = var1;
      this.firstName = var2;
      this.middleName = var3;
      this.lastName = var4;
      this.name = var5;
      this.linkUri = var6;
   }

   Profile(JSONObject var1) {
      Object var2 = null;
      this.id = var1.optString("id", (String)null);
      this.firstName = var1.optString("first_name", (String)null);
      this.middleName = var1.optString("middle_name", (String)null);
      this.lastName = var1.optString("last_name", (String)null);
      this.name = var1.optString("name", (String)null);
      String var3 = var1.optString("link_uri", (String)null);
      Uri var4;
      if(var3 == null) {
         var4 = (Uri)var2;
      } else {
         var4 = Uri.parse(var3);
      }

      this.linkUri = var4;
   }

   public static void fetchProfileForCurrentAccessToken() {
      AccessToken var0 = AccessToken.getCurrentAccessToken();
      if(!AccessToken.isCurrentAccessTokenActive()) {
         setCurrentProfile((Profile)null);
      } else {
         Utility.getGraphMeRequestWithCacheAsync(var0.getToken(), new Utility.GraphMeRequestWithCacheCallback() {
            public void onFailure(FacebookException var1) {
               String var2 = Profile.TAG;
               StringBuilder var3 = new StringBuilder();
               var3.append("Got unexpected exception: ");
               var3.append(var1);
               Log.e(var2, var3.toString());
            }
            public void onSuccess(JSONObject var1) {
               String var2 = var1.optString("id");
               if(var2 != null) {
                  String var7 = var1.optString("link");
                  String var3 = var1.optString("first_name");
                  String var4 = var1.optString("middle_name");
                  String var5 = var1.optString("last_name");
                  String var6 = var1.optString("name");
                  Uri var8;
                  if(var7 != null) {
                     var8 = Uri.parse(var7);
                  } else {
                     var8 = null;
                  }

                  Profile.setCurrentProfile(new Profile(var2, var3, var4, var5, var6, var8));
               }
            }
         });
      }
   }

   public static Profile getCurrentProfile() {
      return ProfileManager.getInstance().getCurrentProfile();
   }

   public static void setCurrentProfile(@Nullable Profile var0) {
      ProfileManager.getInstance().setCurrentProfile(var0);
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof Profile)) {
         return false;
      } else {
         Profile var2 = (Profile)var1;
         if(this.id.equals(var2.id) && this.firstName == null) {
            if(var2.firstName == null) {
               return true;
            }
         } else if(this.firstName.equals(var2.firstName) && this.middleName == null) {
            if(var2.middleName == null) {
               return true;
            }
         } else if(this.middleName.equals(var2.middleName) && this.lastName == null) {
            if(var2.lastName == null) {
               return true;
            }
         } else if(this.lastName.equals(var2.lastName) && this.name == null) {
            if(var2.name == null) {
               return true;
            }
         } else {
            if(!this.name.equals(var2.name) || this.linkUri != null) {
               return this.linkUri.equals(var2.linkUri);
            }

            if(var2.linkUri == null) {
               return true;
            }
         }

         return false;
      }
   }

   public String getFirstName() {
      return this.firstName;
   }

   public String getId() {
      return this.id;
   }

   public String getLastName() {
      return this.lastName;
   }

   public Uri getLinkUri() {
      return this.linkUri;
   }

   public String getMiddleName() {
      return this.middleName;
   }

   public String getName() {
      return this.name;
   }

   public Uri getProfilePictureUri(int var1, int var2) {
      return ImageRequest.getProfilePictureUri(this.id, var1, var2);
   }

   public int hashCode() {
      int var2 = 527 + this.id.hashCode();
      int var1 = var2;
      if(this.firstName != null) {
         var1 = var2 * 31 + this.firstName.hashCode();
      }

      var2 = var1;
      if(this.middleName != null) {
         var2 = var1 * 31 + this.middleName.hashCode();
      }

      var1 = var2;
      if(this.lastName != null) {
         var1 = var2 * 31 + this.lastName.hashCode();
      }

      var2 = var1;
      if(this.name != null) {
         var2 = var1 * 31 + this.name.hashCode();
      }

      var1 = var2;
      if(this.linkUri != null) {
         var1 = var2 * 31 + this.linkUri.hashCode();
      }

      return var1;
   }

   JSONObject toJSONObject() {
      // $FF: Couldn't be decompiled
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeString(this.id);
      var1.writeString(this.firstName);
      var1.writeString(this.middleName);
      var1.writeString(this.lastName);
      var1.writeString(this.name);
      String var3;
      if(this.linkUri == null) {
         var3 = null;
      } else {
         var3 = this.linkUri.toString();
      }

      var1.writeString(var3);
   }
}
