package android.support.v4.app;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.IconCompat;

public class Person {

   private static final String ICON_KEY = "icon";
   private static final String IS_BOT_KEY = "isBot";
   private static final String IS_IMPORTANT_KEY = "isImportant";
   private static final String KEY_KEY = "key";
   private static final String NAME_KEY = "name";
   private static final String URI_KEY = "uri";
   @Nullable
   IconCompat mIcon;
   boolean mIsBot;
   boolean mIsImportant;
   @Nullable
   String mKey;
   @Nullable
   CharSequence mName;
   @Nullable
   String mUri;


   Person(Person.Builder var1) {
      this.mName = var1.mName;
      this.mIcon = var1.mIcon;
      this.mUri = var1.mUri;
      this.mKey = var1.mKey;
      this.mIsBot = var1.mIsBot;
      this.mIsImportant = var1.mIsImportant;
   }

   @NonNull
   @RequiresApi(28)
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static Person fromAndroidPerson(@NonNull android.app.Person var0) {
      Person.Builder var2 = (new Person.Builder()).setName(var0.getName());
      IconCompat var1;
      if(var0.getIcon() != null) {
         var1 = IconCompat.createFromIcon(var0.getIcon());
      } else {
         var1 = null;
      }

      return var2.setIcon(var1).setUri(var0.getUri()).setKey(var0.getKey()).setBot(var0.isBot()).setImportant(var0.isImportant()).build();
   }

   @NonNull
   public static Person fromBundle(@NonNull Bundle var0) {
      Bundle var1 = var0.getBundle("icon");
      Person.Builder var2 = (new Person.Builder()).setName(var0.getCharSequence("name"));
      IconCompat var3;
      if(var1 != null) {
         var3 = IconCompat.createFromBundle(var1);
      } else {
         var3 = null;
      }

      return var2.setIcon(var3).setUri(var0.getString("uri")).setKey(var0.getString("key")).setBot(var0.getBoolean("isBot")).setImportant(var0.getBoolean("isImportant")).build();
   }

   @Nullable
   public IconCompat getIcon() {
      return this.mIcon;
   }

   @Nullable
   public String getKey() {
      return this.mKey;
   }

   @Nullable
   public CharSequence getName() {
      return this.mName;
   }

   @Nullable
   public String getUri() {
      return this.mUri;
   }

   public boolean isBot() {
      return this.mIsBot;
   }

   public boolean isImportant() {
      return this.mIsImportant;
   }

   @NonNull
   @RequiresApi(28)
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public android.app.Person toAndroidPerson() {
      android.app.Person.Builder var2 = (new android.app.Person.Builder()).setName(this.getName());
      Icon var1;
      if(this.getIcon() != null) {
         var1 = this.getIcon().toIcon();
      } else {
         var1 = null;
      }

      return var2.setIcon(var1).setUri(this.getUri()).setKey(this.getKey()).setBot(this.isBot()).setImportant(this.isImportant()).build();
   }

   @NonNull
   public Person.Builder toBuilder() {
      return new Person.Builder(this);
   }

   @NonNull
   public Bundle toBundle() {
      Bundle var2 = new Bundle();
      var2.putCharSequence("name", this.mName);
      Bundle var1;
      if(this.mIcon != null) {
         var1 = this.mIcon.toBundle();
      } else {
         var1 = null;
      }

      var2.putBundle("icon", var1);
      var2.putString("uri", this.mUri);
      var2.putString("key", this.mKey);
      var2.putBoolean("isBot", this.mIsBot);
      var2.putBoolean("isImportant", this.mIsImportant);
      return var2;
   }

   public static class Builder {

      @Nullable
      IconCompat mIcon;
      boolean mIsBot;
      boolean mIsImportant;
      @Nullable
      String mKey;
      @Nullable
      CharSequence mName;
      @Nullable
      String mUri;


      public Builder() {}

      Builder(Person var1) {
         this.mName = var1.mName;
         this.mIcon = var1.mIcon;
         this.mUri = var1.mUri;
         this.mKey = var1.mKey;
         this.mIsBot = var1.mIsBot;
         this.mIsImportant = var1.mIsImportant;
      }

      @NonNull
      public Person build() {
         return new Person(this);
      }

      @NonNull
      public Person.Builder setBot(boolean var1) {
         this.mIsBot = var1;
         return this;
      }

      @NonNull
      public Person.Builder setIcon(@Nullable IconCompat var1) {
         this.mIcon = var1;
         return this;
      }

      @NonNull
      public Person.Builder setImportant(boolean var1) {
         this.mIsImportant = var1;
         return this;
      }

      @NonNull
      public Person.Builder setKey(@Nullable String var1) {
         this.mKey = var1;
         return this;
      }

      @NonNull
      public Person.Builder setName(@Nullable CharSequence var1) {
         this.mName = var1;
         return this;
      }

      @NonNull
      public Person.Builder setUri(@Nullable String var1) {
         this.mUri = var1;
         return this;
      }
   }
}
