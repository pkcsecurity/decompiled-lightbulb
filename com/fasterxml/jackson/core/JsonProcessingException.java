package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import java.io.IOException;

public class JsonProcessingException extends IOException {

   static final long serialVersionUID = 123L;
   protected JsonLocation _location;


   protected JsonProcessingException(String var1) {
      super(var1);
   }

   protected JsonProcessingException(String var1, JsonLocation var2) {
      this(var1, var2, (Throwable)null);
   }

   protected JsonProcessingException(String var1, JsonLocation var2, Throwable var3) {
      super(var1);
      if(var3 != null) {
         this.initCause(var3);
      }

      this._location = var2;
   }

   protected JsonProcessingException(String var1, Throwable var2) {
      this(var1, (JsonLocation)null, var2);
   }

   protected JsonProcessingException(Throwable var1) {
      this((String)null, (JsonLocation)null, var1);
   }

   public JsonLocation getLocation() {
      return this._location;
   }

   public String getMessage() {
      String var2 = super.getMessage();
      String var1 = var2;
      if(var2 == null) {
         var1 = "N/A";
      }

      JsonLocation var3 = this.getLocation();
      String var4 = this.getMessageSuffix();
      if(var3 == null) {
         var2 = var1;
         if(var4 == null) {
            return var2;
         }
      }

      StringBuilder var5 = new StringBuilder(100);
      var5.append(var1);
      if(var4 != null) {
         var5.append(var4);
      }

      if(var3 != null) {
         var5.append('\n');
         var5.append(" at ");
         var5.append(var3.toString());
      }

      var2 = var5.toString();
      return var2;
   }

   protected String getMessageSuffix() {
      return null;
   }

   public String getOriginalMessage() {
      return super.getMessage();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getClass().getName());
      var1.append(": ");
      var1.append(this.getMessage());
      return var1.toString();
   }
}
