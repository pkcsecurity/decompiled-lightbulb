package com.facebook.cache.common;

import java.io.IOException;
import java.io.OutputStream;

public interface WriterCallback {

   void write(OutputStream var1) throws IOException;
}
