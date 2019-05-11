package com.facebook.cache.common;

import android.net.Uri;

public interface CacheKey {

   boolean containsUri(Uri var1);

   boolean equals(Object var1);

   String getUriString();

   int hashCode();

   String toString();
}
