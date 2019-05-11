package com.fasterxml.jackson.core.util;


public interface Instantiatable<T extends Object> {

   T createInstance();
}
