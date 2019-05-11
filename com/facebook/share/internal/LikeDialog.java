package com.facebook.share.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.internal.LikeDialogFeature;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class LikeDialog extends FacebookDialogBase<LikeContent, LikeDialog.Result> {

   private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode();
   private static final String TAG = "LikeDialog";


   @Deprecated
   public LikeDialog(Activity var1) {
      super(var1, DEFAULT_REQUEST_CODE);
   }

   @Deprecated
   public LikeDialog(Fragment var1) {
      this(new FragmentWrapper(var1));
   }

   @Deprecated
   public LikeDialog(android.support.v4.app.Fragment var1) {
      this(new FragmentWrapper(var1));
   }

   @Deprecated
   public LikeDialog(FragmentWrapper var1) {
      super(var1, DEFAULT_REQUEST_CODE);
   }

   @Deprecated
   public static boolean canShowNativeDialog() {
      return false;
   }

   @Deprecated
   public static boolean canShowWebFallback() {
      return false;
   }

   private static Bundle createParameters(LikeContent var0) {
      Bundle var1 = new Bundle();
      var1.putString("object_id", var0.getObjectId());
      var1.putString("object_type", var0.getObjectType());
      return var1;
   }

   private static DialogFeature getFeature() {
      return LikeDialogFeature.LIKE_DIALOG;
   }

   protected AppCall createBaseAppCall() {
      return new AppCall(this.getRequestCode());
   }

   protected List<FacebookDialogBase.ModeHandler> getOrderedModeHandlers() {
      ArrayList var1 = new ArrayList();
      var1.add(new LikeDialog.NativeHandler(null));
      var1.add(new LikeDialog.WebFallbackHandler(null));
      return var1;
   }

   protected void registerCallbackImpl(CallbackManagerImpl var1, final FacebookCallback<LikeDialog.Result> var2) {
      final ResultProcessor var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = new ResultProcessor(var2) {
            public void onSuccess(AppCall var1, Bundle var2x) {
               var2.onSuccess(new LikeDialog.Result(var2x));
            }
         };
      }

      CallbackManagerImpl.Callback var4 = new CallbackManagerImpl.Callback() {
         public boolean onActivityResult(int var1, Intent var2) {
            return ShareInternalUtility.handleActivityResult(LikeDialog.this.getRequestCode(), var1, var2, var3);
         }
      };
      var1.registerCallback(this.getRequestCode(), var4);
   }

   @Deprecated
   public void show(LikeContent var1) {}

   class WebFallbackHandler extends FacebookDialogBase.ModeHandler {

      private WebFallbackHandler() {
         super();
      }

      // $FF: synthetic method
      WebFallbackHandler(Object var2) {
         this();
      }

      public boolean canShow(LikeContent var1, boolean var2) {
         return false;
      }

      public AppCall createAppCall(LikeContent var1) {
         AppCall var2 = LikeDialog.this.createBaseAppCall();
         DialogPresenter.setupAppCallForWebFallbackDialog(var2, LikeDialog.createParameters(var1), LikeDialog.getFeature());
         return var2;
      }
   }

   @Deprecated
   public static final class Result {

      private final Bundle bundle;


      public Result(Bundle var1) {
         this.bundle = var1;
      }

      public Bundle getData() {
         return this.bundle;
      }
   }

   class NativeHandler extends FacebookDialogBase.ModeHandler {

      private NativeHandler() {
         super();
      }

      // $FF: synthetic method
      NativeHandler(Object var2) {
         this();
      }

      public boolean canShow(LikeContent var1, boolean var2) {
         return false;
      }

      public AppCall createAppCall(final LikeContent var1) {
         AppCall var2 = LikeDialog.this.createBaseAppCall();
         DialogPresenter.setupAppCallForNativeDialog(var2, new DialogPresenter.ParameterProvider() {
            public Bundle getLegacyParameters() {
               Log.e("LikeDialog", "Attempting to present the Like Dialog with an outdated Facebook app on the device");
               return new Bundle();
            }
            public Bundle getParameters() {
               return LikeDialog.createParameters(var1);
            }
         }, LikeDialog.getFeature());
         return var2;
      }
   }
}
