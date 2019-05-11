package android.support.v4.os;

import android.os.Build.VERSION;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.Size;
import android.support.v4.os.LocaleHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
final class LocaleListHelper {

   private static final Locale EN_LATN = LocaleHelper.forLanguageTag("en-Latn");
   private static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
   private static final Locale LOCALE_EN_XA = new Locale("en", "XA");
   private static final int NUM_PSEUDO_LOCALES = 2;
   private static final String STRING_AR_XB = "ar-XB";
   private static final String STRING_EN_XA = "en-XA";
   @GuardedBy("sLock")
   private static LocaleListHelper sDefaultAdjustedLocaleList;
   @GuardedBy("sLock")
   private static LocaleListHelper sDefaultLocaleList;
   private static final Locale[] sEmptyList = new Locale[0];
   private static final LocaleListHelper sEmptyLocaleList = new LocaleListHelper(new Locale[0]);
   @GuardedBy("sLock")
   private static Locale sLastDefaultLocale;
   @GuardedBy("sLock")
   private static LocaleListHelper sLastExplicitlySetLocaleList;
   private static final Object sLock = new Object();
   private final Locale[] mList;
   @NonNull
   private final String mStringRepresentation;


   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   LocaleListHelper(@NonNull Locale var1, LocaleListHelper var2) {
      if(var1 == null) {
         throw new NullPointerException("topLocale is null");
      } else {
         byte var6 = 0;
         int var4;
         if(var2 == null) {
            var4 = 0;
         } else {
            var4 = var2.mList.length;
         }

         int var3 = 0;

         while(true) {
            if(var3 >= var4) {
               var3 = -1;
               break;
            }

            if(var1.equals(var2.mList[var3])) {
               break;
            }

            ++var3;
         }

         byte var5;
         if(var3 == -1) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         int var8 = var5 + var4;
         Locale[] var9 = new Locale[var8];
         var9[0] = (Locale)var1.clone();
         int var11;
         if(var3 == -1) {
            for(var3 = 0; var3 < var4; var3 = var11) {
               var11 = var3 + 1;
               var9[var11] = (Locale)var2.mList[var3].clone();
            }
         } else {
            int var7;
            for(var11 = 0; var11 < var3; var11 = var7) {
               var7 = var11 + 1;
               var9[var7] = (Locale)var2.mList[var11].clone();
            }

            ++var3;

            while(var3 < var4) {
               var9[var3] = (Locale)var2.mList[var3].clone();
               ++var3;
            }
         }

         StringBuilder var10 = new StringBuilder();

         for(var3 = var6; var3 < var8; ++var3) {
            var10.append(LocaleHelper.toLanguageTag(var9[var3]));
            if(var3 < var8 - 1) {
               var10.append(',');
            }
         }

         this.mList = var9;
         this.mStringRepresentation = var10.toString();
      }
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   LocaleListHelper(@NonNull Locale ... var1) {
      if(var1.length == 0) {
         this.mList = sEmptyList;
         this.mStringRepresentation = "";
      } else {
         Locale[] var3 = new Locale[var1.length];
         HashSet var4 = new HashSet();
         StringBuilder var5 = new StringBuilder();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            Locale var6 = var1[var2];
            StringBuilder var7;
            if(var6 == null) {
               var7 = new StringBuilder();
               var7.append("list[");
               var7.append(var2);
               var7.append("] is null");
               throw new NullPointerException(var7.toString());
            }

            if(var4.contains(var6)) {
               var7 = new StringBuilder();
               var7.append("list[");
               var7.append(var2);
               var7.append("] is a repetition");
               throw new IllegalArgumentException(var7.toString());
            }

            var6 = (Locale)var6.clone();
            var3[var2] = var6;
            var5.append(LocaleHelper.toLanguageTag(var6));
            if(var2 < var1.length - 1) {
               var5.append(',');
            }

            var4.add(var6);
         }

         this.mList = var3;
         this.mStringRepresentation = var5.toString();
      }
   }

   private Locale computeFirstMatch(Collection<String> var1, boolean var2) {
      int var3 = this.computeFirstMatchIndex(var1, var2);
      return var3 == -1?null:this.mList[var3];
   }

   private int computeFirstMatchIndex(Collection<String> var1, boolean var2) {
      if(this.mList.length == 1) {
         return 0;
      } else if(this.mList.length == 0) {
         return -1;
      } else {
         int var3;
         label38: {
            if(var2) {
               var3 = this.findFirstMatchIndex(EN_LATN);
               if(var3 == 0) {
                  return 0;
               }

               if(var3 < Integer.MAX_VALUE) {
                  break label38;
               }
            }

            var3 = Integer.MAX_VALUE;
         }

         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            int var4 = this.findFirstMatchIndex(LocaleHelper.forLanguageTag((String)var5.next()));
            if(var4 == 0) {
               return 0;
            }

            if(var4 < var3) {
               var3 = var4;
            }
         }

         if(var3 == Integer.MAX_VALUE) {
            return 0;
         } else {
            return var3;
         }
      }
   }

   private int findFirstMatchIndex(Locale var1) {
      for(int var2 = 0; var2 < this.mList.length; ++var2) {
         if(matchScore(var1, this.mList[var2]) > 0) {
            return var2;
         }
      }

      return Integer.MAX_VALUE;
   }

   @NonNull
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   static LocaleListHelper forLanguageTags(@Nullable String var0) {
      if(var0 != null && !var0.isEmpty()) {
         String[] var3 = var0.split(",", -1);
         Locale[] var2 = new Locale[var3.length];

         for(int var1 = 0; var1 < var2.length; ++var1) {
            var2[var1] = LocaleHelper.forLanguageTag(var3[var1]);
         }

         return new LocaleListHelper(var2);
      } else {
         return getEmptyLocaleList();
      }
   }

   @NonNull
   @Size(
      min = 1L
   )
   static LocaleListHelper getAdjustedDefault() {
      // $FF: Couldn't be decompiled
   }

   @NonNull
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   @Size(
      min = 1L
   )
   static LocaleListHelper getDefault() {
      // $FF: Couldn't be decompiled
   }

   @NonNull
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   static LocaleListHelper getEmptyLocaleList() {
      return sEmptyLocaleList;
   }

   private static String getLikelyScript(Locale var0) {
      if(VERSION.SDK_INT >= 21) {
         String var1 = var0.getScript();
         return !var1.isEmpty()?var1:"";
      } else {
         return "";
      }
   }

   private static boolean isPseudoLocale(String var0) {
      return "en-XA".equals(var0) || "ar-XB".equals(var0);
   }

   private static boolean isPseudoLocale(Locale var0) {
      return LOCALE_EN_XA.equals(var0) || LOCALE_AR_XB.equals(var0);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   static boolean isPseudoLocalesOnly(@Nullable String[] var0) {
      if(var0 == null) {
         return true;
      } else if(var0.length > 3) {
         return false;
      } else {
         int var2 = var0.length;

         for(int var1 = 0; var1 < var2; ++var1) {
            String var3 = var0[var1];
            if(!var3.isEmpty() && !isPseudoLocale(var3)) {
               return false;
            }
         }

         return true;
      }
   }

   @IntRange(
      from = 0L,
      to = 1L
   )
   private static int matchScore(Locale var0, Locale var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   static void setDefault(@NonNull 
      @Size(
         min = 1L
      ) LocaleListHelper var0) {
      setDefault(var0, 0);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   static void setDefault(@NonNull 
      @Size(
         min = 1L
      ) LocaleListHelper param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof LocaleListHelper)) {
         return false;
      } else {
         Locale[] var3 = ((LocaleListHelper)var1).mList;
         if(this.mList.length != var3.length) {
            return false;
         } else {
            for(int var2 = 0; var2 < this.mList.length; ++var2) {
               if(!this.mList[var2].equals(var3[var2])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   Locale get(int var1) {
      return var1 >= 0 && var1 < this.mList.length?this.mList[var1]:null;
   }

   @Nullable
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   Locale getFirstMatch(String[] var1) {
      return this.computeFirstMatch(Arrays.asList(var1), false);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int getFirstMatchIndex(String[] var1) {
      return this.computeFirstMatchIndex(Arrays.asList(var1), false);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int getFirstMatchIndexWithEnglishSupported(Collection<String> var1) {
      return this.computeFirstMatchIndex(var1, true);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int getFirstMatchIndexWithEnglishSupported(String[] var1) {
      return this.getFirstMatchIndexWithEnglishSupported((Collection)Arrays.asList(var1));
   }

   @Nullable
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   Locale getFirstMatchWithEnglishSupported(String[] var1) {
      return this.computeFirstMatch(Arrays.asList(var1), true);
   }

   public int hashCode() {
      int var2 = 1;

      for(int var1 = 0; var1 < this.mList.length; ++var1) {
         var2 = var2 * 31 + this.mList[var1].hashCode();
      }

      return var2;
   }

   @IntRange(
      from = -1L
   )
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int indexOf(Locale var1) {
      for(int var2 = 0; var2 < this.mList.length; ++var2) {
         if(this.mList[var2].equals(var1)) {
            return var2;
         }
      }

      return -1;
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   boolean isEmpty() {
      return this.mList.length == 0;
   }

   @IntRange(
      from = 0L
   )
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int size() {
      return this.mList.length;
   }

   @NonNull
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   String toLanguageTags() {
      return this.mStringRepresentation;
   }

   public String toString() {
      StringBuilder var2 = new StringBuilder();
      var2.append("[");

      for(int var1 = 0; var1 < this.mList.length; ++var1) {
         var2.append(this.mList[var1]);
         if(var1 < this.mList.length - 1) {
            var2.append(',');
         }
      }

      var2.append("]");
      return var2.toString();
   }
}
