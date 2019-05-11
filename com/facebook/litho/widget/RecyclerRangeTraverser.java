package com.facebook.litho.widget;


public interface RecyclerRangeTraverser {

   RecyclerRangeTraverser BACKWARD_TRAVERSER = new RecyclerRangeTraverser() {
      public void traverse(int var1, int var2, int var3, int var4, RecyclerRangeTraverser.Processor var5) {
         --var2;

         while(var2 >= var1) {
            if(!var5.process(var2)) {
               return;
            }

            --var2;
         }

      }
   };
   RecyclerRangeTraverser BIDIRECTIONAL_TRAVERSER = new RecyclerRangeTraverser() {
      public void traverse(int var1, int var2, int var3, int var4, RecyclerRangeTraverser.Processor var5) {
         if(var2 > var1) {
            boolean var6;
            if(var1 <= var3 && var3 < var2) {
               var6 = true;
            } else {
               var6 = false;
            }

            boolean var7;
            if(var1 <= var4 && var4 < var2) {
               var7 = true;
            } else {
               var7 = false;
            }

            if(!var6 && !var7) {
               var3 = (var2 + var1 - 1) / 2;
            } else if(!var6) {
               var3 = var4;
            } else if(var7) {
               var3 = (var3 + var4) / 2;
            }

            if(var5.process(var3)) {
               var4 = 1;

               while(true) {
                  int var8 = var3 - var4;
                  int var9 = var3 + var4;
                  if(var8 >= var1) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  if(var9 < var2) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if(!var6 && !var7) {
                     return;
                  }

                  if(var6 && !var5.process(var8)) {
                     return;
                  }

                  if(var7 && !var5.process(var9)) {
                     return;
                  }

                  ++var4;
               }
            }
         }
      }
   };
   RecyclerRangeTraverser FORWARD_TRAVERSER = new RecyclerRangeTraverser() {
      public void traverse(int var1, int var2, int var3, int var4, RecyclerRangeTraverser.Processor var5) {
         while(var1 < var2) {
            if(!var5.process(var1)) {
               return;
            }

            ++var1;
         }

      }
   };


   void traverse(int var1, int var2, int var3, int var4, RecyclerRangeTraverser.Processor var5);

   public interface Processor {

      boolean process(int var1);
   }
}
