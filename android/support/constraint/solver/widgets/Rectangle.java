package android.support.constraint.solver.widgets;


public class Rectangle {

   public int height;
   public int width;
   public int x;
   public int y;


   public boolean contains(int var1, int var2) {
      return var1 >= this.x && var1 < this.x + this.width && var2 >= this.y && var2 < this.y + this.height;
   }

   public int getCenterX() {
      return (this.x + this.width) / 2;
   }

   public int getCenterY() {
      return (this.y + this.height) / 2;
   }

   void grow(int var1, int var2) {
      this.x -= var1;
      this.y -= var2;
      this.width += var1 * 2;
      this.height += var2 * 2;
   }

   boolean intersects(Rectangle var1) {
      return this.x >= var1.x && this.x < var1.x + var1.width && this.y >= var1.y && this.y < var1.y + var1.height;
   }

   public void setBounds(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }
}
