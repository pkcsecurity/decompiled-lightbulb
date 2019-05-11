package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.Guideline;
import java.util.ArrayList;
import java.util.HashSet;

public class ConstraintAnchor {

   private static final boolean ALLOW_BINARY = false;
   public static final int ANY_GROUP = Integer.MAX_VALUE;
   public static final int APPLY_GROUP_RESULTS = -2;
   public static final int AUTO_CONSTRAINT_CREATOR = 2;
   public static final int SCOUT_CREATOR = 1;
   private static final int UNSET_GONE_MARGIN = -1;
   public static final int USER_CREATOR = 0;
   public static final boolean USE_CENTER_ANCHOR = false;
   private int mConnectionCreator;
   private ConstraintAnchor.ConnectionType mConnectionType;
   int mGoneMargin = -1;
   int mGroup;
   public int mMargin = 0;
   final ConstraintWidget mOwner;
   SolverVariable mSolverVariable;
   private ConstraintAnchor.Strength mStrength;
   ConstraintAnchor mTarget;
   final ConstraintAnchor.Type mType;


   public ConstraintAnchor(ConstraintWidget var1, ConstraintAnchor.Type var2) {
      this.mStrength = ConstraintAnchor.Strength.NONE;
      this.mConnectionType = ConstraintAnchor.ConnectionType.RELAXED;
      this.mConnectionCreator = 0;
      this.mGroup = Integer.MAX_VALUE;
      this.mOwner = var1;
      this.mType = var2;
   }

   private boolean isConnectionToMe(ConstraintWidget var1, HashSet<ConstraintWidget> var2) {
      if(var2.contains(var1)) {
         return false;
      } else {
         var2.add(var1);
         if(var1 == this.getOwner()) {
            return true;
         } else {
            ArrayList var6 = var1.getAnchors();
            int var4 = var6.size();

            for(int var3 = 0; var3 < var4; ++var3) {
               ConstraintAnchor var5 = (ConstraintAnchor)var6.get(var3);
               if(var5.isSimilarDimensionConnection(this) && var5.isConnected() && this.isConnectionToMe(var5.getTarget().getOwner(), var2)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private String toString(HashSet<ConstraintAnchor> var1) {
      if(var1.add(this)) {
         StringBuilder var2 = new StringBuilder();
         var2.append(this.mOwner.getDebugName());
         var2.append(":");
         var2.append(this.mType.toString());
         String var4;
         if(this.mTarget != null) {
            StringBuilder var3 = new StringBuilder();
            var3.append(" connected to ");
            var3.append(this.mTarget.toString(var1));
            var4 = var3.toString();
         } else {
            var4 = "";
         }

         var2.append(var4);
         return var2.toString();
      } else {
         return "<-";
      }
   }

   public boolean connect(ConstraintAnchor var1, int var2) {
      return this.connect(var1, var2, -1, ConstraintAnchor.Strength.STRONG, 0, false);
   }

   public boolean connect(ConstraintAnchor var1, int var2, int var3) {
      return this.connect(var1, var2, -1, ConstraintAnchor.Strength.STRONG, var3, false);
   }

   public boolean connect(ConstraintAnchor var1, int var2, int var3, ConstraintAnchor.Strength var4, int var5, boolean var6) {
      if(var1 == null) {
         this.mTarget = null;
         this.mMargin = 0;
         this.mGoneMargin = -1;
         this.mStrength = ConstraintAnchor.Strength.NONE;
         this.mConnectionCreator = 2;
         return true;
      } else if(!var6 && !this.isValidConnection(var1)) {
         return false;
      } else {
         this.mTarget = var1;
         if(var2 > 0) {
            this.mMargin = var2;
         } else {
            this.mMargin = 0;
         }

         this.mGoneMargin = var3;
         this.mStrength = var4;
         this.mConnectionCreator = var5;
         return true;
      }
   }

   public boolean connect(ConstraintAnchor var1, int var2, ConstraintAnchor.Strength var3, int var4) {
      return this.connect(var1, var2, -1, var3, var4, false);
   }

   public int getConnectionCreator() {
      return this.mConnectionCreator;
   }

   public ConstraintAnchor.ConnectionType getConnectionType() {
      return this.mConnectionType;
   }

   public int getGroup() {
      return this.mGroup;
   }

   public int getMargin() {
      return this.mOwner.getVisibility() == 8?0:(this.mGoneMargin > -1 && this.mTarget != null && this.mTarget.mOwner.getVisibility() == 8?this.mGoneMargin:this.mMargin);
   }

   public final ConstraintAnchor getOpposite() {
      switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
      case 2:
         return this.mOwner.mRight;
      case 3:
         return this.mOwner.mLeft;
      case 4:
         return this.mOwner.mBottom;
      case 5:
         return this.mOwner.mTop;
      default:
         return null;
      }
   }

   public ConstraintWidget getOwner() {
      return this.mOwner;
   }

   public int getPriorityLevel() {
      switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
      case 1:
         return 2;
      case 2:
         return 2;
      case 3:
         return 2;
      case 4:
         return 2;
      case 5:
         return 2;
      case 6:
         return 0;
      case 7:
         return 0;
      case 8:
         return 1;
      default:
         return 0;
      }
   }

   public int getSnapPriorityLevel() {
      switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
      case 1:
         return 3;
      case 2:
         return 1;
      case 3:
         return 1;
      case 4:
         return 0;
      case 5:
         return 0;
      case 6:
         return 0;
      case 7:
         return 1;
      case 8:
         return 2;
      default:
         return 0;
      }
   }

   public SolverVariable getSolverVariable() {
      return this.mSolverVariable;
   }

   public ConstraintAnchor.Strength getStrength() {
      return this.mStrength;
   }

   public ConstraintAnchor getTarget() {
      return this.mTarget;
   }

   public ConstraintAnchor.Type getType() {
      return this.mType;
   }

   public boolean isConnected() {
      return this.mTarget != null;
   }

   public boolean isConnectionAllowed(ConstraintWidget var1) {
      if(this.isConnectionToMe(var1, new HashSet())) {
         return false;
      } else {
         ConstraintWidget var2 = this.getOwner().getParent();
         return var2 == var1?true:var1.getParent() == var2;
      }
   }

   public boolean isConnectionAllowed(ConstraintWidget var1, ConstraintAnchor var2) {
      return this.isConnectionAllowed(var1);
   }

   public boolean isSideAnchor() {
      switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
      case 2:
      case 3:
      case 4:
      case 5:
         return true;
      default:
         return false;
      }
   }

   public boolean isSimilarDimensionConnection(ConstraintAnchor var1) {
      ConstraintAnchor.Type var6 = var1.getType();
      ConstraintAnchor.Type var5 = this.mType;
      boolean var4 = true;
      boolean var3 = true;
      if(var6 == var5) {
         return true;
      } else {
         boolean var2;
         switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
         case 1:
            if(var6 != ConstraintAnchor.Type.BASELINE) {
               return true;
            }

            return false;
         case 2:
         case 3:
         case 6:
            var2 = var4;
            if(var6 != ConstraintAnchor.Type.LEFT) {
               var2 = var4;
               if(var6 != ConstraintAnchor.Type.RIGHT) {
                  if(var6 == ConstraintAnchor.Type.CENTER_X) {
                     return true;
                  }

                  var2 = false;
               }
            }

            return var2;
         case 4:
         case 5:
         case 7:
         case 8:
            var2 = var3;
            if(var6 != ConstraintAnchor.Type.TOP) {
               var2 = var3;
               if(var6 != ConstraintAnchor.Type.BOTTOM) {
                  var2 = var3;
                  if(var6 != ConstraintAnchor.Type.CENTER_Y) {
                     if(var6 == ConstraintAnchor.Type.BASELINE) {
                        return true;
                     }

                     var2 = false;
                  }
               }
            }

            return var2;
         default:
            return false;
         }
      }
   }

   public boolean isSnapCompatibleWith(ConstraintAnchor var1) {
      if(this.mType == ConstraintAnchor.Type.CENTER) {
         return false;
      } else if(this.mType == var1.getType()) {
         return true;
      } else {
         int var2;
         switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
         case 2:
            var2 = null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[var1.getType().ordinal()];
            if(var2 != 3) {
               if(var2 != 6) {
                  return false;
               }

               return true;
            }

            return true;
         case 3:
            var2 = null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[var1.getType().ordinal()];
            if(var2 != 2) {
               if(var2 != 6) {
                  return false;
               }

               return true;
            }

            return true;
         case 4:
            var2 = null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[var1.getType().ordinal()];
            if(var2 != 5) {
               if(var2 != 7) {
                  return false;
               }

               return true;
            }

            return true;
         case 5:
            var2 = null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[var1.getType().ordinal()];
            if(var2 != 4) {
               if(var2 != 7) {
                  return false;
               }

               return true;
            }

            return true;
         case 6:
            switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[var1.getType().ordinal()]) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return false;
            }
         case 7:
            switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[var1.getType().ordinal()]) {
            case 4:
               return true;
            case 5:
               return true;
            default:
               return false;
            }
         default:
            return false;
         }
      }
   }

   public boolean isValidConnection(ConstraintAnchor var1) {
      boolean var3 = false;
      if(var1 == null) {
         return false;
      } else {
         ConstraintAnchor.Type var4 = var1.getType();
         if(var4 == this.mType) {
            return this.mType == ConstraintAnchor.Type.CENTER?false:this.mType != ConstraintAnchor.Type.BASELINE || var1.getOwner().hasBaseline() && this.getOwner().hasBaseline();
         } else {
            boolean var2;
            switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
            case 1:
               var2 = var3;
               if(var4 != ConstraintAnchor.Type.BASELINE) {
                  var2 = var3;
                  if(var4 != ConstraintAnchor.Type.CENTER_X) {
                     var2 = var3;
                     if(var4 != ConstraintAnchor.Type.CENTER_Y) {
                        var2 = true;
                     }
                  }
               }

               return var2;
            case 2:
            case 3:
               if(var4 != ConstraintAnchor.Type.LEFT && var4 != ConstraintAnchor.Type.RIGHT) {
                  var2 = false;
               } else {
                  var2 = true;
               }

               var3 = var2;
               if(var1.getOwner() instanceof Guideline) {
                  if(!var2 && var4 != ConstraintAnchor.Type.CENTER_X) {
                     return false;
                  }

                  var3 = true;
               }

               return var3;
            case 4:
            case 5:
               if(var4 != ConstraintAnchor.Type.TOP && var4 != ConstraintAnchor.Type.BOTTOM) {
                  var2 = false;
               } else {
                  var2 = true;
               }

               var3 = var2;
               if(var1.getOwner() instanceof Guideline) {
                  if(!var2 && var4 != ConstraintAnchor.Type.CENTER_Y) {
                     return false;
                  }

                  var3 = true;
               }

               return var3;
            default:
               return false;
            }
         }
      }
   }

   public boolean isVerticalAnchor() {
      int var1 = null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()];
      if(var1 != 6) {
         switch(var1) {
         case 1:
         case 2:
         case 3:
            break;
         default:
            return true;
         }
      }

      return false;
   }

   public void reset() {
      this.mTarget = null;
      this.mMargin = 0;
      this.mGoneMargin = -1;
      this.mStrength = ConstraintAnchor.Strength.STRONG;
      this.mConnectionCreator = 0;
      this.mConnectionType = ConstraintAnchor.ConnectionType.RELAXED;
   }

   public void resetSolverVariable(Cache var1) {
      if(this.mSolverVariable == null) {
         this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED);
      } else {
         this.mSolverVariable.reset();
      }
   }

   public void setConnectionCreator(int var1) {
      this.mConnectionCreator = var1;
   }

   public void setConnectionType(ConstraintAnchor.ConnectionType var1) {
      this.mConnectionType = var1;
   }

   public void setGoneMargin(int var1) {
      if(this.isConnected()) {
         this.mGoneMargin = var1;
      }

   }

   public void setGroup(int var1) {
      this.mGroup = var1;
   }

   public void setMargin(int var1) {
      if(this.isConnected()) {
         this.mMargin = var1;
      }

   }

   public void setStrength(ConstraintAnchor.Strength var1) {
      if(this.isConnected()) {
         this.mStrength = var1;
      }

   }

   public String toString() {
      HashSet var1 = new HashSet();
      StringBuilder var2 = new StringBuilder();
      var2.append(this.mOwner.getDebugName());
      var2.append(":");
      var2.append(this.mType.toString());
      String var4;
      if(this.mTarget != null) {
         StringBuilder var3 = new StringBuilder();
         var3.append(" connected to ");
         var3.append(this.mTarget.toString(var1));
         var4 = var3.toString();
      } else {
         var4 = "";
      }

      var2.append(var4);
      return var2.toString();
   }

   public static enum Type {

      // $FF: synthetic field
      private static final ConstraintAnchor.Type[] $VALUES = new ConstraintAnchor.Type[]{NONE, LEFT, TOP, RIGHT, BOTTOM, BASELINE, CENTER, CENTER_X, CENTER_Y};
      BASELINE("BASELINE", 5),
      BOTTOM("BOTTOM", 4),
      CENTER("CENTER", 6),
      CENTER_X("CENTER_X", 7),
      CENTER_Y("CENTER_Y", 8),
      LEFT("LEFT", 1),
      NONE("NONE", 0),
      RIGHT("RIGHT", 3),
      TOP("TOP", 2);


      private Type(String var1, int var2) {}
   }

   public static enum Strength {

      // $FF: synthetic field
      private static final ConstraintAnchor.Strength[] $VALUES = new ConstraintAnchor.Strength[]{NONE, STRONG, WEAK};
      NONE("NONE", 0),
      STRONG("STRONG", 1),
      WEAK("WEAK", 2);


      private Strength(String var1, int var2) {}
   }

   public static enum ConnectionType {

      // $FF: synthetic field
      private static final ConstraintAnchor.ConnectionType[] $VALUES = new ConstraintAnchor.ConnectionType[]{RELAXED, STRICT};
      RELAXED("RELAXED", 0),
      STRICT("STRICT", 1);


      private ConnectionType(String var1, int var2) {}
   }
}
