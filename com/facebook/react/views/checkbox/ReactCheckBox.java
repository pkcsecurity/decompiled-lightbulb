package com.facebook.react.views.checkbox;

import android.content.Context;
import android.widget.CheckBox;

class ReactCheckBox extends CheckBox {

   private boolean mAllowChange = true;


   public ReactCheckBox(Context var1) {
      super(var1);
   }

   public void setChecked(boolean var1) {
      if(this.mAllowChange) {
         this.mAllowChange = false;
         super.setChecked(var1);
      }

   }

   void setOn(boolean var1) {
      if(this.isChecked() != var1) {
         super.setChecked(var1);
      }

      this.mAllowChange = true;
   }
}
