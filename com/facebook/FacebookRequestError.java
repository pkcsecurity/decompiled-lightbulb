package com.facebook;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.FacebookException;
import com.facebook.FacebookServiceException;
import com.facebook.internal.FacebookRequestErrorClassification;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public final class FacebookRequestError implements Parcelable {

   private static final String BODY_KEY = "body";
   private static final String CODE_KEY = "code";
   public static final Creator<FacebookRequestError> CREATOR = new Creator() {
      public FacebookRequestError createFromParcel(Parcel var1) {
         return new FacebookRequestError(var1, null);
      }
      public FacebookRequestError[] newArray(int var1) {
         return new FacebookRequestError[var1];
      }
   };
   private static final String ERROR_CODE_FIELD_KEY = "code";
   private static final String ERROR_CODE_KEY = "error_code";
   private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
   private static final String ERROR_KEY = "error";
   private static final String ERROR_MESSAGE_FIELD_KEY = "message";
   private static final String ERROR_MSG_KEY = "error_msg";
   private static final String ERROR_REASON_KEY = "error_reason";
   private static final String ERROR_SUB_CODE_KEY = "error_subcode";
   private static final String ERROR_TYPE_FIELD_KEY = "type";
   private static final String ERROR_USER_MSG_KEY = "error_user_msg";
   private static final String ERROR_USER_TITLE_KEY = "error_user_title";
   static final FacebookRequestError.Range HTTP_RANGE_SUCCESS = new FacebookRequestError.Range(200, 299, null);
   public static final int INVALID_ERROR_CODE = -1;
   public static final int INVALID_HTTP_STATUS_CODE = -1;
   private final Object batchRequestResult;
   private final FacebookRequestError.Category category;
   private final HttpURLConnection connection;
   private final int errorCode;
   private final String errorMessage;
   private final String errorRecoveryMessage;
   private final String errorType;
   private final String errorUserMessage;
   private final String errorUserTitle;
   private final FacebookException exception;
   private final JSONObject requestResult;
   private final JSONObject requestResultBody;
   private final int requestStatusCode;
   private final int subErrorCode;


   private FacebookRequestError(int var1, int var2, int var3, String var4, String var5, String var6, String var7, boolean var8, JSONObject var9, JSONObject var10, Object var11, HttpURLConnection var12, FacebookException var13) {
      this.requestStatusCode = var1;
      this.errorCode = var2;
      this.subErrorCode = var3;
      this.errorType = var4;
      this.errorMessage = var5;
      this.requestResultBody = var9;
      this.requestResult = var10;
      this.batchRequestResult = var11;
      this.connection = var12;
      this.errorUserTitle = var6;
      this.errorUserMessage = var7;
      boolean var14;
      if(var13 != null) {
         this.exception = var13;
         var14 = true;
      } else {
         this.exception = new FacebookServiceException(this, var5);
         var14 = false;
      }

      FacebookRequestErrorClassification var16 = getErrorClassification();
      FacebookRequestError.Category var15;
      if(var14) {
         var15 = FacebookRequestError.Category.OTHER;
      } else {
         var15 = var16.classify(var2, var3, var8);
      }

      this.category = var15;
      this.errorRecoveryMessage = var16.getRecoveryMessage(this.category);
   }

   public FacebookRequestError(int var1, String var2, String var3) {
      this(-1, var1, -1, var2, var3, (String)null, (String)null, false, (JSONObject)null, (JSONObject)null, (Object)null, (HttpURLConnection)null, (FacebookException)null);
   }

   private FacebookRequestError(Parcel var1) {
      this(var1.readInt(), var1.readInt(), var1.readInt(), var1.readString(), var1.readString(), var1.readString(), var1.readString(), false, (JSONObject)null, (JSONObject)null, (Object)null, (HttpURLConnection)null, (FacebookException)null);
   }

   // $FF: synthetic method
   FacebookRequestError(Parcel var1, Object var2) {
      this(var1);
   }

   FacebookRequestError(HttpURLConnection var1, Exception var2) {
      FacebookException var3;
      if(var2 instanceof FacebookException) {
         var3 = (FacebookException)var2;
      } else {
         var3 = new FacebookException(var2);
      }

      this(-1, -1, -1, (String)null, (String)null, (String)null, (String)null, false, (JSONObject)null, (JSONObject)null, (Object)null, var1, var3);
   }

   static FacebookRequestError checkResponseAndCreateError(JSONObject param0, Object param1, HttpURLConnection param2) {
      // $FF: Couldn't be decompiled
   }

   static FacebookRequestErrorClassification getErrorClassification() {
      // $FF: Couldn't be decompiled
   }

   public int describeContents() {
      return 0;
   }

   public Object getBatchRequestResult() {
      return this.batchRequestResult;
   }

   public FacebookRequestError.Category getCategory() {
      return this.category;
   }

   public HttpURLConnection getConnection() {
      return this.connection;
   }

   public int getErrorCode() {
      return this.errorCode;
   }

   public String getErrorMessage() {
      return this.errorMessage != null?this.errorMessage:this.exception.getLocalizedMessage();
   }

   public String getErrorRecoveryMessage() {
      return this.errorRecoveryMessage;
   }

   public String getErrorType() {
      return this.errorType;
   }

   public String getErrorUserMessage() {
      return this.errorUserMessage;
   }

   public String getErrorUserTitle() {
      return this.errorUserTitle;
   }

   public FacebookException getException() {
      return this.exception;
   }

   public JSONObject getRequestResult() {
      return this.requestResult;
   }

   public JSONObject getRequestResultBody() {
      return this.requestResultBody;
   }

   public int getRequestStatusCode() {
      return this.requestStatusCode;
   }

   public int getSubErrorCode() {
      return this.subErrorCode;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("{HttpStatus: ");
      var1.append(this.requestStatusCode);
      var1.append(", errorCode: ");
      var1.append(this.errorCode);
      var1.append(", subErrorCode: ");
      var1.append(this.subErrorCode);
      var1.append(", errorType: ");
      var1.append(this.errorType);
      var1.append(", errorMessage: ");
      var1.append(this.getErrorMessage());
      var1.append("}");
      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeInt(this.requestStatusCode);
      var1.writeInt(this.errorCode);
      var1.writeInt(this.subErrorCode);
      var1.writeString(this.errorType);
      var1.writeString(this.errorMessage);
      var1.writeString(this.errorUserTitle);
      var1.writeString(this.errorUserMessage);
   }

   static class Range {

      private final int end;
      private final int start;


      private Range(int var1, int var2) {
         this.start = var1;
         this.end = var2;
      }

      // $FF: synthetic method
      Range(int var1, int var2, Object var3) {
         this(var1, var2);
      }

      boolean contains(int var1) {
         return this.start <= var1 && var1 <= this.end;
      }
   }

   public static enum Category {

      // $FF: synthetic field
      private static final FacebookRequestError.Category[] $VALUES = new FacebookRequestError.Category[]{LOGIN_RECOVERABLE, OTHER, TRANSIENT};
      LOGIN_RECOVERABLE("LOGIN_RECOVERABLE", 0),
      OTHER("OTHER", 1),
      TRANSIENT("TRANSIENT", 2);


      private Category(String var1, int var2) {}
   }
}
