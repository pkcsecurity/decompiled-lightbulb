package android.arch.lifecycle;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.arch.lifecycle.EmptyActivityLifecycleCallbacks;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class HolderFragment extends Fragment implements ViewModelStoreOwner {

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static final String HOLDER_TAG = "android.arch.lifecycle.state.StateProviderHolderFragment";
   private static final String LOG_TAG = "ViewModelStores";
   private static final HolderFragment.a sHolderFragmentManager = new HolderFragment.a();
   private o mViewModelStore = new o();


   public HolderFragment() {
      this.setRetainInstance(true);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static HolderFragment holderFragmentFor(Fragment var0) {
      return sHolderFragmentManager.b(var0);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public static HolderFragment holderFragmentFor(FragmentActivity var0) {
      return sHolderFragmentManager.a(var0);
   }

   @NonNull
   public o getViewModelStore() {
      return this.mViewModelStore;
   }

   public void onCreate(@Nullable Bundle var1) {
      super.onCreate(var1);
      sHolderFragmentManager.a((Fragment)this);
   }

   public void onDestroy() {
      super.onDestroy();
      this.mViewModelStore.a();
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
   }

   static class a {

      private Map<Activity, HolderFragment> a = new HashMap();
      private Map<Fragment, HolderFragment> b = new HashMap();
      private ActivityLifecycleCallbacks c = new EmptyActivityLifecycleCallbacks() {
         public void onActivityDestroyed(Activity var1) {
            if((HolderFragment)a.this.a.remove(var1) != null) {
               StringBuilder var2 = new StringBuilder();
               var2.append("Failed to save a ViewModel for ");
               var2.append(var1);
               Log.e("ViewModelStores", var2.toString());
            }

         }
      };
      private boolean d = false;
      private FragmentManager.FragmentLifecycleCallbacks e = new FragmentManager.FragmentLifecycleCallbacks() {
         public void onFragmentDestroyed(FragmentManager var1, Fragment var2) {
            super.onFragmentDestroyed(var1, var2);
            if((HolderFragment)a.this.b.remove(var2) != null) {
               StringBuilder var3 = new StringBuilder();
               var3.append("Failed to save a ViewModel for ");
               var3.append(var2);
               Log.e("ViewModelStores", var3.toString());
            }

         }
      };


      private static HolderFragment a(FragmentManager var0) {
         if(var0.isDestroyed()) {
            throw new IllegalStateException("Can\'t access ViewModels from onDestroy");
         } else {
            Fragment var1 = var0.findFragmentByTag("android.arch.lifecycle.state.StateProviderHolderFragment");
            if(var1 != null && !(var1 instanceof HolderFragment)) {
               throw new IllegalStateException("Unexpected fragment instance was returned by HOLDER_TAG");
            } else {
               return (HolderFragment)var1;
            }
         }
      }

      private static HolderFragment b(FragmentManager var0) {
         HolderFragment var1 = new HolderFragment();
         var0.beginTransaction().add(var1, "android.arch.lifecycle.state.StateProviderHolderFragment").commitAllowingStateLoss();
         return var1;
      }

      HolderFragment a(FragmentActivity var1) {
         FragmentManager var2 = var1.getSupportFragmentManager();
         HolderFragment var3 = a(var2);
         if(var3 != null) {
            return var3;
         } else {
            var3 = (HolderFragment)this.a.get(var1);
            if(var3 != null) {
               return var3;
            } else {
               if(!this.d) {
                  this.d = true;
                  var1.getApplication().registerActivityLifecycleCallbacks(this.c);
               }

               HolderFragment var4 = b(var2);
               this.a.put(var1, var4);
               return var4;
            }
         }
      }

      void a(Fragment var1) {
         Fragment var2 = var1.getParentFragment();
         if(var2 != null) {
            this.b.remove(var2);
            var2.getFragmentManager().unregisterFragmentLifecycleCallbacks(this.e);
         } else {
            this.a.remove(var1.getActivity());
         }
      }

      HolderFragment b(Fragment var1) {
         FragmentManager var2 = var1.getChildFragmentManager();
         HolderFragment var3 = a(var2);
         if(var3 != null) {
            return var3;
         } else {
            var3 = (HolderFragment)this.b.get(var1);
            if(var3 != null) {
               return var3;
            } else {
               var1.getFragmentManager().registerFragmentLifecycleCallbacks(this.e, false);
               HolderFragment var4 = b(var2);
               this.b.put(var1, var4);
               return var4;
            }
         }
      }
   }
}
