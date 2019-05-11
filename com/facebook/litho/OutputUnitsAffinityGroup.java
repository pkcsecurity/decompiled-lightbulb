package com.facebook.litho;

import javax.annotation.Nullable;

public class OutputUnitsAffinityGroup<T extends Object> {

   private final Object[] mContent = new Object[5];
   private short mSize;


   public OutputUnitsAffinityGroup() {
      this.mSize = 0;
   }

   public OutputUnitsAffinityGroup(OutputUnitsAffinityGroup<T> var1) {
      int var2 = 0;
      this.mSize = 0;

      for(int var3 = this.mContent.length; var2 < var3; ++var2) {
         this.mContent[var2] = var1.mContent[var2];
      }

      this.mSize = var1.mSize;
   }

   @Nullable
   private static String typeToString(int var0) {
      switch(var0) {
      case 0:
         return "CONTENT";
      case 1:
         return "BACKGROUND";
      case 2:
         return "FOREGROUND";
      case 3:
         return "HOST";
      case 4:
         return "BORDER";
      default:
         return null;
      }
   }

   public void add(int var1, T var2) {
      if(var2 == null) {
         throw new IllegalArgumentException("value should not be null");
      } else if(this.mContent[var1] != null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Already contains unit for type ");
         var3.append(typeToString(var1));
         throw new RuntimeException(var3.toString());
      } else if(this.mContent[3] == null && (var1 != 3 || this.mSize <= 0)) {
         this.mContent[var1] = var2;
         ++this.mSize;
      } else {
         throw new RuntimeException("OutputUnitType.HOST unit should be the only member of an OutputUnitsAffinityGroup");
      }
   }

   public void clean() {
      for(int var1 = 0; var1 < this.mContent.length; ++var1) {
         this.mContent[var1] = null;
      }

      this.mSize = 0;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            OutputUnitsAffinityGroup var3 = (OutputUnitsAffinityGroup)var1;
            if(this.mSize != var3.mSize) {
               return false;
            } else {
               for(int var2 = 0; var2 < this.mContent.length; ++var2) {
                  if(this.mContent[var2] != var3.mContent[var2]) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public T get(int var1) {
      return this.mContent[var1];
   }

   public T getAt(int var1) {
      return this.get(this.typeAt(var1));
   }

   public T getMostSignificantUnit() {
      return this.mContent[3] != null?this.get(3):(this.mContent[0] != null?this.get(0):(this.mContent[1] != null?this.get(1):(this.mContent[2] != null?this.get(2):this.get(4))));
   }

   public boolean isEmpty() {
      return this.mSize == 0;
   }

   public void replace(int var1, T var2) {
      if(var2 != null && this.mContent[var1] != null) {
         this.mContent[var1] = var2;
      } else if(var2 != null && this.mContent[var1] == null) {
         this.add(var1, var2);
      } else {
         if(var2 == null && this.mContent[var1] != null) {
            this.mContent[var1] = null;
            --this.mSize;
         }

      }
   }

   public int size() {
      return this.mSize;
   }

   public String toString() {
      StringBuilder var3 = new StringBuilder(super.toString());

      for(int var1 = 0; var1 < this.size(); ++var1) {
         int var2 = this.typeAt(var1);
         Object var4 = this.getAt(var1);
         var3.append("\n\t");
         var3.append(typeToString(var2));
         var3.append(": ");
         var3.append(var4.toString());
      }

      return var3.toString();
   }

   public int typeAt(int var1) {
      if(var1 >= 0 && var1 < this.mSize) {
         int var3 = 0;

         int var2;
         int var4;
         for(var2 = 0; var3 <= var1; var3 = var4) {
            var4 = var3;
            if(this.mContent[var2] != null) {
               var4 = var3 + 1;
            }

            ++var2;
         }

         return var2 - 1;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("index=");
         var5.append(var1);
         var5.append(", size=");
         var5.append(this.mSize);
         throw new IndexOutOfBoundsException(var5.toString());
      }
   }
}
