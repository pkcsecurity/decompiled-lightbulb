package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.TooltipCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class BottomNavigationItemView extends FrameLayout implements MenuView.ItemView {

   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   public static final int INVALID_ITEM_POSITION = -1;
   private final int defaultMargin;
   private ImageView icon;
   private ColorStateList iconTint;
   private boolean isShifting;
   private MenuItemImpl itemData;
   private int itemPosition;
   private int labelVisibilityMode;
   private final TextView largeLabel;
   private float scaleDownFactor;
   private float scaleUpFactor;
   private float shiftAmount;
   private final TextView smallLabel;


   public BottomNavigationItemView(@NonNull Context var1) {
      this(var1, (AttributeSet)null);
   }

   public BottomNavigationItemView(@NonNull Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public BottomNavigationItemView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.itemPosition = -1;
      Resources var4 = this.getResources();
      LayoutInflater.from(var1).inflate(R.layout.design_bottom_navigation_item, this, true);
      this.setBackgroundResource(R.drawable.design_bottom_navigation_item_background);
      this.defaultMargin = var4.getDimensionPixelSize(R.dimen.design_bottom_navigation_margin);
      this.icon = (ImageView)this.findViewById(R.id.icon);
      this.smallLabel = (TextView)this.findViewById(R.id.smallLabel);
      this.largeLabel = (TextView)this.findViewById(R.id.largeLabel);
      ViewCompat.setImportantForAccessibility(this.smallLabel, 2);
      ViewCompat.setImportantForAccessibility(this.largeLabel, 2);
      this.setFocusable(true);
      this.calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
   }

   private void calculateTextScaleFactors(float var1, float var2) {
      this.shiftAmount = var1 - var2;
      this.scaleUpFactor = var2 * 1.0F / var1;
      this.scaleDownFactor = var1 * 1.0F / var2;
   }

   private void setViewLayoutParams(@NonNull View var1, int var2, int var3) {
      LayoutParams var4 = (LayoutParams)var1.getLayoutParams();
      var4.topMargin = var2;
      var4.gravity = var3;
      var1.setLayoutParams(var4);
   }

   private void setViewValues(@NonNull View var1, float var2, float var3, int var4) {
      var1.setScaleX(var2);
      var1.setScaleY(var3);
      var1.setVisibility(var4);
   }

   public MenuItemImpl getItemData() {
      return this.itemData;
   }

   public int getItemPosition() {
      return this.itemPosition;
   }

   public void initialize(MenuItemImpl var1, int var2) {
      this.itemData = var1;
      this.setCheckable(var1.isCheckable());
      this.setChecked(var1.isChecked());
      this.setEnabled(var1.isEnabled());
      this.setIcon(var1.getIcon());
      this.setTitle(var1.getTitle());
      this.setId(var1.getItemId());
      if(!TextUtils.isEmpty(var1.getContentDescription())) {
         this.setContentDescription(var1.getContentDescription());
      }

      TooltipCompat.setTooltipText(this, var1.getTooltipText());
      byte var3;
      if(var1.isVisible()) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      this.setVisibility(var3);
   }

   public int[] onCreateDrawableState(int var1) {
      int[] var2 = super.onCreateDrawableState(var1 + 1);
      if(this.itemData != null && this.itemData.isCheckable() && this.itemData.isChecked()) {
         mergeDrawableStates(var2, CHECKED_STATE_SET);
      }

      return var2;
   }

   public boolean prefersCondensedTitle() {
      return false;
   }

   public void setCheckable(boolean var1) {
      this.refreshDrawableState();
   }

   public void setChecked(boolean var1) {
      this.largeLabel.setPivotX((float)(this.largeLabel.getWidth() / 2));
      this.largeLabel.setPivotY((float)this.largeLabel.getBaseline());
      this.smallLabel.setPivotX((float)(this.smallLabel.getWidth() / 2));
      this.smallLabel.setPivotY((float)this.smallLabel.getBaseline());
      switch(this.labelVisibilityMode) {
      case -1:
         if(this.isShifting) {
            if(var1) {
               this.setViewLayoutParams(this.icon, this.defaultMargin, 49);
               this.setViewValues(this.largeLabel, 1.0F, 1.0F, 0);
            } else {
               this.setViewLayoutParams(this.icon, this.defaultMargin, 17);
               this.setViewValues(this.largeLabel, 0.5F, 0.5F, 4);
            }

            this.smallLabel.setVisibility(4);
         } else if(var1) {
            this.setViewLayoutParams(this.icon, (int)((float)this.defaultMargin + this.shiftAmount), 49);
            this.setViewValues(this.largeLabel, 1.0F, 1.0F, 0);
            this.setViewValues(this.smallLabel, this.scaleUpFactor, this.scaleUpFactor, 4);
         } else {
            this.setViewLayoutParams(this.icon, this.defaultMargin, 49);
            this.setViewValues(this.largeLabel, this.scaleDownFactor, this.scaleDownFactor, 4);
            this.setViewValues(this.smallLabel, 1.0F, 1.0F, 0);
         }
         break;
      case 0:
         if(var1) {
            this.setViewLayoutParams(this.icon, this.defaultMargin, 49);
            this.setViewValues(this.largeLabel, 1.0F, 1.0F, 0);
         } else {
            this.setViewLayoutParams(this.icon, this.defaultMargin, 17);
            this.setViewValues(this.largeLabel, 0.5F, 0.5F, 4);
         }

         this.smallLabel.setVisibility(4);
         break;
      case 1:
         if(var1) {
            this.setViewLayoutParams(this.icon, (int)((float)this.defaultMargin + this.shiftAmount), 49);
            this.setViewValues(this.largeLabel, 1.0F, 1.0F, 0);
            this.setViewValues(this.smallLabel, this.scaleUpFactor, this.scaleUpFactor, 4);
         } else {
            this.setViewLayoutParams(this.icon, this.defaultMargin, 49);
            this.setViewValues(this.largeLabel, this.scaleDownFactor, this.scaleDownFactor, 4);
            this.setViewValues(this.smallLabel, 1.0F, 1.0F, 0);
         }
         break;
      case 2:
         this.setViewLayoutParams(this.icon, this.defaultMargin, 17);
         this.largeLabel.setVisibility(8);
         this.smallLabel.setVisibility(8);
      }

      this.refreshDrawableState();
      this.setSelected(var1);
   }

   public void setEnabled(boolean var1) {
      super.setEnabled(var1);
      this.smallLabel.setEnabled(var1);
      this.largeLabel.setEnabled(var1);
      this.icon.setEnabled(var1);
      if(var1) {
         ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(this.getContext(), 1002));
      } else {
         ViewCompat.setPointerIcon(this, (PointerIconCompat)null);
      }
   }

   public void setIcon(Drawable var1) {
      Drawable var2 = var1;
      if(var1 != null) {
         ConstantState var3 = var1.getConstantState();
         if(var3 != null) {
            var1 = var3.newDrawable();
         }

         var2 = DrawableCompat.wrap(var1).mutate();
         DrawableCompat.setTintList(var2, this.iconTint);
      }

      this.icon.setImageDrawable(var2);
   }

   public void setIconSize(int var1) {
      LayoutParams var2 = (LayoutParams)this.icon.getLayoutParams();
      var2.width = var1;
      var2.height = var1;
      this.icon.setLayoutParams(var2);
   }

   public void setIconTintList(ColorStateList var1) {
      this.iconTint = var1;
      if(this.itemData != null) {
         this.setIcon(this.itemData.getIcon());
      }

   }

   public void setItemBackground(int var1) {
      Drawable var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         var2 = ContextCompat.getDrawable(this.getContext(), var1);
      }

      this.setItemBackground(var2);
   }

   public void setItemBackground(@Nullable Drawable var1) {
      ViewCompat.setBackground(this, var1);
   }

   public void setItemPosition(int var1) {
      this.itemPosition = var1;
   }

   public void setLabelVisibilityMode(int var1) {
      if(this.labelVisibilityMode != var1) {
         this.labelVisibilityMode = var1;
         boolean var2;
         if(this.itemData != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         if(var2) {
            this.setChecked(this.itemData.isChecked());
         }
      }

   }

   public void setShifting(boolean var1) {
      if(this.isShifting != var1) {
         this.isShifting = var1;
         boolean var2;
         if(this.itemData != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         if(var2) {
            this.setChecked(this.itemData.isChecked());
         }
      }

   }

   public void setShortcut(boolean var1, char var2) {}

   public void setTextAppearanceActive(@StyleRes int var1) {
      TextViewCompat.setTextAppearance(this.largeLabel, var1);
      this.calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
   }

   public void setTextAppearanceInactive(@StyleRes int var1) {
      TextViewCompat.setTextAppearance(this.smallLabel, var1);
      this.calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
   }

   public void setTextColor(@Nullable ColorStateList var1) {
      if(var1 != null) {
         this.smallLabel.setTextColor(var1);
         this.largeLabel.setTextColor(var1);
      }

   }

   public void setTitle(CharSequence var1) {
      this.smallLabel.setText(var1);
      this.largeLabel.setText(var1);
      if(this.itemData == null || TextUtils.isEmpty(this.itemData.getContentDescription())) {
         this.setContentDescription(var1);
      }

   }

   public boolean showsIcon() {
      return true;
   }
}
