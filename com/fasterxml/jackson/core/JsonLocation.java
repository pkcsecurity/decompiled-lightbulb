package com.fasterxml.jackson.core;

import java.io.Serializable;

public class JsonLocation implements Serializable {

   public static final JsonLocation NA = new JsonLocation("N/A", -1L, -1L, -1, -1);
   private static final long serialVersionUID = 1L;
   final int _columnNr;
   final int _lineNr;
   final transient Object _sourceRef;
   final long _totalBytes;
   final long _totalChars;


   public JsonLocation(Object var1, long var2, int var4, int var5) {
      this(var1, -1L, var2, var4, var5);
   }

   public JsonLocation(Object var1, long var2, long var4, int var6, int var7) {
      this._sourceRef = var1;
      this._totalBytes = var2;
      this._totalChars = var4;
      this._lineNr = var6;
      this._columnNr = var7;
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(!(var1 instanceof JsonLocation)) {
         return false;
      } else {
         JsonLocation var2 = (JsonLocation)var1;
         if(this._sourceRef == null) {
            if(var2._sourceRef != null) {
               return false;
            }
         } else if(!this._sourceRef.equals(var2._sourceRef)) {
            return false;
         }

         return this._lineNr == var2._lineNr && this._columnNr == var2._columnNr && this._totalChars == var2._totalChars && this.getByteOffset() == var2.getByteOffset();
      }
   }

   public long getByteOffset() {
      return this._totalBytes;
   }

   public long getCharOffset() {
      return this._totalChars;
   }

   public int getColumnNr() {
      return this._columnNr;
   }

   public int getLineNr() {
      return this._lineNr;
   }

   public Object getSourceRef() {
      return this._sourceRef;
   }

   public int hashCode() {
      int var1;
      if(this._sourceRef == null) {
         var1 = 1;
      } else {
         var1 = this._sourceRef.hashCode();
      }

      return ((var1 ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(80);
      var1.append("[Source: ");
      if(this._sourceRef == null) {
         var1.append("UNKNOWN");
      } else {
         var1.append(this._sourceRef.toString());
      }

      var1.append("; line: ");
      var1.append(this._lineNr);
      var1.append(", column: ");
      var1.append(this._columnNr);
      var1.append(']');
      return var1.toString();
   }
}
