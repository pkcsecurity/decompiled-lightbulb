package com.facebook.react.views.modal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.R;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.view.ReactViewGroup;
import java.util.ArrayList;
import javax.annotation.Nullable;

public class ReactModalHostView extends ViewGroup implements LifecycleEventListener {

   private String mAnimationType;
   @Nullable
   private Dialog mDialog;
   private boolean mHardwareAccelerated;
   private ReactModalHostView.DialogRootViewGroup mHostView;
   @Nullable
   private ReactModalHostView.OnRequestCloseListener mOnRequestCloseListener;
   @Nullable
   private OnShowListener mOnShowListener;
   private boolean mPropertyRequiresNewDialog;
   private boolean mTransparent;


   public ReactModalHostView(Context var1) {
      super(var1);
      ((ReactContext)var1).addLifecycleEventListener(this);
      this.mHostView = new ReactModalHostView.DialogRootViewGroup(var1);
   }

   private void dismiss() {
      if(this.mDialog != null) {
         this.mDialog.dismiss();
         this.mDialog = null;
         ((ViewGroup)this.mHostView.getParent()).removeViewAt(0);
      }

   }

   private View getContentView() {
      FrameLayout var1 = new FrameLayout(this.getContext());
      var1.addView(this.mHostView);
      var1.setFitsSystemWindows(true);
      return var1;
   }

   private void updateProperties() {
      Assertions.assertNotNull(this.mDialog, "mDialog must exist when we call updateProperties");
      if(this.mTransparent) {
         this.mDialog.getWindow().clearFlags(2);
      } else {
         this.mDialog.getWindow().setDimAmount(0.5F);
         this.mDialog.getWindow().setFlags(2, 2);
      }
   }

   public void addChildrenForAccessibility(ArrayList<View> var1) {}

   public void addView(View var1, int var2) {
      this.mHostView.addView(var1, var2);
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      return false;
   }

   public View getChildAt(int var1) {
      return this.mHostView.getChildAt(var1);
   }

   public int getChildCount() {
      return this.mHostView.getChildCount();
   }

   @Nullable
   @VisibleForTesting
   public Dialog getDialog() {
      return this.mDialog;
   }

   public void onDropInstance() {
      ((ReactContext)this.getContext()).removeLifecycleEventListener(this);
      this.dismiss();
   }

   public void onHostDestroy() {
      this.onDropInstance();
   }

   public void onHostPause() {
      this.dismiss();
   }

   public void onHostResume() {
      this.showOrUpdate();
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {}

   public void removeView(View var1) {
      this.mHostView.removeView(var1);
   }

   public void removeViewAt(int var1) {
      View var2 = this.getChildAt(var1);
      this.mHostView.removeView(var2);
   }

   protected void setAnimationType(String var1) {
      this.mAnimationType = var1;
      this.mPropertyRequiresNewDialog = true;
   }

   protected void setHardwareAccelerated(boolean var1) {
      this.mHardwareAccelerated = var1;
      this.mPropertyRequiresNewDialog = true;
   }

   protected void setOnRequestCloseListener(ReactModalHostView.OnRequestCloseListener var1) {
      this.mOnRequestCloseListener = var1;
   }

   protected void setOnShowListener(OnShowListener var1) {
      this.mOnShowListener = var1;
   }

   protected void setTransparent(boolean var1) {
      this.mTransparent = var1;
   }

   protected void showOrUpdate() {
      if(this.mDialog != null) {
         if(!this.mPropertyRequiresNewDialog) {
            this.updateProperties();
            return;
         }

         this.dismiss();
      }

      this.mPropertyRequiresNewDialog = false;
      int var1 = R.style.Theme_FullScreenDialog;
      if(this.mAnimationType.equals("fade")) {
         var1 = R.style.Theme_FullScreenDialogAnimatedFade;
      } else if(this.mAnimationType.equals("slide")) {
         var1 = R.style.Theme_FullScreenDialogAnimatedSlide;
      }

      this.mDialog = new Dialog(this.getContext(), var1);
      this.mDialog.setContentView(this.getContentView());
      this.updateProperties();
      this.mDialog.setOnShowListener(this.mOnShowListener);
      this.mDialog.setOnKeyListener(new OnKeyListener() {
         public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
            if(var3.getAction() == 1) {
               if(var2 == 4) {
                  Assertions.assertNotNull(ReactModalHostView.this.mOnRequestCloseListener, "setOnRequestCloseListener must be called by the manager");
                  ReactModalHostView.this.mOnRequestCloseListener.onRequestClose(var1);
                  return true;
               }

               Activity var4 = ((ReactContext)ReactModalHostView.this.getContext()).getCurrentActivity();
               if(var4 != null) {
                  return var4.onKeyUp(var2, var3);
               }
            }

            return false;
         }
      });
      this.mDialog.getWindow().setSoftInputMode(16);
      if(this.mHardwareAccelerated) {
         this.mDialog.getWindow().addFlags(16777216);
      }

      this.mDialog.show();
   }

   static class DialogRootViewGroup extends ReactViewGroup implements RootView {

      private final JSTouchDispatcher mJSTouchDispatcher = new JSTouchDispatcher(this);


      public DialogRootViewGroup(Context var1) {
         super(var1);
      }

      private EventDispatcher getEventDispatcher() {
         return ((UIManagerModule)((ReactContext)this.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher();
      }

      public void onChildStartedNativeGesture(MotionEvent var1) {
         this.mJSTouchDispatcher.onChildStartedNativeGesture(var1, this.getEventDispatcher());
      }

      public boolean onInterceptTouchEvent(MotionEvent var1) {
         this.mJSTouchDispatcher.handleTouchEvent(var1, this.getEventDispatcher());
         return super.onInterceptTouchEvent(var1);
      }

      protected void onSizeChanged(final int var1, final int var2, final int var3, int var4) {
         super.onSizeChanged(var1, var2, var3, var4);
         if(this.getChildCount() > 0) {
            var3 = this.getChildAt(0).getId();
            final ReactContext var5 = (ReactContext)this.getContext();
            var5.runUIBackgroundRunnable(new GuardedRunnable(var5) {
               public void runGuarded() {
                  ((UIManagerModule)((ReactContext)DialogRootViewGroup.this.getContext()).getNativeModule(UIManagerModule.class)).updateNodeSize(var3, var1, var2);
               }
            });
         }

      }

      public boolean onTouchEvent(MotionEvent var1) {
         this.mJSTouchDispatcher.handleTouchEvent(var1, this.getEventDispatcher());
         super.onTouchEvent(var1);
         return true;
      }

      public void requestDisallowInterceptTouchEvent(boolean var1) {}
   }

   public interface OnRequestCloseListener {

      void onRequestClose(DialogInterface var1);
   }
}
