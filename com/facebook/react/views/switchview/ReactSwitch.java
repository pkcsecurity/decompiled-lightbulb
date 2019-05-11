package com.facebook.react.views.switchview;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;

class ReactSwitch extends SwitchCompat {

   private boolean mAllowChange = true;


   public ReactSwitch(Context var1) {
      super(var1);
   }

   public void setChecked(boolean var1) {
      if(this.mAllowChange && this.isChecked() != var1) {
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
