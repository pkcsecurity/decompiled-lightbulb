package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Rectangle;
import java.util.ArrayList;

public class WidgetContainer extends ConstraintWidget {

   protected ArrayList<ConstraintWidget> mChildren = new ArrayList();


   public WidgetContainer() {}

   public WidgetContainer(int var1, int var2) {
      super(var1, var2);
   }

   public WidgetContainer(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public static Rectangle getBounds(ArrayList<ConstraintWidget> var0) {
      Rectangle var11 = new Rectangle();
      if(var0.size() == 0) {
         return var11;
      } else {
         int var10 = var0.size();
         int var8 = Integer.MAX_VALUE;
         int var2 = 0;
         int var5 = Integer.MAX_VALUE;
         int var3 = 0;

         int var1;
         int var9;
         for(var1 = 0; var2 < var10; var1 = var9) {
            ConstraintWidget var12 = (ConstraintWidget)var0.get(var2);
            int var4 = var8;
            if(var12.getX() < var8) {
               var4 = var12.getX();
            }

            int var6 = var5;
            if(var12.getY() < var5) {
               var6 = var12.getY();
            }

            int var7 = var3;
            if(var12.getRight() > var3) {
               var7 = var12.getRight();
            }

            var9 = var1;
            if(var12.getBottom() > var1) {
               var9 = var12.getBottom();
            }

            ++var2;
            var8 = var4;
            var5 = var6;
            var3 = var7;
         }

         var11.setBounds(var8, var5, var3 - var8, var1 - var5);
         return var11;
      }
   }

   public void add(ConstraintWidget var1) {
      this.mChildren.add(var1);
      if(var1.getParent() != null) {
         ((WidgetContainer)var1.getParent()).remove(var1);
      }

      var1.setParent(this);
   }

   public ConstraintWidget findWidget(float var1, float var2) {
      int var3 = this.getDrawX();
      int var4 = this.getDrawY();
      int var5 = this.getWidth();
      int var6 = this.getHeight();
      Object var9;
      if(var1 >= (float)var3 && var1 <= (float)(var5 + var3) && var2 >= (float)var4 && var2 <= (float)(var6 + var4)) {
         var9 = this;
      } else {
         var9 = null;
      }

      var3 = 0;

      Object var10;
      for(var4 = this.mChildren.size(); var3 < var4; var9 = var10) {
         label37: {
            ConstraintWidget var11 = (ConstraintWidget)this.mChildren.get(var3);
            ConstraintWidget var12;
            if(var11 instanceof WidgetContainer) {
               var11 = ((WidgetContainer)var11).findWidget(var1, var2);
               var10 = var9;
               if(var11 == null) {
                  break label37;
               }

               var12 = var11;
            } else {
               var5 = var11.getDrawX();
               var6 = var11.getDrawY();
               int var7 = var11.getWidth();
               int var8 = var11.getHeight();
               var10 = var9;
               if(var1 < (float)var5) {
                  break label37;
               }

               var10 = var9;
               if(var1 > (float)(var7 + var5)) {
                  break label37;
               }

               var10 = var9;
               if(var2 < (float)var6) {
                  break label37;
               }

               var10 = var9;
               if(var2 > (float)(var8 + var6)) {
                  break label37;
               }

               var12 = var11;
            }

            var10 = var12;
         }

         ++var3;
      }

      return (ConstraintWidget)var9;
   }

   public ArrayList<ConstraintWidget> findWidgets(int var1, int var2, int var3, int var4) {
      ArrayList var5 = new ArrayList();
      Rectangle var6 = new Rectangle();
      var6.setBounds(var1, var2, var3, var4);
      var2 = this.mChildren.size();

      for(var1 = 0; var1 < var2; ++var1) {
         ConstraintWidget var7 = (ConstraintWidget)this.mChildren.get(var1);
         Rectangle var8 = new Rectangle();
         var8.setBounds(var7.getDrawX(), var7.getDrawY(), var7.getWidth(), var7.getHeight());
         if(var6.intersects(var8)) {
            var5.add(var7);
         }
      }

      return var5;
   }

   public ArrayList<ConstraintWidget> getChildren() {
      return this.mChildren;
   }

   public ConstraintWidgetContainer getRootConstraintContainer() {
      ConstraintWidget var2 = this.getParent();
      ConstraintWidgetContainer var1;
      if(this instanceof ConstraintWidgetContainer) {
         var1 = (ConstraintWidgetContainer)this;
      } else {
         var1 = null;
      }

      ConstraintWidget var3;
      for(; var2 != null; var2 = var3) {
         var3 = var2.getParent();
         if(var2 instanceof ConstraintWidgetContainer) {
            var1 = (ConstraintWidgetContainer)var2;
         }
      }

      return var1;
   }

   public void layout() {
      this.updateDrawPosition();
      if(this.mChildren != null) {
         int var2 = this.mChildren.size();

         for(int var1 = 0; var1 < var2; ++var1) {
            ConstraintWidget var3 = (ConstraintWidget)this.mChildren.get(var1);
            if(var3 instanceof WidgetContainer) {
               ((WidgetContainer)var3).layout();
            }
         }

      }
   }

   public void remove(ConstraintWidget var1) {
      this.mChildren.remove(var1);
      var1.setParent((ConstraintWidget)null);
   }

   public void removeAllChildren() {
      this.mChildren.clear();
   }

   public void reset() {
      this.mChildren.clear();
      super.reset();
   }

   public void resetGroups() {
      super.resetGroups();
      int var2 = this.mChildren.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ((ConstraintWidget)this.mChildren.get(var1)).resetGroups();
      }

   }

   public void resetSolverVariables(Cache var1) {
      super.resetSolverVariables(var1);
      int var3 = this.mChildren.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         ((ConstraintWidget)this.mChildren.get(var2)).resetSolverVariables(var1);
      }

   }

   public void setOffset(int var1, int var2) {
      super.setOffset(var1, var2);
      var2 = this.mChildren.size();

      for(var1 = 0; var1 < var2; ++var1) {
         ((ConstraintWidget)this.mChildren.get(var1)).setOffset(this.getRootX(), this.getRootY());
      }

   }

   public void updateDrawPosition() {
      super.updateDrawPosition();
      if(this.mChildren != null) {
         int var2 = this.mChildren.size();

         for(int var1 = 0; var1 < var2; ++var1) {
            ConstraintWidget var3 = (ConstraintWidget)this.mChildren.get(var1);
            var3.setOffset(this.getDrawX(), this.getDrawY());
            if(!(var3 instanceof ConstraintWidgetContainer)) {
               var3.updateDrawPosition();
            }
         }

      }
   }
}
