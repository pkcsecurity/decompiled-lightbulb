package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {

   private ConstraintHorizontalLayout.ContentAlignment mAlignment;


   public ConstraintHorizontalLayout() {
      this.mAlignment = ConstraintHorizontalLayout.ContentAlignment.MIDDLE;
   }

   public ConstraintHorizontalLayout(int var1, int var2) {
      super(var1, var2);
      this.mAlignment = ConstraintHorizontalLayout.ContentAlignment.MIDDLE;
   }

   public ConstraintHorizontalLayout(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.mAlignment = ConstraintHorizontalLayout.ContentAlignment.MIDDLE;
   }

   public void addToSolver(LinearSystem var1, int var2) {
      if(this.mChildren.size() != 0) {
         int var3 = 0;
         int var4 = this.mChildren.size();

         Object var5;
         ConstraintAnchor.Strength var6;
         ConstraintWidget var7;
         for(var5 = this; var3 < var4; var5 = var7) {
            var7 = (ConstraintWidget)this.mChildren.get(var3);
            if(var5 != this) {
               var7.connect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)var5, ConstraintAnchor.Type.RIGHT);
               ((ConstraintWidget)var5).connect(ConstraintAnchor.Type.RIGHT, var7, ConstraintAnchor.Type.LEFT);
            } else {
               var6 = ConstraintAnchor.Strength.STRONG;
               if(this.mAlignment == ConstraintHorizontalLayout.ContentAlignment.END) {
                  var6 = ConstraintAnchor.Strength.WEAK;
               }

               var7.connect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)var5, ConstraintAnchor.Type.LEFT, 0, var6);
            }

            var7.connect(ConstraintAnchor.Type.TOP, this, ConstraintAnchor.Type.TOP);
            var7.connect(ConstraintAnchor.Type.BOTTOM, this, ConstraintAnchor.Type.BOTTOM);
            ++var3;
         }

         if(var5 != this) {
            var6 = ConstraintAnchor.Strength.STRONG;
            if(this.mAlignment == ConstraintHorizontalLayout.ContentAlignment.BEGIN) {
               var6 = ConstraintAnchor.Strength.WEAK;
            }

            ((ConstraintWidget)var5).connect(ConstraintAnchor.Type.RIGHT, this, ConstraintAnchor.Type.RIGHT, 0, var6);
         }
      }

      super.addToSolver(var1, var2);
   }

   public static enum ContentAlignment {

      // $FF: synthetic field
      private static final ConstraintHorizontalLayout.ContentAlignment[] $VALUES = new ConstraintHorizontalLayout.ContentAlignment[]{BEGIN, MIDDLE, END, TOP, VERTICAL_MIDDLE, BOTTOM, LEFT, RIGHT};
      BEGIN("BEGIN", 0),
      BOTTOM("BOTTOM", 5),
      END("END", 2),
      LEFT("LEFT", 6),
      MIDDLE("MIDDLE", 1),
      RIGHT("RIGHT", 7),
      TOP("TOP", 3),
      VERTICAL_MIDDLE("VERTICAL_MIDDLE", 4);


      private ContentAlignment(String var1, int var2) {}
   }
}
