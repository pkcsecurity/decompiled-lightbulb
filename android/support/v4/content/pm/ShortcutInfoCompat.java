package android.support.v4.content.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.IconCompat;
import android.text.TextUtils;
import java.util.Arrays;

public class ShortcutInfoCompat {

   ComponentName mActivity;
   Context mContext;
   CharSequence mDisabledMessage;
   IconCompat mIcon;
   String mId;
   Intent[] mIntents;
   boolean mIsAlwaysBadged;
   CharSequence mLabel;
   CharSequence mLongLabel;


   Intent addToIntent(Intent var1) {
      var1.putExtra("android.intent.extra.shortcut.INTENT", this.mIntents[this.mIntents.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
      if(this.mIcon != null) {
         Drawable var3 = null;
         Object var4 = null;
         if(this.mIsAlwaysBadged) {
            PackageManager var5 = this.mContext.getPackageManager();
            Drawable var2 = (Drawable)var4;
            if(this.mActivity != null) {
               try {
                  var2 = var5.getActivityIcon(this.mActivity);
               } catch (NameNotFoundException var6) {
                  var2 = (Drawable)var4;
               }
            }

            var3 = var2;
            if(var2 == null) {
               var3 = this.mContext.getApplicationInfo().loadIcon(var5);
            }
         }

         this.mIcon.addToShortcutIntent(var1, var3, this.mContext);
      }

      return var1;
   }

   @Nullable
   public ComponentName getActivity() {
      return this.mActivity;
   }

   @Nullable
   public CharSequence getDisabledMessage() {
      return this.mDisabledMessage;
   }

   @NonNull
   public String getId() {
      return this.mId;
   }

   @NonNull
   public Intent getIntent() {
      return this.mIntents[this.mIntents.length - 1];
   }

   @NonNull
   public Intent[] getIntents() {
      return (Intent[])Arrays.copyOf(this.mIntents, this.mIntents.length);
   }

   @Nullable
   public CharSequence getLongLabel() {
      return this.mLongLabel;
   }

   @NonNull
   public CharSequence getShortLabel() {
      return this.mLabel;
   }

   @RequiresApi(25)
   public ShortcutInfo toShortcutInfo() {
      android.content.pm.ShortcutInfo.Builder var1 = (new android.content.pm.ShortcutInfo.Builder(this.mContext, this.mId)).setShortLabel(this.mLabel).setIntents(this.mIntents);
      if(this.mIcon != null) {
         var1.setIcon(this.mIcon.toIcon());
      }

      if(!TextUtils.isEmpty(this.mLongLabel)) {
         var1.setLongLabel(this.mLongLabel);
      }

      if(!TextUtils.isEmpty(this.mDisabledMessage)) {
         var1.setDisabledMessage(this.mDisabledMessage);
      }

      if(this.mActivity != null) {
         var1.setActivity(this.mActivity);
      }

      return var1.build();
   }

   public static class Builder {

      private final ShortcutInfoCompat mInfo = new ShortcutInfoCompat();


      public Builder(@NonNull Context var1, @NonNull String var2) {
         this.mInfo.mContext = var1;
         this.mInfo.mId = var2;
      }

      @NonNull
      public ShortcutInfoCompat build() {
         if(TextUtils.isEmpty(this.mInfo.mLabel)) {
            throw new IllegalArgumentException("Shortcut must have a non-empty label");
         } else if(this.mInfo.mIntents != null && this.mInfo.mIntents.length != 0) {
            return this.mInfo;
         } else {
            throw new IllegalArgumentException("Shortcut must have an intent");
         }
      }

      @NonNull
      public ShortcutInfoCompat.Builder setActivity(@NonNull ComponentName var1) {
         this.mInfo.mActivity = var1;
         return this;
      }

      public ShortcutInfoCompat.Builder setAlwaysBadged() {
         this.mInfo.mIsAlwaysBadged = true;
         return this;
      }

      @NonNull
      public ShortcutInfoCompat.Builder setDisabledMessage(@NonNull CharSequence var1) {
         this.mInfo.mDisabledMessage = var1;
         return this;
      }

      @NonNull
      public ShortcutInfoCompat.Builder setIcon(IconCompat var1) {
         this.mInfo.mIcon = var1;
         return this;
      }

      @NonNull
      public ShortcutInfoCompat.Builder setIntent(@NonNull Intent var1) {
         return this.setIntents(new Intent[]{var1});
      }

      @NonNull
      public ShortcutInfoCompat.Builder setIntents(@NonNull Intent[] var1) {
         this.mInfo.mIntents = var1;
         return this;
      }

      @NonNull
      public ShortcutInfoCompat.Builder setLongLabel(@NonNull CharSequence var1) {
         this.mInfo.mLongLabel = var1;
         return this;
      }

      @NonNull
      public ShortcutInfoCompat.Builder setShortLabel(@NonNull CharSequence var1) {
         this.mInfo.mLabel = var1;
         return this;
      }
   }
}
