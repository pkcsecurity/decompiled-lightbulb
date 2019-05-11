package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.format.DataFormatMatcher;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatDetector {

   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
   protected final JsonFactory[] _detectors;
   protected final int _maxInputLookahead;
   protected final MatchStrength _minimalMatch;
   protected final MatchStrength _optimalMatch;


   public DataFormatDetector(Collection<JsonFactory> var1) {
      this((JsonFactory[])var1.toArray(new JsonFactory[var1.size()]));
   }

   public DataFormatDetector(JsonFactory ... var1) {
      this(var1, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
   }

   private DataFormatDetector(JsonFactory[] var1, MatchStrength var2, MatchStrength var3, int var4) {
      this._detectors = var1;
      this._optimalMatch = var2;
      this._minimalMatch = var3;
      this._maxInputLookahead = var4;
   }

   private DataFormatMatcher _findFormat(InputAccessor.Std var1) throws IOException {
      JsonFactory[] var10 = this._detectors;
      int var3 = var10.length;
      JsonFactory var5 = null;
      int var2 = 0;
      MatchStrength var4 = null;

      JsonFactory var6;
      MatchStrength var7;
      while(true) {
         var6 = var5;
         var7 = var4;
         if(var2 >= var3) {
            break;
         }

         JsonFactory var8 = var10[var2];
         var1.reset();
         MatchStrength var11 = var8.hasFormat(var1);
         JsonFactory var9 = var5;
         var7 = var4;
         if(var11 != null) {
            if(var11.ordinal() < this._minimalMatch.ordinal()) {
               var9 = var5;
               var7 = var4;
            } else if(var5 != null && var4.ordinal() >= var11.ordinal()) {
               var9 = var5;
               var7 = var4;
            } else {
               if(var11.ordinal() >= this._optimalMatch.ordinal()) {
                  var7 = var11;
                  var6 = var8;
                  break;
               }

               var7 = var11;
               var9 = var8;
            }
         }

         ++var2;
         var5 = var9;
         var4 = var7;
      }

      return var1.createMatcher(var6, var7);
   }

   public DataFormatMatcher findFormat(InputStream var1) throws IOException {
      return this._findFormat(new InputAccessor.Std(var1, new byte[this._maxInputLookahead]));
   }

   public DataFormatMatcher findFormat(byte[] var1) throws IOException {
      return this._findFormat(new InputAccessor.Std(var1));
   }

   public DataFormatMatcher findFormat(byte[] var1, int var2, int var3) throws IOException {
      return this._findFormat(new InputAccessor.Std(var1, var2, var3));
   }

   public String toString() {
      StringBuilder var3 = new StringBuilder();
      var3.append('[');
      int var2 = this._detectors.length;
      if(var2 > 0) {
         var3.append(this._detectors[0].getFormatName());

         for(int var1 = 1; var1 < var2; ++var1) {
            var3.append(", ");
            var3.append(this._detectors[var1].getFormatName());
         }
      }

      var3.append(']');
      return var3.toString();
   }

   public DataFormatDetector withMaxInputLookahead(int var1) {
      return var1 == this._maxInputLookahead?this:new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, var1);
   }

   public DataFormatDetector withMinimalMatch(MatchStrength var1) {
      return var1 == this._minimalMatch?this:new DataFormatDetector(this._detectors, this._optimalMatch, var1, this._maxInputLookahead);
   }

   public DataFormatDetector withOptimalMatch(MatchStrength var1) {
      return var1 == this._optimalMatch?this:new DataFormatDetector(this._detectors, var1, this._minimalMatch, this._maxInputLookahead);
   }
}
