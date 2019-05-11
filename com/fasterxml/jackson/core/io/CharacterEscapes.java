package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import java.io.Serializable;

public abstract class CharacterEscapes implements Serializable {

   public static final int ESCAPE_CUSTOM = -2;
   public static final int ESCAPE_NONE = 0;
   public static final int ESCAPE_STANDARD = -1;
   private static final long serialVersionUID = 1L;


   public static int[] standardAsciiEscapesForJSON() {
      int[] var0 = CharTypes.get7BitOutputEscapes();
      int[] var1 = new int[var0.length];
      System.arraycopy(var0, 0, var1, 0, var0.length);
      return var1;
   }

   public abstract int[] getEscapeCodesForAscii();

   public abstract SerializableString getEscapeSequence(int var1);
}
