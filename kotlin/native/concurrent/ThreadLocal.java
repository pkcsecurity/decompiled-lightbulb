package kotlin.native.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.OptionalExpectation;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
@OptionalExpectation
@interface ThreadLocal {
}
