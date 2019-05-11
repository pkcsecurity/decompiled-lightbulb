package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.Arrays;
import java.util.List;

public final class GameRequestContent implements ShareModel {

   public static final Creator<GameRequestContent> CREATOR = new Creator() {
      public GameRequestContent createFromParcel(Parcel var1) {
         return new GameRequestContent(var1);
      }
      public GameRequestContent[] newArray(int var1) {
         return new GameRequestContent[var1];
      }
   };
   private final GameRequestContent.ActionType actionType;
   private final String data;
   private final GameRequestContent.Filters filters;
   private final String message;
   private final String objectId;
   private final List<String> recipients;
   private final List<String> suggestions;
   private final String title;


   GameRequestContent(Parcel var1) {
      this.message = var1.readString();
      this.recipients = var1.createStringArrayList();
      this.title = var1.readString();
      this.data = var1.readString();
      this.actionType = (GameRequestContent.ActionType)var1.readSerializable();
      this.objectId = var1.readString();
      this.filters = (GameRequestContent.Filters)var1.readSerializable();
      this.suggestions = var1.createStringArrayList();
      var1.readStringList(this.suggestions);
   }

   private GameRequestContent(GameRequestContent.Builder var1) {
      this.message = var1.message;
      this.recipients = var1.recipients;
      this.title = var1.title;
      this.data = var1.data;
      this.actionType = var1.actionType;
      this.objectId = var1.objectId;
      this.filters = var1.filters;
      this.suggestions = var1.suggestions;
   }

   // $FF: synthetic method
   GameRequestContent(GameRequestContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public GameRequestContent.ActionType getActionType() {
      return this.actionType;
   }

   public String getData() {
      return this.data;
   }

   public GameRequestContent.Filters getFilters() {
      return this.filters;
   }

   public String getMessage() {
      return this.message;
   }

   public String getObjectId() {
      return this.objectId;
   }

   public List<String> getRecipients() {
      return this.recipients;
   }

   public List<String> getSuggestions() {
      return this.suggestions;
   }

   public String getTitle() {
      return this.title;
   }

   public String getTo() {
      return this.getRecipients() != null?TextUtils.join(",", this.getRecipients()):null;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeString(this.message);
      var1.writeStringList(this.recipients);
      var1.writeString(this.title);
      var1.writeString(this.data);
      var1.writeSerializable(this.actionType);
      var1.writeString(this.objectId);
      var1.writeSerializable(this.filters);
      var1.writeStringList(this.suggestions);
   }

   public static enum ActionType {

      // $FF: synthetic field
      private static final GameRequestContent.ActionType[] $VALUES = new GameRequestContent.ActionType[]{SEND, ASKFOR, TURN};
      ASKFOR("ASKFOR", 1),
      SEND("SEND", 0),
      TURN("TURN", 2);


      private ActionType(String var1, int var2) {}
   }

   public static class Builder implements ShareModelBuilder<GameRequestContent, GameRequestContent.Builder> {

      private GameRequestContent.ActionType actionType;
      private String data;
      private GameRequestContent.Filters filters;
      private String message;
      private String objectId;
      private List<String> recipients;
      private List<String> suggestions;
      private String title;


      public GameRequestContent build() {
         return new GameRequestContent(this, null);
      }

      GameRequestContent.Builder readFrom(Parcel var1) {
         return this.readFrom((GameRequestContent)var1.readParcelable(GameRequestContent.class.getClassLoader()));
      }

      public GameRequestContent.Builder readFrom(GameRequestContent var1) {
         return var1 == null?this:this.setMessage(var1.getMessage()).setRecipients(var1.getRecipients()).setTitle(var1.getTitle()).setData(var1.getData()).setActionType(var1.getActionType()).setObjectId(var1.getObjectId()).setFilters(var1.getFilters()).setSuggestions(var1.getSuggestions());
      }

      public GameRequestContent.Builder setActionType(GameRequestContent.ActionType var1) {
         this.actionType = var1;
         return this;
      }

      public GameRequestContent.Builder setData(String var1) {
         this.data = var1;
         return this;
      }

      public GameRequestContent.Builder setFilters(GameRequestContent.Filters var1) {
         this.filters = var1;
         return this;
      }

      public GameRequestContent.Builder setMessage(String var1) {
         this.message = var1;
         return this;
      }

      public GameRequestContent.Builder setObjectId(String var1) {
         this.objectId = var1;
         return this;
      }

      public GameRequestContent.Builder setRecipients(List<String> var1) {
         this.recipients = var1;
         return this;
      }

      public GameRequestContent.Builder setSuggestions(List<String> var1) {
         this.suggestions = var1;
         return this;
      }

      public GameRequestContent.Builder setTitle(String var1) {
         this.title = var1;
         return this;
      }

      public GameRequestContent.Builder setTo(String var1) {
         if(var1 != null) {
            this.recipients = Arrays.asList(var1.split(","));
         }

         return this;
      }
   }

   public static enum Filters {

      // $FF: synthetic field
      private static final GameRequestContent.Filters[] $VALUES = new GameRequestContent.Filters[]{APP_USERS, APP_NON_USERS};
      APP_NON_USERS("APP_NON_USERS", 1),
      APP_USERS("APP_USERS", 0);


      private Filters(String var1, int var2) {}
   }
}
