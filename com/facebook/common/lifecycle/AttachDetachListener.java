package com.facebook.common.lifecycle;

import android.view.View;

public interface AttachDetachListener {

   void onAttachToView(View var1);

   void onDetachFromView(View var1);
}
