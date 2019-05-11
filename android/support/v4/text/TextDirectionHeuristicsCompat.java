package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextUtilsCompat;
import java.nio.CharBuffer;
import java.util.Locale;

public final class TextDirectionHeuristicsCompat {

   public static final TextDirectionHeuristicCompat ANYRTL_LTR = new TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal(TextDirectionHeuristicsCompat.AnyStrong.INSTANCE_RTL, false);
   public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR = new TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal(TextDirectionHeuristicsCompat.FirstStrong.INSTANCE, false);
   public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL = new TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal(TextDirectionHeuristicsCompat.FirstStrong.INSTANCE, true);
   public static final TextDirectionHeuristicCompat LOCALE = TextDirectionHeuristicsCompat.TextDirectionHeuristicLocale.INSTANCE;
   public static final TextDirectionHeuristicCompat LTR = new TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal((TextDirectionHeuristicsCompat.TextDirectionAlgorithm)null, false);
   public static final TextDirectionHeuristicCompat RTL = new TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal((TextDirectionHeuristicsCompat.TextDirectionAlgorithm)null, true);
   private static final int STATE_FALSE = 1;
   private static final int STATE_TRUE = 0;
   private static final int STATE_UNKNOWN = 2;


   static int isRtlText(int var0) {
      switch(var0) {
      case 0:
         return 1;
      case 1:
      case 2:
         return 0;
      default:
         return 2;
      }
   }

   static int isRtlTextOrFormat(int var0) {
      switch(var0) {
      case 1:
      case 2:
         return 0;
      default:
         switch(var0) {
         case 14:
         case 15:
            break;
         case 16:
         case 17:
            return 0;
         default:
            return 2;
         }
      case 0:
         return 1;
      }
   }

   static class TextDirectionHeuristicInternal extends TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl {

      private final boolean mDefaultIsRtl;


      TextDirectionHeuristicInternal(TextDirectionHeuristicsCompat.TextDirectionAlgorithm var1, boolean var2) {
         super(var1);
         this.mDefaultIsRtl = var2;
      }

      protected boolean defaultIsRtl() {
         return this.mDefaultIsRtl;
      }
   }

   static class TextDirectionHeuristicLocale extends TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl {

      static final TextDirectionHeuristicsCompat.TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicsCompat.TextDirectionHeuristicLocale();


      TextDirectionHeuristicLocale() {
         super((TextDirectionHeuristicsCompat.TextDirectionAlgorithm)null);
      }

      protected boolean defaultIsRtl() {
         return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
      }
   }

   abstract static class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {

      private final TextDirectionHeuristicsCompat.TextDirectionAlgorithm mAlgorithm;


      TextDirectionHeuristicImpl(TextDirectionHeuristicsCompat.TextDirectionAlgorithm var1) {
         this.mAlgorithm = var1;
      }

      private boolean doCheck(CharSequence var1, int var2, int var3) {
         switch(this.mAlgorithm.checkRtl(var1, var2, var3)) {
         case 0:
            return true;
         case 1:
            return false;
         default:
            return this.defaultIsRtl();
         }
      }

      protected abstract boolean defaultIsRtl();

      public boolean isRtl(CharSequence var1, int var2, int var3) {
         if(var1 != null && var2 >= 0 && var3 >= 0 && var1.length() - var3 >= var2) {
            return this.mAlgorithm == null?this.defaultIsRtl():this.doCheck(var1, var2, var3);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public boolean isRtl(char[] var1, int var2, int var3) {
         return this.isRtl((CharSequence)CharBuffer.wrap(var1), var2, var3);
      }
   }

   static class AnyStrong implements TextDirectionHeuristicsCompat.TextDirectionAlgorithm {

      static final TextDirectionHeuristicsCompat.AnyStrong INSTANCE_LTR = new TextDirectionHeuristicsCompat.AnyStrong(false);
      static final TextDirectionHeuristicsCompat.AnyStrong INSTANCE_RTL = new TextDirectionHeuristicsCompat.AnyStrong(true);
      private final boolean mLookForRtl;


      private AnyStrong(boolean var1) {
         this.mLookForRtl = var1;
      }

      public int checkRtl(CharSequence var1, int var2, int var3) {
         throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
      }
   }

   interface TextDirectionAlgorithm {

      int checkRtl(CharSequence var1, int var2, int var3);
   }

   static class FirstStrong implements TextDirectionHeuristicsCompat.TextDirectionAlgorithm {

      static final TextDirectionHeuristicsCompat.FirstStrong INSTANCE = new TextDirectionHeuristicsCompat.FirstStrong();


      public int checkRtl(CharSequence var1, int var2, int var3) {
         int var5 = 2;

         for(int var4 = var2; var4 < var3 + var2 && var5 == 2; ++var4) {
            var5 = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(var1.charAt(var4)));
         }

         return var5;
      }
   }
}
