package com.facebook.react.modules.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import com.facebook.react.modules.dialog.DialogModule;
import javax.annotation.Nullable;

public class AlertFragment extends DialogFragment implements OnClickListener {

   static final String ARG_BUTTON_NEGATIVE = "button_negative";
   static final String ARG_BUTTON_NEUTRAL = "button_neutral";
   static final String ARG_BUTTON_POSITIVE = "button_positive";
   static final String ARG_ITEMS = "items";
   static final String ARG_MESSAGE = "message";
   static final String ARG_TITLE = "title";
   @Nullable
   private final DialogModule.AlertFragmentListener mListener;


   public AlertFragment() {
      this.mListener = null;
   }

   public AlertFragment(@Nullable DialogModule.AlertFragmentListener var1, Bundle var2) {
      this.mListener = var1;
      this.setArguments(var2);
   }

   public static Dialog createDialog(Context var0, Bundle var1, OnClickListener var2) {
      Builder var3 = (new Builder(var0)).setTitle(var1.getString("title"));
      if(var1.containsKey("button_positive")) {
         var3.setPositiveButton(var1.getString("button_positive"), var2);
      }

      if(var1.containsKey("button_negative")) {
         var3.setNegativeButton(var1.getString("button_negative"), var2);
      }

      if(var1.containsKey("button_neutral")) {
         var3.setNeutralButton(var1.getString("button_neutral"), var2);
      }

      if(var1.containsKey("message")) {
         var3.setMessage(var1.getString("message"));
      }

      if(var1.containsKey("items")) {
         var3.setItems(var1.getCharSequenceArray("items"), var2);
      }

      return var3.create();
   }

   public void onClick(DialogInterface var1, int var2) {
      if(this.mListener != null) {
         this.mListener.onClick(var1, var2);
      }

   }

   public Dialog onCreateDialog(Bundle var1) {
      return createDialog(this.getActivity(), this.getArguments(), this);
   }

   public void onDismiss(DialogInterface var1) {
      super.onDismiss(var1);
      if(this.mListener != null) {
         this.mListener.onDismiss(var1);
      }

   }
}
