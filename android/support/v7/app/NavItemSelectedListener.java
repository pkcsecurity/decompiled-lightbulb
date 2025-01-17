package android.support.v7.app;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

class NavItemSelectedListener implements OnItemSelectedListener {

   private final ActionBar.OnNavigationListener mListener;


   public NavItemSelectedListener(ActionBar.OnNavigationListener var1) {
      this.mListener = var1;
   }

   public void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4) {
      if(this.mListener != null) {
         this.mListener.onNavigationItemSelected(var3, var4);
      }

   }

   public void onNothingSelected(AdapterView<?> var1) {}
}
