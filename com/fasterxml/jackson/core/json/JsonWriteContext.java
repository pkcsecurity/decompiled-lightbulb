package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonStreamContext;

public class JsonWriteContext extends JsonStreamContext {

   public static final int STATUS_EXPECT_NAME = 5;
   public static final int STATUS_EXPECT_VALUE = 4;
   public static final int STATUS_OK_AFTER_COLON = 2;
   public static final int STATUS_OK_AFTER_COMMA = 1;
   public static final int STATUS_OK_AFTER_SPACE = 3;
   public static final int STATUS_OK_AS_IS = 0;
   protected JsonWriteContext _child = null;
   protected String _currentName;
   protected final JsonWriteContext _parent;


   protected JsonWriteContext(int var1, JsonWriteContext var2) {
      this._type = var1;
      this._parent = var2;
      this._index = -1;
   }

   public static JsonWriteContext createRootContext() {
      return new JsonWriteContext(0, (JsonWriteContext)null);
   }

   private JsonWriteContext reset(int var1) {
      this._type = var1;
      this._index = -1;
      this._currentName = null;
      return this;
   }

   protected final void appendDesc(StringBuilder var1) {
      if(this._type == 2) {
         var1.append('{');
         if(this._currentName != null) {
            var1.append('\"');
            var1.append(this._currentName);
            var1.append('\"');
         } else {
            var1.append('?');
         }

         var1.append('}');
      } else if(this._type == 1) {
         var1.append('[');
         var1.append(this.getCurrentIndex());
         var1.append(']');
      } else {
         var1.append("/");
      }
   }

   public final JsonWriteContext createChildArrayContext() {
      JsonWriteContext var1 = this._child;
      if(var1 == null) {
         var1 = new JsonWriteContext(1, this);
         this._child = var1;
         return var1;
      } else {
         return var1.reset(1);
      }
   }

   public final JsonWriteContext createChildObjectContext() {
      JsonWriteContext var1 = this._child;
      if(var1 == null) {
         var1 = new JsonWriteContext(2, this);
         this._child = var1;
         return var1;
      } else {
         return var1.reset(2);
      }
   }

   public final String getCurrentName() {
      return this._currentName;
   }

   public final JsonWriteContext getParent() {
      return this._parent;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder(64);
      this.appendDesc(var1);
      return var1.toString();
   }

   public final int writeFieldName(String var1) {
      if(this._type == 2) {
         if(this._currentName != null) {
            return 4;
         } else {
            this._currentName = var1;
            return this._index < 0?0:1;
         }
      } else {
         return 4;
      }
   }

   public final int writeValue() {
      if(this._type == 2) {
         if(this._currentName == null) {
            return 5;
         } else {
            this._currentName = null;
            ++this._index;
            return 2;
         }
      } else if(this._type == 1) {
         int var1 = this._index++;
         return var1 < 0?0:1;
      } else {
         ++this._index;
         return this._index == 0?0:3;
      }
   }
}
