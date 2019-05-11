package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import java.util.Iterator;

public interface TreeNode {

   JsonToken asToken();

   Iterator<String> fieldNames();

   TreeNode get(int var1);

   TreeNode get(String var1);

   boolean isArray();

   boolean isContainerNode();

   boolean isMissingNode();

   boolean isObject();

   boolean isValueNode();

   JsonParser.NumberType numberType();

   TreeNode path(int var1);

   TreeNode path(String var1);

   int size();

   JsonParser traverse();

   JsonParser traverse(ObjectCodec var1);
}
