package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class SerializedString implements SerializableString, Serializable {

   protected transient String _jdkSerializeValue;
   protected char[] _quotedChars;
   protected byte[] _quotedUTF8Ref;
   protected byte[] _unquotedUTF8Ref;
   protected final String _value;


   public SerializedString(String var1) {
      if(var1 == null) {
         throw new IllegalStateException("Null String illegal for SerializedString");
      } else {
         this._value = var1;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException {
      this._jdkSerializeValue = var1.readUTF();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeUTF(this._value);
   }

   public int appendQuoted(char[] var1, int var2) {
      char[] var5 = this._quotedChars;
      char[] var4 = var5;
      if(var5 == null) {
         var4 = JsonStringEncoder.getInstance().quoteAsString(this._value);
         this._quotedChars = var4;
      }

      int var3 = var4.length;
      if(var2 + var3 > var1.length) {
         return -1;
      } else {
         System.arraycopy(var4, 0, var1, var2, var3);
         return var3;
      }
   }

   public int appendQuotedUTF8(byte[] var1, int var2) {
      byte[] var5 = this._quotedUTF8Ref;
      byte[] var4 = var5;
      if(var5 == null) {
         var4 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var4;
      }

      int var3 = var4.length;
      if(var2 + var3 > var1.length) {
         return -1;
      } else {
         System.arraycopy(var4, 0, var1, var2, var3);
         return var3;
      }
   }

   public int appendUnquoted(char[] var1, int var2) {
      String var4 = this._value;
      int var3 = var4.length();
      if(var2 + var3 > var1.length) {
         return -1;
      } else {
         var4.getChars(0, var3, var1, var2);
         return var3;
      }
   }

   public int appendUnquotedUTF8(byte[] var1, int var2) {
      byte[] var5 = this._unquotedUTF8Ref;
      byte[] var4 = var5;
      if(var5 == null) {
         var4 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var4;
      }

      int var3 = var4.length;
      if(var2 + var3 > var1.length) {
         return -1;
      } else {
         System.arraycopy(var4, 0, var1, var2, var3);
         return var3;
      }
   }

   public final char[] asQuotedChars() {
      char[] var2 = this._quotedChars;
      char[] var1 = var2;
      if(var2 == null) {
         var1 = JsonStringEncoder.getInstance().quoteAsString(this._value);
         this._quotedChars = var1;
      }

      return var1;
   }

   public final byte[] asQuotedUTF8() {
      byte[] var2 = this._quotedUTF8Ref;
      byte[] var1 = var2;
      if(var2 == null) {
         var1 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var1;
      }

      return var1;
   }

   public final byte[] asUnquotedUTF8() {
      byte[] var2 = this._unquotedUTF8Ref;
      byte[] var1 = var2;
      if(var2 == null) {
         var1 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var1;
      }

      return var1;
   }

   public final int charLength() {
      return this._value.length();
   }

   public final boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(var1 != null && var1.getClass() == this.getClass()) {
         SerializedString var2 = (SerializedString)var1;
         return this._value.equals(var2._value);
      } else {
         return false;
      }
   }

   public final String getValue() {
      return this._value;
   }

   public final int hashCode() {
      return this._value.hashCode();
   }

   public int putQuotedUTF8(ByteBuffer var1) {
      byte[] var4 = this._quotedUTF8Ref;
      byte[] var3 = var4;
      if(var4 == null) {
         var3 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var3;
      }

      int var2 = var3.length;
      if(var2 > var1.remaining()) {
         return -1;
      } else {
         var1.put(var3, 0, var2);
         return var2;
      }
   }

   public int putUnquotedUTF8(ByteBuffer var1) {
      byte[] var4 = this._unquotedUTF8Ref;
      byte[] var3 = var4;
      if(var4 == null) {
         var3 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var3;
      }

      int var2 = var3.length;
      if(var2 > var1.remaining()) {
         return -1;
      } else {
         var1.put(var3, 0, var2);
         return var2;
      }
   }

   protected Object readResolve() {
      return new SerializedString(this._jdkSerializeValue);
   }

   public final String toString() {
      return this._value;
   }

   public int writeQuotedUTF8(OutputStream var1) throws IOException {
      byte[] var4 = this._quotedUTF8Ref;
      byte[] var3 = var4;
      if(var4 == null) {
         var3 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var3;
      }

      int var2 = var3.length;
      var1.write(var3, 0, var2);
      return var2;
   }

   public int writeUnquotedUTF8(OutputStream var1) throws IOException {
      byte[] var4 = this._unquotedUTF8Ref;
      byte[] var3 = var4;
      if(var4 == null) {
         var3 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var3;
      }

      int var2 = var3.length;
      var1.write(var3, 0, var2);
      return var2;
   }
}
