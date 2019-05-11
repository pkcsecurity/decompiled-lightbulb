package android.support.constraint.solver;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.Goal;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem {

   private static final boolean DEBUG = false;
   private static int POOL_SIZE;
   private int TABLE_SIZE = 32;
   private boolean[] mAlreadyTestedCandidates;
   final Cache mCache;
   private Goal mGoal = new Goal();
   private int mMaxColumns;
   private int mMaxRows;
   int mNumColumns;
   private int mNumRows;
   private SolverVariable[] mPoolVariables;
   private int mPoolVariablesCount;
   private ArrayRow[] mRows;
   private HashMap<String, SolverVariable> mVariables = null;
   int mVariablesID = 0;
   private ArrayRow[] tempClientsCopy;


   public LinearSystem() {
      this.mMaxColumns = this.TABLE_SIZE;
      this.mRows = null;
      this.mAlreadyTestedCandidates = new boolean[this.TABLE_SIZE];
      this.mNumColumns = 1;
      this.mNumRows = 0;
      this.mMaxRows = this.TABLE_SIZE;
      this.mPoolVariables = new SolverVariable[POOL_SIZE];
      this.mPoolVariablesCount = 0;
      this.tempClientsCopy = new ArrayRow[this.TABLE_SIZE];
      this.mRows = new ArrayRow[this.TABLE_SIZE];
      this.releaseRows();
      this.mCache = new Cache();
   }

   private SolverVariable acquireSolverVariable(SolverVariable.Type var1) {
      SolverVariable var3 = (SolverVariable)this.mCache.solverVariablePool.acquire();
      SolverVariable var4;
      if(var3 == null) {
         var4 = new SolverVariable(var1);
      } else {
         var3.reset();
         var3.setType(var1);
         var4 = var3;
      }

      if(this.mPoolVariablesCount >= POOL_SIZE) {
         POOL_SIZE *= 2;
         this.mPoolVariables = (SolverVariable[])Arrays.copyOf(this.mPoolVariables, POOL_SIZE);
      }

      SolverVariable[] var5 = this.mPoolVariables;
      int var2 = this.mPoolVariablesCount;
      this.mPoolVariablesCount = var2 + 1;
      var5[var2] = var4;
      return var4;
   }

   private void addError(ArrayRow var1) {
      var1.addError(this.createErrorVariable(), this.createErrorVariable());
   }

   private void addSingleError(ArrayRow var1, int var2) {
      var1.addSingleError(this.createErrorVariable(), var2);
   }

   private void computeValues() {
      for(int var1 = 0; var1 < this.mNumRows; ++var1) {
         ArrayRow var2 = this.mRows[var1];
         var2.variable.computedValue = var2.constantValue;
      }

   }

   public static ArrayRow createRowCentering(LinearSystem var0, SolverVariable var1, SolverVariable var2, int var3, float var4, SolverVariable var5, SolverVariable var6, int var7, boolean var8) {
      ArrayRow var9 = var0.createRow();
      var9.createRowCentering(var1, var2, var3, var4, var5, var6, var7);
      if(var8) {
         var1 = var0.createErrorVariable();
         SolverVariable var10 = var0.createErrorVariable();
         var1.strength = 4;
         var10.strength = 4;
         var9.addError(var1, var10);
      }

      return var9;
   }

   public static ArrayRow createRowDimensionPercent(LinearSystem var0, SolverVariable var1, SolverVariable var2, SolverVariable var3, float var4, boolean var5) {
      ArrayRow var6 = var0.createRow();
      if(var5) {
         var0.addError(var6);
      }

      return var6.createRowDimensionPercent(var1, var2, var3, var4);
   }

   public static ArrayRow createRowEquals(LinearSystem var0, SolverVariable var1, SolverVariable var2, int var3, boolean var4) {
      ArrayRow var5 = var0.createRow();
      var5.createRowEquals(var1, var2, var3);
      if(var4) {
         var0.addSingleError(var5, 1);
      }

      return var5;
   }

   public static ArrayRow createRowGreaterThan(LinearSystem var0, SolverVariable var1, SolverVariable var2, int var3, boolean var4) {
      SolverVariable var5 = var0.createSlackVariable();
      ArrayRow var6 = var0.createRow();
      var6.createRowGreaterThan(var1, var2, var5, var3);
      if(var4) {
         var0.addSingleError(var6, (int)(var6.variables.get(var5) * -1.0F));
      }

      return var6;
   }

   public static ArrayRow createRowLowerThan(LinearSystem var0, SolverVariable var1, SolverVariable var2, int var3, boolean var4) {
      SolverVariable var5 = var0.createSlackVariable();
      ArrayRow var6 = var0.createRow();
      var6.createRowLowerThan(var1, var2, var5, var3);
      if(var4) {
         var0.addSingleError(var6, (int)(var6.variables.get(var5) * -1.0F));
      }

      return var6;
   }

   private SolverVariable createVariable(String var1, SolverVariable.Type var2) {
      if(this.mNumColumns + 1 >= this.mMaxColumns) {
         this.increaseTableSize();
      }

      SolverVariable var3 = this.acquireSolverVariable(var2);
      var3.setName(var1);
      ++this.mVariablesID;
      ++this.mNumColumns;
      var3.id = this.mVariablesID;
      if(this.mVariables == null) {
         this.mVariables = new HashMap();
      }

      this.mVariables.put(var1, var3);
      this.mCache.mIndexedVariables[this.mVariablesID] = var3;
      return var3;
   }

   private void displayRows() {
      this.displaySolverVariables();
      String var2 = "";

      StringBuilder var3;
      for(int var1 = 0; var1 < this.mNumRows; ++var1) {
         var3 = new StringBuilder();
         var3.append(var2);
         var3.append(this.mRows[var1]);
         var2 = var3.toString();
         var3 = new StringBuilder();
         var3.append(var2);
         var3.append("\n");
         var2 = var3.toString();
      }

      String var4 = var2;
      if(this.mGoal.variables.size() != 0) {
         var3 = new StringBuilder();
         var3.append(var2);
         var3.append(this.mGoal);
         var3.append("\n");
         var4 = var3.toString();
      }

      System.out.println(var4);
   }

   private void displaySolverVariables() {
      StringBuilder var2 = new StringBuilder();
      var2.append("Display Rows (");
      var2.append(this.mNumRows);
      var2.append("x");
      var2.append(this.mNumColumns);
      var2.append(") :\n\t | C | ");
      String var5 = var2.toString();

      StringBuilder var6;
      for(int var1 = 1; var1 <= this.mNumColumns; ++var1) {
         SolverVariable var3 = this.mCache.mIndexedVariables[var1];
         StringBuilder var4 = new StringBuilder();
         var4.append(var5);
         var4.append(var3);
         var5 = var4.toString();
         var6 = new StringBuilder();
         var6.append(var5);
         var6.append(" | ");
         var5 = var6.toString();
      }

      var6 = new StringBuilder();
      var6.append(var5);
      var6.append("\n");
      var5 = var6.toString();
      System.out.println(var5);
   }

   private int enforceBFS(Goal var1) throws Exception {
      int var5 = 0;

      boolean var16;
      while(true) {
         if(var5 >= this.mNumRows) {
            var16 = false;
            break;
         }

         if(this.mRows[var5].variable.mType != SolverVariable.Type.UNRESTRICTED && this.mRows[var5].constantValue < 0.0F) {
            var16 = true;
            break;
         }

         ++var5;
      }

      int var6;
      if(var16) {
         boolean var11 = false;
         var5 = 0;

         while(true) {
            var6 = var5;
            if(var11) {
               break;
            }

            int var13 = var5 + 1;
            int var8 = 0;
            var5 = -1;
            var6 = -1;
            float var2 = Float.MAX_VALUE;

            int var9;
            ArrayRow var14;
            for(int var7 = 0; var8 < this.mNumRows; var7 = var9) {
               var14 = this.mRows[var8];
               float var3;
               int var10;
               int var12;
               if(var14.variable.mType == SolverVariable.Type.UNRESTRICTED) {
                  var12 = var5;
                  var10 = var6;
                  var3 = var2;
                  var9 = var7;
               } else {
                  var12 = var5;
                  var10 = var6;
                  var3 = var2;
                  var9 = var7;
                  if(var14.constantValue < 0.0F) {
                     var9 = var5;
                     byte var17 = 1;
                     var5 = var7;
                     var7 = var9;

                     for(var9 = var17; var9 < this.mNumColumns; ++var9) {
                        SolverVariable var15 = this.mCache.mIndexedVariables[var9];
                        float var4 = var14.variables.get(var15);
                        if(var4 > 0.0F) {
                           var12 = var5;
                           var10 = var7;
                           var5 = 0;
                           var7 = var6;

                           for(var6 = var12; var5 < 6; var6 = var12) {
                              label121: {
                                 var3 = var15.strengthVector[var5] / var4;
                                 if(var3 >= var2 || var5 != var6) {
                                    var12 = var6;
                                    if(var5 <= var6) {
                                       break label121;
                                    }
                                 }

                                 var2 = var3;
                                 var10 = var8;
                                 var7 = var9;
                                 var12 = var5;
                              }

                              ++var5;
                           }

                           var5 = var6;
                           var6 = var7;
                           var7 = var10;
                        }
                     }

                     var9 = var5;
                     var3 = var2;
                     var10 = var6;
                     var12 = var7;
                  }
               }

               ++var8;
               var5 = var12;
               var6 = var10;
               var2 = var3;
            }

            if(var5 == -1) {
               var11 = true;
               var5 = var13;
            } else {
               var14 = this.mRows[var5];
               var14.variable.definitionId = -1;
               var14.pivot(this.mCache.mIndexedVariables[var6]);
               var14.variable.definitionId = var5;

               for(var5 = 0; var5 < this.mNumRows; ++var5) {
                  this.mRows[var5].updateRowWithEquation(var14);
               }

               var1.updateFromSystem(this);
               var5 = var13;
            }
         }
      } else {
         var6 = 0;
      }

      for(var5 = 0; var5 < this.mNumRows; ++var5) {
         if(this.mRows[var5].variable.mType != SolverVariable.Type.UNRESTRICTED && this.mRows[var5].constantValue < 0.0F) {
            return var6;
         }
      }

      return var6;
   }

   private String getDisplaySize(int var1) {
      var1 *= 4;
      int var2 = var1 / 1024;
      int var3 = var2 / 1024;
      StringBuilder var4;
      if(var3 > 0) {
         var4 = new StringBuilder();
         var4.append("");
         var4.append(var3);
         var4.append(" Mb");
         return var4.toString();
      } else if(var2 > 0) {
         var4 = new StringBuilder();
         var4.append("");
         var4.append(var2);
         var4.append(" Kb");
         return var4.toString();
      } else {
         var4 = new StringBuilder();
         var4.append("");
         var4.append(var1);
         var4.append(" bytes");
         return var4.toString();
      }
   }

   private void increaseTableSize() {
      this.TABLE_SIZE *= 2;
      this.mRows = (ArrayRow[])Arrays.copyOf(this.mRows, this.TABLE_SIZE);
      this.mCache.mIndexedVariables = (SolverVariable[])Arrays.copyOf(this.mCache.mIndexedVariables, this.TABLE_SIZE);
      this.mAlreadyTestedCandidates = new boolean[this.TABLE_SIZE];
      this.mMaxColumns = this.TABLE_SIZE;
      this.mMaxRows = this.TABLE_SIZE;
      this.mGoal.variables.clear();
   }

   private int optimize(Goal var1) {
      int var5;
      for(var5 = 0; var5 < this.mNumColumns; ++var5) {
         this.mAlreadyTestedCandidates[var5] = false;
      }

      boolean var14 = false;
      int var7 = 0;
      int var8 = 0;

      while(!var14) {
         int var10 = var7 + 1;
         SolverVariable var12 = var1.getPivotCandidate();
         boolean var15 = var14;
         int var6 = var8;
         SolverVariable var11 = var12;
         if(var12 != null) {
            if(this.mAlreadyTestedCandidates[var12.id]) {
               var11 = null;
               var15 = var14;
               var6 = var8;
            } else {
               this.mAlreadyTestedCandidates[var12.id] = true;
               ++var8;
               var15 = var14;
               var6 = var8;
               var11 = var12;
               if(var8 >= this.mNumColumns) {
                  var15 = true;
                  var11 = var12;
                  var6 = var8;
               }
            }
         }

         if(var11 != null) {
            var5 = 0;
            var8 = -1;

            float var3;
            ArrayRow var16;
            for(float var2 = Float.MAX_VALUE; var5 < this.mNumRows; var2 = var3) {
               var16 = this.mRows[var5];
               int var9;
               if(var16.variable.mType == SolverVariable.Type.UNRESTRICTED) {
                  var9 = var8;
                  var3 = var2;
               } else {
                  var9 = var8;
                  var3 = var2;
                  if(var16.hasVariable(var11)) {
                     float var4 = var16.variables.get(var11);
                     var9 = var8;
                     var3 = var2;
                     if(var4 < 0.0F) {
                        var4 = -var16.constantValue / var4;
                        var9 = var8;
                        var3 = var2;
                        if(var4 < var2) {
                           var9 = var5;
                           var3 = var4;
                        }
                     }
                  }
               }

               ++var5;
               var8 = var9;
            }

            if(var8 > -1) {
               var16 = this.mRows[var8];
               var16.variable.definitionId = -1;
               var16.pivot(var11);
               var16.variable.definitionId = var8;

               for(var5 = 0; var5 < this.mNumRows; ++var5) {
                  this.mRows[var5].updateRowWithEquation(var16);
               }

               var1.updateFromSystem(this);

               try {
                  this.enforceBFS(var1);
               } catch (Exception var13) {
                  var13.printStackTrace();
                  var14 = var15;
                  var7 = var10;
                  var8 = var6;
                  continue;
               }

               var14 = var15;
               var7 = var10;
               var8 = var6;
               continue;
            }
         }

         var14 = true;
         var7 = var10;
         var8 = var6;
      }

      return var7;
   }

   private void releaseRows() {
      for(int var1 = 0; var1 < this.mRows.length; ++var1) {
         ArrayRow var2 = this.mRows[var1];
         if(var2 != null) {
            this.mCache.arrayRowPool.release(var2);
         }

         this.mRows[var1] = null;
      }

   }

   private void updateRowFromVariables(ArrayRow var1) {
      if(this.mNumRows > 0) {
         var1.variables.updateFromSystem(var1, this.mRows);
         if(var1.variables.currentSize == 0) {
            var1.isSimpleDefinition = true;
         }
      }

   }

   public void addCentering(SolverVariable var1, SolverVariable var2, int var3, float var4, SolverVariable var5, SolverVariable var6, int var7, int var8) {
      ArrayRow var9 = this.createRow();
      var9.createRowCentering(var1, var2, var3, var4, var5, var6, var7);
      var1 = this.createErrorVariable();
      var2 = this.createErrorVariable();
      var1.strength = var8;
      var2.strength = var8;
      var9.addError(var1, var2);
      this.addConstraint(var9);
   }

   public void addConstraint(ArrayRow var1) {
      if(var1 != null) {
         if(this.mNumRows + 1 >= this.mMaxRows || this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
         }

         if(!var1.isSimpleDefinition) {
            this.updateRowFromVariables(var1);
            var1.ensurePositiveConstant();
            var1.pickRowVariable();
            if(!var1.hasKeyVariable()) {
               return;
            }
         }

         if(this.mRows[this.mNumRows] != null) {
            this.mCache.arrayRowPool.release(this.mRows[this.mNumRows]);
         }

         if(!var1.isSimpleDefinition) {
            var1.updateClientEquations();
         }

         this.mRows[this.mNumRows] = var1;
         var1.variable.definitionId = this.mNumRows++;
         int var5 = var1.variable.mClientEquationsCount;
         if(var5 > 0) {
            while(this.tempClientsCopy.length < var5) {
               this.tempClientsCopy = new ArrayRow[this.tempClientsCopy.length * 2];
            }

            ArrayRow[] var6 = this.tempClientsCopy;
            byte var4 = 0;
            int var2 = 0;

            while(true) {
               int var3 = var4;
               if(var2 >= var5) {
                  for(; var3 < var5; ++var3) {
                     ArrayRow var7 = var6[var3];
                     if(var7 != var1) {
                        var7.variables.updateFromRow(var7, var1);
                        var7.updateClientEquations();
                     }
                  }
                  break;
               }

               var6[var2] = var1.variable.mClientEquations[var2];
               ++var2;
            }
         }

      }
   }

   public ArrayRow addEquality(SolverVariable var1, SolverVariable var2, int var3, int var4) {
      ArrayRow var5 = this.createRow();
      var5.createRowEquals(var1, var2, var3);
      var1 = this.createErrorVariable();
      var2 = this.createErrorVariable();
      var1.strength = var4;
      var2.strength = var4;
      var5.addError(var1, var2);
      this.addConstraint(var5);
      return var5;
   }

   public void addEquality(SolverVariable var1, int var2) {
      int var3 = var1.definitionId;
      ArrayRow var4;
      if(var1.definitionId != -1) {
         var4 = this.mRows[var3];
         if(var4.isSimpleDefinition) {
            var4.constantValue = (float)var2;
         } else {
            var4 = this.createRow();
            var4.createRowEquals(var1, var2);
            this.addConstraint(var4);
         }
      } else {
         var4 = this.createRow();
         var4.createRowDefinition(var1, var2);
         this.addConstraint(var4);
      }
   }

   public void addGreaterThan(SolverVariable var1, SolverVariable var2, int var3, int var4) {
      ArrayRow var5 = this.createRow();
      SolverVariable var6 = this.createSlackVariable();
      var6.strength = var4;
      var5.createRowGreaterThan(var1, var2, var6, var3);
      this.addConstraint(var5);
   }

   public void addLowerThan(SolverVariable var1, SolverVariable var2, int var3, int var4) {
      ArrayRow var5 = this.createRow();
      SolverVariable var6 = this.createSlackVariable();
      var6.strength = var4;
      var5.createRowLowerThan(var1, var2, var6, var3);
      this.addConstraint(var5);
   }

   public SolverVariable createErrorVariable() {
      if(this.mNumColumns + 1 >= this.mMaxColumns) {
         this.increaseTableSize();
      }

      SolverVariable var1 = this.acquireSolverVariable(SolverVariable.Type.ERROR);
      ++this.mVariablesID;
      ++this.mNumColumns;
      var1.id = this.mVariablesID;
      this.mCache.mIndexedVariables[this.mVariablesID] = var1;
      return var1;
   }

   public SolverVariable createObjectVariable(Object var1) {
      SolverVariable var2 = null;
      if(var1 == null) {
         return null;
      } else {
         if(this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
         }

         if(var1 instanceof ConstraintAnchor) {
            ConstraintAnchor var3 = (ConstraintAnchor)var1;
            var2 = var3.getSolverVariable();
            SolverVariable var4 = var2;
            if(var2 == null) {
               var3.resetSolverVariable(this.mCache);
               var4 = var3.getSolverVariable();
            }

            if(var4.id != -1 && var4.id <= this.mVariablesID) {
               var2 = var4;
               if(this.mCache.mIndexedVariables[var4.id] != null) {
                  return var2;
               }
            }

            if(var4.id != -1) {
               var4.reset();
            }

            ++this.mVariablesID;
            ++this.mNumColumns;
            var4.id = this.mVariablesID;
            var4.mType = SolverVariable.Type.UNRESTRICTED;
            this.mCache.mIndexedVariables[this.mVariablesID] = var4;
            var2 = var4;
         }

         return var2;
      }
   }

   public ArrayRow createRow() {
      ArrayRow var1 = (ArrayRow)this.mCache.arrayRowPool.acquire();
      if(var1 == null) {
         return new ArrayRow(this.mCache);
      } else {
         var1.reset();
         return var1;
      }
   }

   public SolverVariable createSlackVariable() {
      if(this.mNumColumns + 1 >= this.mMaxColumns) {
         this.increaseTableSize();
      }

      SolverVariable var1 = this.acquireSolverVariable(SolverVariable.Type.SLACK);
      ++this.mVariablesID;
      ++this.mNumColumns;
      var1.id = this.mVariablesID;
      this.mCache.mIndexedVariables[this.mVariablesID] = var1;
      return var1;
   }

   void displayReadableRows() {
      this.displaySolverVariables();
      String var2 = "";

      StringBuilder var3;
      for(int var1 = 0; var1 < this.mNumRows; ++var1) {
         var3 = new StringBuilder();
         var3.append(var2);
         var3.append(this.mRows[var1].toReadableString());
         var2 = var3.toString();
         var3 = new StringBuilder();
         var3.append(var2);
         var3.append("\n");
         var2 = var3.toString();
      }

      String var4 = var2;
      if(this.mGoal != null) {
         var3 = new StringBuilder();
         var3.append(var2);
         var3.append(this.mGoal);
         var3.append("\n");
         var4 = var3.toString();
      }

      System.out.println(var4);
   }

   void displaySystemInformations() {
      int var2 = 0;

      int var1;
      int var3;
      for(var1 = 0; var2 < this.TABLE_SIZE; var1 = var3) {
         var3 = var1;
         if(this.mRows[var2] != null) {
            var3 = var1 + this.mRows[var2].sizeInBytes();
         }

         ++var2;
      }

      var2 = 0;

      int var4;
      for(var3 = 0; var2 < this.mNumRows; var3 = var4) {
         var4 = var3;
         if(this.mRows[var2] != null) {
            var4 = var3 + this.mRows[var2].sizeInBytes();
         }

         ++var2;
      }

      PrintStream var5 = System.out;
      StringBuilder var6 = new StringBuilder();
      var6.append("Linear System -> Table size: ");
      var6.append(this.TABLE_SIZE);
      var6.append(" (");
      var6.append(this.getDisplaySize(this.TABLE_SIZE * this.TABLE_SIZE));
      var6.append(") -- row sizes: ");
      var6.append(this.getDisplaySize(var1));
      var6.append(", actual size: ");
      var6.append(this.getDisplaySize(var3));
      var6.append(" rows: ");
      var6.append(this.mNumRows);
      var6.append("/");
      var6.append(this.mMaxRows);
      var6.append(" cols: ");
      var6.append(this.mNumColumns);
      var6.append("/");
      var6.append(this.mMaxColumns);
      var6.append(" ");
      var6.append(0);
      var6.append(" occupied cells, ");
      var6.append(this.getDisplaySize(0));
      var5.println(var6.toString());
   }

   public void displayVariablesReadableRows() {
      this.displaySolverVariables();
      String var2 = "";

      String var3;
      StringBuilder var4;
      for(int var1 = 0; var1 < this.mNumRows; var2 = var3) {
         var3 = var2;
         if(this.mRows[var1].variable.mType == SolverVariable.Type.UNRESTRICTED) {
            var4 = new StringBuilder();
            var4.append(var2);
            var4.append(this.mRows[var1].toReadableString());
            var2 = var4.toString();
            var4 = new StringBuilder();
            var4.append(var2);
            var4.append("\n");
            var3 = var4.toString();
         }

         ++var1;
      }

      var3 = var2;
      if(this.mGoal.variables.size() != 0) {
         var4 = new StringBuilder();
         var4.append(var2);
         var4.append(this.mGoal);
         var4.append("\n");
         var3 = var4.toString();
      }

      System.out.println(var3);
   }

   public Cache getCache() {
      return this.mCache;
   }

   Goal getGoal() {
      return this.mGoal;
   }

   public int getMemoryUsed() {
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < this.mNumRows; var2 = var3) {
         var3 = var2;
         if(this.mRows[var1] != null) {
            var3 = var2 + this.mRows[var1].sizeInBytes();
         }

         ++var1;
      }

      return var2;
   }

   public int getNumEquations() {
      return this.mNumRows;
   }

   public int getNumVariables() {
      return this.mVariablesID;
   }

   public int getObjectVariableValue(Object var1) {
      SolverVariable var2 = ((ConstraintAnchor)var1).getSolverVariable();
      return var2 != null?(int)(var2.computedValue + 0.5F):0;
   }

   ArrayRow getRow(int var1) {
      return this.mRows[var1];
   }

   float getValueFor(String var1) {
      SolverVariable var2 = this.getVariable(var1, SolverVariable.Type.UNRESTRICTED);
      return var2 == null?0.0F:var2.computedValue;
   }

   SolverVariable getVariable(String var1, SolverVariable.Type var2) {
      if(this.mVariables == null) {
         this.mVariables = new HashMap();
      }

      SolverVariable var4 = (SolverVariable)this.mVariables.get(var1);
      SolverVariable var3 = var4;
      if(var4 == null) {
         var3 = this.createVariable(var1, var2);
      }

      return var3;
   }

   public void minimize() throws Exception {
      this.minimizeGoal(this.mGoal);
   }

   void minimizeGoal(Goal var1) throws Exception {
      var1.updateFromSystem(this);
      this.enforceBFS(var1);
      this.optimize(var1);
      this.computeValues();
   }

   void rebuildGoalFromErrors() {
      this.mGoal.updateFromSystem(this);
   }

   public void reset() {
      int var1;
      for(var1 = 0; var1 < this.mCache.mIndexedVariables.length; ++var1) {
         SolverVariable var2 = this.mCache.mIndexedVariables[var1];
         if(var2 != null) {
            var2.reset();
         }
      }

      this.mCache.solverVariablePool.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
      this.mPoolVariablesCount = 0;
      Arrays.fill(this.mCache.mIndexedVariables, (Object)null);
      if(this.mVariables != null) {
         this.mVariables.clear();
      }

      this.mVariablesID = 0;
      this.mGoal.variables.clear();
      this.mNumColumns = 1;

      for(var1 = 0; var1 < this.mNumRows; ++var1) {
         this.mRows[var1].used = false;
      }

      this.releaseRows();
      this.mNumRows = 0;
   }
}
