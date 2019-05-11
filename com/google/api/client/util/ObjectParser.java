package com.google.api.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public interface ObjectParser {

   <T extends Object> T a(InputStream var1, Charset var2, Class<T> var3) throws IOException;
}
