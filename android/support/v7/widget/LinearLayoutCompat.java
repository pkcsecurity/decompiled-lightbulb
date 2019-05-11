package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {

   public static final int HORIZONTAL = 0;
   private static final int INDEX_BOTTOM = 2;
   private static final int INDEX_CENTER_VERTICAL = 0;
   private static final int INDEX_FILL = 3;
   private static final int INDEX_TOP = 1;
   public static final int SHOW_DIVIDER_BEGINNING = 1;
   public static final int SHOW_DIVIDER_END = 4;
   public static final int SHOW_DIVIDER_MIDDLE = 2;
   public static final int SHOW_DIVIDER_NONE = 0;
   public static final int VERTICAL = 1;
   private static final int VERTICAL_GRAVITY_COUNT = 4;
   private boolean mBaselineAligned;
   private int mBaselineAlignedChildIndex;
   private int mBaselineChildTop;
   private Drawable mDivider;
   private int mDividerHeight;
   private int mDividerPadding;
   private int mDividerWidth;
   private int mGravity;
   private int[] mMaxAscent;
   private int[] mMaxDescent;
   private int mOrientation;
   private int mShowDividers;
   private int mTotalLength;
   private boolean mUseLargestChild;
   private float mWeightSum;


   public LinearLayoutCompat(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public LinearLayoutCompat(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public LinearLayoutCompat(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mBaselineAligned = true;
      this.mBaselineAlignedChildIndex = -1;
      this.mBaselineChildTop = 0;
      this.mGravity = 8388659;
      TintTypedArray var5 = TintTypedArray.obtainStyledAttributes(var1, var2, R.styleable.LinearLayoutCompat, var3, 0);
      var3 = var5.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
      if(var3 >= 0) {
         this.setOrientation(var3);
      }

      var3 = var5.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
      if(var3 >= 0) {
         this.setGravity(var3);
      }

      boolean var4 = var5.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
      if(!var4) {
         this.setBaselineAligned(var4);
      }

      this.mWeightSum = var5.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0F);
      this.mBaselineAlignedChildIndex = var5.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
      this.mUseLargestChild = var5.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
      this.setDividerDrawable(var5.getDrawable(R.styleable.LinearLayoutCompat_divider));
      this.mShowDividers = var5.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
      this.mDividerPadding = var5.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
      var5.recycle();
   }

   private void forceUniformHeight(int var1, int var2) {
      int var4 = MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), 1073741824);

      for(int var3 = 0; var3 < var1; ++var3) {
         View var6 = this.getVirtualChildAt(var3);
         if(var6.getVisibility() != 8) {
            LinearLayoutCompat.LayoutParams var7 = (LinearLayoutCompat.LayoutParams)var6.getLayoutParams();
            if(var7.height == -1) {
               int var5 = var7.width;
               var7.width = var6.getMeasuredWidth();
               this.measureChildWithMargins(var6, var2, 0, var4, 0);
               var7.width = var5;
            }
         }
      }

   }

   private void forceUniformWidth(int var1, int var2) {
      int var4 = MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824);

      for(int var3 = 0; var3 < var1; ++var3) {
         View var6 = this.getVirtualChildAt(var3);
         if(var6.getVisibility() != 8) {
            LinearLayoutCompat.LayoutParams var7 = (LinearLayoutCompat.LayoutParams)var6.getLayoutParams();
            if(var7.width == -1) {
               int var5 = var7.height;
               var7.height = var6.getMeasuredHeight();
               this.measureChildWithMargins(var6, var4, 0, var2, 0);
               var7.height = var5;
            }
         }
      }

   }

   private void setChildFrame(View var1, int var2, int var3, int var4, int var5) {
      var1.layout(var2, var3, var4 + var2, var5 + var3);
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof LinearLayoutCompat.LayoutParams;
   }

   void drawDividersHorizontal(Canvas var1) {
      int var4 = this.getVirtualChildCount();
      boolean var5 = ViewUtils.isLayoutRtl(this);

      int var2;
      View var6;
      LinearLayoutCompat.LayoutParams var7;
      for(var2 = 0; var2 < var4; ++var2) {
         var6 = this.getVirtualChildAt(var2);
         if(var6 != null && var6.getVisibility() != 8 && this.hasDividerBeforeChildAt(var2)) {
            var7 = (LinearLayoutCompat.LayoutParams)var6.getLayoutParams();
            int var3;
            if(var5) {
               var3 = var6.getRight() + var7.rightMargin;
            } else {
               var3 = var6.getLeft() - var7.leftMargin - this.mDividerWidth;
            }

            this.drawVerticalDivider(var1, var3);
         }
      }

      if(this.hasDividerBeforeChildAt(var4)) {
         var6 = this.getVirtualChildAt(var4 - 1);
         if(var6 == null) {
            if(var5) {
               var2 = this.getPaddingLeft();
            } else {
               var2 = this.getWidth() - this.getPaddingRight() - this.mDividerWidth;
            }
         } else {
            var7 = (LinearLayoutCompat.LayoutParams)var6.getLayoutParams();
            if(var5) {
               var2 = var6.getLeft() - var7.leftMargin - this.mDividerWidth;
            } else {
               var2 = var6.getRight() + var7.rightMargin;
            }
         }

         this.drawVerticalDivider(var1, var2);
      }

   }

   void drawDividersVertical(Canvas var1) {
      int var3 = this.getVirtualChildCount();

      int var2;
      View var4;
      LinearLayoutCompat.LayoutParams var5;
      for(var2 = 0; var2 < var3; ++var2) {
         var4 = this.getVirtualChildAt(var2);
         if(var4 != null && var4.getVisibility() != 8 && this.hasDividerBeforeChildAt(var2)) {
            var5 = (LinearLayoutCompat.LayoutParams)var4.getLayoutParams();
            this.drawHorizontalDivider(var1, var4.getTop() - var5.topMargin - this.mDividerHeight);
         }
      }

      if(this.hasDividerBeforeChildAt(var3)) {
         var4 = this.getVirtualChildAt(var3 - 1);
         if(var4 == null) {
            var2 = this.getHeight() - this.getPaddingBottom() - this.mDividerHeight;
         } else {
            var5 = (LinearLayoutCompat.LayoutParams)var4.getLayoutParams();
            var2 = var4.getBottom() + var5.bottomMargin;
         }

         this.drawHorizontalDivider(var1, var2);
      }

   }

   void drawHorizontalDivider(Canvas var1, int var2) {
      this.mDivider.setBounds(this.getPaddingLeft() + this.mDividerPadding, var2, this.getWidth() - this.getPaddingRight() - this.mDividerPadding, this.mDividerHeight + var2);
      this.mDivider.draw(var1);
   }

   void drawVerticalDivider(Canvas var1, int var2) {
      this.mDivider.setBounds(var2, this.getPaddingTop() + this.mDividerPadding, this.mDividerWidth + var2, this.getHeight() - this.getPaddingBottom() - this.mDividerPadding);
      this.mDivider.draw(var1);
   }

   protected LinearLayoutCompat.LayoutParams generateDefaultLayoutParams() {
      return this.mOrientation == 0?new LinearLayoutCompat.LayoutParams(-2, -2):(this.mOrientation == 1?new LinearLayoutCompat.LayoutParams(-1, -2):null);
   }

   public LinearLayoutCompat.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new LinearLayoutCompat.LayoutParams(this.getContext(), var1);
   }

   protected LinearLayoutCompat.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return new LinearLayoutCompat.LayoutParams(var1);
   }

   public int getBaseline() {
      if(this.mBaselineAlignedChildIndex < 0) {
         return super.getBaseline();
      } else if(this.getChildCount() <= this.mBaselineAlignedChildIndex) {
         throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
      } else {
         View var5 = this.getChildAt(this.mBaselineAlignedChildIndex);
         int var3 = var5.getBaseline();
         if(var3 == -1) {
            if(this.mBaselineAlignedChildIndex == 0) {
               return -1;
            } else {
               throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn\'t know how to get its baseline.");
            }
         } else {
            int var2 = this.mBaselineChildTop;
            int var1 = var2;
            if(this.mOrientation == 1) {
               int var4 = this.mGravity & 112;
               var1 = var2;
               if(var4 != 48) {
                  if(var4 != 16) {
                     if(var4 != 80) {
                        var1 = var2;
                     } else {
                        var1 = this.getBottom() - this.getTop() - this.getPaddingBottom() - this.mTotalLength;
                     }
                  } else {
                     var1 = var2 + (this.getBottom() - this.getTop() - this.getPaddingTop() - this.getPaddingBottom() - this.mTotalLength) / 2;
                  }
               }
            }

            return var1 + ((LinearLayoutCompat.LayoutParams)var5.getLayoutParams()).topMargin + var3;
         }
      }
   }

   public int getBaselineAlignedChildIndex() {
      return this.mBaselineAlignedChildIndex;
   }

   int getChildrenSkipCount(View var1, int var2) {
      return 0;
   }

   public Drawable getDividerDrawable() {
      return this.mDivider;
   }

   public int getDividerPadding() {
      return this.mDividerPadding;
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public int getDividerWidth() {
      return this.mDividerWidth;
   }

   public int getGravity() {
      return this.mGravity;
   }

   int getLocationOffset(View var1) {
      return 0;
   }

   int getNextLocationOffset(View var1) {
      return 0;
   }

   public int getOrientation() {
      return this.mOrientation;
   }

   public int getShowDividers() {
      return this.mShowDividers;
   }

   View getVirtualChildAt(int var1) {
      return this.getChildAt(var1);
   }

   int getVirtualChildCount() {
      return this.getChildCount();
   }

   public float getWeightSum() {
      return this.mWeightSum;
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY})
   protected boolean hasDividerBeforeChildAt(int var1) {
      boolean var3 = false;
      boolean var2 = false;
      if(var1 == 0) {
         if((this.mShowDividers & 1) != 0) {
            var2 = true;
         }

         return var2;
      } else if(var1 == this.getChildCount()) {
         var2 = var3;
         if((this.mShowDividers & 4) != 0) {
            var2 = true;
         }

         return var2;
      } else if((this.mShowDividers & 2) != 0) {
         --var1;

         while(var1 >= 0) {
            if(this.getChildAt(var1).getVisibility() != 8) {
               return true;
            }

            --var1;
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean isBaselineAligned() {
      return this.mBaselineAligned;
   }

   public boolean isMeasureWithLargestChildEnabled() {
      return this.mUseLargestChild;
   }

   void layoutHorizontal(int var1, int var2, int var3, int var4) {
      boolean var18 = ViewUtils.isLayoutRtl(this);
      int var10 = this.getPaddingTop();
      int var12 = var4 - var2;
      int var13 = this.getPaddingBottom();
      int var14 = this.getPaddingBottom();
      int var8 = this.getVirtualChildCount();
      var4 = this.mGravity;
      var2 = this.mGravity & 112;
      boolean var19 = this.mBaselineAligned;
      int[] var20 = this.mMaxAscent;
      int[] var21 = this.mMaxDescent;
      var4 = GravityCompat.getAbsoluteGravity(var4 & 8388615, ViewCompat.getLayoutDirection(this));
      if(var4 != 1) {
         if(var4 != 5) {
            var1 = this.getPaddingLeft();
         } else {
            var1 = this.getPaddingLeft() + var3 - var1 - this.mTotalLength;
         }
      } else {
         var4 = this.getPaddingLeft();
         var1 = (var3 - var1 - this.mTotalLength) / 2 + var4;
      }

      int var6;
      byte var7;
      if(var18) {
         var6 = var8 - 1;
         var7 = -1;
      } else {
         var6 = 0;
         var7 = 1;
      }

      var4 = 0;

      for(var3 = var10; var4 < var8; ++var4) {
         int var16 = var6 + var7 * var4;
         View var22 = this.getVirtualChildAt(var16);
         if(var22 == null) {
            var1 += this.measureNullChild(var16);
         } else if(var22.getVisibility() != 8) {
            int var15 = var22.getMeasuredWidth();
            int var17 = var22.getMeasuredHeight();
            LinearLayoutCompat.LayoutParams var23 = (LinearLayoutCompat.LayoutParams)var22.getLayoutParams();
            int var9;
            if(var19 && var23.height != -1) {
               var9 = var22.getBaseline();
            } else {
               var9 = -1;
            }

            int var11 = var23.gravity;
            int var5 = var11;
            if(var11 < 0) {
               var5 = var2;
            }

            var5 &= 112;
            if(var5 != 16) {
               if(var5 != 48) {
                  if(var5 != 80) {
                     var5 = var3;
                  } else {
                     var11 = var12 - var13 - var17 - var23.bottomMargin;
                     var5 = var11;
                     if(var9 != -1) {
                        var5 = var22.getMeasuredHeight();
                        var5 = var11 - (var21[2] - (var5 - var9));
                     }
                  }
               } else {
                  var5 = var23.topMargin + var3;
                  if(var9 != -1) {
                     var5 += var20[1] - var9;
                  }
               }
            } else {
               var5 = (var12 - var10 - var14 - var17) / 2 + var3 + var23.topMargin - var23.bottomMargin;
            }

            var9 = var1;
            if(this.hasDividerBeforeChildAt(var16)) {
               var9 = var1 + this.mDividerWidth;
            }

            var1 = var23.leftMargin + var9;
            this.setChildFrame(var22, var1 + this.getLocationOffset(var22), var5, var15, var17);
            var5 = var23.rightMargin;
            var9 = this.getNextLocationOffset(var22);
            var4 += this.getChildrenSkipCount(var22, var16);
            var1 += var15 + var5 + var9;
         }
      }

   }

   void layoutVertical(int var1, int var2, int var3, int var4) {
      int var5 = this.getPaddingLeft();
      int var6 = var3 - var1;
      int var7 = this.getPaddingRight();
      int var8 = this.getPaddingRight();
      int var9 = this.getVirtualChildCount();
      var1 = this.mGravity & 112;
      int var10 = this.mGravity;
      if(var1 != 16) {
         if(var1 != 80) {
            var1 = this.getPaddingTop();
         } else {
            var1 = this.getPaddingTop() + var4 - var2 - this.mTotalLength;
         }
      } else {
         var1 = this.getPaddingTop();
         var1 += (var4 - var2 - this.mTotalLength) / 2;
      }

      for(var2 = 0; var2 < var9; var1 = var3) {
         View var13 = this.getVirtualChildAt(var2);
         if(var13 == null) {
            var3 = var1 + this.measureNullChild(var2);
            var4 = var2;
         } else {
            var3 = var1;
            var4 = var2;
            if(var13.getVisibility() != 8) {
               int var12 = var13.getMeasuredWidth();
               int var11 = var13.getMeasuredHeight();
               LinearLayoutCompat.LayoutParams var14 = (LinearLayoutCompat.LayoutParams)var13.getLayoutParams();
               var4 = var14.gravity;
               var3 = var4;
               if(var4 < 0) {
                  var3 = var10 & 8388615;
               }

               var3 = GravityCompat.getAbsoluteGravity(var3, ViewCompat.getLayoutDirection(this)) & 7;
               if(var3 != 1) {
                  if(var3 != 5) {
                     var3 = var14.leftMargin + var5;
                  } else {
                     var3 = var6 - var7 - var12 - var14.rightMargin;
                  }
               } else {
                  var3 = (var6 - var5 - var8 - var12) / 2 + var5 + var14.leftMargin - var14.rightMargin;
               }

               var4 = var1;
               if(this.hasDividerBeforeChildAt(var2)) {
                  var4 = var1 + this.mDividerHeight;
               }

               var1 = var4 + var14.topMargin;
               this.setChildFrame(var13, var3, var1 + this.getLocationOffset(var13), var12, var11);
               var3 = var14.bottomMargin;
               var12 = this.getNextLocationOffset(var13);
               var4 = var2 + this.getChildrenSkipCount(var13, var2);
               var3 = var1 + var11 + var3 + var12;
            }
         }

         var2 = var4 + 1;
      }

   }

   void measureChildBeforeLayout(View var1, int var2, int var3, int var4, int var5, int var6) {
      this.measureChildWithMargins(var1, var3, var4, var5, var6);
   }

   void measureHorizontal(int var1, int var2) {
      this.mTotalLength = 0;
      int var19 = this.getVirtualChildCount();
      int var21 = MeasureSpec.getMode(var1);
      int var20 = MeasureSpec.getMode(var2);
      if(this.mMaxAscent == null || this.mMaxDescent == null) {
         this.mMaxAscent = new int[4];
         this.mMaxDescent = new int[4];
      }

      int[] var28 = this.mMaxAscent;
      int[] var25 = this.mMaxDescent;
      var28[3] = -1;
      var28[2] = -1;
      var28[1] = -1;
      var28[0] = -1;
      var25[3] = -1;
      var25[2] = -1;
      var25[1] = -1;
      var25[0] = -1;
      boolean var23 = this.mBaselineAligned;
      boolean var24 = this.mUseLargestChild;
      boolean var15;
      if(var21 == 1073741824) {
         var15 = true;
      } else {
         var15 = false;
      }

      float var3 = 0.0F;
      int var8 = 0;
      int var7 = 0;
      int var11 = 0;
      boolean var12 = false;
      int var6 = 0;
      int var10 = 0;
      int var13 = 0;
      boolean var5 = true;

      boolean var9;
      int var14;
      int var16;
      int var17;
      View var26;
      for(var9 = false; var8 < var19; ++var8) {
         var26 = this.getVirtualChildAt(var8);
         if(var26 == null) {
            this.mTotalLength += this.measureNullChild(var8);
         } else if(var26.getVisibility() == 8) {
            var8 += this.getChildrenSkipCount(var26, var8);
         } else {
            if(this.hasDividerBeforeChildAt(var8)) {
               this.mTotalLength += this.mDividerWidth;
            }

            LinearLayoutCompat.LayoutParams var29;
            label345: {
               var29 = (LinearLayoutCompat.LayoutParams)var26.getLayoutParams();
               var3 += var29.weight;
               if(var21 == 1073741824 && var29.width == 0 && var29.weight > 0.0F) {
                  if(var15) {
                     this.mTotalLength += var29.leftMargin + var29.rightMargin;
                  } else {
                     var14 = this.mTotalLength;
                     this.mTotalLength = Math.max(var14, var29.leftMargin + var14 + var29.rightMargin);
                  }

                  if(!var23) {
                     var12 = true;
                     break label345;
                  }

                  var14 = MeasureSpec.makeMeasureSpec(0, 0);
                  var26.measure(var14, var14);
                  var14 = var7;
               } else {
                  if(var29.width == 0 && var29.weight > 0.0F) {
                     var29.width = -2;
                     var14 = 0;
                  } else {
                     var14 = Integer.MIN_VALUE;
                  }

                  if(var3 == 0.0F) {
                     var16 = this.mTotalLength;
                  } else {
                     var16 = 0;
                  }

                  this.measureChildBeforeLayout(var26, var8, var1, var16, var2, 0);
                  if(var14 != Integer.MIN_VALUE) {
                     var29.width = var14;
                  }

                  var16 = var26.getMeasuredWidth();
                  if(var15) {
                     this.mTotalLength += var29.leftMargin + var16 + var29.rightMargin + this.getNextLocationOffset(var26);
                  } else {
                     var14 = this.mTotalLength;
                     this.mTotalLength = Math.max(var14, var14 + var16 + var29.leftMargin + var29.rightMargin + this.getNextLocationOffset(var26));
                  }

                  var14 = var7;
                  if(var24) {
                     var14 = Math.max(var16, var7);
                  }
               }

               var7 = var14;
            }

            var17 = var8;
            boolean var32;
            if(var20 != 1073741824 && var29.height == -1) {
               var32 = true;
               var9 = true;
            } else {
               var32 = false;
            }

            var14 = var29.topMargin + var29.bottomMargin;
            var16 = var26.getMeasuredHeight() + var14;
            int var18 = View.combineMeasuredStates(var13, var26.getMeasuredState());
            if(var23) {
               int var22 = var26.getBaseline();
               if(var22 != -1) {
                  if(var29.gravity < 0) {
                     var13 = this.mGravity;
                  } else {
                     var13 = var29.gravity;
                  }

                  var13 = ((var13 & 112) >> 4 & -2) >> 1;
                  var28[var13] = Math.max(var28[var13], var22);
                  var25[var13] = Math.max(var25[var13], var16 - var22);
               }
            }

            var11 = Math.max(var11, var16);
            if(var5 && var29.height == -1) {
               var5 = true;
            } else {
               var5 = false;
            }

            if(var29.weight > 0.0F) {
               if(!var32) {
                  var14 = var16;
               }

               var8 = Math.max(var10, var14);
            } else {
               if(var32) {
                  var16 = var14;
               }

               var6 = Math.max(var6, var16);
               var8 = var10;
            }

            var14 = this.getChildrenSkipCount(var26, var17) + var17;
            var13 = var18;
            var10 = var8;
            var8 = var14;
         }
      }

      if(this.mTotalLength > 0 && this.hasDividerBeforeChildAt(var19)) {
         this.mTotalLength += this.mDividerWidth;
      }

      label278: {
         if(var28[1] == -1 && var28[0] == -1 && var28[2] == -1) {
            var11 = var11;
            if(var28[3] == -1) {
               break label278;
            }
         }

         var11 = Math.max(var11, Math.max(var28[3], Math.max(var28[0], Math.max(var28[1], var28[2]))) + Math.max(var25[3], Math.max(var25[0], Math.max(var25[1], var25[2]))));
      }

      LinearLayoutCompat.LayoutParams var27;
      if(var24 && (var21 == Integer.MIN_VALUE || var21 == 0)) {
         this.mTotalLength = 0;

         for(var8 = 0; var8 < var19; ++var8) {
            var26 = this.getVirtualChildAt(var8);
            if(var26 == null) {
               this.mTotalLength += this.measureNullChild(var8);
            } else if(var26.getVisibility() == 8) {
               var8 += this.getChildrenSkipCount(var26, var8);
            } else {
               var27 = (LinearLayoutCompat.LayoutParams)var26.getLayoutParams();
               if(var15) {
                  this.mTotalLength += var27.leftMargin + var7 + var27.rightMargin + this.getNextLocationOffset(var26);
               } else {
                  var14 = this.mTotalLength;
                  this.mTotalLength = Math.max(var14, var14 + var7 + var27.leftMargin + var27.rightMargin + this.getNextLocationOffset(var26));
               }
            }
         }
      }

      this.mTotalLength += this.getPaddingLeft() + this.getPaddingRight();
      var17 = View.resolveSizeAndState(Math.max(this.mTotalLength, this.getSuggestedMinimumWidth()), var1, 0);
      var14 = (16777215 & var17) - this.mTotalLength;
      boolean var31;
      if(!var12 && (var14 == 0 || var3 <= 0.0F)) {
         var8 = Math.max(var6, var10);
         if(var24 && var21 != 1073741824) {
            for(var6 = 0; var6 < var19; ++var6) {
               View var35 = this.getVirtualChildAt(var6);
               if(var35 != null && var35.getVisibility() != 8 && ((LinearLayoutCompat.LayoutParams)var35.getLayoutParams()).weight > 0.0F) {
                  var35.measure(MeasureSpec.makeMeasureSpec(var7, 1073741824), MeasureSpec.makeMeasureSpec(var35.getMeasuredHeight(), 1073741824));
               }
            }
         }

         var6 = var8;
         var31 = var5;
      } else {
         if(this.mWeightSum > 0.0F) {
            var3 = this.mWeightSum;
         }

         var28[3] = -1;
         var28[2] = -1;
         var28[1] = -1;
         var28[0] = -1;
         var25[3] = -1;
         var25[2] = -1;
         var25[1] = -1;
         var25[0] = -1;
         this.mTotalLength = 0;
         var10 = -1;
         int var33 = 0;
         var31 = var5;
         var8 = var6;
         int var30 = var13;

         for(var6 = var14; var33 < var19; ++var33) {
            var26 = this.getVirtualChildAt(var33);
            if(var26 != null && var26.getVisibility() != 8) {
               var27 = (LinearLayoutCompat.LayoutParams)var26.getLayoutParams();
               float var4 = var27.weight;
               if(var4 > 0.0F) {
                  var13 = (int)((float)var6 * var4 / var3);
                  var16 = getChildMeasureSpec(var2, this.getPaddingTop() + this.getPaddingBottom() + var27.topMargin + var27.bottomMargin, var27.height);
                  if(var27.width == 0 && var21 == 1073741824) {
                     if(var13 > 0) {
                        var11 = var13;
                     } else {
                        var11 = 0;
                     }

                     var26.measure(MeasureSpec.makeMeasureSpec(var11, 1073741824), var16);
                  } else {
                     var14 = var26.getMeasuredWidth() + var13;
                     var11 = var14;
                     if(var14 < 0) {
                        var11 = 0;
                     }

                     var26.measure(MeasureSpec.makeMeasureSpec(var11, 1073741824), var16);
                  }

                  var30 = View.combineMeasuredStates(var30, var26.getMeasuredState() & -16777216);
                  var3 -= var4;
                  var6 -= var13;
               }

               if(var15) {
                  this.mTotalLength += var26.getMeasuredWidth() + var27.leftMargin + var27.rightMargin + this.getNextLocationOffset(var26);
               } else {
                  var11 = this.mTotalLength;
                  this.mTotalLength = Math.max(var11, var26.getMeasuredWidth() + var11 + var27.leftMargin + var27.rightMargin + this.getNextLocationOffset(var26));
               }

               boolean var34;
               if(var20 != 1073741824 && var27.height == -1) {
                  var34 = true;
               } else {
                  var34 = false;
               }

               var16 = var27.topMargin + var27.bottomMargin;
               var14 = var26.getMeasuredHeight() + var16;
               var13 = Math.max(var10, var14);
               if(var34) {
                  var10 = var16;
               } else {
                  var10 = var14;
               }

               var10 = Math.max(var8, var10);
               if(var31 && var27.height == -1) {
                  var31 = true;
               } else {
                  var31 = false;
               }

               if(var23) {
                  var11 = var26.getBaseline();
                  if(var11 != -1) {
                     if(var27.gravity < 0) {
                        var8 = this.mGravity;
                     } else {
                        var8 = var27.gravity;
                     }

                     var8 = ((var8 & 112) >> 4 & -2) >> 1;
                     var28[var8] = Math.max(var28[var8], var11);
                     var25[var8] = Math.max(var25[var8], var14 - var11);
                  }
               }

               var8 = var10;
               var10 = var13;
            }
         }

         label216: {
            this.mTotalLength += this.getPaddingLeft() + this.getPaddingRight();
            if(var28[1] == -1 && var28[0] == -1 && var28[2] == -1) {
               var6 = var10;
               if(var28[3] == -1) {
                  break label216;
               }
            }

            var6 = Math.max(var10, Math.max(var28[3], Math.max(var28[0], Math.max(var28[1], var28[2]))) + Math.max(var25[3], Math.max(var25[0], Math.max(var25[1], var25[2]))));
         }

         var13 = var30;
         var11 = var6;
         var6 = var8;
      }

      if(var31 || var20 == 1073741824) {
         var6 = var11;
      }

      this.setMeasuredDimension(var17 | -16777216 & var13, View.resolveSizeAndState(Math.max(var6 + this.getPaddingTop() + this.getPaddingBottom(), this.getSuggestedMinimumHeight()), var2, var13 << 16));
      if(var9) {
         this.forceUniformHeight(var19, var1);
      }

   }

   int measureNullChild(int var1) {
      return 0;
   }

   void measureVertical(int var1, int var2) {
      this.mTotalLength = 0;
      int var13 = this.getVirtualChildCount();
      int var20 = MeasureSpec.getMode(var1);
      int var6 = MeasureSpec.getMode(var2);
      int var21 = this.mBaselineAlignedChildIndex;
      boolean var22 = this.mUseLargestChild;
      float var3 = 0.0F;
      int var10 = 0;
      int var15 = 0;
      int var9 = 0;
      int var5 = 0;
      int var8 = 0;
      int var11 = 0;
      boolean var14 = false;
      boolean var7 = true;

      boolean var12;
      int var16;
      int var17;
      int var18;
      View var23;
      int var28;
      int var31;
      for(var12 = false; var11 < var13; ++var11) {
         var23 = this.getVirtualChildAt(var11);
         if(var23 == null) {
            this.mTotalLength += this.measureNullChild(var11);
         } else if(var23.getVisibility() == 8) {
            var11 += this.getChildrenSkipCount(var23, var11);
         } else {
            if(this.hasDividerBeforeChildAt(var11)) {
               this.mTotalLength += this.mDividerHeight;
            }

            LinearLayoutCompat.LayoutParams var25 = (LinearLayoutCompat.LayoutParams)var23.getLayoutParams();
            var3 += var25.weight;
            if(var6 == 1073741824 && var25.height == 0 && var25.weight > 0.0F) {
               var31 = this.mTotalLength;
               this.mTotalLength = Math.max(var31, var25.topMargin + var31 + var25.bottomMargin);
               var14 = true;
            } else {
               if(var25.height == 0 && var25.weight > 0.0F) {
                  var25.height = -2;
                  var16 = 0;
               } else {
                  var16 = Integer.MIN_VALUE;
               }

               if(var3 == 0.0F) {
                  var17 = this.mTotalLength;
               } else {
                  var17 = 0;
               }

               this.measureChildBeforeLayout(var23, var11, var1, 0, var2, var17);
               if(var16 != Integer.MIN_VALUE) {
                  var25.height = var16;
               }

               var16 = var23.getMeasuredHeight();
               var17 = this.mTotalLength;
               this.mTotalLength = Math.max(var17, var17 + var16 + var25.topMargin + var25.bottomMargin + this.getNextLocationOffset(var23));
               if(var22) {
                  var9 = Math.max(var16, var9);
               }
            }

            if(var21 >= 0 && var21 == var11 + 1) {
               this.mBaselineChildTop = this.mTotalLength;
            }

            if(var11 < var21 && var25.weight > 0.0F) {
               throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won\'t work.  Either remove the weight, or don\'t set mBaselineAlignedChildIndex.");
            }

            boolean var30;
            if(var20 != 1073741824 && var25.width == -1) {
               var30 = true;
               var12 = true;
            } else {
               var30 = false;
            }

            var17 = var25.leftMargin + var25.rightMargin;
            var18 = var23.getMeasuredWidth() + var17;
            var15 = Math.max(var15, var18);
            var10 = View.combineMeasuredStates(var10, var23.getMeasuredState());
            boolean var26;
            if(var7 && var25.width == -1) {
               var26 = true;
            } else {
               var26 = false;
            }

            if(var25.weight > 0.0F) {
               if(!var30) {
                  var17 = var18;
               }

               var11 = Math.max(var5, var17);
               var28 = var8;
               var8 = var11;
            } else {
               if(var30) {
                  var18 = var17;
               }

               var28 = Math.max(var8, var18);
               var8 = var5;
            }

            var16 = this.getChildrenSkipCount(var23, var11);
            var5 = var8;
            var16 += var11;
            var8 = var28;
            var7 = var26;
            var11 = var16;
         }
      }

      var16 = var10;
      var10 = var15;
      if(this.mTotalLength > 0 && this.hasDividerBeforeChildAt(var13)) {
         this.mTotalLength += this.mDividerHeight;
      }

      var15 = var13;
      LinearLayoutCompat.LayoutParams var24;
      if(var22) {
         label250: {
            if(var6 != Integer.MIN_VALUE) {
               var13 = var10;
               if(var6 != 0) {
                  break label250;
               }
            }

            this.mTotalLength = 0;
            var11 = 0;

            while(true) {
               var13 = var10;
               if(var11 >= var15) {
                  break;
               }

               var23 = this.getVirtualChildAt(var11);
               if(var23 == null) {
                  this.mTotalLength += this.measureNullChild(var11);
               } else if(var23.getVisibility() == 8) {
                  var11 += this.getChildrenSkipCount(var23, var11);
               } else {
                  var24 = (LinearLayoutCompat.LayoutParams)var23.getLayoutParams();
                  var13 = this.mTotalLength;
                  this.mTotalLength = Math.max(var13, var13 + var9 + var24.topMargin + var24.bottomMargin + this.getNextLocationOffset(var23));
               }

               ++var11;
            }
         }

         var10 = var13;
      }

      var13 = var6;
      this.mTotalLength += this.getPaddingTop() + this.getPaddingBottom();
      var17 = View.resolveSizeAndState(Math.max(this.mTotalLength, this.getSuggestedMinimumHeight()), var2, 0);
      var6 = (16777215 & var17) - this.mTotalLength;
      if(!var14 && (var6 == 0 || var3 <= 0.0F)) {
         var6 = Math.max(var8, var5);
         if(var22 && var13 != 1073741824) {
            for(var5 = 0; var5 < var15; ++var5) {
               var23 = this.getVirtualChildAt(var5);
               if(var23 != null && var23.getVisibility() != 8 && ((LinearLayoutCompat.LayoutParams)var23.getLayoutParams()).weight > 0.0F) {
                  var23.measure(MeasureSpec.makeMeasureSpec(var23.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(var9, 1073741824));
               }
            }
         }

         var8 = var10;
         var5 = var6;
      } else {
         if(this.mWeightSum > 0.0F) {
            var3 = this.mWeightSum;
         }

         this.mTotalLength = 0;
         var9 = var6;
         byte var32 = 0;
         var6 = var8;
         var8 = var10;
         var5 = var16;

         for(var10 = var32; var10 < var15; ++var10) {
            var23 = this.getVirtualChildAt(var10);
            if(var23.getVisibility() != 8) {
               var24 = (LinearLayoutCompat.LayoutParams)var23.getLayoutParams();
               float var4 = var24.weight;
               if(var4 > 0.0F) {
                  var31 = (int)((float)var9 * var4 / var3);
                  var16 = this.getPaddingLeft();
                  var18 = this.getPaddingRight();
                  var11 = var9 - var31;
                  var9 = var24.leftMargin;
                  int var19 = var24.rightMargin;
                  var21 = var24.width;
                  var3 -= var4;
                  var16 = getChildMeasureSpec(var1, var16 + var18 + var9 + var19, var21);
                  if(var24.height == 0 && var13 == 1073741824) {
                     if(var31 > 0) {
                        var9 = var31;
                     } else {
                        var9 = 0;
                     }

                     var23.measure(var16, MeasureSpec.makeMeasureSpec(var9, 1073741824));
                  } else {
                     var31 += var23.getMeasuredHeight();
                     var9 = var31;
                     if(var31 < 0) {
                        var9 = 0;
                     }

                     var23.measure(var16, MeasureSpec.makeMeasureSpec(var9, 1073741824));
                  }

                  var5 = View.combineMeasuredStates(var5, var23.getMeasuredState() & -256);
                  var9 = var11;
               }

               var31 = var24.leftMargin + var24.rightMargin;
               var16 = var23.getMeasuredWidth() + var31;
               var11 = Math.max(var8, var16);
               boolean var29;
               if(var20 != 1073741824 && var24.width == -1) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               if(var29) {
                  var8 = var31;
               } else {
                  var8 = var16;
               }

               var31 = Math.max(var6, var8);
               boolean var27;
               if(var7 && var24.width == -1) {
                  var27 = true;
               } else {
                  var27 = false;
               }

               var28 = this.mTotalLength;
               this.mTotalLength = Math.max(var28, var23.getMeasuredHeight() + var28 + var24.topMargin + var24.bottomMargin + this.getNextLocationOffset(var23));
               var8 = var11;
               var7 = var27;
               var6 = var31;
            }
         }

         this.mTotalLength += this.getPaddingTop() + this.getPaddingBottom();
         var16 = var5;
         var5 = var6;
      }

      if(var7 || var20 == 1073741824) {
         var5 = var8;
      }

      this.setMeasuredDimension(View.resolveSizeAndState(Math.max(var5 + this.getPaddingLeft() + this.getPaddingRight(), this.getSuggestedMinimumWidth()), var1, var16), var17);
      if(var12) {
         this.forceUniformWidth(var15, var2);
      }

   }

   protected void onDraw(Canvas var1) {
      if(this.mDivider != null) {
         if(this.mOrientation == 1) {
            this.drawDividersVertical(var1);
         } else {
            this.drawDividersHorizontal(var1);
         }
      }
   }

   public void onInitializeAccessibilityEvent(AccessibilityEvent var1) {
      super.onInitializeAccessibilityEvent(var1);
      var1.setClassName(LinearLayoutCompat.class.getName());
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      var1.setClassName(LinearLayoutCompat.class.getName());
   }

   public void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      if(this.mOrientation == 1) {
         this.layoutVertical(var2, var3, var4, var5);
      } else {
         this.layoutHorizontal(var2, var3, var4, var5);
      }
   }

   protected void onMeasure(int var1, int var2) {
      if(this.mOrientation == 1) {
         this.measureVertical(var1, var2);
      } else {
         this.measureHorizontal(var1, var2);
      }
   }

   public void setBaselineAligned(boolean var1) {
      this.mBaselineAligned = var1;
   }

   public void setBaselineAlignedChildIndex(int var1) {
      if(var1 >= 0 && var1 < this.getChildCount()) {
         this.mBaselineAlignedChildIndex = var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("base aligned child index out of range (0, ");
         var2.append(this.getChildCount());
         var2.append(")");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void setDividerDrawable(Drawable var1) {
      if(var1 != this.mDivider) {
         this.mDivider = var1;
         boolean var2 = false;
         if(var1 != null) {
            this.mDividerWidth = var1.getIntrinsicWidth();
            this.mDividerHeight = var1.getIntrinsicHeight();
         } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
         }

         if(var1 == null) {
            var2 = true;
         }

         this.setWillNotDraw(var2);
         this.requestLayout();
      }
   }

   public void setDividerPadding(int var1) {
      this.mDividerPadding = var1;
   }

   public void setGravity(int var1) {
      if(this.mGravity != var1) {
         int var2 = var1;
         if((8388615 & var1) == 0) {
            var2 = var1 | 8388611;
         }

         var1 = var2;
         if((var2 & 112) == 0) {
            var1 = var2 | 48;
         }

         this.mGravity = var1;
         this.requestLayout();
      }

   }

   public void setHorizontalGravity(int var1) {
      var1 &= 8388615;
      if((8388615 & this.mGravity) != var1) {
         this.mGravity = var1 | this.mGravity & -8388616;
         this.requestLayout();
      }

   }

   public void setMeasureWithLargestChildEnabled(boolean var1) {
      this.mUseLargestChild = var1;
   }

   public void setOrientation(int var1) {
      if(this.mOrientation != var1) {
         this.mOrientation = var1;
         this.requestLayout();
      }

   }

   public void setShowDividers(int var1) {
      if(var1 != this.mShowDividers) {
         this.requestLayout();
      }

      this.mShowDividers = var1;
   }

   public void setVerticalGravity(int var1) {
      var1 &= 112;
      if((this.mGravity & 112) != var1) {
         this.mGravity = var1 | this.mGravity & -113;
         this.requestLayout();
      }

   }

   public void setWeightSum(float var1) {
      this.mWeightSum = Math.max(0.0F, var1);
   }

   public boolean shouldDelayChildPressedState() {
      return false;
   }

   @Retention(RetentionPolicy.SOURCE)
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public @interface DividerMode {
   }

   @Retention(RetentionPolicy.SOURCE)
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public @interface OrientationMode {
   }

   public static class LayoutParams extends MarginLayoutParams {

      public int gravity = -1;
      public float weight;


      public LayoutParams(int var1, int var2) {
         super(var1, var2);
         this.weight = 0.0F;
      }

      public LayoutParams(int var1, int var2, float var3) {
         super(var1, var2);
         this.weight = var3;
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.LinearLayoutCompat_Layout);
         this.weight = var3.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0F);
         this.gravity = var3.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
         var3.recycle();
      }

      public LayoutParams(LinearLayoutCompat.LayoutParams var1) {
         super(var1);
         this.weight = var1.weight;
         this.gravity = var1.gravity;
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }
   }
}
