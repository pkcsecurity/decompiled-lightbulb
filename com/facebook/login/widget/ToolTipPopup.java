package com.facebook.login.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.facebook.login.R;
import java.lang.ref.WeakReference;

public class ToolTipPopup {

   public static final long DEFAULT_POPUP_DISPLAY_TIME = 6000L;
   private final WeakReference<View> mAnchorViewRef;
   private final Context mContext;
   private long mNuxDisplayTime;
   private ToolTipPopup.PopupContentView mPopupContent;
   private PopupWindow mPopupWindow;
   private final OnScrollChangedListener mScrollListener;
   private ToolTipPopup.Style mStyle;
   private final String mText;


   public ToolTipPopup(String var1, View var2) {
      this.mStyle = ToolTipPopup.Style.BLUE;
      this.mNuxDisplayTime = 6000L;
      this.mScrollListener = new OnScrollChangedListener() {
         public void onScrollChanged() {
            if(ToolTipPopup.this.mAnchorViewRef.get() != null && ToolTipPopup.this.mPopupWindow != null && ToolTipPopup.this.mPopupWindow.isShowing()) {
               if(ToolTipPopup.this.mPopupWindow.isAboveAnchor()) {
                  ToolTipPopup.this.mPopupContent.showBottomArrow();
                  return;
               }

               ToolTipPopup.this.mPopupContent.showTopArrow();
            }

         }
      };
      this.mText = var1;
      this.mAnchorViewRef = new WeakReference(var2);
      this.mContext = var2.getContext();
   }

   private void registerObserver() {
      this.unregisterObserver();
      if(this.mAnchorViewRef.get() != null) {
         ((View)this.mAnchorViewRef.get()).getViewTreeObserver().addOnScrollChangedListener(this.mScrollListener);
      }

   }

   private void unregisterObserver() {
      if(this.mAnchorViewRef.get() != null) {
         ((View)this.mAnchorViewRef.get()).getViewTreeObserver().removeOnScrollChangedListener(this.mScrollListener);
      }

   }

   private void updateArrows() {
      if(this.mPopupWindow != null && this.mPopupWindow.isShowing()) {
         if(this.mPopupWindow.isAboveAnchor()) {
            this.mPopupContent.showBottomArrow();
            return;
         }

         this.mPopupContent.showTopArrow();
      }

   }

   public void dismiss() {
      this.unregisterObserver();
      if(this.mPopupWindow != null) {
         this.mPopupWindow.dismiss();
      }

   }

   public void setNuxDisplayTime(long var1) {
      this.mNuxDisplayTime = var1;
   }

   public void setStyle(ToolTipPopup.Style var1) {
      this.mStyle = var1;
   }

   public void show() {
      if(this.mAnchorViewRef.get() != null) {
         this.mPopupContent = new ToolTipPopup.PopupContentView(this.mContext);
         ((TextView)this.mPopupContent.findViewById(R.id.com_facebook_tooltip_bubble_view_text_body)).setText(this.mText);
         if(this.mStyle == ToolTipPopup.Style.BLUE) {
            this.mPopupContent.bodyFrame.setBackgroundResource(R.drawable.com_facebook_tooltip_blue_background);
            this.mPopupContent.bottomArrow.setImageResource(R.drawable.com_facebook_tooltip_blue_bottomnub);
            this.mPopupContent.topArrow.setImageResource(R.drawable.com_facebook_tooltip_blue_topnub);
            this.mPopupContent.xOut.setImageResource(R.drawable.com_facebook_tooltip_blue_xout);
         } else {
            this.mPopupContent.bodyFrame.setBackgroundResource(R.drawable.com_facebook_tooltip_black_background);
            this.mPopupContent.bottomArrow.setImageResource(R.drawable.com_facebook_tooltip_black_bottomnub);
            this.mPopupContent.topArrow.setImageResource(R.drawable.com_facebook_tooltip_black_topnub);
            this.mPopupContent.xOut.setImageResource(R.drawable.com_facebook_tooltip_black_xout);
         }

         View var3 = ((Activity)this.mContext).getWindow().getDecorView();
         int var1 = var3.getWidth();
         int var2 = var3.getHeight();
         this.registerObserver();
         this.mPopupContent.measure(MeasureSpec.makeMeasureSpec(var1, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(var2, Integer.MIN_VALUE));
         this.mPopupWindow = new PopupWindow(this.mPopupContent, this.mPopupContent.getMeasuredWidth(), this.mPopupContent.getMeasuredHeight());
         this.mPopupWindow.showAsDropDown((View)this.mAnchorViewRef.get());
         this.updateArrows();
         if(this.mNuxDisplayTime > 0L) {
            this.mPopupContent.postDelayed(new Runnable() {
               public void run() {
                  ToolTipPopup.this.dismiss();
               }
            }, this.mNuxDisplayTime);
         }

         this.mPopupWindow.setTouchable(true);
         this.mPopupContent.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               ToolTipPopup.this.dismiss();
            }
         });
      }

   }

   public static enum Style {

      // $FF: synthetic field
      private static final ToolTipPopup.Style[] $VALUES = new ToolTipPopup.Style[]{BLUE, BLACK};
      BLACK("BLACK", 1),
      BLUE("BLUE", 0);


      private Style(String var1, int var2) {}
   }

   class PopupContentView extends FrameLayout {

      private View bodyFrame;
      private ImageView bottomArrow;
      private ImageView topArrow;
      private ImageView xOut;


      public PopupContentView(Context var2) {
         super(var2);
         this.init();
      }

      private void init() {
         LayoutInflater.from(this.getContext()).inflate(R.layout.com_facebook_tooltip_bubble, this);
         this.topArrow = (ImageView)this.findViewById(R.id.com_facebook_tooltip_bubble_view_top_pointer);
         this.bottomArrow = (ImageView)this.findViewById(R.id.com_facebook_tooltip_bubble_view_bottom_pointer);
         this.bodyFrame = this.findViewById(R.id.com_facebook_body_frame);
         this.xOut = (ImageView)this.findViewById(R.id.com_facebook_button_xout);
      }

      public void showBottomArrow() {
         this.topArrow.setVisibility(4);
         this.bottomArrow.setVisibility(0);
      }

      public void showTopArrow() {
         this.topArrow.setVisibility(0);
         this.bottomArrow.setVisibility(4);
      }
   }
}
