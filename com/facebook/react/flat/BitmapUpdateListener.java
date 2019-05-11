package com.facebook.react.flat;

import android.graphics.Bitmap;

interface BitmapUpdateListener {

   void onBitmapReady(Bitmap var1);

   void onImageLoadEvent(int var1);

   void onSecondaryAttach(Bitmap var1);
}
