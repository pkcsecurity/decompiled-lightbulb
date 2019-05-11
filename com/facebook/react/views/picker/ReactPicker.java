package com.facebook.react.views.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.facebook.react.common.annotations.VisibleForTesting;
import javax.annotation.Nullable;

public class ReactPicker extends Spinner {

   private int mMode = 0;
   @Nullable
   private ReactPicker.OnSelectListener mOnSelectListener;
   @Nullable
   private Integer mPrimaryColor;
   @Nullable
   private Integer mStagedSelection;
   private boolean mSuppressNextEvent;
   private final Runnable measureAndLayout = new Runnable() {
      public void run() {
         ReactPicker.this.measure(MeasureSpec.makeMeasureSpec(ReactPicker.this.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(ReactPicker.this.getHeight(), 1073741824));
         ReactPicker.this.layout(ReactPicker.this.getLeft(), ReactPicker.this.getTop(), ReactPicker.this.getRight(), ReactPicker.this.getBottom());
      }
   };


   public ReactPicker(Context var1) {
      super(var1);
   }

   public ReactPicker(Context var1, int var2) {
      super(var1, var2);
      this.mMode = var2;
   }

   public ReactPicker(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public ReactPicker(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public ReactPicker(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.mMode = var4;
   }

   private void setSelectionWithSuppressEvent(int var1) {
      if(var1 != this.getSelectedItemPosition()) {
         this.mSuppressNextEvent = true;
         this.setSelection(var1);
      }

   }

   @VisibleForTesting
   public int getMode() {
      return this.mMode;
   }

   @Nullable
   public ReactPicker.OnSelectListener getOnSelectListener() {
      return this.mOnSelectListener;
   }

   @Nullable
   public Integer getPrimaryColor() {
      return this.mPrimaryColor;
   }

   public void requestLayout() {
      super.requestLayout();
      this.post(this.measureAndLayout);
   }

   public void setOnSelectListener(@Nullable ReactPicker.OnSelectListener var1) {
      if(this.getOnItemSelectedListener() == null) {
         this.mSuppressNextEvent = true;
         this.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4) {
               if(!ReactPicker.this.mSuppressNextEvent && ReactPicker.this.mOnSelectListener != null) {
                  ReactPicker.this.mOnSelectListener.onItemSelected(var3);
               }

               ReactPicker.this.mSuppressNextEvent = false;
            }
            public void onNothingSelected(AdapterView<?> var1) {
               if(!ReactPicker.this.mSuppressNextEvent && ReactPicker.this.mOnSelectListener != null) {
                  ReactPicker.this.mOnSelectListener.onItemSelected(-1);
               }

               ReactPicker.this.mSuppressNextEvent = false;
            }
         });
      }

      this.mOnSelectListener = var1;
   }

   public void setPrimaryColor(@Nullable Integer var1) {
      this.mPrimaryColor = var1;
   }

   public void setStagedSelection(int var1) {
      this.mStagedSelection = Integer.valueOf(var1);
   }

   public void updateStagedSelection() {
      if(this.mStagedSelection != null) {
         this.setSelectionWithSuppressEvent(this.mStagedSelection.intValue());
         this.mStagedSelection = null;
      }

   }

   public interface OnSelectListener {

      void onItemSelected(int var1);
   }
}
