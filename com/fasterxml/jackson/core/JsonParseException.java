package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonParseException extends JsonProcessingException {

   static final long serialVersionUID = 123L;


   public JsonParseException(String var1, JsonLocation var2) {
      super(var1, var2);
   }

   public JsonParseException(String var1, JsonLocation var2, Throwable var3) {
      super(var1, var2, var3);
   }
}
