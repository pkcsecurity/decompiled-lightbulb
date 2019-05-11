package com.facebook.internal;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Iterator;
import java.util.List;

public abstract class FacebookDialogBase<CONTENT extends Object, RESULT extends Object> implements FacebookDialog<CONTENT, RESULT> {

   protected static final Object BASE_AUTOMATIC_MODE = new Object();
   private static final String TAG = "FacebookDialog";
   private final Activity activity;
   private final FragmentWrapper fragmentWrapper;
   private List<FacebookDialogBase.ModeHandler> modeHandlers;
   private int requestCode;


   protected FacebookDialogBase(Activity var1, int var2) {
      Validate.notNull(var1, "activity");
      this.activity = var1;
      this.fragmentWrapper = null;
      this.requestCode = var2;
   }

   protected FacebookDialogBase(FragmentWrapper var1, int var2) {
      Validate.notNull(var1, "fragmentWrapper");
      this.fragmentWrapper = var1;
      this.activity = null;
      this.requestCode = var2;
      if(var1.getActivity() == null) {
         throw new IllegalArgumentException("Cannot use a fragment that is not attached to an activity");
      }
   }

   private List<FacebookDialogBase.ModeHandler> cachedModeHandlers() {
      if(this.modeHandlers == null) {
         this.modeHandlers = this.getOrderedModeHandlers();
      }

      return this.modeHandlers;
   }

   private AppCall createAppCallForMode(CONTENT var1, Object var2) {
      boolean var3;
      if(var2 == BASE_AUTOMATIC_MODE) {
         var3 = true;
      } else {
         var3 = false;
      }

      Object var5 = null;
      Iterator var6 = this.cachedModeHandlers().iterator();

      AppCall var4;
      while(true) {
         var4 = (AppCall)var5;
         if(!var6.hasNext()) {
            break;
         }

         FacebookDialogBase.ModeHandler var9 = (FacebookDialogBase.ModeHandler)var6.next();
         if((var3 || Utility.areObjectsEqual(var9.getMode(), var2)) && var9.canShow(var1, true)) {
            try {
               var4 = var9.createAppCall(var1);
            } catch (FacebookException var7) {
               var4 = this.createBaseAppCall();
               DialogPresenter.setupAppCallForValidationError(var4, var7);
            }
            break;
         }
      }

      AppCall var8 = var4;
      if(var4 == null) {
         var8 = this.createBaseAppCall();
         DialogPresenter.setupAppCallForCannotShowError(var8);
      }

      return var8;
   }

   public boolean canShow(CONTENT var1) {
      return this.canShowImpl(var1, BASE_AUTOMATIC_MODE);
   }

   protected boolean canShowImpl(CONTENT var1, Object var2) {
      boolean var3;
      if(var2 == BASE_AUTOMATIC_MODE) {
         var3 = true;
      } else {
         var3 = false;
      }

      Iterator var4 = this.cachedModeHandlers().iterator();

      FacebookDialogBase.ModeHandler var5;
      do {
         if(!var4.hasNext()) {
            return false;
         }

         var5 = (FacebookDialogBase.ModeHandler)var4.next();
      } while(!var3 && !Utility.areObjectsEqual(var5.getMode(), var2) || !var5.canShow(var1, false));

      return true;
   }

   public abstract AppCall createBaseAppCall();

   protected Activity getActivityContext() {
      return this.activity != null?this.activity:(this.fragmentWrapper != null?this.fragmentWrapper.getActivity():null);
   }

   public abstract List<FacebookDialogBase.ModeHandler> getOrderedModeHandlers();

   public int getRequestCode() {
      return this.requestCode;
   }

   public final void registerCallback(CallbackManager var1, FacebookCallback<RESULT> var2) {
      if(!(var1 instanceof CallbackManagerImpl)) {
         throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
      } else {
         this.registerCallbackImpl((CallbackManagerImpl)var1, var2);
      }
   }

   public final void registerCallback(CallbackManager var1, FacebookCallback<RESULT> var2, int var3) {
      this.setRequestCode(var3);
      this.registerCallback(var1, var2);
   }

   public abstract void registerCallbackImpl(CallbackManagerImpl var1, FacebookCallback<RESULT> var2);

   protected void setRequestCode(int var1) {
      if(FacebookSdk.isFacebookRequestCode(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Request code ");
         var2.append(var1);
         var2.append(" cannot be within the range reserved by the Facebook SDK.");
         throw new IllegalArgumentException(var2.toString());
      } else {
         this.requestCode = var1;
      }
   }

   public void show(CONTENT var1) {
      this.showImpl(var1, BASE_AUTOMATIC_MODE);
   }

   protected void showImpl(CONTENT var1, Object var2) {
      AppCall var3 = this.createAppCallForMode(var1, var2);
      if(var3 != null) {
         if(this.fragmentWrapper != null) {
            DialogPresenter.present(var3, this.fragmentWrapper);
         } else {
            DialogPresenter.present(var3, this.activity);
         }
      } else {
         Log.e("FacebookDialog", "No code path should ever result in a null appCall");
         if(FacebookSdk.isDebugEnabled()) {
            throw new IllegalStateException("No code path should ever result in a null appCall");
         }
      }
   }

   protected void startActivityForResult(Intent var1, int var2) {
      String var3;
      label26: {
         if(this.activity != null) {
            this.activity.startActivityForResult(var1, var2);
         } else {
            if(this.fragmentWrapper == null) {
               var3 = "Failed to find Activity or Fragment to startActivityForResult ";
               break label26;
            }

            if(this.fragmentWrapper.getNativeFragment() != null) {
               this.fragmentWrapper.getNativeFragment().startActivityForResult(var1, var2);
            } else {
               if(this.fragmentWrapper.getSupportFragment() == null) {
                  var3 = "Failed to find Activity or Fragment to startActivityForResult ";
                  break label26;
               }

               this.fragmentWrapper.getSupportFragment().startActivityForResult(var1, var2);
            }
         }

         var3 = null;
      }

      if(var3 != null) {
         Logger.log(LoggingBehavior.DEVELOPER_ERRORS, 6, this.getClass().getName(), var3);
      }

   }

   public abstract class ModeHandler {

      public abstract boolean canShow(CONTENT var1, boolean var2);

      public abstract AppCall createAppCall(CONTENT var1);

      public Object getMode() {
         return FacebookDialogBase.BASE_AUTOMATIC_MODE;
      }
   }
}
