package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.io.CharTypes;

public final class JsonReadContext extends JsonStreamContext {

   protected JsonReadContext _child = null;
   protected int _columnNr;
   protected String _currentName;
   protected int _lineNr;
   protected final JsonReadContext _parent;


   public JsonReadContext(JsonReadContext var1, int var2, int var3, int var4) {
      this._type = var2;
      this._parent = var1;
      this._lineNr = var3;
      this._columnNr = var4;
      this._index = -1;
   }

   public static JsonReadContext createRootContext() {
      return new JsonReadContext((JsonReadContext)null, 0, 1, 0);
   }

   public static JsonReadContext createRootContext(int var0, int var1) {
      return new JsonReadContext((JsonReadContext)null, 0, var0, var1);
   }

   public JsonReadContext createChildArrayContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if(var3 == null) {
         var3 = new JsonReadContext(this, 1, var1, var2);
         this._child = var3;
         return var3;
      } else {
         var3.reset(1, var1, var2);
         return var3;
      }
   }

   public JsonReadContext createChildObjectContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if(var3 == null) {
         var3 = new JsonReadContext(this, 2, var1, var2);
         this._child = var3;
         return var3;
      } else {
         var3.reset(2, var1, var2);
         return var3;
      }
   }

   public boolean expectComma() {
      int var1 = this._index + 1;
      this._index = var1;
      return this._type != 0 && var1 > 0;
   }

   public String getCurrentName() {
      return this._currentName;
   }

   public JsonReadContext getParent() {
      return this._parent;
   }

   public JsonLocation getStartLocation(Object var1) {
      return new JsonLocation(var1, -1L, this._lineNr, this._columnNr);
   }

   protected void reset(int var1, int var2, int var3) {
      this._type = var1;
      this._index = -1;
      this._lineNr = var2;
      this._columnNr = var3;
      this._currentName = null;
   }

   public void setCurrentName(String var1) {
      this._currentName = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(64);
      switch(this._type) {
      case 0:
         var1.append("/");
         break;
      case 1:
         var1.append('[');
         var1.append(this.getCurrentIndex());
         var1.append(']');
         break;
      case 2:
         var1.append('{');
         if(this._currentName != null) {
            var1.append('\"');
            CharTypes.appendQuoted(var1, this._currentName);
            var1.append('\"');
         } else {
            var1.append('?');
         }

         var1.append('}');
      }

      return var1.toString();
   }
}
