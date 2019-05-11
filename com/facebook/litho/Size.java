package com.facebook.litho;

import com.facebook.infer.annotation.ThreadConfined;

@ThreadConfined("ANY")
public class Size {

   public int height;
   public int width;


   public Size() {
      this.width = 0;
      this.height = 0;
   }

   public Size(int var1, int var2) {
      this.width = var1;
      this.height = var2;
   }
}
