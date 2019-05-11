package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Iterator;

public abstract class ObjectCodec {

   public abstract TreeNode createArrayNode();

   public abstract TreeNode createObjectNode();

   public JsonFactory getFactory() {
      return this.getJsonFactory();
   }

   @Deprecated
   public abstract JsonFactory getJsonFactory();

   public abstract <T extends Object & TreeNode> T readTree(JsonParser var1) throws IOException, JsonProcessingException;

   public abstract <T extends Object> T readValue(JsonParser var1, ResolvedType var2) throws IOException, JsonProcessingException;

   public abstract <T extends Object> T readValue(JsonParser var1, TypeReference<?> var2) throws IOException, JsonProcessingException;

   public abstract <T extends Object> T readValue(JsonParser var1, Class<T> var2) throws IOException, JsonProcessingException;

   public abstract <T extends Object> Iterator<T> readValues(JsonParser var1, ResolvedType var2) throws IOException, JsonProcessingException;

   public abstract <T extends Object> Iterator<T> readValues(JsonParser var1, TypeReference<?> var2) throws IOException, JsonProcessingException;

   public abstract <T extends Object> Iterator<T> readValues(JsonParser var1, Class<T> var2) throws IOException, JsonProcessingException;

   public abstract JsonParser treeAsTokens(TreeNode var1);

   public abstract <T extends Object> T treeToValue(TreeNode var1, Class<T> var2) throws JsonProcessingException;

   public abstract void writeValue(JsonGenerator var1, Object var2) throws IOException, JsonProcessingException;
}
