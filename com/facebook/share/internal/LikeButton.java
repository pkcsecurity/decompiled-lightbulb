package com.facebook.share.internal;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.FacebookButtonBase;
import com.facebook.common.R;

@Deprecated
public class LikeButton extends FacebookButtonBase {

   @Deprecated
   public LikeButton(Context var1, boolean var2) {
      super(var1, (AttributeSet)null, 0, 0, "fb_like_button_create", "fb_like_button_did_tap");
      this.setSelected(var2);
   }

   private void updateForLikeStatus() {
      if(this.isSelected()) {
         this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_like_icon_selected, 0, 0, 0);
         this.setText(this.getResources().getString(R.string.com_facebook_like_button_liked));
      } else {
         this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_icon, 0, 0, 0);
         this.setText(this.getResources().getString(R.string.com_facebook_like_button_not_liked));
      }
   }

   protected void configureButton(Context var1, AttributeSet var2, int var3, int var4) {
      super.configureButton(var1, var2, var3, var4);
      this.updateForLikeStatus();
   }

   protected int getDefaultRequestCode() {
      return 0;
   }

   protected int getDefaultStyleResource() {
      return R.style.com_facebook_button_like;
   }

   @Deprecated
   public void setSelected(boolean var1) {
      super.setSelected(var1);
      this.updateForLikeStatus();
   }
}
