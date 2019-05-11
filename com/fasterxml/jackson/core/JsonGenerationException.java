package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonGenerationException extends JsonProcessingException {

   private static final long serialVersionUID = 123L;


   public JsonGenerationException(String var1) {
      super(var1, (JsonLocation)null);
   }

   public JsonGenerationException(String var1, Throwable var2) {
      super(var1, (JsonLocation)null, var2);
   }

   public JsonGenerationException(Throwable var1) {
      super(var1);
   }
}
