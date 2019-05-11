package com.facebook.react.views.text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.facebook.react.uimanager.ReactCompoundView;
import com.facebook.react.views.text.ReactTagSpan;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.text.TextInlineImageSpan;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import javax.annotation.Nullable;

public class ReactTextView extends TextView implements ReactCompoundView {

   private static final LayoutParams EMPTY_LAYOUT_PARAMS = new LayoutParams(0, 0);
   private boolean mContainsImages;
   private int mDefaultGravityHorizontal;
   private int mDefaultGravityVertical;
   private TruncateAt mEllipsizeLocation;
   private float mLineHeight = Float.NaN;
   private int mNumberOfLines = Integer.MAX_VALUE;
   private ReactViewBackgroundManager mReactBackgroundManager;
   private int mTextAlign = 0;
   private boolean mTextIsSelectable;


   public ReactTextView(Context var1) {
      super(var1);
      this.mEllipsizeLocation = TruncateAt.END;
      this.mReactBackgroundManager = new ReactViewBackgroundManager(this);
      this.mDefaultGravityHorizontal = this.getGravity() & 8388615;
      this.mDefaultGravityVertical = this.getGravity() & 112;
   }

   public void invalidateDrawable(Drawable var1) {
      if(this.mContainsImages && this.getText() instanceof Spanned) {
         Spanned var4 = (Spanned)this.getText();
         int var3 = var4.length();
         int var2 = 0;
         TextInlineImageSpan[] var5 = (TextInlineImageSpan[])var4.getSpans(0, var3, TextInlineImageSpan.class);

         for(var3 = var5.length; var2 < var3; ++var2) {
            if(var5[var2].getDrawable() == var1) {
               this.invalidate();
            }
         }
      }

      super.invalidateDrawable(var1);
   }

   public void onAttachedToWindow() {
      super.onAttachedToWindow();
      if(this.mContainsImages && this.getText() instanceof Spanned) {
         Spanned var3 = (Spanned)this.getText();
         int var2 = var3.length();
         int var1 = 0;
         TextInlineImageSpan[] var4 = (TextInlineImageSpan[])var3.getSpans(0, var2, TextInlineImageSpan.class);

         for(var2 = var4.length; var1 < var2; ++var1) {
            var4[var1].onAttachedToWindow();
         }
      }

   }

   public void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      if(this.mContainsImages && this.getText() instanceof Spanned) {
         Spanned var3 = (Spanned)this.getText();
         int var2 = var3.length();
         int var1 = 0;
         TextInlineImageSpan[] var4 = (TextInlineImageSpan[])var3.getSpans(0, var2, TextInlineImageSpan.class);

         for(var2 = var4.length; var1 < var2; ++var1) {
            var4[var1].onDetachedFromWindow();
         }
      }

   }

   public void onFinishTemporaryDetach() {
      super.onFinishTemporaryDetach();
      if(this.mContainsImages && this.getText() instanceof Spanned) {
         Spanned var3 = (Spanned)this.getText();
         int var2 = var3.length();
         int var1 = 0;
         TextInlineImageSpan[] var4 = (TextInlineImageSpan[])var3.getSpans(0, var2, TextInlineImageSpan.class);

         for(var2 = var4.length; var1 < var2; ++var1) {
            var4[var1].onFinishTemporaryDetach();
         }
      }

   }

   public void onStartTemporaryDetach() {
      super.onStartTemporaryDetach();
      if(this.mContainsImages && this.getText() instanceof Spanned) {
         Spanned var3 = (Spanned)this.getText();
         int var2 = var3.length();
         int var1 = 0;
         TextInlineImageSpan[] var4 = (TextInlineImageSpan[])var3.getSpans(0, var2, TextInlineImageSpan.class);

         for(var2 = var4.length; var1 < var2; ++var1) {
            var4[var1].onStartTemporaryDetach();
         }
      }

   }

   public int reactTagForTouch(float var1, float var2) {
      Spanned var11 = (Spanned)this.getText();
      int var3 = this.getId();
      int var4 = (int)var1;
      int var5 = (int)var2;
      Layout var12 = this.getLayout();
      if(var12 == null) {
         return var3;
      } else {
         var5 = var12.getLineForVertical(var5);
         int var7 = (int)var12.getLineLeft(var5);
         int var8 = (int)var12.getLineRight(var5);
         int var6 = var3;
         if(var4 >= var7) {
            var6 = var3;
            if(var4 <= var8) {
               int var9 = var12.getOffsetForHorizontal(var5, (float)var4);
               ReactTagSpan[] var13 = (ReactTagSpan[])var11.getSpans(var9, var9, ReactTagSpan.class);
               var6 = var3;
               if(var13 != null) {
                  var5 = var11.length();
                  var4 = 0;

                  while(true) {
                     var6 = var3;
                     if(var4 >= var13.length) {
                        break;
                     }

                     var8 = var11.getSpanStart(var13[var4]);
                     int var10 = var11.getSpanEnd(var13[var4]);
                     var7 = var3;
                     var6 = var5;
                     if(var10 > var9) {
                        var8 = var10 - var8;
                        var7 = var3;
                        var6 = var5;
                        if(var8 <= var5) {
                           var7 = var13[var4].getReactTag();
                           var6 = var8;
                        }
                     }

                     ++var4;
                     var3 = var7;
                     var5 = var6;
                  }
               }
            }
         }

         return var6;
      }
   }

   public void setBackgroundColor(int var1) {
      this.mReactBackgroundManager.setBackgroundColor(var1);
   }

   public void setBorderColor(int var1, float var2, float var3) {
      this.mReactBackgroundManager.setBorderColor(var1, var2, var3);
   }

   public void setBorderRadius(float var1) {
      this.mReactBackgroundManager.setBorderRadius(var1);
   }

   public void setBorderRadius(float var1, int var2) {
      this.mReactBackgroundManager.setBorderRadius(var1, var2);
   }

   public void setBorderStyle(@Nullable String var1) {
      this.mReactBackgroundManager.setBorderStyle(var1);
   }

   public void setBorderWidth(int var1, float var2) {
      this.mReactBackgroundManager.setBorderWidth(var1, var2);
   }

   public void setEllipsizeLocation(TruncateAt var1) {
      this.mEllipsizeLocation = var1;
   }

   void setGravityHorizontal(int var1) {
      int var2 = var1;
      if(var1 == 0) {
         var2 = this.mDefaultGravityHorizontal;
      }

      this.setGravity(var2 | this.getGravity() & -8 & -8388616);
   }

   void setGravityVertical(int var1) {
      int var2 = var1;
      if(var1 == 0) {
         var2 = this.mDefaultGravityVertical;
      }

      this.setGravity(var2 | this.getGravity() & -113);
   }

   public void setNumberOfLines(int var1) {
      int var2 = var1;
      if(var1 == 0) {
         var2 = Integer.MAX_VALUE;
      }

      this.mNumberOfLines = var2;
      var1 = this.mNumberOfLines;
      boolean var3 = true;
      if(var1 != 1) {
         var3 = false;
      }

      this.setSingleLine(var3);
      this.setMaxLines(this.mNumberOfLines);
   }

   public void setText(ReactTextUpdate var1) {
      this.mContainsImages = var1.containsImages();
      if(this.getLayoutParams() == null) {
         this.setLayoutParams(EMPTY_LAYOUT_PARAMS);
      }

      this.setText(var1.getText());
      this.setPadding((int)Math.floor((double)var1.getPaddingLeft()), (int)Math.floor((double)var1.getPaddingTop()), (int)Math.floor((double)var1.getPaddingRight()), (int)Math.floor((double)var1.getPaddingBottom()));
      int var2 = var1.getTextAlign();
      if(this.mTextAlign != var2) {
         this.mTextAlign = var2;
      }

      this.setGravityHorizontal(this.mTextAlign);
      if(VERSION.SDK_INT >= 23 && this.getBreakStrategy() != var1.getTextBreakStrategy()) {
         this.setBreakStrategy(var1.getTextBreakStrategy());
      }

   }

   public void setTextIsSelectable(boolean var1) {
      this.mTextIsSelectable = var1;
      super.setTextIsSelectable(var1);
   }

   public void updateView() {
      TruncateAt var1;
      if(this.mNumberOfLines == Integer.MAX_VALUE) {
         var1 = null;
      } else {
         var1 = this.mEllipsizeLocation;
      }

      this.setEllipsize(var1);
   }

   protected boolean verifyDrawable(Drawable var1) {
      if(this.mContainsImages && this.getText() instanceof Spanned) {
         Spanned var4 = (Spanned)this.getText();
         int var3 = var4.length();
         int var2 = 0;
         TextInlineImageSpan[] var5 = (TextInlineImageSpan[])var4.getSpans(0, var3, TextInlineImageSpan.class);

         for(var3 = var5.length; var2 < var3; ++var2) {
            if(var5[var2].getDrawable() == var1) {
               return true;
            }
         }
      }

      return super.verifyDrawable(var1);
   }
}
