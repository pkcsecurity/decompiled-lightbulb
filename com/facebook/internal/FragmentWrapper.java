package com.facebook.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import com.facebook.internal.Validate;

public class FragmentWrapper {

   private Fragment nativeFragment;
   private android.support.v4.app.Fragment supportFragment;


   public FragmentWrapper(Fragment var1) {
      Validate.notNull(var1, "fragment");
      this.nativeFragment = var1;
   }

   public FragmentWrapper(android.support.v4.app.Fragment var1) {
      Validate.notNull(var1, "fragment");
      this.supportFragment = var1;
   }

   public final Activity getActivity() {
      return (Activity)(this.supportFragment != null?this.supportFragment.getActivity():this.nativeFragment.getActivity());
   }

   public Fragment getNativeFragment() {
      return this.nativeFragment;
   }

   public android.support.v4.app.Fragment getSupportFragment() {
      return this.supportFragment;
   }

   public void startActivityForResult(Intent var1, int var2) {
      if(this.supportFragment != null) {
         this.supportFragment.startActivityForResult(var1, var2);
      } else {
         this.nativeFragment.startActivityForResult(var1, var2);
      }
   }
}
