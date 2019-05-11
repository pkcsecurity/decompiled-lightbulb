package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.util.Preconditions;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E extends Object> extends FragmentContainer {

   @Nullable
   private final Activity mActivity;
   @NonNull
   private final Context mContext;
   final FragmentManagerImpl mFragmentManager;
   @NonNull
   private final Handler mHandler;
   private final int mWindowAnimations;


   FragmentHostCallback(@Nullable Activity var1, @NonNull Context var2, @NonNull Handler var3, int var4) {
      this.mFragmentManager = new FragmentManagerImpl();
      this.mActivity = var1;
      this.mContext = (Context)Preconditions.checkNotNull(var2, "context == null");
      this.mHandler = (Handler)Preconditions.checkNotNull(var3, "handler == null");
      this.mWindowAnimations = var4;
   }

   public FragmentHostCallback(@NonNull Context var1, @NonNull Handler var2, int var3) {
      Activity var4;
      if(var1 instanceof Activity) {
         var4 = (Activity)var1;
      } else {
         var4 = null;
      }

      this(var4, var1, var2, var3);
   }

   FragmentHostCallback(@NonNull FragmentActivity var1) {
      this(var1, var1, var1.mHandler, 0);
   }

   @Nullable
   Activity getActivity() {
      return this.mActivity;
   }

   @NonNull
   Context getContext() {
      return this.mContext;
   }

   FragmentManagerImpl getFragmentManagerImpl() {
      return this.mFragmentManager;
   }

   @NonNull
   Handler getHandler() {
      return this.mHandler;
   }

   void onAttachFragment(Fragment var1) {}

   public void onDump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {}

   @Nullable
   public View onFindViewById(int var1) {
      return null;
   }

   @Nullable
   public abstract E onGetHost();

   @NonNull
   public LayoutInflater onGetLayoutInflater() {
      return LayoutInflater.from(this.mContext);
   }

   public int onGetWindowAnimations() {
      return this.mWindowAnimations;
   }

   public boolean onHasView() {
      return true;
   }

   public boolean onHasWindowAnimations() {
      return true;
   }

   public void onRequestPermissionsFromFragment(@NonNull Fragment var1, @NonNull String[] var2, int var3) {}

   public boolean onShouldSaveFragmentState(Fragment var1) {
      return true;
   }

   public boolean onShouldShowRequestPermissionRationale(@NonNull String var1) {
      return false;
   }

   public void onStartActivityFromFragment(Fragment var1, Intent var2, int var3) {
      this.onStartActivityFromFragment(var1, var2, var3, (Bundle)null);
   }

   public void onStartActivityFromFragment(Fragment var1, Intent var2, int var3, @Nullable Bundle var4) {
      if(var3 != -1) {
         throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
      } else {
         this.mContext.startActivity(var2);
      }
   }

   public void onStartIntentSenderFromFragment(Fragment var1, IntentSender var2, int var3, @Nullable Intent var4, int var5, int var6, int var7, Bundle var8) throws SendIntentException {
      if(var3 != -1) {
         throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
      } else {
         ActivityCompat.startIntentSenderForResult(this.mActivity, var2, var3, var4, var5, var6, var7, var8);
      }
   }

   public void onSupportInvalidateOptionsMenu() {}
}
