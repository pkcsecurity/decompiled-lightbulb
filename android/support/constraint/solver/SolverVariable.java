package android.support.constraint.solver;

import android.support.constraint.solver.ArrayRow;
import java.util.Arrays;

public class SolverVariable {

   private static final boolean INTERNAL_DEBUG = false;
   static final int MAX_STRENGTH = 6;
   public static final int STRENGTH_EQUALITY = 5;
   public static final int STRENGTH_HIGH = 3;
   public static final int STRENGTH_HIGHEST = 4;
   public static final int STRENGTH_LOW = 1;
   public static final int STRENGTH_MEDIUM = 2;
   public static final int STRENGTH_NONE = 0;
   private static int uniqueId;
   public float computedValue;
   int definitionId = -1;
   public int id = -1;
   ArrayRow[] mClientEquations = new ArrayRow[8];
   int mClientEquationsCount = 0;
   private String mName;
   SolverVariable.Type mType;
   public int strength = 0;
   float[] strengthVector = new float[6];


   public SolverVariable(SolverVariable.Type var1) {
      this.mType = var1;
   }

   public SolverVariable(String var1, SolverVariable.Type var2) {
      this.mName = var1;
      this.mType = var2;
   }

   private static String getUniqueName(SolverVariable.Type var0) {
      ++uniqueId;
      StringBuilder var1;
      switch(null.$SwitchMap$android$support$constraint$solver$SolverVariable$Type[var0.ordinal()]) {
      case 1:
         var1 = new StringBuilder();
         var1.append("U");
         var1.append(uniqueId);
         return var1.toString();
      case 2:
         var1 = new StringBuilder();
         var1.append("C");
         var1.append(uniqueId);
         return var1.toString();
      case 3:
         var1 = new StringBuilder();
         var1.append("S");
         var1.append(uniqueId);
         return var1.toString();
      case 4:
         var1 = new StringBuilder();
         var1.append("e");
         var1.append(uniqueId);
         return var1.toString();
      default:
         var1 = new StringBuilder();
         var1.append("V");
         var1.append(uniqueId);
         return var1.toString();
      }
   }

   void addClientEquation(ArrayRow var1) {
      for(int var2 = 0; var2 < this.mClientEquationsCount; ++var2) {
         if(this.mClientEquations[var2] == var1) {
            return;
         }
      }

      if(this.mClientEquationsCount >= this.mClientEquations.length) {
         this.mClientEquations = (ArrayRow[])Arrays.copyOf(this.mClientEquations, this.mClientEquations.length * 2);
      }

      this.mClientEquations[this.mClientEquationsCount] = var1;
      ++this.mClientEquationsCount;
   }

   void clearStrengths() {
      for(int var1 = 0; var1 < 6; ++var1) {
         this.strengthVector[var1] = 0.0F;
      }

   }

   public String getName() {
      return this.mName;
   }

   void removeClientEquation(ArrayRow var1) {
      int var3 = 0;

      for(int var2 = 0; var2 < this.mClientEquationsCount; ++var2) {
         if(this.mClientEquations[var2] == var1) {
            while(var3 < this.mClientEquationsCount - var2 - 1) {
               ArrayRow[] var5 = this.mClientEquations;
               int var4 = var2 + var3;
               var5[var4] = this.mClientEquations[var4 + 1];
               ++var3;
            }

            --this.mClientEquationsCount;
            return;
         }
      }

   }

   public void reset() {
      this.mName = null;
      this.mType = SolverVariable.Type.UNKNOWN;
      this.strength = 0;
      this.id = -1;
      this.definitionId = -1;
      this.computedValue = 0.0F;
      this.mClientEquationsCount = 0;
   }

   public void setName(String var1) {
      this.mName = var1;
   }

   public void setType(SolverVariable.Type var1) {
      this.mType = var1;
   }

   String strengthsToString() {
      StringBuilder var2 = new StringBuilder();
      var2.append(this);
      var2.append("[");
      String var4 = var2.toString();

      for(int var1 = 0; var1 < this.strengthVector.length; ++var1) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var4);
         var3.append(this.strengthVector[var1]);
         var4 = var3.toString();
         if(var1 < this.strengthVector.length - 1) {
            var3 = new StringBuilder();
            var3.append(var4);
            var3.append(", ");
            var4 = var3.toString();
         } else {
            var3 = new StringBuilder();
            var3.append(var4);
            var3.append("] ");
            var4 = var3.toString();
         }
      }

      return var4;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("");
      var1.append(this.mName);
      return var1.toString();
   }

   public static enum Type {

      // $FF: synthetic field
      private static final SolverVariable.Type[] $VALUES = new SolverVariable.Type[]{UNRESTRICTED, CONSTANT, SLACK, ERROR, UNKNOWN};
      CONSTANT("CONSTANT", 1),
      ERROR("ERROR", 3),
      SLACK("SLACK", 2),
      UNKNOWN("UNKNOWN", 4),
      UNRESTRICTED("UNRESTRICTED", 0);


      private Type(String var1, int var2) {}
   }
}
