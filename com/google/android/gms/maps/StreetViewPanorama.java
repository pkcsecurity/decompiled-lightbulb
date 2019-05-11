package com.google.android.gms.maps;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;

public class StreetViewPanorama {

   private final IStreetViewPanoramaDelegate a;


   public StreetViewPanorama(@NonNull IStreetViewPanoramaDelegate var1) {
      this.a = (IStreetViewPanoramaDelegate)Preconditions.checkNotNull(var1, "delegate");
   }

   public interface OnStreetViewPanoramaChangeListener {
   }

   public interface OnStreetViewPanoramaLongClickListener {
   }

   public interface OnStreetViewPanoramaClickListener {
   }

   public interface OnStreetViewPanoramaCameraChangeListener {
   }
}
