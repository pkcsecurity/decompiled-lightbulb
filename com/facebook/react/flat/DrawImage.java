package com.facebook.react.flat;

import android.content.Context;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.AttachDetachListener;
import javax.annotation.Nullable;

interface DrawImage extends AttachDetachListener {

   int getBorderColor();

   float getBorderRadius();

   float getBorderWidth();

   ScalingUtils.ScaleType getScaleType();

   boolean hasImageRequest();

   void setBorderColor(int var1);

   void setBorderRadius(float var1);

   void setBorderWidth(float var1);

   void setFadeDuration(int var1);

   void setProgressiveRenderingEnabled(boolean var1);

   void setReactTag(int var1);

   void setScaleType(ScalingUtils.ScaleType var1);

   void setSource(Context var1, @Nullable ReadableArray var2);

   void setTintColor(int var1);
}
