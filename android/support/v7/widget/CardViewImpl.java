package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardViewDelegate;

interface CardViewImpl {

   ColorStateList getBackgroundColor(CardViewDelegate var1);

   float getElevation(CardViewDelegate var1);

   float getMaxElevation(CardViewDelegate var1);

   float getMinHeight(CardViewDelegate var1);

   float getMinWidth(CardViewDelegate var1);

   float getRadius(CardViewDelegate var1);

   void initStatic();

   void initialize(CardViewDelegate var1, Context var2, ColorStateList var3, float var4, float var5, float var6);

   void onCompatPaddingChanged(CardViewDelegate var1);

   void onPreventCornerOverlapChanged(CardViewDelegate var1);

   void setBackgroundColor(CardViewDelegate var1, @Nullable ColorStateList var2);

   void setElevation(CardViewDelegate var1, float var2);

   void setMaxElevation(CardViewDelegate var1, float var2);

   void setRadius(CardViewDelegate var1, float var2);

   void updatePadding(CardViewDelegate var1);
}
