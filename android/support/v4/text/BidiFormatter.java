package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.text.TextUtilsCompat;
import android.text.SpannableStringBuilder;
import java.util.Locale;

public final class BidiFormatter {

   private static final int DEFAULT_FLAGS = 2;
   static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
   static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
   static final TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
   private static final int DIR_LTR = -1;
   private static final int DIR_RTL = 1;
   private static final int DIR_UNKNOWN = 0;
   private static final String EMPTY_STRING = "";
   private static final int FLAG_STEREO_RESET = 2;
   private static final char LRE = '\u202a';
   private static final char LRM = '\u200e';
   private static final String LRM_STRING = Character.toString('\u200e');
   private static final char PDF = '\u202c';
   private static final char RLE = '\u202b';
   private static final char RLM = '\u200f';
   private static final String RLM_STRING = Character.toString('\u200f');
   private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
   private final int mFlags;
   private final boolean mIsRtlContext;


   BidiFormatter(boolean var1, int var2, TextDirectionHeuristicCompat var3) {
      this.mIsRtlContext = var1;
      this.mFlags = var2;
      this.mDefaultTextDirectionHeuristicCompat = var3;
   }

   private static int getEntryDir(CharSequence var0) {
      return (new BidiFormatter.DirectionalityEstimator(var0, false)).getEntryDir();
   }

   private static int getExitDir(CharSequence var0) {
      return (new BidiFormatter.DirectionalityEstimator(var0, false)).getExitDir();
   }

   public static BidiFormatter getInstance() {
      return (new BidiFormatter.Builder()).build();
   }

   public static BidiFormatter getInstance(Locale var0) {
      return (new BidiFormatter.Builder(var0)).build();
   }

   public static BidiFormatter getInstance(boolean var0) {
      return (new BidiFormatter.Builder(var0)).build();
   }

   static boolean isRtlLocale(Locale var0) {
      return TextUtilsCompat.getLayoutDirectionFromLocale(var0) == 1;
   }

   private String markAfter(CharSequence var1, TextDirectionHeuristicCompat var2) {
      boolean var3 = var2.isRtl(var1, 0, var1.length());
      return !this.mIsRtlContext && (var3 || getExitDir(var1) == 1)?LRM_STRING:(this.mIsRtlContext && (!var3 || getExitDir(var1) == -1)?RLM_STRING:"");
   }

   private String markBefore(CharSequence var1, TextDirectionHeuristicCompat var2) {
      boolean var3 = var2.isRtl(var1, 0, var1.length());
      return !this.mIsRtlContext && (var3 || getEntryDir(var1) == 1)?LRM_STRING:(this.mIsRtlContext && (!var3 || getEntryDir(var1) == -1)?RLM_STRING:"");
   }

   public boolean getStereoReset() {
      return (this.mFlags & 2) != 0;
   }

   public boolean isRtl(CharSequence var1) {
      return this.mDefaultTextDirectionHeuristicCompat.isRtl(var1, 0, var1.length());
   }

   public boolean isRtl(String var1) {
      return this.isRtl((CharSequence)var1);
   }

   public boolean isRtlContext() {
      return this.mIsRtlContext;
   }

   public CharSequence unicodeWrap(CharSequence var1) {
      return this.unicodeWrap(var1, this.mDefaultTextDirectionHeuristicCompat, true);
   }

   public CharSequence unicodeWrap(CharSequence var1, TextDirectionHeuristicCompat var2) {
      return this.unicodeWrap(var1, var2, true);
   }

   public CharSequence unicodeWrap(CharSequence var1, TextDirectionHeuristicCompat var2, boolean var3) {
      if(var1 == null) {
         return null;
      } else {
         boolean var5 = var2.isRtl(var1, 0, var1.length());
         SpannableStringBuilder var6 = new SpannableStringBuilder();
         if(this.getStereoReset() && var3) {
            if(var5) {
               var2 = TextDirectionHeuristicsCompat.RTL;
            } else {
               var2 = TextDirectionHeuristicsCompat.LTR;
            }

            var6.append(this.markBefore(var1, var2));
         }

         if(var5 != this.mIsRtlContext) {
            char var4;
            if(var5) {
               var4 = 8235;
            } else {
               var4 = 8234;
            }

            var6.append(var4);
            var6.append(var1);
            var6.append('\u202c');
         } else {
            var6.append(var1);
         }

         if(var3) {
            if(var5) {
               var2 = TextDirectionHeuristicsCompat.RTL;
            } else {
               var2 = TextDirectionHeuristicsCompat.LTR;
            }

            var6.append(this.markAfter(var1, var2));
         }

         return var6;
      }
   }

   public CharSequence unicodeWrap(CharSequence var1, boolean var2) {
      return this.unicodeWrap(var1, this.mDefaultTextDirectionHeuristicCompat, var2);
   }

   public String unicodeWrap(String var1) {
      return this.unicodeWrap(var1, this.mDefaultTextDirectionHeuristicCompat, true);
   }

   public String unicodeWrap(String var1, TextDirectionHeuristicCompat var2) {
      return this.unicodeWrap(var1, var2, true);
   }

   public String unicodeWrap(String var1, TextDirectionHeuristicCompat var2, boolean var3) {
      return var1 == null?null:this.unicodeWrap((CharSequence)var1, var2, var3).toString();
   }

   public String unicodeWrap(String var1, boolean var2) {
      return this.unicodeWrap(var1, this.mDefaultTextDirectionHeuristicCompat, var2);
   }

   static class DirectionalityEstimator {

      private static final byte[] DIR_TYPE_CACHE = new byte[1792];
      private static final int DIR_TYPE_CACHE_SIZE = 1792;
      private int charIndex;
      private final boolean isHtml;
      private char lastChar;
      private final int length;
      private final CharSequence text;


      static {
         for(int var0 = 0; var0 < 1792; ++var0) {
            DIR_TYPE_CACHE[var0] = Character.getDirectionality(var0);
         }

      }

      DirectionalityEstimator(CharSequence var1, boolean var2) {
         this.text = var1;
         this.isHtml = var2;
         this.length = var1.length();
      }

      private static byte getCachedDirectionality(char var0) {
         return var0 < 1792?DIR_TYPE_CACHE[var0]:Character.getDirectionality(var0);
      }

      private byte skipEntityBackward() {
         int var1 = this.charIndex;

         while(this.charIndex > 0) {
            CharSequence var3 = this.text;
            int var2 = this.charIndex - 1;
            this.charIndex = var2;
            this.lastChar = var3.charAt(var2);
            if(this.lastChar == 38) {
               return (byte)12;
            }

            if(this.lastChar == 59) {
               break;
            }
         }

         this.charIndex = var1;
         this.lastChar = 59;
         return (byte)13;
      }

      private byte skipEntityForward() {
         while(true) {
            if(this.charIndex < this.length) {
               CharSequence var3 = this.text;
               int var2 = this.charIndex;
               this.charIndex = var2 + 1;
               char var1 = var3.charAt(var2);
               this.lastChar = var1;
               if(var1 != 59) {
                  continue;
               }
            }

            return (byte)12;
         }
      }

      private byte skipTagBackward() {
         int var2 = this.charIndex;

         while(this.charIndex > 0) {
            CharSequence var5 = this.text;
            int var3 = this.charIndex - 1;
            this.charIndex = var3;
            this.lastChar = var5.charAt(var3);
            if(this.lastChar == 60) {
               return (byte)12;
            }

            if(this.lastChar == 62) {
               break;
            }

            if(this.lastChar == 34 || this.lastChar == 39) {
               char var6 = this.lastChar;

               while(this.charIndex > 0) {
                  var5 = this.text;
                  int var4 = this.charIndex - 1;
                  this.charIndex = var4;
                  char var1 = var5.charAt(var4);
                  this.lastChar = var1;
                  if(var1 == var6) {
                     break;
                  }
               }
            }
         }

         this.charIndex = var2;
         this.lastChar = 62;
         return (byte)13;
      }

      private byte skipTagForward() {
         int var2 = this.charIndex;

         while(this.charIndex < this.length) {
            CharSequence var5 = this.text;
            int var3 = this.charIndex;
            this.charIndex = var3 + 1;
            this.lastChar = var5.charAt(var3);
            if(this.lastChar == 62) {
               return (byte)12;
            }

            if(this.lastChar == 34 || this.lastChar == 39) {
               char var6 = this.lastChar;

               while(this.charIndex < this.length) {
                  var5 = this.text;
                  int var4 = this.charIndex;
                  this.charIndex = var4 + 1;
                  char var1 = var5.charAt(var4);
                  this.lastChar = var1;
                  if(var1 == var6) {
                     break;
                  }
               }
            }
         }

         this.charIndex = var2;
         this.lastChar = 60;
         return (byte)13;
      }

      byte dirTypeBackward() {
         this.lastChar = this.text.charAt(this.charIndex - 1);
         if(Character.isLowSurrogate(this.lastChar)) {
            int var3 = Character.codePointBefore(this.text, this.charIndex);
            this.charIndex -= Character.charCount(var3);
            return Character.getDirectionality(var3);
         } else {
            --this.charIndex;
            byte var2 = getCachedDirectionality(this.lastChar);
            byte var1 = var2;
            if(this.isHtml) {
               if(this.lastChar == 62) {
                  return this.skipTagBackward();
               }

               var1 = var2;
               if(this.lastChar == 59) {
                  var1 = this.skipEntityBackward();
               }
            }

            return var1;
         }
      }

      byte dirTypeForward() {
         this.lastChar = this.text.charAt(this.charIndex);
         if(Character.isHighSurrogate(this.lastChar)) {
            int var3 = Character.codePointAt(this.text, this.charIndex);
            this.charIndex += Character.charCount(var3);
            return Character.getDirectionality(var3);
         } else {
            ++this.charIndex;
            byte var2 = getCachedDirectionality(this.lastChar);
            byte var1 = var2;
            if(this.isHtml) {
               if(this.lastChar == 60) {
                  return this.skipTagForward();
               }

               var1 = var2;
               if(this.lastChar == 38) {
                  var1 = this.skipEntityForward();
               }
            }

            return var1;
         }
      }

      int getEntryDir() {
         this.charIndex = 0;
         int var3 = 0;
         byte var2 = 0;
         int var1 = 0;

         while(this.charIndex < this.length && var3 == 0) {
            byte var4 = this.dirTypeForward();
            if(var4 != 9) {
               switch(var4) {
               case 0:
                  if(var1 == 0) {
                     return -1;
                  }
                  break;
               case 1:
               case 2:
                  if(var1 == 0) {
                     return 1;
                  }
                  break;
               default:
                  switch(var4) {
                  case 14:
                  case 15:
                     ++var1;
                     var2 = -1;
                     continue;
                  case 16:
                  case 17:
                     ++var1;
                     var2 = 1;
                     continue;
                  case 18:
                     --var1;
                     var2 = 0;
                     continue;
                  }
               }

               var3 = var1;
            }
         }

         if(var3 == 0) {
            return 0;
         } else if(var2 != 0) {
            return var2;
         } else {
            while(this.charIndex > 0) {
               switch(this.dirTypeBackward()) {
               case 14:
               case 15:
                  if(var3 == var1) {
                     return -1;
                  }

                  --var1;
                  break;
               case 16:
               case 17:
                  if(var3 == var1) {
                     return 1;
                  }

                  --var1;
                  break;
               case 18:
                  ++var1;
               }
            }

            return 0;
         }
      }

      int getExitDir() {
         this.charIndex = this.length;
         int var2 = 0;
         int var1 = 0;

         while(this.charIndex > 0) {
            byte var3 = this.dirTypeBackward();
            if(var3 != 9) {
               switch(var3) {
               case 0:
                  if(var1 == 0) {
                     return -1;
                  }

                  if(var2 != 0) {
                     continue;
                  }
                  break;
               case 1:
               case 2:
                  if(var1 == 0) {
                     return 1;
                  }

                  if(var2 != 0) {
                     continue;
                  }
                  break;
               default:
                  switch(var3) {
                  case 14:
                  case 15:
                     if(var2 == var1) {
                        return -1;
                     }

                     --var1;
                     continue;
                  case 16:
                  case 17:
                     if(var2 == var1) {
                        return 1;
                     }

                     --var1;
                     continue;
                  case 18:
                     ++var1;
                     continue;
                  default:
                     if(var2 != 0) {
                        continue;
                     }
                  }
               }

               var2 = var1;
            }
         }

         return 0;
      }
   }

   public static final class Builder {

      private int mFlags;
      private boolean mIsRtlContext;
      private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;


      public Builder() {
         this.initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
      }

      public Builder(Locale var1) {
         this.initialize(BidiFormatter.isRtlLocale(var1));
      }

      public Builder(boolean var1) {
         this.initialize(var1);
      }

      private static BidiFormatter getDefaultInstanceFromContext(boolean var0) {
         return var0?BidiFormatter.DEFAULT_RTL_INSTANCE:BidiFormatter.DEFAULT_LTR_INSTANCE;
      }

      private void initialize(boolean var1) {
         this.mIsRtlContext = var1;
         this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
         this.mFlags = 2;
      }

      public BidiFormatter build() {
         return this.mFlags == 2 && this.mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC?getDefaultInstanceFromContext(this.mIsRtlContext):new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
      }

      public BidiFormatter.Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat var1) {
         this.mTextDirectionHeuristicCompat = var1;
         return this;
      }

      public BidiFormatter.Builder stereoReset(boolean var1) {
         if(var1) {
            this.mFlags |= 2;
            return this;
         } else {
            this.mFlags &= -3;
            return this;
         }
      }
   }
}
