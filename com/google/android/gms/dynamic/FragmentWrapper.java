package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;

@SuppressLint({"NewApi"})
@KeepForSdk
public final class FragmentWrapper extends IFragmentWrapper.Stub {

   private Fragment a;


   private FragmentWrapper(Fragment var1) {
      this.a = var1;
   }

   @KeepForSdk
   public static FragmentWrapper a(Fragment var0) {
      return var0 != null?new FragmentWrapper(var0):null;
   }

   public final IObjectWrapper a() {
      return ObjectWrapper.a((Object)this.a.getActivity());
   }

   public final void a(Intent var1) {
      this.a.startActivity(var1);
   }

   public final void a(Intent var1, int var2) {
      this.a.startActivityForResult(var1, var2);
   }

   public final void a(IObjectWrapper var1) {
      View var2 = (View)ObjectWrapper.a(var1);
      this.a.registerForContextMenu(var2);
   }

   public final void a(boolean var1) {
      this.a.setHasOptionsMenu(var1);
   }

   public final Bundle b() {
      return this.a.getArguments();
   }

   public final void b(IObjectWrapper var1) {
      View var2 = (View)ObjectWrapper.a(var1);
      this.a.unregisterForContextMenu(var2);
   }

   public final void b(boolean var1) {
      this.a.setMenuVisibility(var1);
   }

   public final int c() {
      return this.a.getId();
   }

   public final void c(boolean var1) {
      this.a.setRetainInstance(var1);
   }

   public final IFragmentWrapper d() {
      return a(this.a.getParentFragment());
   }

   public final void d(boolean var1) {
      this.a.setUserVisibleHint(var1);
   }

   public final IObjectWrapper e() {
      return ObjectWrapper.a((Object)this.a.getResources());
   }

   public final boolean f() {
      return this.a.getRetainInstance();
   }

   public final String g() {
      return this.a.getTag();
   }

   public final IFragmentWrapper h() {
      return a(this.a.getTargetFragment());
   }

   public final int i() {
      return this.a.getTargetRequestCode();
   }

   public final boolean j() {
      return this.a.getUserVisibleHint();
   }

   public final IObjectWrapper k() {
      return ObjectWrapper.a((Object)this.a.getView());
   }

   public final boolean l() {
      return this.a.isAdded();
   }

   public final boolean m() {
      return this.a.isDetached();
   }

   public final boolean n() {
      return this.a.isHidden();
   }

   public final boolean o() {
      return this.a.isInLayout();
   }

   public final boolean p() {
      return this.a.isRemoving();
   }

   public final boolean q() {
      return this.a.isResumed();
   }

   public final boolean r() {
      return this.a.isVisible();
   }
}
