package com.airbnb.lottie.parser;

import android.util.JsonReader;
import java.io.IOException;

public interface ValueParser<V extends Object> {

   V b(JsonReader var1, float var2) throws IOException;
}
