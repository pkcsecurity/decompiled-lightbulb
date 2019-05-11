package com.facebook.react.flat;

import com.facebook.react.flat.FlatViewGroup;

interface AttachDetachListener {

   AttachDetachListener[] EMPTY_ARRAY = new AttachDetachListener[0];


   void onAttached(FlatViewGroup.InvalidateCallback var1);

   void onDetached();
}
